package net.badware.dieofdeath.entity.client;

import net.badware.dieofdeath.DieOfDeath;
import net.badware.dieofdeath.entity.custom.ArtfulPuppetEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ArtfulPuppetModel extends GeoModel<ArtfulPuppetEntity> {

    private static final Identifier GEO_DEFAULT = Identifier.of(DieOfDeath.MOD_ID, "geo/artful_puppet.geo.json");
    private static final Identifier TEX_DEFAULT = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/artful/artful_puppet.png");
    private static final Identifier ANIM_DEFAULT = Identifier.of(DieOfDeath.MOD_ID, "animations/artful_puppet.animation.json");

    @Override
    public Identifier getModelResource(ArtfulPuppetEntity animatable) {
        return GEO_DEFAULT;
    }

    @Override
    public Identifier getTextureResource(ArtfulPuppetEntity animatable) {
        return TEX_DEFAULT;
    }

    @Override
    public Identifier getAnimationResource(ArtfulPuppetEntity animatable) {
        return ANIM_DEFAULT;
    }
}