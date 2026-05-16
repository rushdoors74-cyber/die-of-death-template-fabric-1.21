package net.badware.dieofdeath.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {
    public static final SimpleParticleType GREEN_STAR = FabricParticleTypes.simple();
    public static final SimpleParticleType LOVE_HEART = FabricParticleTypes.simple();

    public static void registerParticles() {
        Registry.register(Registries.PARTICLE_TYPE,
                Identifier.of("dieofdeath", "green_star"), GREEN_STAR);

        Registry.register(Registries.PARTICLE_TYPE,
                Identifier.of("dieofdeath", "love_heart"), LOVE_HEART);
    }
}