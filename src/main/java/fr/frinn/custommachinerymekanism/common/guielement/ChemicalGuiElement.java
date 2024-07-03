package fr.frinn.custommachinerymekanism.common.guielement;

import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Function8;
import fr.frinn.custommachinery.api.ICustomMachineryAPI;
import fr.frinn.custommachinery.api.codec.NamedCodec;
import fr.frinn.custommachinery.api.guielement.IComponentGuiElement;
import fr.frinn.custommachinery.impl.guielement.AbstractTexturedGuiElement;
import fr.frinn.custommachinerymekanism.common.component.ChemicalMachineComponent;
import net.minecraft.resources.ResourceLocation;

public abstract class ChemicalGuiElement<C extends ChemicalMachineComponent<?, ?>> extends AbstractTexturedGuiElement implements IComponentGuiElement<C> {

    private static final ResourceLocation BASE_TEXTURE = ICustomMachineryAPI.INSTANCE.rl("textures/gui/base_fluid_storage.png");

    public static <C extends ChemicalGuiElement<?>> NamedCodec<C> makeCodec(Function3<Properties, String, Boolean, C> constructor, String name) {
        return NamedCodec.record(elementInstance ->
                elementInstance.group(
                        makePropertiesCodec(BASE_TEXTURE).forGetter(ChemicalGuiElement::getProperties),
                        NamedCodec.STRING.fieldOf("id").forGetter(ChemicalGuiElement::getComponentId),
                        NamedCodec.BOOL.optionalFieldOf("highlight", true).forGetter(ChemicalGuiElement::highlight)
                ).apply(elementInstance, constructor), name
        );
    }

    private final String id;
    private final boolean highlight;

    public ChemicalGuiElement(Properties properties, String id, boolean highlight) {
        super(properties);
        this.id = id;
        this.highlight = highlight;
    }

    @Override
    public String getComponentId() {
        return this.id;
    }

    public boolean highlight() {
        return this.highlight;
    }
}
