package fr.frinn.custommachinerymekanism.common.utils;

import fr.frinn.custommachinery.api.codec.NamedCodec;
import fr.frinn.custommachinerymekanism.Registration;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.infuse.InfuseType;
import mekanism.api.chemical.pigment.Pigment;
import mekanism.api.chemical.slurry.Slurry;

public class Codecs {

    public static final NamedCodec<Gas> GAS = NamedCodec.lazy(() -> NamedCodec.registrar(Registration.REGISTRIES.get(MekanismAPI.gasRegistry().getRegistryKey())), "Gas");
    public static final NamedCodec<InfuseType> INFUSE_TYPE = NamedCodec.lazy(() -> NamedCodec.registrar(Registration.REGISTRIES.get(MekanismAPI.infuseTypeRegistry().getRegistryKey())), "Infuse type");
    public static final NamedCodec<Pigment> PIGMENT = NamedCodec.lazy(() -> NamedCodec.registrar(Registration.REGISTRIES.get(MekanismAPI.pigmentRegistry().getRegistryKey())), "Pigment");
    public static final NamedCodec<Slurry> SLURRY = NamedCodec.lazy(() -> NamedCodec.registrar(Registration.REGISTRIES.get(MekanismAPI.slurryRegistry().getRegistryKey())), "Slurry");
}
