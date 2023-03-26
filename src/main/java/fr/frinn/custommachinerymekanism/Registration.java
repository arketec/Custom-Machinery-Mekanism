package fr.frinn.custommachinerymekanism;

import dev.architectury.registry.registries.Registries;
import fr.frinn.custommachinery.api.ICustomMachineryAPI;
import fr.frinn.custommachinery.api.component.MachineComponentType;
import fr.frinn.custommachinery.api.guielement.GuiElementType;
import fr.frinn.custommachinery.api.network.DataType;
import fr.frinn.custommachinery.api.requirement.RequirementType;
import fr.frinn.custommachinerymekanism.common.component.GasMachineComponent;
import fr.frinn.custommachinerymekanism.common.component.HeatMachineComponent;
import fr.frinn.custommachinerymekanism.common.component.InfusionMachineComponent;
import fr.frinn.custommachinerymekanism.common.component.PigmentMachineComponent;
import fr.frinn.custommachinerymekanism.common.component.SlurryMachineComponent;
import fr.frinn.custommachinerymekanism.common.component.handler.GasComponentHandler;
import fr.frinn.custommachinerymekanism.common.component.handler.InfusionComponentHandler;
import fr.frinn.custommachinerymekanism.common.component.handler.PigmentComponentHandler;
import fr.frinn.custommachinerymekanism.common.component.handler.SlurryComponentHandler;
import fr.frinn.custommachinerymekanism.common.guielement.GasGuiElement;
import fr.frinn.custommachinerymekanism.common.guielement.HeatGuiElement;
import fr.frinn.custommachinerymekanism.common.guielement.InfusionGuiElement;
import fr.frinn.custommachinerymekanism.common.guielement.PigmentGuiElement;
import fr.frinn.custommachinerymekanism.common.guielement.SlurryGuiElement;
import fr.frinn.custommachinerymekanism.common.network.data.GasStackData;
import fr.frinn.custommachinerymekanism.common.network.data.InfusionStackData;
import fr.frinn.custommachinerymekanism.common.network.data.PigmentStackData;
import fr.frinn.custommachinerymekanism.common.network.data.SlurryStackData;
import fr.frinn.custommachinerymekanism.common.network.syncable.ChemicalStackSyncable;
import fr.frinn.custommachinerymekanism.common.requirement.GasPerTickRequirement;
import fr.frinn.custommachinerymekanism.common.requirement.GasRequirement;
import fr.frinn.custommachinerymekanism.common.requirement.HeatPerTickRequirement;
import fr.frinn.custommachinerymekanism.common.requirement.HeatRequirement;
import fr.frinn.custommachinerymekanism.common.requirement.InfusionPerTickRequirement;
import fr.frinn.custommachinerymekanism.common.requirement.InfusionRequirement;
import fr.frinn.custommachinerymekanism.common.requirement.PigmentPerTickRequirement;
import fr.frinn.custommachinerymekanism.common.requirement.PigmentRequirement;
import fr.frinn.custommachinerymekanism.common.requirement.SlurryPerTickRequirement;
import fr.frinn.custommachinerymekanism.common.requirement.SlurryRequirement;
import fr.frinn.custommachinerymekanism.common.requirement.TemperatureRequirement;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.infuse.InfusionStack;
import mekanism.api.chemical.pigment.PigmentStack;
import mekanism.api.chemical.slurry.SlurryStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class Registration {

    public static final Registries REGISTRIES = Registries.get(CustomMachineryMekanism.MODID);

    public static final DeferredRegister<MachineComponentType<?>> MACHINE_COMPONENTS = DeferredRegister.create(MachineComponentType.REGISTRY_KEY, ICustomMachineryAPI.INSTANCE.modid());
    public static final DeferredRegister<GuiElementType<?>> GUI_ELEMENTS = DeferredRegister.create(GuiElementType.REGISTRY_KEY, ICustomMachineryAPI.INSTANCE.modid());
    public static final DeferredRegister<RequirementType<?>> REQUIREMENTS = DeferredRegister.create(RequirementType.REGISTRY_KEY, ICustomMachineryAPI.INSTANCE.modid());
    public static final DeferredRegister<DataType<?, ?>> DATAS = DeferredRegister.create(DataType.REGISTRY_KEY, ICustomMachineryAPI.INSTANCE.modid());

    public static final RegistryObject<MachineComponentType<GasMachineComponent>> GAS_MACHINE_COMPONENT = MACHINE_COMPONENTS.register("gas", () -> MachineComponentType.create(GasMachineComponent.Template.CODEC).setNotSingle(GasComponentHandler::new));
    public static final RegistryObject<MachineComponentType<InfusionMachineComponent>> INFUSION_MACHINE_COMPONENT = MACHINE_COMPONENTS.register("infusion", () -> MachineComponentType.create(InfusionMachineComponent.Template.CODEC).setNotSingle(InfusionComponentHandler::new));
    public static final RegistryObject<MachineComponentType<PigmentMachineComponent>> PIGMENT_MACHINE_COMPONENT = MACHINE_COMPONENTS.register("pigment", () -> MachineComponentType.create(PigmentMachineComponent.Template.CODEC).setNotSingle(PigmentComponentHandler::new));
    public static final RegistryObject<MachineComponentType<SlurryMachineComponent>> SLURRY_MACHINE_COMPONENT = MACHINE_COMPONENTS.register("slurry", () -> MachineComponentType.create(SlurryMachineComponent.Template.CODEC).setNotSingle(SlurryComponentHandler::new));
    public static final RegistryObject<MachineComponentType<HeatMachineComponent>> HEAT_MACHINE_COMPONENT = MACHINE_COMPONENTS.register("heat", () -> MachineComponentType.create(HeatMachineComponent.Template.CODEC));

    public static final RegistryObject<GuiElementType<GasGuiElement>> GAS_GUI_ELEMENT = GUI_ELEMENTS.register("gas", () -> GuiElementType.create(GasGuiElement.CODEC));
    public static final RegistryObject<GuiElementType<InfusionGuiElement>> INFUSION_GUI_ELEMENT = GUI_ELEMENTS.register("infusion", () -> GuiElementType.create(InfusionGuiElement.CODEC));
    public static final RegistryObject<GuiElementType<PigmentGuiElement>> PIGMENT_GUI_ELEMENT = GUI_ELEMENTS.register("pigment", () -> GuiElementType.create(PigmentGuiElement.CODEC));
    public static final RegistryObject<GuiElementType<SlurryGuiElement>> SLURRY_GUI_ELEMENT = GUI_ELEMENTS.register("slurry", () -> GuiElementType.create(SlurryGuiElement.CODEC));
    public static final RegistryObject<GuiElementType<HeatGuiElement>> HEAT_GUI_ELEMENT = GUI_ELEMENTS.register("heat", () -> GuiElementType.create(HeatGuiElement.CODEC));

    public static final RegistryObject<RequirementType<GasRequirement>> GAS_REQUIREMENT = REQUIREMENTS.register("gas", () -> RequirementType.inventory(GasRequirement.CODEC));
    public static final RegistryObject<RequirementType<GasPerTickRequirement>> GAS_PER_TICK_REQUIREMENT = REQUIREMENTS.register("gas_per_tick", () -> RequirementType.inventory(GasPerTickRequirement.CODEC));
    public static final RegistryObject<RequirementType<InfusionRequirement>> INFUSION_REQUIREMENT = REQUIREMENTS.register("infusion", () -> RequirementType.inventory(InfusionRequirement.CODEC));
    public static final RegistryObject<RequirementType<InfusionPerTickRequirement>> INFUSION_PER_TICK_REQUIREMENT = REQUIREMENTS.register("infusion_per_tick", () -> RequirementType.inventory(InfusionPerTickRequirement.CODEC));
    public static final RegistryObject<RequirementType<PigmentRequirement>> PIGMENT_REQUIREMENT = REQUIREMENTS.register("pigment", () -> RequirementType.inventory(PigmentRequirement.CODEC));
    public static final RegistryObject<RequirementType<PigmentPerTickRequirement>> PIGMENT_PER_TICK_REQUIREMENT = REQUIREMENTS.register("pigment_per_tick", () -> RequirementType.inventory(PigmentPerTickRequirement.CODEC));
    public static final RegistryObject<RequirementType<SlurryRequirement>> SLURRY_REQUIREMENT = REQUIREMENTS.register("slurry", () -> RequirementType.inventory(SlurryRequirement.CODEC));
    public static final RegistryObject<RequirementType<SlurryPerTickRequirement>> SLURRY_PER_TICK_REQUIREMENT = REQUIREMENTS.register("slurry_per_tick", () -> RequirementType.inventory(SlurryPerTickRequirement.CODEC));
    public static final RegistryObject<RequirementType<HeatRequirement>> HEAT_REQUIREMENT = REQUIREMENTS.register("heat", () -> RequirementType.inventory(HeatRequirement.CODEC));
    public static final RegistryObject<RequirementType<HeatPerTickRequirement>> HEAT_PER_TICK_REQUIREMENT = REQUIREMENTS.register("heat_per_tick", () -> RequirementType.inventory(HeatPerTickRequirement.CODEC));
    public static final RegistryObject<RequirementType<TemperatureRequirement>> TEMPERATURE_REQUIREMENT = REQUIREMENTS.register("temperature", () -> RequirementType.inventory(TemperatureRequirement.CODEC));

    public static final RegistryObject<DataType<GasStackData, GasStack>> GAS_DATA = DATAS.register("gas", () -> DataType.create(GasStack.class, ChemicalStackSyncable::create, GasStackData::new));
    public static final RegistryObject<DataType<SlurryStackData, SlurryStack>> SLURRY_DATA = DATAS.register("slurry", () -> DataType.create(SlurryStack.class, ChemicalStackSyncable::create, SlurryStackData::new));
    public static final RegistryObject<DataType<InfusionStackData, InfusionStack>> INFUSION_DATA = DATAS.register("infusion", () -> DataType.create(InfusionStack.class, ChemicalStackSyncable::create, InfusionStackData::new));
    public static final RegistryObject<DataType<PigmentStackData, PigmentStack>> PIGMENT_DATA = DATAS.register("pigment", () -> DataType.create(PigmentStack.class, ChemicalStackSyncable::create, PigmentStackData::new));
}
