package fr.frinn.custommachinerymekanism.common.network.data;

import fr.frinn.custommachinery.api.network.DataType;
import fr.frinn.custommachinerymekanism.Registration;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import net.minecraft.network.FriendlyByteBuf;

public class GasStackData extends ChemicalStackData<Gas, GasStack> {

    public GasStackData(short id, GasStack value) {
        super(id, value);
    }

    public GasStackData(short id, FriendlyByteBuf buf) {
        super(id, GasStack.readFromPacket(buf));
    }

    @Override
    public DataType<GasStackData, GasStack> getType() {
        return Registration.GAS_DATA.get();
    }
}
