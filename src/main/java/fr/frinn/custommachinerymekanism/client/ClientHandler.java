package fr.frinn.custommachinerymekanism.client;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.frinn.custommachinery.api.guielement.RegisterGuiElementWidgetSupplierEvent;
import fr.frinn.custommachinery.api.integration.jei.RegisterGuiElementJEIRendererEvent;
import fr.frinn.custommachinerymekanism.Registration;
import fr.frinn.custommachinerymekanism.client.jei.element.ChemicalGuiElementJeiRenderer;
import fr.frinn.custommachinerymekanism.client.render.element.GasGuiElementWidget;
import fr.frinn.custommachinerymekanism.client.render.element.HeatGuiElementWidget;
import fr.frinn.custommachinerymekanism.client.render.element.InfusionGuiElementWidget;
import fr.frinn.custommachinerymekanism.client.render.element.PigmentGuiElementWidget;
import fr.frinn.custommachinerymekanism.client.render.element.SlurryGuiElementWidget;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;

public class ClientHandler {

    public static void clientInit() {
        RegisterGuiElementWidgetSupplierEvent.EVENT.register(ClientHandler::registerGuiElementWidgets);

        if(ModList.get().isLoaded("jei"))
            RegisterGuiElementJEIRendererEvent.EVENT.register(ClientHandler::registerGuiElementJeiRenderers);
    }

    private static void registerGuiElementWidgets(RegisterGuiElementWidgetSupplierEvent event) {
        event.register(Registration.GAS_GUI_ELEMENT.get(), GasGuiElementWidget::new);
        event.register(Registration.INFUSION_GUI_ELEMENT.get(), InfusionGuiElementWidget::new);
        event.register(Registration.PIGMENT_GUI_ELEMENT.get(), PigmentGuiElementWidget::new);
        event.register(Registration.SLURRY_GUI_ELEMENT.get(), SlurryGuiElementWidget::new);
        event.register(Registration.HEAT_GUI_ELEMENT.get(), HeatGuiElementWidget::new);
    }

    private static void registerGuiElementJeiRenderers(RegisterGuiElementJEIRendererEvent event) {
        event.register(Registration.GAS_GUI_ELEMENT.get(), new ChemicalGuiElementJeiRenderer<>());
        event.register(Registration.INFUSION_GUI_ELEMENT.get(), new ChemicalGuiElementJeiRenderer<>());
        event.register(Registration.PIGMENT_GUI_ELEMENT.get(), new ChemicalGuiElementJeiRenderer<>());
        event.register(Registration.SLURRY_GUI_ELEMENT.get(), new ChemicalGuiElementJeiRenderer<>());
    }

    public static void bindTexture(ResourceLocation texture) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, texture);
    }
}
