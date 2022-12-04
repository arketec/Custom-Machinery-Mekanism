package fr.frinn.custommachinerymekanism.common.network.data;

import fr.frinn.custommachinery.api.network.DataType;
import fr.frinn.custommachinerymekanism.Registration;
import mekanism.api.chemical.slurry.Slurry;
import mekanism.api.chemical.slurry.SlurryStack;
import net.minecraft.network.FriendlyByteBuf;

public class SlurryStackData extends ChemicalStackData<Slurry, SlurryStack> {

    public SlurryStackData(short id, SlurryStack value) {
        super(id, value);
    }

    public SlurryStackData(short id, FriendlyByteBuf buf) {
        super(id, SlurryStack.readFromPacket(buf));
    }

    @Override
    public DataType<SlurryStackData, SlurryStack> getType() {
        return Registration.SLURRY_DATA.get();
    }
}
