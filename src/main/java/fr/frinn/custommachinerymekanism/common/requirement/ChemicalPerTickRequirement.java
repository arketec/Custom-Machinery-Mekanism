package fr.frinn.custommachinerymekanism.common.requirement;

import fr.frinn.custommachinery.api.crafting.CraftingResult;
import fr.frinn.custommachinery.api.crafting.ICraftingContext;
import fr.frinn.custommachinery.api.requirement.ITickableRequirement;
import fr.frinn.custommachinery.api.requirement.RequirementIOMode;
import fr.frinn.custommachinerymekanism.common.component.handler.ChemicalComponentHandler;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import net.minecraft.network.chat.Component;

public abstract class ChemicalPerTickRequirement<C extends Chemical<C>, S extends ChemicalStack<C>, T extends ChemicalComponentHandler<C, S, ?, ?>> extends ChemicalRequirement<C, S, T> implements ITickableRequirement<T> {

    public ChemicalPerTickRequirement(RequirementIOMode mode, C chemical, long amount, String tank) {
        super(mode, chemical, amount, tank);
    }

    @Override
    public CraftingResult processStart(T handler, ICraftingContext context) {
        return CraftingResult.pass();
    }

    @Override
    public CraftingResult processEnd(T handler, ICraftingContext context) {
        return CraftingResult.pass();
    }

    @Override
    public CraftingResult processTick(T handler, ICraftingContext context) {
        if(this.getMode() == RequirementIOMode.INPUT) {
            long amount = (long)context.getModifiedValue(this.amount, this, null);
            if(!test(handler, context))
                return CraftingResult.error(Component.translatable("custommachinerymekanism.requirements.chemical.error.input", Component.translatable(this.chemical.getTranslationKey()), amount));

            handler.removeFromInputs(this.tank, this.chemical, amount);
            return CraftingResult.success();
        } else {
            long amount = (long)context.getModifiedValue(this.amount, this, null);
            if(!test(handler, context))
                return CraftingResult.error(Component.translatable("custommachinerymekanism.requirements.chemical.error.output", amount, Component.translatable(this.chemical.getTranslationKey())));

            handler.addToOutputs(this.tank, this.chemical, amount);
            return CraftingResult.success();
        }
    }
}
