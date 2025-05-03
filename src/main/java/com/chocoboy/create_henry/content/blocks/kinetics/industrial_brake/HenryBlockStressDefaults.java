//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.chocoboy.create_henry.content.blocks.kinetics.industrial_brake;

import com.simibubi.create.foundation.utility.Couple;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class HenryBlockStressDefaults {
    public static final Map<ResourceLocation, Supplier<Couple<Double>>> IMPACT_PROVIDERS = new HashMap<>();

    public HenryBlockStressDefaults() {
    }

    public static void setImpactProvider(ResourceLocation blockId, Supplier<Couple<Double>> provider) {
        IMPACT_PROVIDERS.put(blockId, provider);
    }

    public static <B extends Block, P> NonNullUnaryOperator<BlockBuilder<B, P>> setImpact(Supplier<Couple<Double>> provider) {
        return b -> {
            ResourceLocation id = new ResourceLocation(b.getOwner().getModid(), b.getName());
            setImpactProvider(id, provider);
            return b;
        };
    }
}
