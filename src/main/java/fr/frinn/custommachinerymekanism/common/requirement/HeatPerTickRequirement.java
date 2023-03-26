package fr.frinn.custommachinerymekanism.common.requirement;

import fr.frinn.custommachinery.api.codec.NamedCodec;
import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.api.crafting.CraftingResult;
import fr.frinn.custommachinery.api.crafting.ICraftingContext;
import fr.frinn.custommachinery.api.crafting.IMachineRecipe;
import fr.frinn.custommachinery.api.integration.jei.IJEIIngredientRequirement;
import fr.frinn.custommachinery.api.integration.jei.IJEIIngredientWrapper;
import fr.frinn.custommachinery.api.requirement.ITickableRequirement;
import fr.frinn.custommachinery.api.requirement.RequirementIOMode;
import fr.frinn.custommachinery.api.requirement.RequirementType;
import fr.frinn.custommachinery.impl.requirement.AbstractChanceableRequirement;
import fr.frinn.custommachinerymekanism.Registration;
import fr.frinn.custommachinerymekanism.client.jei.heat.Heat;
import fr.frinn.custommachinerymekanism.client.jei.wrapper.HeatIngredientWrapper;
import fr.frinn.custommachinerymekanism.common.component.HeatMachineComponent;
import mekanism.api.heat.IHeatCapacitor;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.Collections;
import java.util.List;

public class HeatPerTickRequirement extends AbstractChanceableRequirement<HeatMachineComponent> implements ITickableRequirement<HeatMachineComponent>, IJEIIngredientRequirement<Heat> {

    public static final NamedCodec<HeatPerTickRequirement> CODEC = NamedCodec.record(heatRequirementInstance ->
            heatRequirementInstance.group(
                    RequirementIOMode.CODEC.fieldOf("mode").forGetter(HeatPerTickRequirement::getMode),
                    NamedCodec.doubleRange(0.0D, Double.MAX_VALUE).fieldOf("amount").forGetter(requirement -> requirement.amount),
                    NamedCodec.doubleRange(0.0D, 1.0D).optionalFieldOf("chance", 1.0D).forGetter(HeatPerTickRequirement::getChance)
            ).apply(heatRequirementInstance, (mode, amount, chance) -> {
                HeatPerTickRequirement requirement = new HeatPerTickRequirement(mode, amount);
                requirement.setChance(chance);
                return requirement;
            }), "Heat per tick requirement"
    );

    private final double amount;

    public HeatPerTickRequirement(RequirementIOMode mode, double amount) {
        super(mode);
        this.amount = amount;
    }

    @Override
    public RequirementType<HeatPerTickRequirement> getType() {
        return Registration.HEAT_PER_TICK_REQUIREMENT.get();
    }

    @Override
    public MachineComponentType<HeatMachineComponent> getComponentType() {
        return Registration.HEAT_MACHINE_COMPONENT.get();
    }

    @Override
    public boolean test(HeatMachineComponent component, ICraftingContext context) {
        double amount = context.getModifiedValue(this.amount, this, null);
        if(getMode() == RequirementIOMode.INPUT)
            return component.getHeatCapacitors(null).get(0).getHeat() >= amount;
        return true;
    }

    @Override
    public CraftingResult processStart(HeatMachineComponent component, ICraftingContext context) {
        return CraftingResult.pass();
    }

    @Override
    public CraftingResult processEnd(HeatMachineComponent component, ICraftingContext context) {
        return CraftingResult.pass();
    }

    @Override
    public CraftingResult processTick(HeatMachineComponent component, ICraftingContext context) {
        double amount = context.getModifiedValue(this.amount, this, null);
        IHeatCapacitor capacitor = component.getHeatCapacitors(null).get(0);
        if(getMode() == RequirementIOMode.INPUT) {
            if(capacitor.getHeat() < amount)
                return CraftingResult.error(new TranslatableComponent("custommachinerymekanism.requirements.heat.error.input", amount, capacitor.getHeat()));
            capacitor.handleHeat(-amount);
        }
        else
            component.getHeatCapacitors(null).get(0).handleHeat(amount);

        return CraftingResult.success();
    }

    @Override
    public List<IJEIIngredientWrapper<Heat>> getJEIIngredientWrappers(IMachineRecipe recipe) {
        return Collections.singletonList(new HeatIngredientWrapper(getMode(), this.amount, getChance(), true));
    }
}
