package fr.frinn.custommachinerymekanism.common.guielement;

import com.mojang.datafixers.util.Function7;
import fr.frinn.custommachinery.api.ICustomMachineryAPI;
import fr.frinn.custommachinery.api.codec.NamedCodec;
import fr.frinn.custommachinery.api.guielement.IComponentGuiElement;
import fr.frinn.custommachinery.impl.guielement.AbstractTexturedGuiElement;
import fr.frinn.custommachinerymekanism.common.component.ChemicalMachineComponent;
import net.minecraft.resources.ResourceLocation;

public abstract class ChemicalGuiElement<C extends ChemicalMachineComponent<?, ?>> extends AbstractTexturedGuiElement implements IComponentGuiElement<C> {

    private static final ResourceLocation BASE_TEXTURE = ICustomMachineryAPI.INSTANCE.rl("textures/gui/base_fluid_storage.png");

    public static <C extends ChemicalGuiElement<?>> NamedCodec<C> makeCodec(Function7<Integer, Integer, Integer, Integer, Integer, ResourceLocation, String, C> constructor, String name) {
        return NamedCodec.record(elementInstance ->
                makeBaseTexturedCodec(elementInstance, BASE_TEXTURE)
                        .and(NamedCodec.STRING.fieldOf("id").forGetter(element -> element.getID()))
                        .apply(elementInstance, constructor), name
        );
    }

    private final String id;
    public ChemicalGuiElement(int x, int y, int width, int height, int priority, ResourceLocation texture, String id) {
        super(x, y, width, height, priority, texture);
        this.id = id;
    }

    @Override
    public String getID() {
        return this.id;
    }
}
