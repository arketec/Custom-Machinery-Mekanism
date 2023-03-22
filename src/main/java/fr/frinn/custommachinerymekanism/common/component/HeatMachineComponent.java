package fr.frinn.custommachinerymekanism.common.component;

import com.google.common.collect.Maps;
import fr.frinn.custommachinery.api.codec.NamedCodec;
import fr.frinn.custommachinery.api.component.ComponentIOMode;
import fr.frinn.custommachinery.api.component.IMachineComponentManager;
import fr.frinn.custommachinery.api.component.IMachineComponentTemplate;
import fr.frinn.custommachinery.api.component.ISerializableComponent;
import fr.frinn.custommachinery.api.component.ISideConfigComponent;
import fr.frinn.custommachinery.api.component.ITickableComponent;
import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.api.network.DataType;
import fr.frinn.custommachinery.api.network.ISyncable;
import fr.frinn.custommachinery.api.network.ISyncableStuff;
import fr.frinn.custommachinery.impl.component.AbstractMachineComponent;
import fr.frinn.custommachinery.impl.component.config.RelativeSide;
import fr.frinn.custommachinery.impl.component.config.SideConfig;
import fr.frinn.custommachinery.impl.component.config.SideMode;
import fr.frinn.custommachinerymekanism.Registration;
import mekanism.api.heat.HeatAPI.HeatTransfer;
import mekanism.api.heat.IHeatCapacitor;
import mekanism.api.heat.IHeatHandler;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.capabilities.heat.BasicHeatCapacitor;
import mekanism.common.capabilities.heat.ITileHeatHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class HeatMachineComponent extends AbstractMachineComponent implements ISideConfigComponent, ITileHeatHandler, ISerializableComponent, ISyncableStuff, ITickableComponent {

    private final double baseTemp;
    private final SideConfig config;
    private final BasicHeatCapacitor capacitor;
    private final Map<Direction, LazyOptional<IHeatHandler>> neighbours = Maps.newEnumMap(Direction.class);
    private LazyOptional<IHeatHandler> handler;
    private double lastEnvironmentalLoss;

    public HeatMachineComponent(IMachineComponentManager manager, ComponentIOMode mode, double capacity, double baseTemp, double inverseConductionCoefficient, double inverseInsulationCoefficient, SideConfig.Template config) {
        super(manager, mode);
        this.baseTemp = baseTemp;
        this.config = config.build(this);
        this.config.setCallback(this::onConfigChange);
        this.capacitor = BasicHeatCapacitor.create(capacity, inverseConductionCoefficient, inverseInsulationCoefficient, () -> baseTemp, null);
        this.handler = LazyOptional.of(() -> this);
    }

    @Override
    public MachineComponentType<HeatMachineComponent> getType() {
        return Registration.HEAT_MACHINE_COMPONENT.get();
    }

    @Override
    public SideConfig getConfig() {
        return this.config;
    }

    @Override
    public String getId() {
        return "Heat";
    }

    @Override
    public void serverTick() {
        this.capacitor.update();
        this.updateNeighbours();
        HeatTransfer transfer = this.simulate();
        this.lastEnvironmentalLoss = transfer.environmentTransfer();
    }

    public double getLastEnvironmentalLoss() {
        return this.lastEnvironmentalLoss;
    }

    public double getHeatFillPercent() {
        return (this.capacitor.getTemperature() - this.baseTemp) / this.capacitor.getHeatCapacity();
    }

    private void onConfigChange(RelativeSide side, SideMode old, SideMode now) {
        this.handler.invalidate();
        this.handler = LazyOptional.of(() -> this);
        if(old.isNone())
            this.getManager().getLevel().updateNeighborsAt(this.getManager().getTile().getBlockPos(), this.getManager().getTile().getBlockState().getBlock());
    }

    private void updateNeighbours() {
        Level level = this.getManager().getLevel();
        BlockPos pos = this.getManager().getTile().getBlockPos();
        for(Direction side : Direction.values()) {
            LazyOptional<IHeatHandler> handler = this.neighbours.get(side);
            if(handler == null) {
                this.neighbours.put(side, LazyOptional.empty());
                continue;
            }
            else if(handler.isPresent())
                continue;
            BlockEntity be = level.getBlockEntity(pos.relative(side));
            if(be == null)
                continue;
            this.neighbours.put(side, be.getCapability(Capabilities.HEAT_HANDLER_CAPABILITY, side.getOpposite()));
        }
    }

    public LazyOptional<IHeatHandler> getHeatHandler(@Nullable Direction side) {
        if(!this.config.getSideMode(side).isNone())
            return this.handler;
        return LazyOptional.empty();
    }

    @Override
    public void serialize(CompoundTag nbt) {
        nbt.putDouble("Heat", this.capacitor.getHeat());
    }

    @Override
    public void deserialize(CompoundTag nbt) {
        if(nbt.contains("Heat", Tag.TAG_DOUBLE))
            this.capacitor.setHeat(nbt.getDouble("Heat"));
    }

    @Override
    public void getStuffToSync(Consumer<ISyncable<?, ?>> container) {
        container.accept(DataType.createSyncable(SideConfig.class, this::getConfig, this.config::set));
        container.accept(DataType.createSyncable(Double.class, this.capacitor::getHeat, this.capacitor::setHeat));
        container.accept(DataType.createSyncable(Double.class, this::getLastEnvironmentalLoss, loss -> this.lastEnvironmentalLoss = loss));
    }

    /** HEAT HANDLER STUFF **/

    @Override
    public List<IHeatCapacitor> getHeatCapacitors(@Nullable Direction direction) {
        return Collections.singletonList(this.capacitor);
    }

    @Override
    public void onContentsChanged() {

    }

    @Nullable
    @Override
    public IHeatHandler getAdjacent(Direction side) {
        return this.neighbours.get(side).orElse(null);
    }

    public static class Template implements IMachineComponentTemplate<HeatMachineComponent> {

        public static final NamedCodec<Template> CODEC = NamedCodec.record(templateInstance ->
                templateInstance.group(
                        ComponentIOMode.CODEC.optionalFieldOf("mode", ComponentIOMode.INPUT).forGetter(template -> template.mode),
                        NamedCodec.DOUBLE.optionalFieldOf("capacity", 373.0D).forGetter(template -> template.capacity),
                        NamedCodec.DOUBLE.optionalFieldOf("base_temp", 300.0D).forGetter(template -> template.baseTemp),
                        NamedCodec.DOUBLE.optionalFieldOf("conduction", 1.0D).forGetter(template -> template.inverseConductionCoefficient),
                        NamedCodec.DOUBLE.optionalFieldOf("insulation", 0.0D).forGetter(template -> template.inverseInsulationCoefficient),
                        SideConfig.Template.CODEC.optionalFieldOf("config").forGetter(template -> Optional.of(template.config))
                ).apply(templateInstance, (mode, capacity, baseTemp, conduction, insulation, config) ->
                        new Template(mode, capacity, baseTemp, conduction, insulation, config.orElse(mode.getBaseConfig()))
                ), "Heat machine component"
        );

        private final ComponentIOMode mode;
        private final double capacity;
        private final double baseTemp;
        private final double inverseConductionCoefficient;
        private final double inverseInsulationCoefficient;
        private final SideConfig.Template config;

        public Template(ComponentIOMode mode, double capacity, double baseTemp, double inverseConductionCoefficient, double inverseInsulationCoefficient, SideConfig.Template config) {
            this.mode = mode;
            this.capacity = capacity;
            this.baseTemp = baseTemp;
            this.inverseConductionCoefficient = inverseConductionCoefficient;
            this.inverseInsulationCoefficient = inverseInsulationCoefficient;
            this.config = config;
        }

        @Override
        public MachineComponentType<HeatMachineComponent> getType() {
            return Registration.HEAT_MACHINE_COMPONENT.get();
        }

        @Override
        public String getId() {
            return "Heat";
        }

        @Override
        public boolean canAccept(Object ingredient, boolean isInput, IMachineComponentManager manager) {
            return true;
        }

        @Override
        public HeatMachineComponent build(IMachineComponentManager manager) {
            return new HeatMachineComponent(manager, this.mode, this.capacity, this.baseTemp, this.inverseConductionCoefficient, this.inverseInsulationCoefficient, this.config);
        }
    }
}
