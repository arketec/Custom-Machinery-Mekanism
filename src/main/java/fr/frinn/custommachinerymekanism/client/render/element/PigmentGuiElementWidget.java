package fr.frinn.custommachinerymekanism.client.render.element;

import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.api.guielement.IMachineScreen;
import fr.frinn.custommachinerymekanism.Registration;
import fr.frinn.custommachinerymekanism.common.component.PigmentMachineComponent;
import fr.frinn.custommachinerymekanism.common.guielement.PigmentGuiElement;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class PigmentGuiElementWidget extends ChemicalGuiElementWidget<PigmentMachineComponent, PigmentGuiElement> {

    public PigmentGuiElementWidget(PigmentGuiElement element, IMachineScreen screen) {
        super(element, screen, Component.literal("Pigment"));
    }

    @Override
    public MachineComponentType<PigmentMachineComponent> componentType() {
        return Registration.PIGMENT_MACHINE_COMPONENT.get();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput arg) {
        
    }
}
