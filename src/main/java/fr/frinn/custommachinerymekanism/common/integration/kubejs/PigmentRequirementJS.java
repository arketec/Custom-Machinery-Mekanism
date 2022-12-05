package fr.frinn.custommachinerymekanism.common.integration.kubejs;

import fr.frinn.custommachinery.api.integration.kubejs.RecipeJSBuilder;
import fr.frinn.custommachinery.api.requirement.RequirementIOMode;
import fr.frinn.custommachinerymekanism.common.requirement.PigmentPerTickRequirement;
import fr.frinn.custommachinerymekanism.common.requirement.PigmentRequirement;
import mekanism.api.chemical.pigment.Pigment;

public interface PigmentRequirementJS extends RecipeJSBuilder {

    default RecipeJSBuilder requirePigment(Pigment pigment, long amount) {
        return requirePigment(pigment, amount, "");
    }

    default RecipeJSBuilder requirePigment(Pigment pigment, long amount, String tank) {
        return addRequirement(new PigmentRequirement(RequirementIOMode.INPUT, pigment, amount, tank));
    }

    default RecipeJSBuilder requirePigmentPerTick(Pigment pigment, long amount) {
        return requirePigmentPerTick(pigment, amount, "");
    }

    default RecipeJSBuilder requirePigmentPerTick(Pigment pigment, long amount, String tank) {
        return addRequirement(new PigmentPerTickRequirement(RequirementIOMode.INPUT, pigment, amount, tank));
    }

    default RecipeJSBuilder producePigment(Pigment pigment, long amount) {
        return producePigment(pigment, amount, "");
    }

    default RecipeJSBuilder producePigment(Pigment pigment, long amount, String tank) {
        return addRequirement(new PigmentRequirement(RequirementIOMode.OUTPUT, pigment, amount, tank));
    }

    default RecipeJSBuilder producePigmentPerTick(Pigment pigment, long amount) {
        return producePigmentPerTick(pigment, amount, "");
    }

    default RecipeJSBuilder producePigmentPerTick(Pigment pigment, long amount, String tank) {
        return addRequirement(new PigmentPerTickRequirement(RequirementIOMode.OUTPUT, pigment, amount, tank));
    }
}
