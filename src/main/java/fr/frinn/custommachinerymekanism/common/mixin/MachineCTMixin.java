package fr.frinn.custommachinerymekanism.common.mixin;

import fr.frinn.custommachinery.common.init.CustomMachineTile;
import fr.frinn.custommachinery.common.integration.crafttweaker.function.MachineCT;
import fr.frinn.custommachinerymekanism.Registration;
import fr.frinn.custommachinerymekanism.common.component.GasMachineComponent;
import fr.frinn.custommachinerymekanism.common.component.InfusionMachineComponent;
import fr.frinn.custommachinerymekanism.common.component.PigmentMachineComponent;
import fr.frinn.custommachinerymekanism.common.component.RadiationMachineComponent;
import fr.frinn.custommachinerymekanism.common.component.SlurryMachineComponent;
import fr.frinn.custommachinerymekanism.common.component.handler.GasComponentHandler;
import fr.frinn.custommachinerymekanism.common.component.handler.InfusionComponentHandler;
import fr.frinn.custommachinerymekanism.common.component.handler.PigmentComponentHandler;
import fr.frinn.custommachinerymekanism.common.component.handler.SlurryComponentHandler;
import mekanism.api.Action;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.infuse.InfusionStack;
import mekanism.api.chemical.pigment.PigmentStack;
import mekanism.api.chemical.slurry.SlurryStack;
import mekanism.api.heat.IHeatCapacitor;
import mekanism.common.integration.crafttweaker.chemical.CrTChemicalStack.CrTGasStack;
import mekanism.common.integration.crafttweaker.chemical.CrTChemicalStack.CrTInfusionStack;
import mekanism.common.integration.crafttweaker.chemical.CrTChemicalStack.CrTPigmentStack;
import mekanism.common.integration.crafttweaker.chemical.CrTChemicalStack.CrTSlurryStack;
import mekanism.common.integration.crafttweaker.chemical.ICrTChemicalStack.ICrTGasStack;
import mekanism.common.integration.crafttweaker.chemical.ICrTChemicalStack.ICrTInfusionStack;
import mekanism.common.integration.crafttweaker.chemical.ICrTChemicalStack.ICrTPigmentStack;
import mekanism.common.integration.crafttweaker.chemical.ICrTChemicalStack.ICrTSlurryStack;
import org.openzen.zencode.java.ZenCodeType.Method;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(value = MachineCT.class, remap = false)
public class MachineCTMixin {

    @Final
    @Shadow(remap = false)
    private CustomMachineTile internal;

    /** GAS **/

