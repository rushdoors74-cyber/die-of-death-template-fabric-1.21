package net.badware.dieofdeath;

import net.badware.dieofdeath.block.ModBlocks;
import net.badware.dieofdeath.client.RiftOverlay;
import net.badware.dieofdeath.entity.ModEntities;
import net.badware.dieofdeath.entity.client.BadwarePCRenderer;
import net.badware.dieofdeath.entity.client.BadwareRenderer;
import net.badware.dieofdeath.entity.client.PursuerRenderer;
import net.badware.dieofdeath.entity.custom.BadwareEntity;
import net.badware.dieofdeath.particle.ModParticles;
import net.badware.dieofdeath.util.ModModelPredicates;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.SpellParticle;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerEntity;

public class DieOfDeathClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DOOR_0, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TRAPDOOR_0, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BACKYARD_BUSH, RenderLayer.getCutout());

        ModModelPredicates.registerModModels();

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TUNDRA_TRENCH_SAPLING, RenderLayer.getCutout());

        EntityRendererRegistry.register(ModEntities.PURSUER, PursuerRenderer::new);
        EntityRendererRegistry.register(ModEntities.BADWARE, BadwareRenderer::new);
        EntityRendererRegistry.register(ModEntities.BADWARE_PC, BadwarePCRenderer::new);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.KILLER_ONLY_BLOCK, RenderLayer.getTranslucent());

        ParticleFactoryRegistry.getInstance().register(ModParticles.GREEN_STAR, SpellParticle.DefaultFactory::new);

        HudRenderCallback.EVENT.register((context, tickDelta) -> {
            PlayerEntity player = MinecraftClient.getInstance().player;
            if (player == null) return;

            boolean isAnyBadwareRifting = player.getWorld().getEntitiesByClass(
                    BadwareEntity.class,
                    player.getBoundingBox().expand(128.0),
                    badware -> badware.getDataTracker().get(BadwareEntity.IS_RIFTING)
            ).size() > 0;

            if (isAnyBadwareRifting) {
                RiftOverlay.render(context);
            }
        });
    }
}
