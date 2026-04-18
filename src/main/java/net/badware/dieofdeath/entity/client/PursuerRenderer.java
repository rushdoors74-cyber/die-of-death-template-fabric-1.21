package net.badware.dieofdeath.entity.client;

import net.badware.dieofdeath.entity.custom.PursuerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PursuerRenderer extends GeoEntityRenderer<PursuerEntity> {
    public PursuerRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new PursuerModel());
    }

    @Override
    public void render(PursuerEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        poseStack.push();
        poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180f));

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);

        poseStack.pop();
    }

}