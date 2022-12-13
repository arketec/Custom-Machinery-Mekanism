package fr.frinn.custommachinerymekanism.common.mixin;

import fr.frinn.custommachinery.api.integration.kubejs.RecipeJSBuilder;
import fr.frinn.custommachinerymekanism.common.integration.kubejs.GasRequirementJS;
import fr.frinn.custommachinerymekanism.common.integration.kubejs.InfusionRequirementJS;
import fr.frinn.custommachinerymekanism.common.integration.kubejs.PigmentRequirementJS;
import fr.frinn.custommachinerymekanism.common.integration.kubejs.SlurryRequirementJS;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = {"fr.frinn.custommachinery.common.integration.kubejs.CustomMachineRecipeBuilderJS", "fr.frinn.custommachinery.common.integration.kubejs.CustomCraftRecipeJSBuilder"})
public abstract class KubeJSIntegrationMixin implements RecipeJSBuilder,
        GasRequirementJS, InfusionRequirementJS, PigmentRequirementJS, SlurryRequirementJS {

}
