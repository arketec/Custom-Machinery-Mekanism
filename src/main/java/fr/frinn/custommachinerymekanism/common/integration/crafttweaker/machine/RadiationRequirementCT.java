package fr.frinn.custommachinerymekanism.common.integration.crafttweaker.machine;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import fr.frinn.custommachinery.api.requirement.RequirementIOMode;
import fr.frinn.custommachinery.common.integration.crafttweaker.CTConstants;
import fr.frinn.custommachinery.common.integration.crafttweaker.CustomMachineRecipeCTBuilder;
import fr.frinn.custommachinerymekanism.common.requirement.RadiationPerTickRequirement;
import fr.frinn.custommachinerymekanism.common.requirement.RadiationRequirement;
import mekanism.common.config.MekanismConfig;
import org.openzen.zencode.java.ZenCodeType.Expansion;
import org.openzen.zencode.java.ZenCodeType.Method;

@ZenRegister
@Expansion(CTConstants.RECIPE_BUILDER_MACHINE)
public class RadiationRequirementCT {

    @Method
    public static CustomMachineRecipeCTBuilder requireRadiation(CustomMachineRecipeCTBuilder builder, double amount) {
        return requireRadiation(builder, amount, MekanismConfig.general.radiationChunkCheckRadius.get() * 16);
    }

    @Method
    public static CustomMachineRecipeCTBuilder requireRadiation(CustomMachineRecipeCTBuilder builder, double amount, int radius) {
        return builder.addRequirement(new RadiationRequirement(RequirementIOMode.INPUT, amount, radius));
    }

    @Method
    public static CustomMachineRecipeCTBuilder requireRadiationPerTick(CustomMachineRecipeCTBuilder builder, double amount) {
        return requireRadiationPerTick(builder, amount, MekanismConfig.general.radiationChunkCheckRadius.get() * 16);
    }

    @Method
    public static CustomMachineRecipeCTBuilder requireRadiationPerTick(CustomMachineRecipeCTBuilder builder, double amount, int radius) {
        return builder.addRequirement(new RadiationPerTickRequirement(RequirementIOMode.INPUT, amount, radius));
    }

    @Method
    public static CustomMachineRecipeCTBuilder emitRadiation(CustomMachineRecipeCTBuilder builder, double amount) {
        return builder.addRequirement(new RadiationRequirement(RequirementIOMode.OUTPUT, amount, 1));
    }

    @Method
    public static CustomMachineRecipeCTBuilder emitRadiationPerTick(CustomMachineRecipeCTBuilder builder, double amount) {
        return builder.addRequirement(new RadiationPerTickRequirement(RequirementIOMode.OUTPUT, amount, 1));
    }
}