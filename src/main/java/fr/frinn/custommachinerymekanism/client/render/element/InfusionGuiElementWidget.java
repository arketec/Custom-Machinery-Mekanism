package fr.frinn.custommachinerymekanism.client.render.element;

import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.api.guielement.IMachineScreen;
import fr.frinn.custommachinerymekanism.Registration;
import fr.frinn.custommachinerymekanism.common.component.InfusionMachineComponent;
import fr.frinn.custommachinerymekanism.common.guielement.InfusionGuiElement;
import net.minecraft.network.chat.Component;

public class InfusionGuiElementWidget extends ChemicalGuiElementWidget<InfusionMachineComponent, InfusionGuiElement> {

    public InfusionGuiElementWidget(InfusionGuiElement element, IMachineScreen screen) {
        super(element, screen, Component.literal("Infusion"));
    }

    @Override
    public MachineComponentType<InfusionMachineComponent> componentType() {
        return Registration.INFUSION_MACHINE_COMPONENT.get();
    }
}
