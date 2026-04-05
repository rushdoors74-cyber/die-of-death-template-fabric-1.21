package net.badware.dieofdeath.enchantment;

import net.badware.dieofdeath.DieOfDeath;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEnchantments {

    public static final RegistryKey<Enchantment> ENTANGLEMENT =
            RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(DieOfDeath.MOD_ID, "entanglement"));

    public static void register() {
    }
}