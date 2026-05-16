package net.badware.dieofdeath.entity.client;

import net.badware.dieofdeath.DieOfDeath;
import net.badware.dieofdeath.entity.custom.BadwareEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;

public class BadwareModel extends GeoModel<BadwareEntity> {

    private static final Identifier GEO_DEFAULT = Identifier.of(DieOfDeath.MOD_ID, "geo/badware.geo.json");
    private static final Identifier GEO_WIPERWARE = Identifier.of(DieOfDeath.MOD_ID, "geo/badware_wiperware.geo.json");
    private static final Identifier GEO_FRUTIGER_AEROWARE = Identifier.of(DieOfDeath.MOD_ID, "geo/badware_aeroware.geo.json");
    private static final Identifier GEO_COOKIEWARE = Identifier.of(DieOfDeath.MOD_ID, "geo/badware_cookieware.geo.json");
    private static final Identifier GEO_RUNICWARE = Identifier.of(DieOfDeath.MOD_ID, "geo/badware_runicware.geo.json");
    private static final Identifier GEO_ANGELWARE = Identifier.of(DieOfDeath.MOD_ID, "geo/badware_angelware.geo.json");
    private static final Identifier GEO_DEVILWARE = Identifier.of(DieOfDeath.MOD_ID, "geo/badware_devilware.geo.json");
    private static final Identifier GEO_LOVEWARE = Identifier.of(DieOfDeath.MOD_ID, "geo/badware_loveware.geo.json");
    private static final Identifier GEO_SPYWARE = Identifier.of(DieOfDeath.MOD_ID, "geo/badware_spyware.geo.json");


    private static final Identifier TEX_DEFAULT = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/badware.png");
    private static final Identifier TEX_WIPERWARE = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/badware_wiperware.png");
    private static final Identifier TEX_FRUTIGER_AEROWARE_HURT = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/badware_aeroware_hurt.png");
    private static final Identifier TEX_FRUTIGER_AEROWARE = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/badware_aeroware.png");
    private static final Identifier TEX_COOKIEWARE_HURT = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/badware_cookieware_hurt.png");
    private static final Identifier TEX_COOKIEWARE = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/badware_cookieware.png");
    private static final Identifier TEX_RUNICWARE_HIT = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/badware_runicware_hit.png");
    private static final Identifier TEX_RUNICWARE = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/badware_runicware.png");
    private static final Identifier TEX_ANGELWARE = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/badware_angelware.png");
    private static final Identifier TEX_DEVILWARE = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/badware_devilware.png");
    private static final Identifier TEX_LOVEWARE = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/badware_loveware.png");
    private static final Identifier TEX_SPYWARE = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/badware_spyware.png");
    private static final Identifier TEX_GOODWARE = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/badware_goodware.png");

    private static final Identifier ANIM_DEFAULT = Identifier.of(DieOfDeath.MOD_ID, "animations/badware.animation.json");

    @Override
    public Identifier getModelResource(BadwareEntity animatable) {
        return switch (animatable.getVariant()) {
            case 9 -> GEO_WIPERWARE;
            case 8 -> GEO_FRUTIGER_AEROWARE;
            case 7 -> GEO_COOKIEWARE;
            case 6 -> GEO_RUNICWARE;
            case 5 -> GEO_ANGELWARE;
            case 4 -> GEO_DEVILWARE;
            case 3 -> GEO_LOVEWARE;
            case 2 -> GEO_SPYWARE;
            default -> GEO_DEFAULT;
        };
    }

    @Override
    public Identifier getTextureResource(BadwareEntity animatable) {
        return switch (animatable.getVariant()) {
            case 9 -> TEX_WIPERWARE;
            case 8 -> (animatable.getHealth() <= animatable.getMaxHealth() / 2) ? TEX_FRUTIGER_AEROWARE_HURT : TEX_FRUTIGER_AEROWARE;
            case 7 -> (animatable.getHealth() <= animatable.getMaxHealth() / 2) ? TEX_COOKIEWARE_HURT : TEX_COOKIEWARE;
            case 6 -> (animatable.getHitFaceTimer() > 0) ? TEX_RUNICWARE_HIT : TEX_RUNICWARE;
            case 5 -> TEX_ANGELWARE;
            case 4 -> TEX_DEVILWARE;
            case 3 -> TEX_LOVEWARE;
            case 2 -> TEX_SPYWARE;
            case 1 -> TEX_GOODWARE;
            default -> TEX_DEFAULT;
        };
    }

    @Override
    public void setCustomAnimations(BadwareEntity animatable, long instanceId, AnimationState<BadwareEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        if (animatable.getVariant() == 8) {
            GeoBone mp3Disc = this.getAnimationProcessor().getBone("mp3_disc");

            if (mp3Disc != null) {
                float totalTicks = animatable.age + animationState.getPartialTick();
                mp3Disc.setRotZ(totalTicks * 0.05f);
            }
        }
    }

    @Override
    public Identifier getAnimationResource(BadwareEntity animatable) {
        return ANIM_DEFAULT;
    }
}
