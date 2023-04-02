package fr.frinn.custommachinerymekanism.common.requirement;

import fr.frinn.custommachinery.api.codec.NamedCodec;
import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.api.crafting.CraftingResult;
import fr.frinn.custommachinery.api.crafting.ICraftingContext;
import fr.frinn.custommachinery.api.integration.jei.IDisplayInfo;
import fr.frinn.custommachinery.api.integration.jei.IDisplayInfoRequirement;
import fr.frinn.custommachinery.api.requirement.RequirementIOMode;
import fr.frinn.custommachinery.api.requirement.RequirementType;
import fr.frinn.custommachinery.impl.requirement.AbstractDelayedChanceableRequirement;
import fr.frinn.custommachinerymekanism.Registration;
import fr.frinn.custommachinerymekanism.common.component.RadiationMachineComponent;
import mekanism.common.config.MekanismConfig;
import mekanism.common.lib.radiation.RadiationManager.RadiationScale;
import mekanism.common.registries.MekanismItems;
import mekanism.common.util.UnitDisplayUtils;
import mekanism.common.util.UnitDisplayUtils.RadiationUnit;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class RadiationRequirement extends AbstractDelayedChanceableRequirement<RadiationMachineComponent> implements IDisplayInfoRequirement {

    public static final NamedCodec<RadiationRequirement> CODEC = NamedCodec.record(radiationRequirementInstance ->
            radiationRequirementInstance.group(
                    RequirementIOMode.CODEC.fieldOf("mode").forGetter(RadiationRequirement::getMode),
                    NamedCodec.doubleRange(0.0D, Double.MAX_VALUE).fieldOf("amount").forGetter(requirement -> requirement.amount),
                    NamedCodec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("range", MekanismConfig.general.radiationChunkCheckRadius.get() * 16).forGetter(requirement -> requirement.radius),
                    NamedCodec.doubleRange(0.0D, 1.0D).optionalFieldOf("chance", 1.0D).forGetter(RadiationRequirement::getChance),
                    NamedCodec.doubleRange(0.0D, 1.0D).optionalFieldOf("delay", 0.0D).forGetter(RadiationRequirement::getDelay)
            ).apply(radiationRequirementInstance, (mode, amount, radius, chance, delay) -> {
                    RadiationRequirement requirement = new RadiationRequirement(mode, amount, radius);
                    requirement.setChance(chance);
                    requirement.setDelay(delay);
                    return requirement;
            }), "Radiation requirement"
    );

    private final double amount;
    private final int radius;

    public RadiationRequirement(RequirementIOMode mode, double amount, int radius) {
        super(mode);
        this.amount = amount;
        this.radius = radius;
    }

    @Override
    public RequirementType<RadiationRequirement> getType() {
        return Registration.RADIATION_REQUIREMENT.get();
    }

    @Override
    public MachineComponentType<RadiationMachineComponent> getComponentType() {
        return Registration.RADIATION_MACHINE_COMPONENT.get();
    }

    @Override
    public boolean test(RadiationMachineComponent component, ICraftingContext context) {
        if(getMode() == RequirementIOMode.INPUT)
            return component.getRadiations() >= this.amount;
        return true;
    }

    @Override
    public CraftingResult processStart(RadiationMachineComponent component, ICraftingContext context) {
        if(getMode() == RequirementIOMode.OUTPUT || getDelay() != 0.0D)
            return CraftingResult.pass();

        double radiations = component.getRadiations();
        if(radiations < this.amount)
            return CraftingResult.error(new TranslatableComponent("custommachinerymekanism.requirements.radiation.error", sievert(radiations), sievert(this.amount)));
        component.removeRadiations(this.amount, this.radius);
        return CraftingResult.success();
    }

    @Override
    public CraftingResult processEnd(RadiationMachineComponent component, ICraftingContext context) {
        if(getMode() == RequirementIOMode.INPUT || getDelay() != 0.0D)
            return CraftingResult.pass();

        component.addRadiations(this.amount);
        return CraftingResult.success();
    }

    @Override
    public CraftingResult execute(RadiationMachineComponent component, ICraftingContext context) {
        if(getMode() == RequirementIOMode.INPUT) {
            double radiations = component.getRadiations();
            if(radiations < this.amount)
                return CraftingResult.error(new TranslatableComponent("custommachinerymekanism.requirements.radiation.error", sievert(radiations), sievert(this.amount)));
            component.removeRadiations(this.amount, this.radius);
        } else {
            component.addRadiations(this.amount);
        }
        return CraftingResult.success();
    }

    @Override
    public void getDisplayInfo(IDisplayInfo info) {
        if(getMode() == RequirementIOMode.INPUT) {
            info.setItemIcon(MekanismItems.GEIGER_COUNTER.asItem());
            info.addTooltip(new TranslatableComponent("custommachinerymekanism.requirements.radiation.info.input", sievert(this.amount), this.radius));
        }
        else {
            info.setItemIcon(MekanismItems.GEIGER_COUNTER.asItem());
            info.addTooltip(new TranslatableComponent("custommachinerymekanism.requirements.radiation.info.output", sievert(this.amount)));
        }
    }

    private static Component sievert(double amount) {
        return new TextComponent("")
                .append(UnitDisplayUtils.getDisplayShort(amount, RadiationUnit.SV, 3))
                .withStyle(RadiationScale.getSeverityColor(amount).getColoredName().getStyle());
    }
}
