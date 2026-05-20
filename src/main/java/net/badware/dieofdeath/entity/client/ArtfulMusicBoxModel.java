package net.badware.dieofdeath.entity.client;

import net.badware.dieofdeath.DieOfDeath;
import net.badware.dieofdeath.entity.custom.ArtfulMusicBoxEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;

public class ArtfulMusicBoxModel extends GeoModel<ArtfulMusicBoxEntity> {

    private static final Identifier GEO_DEFAULT = Identifier.of(DieOfDeath.MOD_ID, "geo/artful_musicbox.geo.json");
    private static final Identifier TEX_DEFAULT = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/artful/artful_musicbox.png");
    private static final Identifier ANIM_DEFAULT = Identifier.of(DieOfDeath.MOD_ID, "animations/artful_musicbox.animation.json");

    @Override
    public Identifier getModelResource(ArtfulMusicBoxEntity animatable) {
        return GEO_DEFAULT;
    }

    @Override
    public Identifier getTextureResource(ArtfulMusicBoxEntity animatable) {
        return TEX_DEFAULT;
    }

    @Override
    public Identifier getAnimationResource(ArtfulMusicBoxEntity animatable) {
        return ANIM_DEFAULT;
    }

    @Override
    public void setCustomAnimations(ArtfulMusicBoxEntity animatable, long instanceId, AnimationState<ArtfulMusicBoxEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        GeoBone root = getAnimationProcessor().getBone("root");

        if (root != null) {
            float age = animatable.age + animationState.getPartialTick();

            if (animatable.isOverdriveMode()) {
                root.setRotY(age * 0.15F);

                root.setPosY((float) Math.sin(age * 0.08F) * 3.0F);

                root.setRotX(0.0F);
                root.setRotZ(0.0F);
            } else {
                root.setRotX(0.4F);
                root.setRotZ(0.4F);

                int stepsPerSecond = 4;
                float steppedAge = (float) Math.floor(age / stepsPerSecond) * stepsPerSecond;

                root.setRotY(steppedAge * 0.1F);
            }
        }
    }
}