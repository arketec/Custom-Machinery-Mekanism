package fr.frinn.custommachinerymekanism.common.guielement;

import com.mojang.serialization.Codec;
import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.api.guielement.GuiElementType;
import fr.frinn.custommachinerymekanism.Registration;
import fr.frinn.custommachinerymekanism.common.component.GasMachineComponent;
import net.minecraft.resources.ResourceLocation;

public class GasGuiElement extends ChemicalGuiElement<GasMachineComponent> {

    public static final Codec<GasGuiElement> CODEC = makeCodec(GasGuiElement::new);

    public GasGuiElement(int x, int y, int width, int height, int priority, ResourceLocation texture, String id) {
        super(x, y, width, height, priority, texture, id);
    }

    @Override
    public GuiElementType<GasGuiElement> getType() {
        return Registration.GAS_GUI_ELEMENT.get();
    }

    @Override
    public MachineComponentType<GasMachineComponent> getComponentType() {
        return Registration.GAS_MACHINE_COMPONENT.get();
    }
}
