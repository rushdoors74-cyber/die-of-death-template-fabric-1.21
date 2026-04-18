package net.badware.dieofdeath.entity.client;

import net.badware.dieofdeath.DieOfDeath;
import net.badware.dieofdeath.entity.custom.PursuerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class PursuerModel extends GeoModel<PursuerEntity> {
    @Override
    public Identifier getModelResource(PursuerEntity animatable) {
        return Identifier.of(DieOfDeath.MOD_ID, "geo/pursuer.geo.json");
    }

    @Override
    public Identifier getTextureResource(PursuerEntity animatable) {
        return switch (animatable.getVariant()) {
            case 1 -> Identifier.of(DieOfDeath.MOD_ID, "textures/entity/pursuer/pursuer_avoider.png");
            default -> Identifier.of(DieOfDeath.MOD_ID, "textures/entity/pursuer/pursuer_killer.png");
        };
    }

    @Override
    public Identifier getAnimationResource(PursuerEntity animatable) {
        return Identifier.of(DieOfDeath.MOD_ID, "animations/pursuer.animation.json");
    }
}