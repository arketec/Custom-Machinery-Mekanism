package fr.frinn.custommachinerymekanism.common.transfer;

import fr.frinn.custommachinerymekanism.common.component.GasMachineComponent;
import fr.frinn.custommachinerymekanism.common.component.handler.GasComponentHandler;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasHandler;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

public class SidedGasTank extends SidedChemicalTank<Gas, GasStack, GasMachineComponent, GasComponentHandler> implements IGasHandler {

    public SidedGasTank(GasComponentHandler handler, @Nullable Direction side) {
        super(handler, side);
    }
}
