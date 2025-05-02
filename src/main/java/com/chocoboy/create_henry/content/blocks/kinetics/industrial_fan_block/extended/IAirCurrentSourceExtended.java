package com.chocoboy.create_henry.content.blocks.kinetics.industrial_fan_block.extended;

import com.simibubi.create.content.kinetics.fan.IAirCurrentSource;
import net.minecraft.util.Mth;
import com.chocoboy.create_henry.infrastructure.config.HKinetics;
import com.chocoboy.create_henry.infrastructure.config.HenryConfigs;

public interface IAirCurrentSourceExtended extends IAirCurrentSource {

    @Override
    default float getMaxDistance() {
        float speed = Math.abs(this.getSpeed());
        HKinetics config = HenryConfigs.server().kinetics;
        float distanceFactor = Math.min(speed / config.fanRotationArgmax.get(), 1);
        float pushDistance = Mth.lerp(distanceFactor, 3, config.fanPushDistance.get());
        float pullDistance = Mth.lerp(distanceFactor, 3f, config.fanPullDistance.get());
        return this.getSpeed() > 0 ? pushDistance : pullDistance;
    }

}