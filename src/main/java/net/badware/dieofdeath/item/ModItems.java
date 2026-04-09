package net.badware.dieofdeath.item;

import net.badware.dieofdeath.DieOfDeath;
import net.badware.dieofdeath.item.advanced.ArtfulWand;
import net.badware.dieofdeath.item.advanced.HammerItem;
import net.badware.dieofdeath.item.advanced.ModArmorItem;
import net.badware.dieofdeath.item.advanced.ModToolMaterials;
import net.badware.dieofdeath.sound.ModSounds;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
    public static final Item WALL_BRICK = registerItem("wall_brick", new Item(new Item.Settings()));
    public static final Item BONUSPAD_SHARD = registerItem("bonuspad_shard", new Item(new Item.Settings().fireproof()));
    public static final Item BOX_CHAIN = registerItem("box_chain", new Item(new Item.Settings()));
    public static final Item CEMENT = registerItem("cement", new Item(new Item.Settings()));

    public static final Item ARTFUL_WAND = registerItem("artful_wand", new ArtfulWand(new Item.Settings().maxDamage(32)));

    public static final Item HOTDOG = registerItem("hotdog", new Item(new Item.Settings().food(ModFoodComponents.HOTDOG)));

    public static final Item BOILING_WATER = registerItem("boiling_water", new Item(new Item.Settings()));

    public static final Item PURSUER_CLEAVE = registerItem("pursuer_cleave",
            new SwordItem(ModToolMaterials.YELLOW_THING, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolMaterials.YELLOW_THING, 3, -0.4f))));

public static final Item YELLOW_THING = registerItem("yellow_thing",
            new PickaxeItem(ModToolMaterials.YELLOW_THING, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.YELLOW_THING, 2, -2.4f))));

public static final Item BLUE_AXE = registerItem("blue_axe",
            new AxeItem(ModToolMaterials.YELLOW_THING, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.YELLOW_THING, 5, -1.4f))));

public static final Item SHOVEL_STUFF = registerItem("shovel_stuff",
            new ShovelItem(ModToolMaterials.YELLOW_THING, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.YELLOW_THING, 1, -4.666666666667f))));

public static final Item WEIRD_HOE = registerItem("weird_hoe",
            new HoeItem(ModToolMaterials.YELLOW_THING, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.YELLOW_THING, 4, -0.7f))));

public static final Item IRON_HAMMER = registerItem("iron_hammer",
            new HammerItem(ModToolMaterials.YELLOW_THING, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.YELLOW_THING, 6, -3.3333333f))));

public static final Item BLOCK_HELMET = registerItem("block_helmet",
            new ModArmorItem(ModArmorMaterials.BLOCK_ARMOR, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(1)));

public static final Item BLOCK_CHESTPLATE = registerItem("block_chestplate",
            new ModArmorItem(ModArmorMaterials.BLOCK_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(1)));

public static final Item BLOCK_LEGGINGS = registerItem("block_leggings",
            new ModArmorItem(ModArmorMaterials.BLOCK_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(1)));

public static final Item BLOCK_BOOTS = registerItem("block_boots",
            new ModArmorItem(ModArmorMaterials.BLOCK_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(1)));

public static final Item HARKEN_BOW = registerItem("harken_bow",
         new BowItem(new Item.Settings().maxDamage(2486)));

public static final Item ETERNITY_V2_MUSIC_DISC = registerItem("eternity_v2_music_disc",
        new Item(new Item.Settings().rarity(Rarity.RARE).jukeboxPlayable(ModSounds.ETERNITY_V2_KEY).maxCount(1)));

public static final Item BONUSPAD_INGOT = registerItem("bonuspad_ingot",
        new Item(new Item.Settings().fireproof()));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(DieOfDeath.MOD_ID, name), item);
    }

    public static void registerModItems() {
        DieOfDeath.LOGGER.info("Registering Mod Items for " + DieOfDeath.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(WALL_BRICK);
            fabricItemGroupEntries.add(BONUSPAD_SHARD);
            fabricItemGroupEntries.add(BOX_CHAIN);
            fabricItemGroupEntries.add(CEMENT);
        });
    }
}