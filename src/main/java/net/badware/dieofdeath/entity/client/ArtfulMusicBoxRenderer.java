package net.badware.dieofdeath.entity.client;

import net.badware.dieofdeath.entity.custom.ArtfulMusicBoxEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ArtfulMusicBoxRenderer extends GeoEntityRenderer<ArtfulMusicBoxEntity> {
    public ArtfulMusicBoxRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ArtfulMusicBoxModel());
    }
    @Override
    public void render(ArtfulMusicBoxEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
