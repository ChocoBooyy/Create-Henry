package com.chocoboy.create_henry.content.fans.processing;

import com.chocoboy.create_henry.registry.HenryRecipeTypes;
import com.chocoboy.create_henry.registry.HenryTags;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import com.simibubi.create.content.trains.CubeParticleData;
import net.createmod.catnip.theme.Color;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public final class DragonBreathingType extends AbstractFanProcessingType {

    private static final int COLOR_LIGHT = 0xD36FD9;
    private static final int COLOR_DARK  = 0xC21BF5;

    // Cube particle color components (purple-ish, matching dragon breath)
    private static final float CUBE_R = 0.2f;
    private static final float CUBE_G = 0.08f;
    private static final float CUBE_B = 0.3f;

    public DragonBreathingType() {
        super(HenryRecipeTypes.DRAGON_BREATHING);
    }

    @Override
    public boolean isValidAt(Level level, BlockPos pos) {
        return isValidAtTags(level, pos,
                HenryTags.AllFluidTags.FAN_PROCESSING_CATALYSTS_DRAGON_BREATHING,
                HenryTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_DRAGON_BREATHING);
    }

    @Override
    public int getPriority() {
        return 691400;
    }

    @Override
    public void spawnProcessingParticles(Level level, Vec3 pos) {
        if (level.random.nextInt(8) != 0) return;
        level.addParticle(ParticleTypes.DRAGON_BREATH,
                pos.x + (level.random.nextFloat() - .5f) * .5f, pos.y + .5f,
                pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
    }

    @Override
    public void morphAirFlow(FanProcessingType.AirFlowParticleAccess particleAccess, RandomSource random) {
        particleAccess.setColor(Color.mixColors(COLOR_LIGHT, COLOR_DARK, random.nextFloat()));
        particleAccess.setAlpha(1f);
        if (random.nextFloat() < 1 / 128f)
            particleAccess.spawnExtraParticle(ParticleTypes.DRAGON_BREATH, .125f);
        if (random.nextFloat() < 1 / 32f)
            particleAccess.spawnExtraParticle(ParticleTypes.WITCH, .125f);
        if (random.nextFloat() < 1 / 32f)
            particleAccess.spawnExtraParticle(new CubeParticleData(CUBE_R, CUBE_G, CUBE_B, 0.075f, 12, false), .125f);
        if (random.nextFloat() < 1 / 32f)
            particleAccess.spawnExtraParticle(new CubeParticleData(CUBE_R, CUBE_G, CUBE_B, 0.1f, 14, false), .125f);
    }

    @Override
    public void affectEntity(Entity entity, Level level) {
        if (entity instanceof Silverfish silverfish) {
            transformEntity(silverfish, EntityType.ENDERMITE,
                    "CreateBreathing",
                    SoundEvents.ENDERMITE_AMBIENT, SoundEvents.ENDERMAN_SCREAM, level);

        } else if (entity instanceof WitherSkeleton witherSkeleton) {
            transformEntity(witherSkeleton, EntityType.ENDERMAN,
                    "CreateBreathing",
                    SoundEvents.ENDERMAN_SCREAM, SoundEvents.ENDERMAN_STARE, level);

        } else if (entity instanceof LivingEntity livingEntity
                && !(entity instanceof EnderMan)
                && !(entity instanceof Endermite)
                && !(entity instanceof EnderDragon)) {
            teleportRandomly(livingEntity, level);
        }
    }

    private static void teleportRandomly(LivingEntity entity, Level level) {
        RandomSource rand = level.random;
        double newX = entity.getX() + (rand.nextDouble() * 5) - 5;
        double newY = Math.max(0, entity.getY() + (rand.nextDouble() * 2) - 2);
        double newZ = entity.getZ() + (rand.nextDouble() * 5) - 5;

        entity.teleportTo(newX, newY, newZ);
        level.playSound(null, newX, newY, newZ, SoundEvents.ENDERMAN_TELEPORT, SoundSource.NEUTRAL,
                1.0f, 1.0f + (float) (rand.nextGaussian() * 0.05));

        for (int i = 0; i < 10; i++) {
            level.addParticle(ParticleTypes.PORTAL,
                    newX + (rand.nextDouble() - 0.5) * 2,
                    newY + rand.nextDouble() * 2,
                    newZ + (rand.nextDouble() - 0.5) * 2,
                    (rand.nextDouble() - 0.5) * 2,
                    (rand.nextDouble() - 0.5) * 2,
                    (rand.nextDouble() - 0.5) * 2);
        }
    }
}
