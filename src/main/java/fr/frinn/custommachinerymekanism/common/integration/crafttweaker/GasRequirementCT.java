package fr.frinn.custommachinerymekanism.common.integration.crafttweaker;

import fr.frinn.custommachinery.api.integration.crafttweaker.RecipeCTBuilder;
import fr.frinn.custommachinery.api.requirement.RequirementIOMode;
import fr.frinn.custommachinerymekanism.common.requirement.GasPerTickRequirement;
import fr.frinn.custommachinerymekanism.common.requirement.GasRequirement;
import mekanism.api.chemical.gas.Gas;

public interface GasRequirementCT<T> extends RecipeCTBuilder<T> {

    default T requireGas(Gas gas, long amount) {
        return requireGas(gas, amount, "");
    }

    default T requireGas(Gas gas, long amount, String tank) {
        return addRequirement(new GasRequirement(RequirementIOMode.INPUT, gas, amount, tank));
    }

    default T requireGasPerTick(Gas gas, long amount) {
        return requireGasPerTick(gas, amount, "");
    }

    default T requireGasPerTick(Gas gas, long amount, String tank) {
        return addRequirement(new GasPerTickRequirement(RequirementIOMode.INPUT, gas, amount, tank));
    }

    default T produceGas(Gas gas, long amount) {
        return produceGas(gas, amount, "");
    }

    default T produceGas(Gas gas, long amount, String tank) {
        return addRequirement(new GasRequirement(RequirementIOMode.OUTPUT, gas, amount, tank));
    }

    default T produceGasPerTick(Gas gas, long amount) {
        return produceGasPerTick(gas, amount, "");
    }

    default T produceGasPerTick(Gas gas, long amount, String tank) {
        return addRequirement(new GasPerTickRequirement(RequirementIOMode.OUTPUT, gas, amount, tank));
    }
}
