package fr.frinn.custommachinerymekanism.common.mixin;

import fr.frinn.custommachinerymekanism.common.integration.crafttweaker.GasRequirementCT;
import fr.frinn.custommachinerymekanism.common.integration.crafttweaker.InfusionRequirementCT;
import fr.frinn.custommachinerymekanism.common.integration.crafttweaker.PigmentRequirementCT;
import fr.frinn.custommachinerymekanism.common.integration.crafttweaker.SlurryRequirementCT;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@Mixin(targets = {"fr.frinn.custommachinery.common.integration.crafttweaker.CustomMachineRecipeCTBuilder", "fr.frinn.custommachinery.common.integration.crafttweaker.CustomCraftRecipeCTBuilder"})
public abstract class CraftTweakerIntegrationMixin<T> implements
        GasRequirementCT<T>, InfusionRequirementCT<T>, PigmentRequirementCT<T>, SlurryRequirementCT<T> {
}
