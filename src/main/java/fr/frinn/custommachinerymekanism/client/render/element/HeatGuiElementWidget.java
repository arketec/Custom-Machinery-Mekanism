package fr.frinn.custommachinerymekanism.client.render.element;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.frinn.custommachinery.api.guielement.IMachineScreen;
import fr.frinn.custommachinery.impl.guielement.TexturedGuiElementWidget;
import fr.frinn.custommachinerymekanism.Registration;
import fr.frinn.custommachinerymekanism.common.guielement.HeatGuiElement;
import mekanism.api.IIncrementalEnum;
import mekanism.common.MekanismLang;
import mekanism.common.config.MekanismConfig;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.UnitDisplayUtils;
import mekanism.common.util.UnitDisplayUtils.TemperatureUnit;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;

public class HeatGuiElementWidget extends TexturedGuiElementWidget<HeatGuiElement> {


    public HeatGuiElementWidget(HeatGuiElement element, IMachineScreen screen) {
        super(element, screen, new TextComponent("Heat"));
    }

    @Override
    public void renderButton(PoseStack pose, int mouseX, int mouseY, float partialTicks) {
        super.renderButton(pose, mouseX, mouseY, partialTicks);
        this.getScreen().getTile().getComponentManager().getComponent(Registration.HEAT_MACHINE_COMPONENT.get()).ifPresent(component -> {
            double fillPercent = component.getHeatFillPercent();
            int barHeight = Mth.clamp((int)(fillPercent * (double)this.height), 1, this.height - 1);
            GuiComponent.fill(pose, this.x + 1, this.y + this.height - barHeight, this.x + this.width - 1, this.y + this.height - 1, FastColor.ARGB32.color(200, 255, 128, 0));
        });
    }

    @Override
    public List<Component> getTooltips() {
        return this.getScreen().getTile().getComponentManager()
                .getComponent(Registration.HEAT_MACHINE_COMPONENT.get())
                .map(component -> List.of(
                        (Component)MekanismLang.TEMPERATURE.translate(MekanismUtils.getTemperatureDisplay(component.getTemperature(0), TemperatureUnit.KELVIN, true)),
                        MekanismLang.DISSIPATED_RATE.translate(MekanismUtils.getTemperatureDisplay(component.getLastEnvironmentalLoss(), TemperatureUnit.KELVIN, false)),
                        MekanismLang.UNIT.translate(MekanismConfig.common.tempUnit.get())
                )).orElse(Collections.emptyList());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(!this.isHoveredOrFocused())
            return false;
        if(button == 0)
            this.updateTemperatureUnit(IIncrementalEnum::getNext);
        else if(button == 1)
            this.updateTemperatureUnit(IIncrementalEnum::getPrevious);
        return true;
    }

    private void updateTemperatureUnit(UnaryOperator<TemperatureUnit> converter) {
        UnitDisplayUtils.TemperatureUnit current = MekanismConfig.common.tempUnit.get();
        UnitDisplayUtils.TemperatureUnit updated = converter.apply(current);
        if (current != updated) {
            MekanismConfig.common.tempUnit.set(updated);
            MekanismConfig.common.save();
        }
    }
}
