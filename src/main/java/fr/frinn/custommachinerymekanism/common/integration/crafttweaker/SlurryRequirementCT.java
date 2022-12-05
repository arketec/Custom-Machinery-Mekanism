package fr.frinn.custommachinerymekanism.common.integration.crafttweaker;

import fr.frinn.custommachinery.api.integration.crafttweaker.RecipeCTBuilder;
import fr.frinn.custommachinery.api.requirement.RequirementIOMode;
import fr.frinn.custommachinerymekanism.common.requirement.SlurryPerTickRequirement;
import fr.frinn.custommachinerymekanism.common.requirement.SlurryRequirement;
import mekanism.api.chemical.slurry.Slurry;

public interface SlurryRequirementCT<T> extends RecipeCTBuilder<T> {

    default T requireSlurry(Slurry slurry, long amount) {
        return requireSlurry(slurry, amount, "");
    }

    default T requireSlurry(Slurry slurry, long amount, String tank) {
        return addRequirement(new SlurryRequirement(RequirementIOMode.INPUT, slurry, amount, tank));
    }

    default T requireSlurryPerTick(Slurry slurry, long amount) {
        return requireSlurryPerTick(slurry, amount, "");
    }

    default T requireSlurryPerTick(Slurry slurry, long amount, String tank) {
        return addRequirement(new SlurryPerTickRequirement(RequirementIOMode.INPUT, slurry, amount, tank));
    }

    default T produceSlurry(Slurry slurry, long amount) {
        return produceSlurry(slurry, amount, "");
    }

    default T produceSlurry(Slurry slurry, long amount, String tank) {
        return addRequirement(new SlurryRequirement(RequirementIOMode.OUTPUT, slurry, amount, tank));
    }

    default T produceSlurryPerTick(Slurry slurry, long amount) {
        return produceSlurryPerTick(slurry, amount, "");
    }

    default T produceSlurryPerTick(Slurry slurry, long amount, String tank) {
        return addRequirement(new SlurryPerTickRequirement(RequirementIOMode.OUTPUT, slurry, amount, tank));
    }
}
