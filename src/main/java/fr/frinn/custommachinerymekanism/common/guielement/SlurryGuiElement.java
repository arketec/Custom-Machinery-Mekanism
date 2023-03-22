package fr.frinn.custommachinerymekanism.common.guielement;

import fr.frinn.custommachinery.api.codec.NamedCodec;
import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.api.guielement.GuiElementType;
import fr.frinn.custommachinerymekanism.Registration;
import fr.frinn.custommachinerymekanism.common.component.SlurryMachineComponent;
import net.minecraft.resources.ResourceLocation;

public class SlurryGuiElement extends ChemicalGuiElement<SlurryMachineComponent> {

    public static final NamedCodec<SlurryGuiElement> CODEC = makeCodec(SlurryGuiElement::new, "Slurry gui element");

    public SlurryGuiElement(int x, int y, int width, int height, int priority, ResourceLocation texture, String id) {
        super(x, y, width, height, priority, texture, id);
    }

    @Override
    public MachineComponentType<SlurryMachineComponent> getComponentType() {
        return Registration.SLURRY_MACHINE_COMPONENT.get();
    }

    @Override
    public GuiElementType<SlurryGuiElement> getType() {
        return Registration.SLURRY_GUI_ELEMENT.get();
    }
}
