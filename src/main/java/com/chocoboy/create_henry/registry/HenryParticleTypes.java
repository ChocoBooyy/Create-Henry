package com.chocoboy.create_henry.registry;

import com.simibubi.create.foundation.particle.ICustomParticleData;
import com.chocoboy.create_henry.util.Lang;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import com.chocoboy.create_henry.HenryCreate;
import com.chocoboy.create_henry.content.blocks.kinetics.furnace_engine.SmokeJetParticleData;

import java.util.function.Supplier;

public enum HenryParticleTypes {

    SMOKE_JET(SmokeJetParticleData::new);

    private final ParticleEntry<?> entry;

    <D extends ParticleOptions> HenryParticleTypes(Supplier<? extends ICustomParticleData<D>> typeFactory) {
        String name = Lang.asId(name());
        entry = new ParticleEntry<>(name, typeFactory);
    }

    public static void register() {
        for (HenryParticleTypes ignored : values()) {
            // Touching the enum forces eager registration through the constructors.
        }
    }

    @Environment(EnvType.CLIENT)
    public static void registerFactories() {
        ParticleEngine particleEngine = Minecraft.getInstance().particleEngine;
        for (HenryParticleTypes particle : values())
            particle.entry.registerFactory(particleEngine);
    }

    public ParticleType<?> get() {
        return entry.object;
    }

    public String parameter() {
        return entry.name;
    }

    private static class ParticleEntry<D extends ParticleOptions> {

        private final String name;
        private final Supplier<? extends ICustomParticleData<D>> typeFactory;
        private final ParticleType<D> object;

        public ParticleEntry(String name, Supplier<? extends ICustomParticleData<D>> typeFactory) {
            this.name = name;
            this.typeFactory = typeFactory;

            object = Registry.register(BuiltInRegistries.PARTICLE_TYPE,
                    HenryCreate.asResource(name), typeFactory.get().createType());
        }

        @Environment(EnvType.CLIENT)
        public void registerFactory(ParticleEngine particleEngine) {
            typeFactory.get()
                    .register(object, particleEngine);
        }

    }

}
