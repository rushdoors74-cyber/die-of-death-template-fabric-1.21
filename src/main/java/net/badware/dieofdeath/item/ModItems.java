package net.badware.dieofdeath.item;

import net.badware.dieofdeath.DieOfDeath;
import net.badware.dieofdeath.item.advanced.*;
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
            new PursuerCleave(ModToolMaterials.YELLOW_THING, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolMaterials.YELLOW_THING, 3, -0.4f))));

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

public static final Item GRANDMA_BACKYARD_MUSIC_DISC = registerItem("grandma_backyard_music_disc",
        new Item(new Item.Settings().rarity(Rarity.RARE).jukeboxPlayable(ModSounds.GRANDMA_BACKYARD_KEY).maxCount(1)));

public static final Item TEMPLE = registerItem("temple_music_disc",
        new Item(new Item.Settings().rarity(Rarity.RARE).jukeboxPlayable(ModSounds.TEMPLE_KEY).maxCount(1)));

public static final Item ROBLOX_HQ = registerItem("roblox_hq_music_disc",
        new Item(new Item.Settings().rarity(Rarity.RARE).jukeboxPlayable(ModSounds.ROBLOX_HQ_KEY).maxCount(1)));

public static final Item BASEPLATE = registerItem("baseplate_music_disc",
        new Item(new Item.Settings().rarity(Rarity.RARE).jukeboxPlayable(ModSounds.BASEPLATE_KEY).maxCount(1)));

public static final Item TUNDRA_TRENCH = registerItem("tundra_trench_music_disc",
        new Item(new Item.Settings().rarity(Rarity.RARE).jukeboxPlayable(ModSounds.TUNDRA_TRENCH_KEY).maxCount(1)));

public static final Item TEAPOT_PALACE_TOUR_CALM = registerItem("teapot_palace_tour_calm_music_disc",
        new Item(new Item.Settings().rarity(Rarity.RARE).jukeboxPlayable(ModSounds.TEAPOT_PALACE_TOUR_CALM_KEY).maxCount(1)));

public static final Item TEAPOT_PALACE_TOUR_RAIN = registerItem("teapot_palace_tour_rain_music_disc",
        new Item(new Item.Settings().rarity(Rarity.RARE).jukeboxPlayable(ModSounds.TEAPOT_PALACE_TOUR_RAIN_KEY).maxCount(1)));

public static final Item TEAPOT_PALACE_TOUR_LMS = registerItem("teapot_palace_tour_lms_music_disc",
        new Item(new Item.Settings().rarity(Rarity.RARE).jukeboxPlayable(ModSounds.TEAPOT_PALACE_TOUR_LMS_KEY).maxCount(1)));

public static final Item OMEGAS_FINAL_STAND = registerItem("omegas_final_stand_music_disc",
        new Item(new Item.Settings().rarity(Rarity.RARE).jukeboxPlayable(ModSounds.OMEGAS_FINAL_STAND_KEY).maxCount(7)));

public static final Item CARELESS = registerItem("careless_music_disc",
        new Item(new Item.Settings().rarity(Rarity.RARE).jukeboxPlayable(ModSounds.CARELESS_KEY).maxCount(1)));

public static final Item VIGILANTE_SHOOTDOWN = registerItem("vigilante_shootdown_music_disc",
        new Item(new Item.Settings().rarity(Rarity.RARE).jukeboxPlayable(ModSounds.VIGILANTE_SHOOTDOWN_KEY).maxCount(2)));

public static final Item SHOWTIME = registerItem("showtime_music_disc",
        new Item(new Item.Settings().rarity(Rarity.RARE).jukeboxPlayable(ModSounds.SHOWTIME_KEY).maxCount(1)));

public static final Item ETERNITY = registerItem("eternity_music_disc",
        new Item(new Item.Settings().rarity(Rarity.RARE).jukeboxPlayable(ModSounds.ETERNITY_KEY).maxCount(2)));

public static final Item Y2K = registerItem("y2k_music_disc",
        new Item(new Item.Settings().rarity(Rarity.RARE).jukeboxPlayable(ModSounds.Y2K_KEY).maxCount(2)));

public static final Item ONE_BOUNCE = registerItem("one_bounce_music_disc",
        new Item(new Item.Settings().rarity(Rarity.RARE).jukeboxPlayable(ModSounds.ONE_BOUNCE_KEY).maxCount(8)));

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