package fr.frinn.custommachinerymekanism;

import fr.frinn.custommachinery.api.component.IMachineComponentManager;
import fr.frinn.custommachinery.api.machine.MachineTile;
import fr.frinn.custommachinerymekanism.client.ClientHandler;
import fr.frinn.custommachinerymekanism.common.component.handler.GasComponentHandler;
import fr.frinn.custommachinerymekanism.common.component.handler.InfusionComponentHandler;
import fr.frinn.custommachinerymekanism.common.component.handler.PigmentComponentHandler;
import fr.frinn.custommachinerymekanism.common.component.handler.SlurryComponentHandler;
import mekanism.common.capabilities.Capabilities;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod(CustomMachineryMekanism.MODID)
public class CustomMachineryMekanism {

    public static final String MODID = "custommachinerymekanism";

    public CustomMachineryMekanism() {
        final IEventBus MOD_BUS = FMLJavaModLoadingContext.get().getModEventBus();

        Registration.MACHINE_COMPONENTS.register(MOD_BUS);
        Registration.GUI_ELEMENTS.register(MOD_BUS);
        Registration.REQUIREMENTS.register(MOD_BUS);
        Registration.DATAS.register(MOD_BUS);

        final IEventBus FORGE_BUS = MinecraftForge.EVENT_BUS;

        FORGE_BUS.addGenericListener(BlockEntity.class, this::attachCapabilities);

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientHandler::clientInit);
    }

    private void attachCapabilities(AttachCapabilitiesEvent<BlockEntity> event) {
        if(event.getObject() instanceof MachineTile machine) {
            event.addCapability(new ResourceLocation(MODID, "extension"), new ICapabilityProvider() {
                @NotNull
                @Override
                public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction side) {
                    IMachineComponentManager manager = machine.getComponentManager();

                    if(capability == Capabilities.GAS_HANDLER_CAPABILITY)
                        return manager.getComponentHandler(Registration.GAS_MACHINE_COMPONENT.get())
                                .map(handler -> ((GasComponentHandler)handler).getSidedHandler(side))
                                .orElse(LazyOptional.empty())
                                .cast();
                    else if(capability == Capabilities.INFUSION_HANDLER_CAPABILITY)
                        return manager.getComponentHandler(Registration.INFUSION_MACHINE_COMPONENT.get())
                                .map(handler -> ((InfusionComponentHandler)handler).getSidedHandler(side))
                                .orElse(LazyOptional.empty())
                                .cast();
                    else if(capability == Capabilities.PIGMENT_HANDLER_CAPABILITY)
                        return manager.getComponentHandler(Registration.PIGMENT_MACHINE_COMPONENT.get())
                                .map(handler -> ((PigmentComponentHandler)handler).getSidedHandler(side))
                                .orElse(LazyOptional.empty())
                                .cast();
                    else if(capability == Capabilities.SLURRY_HANDLER_CAPABILITY)
                        return manager.getComponentHandler(Registration.SLURRY_MACHINE_COMPONENT.get())
                                .map(handler -> ((SlurryComponentHandler)handler).getSidedHandler(side))
                                .orElse(LazyOptional.empty())
                                .cast();
                    else if(capability == Capabilities.HEAT_HANDLER_CAPABILITY)
                        return manager.getComponent(Registration.HEAT_MACHINE_COMPONENT.get())
                                .map(component -> component.getHeatHandler(side))
                                .orElse(LazyOptional.empty())
                                .cast();

                    return LazyOptional.empty();
                }
            });
        }
    }
}
