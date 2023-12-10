package fr.frinn.custommachinerymekanism.client.render.element;

import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.api.guielement.IMachineScreen;
import fr.frinn.custommachinerymekanism.Registration;
import fr.frinn.custommachinerymekanism.common.component.GasMachineComponent;
import fr.frinn.custommachinerymekanism.common.guielement.GasGuiElement;
import net.minecraft.network.chat.Component;

public class GasGuiElementWidget extends ChemicalGuiElementWidget<GasMachineComponent, GasGuiElement> {


    public GasGuiElementWidget(GasGuiElement element, IMachineScreen screen) {
        super(element, screen, Component.literal("Gas"));
    }

    @Override
    public MachineComponentType<GasMachineComponent> componentType() {
        return Registration.GAS_MACHINE_COMPONENT.get();
    }
}
