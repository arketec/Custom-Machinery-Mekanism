package fr.frinn.custommachinerymekanism.common.guielement;

import com.mojang.serialization.Codec;
import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.api.guielement.GuiElementType;
import fr.frinn.custommachinerymekanism.Registration;
import fr.frinn.custommachinerymekanism.common.component.PigmentMachineComponent;
import net.minecraft.resources.ResourceLocation;

public class PigmentGuiElement extends ChemicalGuiElement<PigmentMachineComponent> {

    public static final Codec<PigmentGuiElement> CODEC = makeCodec(PigmentGuiElement::new);

    public PigmentGuiElement(int x, int y, int width, int height, int priority, ResourceLocation texture, String id) {
        super(x, y, width, height, priority, texture, id);
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
