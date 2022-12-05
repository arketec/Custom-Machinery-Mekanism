package fr.frinn.custommachinerymekanism.common.integration.kubejs;

import fr.frinn.custommachinery.api.integration.kubejs.RecipeJSBuilder;
import fr.frinn.custommachinery.api.requirement.RequirementIOMode;
import fr.frinn.custommachinerymekanism.common.requirement.InfusionPerTickRequirement;
import fr.frinn.custommachinerymekanism.common.requirement.InfusionRequirement;
import mekanism.api.chemical.infuse.InfuseType;

public interface InfusionRequirementJS extends RecipeJSBuilder {

    default RecipeJSBuilder requireInfusion(InfuseType infusion, long amount) {
        return requireInfusion(infusion, amount, "");
    }

    default RecipeJSBuilder requireInfusion(InfuseType infusion, long amount, String tank) {
        return addRequirement(new InfusionRequirement(RequirementIOMode.INPUT, infusion, amount, tank));
    }

    default RecipeJSBuilder requireInfusionPerTick(InfuseType infusion, long amount) {
        return requireInfusionPerTick(infusion, amount, "");
    }

    default RecipeJSBuilder requireInfusionPerTick(InfuseType infusion, long amount, String tank) {
        return addRequirement(new InfusionPerTickRequirement(RequirementIOMode.INPUT, infusion, amount, tank));
    }

    default RecipeJSBuilder produceInfusion(InfuseType infusion, long amount) {
        return produceInfusion(infusion, amount, "");
    }

    default RecipeJSBuilder produceInfusion(InfuseType infusion, long amount, String tank) {
        return addRequirement(new InfusionRequirement(RequirementIOMode.OUTPUT, infusion, amount, tank));
    }

    default RecipeJSBuilder produceInfusionPerTick(InfuseType infusion, long amount) {
        return produceInfusionPerTick(infusion, amount, "");
    }

    default RecipeJSBuilder produceInfusionPerTick(InfuseType infusion, long amount, String tank) {
        return addRequirement(new InfusionPerTickRequirement(RequirementIOMode.OUTPUT, infusion, amount, tank));
    }
}
