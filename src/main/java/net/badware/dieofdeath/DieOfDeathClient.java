package net.badware.dieofdeath;

import net.badware.dieofdeath.block.ModBlocks;
import net.badware.dieofdeath.sound.ModSounds;
import net.badware.dieofdeath.util.ModModelPredicates;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class DieOfDeathClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DOOR_0, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TRAPDOOR_0, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BACKYARD_BUSH, RenderLayer.getCutout());

        ModModelPredicates.registerModModels();

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TUNDRA_TRENCH_SAPLING, RenderLayer.getCutout());

    }
}
