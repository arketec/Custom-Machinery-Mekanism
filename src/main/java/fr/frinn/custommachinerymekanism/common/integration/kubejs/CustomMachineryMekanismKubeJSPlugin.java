package fr.frinn.custommachinerymekanism.common.integration.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.RecipeExceptionJS;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.Wrapper;
import dev.latvian.mods.rhino.util.EnumTypeWrapper;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.infuse.InfusionStack;
import mekanism.api.chemical.pigment.PigmentStack;
import mekanism.api.chemical.slurry.SlurryStack;
import mekanism.common.util.UnitDisplayUtils.TemperatureUnit;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiFunction;
import java.util.function.Function;

public class CustomMachineryMekanismKubeJSPlugin extends KubeJSPlugin {

    @Override
    public void addTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
        typeWrappers.register(GasStack.class, o -> of(o, MekanismAPI.EMPTY_GAS, GasStack.EMPTY, MekanismAPI.gasRegistry()::getValue, GasStack::new));
        typeWrappers.register(InfusionStack.class, o -> of(o, MekanismAPI.EMPTY_INFUSE_TYPE, InfusionStack.EMPTY, MekanismAPI.infuseTypeRegistry()::getValue, InfusionStack::new));
        typeWrappers.register(PigmentStack.class, o -> of(o, MekanismAPI.EMPTY_PIGMENT, PigmentStack.EMPTY, MekanismAPI.pigmentRegistry()::getValue, PigmentStack::new));
        typeWrappers.register(SlurryStack.class, o -> of(o, MekanismAPI.EMPTY_SLURRY, SlurryStack.EMPTY, MekanismAPI.slurryRegistry()::getValue, SlurryStack::new));
        typeWrappers.register(TemperatureUnit.class, EnumTypeWrapper.get(TemperatureUnit.class));
    }

    @SuppressWarnings("unchecked")
    private static <C extends Chemical<C>, S extends ChemicalStack<C>> S of(Object o, C air, S empty, Function<ResourceLocation, C> getter, BiFunction<C, Long, S> maker) {
        final long BASE_AMOUNT = 1000L;

        if(o instanceof Wrapper w)
            o = w.unwrap();

        if(o == null || o == empty)
            return empty;
        else if(o.getClass().isInstance(empty.getClass()))
            return (S)o;
        else if (o.getClass().isInstance(air.getClass()) && o != air) {
            return maker.apply((C)o, BASE_AMOUNT);
        } else if(o instanceof ResourceLocation loc) {
            C chemical = getter.apply(loc);
            if(chemical == air)
                throw new RecipeExceptionJS("Chemical " + loc + " not found!");
            return maker.apply(chemical, BASE_AMOUNT);
        } else if (o instanceof CharSequence) {
            String s = o.toString().trim();
            long amount = BASE_AMOUNT;

            if (s.isEmpty() || s.equals("-") || s.equals("empty")) {
                return empty;
            }

            String[] s1 = s.split(" ", 2);
            if(s1.length == 2)
                amount = Long.parseLong(s1[1]);

            C chemical = getter.apply(new ResourceLocation(s1[0]));
            if(chemical == air)
                throw new RecipeExceptionJS("Chemical " + s1[0] + " not found!");
            return maker.apply(chemical, amount);
        }

        return empty;
    }
}
