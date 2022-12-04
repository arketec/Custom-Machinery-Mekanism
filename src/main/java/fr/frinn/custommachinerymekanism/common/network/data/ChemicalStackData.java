package fr.frinn.custommachinerymekanism.common.network.data;

import fr.frinn.custommachinery.api.network.IData;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import net.minecraft.network.FriendlyByteBuf;

public abstract class ChemicalStackData<C extends Chemical<C>, S extends ChemicalStack<C>> implements IData<S> {

    private final short id;
    private final S value;

    public ChemicalStackData(short id, S value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public short getID() {
        return this.id;
    }

    @Override
    public S getValue() {
        return this.value;
    }

    @Override
    public void writeData(FriendlyByteBuf buffer) {
        IData.super.writeData(buffer);
        this.value.writeToPacket(buffer);
    }
}
