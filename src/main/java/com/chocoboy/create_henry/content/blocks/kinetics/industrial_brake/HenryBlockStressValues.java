package com.chocoboy.create_henry.content.blocks.kinetics.industrial_brake;

import com.simibubi.create.api.registry.SimpleRegistry;
import com.tterrag.registrate.util.nullness.NonNullConsumer;

import net.minecraft.world.level.block.Block;

/**
 * Registry for custom stress impacts, capacities, and taken SU for braking blocks.
 * Mirrors Create's BlockStressValues but for negative (brake) mechanics.
 */
public class HenryBlockStressValues {
    /**
     * Registry for fixed SU taken per network update (brake block), independent of RPM.
     */
    public static final SimpleRegistry<Block, TakenSU> TAKEN_SU = SimpleRegistry.create();

    /**
     * Register a fixed SU taken per update (brake block) with optional variability.
     * @param value SU taken per update
     * @param mayTakeLess if true, actual SU taken may be less if network stress is low
     */
    public static NonNullConsumer<Block> setTakenSU(int value, boolean mayTakeLess) {
        return block -> TAKEN_SU.register(block, new TakenSU(value, mayTakeLess));
    }

    /**
     * Data holder for fixed SU taken logic.
     */
    public record TakenSU(int value, boolean mayTakeLess) {}
}
