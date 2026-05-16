package net.badware.dieofdeath.entity.client;

import net.badware.dieofdeath.entity.custom.BadwarePCEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BadwarePCRenderer extends GeoEntityRenderer<BadwarePCEntity> {
    public BadwarePCRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new BadwarePCModel());
    }
    @Override
    public void render(BadwarePCEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public RenderLayer getRenderType(BadwarePCEntity animatable, Identifier texture,
                                     @Nullable VertexConsumerProvider bufferSource,
                                     float partialTick) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}