    @Method
    public ICrTGasStack getGasStored(String tank) {
        return this.internal.getComponentManager().getComponentHandler(Registration.GAS_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(component -> new CrTGasStack(component.getStack().copy()))
                .orElse(new CrTGasStack(GasStack.EMPTY));
    }

    @Method
    public void setGasStored(String tank, ICrTGasStack stack) {
        this.internal.getComponentManager().getComponentHandler(Registration.GAS_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .ifPresent(component -> component.setStack(stack.getInternal()));
    }

    @Method
    public long getGasCapacity(String tank) {
        return this.internal.getComponentManager().getComponentHandler(Registration.GAS_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(GasMachineComponent::getCapacity)
                .orElse(0L);
    }

    @Method
    public ICrTGasStack addGas(ICrTGasStack stack, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.GAS_MACHINE_COMPONENT.get())
                .map(handler -> (ICrTGasStack)new CrTGasStack(((GasComponentHandler)handler).getGeneralHandler().insertChemical(stack.getInternal(), simulate ? Action.SIMULATE : Action.EXECUTE)))
                .orElse(stack);
    }

    @Method
    public ICrTGasStack addGasToTank(String tank, ICrTGasStack stack, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.GAS_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(component -> (ICrTGasStack)new CrTGasStack(component.insert(stack.getInternal(), simulate ? Action.SIMULATE : Action.EXECUTE, true)))
                .orElse(stack);
    }

    @Method
    public ICrTGasStack removeGas(ICrTGasStack stack, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.GAS_MACHINE_COMPONENT.get())
                .map(handler -> new CrTGasStack(((GasComponentHandler)handler).getGeneralHandler().extractChemical(stack.getInternal(), simulate ? Action.SIMULATE : Action.EXECUTE)))
                .orElse(new CrTGasStack(GasStack.EMPTY));
    }

    @Method
    public ICrTGasStack removeGasFromTank(String tank, long amount, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.GAS_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(component -> new CrTGasStack(component.extract(amount, simulate ? Action.SIMULATE : Action.EXECUTE, true)))
                .orElse(new CrTGasStack(GasStack.EMPTY));
    }

    /** INFUSION **/

    @Method
    public ICrTInfusionStack getInfusionStored(String tank) {
        return this.internal.getComponentManager().getComponentHandler(Registration.INFUSION_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(component -> new CrTInfusionStack(component.getStack().copy()))
                .orElse(new CrTInfusionStack(InfusionStack.EMPTY));
    }

    @Method
    public void setInfusionStored(String tank, ICrTInfusionStack stack) {
        this.internal.getComponentManager().getComponentHandler(Registration.INFUSION_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .ifPresent(component -> component.setStack(stack.getInternal()));
    }

    @Method
    public long getInfusionCapacity(String tank) {
        return this.internal.getComponentManager().getComponentHandler(Registration.INFUSION_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(InfusionMachineComponent::getCapacity)
                .orElse(0L);
    }

    @Method
    public ICrTInfusionStack addInfusion(ICrTInfusionStack stack, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.INFUSION_MACHINE_COMPONENT.get())
                .map(handler -> (ICrTInfusionStack)new CrTInfusionStack(((InfusionComponentHandler)handler).getGeneralHandler().insertChemical(stack.getInternal(), simulate ? Action.SIMULATE : Action.EXECUTE)))
                .orElse(stack);
    }

    @Method
    public ICrTInfusionStack addInfusionToTank(String tank, ICrTInfusionStack stack, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.INFUSION_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(component -> (ICrTInfusionStack)new CrTInfusionStack(component.insert(stack.getInternal(), simulate ? Action.SIMULATE : Action.EXECUTE, true)))
                .orElse(stack);
    }

    @Method
    public ICrTInfusionStack removeInfusion(ICrTInfusionStack stack, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.INFUSION_MACHINE_COMPONENT.get())
                .map(handler -> (ICrTInfusionStack)new CrTInfusionStack(((InfusionComponentHandler)handler).getGeneralHandler().extractChemical(stack.getInternal(), simulate ? Action.SIMULATE : Action.EXECUTE)))
                .orElse(new CrTInfusionStack(InfusionStack.EMPTY));
    }

    @Method
    public ICrTInfusionStack removeInfusionFromTank(String tank, long amount, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.INFUSION_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(component -> (ICrTInfusionStack)new CrTInfusionStack(component.extract(amount, simulate ? Action.SIMULATE : Action.EXECUTE, true)))
                .orElse(new CrTInfusionStack(InfusionStack.EMPTY));
    }

    /** PIGMENT **/

    @Method
    public ICrTPigmentStack getPigmentStored(String tank) {
        return this.internal.getComponentManager().getComponentHandler(Registration.PIGMENT_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(component -> new CrTPigmentStack(component.getStack().copy()))
                .orElse(new CrTPigmentStack(PigmentStack.EMPTY));
    }

    @Method
    public void setPigmentStored(String tank, ICrTPigmentStack stack) {
        this.internal.getComponentManager().getComponentHandler(Registration.PIGMENT_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .ifPresent(component -> component.setStack(stack.getInternal()));
    }

    @Method
    public long getPigmentCapacity(String tank) {
        return this.internal.getComponentManager().getComponentHandler(Registration.PIGMENT_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(PigmentMachineComponent::getCapacity)
                .orElse(0L);
    }

    @Method
    public ICrTPigmentStack addPigment(ICrTPigmentStack stack, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.PIGMENT_MACHINE_COMPONENT.get())
                .map(handler -> (ICrTPigmentStack)new CrTPigmentStack(((PigmentComponentHandler)handler).getGeneralHandler().insertChemical(stack.getInternal(), simulate ? Action.SIMULATE : Action.EXECUTE)))
                .orElse(stack);
    }

    @Method
    public ICrTPigmentStack addPigmentToTank(String tank, ICrTPigmentStack stack, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.PIGMENT_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(component -> (ICrTPigmentStack)new CrTPigmentStack(component.insert(stack.getInternal(), simulate ? Action.SIMULATE : Action.EXECUTE, true)))
                .orElse(stack);
    }

    @Method
    public ICrTPigmentStack removePigment(ICrTPigmentStack stack, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.PIGMENT_MACHINE_COMPONENT.get())
                .map(handler -> (ICrTPigmentStack)new CrTPigmentStack(((PigmentComponentHandler)handler).getGeneralHandler().extractChemical(stack.getInternal(), simulate ? Action.SIMULATE : Action.EXECUTE)))
                .orElse(new CrTPigmentStack(PigmentStack.EMPTY));
    }

    @Method
    public ICrTPigmentStack removePigmentFromTank(String tank, long amount, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.PIGMENT_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(component -> (ICrTPigmentStack)new CrTPigmentStack(component.extract(amount, simulate ? Action.SIMULATE : Action.EXECUTE, true)))
                .orElse(new CrTPigmentStack(PigmentStack.EMPTY));
    }

    /** SLURRY **/

    @Method
    public ICrTSlurryStack getSlurryStored(String tank) {
        return this.internal.getComponentManager().getComponentHandler(Registration.SLURRY_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(component -> new CrTSlurryStack(component.getStack().copy()))
                .orElse(new CrTSlurryStack(SlurryStack.EMPTY));
    }

    @Method
    public void setSlurryStored(String tank, ICrTSlurryStack stack) {
        this.internal.getComponentManager().getComponentHandler(Registration.SLURRY_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .ifPresent(component -> component.setStack(stack.getInternal()));
    }

    @Method
    public long getSlurryCapacity(String tank) {
        return this.internal.getComponentManager().getComponentHandler(Registration.SLURRY_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(SlurryMachineComponent::getCapacity)
                .orElse(0L);
    }

    @Method
    public ICrTSlurryStack addSlurry(ICrTSlurryStack stack, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.SLURRY_MACHINE_COMPONENT.get())
                .map(handler -> (ICrTSlurryStack)new CrTSlurryStack(((SlurryComponentHandler)handler).getGeneralHandler().insertChemical(stack.getInternal(), simulate ? Action.SIMULATE : Action.EXECUTE)))
                .orElse(stack);
    }

    @Method
    public ICrTSlurryStack addSlurryToTank(String tank, ICrTSlurryStack stack, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.SLURRY_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(component -> (ICrTSlurryStack)new CrTSlurryStack(component.insert(stack.getInternal(), simulate ? Action.SIMULATE : Action.EXECUTE, true)))
                .orElse(stack);
    }

    @Method
    public ICrTSlurryStack removeSlurry(ICrTSlurryStack stack, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.SLURRY_MACHINE_COMPONENT.get())
                .map(handler -> (ICrTSlurryStack)new CrTSlurryStack(((SlurryComponentHandler)handler).getGeneralHandler().extractChemical(stack.getInternal(), simulate ? Action.SIMULATE : Action.EXECUTE)))
                .orElse(new CrTSlurryStack(SlurryStack.EMPTY));
    }

    @Method
    public ICrTSlurryStack removeSlurryFromTank(String tank, long amount, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.SLURRY_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(component -> (ICrTSlurryStack)new CrTSlurryStack(component.extract(amount, simulate ? Action.SIMULATE : Action.EXECUTE, true)))
                .orElse(new CrTSlurryStack(SlurryStack.EMPTY));
    }

    /** HEAT **/

    @Method
    public double getHeat() {
        return this.internal.getComponentManager().getComponent(Registration.HEAT_MACHINE_COMPONENT.get())
                .flatMap(component -> Optional.ofNullable(component.getHeatCapacitor(0, null)))
                .map(IHeatCapacitor::getHeat)
                .orElse(0.0D);
    }

    @Method
    public double getTemperature() {
        return this.internal.getComponentManager().getComponent(Registration.HEAT_MACHINE_COMPONENT.get())
                .flatMap(component -> Optional.ofNullable(component.getHeatCapacitor(0, null)))
                .map(IHeatCapacitor::getTemperature)
                .orElse(0.0D);
    }

    @Method
    public void addHeat(double heat) {
        this.internal.getComponentManager().getComponent(Registration.HEAT_MACHINE_COMPONENT.get())
                .flatMap(component -> Optional.ofNullable(component.getHeatCapacitor(0, null)))
                .ifPresent(capacitor -> capacitor.handleHeat(heat));
    }

    @Method
    public void setHeat(double heat) {
        this.internal.getComponentManager().getComponent(Registration.HEAT_MACHINE_COMPONENT.get())
                .flatMap(component -> Optional.ofNullable(component.getHeatCapacitor(0, null)))
                .ifPresent(capacitor -> capacitor.setHeat(heat));
    }

    /** RADIATION **/

    @Method
    public double getRadiations() {
        return this.internal.getComponentManager().getComponent(Registration.RADIATION_MACHINE_COMPONENT.get())
                .map(RadiationMachineComponent::getRadiations)
                .orElse(0.0D);
    }

    @Method
    public void addRadiations(double amount) {
        this.internal.getComponentManager().getComponent(Registration.RADIATION_MACHINE_COMPONENT.get())
                .ifPresent(component -> component.addRadiations(amount));
    }

    @Method
    public void removeRadiations(double amount, int radius) {
        this.internal.getComponentManager().getComponent(Registration.RADIATION_MACHINE_COMPONENT.get())
                .ifPresent(component -> component.removeRadiations(amount, radius));
    }
}
