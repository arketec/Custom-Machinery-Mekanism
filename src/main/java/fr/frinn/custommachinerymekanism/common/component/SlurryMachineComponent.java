package fr.frinn.custommachinerymekanism.common.component;

import com.mojang.serialization.Codec;
import fr.frinn.custommachinery.api.component.ComponentIOMode;
import fr.frinn.custommachinery.api.component.IMachineComponentManager;
import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.impl.component.config.SideConfig;
import fr.frinn.custommachinerymekanism.Registration;
import fr.frinn.custommachinerymekanism.common.utils.Codecs;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.slurry.Slurry;
import mekanism.api.chemical.slurry.SlurryStack;
import net.minecraft.nbt.CompoundTag;

import java.util.List;

public class SlurryMachineComponent extends ChemicalMachineComponent<Slurry, SlurryStack> {

    public SlurryMachineComponent(IMachineComponentManager manager, String id, long capacity, ComponentIOMode mode, List<Slurry> filter, boolean whitelist, long maxInput, long maxOutput, SideConfig.Template config) {
        super(manager, id, capacity, mode, filter, whitelist, maxInput, maxOutput, config);
    }

    @Override
    public MachineComponentType<SlurryMachineComponent> getType() {
        return Registration.SLURRY_MACHINE_COMPONENT.get();
    }

    @Override
    public SlurryStack empty() {
        return SlurryStack.EMPTY;
    }

    @Override
    public SlurryStack createStack(Slurry type, long amount) {
        return new SlurryStack(type, amount);
    }

    @Override
    public SlurryStack readFromNBT(CompoundTag nbt) {
        return SlurryStack.readFromNBT(nbt);
    }

    public static class Template extends ChemicalMachineComponent.Template<Slurry, SlurryStack, SlurryMachineComponent> {

        public static final Codec<ChemicalMachineComponent.Template<Slurry, SlurryStack, SlurryMachineComponent>> CODEC = makeCodec(Codecs.SLURRY, Template::new);

        public Template(String id, long capacity, ComponentIOMode mode, List<Slurry> filter, boolean whitelist, long maxInput, long maxOutput, SideConfig.Template config) {
            super(id, capacity, mode, filter, whitelist, maxInput, maxOutput, config);
        }

        @Override
        public boolean isSameType(ChemicalStack<?> stack) {
            return stack instanceof SlurryStack;
        }

        @Override
        public SlurryMachineComponent build(IMachineComponentManager manager, String id, long capacity, ComponentIOMode mode, List<Slurry> filter, boolean whitelist, long maxInput, long maxOutput, SideConfig.Template config) {
            return new SlurryMachineComponent(manager, id, capacity, mode, filter, whitelist, maxInput, maxOutput, config);
        }

        @Override
        public MachineComponentType<SlurryMachineComponent> getType() {
            return Registration.SLURRY_MACHINE_COMPONENT.get();
        }
    }
}
