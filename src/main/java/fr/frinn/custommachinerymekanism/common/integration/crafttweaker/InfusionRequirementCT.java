package fr.frinn.custommachinerymekanism.common.integration.crafttweaker;

import fr.frinn.custommachinery.api.integration.crafttweaker.RecipeCTBuilder;
import fr.frinn.custommachinery.api.requirement.RequirementIOMode;
import fr.frinn.custommachinerymekanism.common.requirement.InfusionPerTickRequirement;
import fr.frinn.custommachinerymekanism.common.requirement.InfusionRequirement;
import mekanism.api.chemical.infuse.InfuseType;

public interface InfusionRequirementCT<T> extends RecipeCTBuilder<T> {

    default T requireInfusion(InfuseType infusion, long amount) {
        return requireInfusion(infusion, amount, "");
    }

    default T requireInfusion(InfuseType infusion, long amount, String tank) {
        return addRequirement(new InfusionRequirement(RequirementIOMode.INPUT, infusion, amount, tank));
    }

    default T requireInfusionPerTick(InfuseType infusion, long amount) {
        return requireInfusionPerTick(infusion, amount, "");
    }

    default T requireInfusionPerTick(InfuseType infusion, long amount, String tank) {
        return addRequirement(new InfusionPerTickRequirement(RequirementIOMode.INPUT, infusion, amount, tank));
    }

    default T produceInfusion(InfuseType infusion, long amount) {
        return produceInfusion(infusion, amount, "");
    }

    default T produceInfusion(InfuseType infusion, long amount, String tank) {
        return addRequirement(new InfusionRequirement(RequirementIOMode.OUTPUT, infusion, amount, tank));
    }

    default T produceInfusionPerTick(InfuseType infusion, long amount) {
        return produceInfusionPerTick(infusion, amount, "");
    }

    default T produceInfusionPerTick(InfuseType infusion, long amount, String tank) {
        return addRequirement(new InfusionPerTickRequirement(RequirementIOMode.OUTPUT, infusion, amount, tank));
    }
}
