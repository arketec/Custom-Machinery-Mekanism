package fr.frinn.custommachinerymekanism.common.requirement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.api.crafting.CraftingResult;
import fr.frinn.custommachinery.api.crafting.ICraftingContext;
import fr.frinn.custommachinery.api.requirement.RequirementIOMode;
import fr.frinn.custommachinery.api.requirement.RequirementType;
import fr.frinn.custommachinery.impl.codec.CodecLogger;
import fr.frinn.custommachinery.impl.codec.EnumCodec;
import fr.frinn.custommachinery.impl.requirement.AbstractDelayedChanceableRequirement;
import fr.frinn.custommachinerymekanism.Registration;
import fr.frinn.custommachinerymekanism.common.component.HeatMachineComponent;
import mekanism.api.heat.IHeatCapacitor;
import net.minecraft.network.chat.TranslatableComponent;

public class HeatRequirement extends AbstractDelayedChanceableRequirement<HeatMachineComponent> {

    public static final Codec<HeatRequirement> CODEC = RecordCodecBuilder.create(heatRequirementInstance ->
            heatRequirementInstance.group(
                    EnumCodec.of(RequirementIOMode.class).fieldOf("mode").forGetter(HeatRequirement::getMode),
                    Codec.doubleRange(0.0D, Double.MAX_VALUE).fieldOf("amount").forGetter(requirement -> requirement.amount),
                    CodecLogger.loggedOptional(Codec.doubleRange(0.0D, 1.0D), "chance", 1.0D).forGetter(HeatRequirement::getChance),
                    CodecLogger.loggedOptional(Codec.doubleRange(0.0D, 1.0D), "delay", 0.0D).forGetter(HeatRequirement::getDelay)
            ).apply(heatRequirementInstance, (mode, amount, chance, delay) -> {
                HeatRequirement requirement = new HeatRequirement(mode, amount);
                requirement.setChance(chance);
                requirement.setDelay(delay);
                return requirement;
            })
    );

    private final double amount;

    public HeatRequirement(RequirementIOMode mode, double amount) {
        super(mode);
        this.amount = amount;
    }

    @Override
    public RequirementType<HeatRequirement> getType() {
        return Registration.HEAT_REQUIREMENT.get();
    }

    @Override
    public MachineComponentType<HeatMachineComponent> getComponentType() {
        return Registration.HEAT_MACHINE_COMPONENT.get();
    }

    @Override
    public boolean test(HeatMachineComponent component, ICraftingContext context) {
        double amount = context.getModifiedValue(this.amount, this, null);
        if(getMode() == RequirementIOMode.INPUT)
            return component.getHeatCapacitors(null).get(0).getHeat() >= amount;
        return true;
    }

    @Override
    public CraftingResult processStart(HeatMachineComponent component, ICraftingContext context) {
        if(getMode() != RequirementIOMode.INPUT || getDelay() != 0)
            return CraftingResult.pass();

        double amount = context.getModifiedValue(this.amount, this, null);
        IHeatCapacitor capacitor = component.getHeatCapacitors(null).get(0);
        if(capacitor.getHeat() < amount)
            return CraftingResult.error(new TranslatableComponent("custommachinerymekanism.requirements.heat.error.input", amount, capacitor.getHeat()));
        capacitor.handleHeat(-amount);
        return CraftingResult.success();
    }

    @Override
    public CraftingResult processEnd(HeatMachineComponent component, ICraftingContext context) {
        if(getMode() != RequirementIOMode.OUTPUT || getDelay() != 0)
            return CraftingResult.pass();

        double amount = context.getModifiedValue(this.amount, this, null);
        component.getHeatCapacitors(null).get(0).handleHeat(amount);
        return CraftingResult.success();
    }

    @Override
    public CraftingResult execute(HeatMachineComponent component, ICraftingContext context) {
        double amount = context.getModifiedValue(this.amount, this, null);
        IHeatCapacitor capacitor = component.getHeatCapacitors(null).get(0);
        if(getMode() == RequirementIOMode.INPUT) {
            if(capacitor.getHeat() < amount)
                return CraftingResult.error(new TranslatableComponent("custommachinerymekanism.requirements.heat.error.input", amount, capacitor.getHeat()));
            capacitor.handleHeat(-amount);
            return CraftingResult.success();
        } else {
            capacitor.handleHeat(amount);
            return CraftingResult.success();
        }
    }
}
