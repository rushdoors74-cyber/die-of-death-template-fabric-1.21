package net.badware.dieofdeath.enchantment;

import net.badware.dieofdeath.DieOfDeath;
import net.badware.dieofdeath.enchantment.custom.EntanglementEnchantmentEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantmentEffects {

    public static void register() {
        Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE,
                Identifier.of(DieOfDeath.MOD_ID, "entanglement"),
                EntanglementEnchantmentEffect.CODEC);
    }
}
