package fr.frinn.custommachinerymekanism.common.network.data;

import fr.frinn.custommachinery.api.network.DataType;
import fr.frinn.custommachinerymekanism.Registration;
import mekanism.api.chemical.pigment.Pigment;
import mekanism.api.chemical.pigment.PigmentStack;
import net.minecraft.network.FriendlyByteBuf;

public class PigmentStackData extends ChemicalStackData<Pigment, PigmentStack> {

    public PigmentStackData(short id, PigmentStack value) {
        super(id, value);
    }

    public PigmentStackData(short id, FriendlyByteBuf buf) {
        super(id, PigmentStack.readFromPacket(buf));
    }

    @Override
    public DataType<PigmentStackData, PigmentStack> getType() {
        return Registration.PIGMENT_DATA.get();
    }
}
