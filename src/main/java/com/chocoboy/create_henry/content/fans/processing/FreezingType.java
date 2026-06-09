package com.chocoboy.create_henry.content.fans.processing;

import com.chocoboy.create_henry.registry.HenryRecipeTypes;
import com.chocoboy.create_henry.registry.HenryTags;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.theme.Color;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public final class FreezingType extends AbstractFanProcessingType {

    private static final int COLOR_DARK  = 0xDDE8FF;
    private static final int COLOR_LIGHT = 0xEEEEFF;

    public FreezingType() {
        super(HenryRecipeTypes.FREEZING);
    }

    @Override
    public boolean isValidAt(Level level, BlockPos pos) {
        return isValidAtTags(level, pos,
                HenryTags.AllFluidTags.FAN_PROCESSING_CATALYSTS_FREEZING,
                HenryTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_FREEZING);
    }

    @Override
    public int getPriority() {
        return 691100;
    }

    @Override
    public void spawnProcessingParticles(Level level, Vec3 pos) {
        if (level.random.nextInt(8) != 0) return;
        Vector3f color = new Color(COLOR_DARK).asVectorF();
        level.addParticle(new DustParticleOptions(color, 1),
                pos.x + (level.random.nextFloat() - .5f) * .5f, pos.y + .5f,
                pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
        level.addParticle(ParticleTypes.SNOWFLAKE,
                pos.x + (level.random.nextFloat() - .5f) * .5f, pos.y + .5f,
                pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
    }

    @Override
    public void morphAirFlow(FanProcessingType.AirFlowParticleAccess particleAccess, RandomSource random) {
        particleAccess.setColor(Color.mixColors(COLOR_LIGHT, COLOR_DARK, random.nextFloat()));
        particleAccess.setAlpha(1f);
        if (random.nextFloat() < 1 / 128f)
            particleAccess.spawnExtraParticle(ParticleTypes.SNOWFLAKE, .125f);
        if (random.nextFloat() < 1 / 32f)
            particleAccess.spawnExtraParticle(ParticleTypes.POOF, .125f);
    }

    @Override
    public void affectEntity(Entity entity, Level level) {
        if (level.isClientSide) {
            if (entity instanceof Skeleton) {
                Vec3 p = entity.getPosition(0);
                Vec3 v = p.add(0, 0.5f, 0)
                        .add(VecHelper.offsetRandomly(Vec3.ZERO, level.random, 1)
                                .multiply(1, 0.2f, 1).normalize().scale(1f));
                level.addParticle(ParticleTypes.SNOWFLAKE, v.x, v.y, v.z, 0, 0.1f, 0);
                if (level.random.nextInt(3) == 0)
                    level.addParticle(ParticleTypes.SNOWFLAKE, p.x, p.y + .5f, p.z,
                            (level.random.nextFloat() - .5f) * .5f, 0.1f,
                            (level.random.nextFloat() - .5f) * .5f);
            }
            return;
        }

        if (entity instanceof EnderMan || entity.getType() == EntityType.BLAZE)
            entity.hurt(level.damageSources().freeze(), 8);

        if (entity instanceof LivingEntity livingEntity)
            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 7, false, false));

        if (entity instanceof SnowGolem snowGolem)
            snowGolem.heal(4);

        if (entity instanceof Stray stray)
            stray.heal(2);

        if (entity.isOnFire()) {
            entity.clearFire();
            level.playSound(null, entity.blockPosition(), SoundEvents.GENERIC_EXTINGUISH_FIRE,
                    SoundSource.NEUTRAL, 0.7f,
                    1.6f + (level.random.nextFloat() - level.random.nextFloat()) * 0.4f);
        }

        if (entity instanceof Skeleton skeleton)
            transformEntity(skeleton, EntityType.STRAY,
                    "CreateFreezing",
                    SoundEvents.STRAY_AMBIENT, SoundEvents.SKELETON_CONVERTED_TO_STRAY, level);
    }
}
