package net.badware.dieofdeath.item;

import net.badware.dieofdeath.DieOfDeath;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {
    public static final  RegistryEntry<ArmorMaterial> BLOCK_ARMOR = registerArmorMaterial("block", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 0);
        map.put(ArmorItem.Type.LEGGINGS, 0);
        map.put(ArmorItem.Type.CHESTPLATE, 0);
        map.put(ArmorItem.Type.HELMET, 0);
        map.put(ArmorItem.Type.BODY, 0);
    }), 0, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, () -> Ingredient.ofItems(ModItems.BLOCK_CHESTPLATE),
            List.of(new ArmorMaterial.Layer(Identifier.of(DieOfDeath.MOD_ID, "block"))), 0, 100.0f));


    public static RegistryEntry<ArmorMaterial> registerArmorMaterial(String name, Supplier<ArmorMaterial> material) {
        return Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(DieOfDeath.MOD_ID, name), material.get());
    }
}
