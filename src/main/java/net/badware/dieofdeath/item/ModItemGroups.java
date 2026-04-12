package net.badware.dieofdeath.item;

import net.badware.dieofdeath.DieOfDeath;
import net.badware.dieofdeath.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup Die_Of_Death = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(DieOfDeath.MOD_ID, "die_of_death"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.BONUSPAD_SHARD))
                    .displayName(Text.translatable("itemgroup.dieofdeath.die_of_death"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.WALL_BRICK);
                        entries.add(ModItems.BONUSPAD_SHARD);
                        entries.add(ModItems.BOX_CHAIN);
                        entries.add(ModItems.CEMENT);
                        entries.add(ModItems.BONUSPAD_INGOT);

                        entries.add(ModItems.ARTFUL_WAND);

                        entries.add(ModItems.HOTDOG);

                        entries.add(ModItems.BOILING_WATER);

                        entries.add(ModItems.PURSUER_CLEAVE);
                        entries.add(ModItems.BLUE_AXE);
                        entries.add(ModItems.YELLOW_THING);
                        entries.add(ModItems.SHOVEL_STUFF);
                        entries.add(ModItems.WEIRD_HOE);

                        entries.add(ModItems.IRON_HAMMER);

                        entries.add(ModItems.BLOCK_HELMET);
                        entries.add(ModItems.BLOCK_CHESTPLATE);
                        entries.add(ModItems.BLOCK_LEGGINGS);
                        entries.add(ModItems.BLOCK_BOOTS);

                        entries.add(ModItems.HARKEN_BOW);

                        entries.add(ModItems.CAREPAD_INGOT);

                    }).build());

    public static final ItemGroup Die_Of_Death_Blocks = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(DieOfDeath.MOD_ID, "die_of_death_blocks"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModBlocks.IMPLEMENT_WALL))
                    .displayName(Text.translatable("itemgroup.dieofdeath.die_of_death_blocks"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModBlocks.KILLER_ONLY_BLOCK);
                        entries.add(ModBlocks.IMPLEMENT_WALL);
                        entries.add(ModBlocks.WOOD_BOX);
                        entries.add(ModBlocks.TEST_RAIL);
                        entries.add(ModBlocks.TRAPDOOR_0);
                        entries.add(ModBlocks.BUTTON_0);
                        entries.add(ModBlocks.DOOR_0);
                        entries.add(ModBlocks.FENCE_0);
                        entries.add(ModBlocks.FENCE_GATE_0);
                        entries.add(ModBlocks.SLAB_0);
                        entries.add(ModBlocks.STAIRS_0);
                        entries.add(ModBlocks.WALL_0);
                        entries.add(ModBlocks.PRESSURE_PLATE_0);
                        entries.add(ModBlocks.BONUSPAD);
                        entries.add(ModBlocks.CAREPAD);

                    }).build());

    public static final ItemGroup Die_Of_Death_Ores = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(DieOfDeath.MOD_ID, "die_of_death_ores"),
    FabricItemGroup.builder().icon(() -> new ItemStack(ModBlocks.BONUSPAD_ORE))
            .displayName(Text.translatable("itemgroup.dieofdeath.die_of_death_ores"))
            .entries((displayContext, entries) -> {
                entries.add(ModBlocks.BONUSPAD_ORE);
                entries.add(ModBlocks.BONUSPAD_DEEPSLATE_ORE);
            }).build());

    public static final ItemGroup Die_Of_Death_Natural = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(DieOfDeath.MOD_ID, "die_of_death_natural"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModBlocks.BACKYARD_BUSH))
                    .displayName(Text.translatable("itemgroup.dieofdeath.die_of_death_natural"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModBlocks.BACKYARD_BUSH);
                        entries.add(ModBlocks.TUNDRA_TRENCH_SAPLING);
                    }).build());

    public static final ItemGroup Die_Of_Death_Music = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(DieOfDeath.MOD_ID, "die_of_death_music"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.ETERNITY_V2_MUSIC_DISC))
                    .displayName(Text.translatable("itemgroup.dieofdeath.die_of_death_music"))
                    .entries(((displayContext, entries) -> {
                        entries.add(ModItems.ETERNITY_V2_MUSIC_DISC);
                        entries.add(ModItems.GRANDMA_BACKYARD_MUSIC_DISC);
                        entries.add(ModItems.TEMPLE);
                        entries.add(ModItems.ROBLOX_HQ);
                        entries.add(ModItems.BASEPLATE);
                        entries.add(ModItems.TUNDRA_TRENCH);
                        entries.add(ModItems.TEAPOT_PALACE_TOUR_CALM);
                        entries.add(ModItems.TEAPOT_PALACE_TOUR_RAIN);
                        entries.add(ModItems.TEAPOT_PALACE_TOUR_LMS);
                        entries.add(ModItems.OMEGAS_FINAL_STAND);
                        entries.add(ModItems.CARELESS);
                        entries.add(ModItems.VIGILANTE_SHOOTDOWN);
                        entries.add(ModItems.SHOWTIME);
                        entries.add(ModItems.ETERNITY);
                        entries.add(ModItems.Y2K);
                        entries.add(ModItems.ONE_BOUNCE);
                    })).build());



    public static void registerItemGroups() {
        DieOfDeath.LOGGER.info("Registering Item Groups for " + DieOfDeath.MOD_ID);
    }
}