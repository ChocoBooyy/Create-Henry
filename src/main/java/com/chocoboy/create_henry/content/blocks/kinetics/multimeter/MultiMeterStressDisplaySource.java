package com.chocoboy.create_henry.content.blocks.kinetics.multimeter;

import com.simibubi.create.content.redstone.displayLink.DisplayLinkContext;
import com.simibubi.create.content.redstone.displayLink.source.KineticStressDisplaySource;

public class MultiMeterStressDisplaySource extends KineticStressDisplaySource {

    @Override
    protected String getTranslationKey() {
        return "multimeter_stress";
    }

    @Override
    protected Float getProgress(DisplayLinkContext ctx) {
        if (!(ctx.getSourceBlockEntity() instanceof MultiMeterBlockEntity be))
            return super.getProgress(ctx);

        float cap = be.getNetworkCapacity();
        float stress = be.getNetworkStress();
        if (cap == 0)
            return 0f;

        int mode = ctx.sourceConfig().getInt("Mode");
        return switch (mode) {
            case 2 -> stress;
            case 3 -> cap - stress;
            case 4 -> cap;
            default -> stress / cap; // 0 = percent, 1 = progress bar
        };
    }
}
