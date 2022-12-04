package fr.frinn.custommachinerymekanism.client.jei.element;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.frinn.custommachinery.api.crafting.IMachineRecipe;
import fr.frinn.custommachinery.api.integration.jei.IJEIElementRenderer;
import fr.frinn.custommachinerymekanism.client.ClientHandler;
import fr.frinn.custommachinerymekanism.common.guielement.ChemicalGuiElement;
import net.minecraft.client.gui.GuiComponent;

public class ChemicalGuiElementJeiRenderer<E extends ChemicalGuiElement<?>> implements IJEIElementRenderer<E> {

    @Override
    public void renderElementInJEI(PoseStack matrix, E element, IMachineRecipe recipe, int mouseX, int mouseY) {
        int posX = element.getX() - 1;
        int posY = element.getY() - 1;
        int width = element.getWidth();
        int height = element.getHeight();
        ClientHandler.bindTexture(element.getTexture());
        GuiComponent.blit(matrix, posX, posY, 0, 0, width, height, width, height);
    }
}
