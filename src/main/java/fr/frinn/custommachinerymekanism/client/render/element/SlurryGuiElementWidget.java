package fr.frinn.custommachinerymekanism.client.render.element;

import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.api.guielement.IMachineScreen;
import fr.frinn.custommachinerymekanism.Registration;
import fr.frinn.custommachinerymekanism.common.component.SlurryMachineComponent;
import fr.frinn.custommachinerymekanism.common.guielement.SlurryGuiElement;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class SlurryGuiElementWidget extends ChemicalGuiElementWidget<SlurryMachineComponent, SlurryGuiElement> {

    public SlurryGuiElementWidget(SlurryGuiElement element, IMachineScreen screen) {
        super(element, screen, Component.literal("Slurry"));
    }

    @Override
    public MachineComponentType<SlurryMachineComponent> componentType() {
        return Registration.SLURRY_MACHINE_COMPONENT.get();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput arg) {
        
    }
}
