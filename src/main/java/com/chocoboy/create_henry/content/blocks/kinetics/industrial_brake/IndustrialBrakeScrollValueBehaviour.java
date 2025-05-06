package com.chocoboy.create_henry.content.blocks.kinetics.industrial_brake;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsBoard;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsFormatter;
import com.simibubi.create.foundation.utility.CreateLang;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Collections;

public class IndustrialBrakeScrollValueBehaviour extends ScrollValueBehaviour implements ValueSettingsBehaviour {
    public IndustrialBrakeScrollValueBehaviour(Component label, SmartBlockEntity be, ValueBoxTransform slot) {
        super(label, be, slot);
        this.withFormatter(v -> String.valueOf(v));
    }

    @Override
    public ValueSettingsBoard createBoard(Player player, BlockHitResult hitResult) {
        MutableComponent header = Component.literal(" ");
        ValueSettingsFormatter formatter = new ValueSettingsFormatter(this::formatSettings);
        return new ValueSettingsBoard(label, max, 10, Collections.singletonList(header), formatter);
    }

    @Override
    public void setValueSettings(Player player, ValueSettingsBehaviour.ValueSettings settings, boolean ctrlDown) {
        int v = Math.max(0, settings.value());
        if (!settings.equals(getValueSettings())) playFeedbackSound(this);
        setValue(v);
    }

    @Override
    public ValueSettingsBehaviour.ValueSettings getValueSettings() {
        return new ValueSettingsBehaviour.ValueSettings(0, value);
    }

    private MutableComponent formatSettings(ValueSettingsBehaviour.ValueSettings settings) {
        return CreateLang.number(settings.value()).component();
    }

    @Override
    public String getClipboardKey() {
        return "Stress";
    }
}