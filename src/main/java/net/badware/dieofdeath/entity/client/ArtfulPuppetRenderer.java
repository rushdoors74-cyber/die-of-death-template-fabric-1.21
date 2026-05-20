package net.badware.dieofdeath.entity.client;

import net.badware.dieofdeath.entity.custom.ArtfulPuppetEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ArtfulPuppetRenderer extends GeoEntityRenderer<ArtfulPuppetEntity> {
    public ArtfulPuppetRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ArtfulPuppetModel());
    }
    @Override
    public void render(ArtfulPuppetEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
