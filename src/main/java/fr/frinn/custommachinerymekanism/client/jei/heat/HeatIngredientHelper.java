package fr.frinn.custommachinerymekanism.client.jei.heat;

import fr.frinn.custommachinerymekanism.CustomMachineryMekanism;
import fr.frinn.custommachinerymekanism.client.jei.CMMJeiPlugin;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.network.chat.TranslatableComponent;
import org.jetbrains.annotations.Nullable;

public class HeatIngredientHelper implements IIngredientHelper<Heat> {

    @Override
    public IIngredientType<Heat> getIngredientType() {
        return CMMJeiPlugin.HEAT_INGREDIENT;
    }

    @Override
    public String getDisplayName(Heat ingredient) {
        return new TranslatableComponent("custommachinerymekanism.jei.ingredient.heat", ingredient.amount()).getString();
    }

    @Override
    public String getUniqueId(Heat ingredient, UidContext context) {
        return "" + ingredient.amount() + ingredient.chance() + ingredient.isPerTick();
    }

    @SuppressWarnings("removal")
    @Override
    public String getModId(Heat ingredient) {
        return CustomMachineryMekanism.MODID;
    }

    @SuppressWarnings("removal")
    @Override
    public String getResourceId(Heat ingredient) {
        return "heat";
    }

    @Override
    public Heat copyIngredient(Heat ingredient) {
        return new Heat(ingredient.amount(), ingredient.chance(), ingredient.isPerTick(), ingredient.mode());
    }

    @Override
    public String getErrorInfo(@Nullable Heat ingredient) {
        return "";
    }
}
