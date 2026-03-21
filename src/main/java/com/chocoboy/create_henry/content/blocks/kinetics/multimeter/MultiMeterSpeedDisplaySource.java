package com.chocoboy.create_henry.content.blocks.kinetics.multimeter;

import com.simibubi.create.content.redstone.displayLink.DisplayLinkContext;
import com.simibubi.create.content.redstone.displayLink.source.KineticSpeedDisplaySource;
import com.simibubi.create.content.redstone.displayLink.target.DisplayTargetStats;
import com.simibubi.create.foundation.utility.CreateLang;
import net.minecraft.network.chat.MutableComponent;

public class MultiMeterSpeedDisplaySource extends KineticSpeedDisplaySource {

    @Override
    protected String getTranslationKey() {
        return "multimeter_speed";
    }

    @Override
    protected MutableComponent provideLine(DisplayLinkContext ctx, DisplayTargetStats stats) {
        if (!(ctx.getSourceBlockEntity() instanceof MultiMeterBlockEntity be))
            return super.provideLine(ctx, stats);

        boolean absolute = ctx.sourceConfig().getInt("Directional") == 0;
        float speed = absolute ? Math.abs(be.getSpeed()) : be.getSpeed();
        return CreateLang.number(speed).space().translate("generic.unit.rpm").component();
    }
}
