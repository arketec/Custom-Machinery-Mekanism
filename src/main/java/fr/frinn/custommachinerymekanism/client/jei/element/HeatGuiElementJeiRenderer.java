package fr.frinn.custommachinerymekanism.client.jei.element;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.frinn.custommachinery.api.crafting.IMachineRecipe;
import fr.frinn.custommachinery.api.integration.jei.IJEIElementRenderer;
import fr.frinn.custommachinerymekanism.common.guielement.HeatGuiElement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;

public class HeatGuiElementJeiRenderer implements IJEIElementRenderer<HeatGuiElement> {
    @Override
    public void renderElementInJEI(GuiGraphics guiGraphics, HeatGuiElement element, IMachineRecipe recipe, int mouseX, int mouseY) {
        int posX = element.getX() - 1;
        int posY = element.getY() - 1;
        int width = element.getWidth();
        int height = element.getHeight();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, element.getTexture());
        guiGraphics.blit(element.getTexture(), posX, posY, 0, 0, width, height, width, height);
    }
}
