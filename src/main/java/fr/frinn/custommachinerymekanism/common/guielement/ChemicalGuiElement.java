package fr.frinn.custommachinerymekanism.common.guielement;

import com.mojang.datafixers.util.Function8;
import fr.frinn.custommachinery.api.ICustomMachineryAPI;
import fr.frinn.custommachinery.api.codec.NamedCodec;
import fr.frinn.custommachinery.api.guielement.IComponentGuiElement;
import fr.frinn.custommachinery.impl.guielement.AbstractTexturedGuiElement;
import fr.frinn.custommachinerymekanism.common.component.ChemicalMachineComponent;
import net.minecraft.resources.ResourceLocation;

public abstract class ChemicalGuiElement<C extends ChemicalMachineComponent<?, ?>> extends AbstractTexturedGuiElement implements IComponentGuiElement<C> {

    private static final ResourceLocation BASE_TEXTURE = ICustomMachineryAPI.INSTANCE.rl("textures/gui/base_fluid_storage.png");

    public static <C extends ChemicalGuiElement<?>> NamedCodec<C> makeCodec(Function8<Integer, Integer, Integer, Integer, Integer, ResourceLocation, String, Boolean, C> constructor, String name) {
        return NamedCodec.record(elementInstance ->
                makeBaseTexturedCodec(elementInstance, BASE_TEXTURE)
                        .and(NamedCodec.STRING.fieldOf("id").forGetter(ChemicalGuiElement::getID))
                        .and(NamedCodec.BOOL.optionalFieldOf("highlight", true).forGetter(ChemicalGuiElement::highlight))
                        .apply(elementInstance, constructor), name
        );
    }

    private final String id;
    private final boolean highlight;

    public ChemicalGuiElement(int x, int y, int width, int height, int priority, ResourceLocation texture, String id, boolean highlight) {
        super(x, y, width, height, priority, texture);
        this.id = id;
        this.highlight = highlight;
    }

    @Override
    public String getID() {
        return this.id;
    }

    public boolean highlight() {
        return this.highlight;
    }
}
