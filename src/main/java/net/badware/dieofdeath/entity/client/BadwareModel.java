package net.badware.dieofdeath.entity.client;

import net.badware.dieofdeath.DieOfDeath;
import net.badware.dieofdeath.entity.custom.BadwareEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BadwareModel extends GeoModel<BadwareEntity> {

    private static final Identifier GEO_DEFAULT = Identifier.of(DieOfDeath.MOD_ID, "geo/badware.geo.json");

    private static final Identifier TEX_DEFAULT = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/badware.png");
    private static final Identifier TEX_GOODWARE = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/badware_goodware.png");

    private static final Identifier ANIM_DEFAULT = Identifier.of(DieOfDeath.MOD_ID, "animations/badware.animation.json");

    @Override
    public Identifier getModelResource(BadwareEntity animatable) {
        return switch (animatable.getVariant()) {
            default -> GEO_DEFAULT;
        };
    }

    @Override
    public Identifier getTextureResource(BadwareEntity animatable) {
        return switch (animatable.getVariant()) {
            case 1 -> TEX_GOODWARE;
            default -> TEX_DEFAULT;
        };
    }

    @Override
    public Identifier getAnimationResource(BadwareEntity animatable) {
        return ANIM_DEFAULT;
    }
}
