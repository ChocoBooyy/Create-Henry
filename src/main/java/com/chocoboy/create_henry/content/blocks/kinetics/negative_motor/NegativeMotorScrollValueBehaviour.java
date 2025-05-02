package com.chocoboy.create_henry.content.blocks.kinetics.negative_motor;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsBoard;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsFormatter;
import com.simibubi.create.foundation.utility.Components;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Collections;

public class NegativeMotorScrollValueBehaviour extends ScrollValueBehaviour implements ValueSettingsBehaviour {
    public NegativeMotorScrollValueBehaviour(Component label, SmartBlockEntity be, ValueBoxTransform slot) {
        super(label, be, slot);
        this.withFormatter(v -> String.valueOf(v));
    }

    @Override
    public ValueSettingsBoard createBoard(Player player, BlockHitResult hitResult) {
        MutableComponent header = Components.literal(" ");
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
        return Lang.number(settings.value()).component();
    }

    @Override
    public String getClipboardKey() {
        return "Stress";
    }
}
