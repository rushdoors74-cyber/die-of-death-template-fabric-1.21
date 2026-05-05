package net.badware.dieofdeath.entity.client;

import net.badware.dieofdeath.entity.custom.BadwareEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BadwareRenderer extends GeoEntityRenderer<BadwareEntity> {
    public BadwareRenderer(EntityRendererFactory.Context renderManager)  {
        super(renderManager, new BadwareModel());
    }
    @Override
    public void render(BadwareEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);

        ClientMusicHandler.handleBadwareMusic(entity);
    }
}
