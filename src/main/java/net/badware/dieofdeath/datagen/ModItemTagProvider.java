package net.badware.dieofdeath.datagen;

import net.badware.dieofdeath.item.ModItems;
import net.badware.dieofdeath.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModTags.Items.TRANSFORMABLE_ITEMS)
                .add(ModItems.BOILING_WATER)
                .add(ModItems.WALL_BRICK)
                .add(ModItems.BONUSPAD_SHARD)
                .add(Items.NETHERITE_INGOT)
                .add(Items.GOLDEN_APPLE);

        getOrCreateTagBuilder(ItemTags.SWORDS)
                .add(ModItems.PURSUER_CLEAVE);
        getOrCreateTagBuilder(ItemTags.AXES)
                .add(ModItems.BLUE_AXE);
        getOrCreateTagBuilder(ItemTags.PICKAXES)
                .add(ModItems.YELLOW_THING);
        getOrCreateTagBuilder(ItemTags.SHOVELS)
                .add(ModItems.SHOVEL_STUFF);
        getOrCreateTagBuilder(ItemTags.HOES)
                .add(ModItems.WEIRD_HOE);

        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.BLOCK_HELMET)
                .add(ModItems.BLOCK_CHESTPLATE)
                .add(ModItems.BLOCK_LEGGINGS)
                .add(ModItems.BLOCK_BOOTS);

        getOrCreateTagBuilder(ItemTags.BOW_ENCHANTABLE)
                .add(ModItems.HARKEN_BOW);


    }
}
