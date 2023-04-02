package fr.frinn.custommachinerymekanism.common.requirement;

import fr.frinn.custommachinery.api.codec.NamedCodec;
import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.api.crafting.CraftingResult;
import fr.frinn.custommachinery.api.crafting.ICraftingContext;
import fr.frinn.custommachinery.api.integration.jei.IDisplayInfo;
import fr.frinn.custommachinery.api.integration.jei.IDisplayInfoRequirement;
import fr.frinn.custommachinery.api.requirement.ITickableRequirement;
import fr.frinn.custommachinery.api.requirement.RequirementIOMode;
import fr.frinn.custommachinery.api.requirement.RequirementType;
import fr.frinn.custommachinery.impl.requirement.AbstractChanceableRequirement;
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

public class RadiationPerTickRequirement extends AbstractChanceableRequirement<RadiationMachineComponent> implements ITickableRequirement<RadiationMachineComponent>, IDisplayInfoRequirement {

    public static final NamedCodec<RadiationPerTickRequirement> CODEC = NamedCodec.record(radiationRequirementInstance ->
            radiationRequirementInstance.group(
                    RequirementIOMode.CODEC.fieldOf("mode").forGetter(RadiationPerTickRequirement::getMode),
                    NamedCodec.doubleRange(0.0D, Double.MAX_VALUE).fieldOf("amount").forGetter(requirement -> requirement.amount),
                    NamedCodec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("range", MekanismConfig.general.radiationChunkCheckRadius.get() * 16).forGetter(requirement -> requirement.radius),
                    NamedCodec.doubleRange(0.0D, 1.0D).optionalFieldOf("chance", 1.0D).forGetter(RadiationPerTickRequirement::getChance)
            ).apply(radiationRequirementInstance, (mode, amount, radius, chance) -> {
                RadiationPerTickRequirement requirement = new RadiationPerTickRequirement(mode, amount, radius);
                requirement.setChance(chance);
                return requirement;
            }), "Radiation per tick requirement"
    );

    private final double amount;
    private final int radius;

    public RadiationPerTickRequirement(RequirementIOMode mode, double amount, int radius) {
        super(mode);
        this.amount = amount;
        this.radius = radius;
    }

    @Override
    public RequirementType<RadiationPerTickRequirement> getType() {
        return Registration.RADIATION_PER_TICK.get();
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
        return CraftingResult.pass();
    }

    @Override
    public CraftingResult processEnd(RadiationMachineComponent component, ICraftingContext context) {
        return CraftingResult.pass();
    }

    @Override
    public CraftingResult processTick(RadiationMachineComponent component, ICraftingContext context) {
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
            info.addTooltip(new TranslatableComponent("custommachinerymekanism.requirements.radiation.info.tick.input", sievert(this.amount), this.radius));
        }
        else {
            info.setItemIcon(MekanismItems.GEIGER_COUNTER.asItem());
            info.addTooltip(new TranslatableComponent("custommachinerymekanism.requirements.radiation.info.tick.output", sievert(this.amount)));
        }
    }

    private static Component sievert(double amount) {
        return new TextComponent("")
                .append(UnitDisplayUtils.getDisplayShort(amount, RadiationUnit.SV, 3))
                .withStyle(RadiationScale.getSeverityColor(amount).getColoredName().getStyle());
    }
}
