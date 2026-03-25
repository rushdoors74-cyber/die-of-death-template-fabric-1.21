package net.badware.dieofdeath.util;

import net.badware.dieofdeath.DieOfDeath;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class Blocks{
        public static final TagKey<Block> NEED_YELLOW_THING_TOOL = createTag("need_yellow_thing_tool");
        public static final TagKey<Block> INCORRECT_FOR_YELLOW_THING_TOOL = createTag("incorrect_for_yellow_thing_tool");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(DieOfDeath.MOD_ID, name));
        }

    }

    public static class Items{
        public static final TagKey<Item> TRANSFORMABLE_ITEMS = createTag("transformable_items");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(DieOfDeath.MOD_ID, name));
        }
    }
}
