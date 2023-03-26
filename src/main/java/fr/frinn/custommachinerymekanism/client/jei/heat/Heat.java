package fr.frinn.custommachinerymekanism.client.jei.heat;

import fr.frinn.custommachinery.api.requirement.RequirementIOMode;

public record Heat(double amount, double chance, boolean isPerTick, RequirementIOMode mode) {
}
