package fr.frinn.custommachinerymekanism.common.transfer;

import fr.frinn.custommachinerymekanism.common.component.InfusionMachineComponent;
import fr.frinn.custommachinerymekanism.common.component.handler.InfusionComponentHandler;
import mekanism.api.chemical.infuse.IInfusionHandler;
import mekanism.api.chemical.infuse.InfuseType;
import mekanism.api.chemical.infuse.InfusionStack;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

public class SidedInfusionTank extends SidedChemicalTank<InfuseType, InfusionStack, InfusionMachineComponent, InfusionComponentHandler> implements IInfusionHandler {

    public SidedInfusionTank(InfusionComponentHandler handler, @Nullable Direction side) {
        super(handler, side);
    }
}
