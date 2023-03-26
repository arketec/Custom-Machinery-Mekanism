package fr.frinn.custommachinerymekanism.client.jei;

import fr.frinn.custommachinerymekanism.CustomMachineryMekanism;
import fr.frinn.custommachinerymekanism.client.jei.heat.Heat;
import fr.frinn.custommachinerymekanism.client.jei.heat.HeatIngredientHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.registration.IModIngredientRegistration;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;

@JeiPlugin
public class CMMJeiPlugin implements IModPlugin {

    public static final ResourceLocation PLUGIN_ID = new ResourceLocation(CustomMachineryMekanism.MODID, "jei_plugin");
    public static final IIngredientType<Heat> HEAT_INGREDIENT = () -> Heat.class;

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registration) {
        registration.register(HEAT_INGREDIENT, Collections.emptyList(), new HeatIngredientHelper(), (ingredient, tooltipFlag) -> Collections.emptyList());
    }
}
