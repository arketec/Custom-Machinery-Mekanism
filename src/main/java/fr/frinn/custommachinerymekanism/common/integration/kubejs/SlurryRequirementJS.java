package fr.frinn.custommachinerymekanism.common.integration.kubejs;

import fr.frinn.custommachinery.api.integration.kubejs.RecipeJSBuilder;
import fr.frinn.custommachinery.api.requirement.RequirementIOMode;
import fr.frinn.custommachinerymekanism.common.requirement.SlurryPerTickRequirement;
import fr.frinn.custommachinerymekanism.common.requirement.SlurryRequirement;
import mekanism.api.chemical.slurry.Slurry;

public interface SlurryRequirementJS extends RecipeJSBuilder {

    default RecipeJSBuilder requireSlurry(Slurry slurry, long amount) {
        return requireSlurry(slurry, amount, "");
    }

    default RecipeJSBuilder requireSlurry(Slurry slurry, long amount, String tank) {
        return addRequirement(new SlurryRequirement(RequirementIOMode.INPUT, slurry, amount, tank));
    }

    default RecipeJSBuilder requireSlurryPerTick(Slurry slurry, long amount) {
        return requireSlurryPerTick(slurry, amount, "");
    }

    default RecipeJSBuilder requireSlurryPerTick(Slurry slurry, long amount, String tank) {
        return addRequirement(new SlurryPerTickRequirement(RequirementIOMode.INPUT, slurry, amount, tank));
    }

    default RecipeJSBuilder produceSlurry(Slurry slurry, long amount) {
        return produceSlurry(slurry, amount, "");
    }

    default RecipeJSBuilder produceSlurry(Slurry slurry, long amount, String tank) {
        return addRequirement(new SlurryRequirement(RequirementIOMode.OUTPUT, slurry, amount, tank));
    }

    default RecipeJSBuilder produceSlurryPerTick(Slurry slurry, long amount) {
        return produceSlurryPerTick(slurry, amount, "");
    }

    default RecipeJSBuilder produceSlurryPerTick(Slurry slurry, long amount, String tank) {
        return addRequirement(new SlurryPerTickRequirement(RequirementIOMode.OUTPUT, slurry, amount, tank));
    }
}
