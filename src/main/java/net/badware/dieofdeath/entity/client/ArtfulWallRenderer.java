package net.badware.dieofdeath.entity.client;

import net.badware.dieofdeath.entity.custom.ArtfulWallEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ArtfulWallRenderer extends GeoEntityRenderer<ArtfulWallEntity> {
    public ArtfulWallRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ArtfulWallModel());
    }
    @Override
    public void render(ArtfulWallEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
