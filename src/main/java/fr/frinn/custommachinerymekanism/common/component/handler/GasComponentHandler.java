package fr.frinn.custommachinerymekanism.common.component.handler;

import fr.frinn.custommachinery.api.component.IMachineComponentManager;
import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinerymekanism.Registration;
import fr.frinn.custommachinerymekanism.common.component.GasMachineComponent;
import fr.frinn.custommachinerymekanism.common.transfer.SidedGasTank;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasHandler;
import mekanism.common.capabilities.Capabilities;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GasComponentHandler extends ChemicalComponentHandler<Gas, GasStack, IGasHandler, GasMachineComponent> {

    public GasComponentHandler(IMachineComponentManager manager, List<GasMachineComponent> components) {
        super(manager, components);
    }

    @Override
    public Capability<IGasHandler> targetCap() {
        return Capabilities.GAS_HANDLER;
    }

    @Override
    public IGasHandler createSidedHandler(@Nullable Direction side) {
        return new SidedGasTank(this, side);
    }

    @Override
    public MachineComponentType<GasMachineComponent> getType() {
        return Registration.GAS_MACHINE_COMPONENT.get();
    }
}
