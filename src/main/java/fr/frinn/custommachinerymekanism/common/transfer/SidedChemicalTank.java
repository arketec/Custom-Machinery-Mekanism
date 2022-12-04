package fr.frinn.custommachinerymekanism.common.transfer;

import fr.frinn.custommachinery.impl.component.config.SideMode;
import fr.frinn.custommachinerymekanism.common.component.ChemicalMachineComponent;
import fr.frinn.custommachinerymekanism.common.component.handler.ChemicalComponentHandler;
import mekanism.api.Action;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalHandler;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

public abstract class SidedChemicalTank<C extends Chemical<C>, S extends ChemicalStack<C>, T extends ChemicalMachineComponent<C, S>, H extends ChemicalComponentHandler<C, S, ?, T>> implements IChemicalHandler<C, S> {

    private final H handler;
    @Nullable
    private final Direction side;

    public SidedChemicalTank(H handler, @Nullable Direction side) {
        this.handler = handler;
        this.side = side;
    }
    @Override
    public int getTanks() {
        return this.handler.getComponents().size();
    }

    @Override
    public S getChemicalInTank(int i) {
        return this.handler.getComponents().get(i).getStack();
    }

    @Override
    public void setChemicalInTank(int i, S stack) {
        this.handler.getComponents().get(i).setStack(stack);
    }

    @Override
    public long getTankCapacity(int i) {
        return this.handler.getComponents().get(i).getCapacity();
    }

    @Override
    public boolean isValid(int i, S stack) {
        return this.handler.getComponents().get(i).isValid(stack);
    }

    @Override
    public S insertChemical(int i, S stack, Action action) {
        T component = this.handler.getComponents().get(i);
        if(!getMode(component).isInput())
            return stack;
        return component.insert(stack, action, false);
    }

    @Override
    public S extractChemical(int i, long amount, Action action) {
        T component = this.handler.getComponents().get(i);
        if(!getMode(component).isOutput())
            return component.empty();
        return component.extract(amount, action, false);
    }

    private SideMode getMode(T component) {
        if(this.side == null)
            return SideMode.BOTH;
        return component.getConfig().getSideMode(this.side);
    }
}
