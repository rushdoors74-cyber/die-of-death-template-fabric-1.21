package net.badware.dieofdeath.datagen;

import net.badware.dieofdeath.block.ModBlocks;
import net.badware.dieofdeath.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.BONUSPAD_DEEPSLATE_ORE)
                .add(ModBlocks.BONUSPAD_ORE)
                .add(ModBlocks.IMPLEMENT_WALL)
                .add(ModBlocks.TEST_RAIL);

        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
                .add(ModBlocks.WOOD_BOX);

        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.IMPLEMENT_WALL);

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.BONUSPAD_ORE);

        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.BONUSPAD_DEEPSLATE_ORE);

        getOrCreateTagBuilder(BlockTags.WOODEN_FENCES).add(ModBlocks.FENCE_0);
        getOrCreateTagBuilder(BlockTags.FENCE_GATES).add(ModBlocks.FENCE_GATE_0);
        getOrCreateTagBuilder(BlockTags.WALLS).add(ModBlocks.WALL_0);

        getOrCreateTagBuilder(ModTags.Blocks.NEED_YELLOW_THING_TOOL)
                .add(ModBlocks.KILLER_ONLY_BLOCK)
                .add(ModBlocks.TEST_RAIL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);

    }
}
