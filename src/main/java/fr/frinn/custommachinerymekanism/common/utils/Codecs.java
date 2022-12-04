package fr.frinn.custommachinerymekanism.common.utils;

import com.mojang.serialization.Codec;
import fr.frinn.custommachinery.impl.codec.EnhancedListCodec;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.infuse.InfuseType;
import mekanism.api.chemical.pigment.Pigment;
import mekanism.api.chemical.slurry.Slurry;
import net.minecraft.util.ExtraCodecs;

import java.util.List;

public class Codecs {

    public static final Codec<Gas> GAS = ExtraCodecs.lazyInitializedCodec(() -> MekanismAPI.gasRegistry().getCodec());
    public static final Codec<InfuseType> INFUSE_TYPE = ExtraCodecs.lazyInitializedCodec(() -> MekanismAPI.infuseTypeRegistry().getCodec());
    public static final Codec<Pigment> PIGMENT = ExtraCodecs.lazyInitializedCodec(() -> MekanismAPI.pigmentRegistry().getCodec());
    public static final Codec<Slurry> SLURRY = ExtraCodecs.lazyInitializedCodec(() -> MekanismAPI.slurryRegistry().getCodec());

    public static <T> Codec<List<T>> list(Codec<T> codec) {
        return new EnhancedListCodec<>(codec);
    }
}
