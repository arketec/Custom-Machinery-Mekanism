package fr.frinn.custommachinerymekanism.common.mixin;

import fr.frinn.custommachinery.common.init.CustomMachineTile;
import fr.frinn.custommachinery.common.integration.kubejs.function.MachineJS;
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
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(value = MachineJS.class, remap = false)
public abstract class MachineJSMixin {

    @Final
    @Shadow(remap = false)
    private CustomMachineTile internal;

    /** GAS **/

    public GasStack getGasStored(String tank) {
        return this.internal.getComponentManager().getComponentHandler(Registration.GAS_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(component -> component.getStack().copy())
                .orElse(GasStack.EMPTY);
    }

    public void setGasStored(String tank, GasStack stack) {
        this.internal.getComponentManager().getComponentHandler(Registration.GAS_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .ifPresent(component -> component.setStack(stack));
    }

    public long getGasCapacity(String tank) {
        return this.internal.getComponentManager().getComponentHandler(Registration.GAS_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(GasMachineComponent::getCapacity)
                .orElse(0L);
    }

    public GasStack addGas(GasStack stack, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.GAS_MACHINE_COMPONENT.get())
                .map(handler -> ((GasComponentHandler)handler).getGeneralHandler().insertChemical(stack, simulate ? Action.SIMULATE : Action.EXECUTE))
                .orElse(stack);
    }

    public GasStack addGasToTank(String tank, GasStack stack, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.GAS_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(component -> component.insert(stack, simulate ? Action.SIMULATE : Action.EXECUTE, true))
                .orElse(stack);
    }

    public GasStack removeGas(GasStack stack, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.GAS_MACHINE_COMPONENT.get())
                .map(handler -> ((GasComponentHandler)handler).getGeneralHandler().extractChemical(stack, simulate ? Action.SIMULATE : Action.EXECUTE))
                .orElse(GasStack.EMPTY);
    }

    public GasStack removeGasFromTank(String tank, long amount, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.GAS_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(component -> component.extract(amount, simulate ? Action.SIMULATE : Action.EXECUTE, true))
                .orElse(GasStack.EMPTY);
    }

    /** INFUSION **/

    public InfusionStack getInfusionStored(String tank) {
        return this.internal.getComponentManager().getComponentHandler(Registration.INFUSION_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(component -> component.getStack().copy())
                .orElse(InfusionStack.EMPTY);
    }

    public void setInfusionStored(String tank, InfusionStack stack) {
        this.internal.getComponentManager().getComponentHandler(Registration.INFUSION_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .ifPresent(component -> component.setStack(stack));
    }

    public long getInfusionCapacity(String tank) {
        return this.internal.getComponentManager().getComponentHandler(Registration.INFUSION_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(InfusionMachineComponent::getCapacity)
                .orElse(0L);
    }

    public InfusionStack addInfusion(InfusionStack stack, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.INFUSION_MACHINE_COMPONENT.get())
                .map(handler -> ((InfusionComponentHandler)handler).getGeneralHandler().insertChemical(stack, simulate ? Action.SIMULATE : Action.EXECUTE))
                .orElse(stack);
    }

    public InfusionStack addInfusionToTank(String tank, InfusionStack stack, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.INFUSION_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(component -> component.insert(stack, simulate ? Action.SIMULATE : Action.EXECUTE, true))
                .orElse(stack);
    }

    public InfusionStack removeInfusion(InfusionStack stack, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.INFUSION_MACHINE_COMPONENT.get())
                .map(handler -> ((InfusionComponentHandler)handler).getGeneralHandler().extractChemical(stack, simulate ? Action.SIMULATE : Action.EXECUTE))
                .orElse(InfusionStack.EMPTY);
    }

    public InfusionStack removeInfusionFromTank(String tank, long amount, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.INFUSION_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(component -> component.extract(amount, simulate ? Action.SIMULATE : Action.EXECUTE, true))
                .orElse(InfusionStack.EMPTY);
    }

    /** PIGMENT **/

    public PigmentStack getPigmentStored(String tank) {
        return this.internal.getComponentManager().getComponentHandler(Registration.PIGMENT_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(component -> component.getStack().copy())
                .orElse(PigmentStack.EMPTY);
    }

    public void setPigmentStored(String tank, PigmentStack stack) {
        this.internal.getComponentManager().getComponentHandler(Registration.PIGMENT_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .ifPresent(component -> component.setStack(stack));
    }

    public long getPigmentCapacity(String tank) {
        return this.internal.getComponentManager().getComponentHandler(Registration.PIGMENT_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(PigmentMachineComponent::getCapacity)
                .orElse(0L);
    }

    public PigmentStack addPigment(PigmentStack stack, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.PIGMENT_MACHINE_COMPONENT.get())
                .map(handler -> ((PigmentComponentHandler)handler).getGeneralHandler().insertChemical(stack, simulate ? Action.SIMULATE : Action.EXECUTE))
                .orElse(stack);
    }

    public PigmentStack addPigmentToTank(String tank, PigmentStack stack, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.PIGMENT_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(component -> component.insert(stack, simulate ? Action.SIMULATE : Action.EXECUTE, true))
                .orElse(stack);
    }

    public PigmentStack removePigment(PigmentStack stack, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.PIGMENT_MACHINE_COMPONENT.get())
                .map(handler -> ((PigmentComponentHandler)handler).getGeneralHandler().extractChemical(stack, simulate ? Action.SIMULATE : Action.EXECUTE))
                .orElse(PigmentStack.EMPTY);
    }

    public PigmentStack removePigmentFromTank(String tank, long amount, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.PIGMENT_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(component -> component.extract(amount, simulate ? Action.SIMULATE : Action.EXECUTE, true))
                .orElse(PigmentStack.EMPTY);
    }

    /** SLURRY **/

    public SlurryStack getSlurryStored(String tank) {
        return this.internal.getComponentManager().getComponentHandler(Registration.SLURRY_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(component -> component.getStack().copy())
                .orElse(SlurryStack.EMPTY);
    }

    public void setSlurryStored(String tank, SlurryStack stack) {
        this.internal.getComponentManager().getComponentHandler(Registration.SLURRY_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .ifPresent(component -> component.setStack(stack));
    }

    public long getSlurryCapacity(String tank) {
        return this.internal.getComponentManager().getComponentHandler(Registration.SLURRY_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(SlurryMachineComponent::getCapacity)
                .orElse(0L);
    }

    public SlurryStack addSlurry(SlurryStack stack, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.SLURRY_MACHINE_COMPONENT.get())
                .map(handler -> ((SlurryComponentHandler)handler).getGeneralHandler().insertChemical(stack, simulate ? Action.SIMULATE : Action.EXECUTE))
                .orElse(stack);
    }

    public SlurryStack addSlurryToTank(String tank, SlurryStack stack, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.SLURRY_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(component -> component.insert(stack, simulate ? Action.SIMULATE : Action.EXECUTE, true))
                .orElse(stack);
    }

    public SlurryStack removeSlurry(SlurryStack stack, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.SLURRY_MACHINE_COMPONENT.get())
                .map(handler -> ((SlurryComponentHandler)handler).getGeneralHandler().extractChemical(stack, simulate ? Action.SIMULATE : Action.EXECUTE))
                .orElse(SlurryStack.EMPTY);
    }

    public SlurryStack removeSlurryFromTank(String tank, long amount, boolean simulate) {
        return this.internal.getComponentManager().getComponentHandler(Registration.SLURRY_MACHINE_COMPONENT.get())
                .flatMap(handler -> handler.getComponentForID(tank))
                .map(component -> component.extract(amount, simulate ? Action.SIMULATE : Action.EXECUTE, true))
                .orElse(SlurryStack.EMPTY);
    }

    /** HEAT **/

    public double getHeat() {
        return this.internal.getComponentManager().getComponent(Registration.HEAT_MACHINE_COMPONENT.get())
                .flatMap(component -> Optional.ofNullable(component.getHeatCapacitor(0, null)))
                .map(IHeatCapacitor::getHeat)
                .orElse(0.0D);
    }

    public double getTemperature() {
        return this.internal.getComponentManager().getComponent(Registration.HEAT_MACHINE_COMPONENT.get())
                .flatMap(component -> Optional.ofNullable(component.getHeatCapacitor(0, null)))
                .map(IHeatCapacitor::getTemperature)
                .orElse(0.0D);
    }

    public void addHeat(double heat) {
        this.internal.getComponentManager().getComponent(Registration.HEAT_MACHINE_COMPONENT.get())
                .flatMap(component -> Optional.ofNullable(component.getHeatCapacitor(0, null)))
                .ifPresent(capacitor -> capacitor.handleHeat(heat));
    }

    public void setHeat(double heat) {
        this.internal.getComponentManager().getComponent(Registration.HEAT_MACHINE_COMPONENT.get())
                .flatMap(component -> Optional.ofNullable(component.getHeatCapacitor(0, null)))
                .ifPresent(capacitor -> capacitor.setHeat(heat));
    }

    /** RADIATION **/

    public double getRadiations() {
        return this.internal.getComponentManager().getComponent(Registration.RADIATION_MACHINE_COMPONENT.get())
                .map(RadiationMachineComponent::getRadiations)
                .orElse(0.0D);
    }

    public void addRadiations(double amount) {
        this.internal.getComponentManager().getComponent(Registration.RADIATION_MACHINE_COMPONENT.get())
                .ifPresent(component -> component.addRadiations(amount));
    }

    public void removeRadiations(double amount, int radius) {
        this.internal.getComponentManager().getComponent(Registration.RADIATION_MACHINE_COMPONENT.get())
                .ifPresent(component -> component.removeRadiations(amount, radius));
    }
}
