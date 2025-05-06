package com.chocoboy.create_henry.registry;

import com.chocoboy.create_henry.content.recipes.*;
import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.trains.CubeParticleData;
import com.simibubi.create.foundation.recipe.RecipeApplier;
import it.unimi.dsi.fastutil.objects.Object2ReferenceOpenHashMap;
import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.theme.Color;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import com.chocoboy.create_henry.HenryCreate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HenryFanProcessingTypes {
    public static final SandingType SANDING = register("sanding", new SandingType());
    public static final FreezingType FREEZING = register("freezing", new FreezingType());
    public static final SeethingType SEETHING = register("seething", new SeethingType());
    public static final WitheringType WITHERING = register("withering", new WitheringType());
    public static final DragonBreathingType DRAGON_BREATHING = register("dragon_breathing", new DragonBreathingType());

    private static final Map<String, FanProcessingType> LEGACY_NAME_MAP;

    static {
        Object2ReferenceOpenHashMap<String, FanProcessingType> map = new Object2ReferenceOpenHashMap<>();
        map.put("SANDING", SANDING);
        map.put("FREEZING", FREEZING);
        map.put("SEETHING", SEETHING);
        map.put("WITHERING", WITHERING);
        map.put("DRAGON_BREATHING", DRAGON_BREATHING);
        map.trim();
        LEGACY_NAME_MAP = map;
    }

    private static <T extends FanProcessingType> T register(String name, T type) {
        return Registry.register(CreateBuiltInRegistries.FAN_PROCESSING_TYPE, HenryCreate.asResource(name), type);
    }

    @Nullable
    public static FanProcessingType ofLegacyName(String name) {
        return LEGACY_NAME_MAP.get(name);
    }

    @Nullable
    public static FanProcessingType parseLegacy(String str) {
        FanProcessingType type = ofLegacyName(str);
        if (type != null) {
            return type;
        }
        return FanProcessingType.parse(str);
    }

    public static void init() {
    }

    public static class SandingType implements FanProcessingType {
        private static final SandingRecipe.SandingWrapper SANDING_WRAPPER = new SandingRecipe.SandingWrapper();

        @Override
        public boolean isValidAt(Level level, BlockPos pos) {
            FluidState fluidState = level.getFluidState(pos);
            if (HenryTags.AllFluidTags.FAN_PROCESSING_CATALYSTS_SANDING.matches(fluidState)) {
                return true;
            }
            BlockState blockState = level.getBlockState(pos);
            return HenryTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_SANDING.matches(blockState);
        }

        @Override
        public int getPriority() {
            return 691000;
        }

        @Override
        public boolean canProcess(ItemStack stack, Level level) {

            SANDING_WRAPPER.setItem(0, stack);
            Optional<SandingRecipe> recipe = HenryRecipeTypes.SANDING.find(SANDING_WRAPPER, level);
            return recipe.isPresent();
        }

        @Override
        @Nullable
        public List<ItemStack> process(ItemStack stack, Level level) {
            SANDING_WRAPPER.setItem(0, stack);
            Optional<SandingRecipe> recipe = HenryRecipeTypes.SANDING.find(SANDING_WRAPPER, level);
            return recipe.map(sandingRecipe -> RecipeApplier.applyRecipeOn(level, stack, sandingRecipe)).orElse(null);
        }

        @Override
        public void spawnProcessingParticles(Level level, Vec3 pos) {
            if (level.random.nextInt(8) != 0)
                return;
            Vector3f color1 = new Color(0xEDEBCB).asVectorF();
            Vector3f color2 = new Color(0xE7E4BB).asVectorF();
            level.addParticle(new DustParticleOptions(color1, 1), pos.x + (level.random.nextFloat() - .5f) * .5f,
                    pos.y + .5f, pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
            level.addParticle(new DustParticleOptions(color2, 1), pos.x + (level.random.nextFloat() - .5f) * .5f,
                    pos.y + .5f, pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
            level.addParticle(new DustParticleOptions(color1, 1), pos.x + (level.random.nextFloat() - .5f) * .5f,
                    pos.y + .5f, pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
            level.addParticle(ParticleTypes.CRIT, pos.x + (level.random.nextFloat() - .5f) * .5f, pos.y + .5f,
                    pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
        }

        @Override
        public void morphAirFlow(AirFlowParticleAccess particleAccess, RandomSource random) {
            particleAccess.setColor(Color.mixColors(0xE7E4BB, 0xEDEBCB, random.nextFloat()));
            particleAccess.setAlpha(1f);
            if (random.nextFloat() < 1 / 128f)
                particleAccess.spawnExtraParticle(ParticleTypes.CRIT, .125f);
            if (random.nextFloat() < 1 / 32f)
                particleAccess.spawnExtraParticle(ParticleTypes.WHITE_ASH, .125f);
        }

        @Override
        public void affectEntity(Entity entity, Level level) {

            if (entity instanceof Zombie zombie && !(entity instanceof Husk)) {
                int progress = zombie.getPersistentData()
                        .getInt("CreateSanding");
                if (progress < 50) {
                    if (progress % 10 == 0) {
                        level.playSound(null, entity.blockPosition(), SoundEvents.HUSK_AMBIENT, SoundSource.NEUTRAL,
                                1f, 1.5f * progress / 50f);
                    }
                    zombie.getPersistentData()
                            .putInt("CreateSanding", progress + 1);
                    return;
                }

                level.playSound(null, entity.blockPosition(), SoundEvents.HUSK_CONVERTED_TO_ZOMBIE,
                        SoundSource.NEUTRAL, 1.25f, 0.65f);

                Husk husk = EntityType.HUSK.create(level);
                CompoundTag serializeNBT = zombie.saveWithoutId(new CompoundTag());
                serializeNBT.remove("UUID");

                assert husk != null;
                husk.deserializeNBT(serializeNBT);
                husk.setPos(zombie.getPosition(0));
                level.addFreshEntity(husk);
                zombie.discard();
            }
        }
    }

    public static class SeethingType implements FanProcessingType {
        private static final SeethingRecipe.SeethingWrapper SEETHING_WRAPPER = new SeethingRecipe.SeethingWrapper();

        @Override
        public boolean isValidAt(Level level, BlockPos pos) {
            FluidState fluidState = level.getFluidState(pos);
            if (HenryTags.AllFluidTags.FAN_PROCESSING_CATALYSTS_SEETHING.matches(fluidState)) {
                return true;
            }
            BlockState blockState = level.getBlockState(pos);
            if (HenryTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_SEETHING.matches(blockState)) {
                return !blockState.hasProperty(BlazeBurnerBlock.HEAT_LEVEL) || blockState.getValue(BlazeBurnerBlock.HEAT_LEVEL).isAtLeast(BlazeBurnerBlock.HeatLevel.SEETHING);
            }
            return false;
        }

        @Override
        public int getPriority() {
            return 691200;
        }

        @Override
        public boolean canProcess(ItemStack stack, Level level) {
            SEETHING_WRAPPER.setItem(0, stack);
            Optional<SeethingRecipe> recipe = HenryRecipeTypes.SEETHING.find(SEETHING_WRAPPER, level);
            return recipe.isPresent();
        }

        @Override
        @Nullable
        public List<ItemStack> process(ItemStack stack, Level level) {
            SEETHING_WRAPPER.setItem(0, stack);
            Optional<SeethingRecipe> recipe = HenryRecipeTypes.SEETHING.find(SEETHING_WRAPPER, level);
            return recipe.map(seethingRecipe -> RecipeApplier.applyRecipeOn(level, stack, seethingRecipe)).orElse(null);
        }

        @Override
        public void spawnProcessingParticles(Level level, Vec3 pos) {
            if (level.random.nextInt(8) != 0)
                return;
            Vector3f color = new Color(0x1e0f3d).asVectorF();
            level.addParticle(new DustParticleOptions(color, 1), pos.x + (level.random.nextFloat() - .5f) * .5f,
                    pos.y + .5f, pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
            level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.x, pos.y + .45f, pos.z, 0, 0, 0);
            level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.x + (level.random.nextFloat() - .5f) * .5f, pos.y + .5f,
                    pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
        }

        @Override
        public void morphAirFlow(AirFlowParticleAccess particleAccess, RandomSource random) {
            particleAccess.setColor(Color.mixColors(0x64C9FD, 0x3f74e8, random.nextFloat()));
            particleAccess.setAlpha(1f);
            if (random.nextFloat() < 1 / 32f)
                particleAccess.spawnExtraParticle(ParticleTypes.SOUL_FIRE_FLAME,  .125f);
            Vector3f colorBright = new Color(0x64C9FD).asVectorF();
            Vector3f colorDark = new Color(0x3f74e8).asVectorF();
            if (random.nextFloat() < 1 / 32f)
                particleAccess.spawnExtraParticle((new DustParticleOptions(colorBright, 1)), .125f);
            if (random.nextFloat() < 1 / 32f)
                particleAccess.spawnExtraParticle((new DustParticleOptions(colorDark, 1)), .125f);
            if (random.nextFloat() < 1 / 48f)
                particleAccess.spawnExtraParticle(ParticleTypes.SMOKE, .125f);
            if (random.nextFloat() < 1 / 32f)
                particleAccess.spawnExtraParticle((new CubeParticleData(192, 122, 85, 0.075f, 10, true)), .125f);
            if (random.nextFloat() < 1 / 32f)
                particleAccess.spawnExtraParticle((new CubeParticleData(191, 82, 91, 0.1f, 10, true)), .125f);
        }

        @Override
        public void affectEntity(Entity entity, Level level) {
            if (level.isClientSide)
                return;

            if (entity instanceof Blaze blaze) {
                blaze.heal(4);
            }

            if (!entity.fireImmune()) {
                entity.setSecondsOnFire(10);
                entity.hurt(level.damageSources().lava(), 10);
            }

            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 2, false, false));
                livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 1, false, false));
                livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 30, 0, false, false));
            }

        }
    }

    public static class FreezingType implements FanProcessingType {
        private static final FreezingRecipe.FreezingWrapper FREEZING_WRAPPER = new FreezingRecipe.FreezingWrapper();

        @Override
        public boolean isValidAt(Level level, BlockPos pos) {
            FluidState fluidState = level.getFluidState(pos);
            if (HenryTags.AllFluidTags.FAN_PROCESSING_CATALYSTS_FREEZING.matches(fluidState)) {
                return true;
            }
            BlockState blockState = level.getBlockState(pos);
            return HenryTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_FREEZING.matches(blockState);
        }

        @Override
        public int getPriority() {
            return 691100;
        }

        @Override
        public boolean canProcess(ItemStack stack, Level level) {
            FREEZING_WRAPPER.setItem(0, stack);
            Optional<FreezingRecipe> recipe = HenryRecipeTypes.FREEZING.find(FREEZING_WRAPPER, level);
            return recipe.isPresent();
        }

        @Override
        @Nullable
        public List<ItemStack> process(ItemStack stack, Level level) {
            FREEZING_WRAPPER.setItem(0, stack);
            Optional<FreezingRecipe> recipe = HenryRecipeTypes.FREEZING.find(FREEZING_WRAPPER, level);
            return recipe.map(freezingRecipe -> RecipeApplier.applyRecipeOn(level, stack, freezingRecipe)).orElse(null);
        }

        @Override
        public void spawnProcessingParticles(Level level, Vec3 pos) {
            if (level.random.nextInt(8) != 0)
                return;
            Vector3f color = new Color(0xDDE8FF).asVectorF();
            level.addParticle(new DustParticleOptions(color, 1), pos.x + (level.random.nextFloat() - .5f) * .5f,
                    pos.y + .5f, pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
            level.addParticle(ParticleTypes.SNOWFLAKE, pos.x + (level.random.nextFloat() - .5f) * .5f, pos.y + .5f,
                    pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
        }

        @Override
        public void morphAirFlow(AirFlowParticleAccess particleAccess, RandomSource random) {
            particleAccess.setColor(Color.mixColors(0xEEEEFF, 0xDDE8FF, random.nextFloat()));
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
                                    .multiply(1, 0.2f, 1)
                                    .normalize()
                                    .scale(1f));
                    level.addParticle(ParticleTypes.SNOWFLAKE, v.x, v.y, v.z, 0, 0.1f, 0);
                    if (level.random.nextInt(3) == 0)
                        level.addParticle(ParticleTypes.SNOWFLAKE, p.x, p.y + .5f, p.z,
                                (level.random.nextFloat() - .5f) * .5f, 0.1f, (level.random.nextFloat() - .5f) * .5f);
                }
                return;
            }

            if (entity instanceof EnderMan || entity.getType() == EntityType.BLAZE) {
                entity.hurt(level.damageSources().freeze(), 8);
            }

            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 7, false, false));
            }

            if (entity instanceof SnowGolem snowgolem) {
                snowgolem.heal(4);
            }

            if (entity instanceof Stray stray) {
                stray.heal(2);
            }

            if (entity.isOnFire()) {
                entity.clearFire();
                level.playSound(null, entity.blockPosition(), SoundEvents.GENERIC_EXTINGUISH_FIRE,
                        SoundSource.NEUTRAL, 0.7F, 1.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.4F);
            }

            if (entity instanceof Skeleton skeleton) {
                int progress = skeleton.getPersistentData()
                        .getInt("CreateFreezing");
                if (progress < 50) {
                    if (progress % 10 == 0) {
                        level.playSound(null, entity.blockPosition(), SoundEvents.STRAY_AMBIENT, SoundSource.NEUTRAL,
                                1f, 1.5f * progress / 50f);
                    }
                    skeleton.getPersistentData()
                            .putInt("CreateFreezing", progress + 1);
                    return;
                }

                level.playSound(null, entity.blockPosition(), SoundEvents.SKELETON_CONVERTED_TO_STRAY,
                        SoundSource.NEUTRAL, 1.25f, 0.65f);

                Stray stray = EntityType.STRAY.create(level);
                CompoundTag serializeNBT = skeleton.saveWithoutId(new CompoundTag());
                serializeNBT.remove("UUID");

                assert stray != null;
                stray.deserializeNBT(serializeNBT);
                stray.setPos(skeleton.getPosition(0));
                level.addFreshEntity(stray);
                skeleton.discard();
            }
        }
    }

    public static class WitheringType implements FanProcessingType {
        private static final WitheringRecipe.WitheringWrapper WITHERING_WRAPPER = new WitheringRecipe.WitheringWrapper();

        @Override
        public boolean isValidAt(Level level, BlockPos pos) {
            FluidState fluidState = level.getFluidState(pos);
            if (HenryTags.AllFluidTags.FAN_PROCESSING_CATALYSTS_WITHERING.matches(fluidState)) {
                return true;
            }
            BlockState blockState = level.getBlockState(pos);
            return HenryTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_WITHERING.matches(blockState);
        }

        @Override
        public int getPriority() {
            return 691300;
        }

        @Override
        public boolean canProcess(ItemStack stack, Level level) {
            WITHERING_WRAPPER.setItem(0, stack);
            Optional<WitheringRecipe> recipe = HenryRecipeTypes.WITHERING.find(WITHERING_WRAPPER, level);
            return recipe.isPresent();
        }

        @Override
        @Nullable
        public List<ItemStack> process(ItemStack stack, Level level) {
            WITHERING_WRAPPER.setItem(0, stack);
            Optional<WitheringRecipe> recipe = HenryRecipeTypes.WITHERING.find(WITHERING_WRAPPER, level);
            return recipe.map(witheringRecipe -> RecipeApplier.applyRecipeOn(level, stack, witheringRecipe)).orElse(null);
        }

        @Override
        public void spawnProcessingParticles(Level level, Vec3 pos) {
            if (level.random.nextInt(8) != 0)
                return;
            Vector3f color = new Color(0x1e0f3d).asVectorF();
            level.addParticle(new DustParticleOptions(color, 1), pos.x + (level.random.nextFloat() - .5f) * .5f,
                    pos.y + .5f, pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
            level.addParticle(ParticleTypes.SOUL, pos.x, pos.y + .45f, pos.z, 0, 0, 0);
            level.addParticle(ParticleTypes.SOUL, pos.x + (level.random.nextFloat() - .5f) * .5f, pos.y + .5f,
                    pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
        }

        @Override
        public void morphAirFlow(AirFlowParticleAccess particleAccess, RandomSource random) {
            particleAccess.setColor(Color.mixColors(0x180c30, 0x1e0f3d, random.nextFloat()));
            particleAccess.setAlpha(1f);
            if (random.nextFloat() < 1 / 32f)
                particleAccess.spawnExtraParticle(ParticleTypes.SOUL,  .125f);
            Vector3f colorBright = new Color(0x180c30).asVectorF();
            Vector3f colorDark = new Color(0x1e0f3d).asVectorF();
            if (random.nextFloat() < 1 / 32f)
                particleAccess.spawnExtraParticle((new DustParticleOptions(colorBright, 1)), .125f);
            if (random.nextFloat() < 1 / 32f)
                particleAccess.spawnExtraParticle((new DustParticleOptions(colorDark, 1)), .125f);
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
                                    .multiply(1, 0.2f, 1)
                                    .normalize()
                                    .scale(1f));
                    level.addParticle(ParticleTypes.ASH, v.x, v.y, v.z, 0, 0.1f, 0);
                    if (level.random.nextInt(3) == 0)
                        level.addParticle(ParticleTypes.ASH, p.x, p.y + .5f, p.z,
                                (level.random.nextFloat() - .5f) * .5f, 0.1f, (level.random.nextFloat() - .5f) * .5f);
                }
                return;
            }

            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 5, 1, false, false));
            }

            if (entity instanceof WitherBoss witherBoss) {
                witherBoss.heal(4);
            }

            if (entity instanceof WitherSkeleton witherSkeleton) {
                witherSkeleton.heal(2);
            }

            if (entity instanceof Skeleton skeleton) {
                int progress = skeleton.getPersistentData()
                        .getInt("CreateWithering");
                if (progress < 50) {
                    if (progress % 10 == 0) {
                        level.playSound(null, entity.blockPosition(), SoundEvents.WITHER_SKELETON_AMBIENT, SoundSource.NEUTRAL,
                                1f, 1.5f * progress / 50f);
                    }
                    skeleton.getPersistentData()
                            .putInt("CreateWithering", progress + 1);
                    return;
                }

                level.playSound(null, entity.blockPosition(), SoundEvents.WITHER_SKELETON_STEP,
                        SoundSource.NEUTRAL, 1.25f, 0.65f);

                WitherSkeleton witherSkeleton = EntityType.WITHER_SKELETON.create(level);
                CompoundTag serializeNBT = skeleton.saveWithoutId(new CompoundTag());
                serializeNBT.remove("UUID");

                assert witherSkeleton != null;
                witherSkeleton.deserializeNBT(serializeNBT);
                witherSkeleton.setPos(skeleton.getPosition(0));
                level.addFreshEntity(witherSkeleton);
                skeleton.discard();
            }
        }
    }

    public static class DragonBreathingType implements FanProcessingType {
        private static final DragonBreathingRecipe.dragonBreathingWrapper DRAGON_BREATHING_WRAPPER = new DragonBreathingRecipe.dragonBreathingWrapper();

        @Override
        public boolean isValidAt(Level level, BlockPos pos) {
            FluidState fluidState = level.getFluidState(pos);
            if (HenryTags.AllFluidTags.FAN_PROCESSING_CATALYSTS_DRAGON_BREATHING.matches(fluidState)) {
                return true;
            }
            BlockState blockState = level.getBlockState(pos);
            return HenryTags.AllBlockTags.FAN_PROCESSING_CATALYSTS_DRAGON_BREATHING.matches(blockState);
        }

        @Override
        public int getPriority() {
            return 691400;
        }

        @Override
        public boolean canProcess(ItemStack stack, Level level) {
            DRAGON_BREATHING_WRAPPER.setItem(0, stack);
            Optional<DragonBreathingRecipe> recipe = HenryRecipeTypes.DRAGON_BREATHING.find(DRAGON_BREATHING_WRAPPER, level);
            return recipe.isPresent();
        }

        @Override
        @Nullable
        public List<ItemStack> process(ItemStack stack, Level level) {
            DRAGON_BREATHING_WRAPPER.setItem(0, stack);
            Optional<DragonBreathingRecipe> recipe = HenryRecipeTypes.DRAGON_BREATHING.find(DRAGON_BREATHING_WRAPPER, level);
            return recipe.map(dragonBreathingRecipe -> RecipeApplier.applyRecipeOn(level, stack, dragonBreathingRecipe)).orElse(null);
        }

        @Override
        public void spawnProcessingParticles(Level level, Vec3 pos) {
            if (level.random.nextInt(8) != 0)
                return;
            level.addParticle(ParticleTypes.DRAGON_BREATH, pos.x + (level.random.nextFloat() - .5f) * .5f, pos.y + .5f,
                    pos.z + (level.random.nextFloat() - .5f) * .5f, 0, 1 / 8f, 0);
        }

        @Override
        public void morphAirFlow(AirFlowParticleAccess particleAccess, RandomSource random) {
            particleAccess.setColor(Color.mixColors(0xD36FD9, 0xC21BF5, random.nextFloat()));
            particleAccess.setAlpha(1f);
            if (random.nextFloat() < 1 / 128f)
                particleAccess.spawnExtraParticle(ParticleTypes.DRAGON_BREATH, .125f);
            if (random.nextFloat() < 1 / 32f)
                particleAccess.spawnExtraParticle(ParticleTypes.WITCH, .125f);

            float r =  0.2f;
            float g =  0.08f;
            float b =  0.3f;

            if (random.nextFloat() < 1/32f)
                particleAccess.spawnExtraParticle(
                        new CubeParticleData(r, g, b,
                                0.075f,
                                12,
                                false),
                        0.125f);

            // bigger cube
            if (random.nextFloat() < 1/32f)
                particleAccess.spawnExtraParticle(
                        new CubeParticleData(r, g, b,
                                0.1f,
                                14,
                                false),
                        0.125f);
        }

        @Override
        public void affectEntity(Entity entity, Level level) {

            if (entity instanceof Silverfish silverfish) {
                int progress = silverfish.getPersistentData()
                        .getInt("CreateBreathing");
                if (progress < 50) {
                    if (progress % 10 == 0) {
                        level.playSound(null, entity.blockPosition(), SoundEvents.ENDERMITE_AMBIENT, SoundSource.NEUTRAL,
                                0.5f, 1.5f * progress / 50f);
                    }
                    silverfish.getPersistentData()
                            .putInt("CreateBreathing", progress + 1);
                    return;
                }

                level.playSound(null, entity.blockPosition(), SoundEvents.ENDERMAN_SCREAM,
                        SoundSource.NEUTRAL, 1.25f, 0.65f);

                Endermite endermite = EntityType.ENDERMITE.create(level);
                CompoundTag serializeNBT = silverfish.saveWithoutId(new CompoundTag());
                serializeNBT.remove("UUID");

                assert endermite != null;
                endermite.deserializeNBT(serializeNBT);
                endermite.setPos(silverfish.getPosition(0));
                level.addFreshEntity(endermite);
                silverfish.discard();
            } else if (entity instanceof WitherSkeleton witherSkeleton) {

                int progress = witherSkeleton.getPersistentData()
                        .getInt("CreateBreathing");
                if (progress < 50) {
                    if (progress % 10 == 0) {
                        level.playSound(null, entity.blockPosition(), SoundEvents.ENDERMAN_SCREAM, SoundSource.NEUTRAL,
                                1f, 1.5f * progress / 50f);
                    }
                    witherSkeleton.getPersistentData()
                            .putInt("CreateBreathing", progress + 1);
                    return;
                }

                level.playSound(null, entity.blockPosition(), SoundEvents.ENDERMAN_STARE,
                        SoundSource.NEUTRAL, 1f, 0.65f);

                EnderMan enderMan = EntityType.ENDERMAN.create(level);
                CompoundTag serializeNBT = witherSkeleton.saveWithoutId(new CompoundTag());
                serializeNBT.remove("UUID");

                assert enderMan != null;
                enderMan.deserializeNBT(serializeNBT);
                enderMan.setPos(witherSkeleton.getPosition(0));
                level.addFreshEntity(enderMan);
                witherSkeleton.discard();

            } else if (entity instanceof LivingEntity livingEntity && !(entity instanceof EnderMan) && !(entity instanceof Endermite) && !(entity instanceof EnderDragon)) {
                // current position
                double x = livingEntity.getX();
                double y = livingEntity.getY();
                double z = livingEntity.getZ();

                // random offsets
                RandomSource rand = level.random;
                double dx = (rand.nextDouble() * 5) - 5;
                double dz = (rand.nextDouble() * 5) - 5;
                double dy = (rand.nextDouble() * 2) - 2;

                double newX = x + dx;
                double newY = Math.max(0, y + dy);         // ensure non-negative
                double newZ = z + dz;

                // teleport the entity
                livingEntity.teleportTo(newX, newY, newZ);

                // play the enderman teleport sound
                level.playSound(null, newX, newY, newZ,
                        SoundEvents.ENDERMAN_TELEPORT, SoundSource.NEUTRAL,
                        1.0f, 1.0f + (float) (rand.nextGaussian() * 0.05));

                // spawn a few portal particles at the new location
                for (int i = 0; i < 10; i++) {
                    double px = newX + (rand.nextDouble() - 0.5) * 2;
                    double py = newY + rand.nextDouble() * 2;
                    double pz = newZ + (rand.nextDouble() - 0.5) * 2;
                    level.addParticle(ParticleTypes.PORTAL, px, py, pz,
                            (rand.nextDouble() - 0.5) * 2,
                            (rand.nextDouble() - 0.5) * 2,
                            (rand.nextDouble() - 0.5) * 2);
                }
            }

        }
    }

}
