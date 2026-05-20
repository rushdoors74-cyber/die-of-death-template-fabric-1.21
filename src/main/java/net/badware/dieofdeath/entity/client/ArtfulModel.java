package net.badware.dieofdeath.entity.client;

import net.badware.dieofdeath.DieOfDeath;
import net.badware.dieofdeath.entity.custom.ArtfulEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;

public class ArtfulModel extends GeoModel<ArtfulEntity> {

    private static final Identifier GEO_DEFAULT = Identifier.of(DieOfDeath.MOD_ID, "geo/artful.geo.json");
    private static final Identifier GEO_BUILDER = Identifier.of(DieOfDeath.MOD_ID, "geo/artful_builder.geo.json");

    private static final Identifier TEX_DEFAULT = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/artful/artful.png");
    private static final Identifier TEX_BUILDER = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/artful/artful_builder.png");

    private static final Identifier ANIM_DEFAULT = Identifier.of(DieOfDeath.MOD_ID, "animations/artful.animation.json");

    @Override
    public Identifier getModelResource(ArtfulEntity animatable) {
        return switch (animatable.getVariant()) {
            case 1 -> GEO_BUILDER;
            default -> GEO_DEFAULT;
        };
    }

    @Override
    public Identifier getTextureResource(ArtfulEntity animatable) {
        return switch (animatable.getVariant()) {
            case 1 -> TEX_BUILDER;
            default -> TEX_DEFAULT;
        };
    }

    @Override
    public Identifier getAnimationResource(ArtfulEntity animatable) {
        return ANIM_DEFAULT;
    }

    @Override
    public void setCustomAnimations(ArtfulEntity animatable, long instanceId, AnimationState<ArtfulEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        GeoBone hiddenCube = getAnimationProcessor().getBone("powerless_cube");

        if (hiddenCube != null) {
            boolean isSpawningMusicBox = animatable.copywriteTicks >= 0;

            hiddenCube.setHidden(!isSpawningMusicBox);
        }
    }
}