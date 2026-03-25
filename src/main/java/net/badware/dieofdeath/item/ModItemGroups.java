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

                        entries.add(ModItems.ARTFUL_WAND);

                        entries.add(ModItems.HOTDOG);

                        entries.add(ModItems.BOILING_WATER);

                        entries.add(ModItems.PURSUER_CLEAVE);
                        entries.add(ModItems.BLUE_AXE);
                        entries.add(ModItems.YELLOW_THING);
                        entries.add(ModItems.SHOVEL_STUFF);
                        entries.add(ModItems.WEIRD_HOE);

                        entries.add(ModItems.IRON_HAMMER);
                    }).build());

    public static final ItemGroup Die_Of_Death_Blocks = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(DieOfDeath.MOD_ID, "die_of_death_blocks"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModBlocks.IMPLEMENT_WALL))
                    .displayName(Text.translatable("itemgroup.dieofdeath.die_of_death_blocks"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModBlocks.KILLER_ONLY_BLOCK);
                        entries.add(ModBlocks.IMPLEMENT_WALL);
                        entries.add(ModBlocks.WOOD_BOX);
                        entries.add(ModBlocks.BONUSPAD_ORE);
                        entries.add(ModBlocks.BONUSPAD_DEEPSLATE_ORE);
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
                        entries.add(ModBlocks.TUNDRA_TRENCH_LAMP);

                    }).build());



    public static void registerItemGroups() {
        DieOfDeath.LOGGER.info("Registering Item Groups for " + DieOfDeath.MOD_ID);
    }
}