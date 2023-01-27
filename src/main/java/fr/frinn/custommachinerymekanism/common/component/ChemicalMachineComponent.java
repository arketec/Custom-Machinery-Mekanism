package fr.frinn.custommachinerymekanism.common.component;

import com.mojang.datafixers.util.Function8;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fr.frinn.custommachinery.api.component.ComponentIOMode;
import fr.frinn.custommachinery.api.component.IComparatorInputComponent;
import fr.frinn.custommachinery.api.component.IMachineComponentManager;
import fr.frinn.custommachinery.api.component.IMachineComponentTemplate;
import fr.frinn.custommachinery.api.component.ISerializableComponent;
import fr.frinn.custommachinery.api.component.ISideConfigComponent;
import fr.frinn.custommachinery.api.network.DataType;
import fr.frinn.custommachinery.api.network.ISyncable;
import fr.frinn.custommachinery.api.network.ISyncableStuff;
import fr.frinn.custommachinery.impl.codec.CodecLogger;
import fr.frinn.custommachinery.impl.codec.EnumCodec;
import fr.frinn.custommachinery.impl.component.AbstractMachineComponent;
import fr.frinn.custommachinery.impl.component.config.SideConfig;
import fr.frinn.custommachinerymekanism.common.network.syncable.ChemicalStackSyncable;
import fr.frinn.custommachinerymekanism.common.utils.Codecs;
import mekanism.api.Action;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import net.minecraft.nbt.CompoundTag;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class ChemicalMachineComponent<C extends Chemical<C>, S extends ChemicalStack<C>> extends AbstractMachineComponent implements ISerializableComponent, ISyncableStuff, IComparatorInputComponent, ISideConfigComponent {

    private final String id;
    private final long capacity;
    private final List<C> filter;
    private final boolean whitelist;
    private final long maxInput;
    private final long maxOutput;
    private final SideConfig config;

    private S stack = empty();

    public ChemicalMachineComponent(IMachineComponentManager manager, String id, long capacity, ComponentIOMode mode, List<C> filter, boolean whitelist, long maxInput, long maxOutput, SideConfig.Template config) {
        super(manager, mode);
        this.id = id;
        this.capacity = capacity;
        this.filter = filter;
        this.whitelist = whitelist;
        this.maxInput = maxInput;
        this.maxOutput = maxOutput;
        this.config = config.build(this);
    }

    public abstract S empty();

    public abstract S createStack(C type, long amount);

    public abstract S readFromNBT(CompoundTag nbt);

    public S createStack(S stack, long amount) {
        return createStack(stack.getType(), amount);
    }

    public String getId() {
        return this.id;
    }

    public long getCapacity() {
        return this.capacity;
    }

    public S getStack() {
        return this.stack;
    }

    public void setStack(S stack) {
        this.stack = stack;
        getManager().markDirty();
    }

    public boolean isValid(S stack) {
        return this.filter.stream().anyMatch(gas -> gas == stack.getType()) == this.whitelist;
    }

    //Return the remaining that was not inserted
    public S insert(S stack, Action action, boolean byPassLimit) {
        if(!isValid(stack))
            return stack;

        if(!this.stack.isEmpty() && this.stack.getType() != stack.getType())
            return stack;

        long maxInsert = this.stack.isEmpty() ? Math.min(this.capacity, stack.getAmount()) : Math.min(this.capacity - this.stack.getAmount(), stack.getAmount());
        if(!byPassLimit)
            maxInsert = Math.min(maxInsert, this.maxInput);
        if(action.execute())
            setStack(createStack(stack, maxInsert + (this.stack.isEmpty() ? 0 : this.stack.getAmount())));
        return createStack(stack, stack.getAmount() - maxInsert);
    }

    //Return the extracted stack
    public S extract(long amount, Action action, boolean byPassLimit) {
        if(this.stack.isEmpty())
            return empty();

        long maxExtract = Math.min(this.stack.getAmount(), amount);
        if(!byPassLimit)
            maxExtract = Math.min(maxExtract, this.maxOutput);
        C type = this.stack.getType();
        if(action.execute()) {
            this.stack.shrink(maxExtract);
            getManager().markDirty();
        }
        return createStack(type, maxExtract);
    }

    @Override
    public int getComparatorInput() {
        return (int) (15 * ((double)this.stack.getAmount() / (double)this.capacity));
    }

    @Override
    public void serialize(CompoundTag nbt) {
        if(!this.stack.isEmpty())
            nbt.put("stack", this.stack.write(new CompoundTag()));
        nbt.put("config", this.config.serialize());
    }

    @Override
    public void deserialize(CompoundTag nbt) {
        if(nbt.contains("stack"))
            this.stack = readFromNBT(nbt.getCompound("stack"));
        if(nbt.contains("config"))
            this.config.deserialize(nbt.get("config"));
    }

    @Override
    public SideConfig getConfig() {
        return this.config;
    }

    @Override
    public void getStuffToSync(Consumer<ISyncable<?, ?>> container) {
        container.accept(ChemicalStackSyncable.create(this::getStack, this::setStack));
        container.accept(DataType.createSyncable(SideConfig.class, this::getConfig, this.config::set));
    }

    public static abstract class Template<C extends Chemical<C>, S extends ChemicalStack<C>, T extends ChemicalMachineComponent<C, S>> implements IMachineComponentTemplate<T> {

        public static <C extends Chemical<C>, S extends ChemicalStack<C>, T extends ChemicalMachineComponent<C, S>> Codec<Template<C, S, T>> makeCodec(Codec<C> codec, Function8<String, Long, ComponentIOMode, List<C>, Boolean, Long, Long, SideConfig.Template, Template<C, S, T>> builder) {
            return RecordCodecBuilder.create(templateInstance ->
                    templateInstance.group(
                            Codec.STRING.fieldOf("id").forGetter(template -> template.id),
                            Codec.LONG.fieldOf("capacity").forGetter(template -> template.capacity),
                            CodecLogger.loggedOptional(EnumCodec.of(ComponentIOMode.class), "mode", ComponentIOMode.BOTH).forGetter(template -> template.mode),
                            CodecLogger.loggedOptional(Codecs.list(codec), "filter", Collections.emptyList()).forGetter(template -> template.filter),
                            CodecLogger.loggedOptional(Codec.BOOL, "whitelist", false).forGetter(template -> template.whitelist),
                            CodecLogger.loggedOptional(Codec.LONG, "max_input").forGetter(template -> Optional.of(template.maxInput)),
                            CodecLogger.loggedOptional(Codec.LONG, "max_output").forGetter(template -> Optional.of(template.maxOutput)),
                            CodecLogger.loggedOptional(SideConfig.Template.CODEC, "config").forGetter(template -> Optional.of(template.config))
                    ).apply(templateInstance, (id, capacity, mode, filter, whitelist, maxInput, maxOutput, config) ->
                            builder.apply(id, capacity, mode, filter, whitelist, maxInput.orElse(capacity), maxOutput.orElse(capacity), config.orElse(mode.getBaseConfig()))
                    )
            );
        }

        private final String id;
        private final long capacity;
        private final ComponentIOMode mode;
        private final List<C> filter;
        private final boolean whitelist;
        private final long maxInput;
        private final long maxOutput;
        private final SideConfig.Template config;

        public Template(String id, long capacity, ComponentIOMode mode, List<C> filter, boolean whitelist, long maxInput, long maxOutput, SideConfig.Template config) {
            this.id = id;
            this.capacity = capacity;
            this.mode = mode;
            this.filter = filter;
            this.whitelist = whitelist;
            this.maxInput = maxInput;
            this.maxOutput = maxOutput;
            this.config = config;
        }

        public abstract boolean isSameType(ChemicalStack<?> stack);

        @Override
        public String getId() {
            return this.id;
        }

        @Override
        public boolean canAccept(Object ingredient, boolean isInput, IMachineComponentManager manager) {
            if(isInput != this.mode.isInput())
                return false;
            if(ingredient instanceof ChemicalStack<?> stack && isSameType(stack)) {
                return this.filter.stream().anyMatch(g -> g == stack.getType()) == this.whitelist;
            } else if(ingredient instanceof List<?> list) {
                return list.stream().allMatch(object -> {
                    if(object instanceof ChemicalStack<?> stack && isSameType(stack))
                        return this.filter.stream().anyMatch(g -> g == stack.getType()) == this.whitelist;
                    return false;
                });
            }
            return false;
        }

        public abstract T build(IMachineComponentManager manager, String id, long capacity, ComponentIOMode mode, List<C> filter, boolean whitelist, long maxInput, long maxOutput, SideConfig.Template config);

        @Override
        public T build(IMachineComponentManager manager) {
            return build(manager, this.id, this.capacity, this.mode, this.filter, this.whitelist, this.maxInput, this.maxOutput, this.config);
        }
    }
}
