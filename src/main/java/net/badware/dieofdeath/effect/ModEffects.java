package net.badware.dieofdeath.effect;

import net.badware.dieofdeath.DieOfDeath;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static final RegistryEntry<StatusEffect> TANGLED = register("tangled", new TangledEffect());

    private static RegistryEntry<StatusEffect> register(String name, StatusEffect effect) {
        return Registry.registerReference(Registries.STATUS_EFFECT,
                Identifier.of("dieofdeath", name), effect);
    }

    public static void registerEffects() {
        DieOfDeath.LOGGER.info("Registering Mod Effects for " + DieOfDeath.MOD_ID);
    }
}