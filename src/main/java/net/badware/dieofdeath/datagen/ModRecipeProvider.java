package net.badware.dieofdeath.datagen;

import net.badware.dieofdeath.DieOfDeath;
import net.badware.dieofdeath.block.ModBlocks;
import net.badware.dieofdeath.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }
    @Override
    public void generate(RecipeExporter exporter) {

        List<ItemConvertible> SHARD_SMELTABLES = List.of(ModItems.HOTDOG, ModBlocks.BONUSPAD_ORE,
                ModBlocks.BONUSPAD_DEEPSLATE_ORE);

        List<ItemConvertible> CEMENT_SMELTABLES = List.of(ModItems.WALL_BRICK);

        offerSmelting(exporter, SHARD_SMELTABLES, RecipeCategory.MISC, ModItems.BONUSPAD_SHARD, 0.30f, 280, "bonuspad_shard");
        offerBlasting(exporter, SHARD_SMELTABLES, RecipeCategory.MISC, ModItems.BONUSPAD_SHARD, 0.30f, 140, "bonuspad_shard");

        offerSmelting(exporter, CEMENT_SMELTABLES, RecipeCategory.MISC, ModItems.CEMENT, 0.20f, 300, "cement");
        offerBlasting(exporter, CEMENT_SMELTABLES, RecipeCategory.MISC, ModItems.CEMENT, 0.21f, 150, "cement");

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BOX_CHAIN)
                .pattern("C C")
                .pattern(" C ")
                .pattern("C C")
                .input('C', Items.CHAIN)
                .criterion(hasItem(Items.CHAIN),conditionsFromItem(Items.CHAIN))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.IMPLEMENT_WALL)
                .pattern("AAA")
                .pattern("AAA")
                .input('A', ModItems.WALL_BRICK)
                .criterion(hasItem(ModItems.WALL_BRICK),conditionsFromItem(ModItems.WALL_BRICK))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.WALL_BRICK, 6)
                .input(ModBlocks.IMPLEMENT_WALL)
                .criterion(hasItem(ModBlocks.IMPLEMENT_WALL), conditionsFromItem(ModBlocks.IMPLEMENT_WALL))
                .offerTo(exporter, Identifier.of(DieOfDeath.MOD_ID, "wall_brick_from_implement_wall"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.BLUE_AXE)
                .pattern("BB ")
                .pattern("BD ")
                .pattern(" D ")
                .input('B', ModItems.BONUSPAD_SHARD)
                .input('D', Items.STICK)
                .criterion(hasItem(ModItems.BONUSPAD_SHARD), conditionsFromItem(ModItems.BONUSPAD_SHARD))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.BLUE_AXE)
                .pattern(" BB")
                .pattern(" DB")
                .pattern(" D ")
                .input('B', ModItems.BONUSPAD_SHARD)
                .input('D', Items.STICK)
                .criterion(hasItem(ModItems.BONUSPAD_SHARD), conditionsFromItem(ModItems.BONUSPAD_SHARD))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .offerTo(exporter, Identifier.of(DieOfDeath.MOD_ID, "blue_axe_mirrored"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.IRON_HAMMER)
                .pattern("EEE")
                .pattern("EFE")
                .pattern(" F ")
                .input('E', ModItems.CEMENT)
                .input('F', Items.BREEZE_ROD)
                .criterion(hasItem(ModItems.CEMENT), conditionsFromItem(ModItems.CEMENT))
                .criterion(hasItem(Items.BREEZE_ROD), conditionsFromItem(Items.BREEZE_ROD))
                .offerTo(exporter);


        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.CHAIN, 5)
                .input(ModItems.BOX_CHAIN)
                .criterion(hasItem(ModItems.BOX_CHAIN), conditionsFromItem(ModItems.BOX_CHAIN))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.HOTDOG, 41)
                .input(ModBlocks.TEST_RAIL)
                .criterion(hasItem(ModBlocks.TEST_RAIL), conditionsFromItem(ModBlocks.TEST_RAIL))
                .offerTo(exporter, Identifier.of(DieOfDeath.MOD_ID, "hotdog_from_test_rail"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModItems.WALL_BRICK)
                .pattern("AB")
                .input('A', ModItems.CEMENT)
                .input('B', Items.WHITE_DYE)
                .criterion(hasItem(ModItems.CEMENT), conditionsFromItem(ModItems.CEMENT))
                .criterion(hasItem(Items.WHITE_DYE), conditionsFromItem(Items.WHITE_DYE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.BLOCK_CHESTPLATE)
                .pattern("A A")
                .pattern("ABA")
                .pattern("AAA")
                .input('A', Items.IRON_INGOT)
                .input('B', Items.IRON_BLOCK)
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .criterion(hasItem(Items.IRON_BLOCK), conditionsFromItem(Items.IRON_BLOCK))
                .offerTo(exporter);
    }
}
