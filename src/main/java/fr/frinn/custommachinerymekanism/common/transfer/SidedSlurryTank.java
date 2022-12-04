package fr.frinn.custommachinerymekanism.common.transfer;

import fr.frinn.custommachinerymekanism.common.component.SlurryMachineComponent;
import fr.frinn.custommachinerymekanism.common.component.handler.SlurryComponentHandler;
import mekanism.api.chemical.slurry.ISlurryHandler;
import mekanism.api.chemical.slurry.Slurry;
import mekanism.api.chemical.slurry.SlurryStack;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

public class SidedSlurryTank extends SidedChemicalTank<Slurry, SlurryStack, SlurryMachineComponent, SlurryComponentHandler> implements ISlurryHandler {

    public SidedSlurryTank(SlurryComponentHandler handler, @Nullable Direction side) {
        super(handler, side);
    }
}
