package fr.frinn.custommachinerymekanism.common.transfer;

import fr.frinn.custommachinerymekanism.common.component.PigmentMachineComponent;
import fr.frinn.custommachinerymekanism.common.component.handler.PigmentComponentHandler;
import mekanism.api.chemical.pigment.IPigmentHandler;
import mekanism.api.chemical.pigment.Pigment;
import mekanism.api.chemical.pigment.PigmentStack;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

public class SidedPigmentTank extends SidedChemicalTank<Pigment, PigmentStack, PigmentMachineComponent, PigmentComponentHandler> implements IPigmentHandler {

    public SidedPigmentTank(PigmentComponentHandler handler, @Nullable Direction side) {
        super(handler, side);
    }
}
