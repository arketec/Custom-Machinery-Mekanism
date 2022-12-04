package fr.frinn.custommachinerymekanism.common.network.data;

import fr.frinn.custommachinery.api.network.DataType;
import fr.frinn.custommachinerymekanism.Registration;
import mekanism.api.chemical.infuse.InfuseType;
import mekanism.api.chemical.infuse.InfusionStack;
import net.minecraft.network.FriendlyByteBuf;

public class InfusionStackData extends ChemicalStackData<InfuseType, InfusionStack> {

    public InfusionStackData(short id, InfusionStack value) {
        super(id, value);
    }

    public InfusionStackData(short id, FriendlyByteBuf buf) {
        super(id, InfusionStack.readFromPacket(buf));
    }

    @Override
    public DataType<InfusionStackData, InfusionStack> getType() {
        return Registration.INFUSION_DATA.get();
    }
}
