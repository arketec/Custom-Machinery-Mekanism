package fr.frinn.custommachinerymekanism.common.integration.crafttweaker;

import fr.frinn.custommachinery.api.integration.crafttweaker.RecipeCTBuilder;
import fr.frinn.custommachinery.api.requirement.RequirementIOMode;
import fr.frinn.custommachinerymekanism.common.requirement.PigmentPerTickRequirement;
import fr.frinn.custommachinerymekanism.common.requirement.PigmentRequirement;
import mekanism.api.chemical.pigment.Pigment;

public interface PigmentRequirementCT<T> extends RecipeCTBuilder<T> {

    default T requirePigment(Pigment pigment, long amount) {
        return requirePigment(pigment, amount, "");
    }

    default T requirePigment(Pigment pigment, long amount, String tank) {
        return addRequirement(new PigmentRequirement(RequirementIOMode.INPUT, pigment, amount, tank));
    }

    default T requirePigmentPerTick(Pigment pigment, long amount) {
        return requirePigmentPerTick(pigment, amount, "");
    }

    default T requirePigmentPerTick(Pigment pigment, long amount, String tank) {
        return addRequirement(new PigmentPerTickRequirement(RequirementIOMode.INPUT, pigment, amount, tank));
    }

    default T producePigment(Pigment pigment, long amount) {
        return producePigment(pigment, amount, "");
    }

    default T producePigment(Pigment pigment, long amount, String tank) {
        return addRequirement(new PigmentRequirement(RequirementIOMode.OUTPUT, pigment, amount, tank));
    }

    default T producePigmentPerTick(Pigment pigment, long amount) {
        return producePigmentPerTick(pigment, amount, "");
    }

    default T producePigmentPerTick(Pigment pigment, long amount, String tank) {
        return addRequirement(new PigmentPerTickRequirement(RequirementIOMode.OUTPUT, pigment, amount, tank));
    }
}
