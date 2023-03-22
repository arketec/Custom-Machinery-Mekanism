package fr.frinn.custommachinerymekanism.common.guielement;

import fr.frinn.custommachinery.api.ICustomMachineryAPI;
import fr.frinn.custommachinery.api.codec.NamedCodec;
import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.api.guielement.GuiElementType;
import fr.frinn.custommachinery.api.guielement.IComponentGuiElement;
import fr.frinn.custommachinery.impl.guielement.AbstractTexturedGuiElement;
import fr.frinn.custommachinerymekanism.Registration;
import fr.frinn.custommachinerymekanism.common.component.HeatMachineComponent;
import net.minecraft.resources.ResourceLocation;

public class HeatGuiElement extends AbstractTexturedGuiElement implements IComponentGuiElement<HeatMachineComponent> {

    private static final ResourceLocation BASE_TEXTURE = ICustomMachineryAPI.INSTANCE.rl("textures/gui/base_fluid_storage.png");

    public static final NamedCodec<HeatGuiElement> CODEC = NamedCodec.record(instance ->
            makeBaseTexturedCodec(instance, BASE_TEXTURE).apply(instance, HeatGuiElement::new), "Heat gui element"
    );


    public HeatGuiElement(int x, int y, int width, int height, int priority, ResourceLocation texture) {
        super(x, y, width, height, priority, texture);
    }

    @Override
    public GuiElementType<HeatGuiElement> getType() {
        return Registration.HEAT_GUI_ELEMENT.get();
    }

    @Override
    public MachineComponentType<HeatMachineComponent> getComponentType() {
        return Registration.HEAT_MACHINE_COMPONENT.get();
    }

    @Override
    public String getID() {
        return "Heat";
    }
}
