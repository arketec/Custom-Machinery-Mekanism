package fr.frinn.custommachinerymekanism.common.guielement;

import fr.frinn.custommachinery.api.codec.NamedCodec;
import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.api.guielement.GuiElementType;
import fr.frinn.custommachinerymekanism.Registration;
import fr.frinn.custommachinerymekanism.common.component.PigmentMachineComponent;
import net.minecraft.resources.ResourceLocation;

public class PigmentGuiElement extends ChemicalGuiElement<PigmentMachineComponent> {

    public static final NamedCodec<PigmentGuiElement> CODEC = makeCodec(PigmentGuiElement::new, "Pigment gui element");

    public PigmentGuiElement(int x, int y, int width, int height, int priority, ResourceLocation texture, String id, boolean highlight) {
        super(x, y, width, height, priority, texture, id, highlight);
    }

    @Override
    public MachineComponentType<PigmentMachineComponent> getComponentType() {
        return Registration.PIGMENT_MACHINE_COMPONENT.get();
    }

    @Override
    public GuiElementType<PigmentGuiElement> getType() {
        return Registration.PIGMENT_GUI_ELEMENT.get();
    }
}
