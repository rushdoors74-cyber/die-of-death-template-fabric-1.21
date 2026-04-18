package net.badware.dieofdeath.datagen;

import net.badware.dieofdeath.enchantment.ModEnchantments;
import net.badware.dieofdeath.enchantment.custom.EntanglementEnchantmentEffect;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModEnchantmentGenerator extends FabricDynamicRegistryProvider {
    public ModEnchantmentGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup, Entries entries) {
        var itemLookup = wrapperLookup.getWrapperOrThrow(RegistryKeys.ITEM);

        Enchantment.Builder builder = Enchantment.builder(Enchantment.definition(
                itemLookup.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                itemLookup.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                4,
                6,
                Enchantment.leveledCost(10, 6),
                Enchantment.leveledCost(20, 6),
                2,
                AttributeModifierSlot.MAINHAND
        ));

        builder.addEffect(
                EnchantmentEffectComponentTypes.POST_ATTACK,
                EnchantmentEffectTarget.ATTACKER,
                EnchantmentEffectTarget.VICTIM,
                new EntanglementEnchantmentEffect()
        );

        entries.add(ModEnchantments.ENTANGLEMENT, builder.build(ModEnchantments.ENTANGLEMENT.getValue()));
    }

    @Override
    public String getName() {
        return "Enchantment Generator";
    }
}