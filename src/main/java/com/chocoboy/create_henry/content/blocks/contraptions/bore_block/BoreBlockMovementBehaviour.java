package com.chocoboy.create_henry.content.blocks.contraptions.bore_block;

import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.mounted.MountedContraption;
import com.simibubi.create.content.kinetics.base.BlockBreakingMovementBehaviour;
import com.simibubi.create.content.trains.entity.CarriageContraption;
import com.simibubi.create.foundation.damageTypes.CreateDamageSources;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Level;

public class BoreBlockMovementBehaviour extends BlockBreakingMovementBehaviour {

    @Override
    protected DamageSource getDamageSource(Level level) {
        return CreateDamageSources.drill(level);
    }

    protected float getBlockBreakingSpeed(MovementContext context) {
        float lowerLimit = 1 / 128f;
        if (context.contraption instanceof MountedContraption)
            lowerLimit = 1f;
        if (context.contraption instanceof CarriageContraption)
            lowerLimit = 2f;
        return Mth.clamp(Math.abs(context.getAnimationSpeed()) / 350f, lowerLimit, 16f);
    }
}
