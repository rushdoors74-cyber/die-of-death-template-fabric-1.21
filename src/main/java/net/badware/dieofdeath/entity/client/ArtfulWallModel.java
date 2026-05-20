package net.badware.dieofdeath.entity.client;

import net.badware.dieofdeath.DieOfDeath;
import net.badware.dieofdeath.entity.custom.ArtfulWallEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ArtfulWallModel extends GeoModel<ArtfulWallEntity> {

    private static final Identifier GEO_DEFAULT = Identifier.of(DieOfDeath.MOD_ID, "geo/artful_wall.geo.json");
    private static final Identifier TEX_DEFAULT = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/artful/artful_wall.png");
    private static final Identifier TEX_WALL_BUILDER = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/artful/builder_wall.png");
    private static final Identifier ANIM_DEFAULT = Identifier.of(DieOfDeath.MOD_ID, "animations/artful_wall.animation.json");


    @Override
    public Identifier getModelResource(ArtfulWallEntity animatable) {
        return GEO_DEFAULT;
    }

    @Override
    public Identifier getTextureResource(ArtfulWallEntity animatable) {
        return switch (animatable.getVariant()) {
            case 1 -> TEX_WALL_BUILDER;
            default -> TEX_DEFAULT;
        };
    }

    @Override
    public Identifier getAnimationResource(ArtfulWallEntity animatable) {
        return ANIM_DEFAULT;
    }
}