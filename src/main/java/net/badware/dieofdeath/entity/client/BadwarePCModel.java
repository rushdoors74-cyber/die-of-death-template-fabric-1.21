package net.badware.dieofdeath.entity.client;

import net.badware.dieofdeath.DieOfDeath;
import net.badware.dieofdeath.entity.custom.BadwarePCEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BadwarePCModel extends GeoModel<BadwarePCEntity> {

    private static final Identifier GEO_DEFAULT = Identifier.of(DieOfDeath.MOD_ID, "geo/badware_pc.geo.json");

    private static final Identifier TEX_DEFAULT = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/badware_pc.png");
    private static final Identifier TEX_GOODWARE_PC = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/goodware_pc.png");

    @Override
    public Identifier getModelResource(BadwarePCEntity animatable) {
        return switch (animatable.getVariant()) {
            default ->  GEO_DEFAULT;
        };
    }

    @Override
    public Identifier getTextureResource(BadwarePCEntity animatable) {
        return switch (animatable.getVariant()) {
            case 1 -> TEX_GOODWARE_PC;
            default -> TEX_DEFAULT;
        };
    }

    @Override
    public Identifier getAnimationResource(BadwarePCEntity animatable) {
        return null;
    }
}
