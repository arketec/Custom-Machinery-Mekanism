package fr.frinn.custommachinerymekanism.common.integration.kubejs;

import fr.frinn.custommachinery.api.integration.kubejs.RecipeJSBuilder;
import fr.frinn.custommachinery.api.requirement.RequirementIOMode;
import fr.frinn.custommachinerymekanism.common.requirement.GasPerTickRequirement;
import fr.frinn.custommachinerymekanism.common.requirement.GasRequirement;
import mekanism.api.chemical.gas.Gas;

public interface GasRequirementJS extends RecipeJSBuilder {

    default RecipeJSBuilder requireGas(Gas gas, long amount) {
        return requireGas(gas, amount, "");
    }

    default RecipeJSBuilder requireGas(Gas gas, long amount, String tank) {
        return addRequirement(new GasRequirement(RequirementIOMode.INPUT, gas, amount, tank));
    }

    default RecipeJSBuilder requireGasPerTick(Gas gas, long amount) {
        return requireGasPerTick(gas, amount, "");
    }

    default RecipeJSBuilder requireGasPerTick(Gas gas, long amount, String tank) {
        return addRequirement(new GasPerTickRequirement(RequirementIOMode.INPUT, gas, amount, tank));
    }

    default RecipeJSBuilder produceGas(Gas gas, long amount) {
        return produceGas(gas, amount, "");
    }

    default RecipeJSBuilder produceGas(Gas gas, long amount, String tank) {
        return addRequirement(new GasRequirement(RequirementIOMode.OUTPUT, gas, amount, tank));
    }

    default RecipeJSBuilder produceGasPerTick(Gas gas, long amount) {
        return produceGasPerTick(gas, amount, "");
    }

    default RecipeJSBuilder produceGasPerTick(Gas gas, long amount, String tank) {
        return addRequirement(new GasPerTickRequirement(RequirementIOMode.OUTPUT, gas, amount, tank));
    }
}
