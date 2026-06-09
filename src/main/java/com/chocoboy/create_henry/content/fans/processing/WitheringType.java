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
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public final class WitheringType extends AbstractFanProcessingType {

    private static final int COLOR_DARK  = 0x180c30;
    private static final int COLOR_LIGHT = 0x1e0f3d;

    public WitheringType() {
        super(HenryRecipeTypes.WITHERING);
    }

    @Override
    public boolean isValidAt(Level level, BlockPos pos) {
        return isValidAtTags(level, pos,
                HenryTags.AllFluidTags.FAN_PROCESSING_CATALYSTS_WITHERING,
                HenryTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_WITHERING);
    }

    @Override
    public int getPriority() {
        return 691300;
    }

    @Override
    public void spawnProcessingParticles(Level level, Vec3 pos) {
        if (level.random.nextInt(8) != 0) return;
        Vector3f color = new Color(COLOR_LIGHT).asVectorF();
        level.addParticle(new DustParticleOptions(color, 1),
                pos.x + (level.random.nextFloat() - .5f) * .5f, pos.y + .5f,
                pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
        level.addParticle(ParticleTypes.SOUL, pos.x, pos.y + .45f, pos.z, 0, 0, 0);
        level.addParticle(ParticleTypes.SOUL,
                pos.x + (level.random.nextFloat() - .5f) * .5f, pos.y + .5f,
                pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
    }

    @Override
    public void morphAirFlow(FanProcessingType.AirFlowParticleAccess particleAccess, RandomSource random) {
        particleAccess.setColor(Color.mixColors(COLOR_DARK, COLOR_LIGHT, random.nextFloat()));
        particleAccess.setAlpha(1f);
        if (random.nextFloat() < 1 / 32f)
            particleAccess.spawnExtraParticle(ParticleTypes.SOUL, .125f);
        Vector3f colorDark  = new Color(COLOR_DARK).asVectorF();
        Vector3f colorLight = new Color(COLOR_LIGHT).asVectorF();
        if (random.nextFloat() < 1 / 32f)
            particleAccess.spawnExtraParticle(new DustParticleOptions(colorDark, 1), .125f);
        if (random.nextFloat() < 1 / 32f)
            particleAccess.spawnExtraParticle(new DustParticleOptions(colorLight, 1), .125f);
        if (random.nextFloat() < 1 / 48f)
            particleAccess.spawnExtraParticle(ParticleTypes.ASH, .125f);
    }

    @Override
    public void affectEntity(Entity entity, Level level) {
        if (level.isClientSide) {
            if (entity instanceof Skeleton) {
                Vec3 p = entity.getPosition(0);
                Vec3 v = p.add(0, 0.5f, 0)
                        .add(VecHelper.offsetRandomly(Vec3.ZERO, level.random, 1)
                                .multiply(1, 0.2f, 1).normalize().scale(1f));
                level.addParticle(ParticleTypes.ASH, v.x, v.y, v.z, 0, 0.1f, 0);
                if (level.random.nextInt(3) == 0)
                    level.addParticle(ParticleTypes.ASH, p.x, p.y + .5f, p.z,
                            (level.random.nextFloat() - .5f) * .5f, 0.1f,
                            (level.random.nextFloat() - .5f) * .5f);
            }
            return;
        }

        if (entity instanceof LivingEntity livingEntity)
            livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 5, 1, false, false));

        if (entity instanceof WitherBoss witherBoss)
            witherBoss.heal(4);

        if (entity instanceof WitherSkeleton witherSkeleton)
            witherSkeleton.heal(2);

        if (entity instanceof Skeleton skeleton)
            transformEntity(skeleton, EntityType.WITHER_SKELETON,
                    "CreateWithering",
                    SoundEvents.WITHER_SKELETON_AMBIENT, SoundEvents.WITHER_SKELETON_STEP, level);
    }
}
