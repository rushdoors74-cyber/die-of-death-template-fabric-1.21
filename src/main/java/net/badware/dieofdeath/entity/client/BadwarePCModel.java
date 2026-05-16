package net.badware.dieofdeath.entity.client;

import net.badware.dieofdeath.DieOfDeath;
import net.badware.dieofdeath.entity.custom.BadwarePCEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BadwarePCModel extends GeoModel<BadwarePCEntity> {

    private static final Identifier GEO_DEFAULT = Identifier.of(DieOfDeath.MOD_ID, "geo/badware_pc.geo.json");
    private static final Identifier GEO_WIPERWARE_PC = Identifier.of(DieOfDeath.MOD_ID, "geo/wiperware_pc.geo.json");
    private static final Identifier GEO_FRUTIGER_AEROWARE_PC = Identifier.of(DieOfDeath.MOD_ID, "geo/aeroware_pc.geo.json");
    private static final Identifier GEO_COOKIEWARE_PC = Identifier.of(DieOfDeath.MOD_ID, "geo/cookieware_pc.geo.json");
    private static final Identifier GEO_RUNICWARE_PC = Identifier.of(DieOfDeath.MOD_ID, "geo/runicware_pc.geo.json");
    private static final Identifier GEO_ANGELWARE_PC = Identifier.of(DieOfDeath.MOD_ID, "geo/angelware_pc.geo.json");
    private static final Identifier GEO_DEVILWARE_PC = Identifier.of(DieOfDeath.MOD_ID, "geo/devilware_pc.geo.json");
    private static final Identifier GEO_LOVEWARE_PC = Identifier.of(DieOfDeath.MOD_ID, "geo/loveware_pc.geo.json");

    private static final Identifier TEX_DEFAULT = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/badware_pc.png");
    private static final Identifier TEX_WIPERWARE_PC = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/wiperware_pc.png");
    private static final Identifier TEX_FRUTIGER_AEROWARE_PC = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/aeroware_pc.png");
    private static final Identifier TEX_COOKIEWARE_PC_HURT = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/cookieware_pc_hurt.png");
    private static final Identifier TEX_COOKIEWARE_PC_CRITICAL = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/cookieware_pc_critical.png");
    private static final Identifier TEX_COOKIEWARE_PC = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/cookieware_pc.png");
    private static final Identifier TEX_RUNICWARE_PC = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/runicware_pc.png");
    private static final Identifier TEX_ANGELWARE_PC = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/angelware_pc.png");
    private static final Identifier TEX_DEVILWARE_PC = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/devilware_pc.png");
    private static final Identifier TEX_LOVEWARE_PC = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/loveware_pc.png");
    private static final Identifier TEX_GOODWARE_PC = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/badware/goodware_pc.png");

    @Override
    public Identifier getModelResource(BadwarePCEntity animatable) {
        return switch (animatable.getVariant()) {
            case 9 -> GEO_WIPERWARE_PC;
            case 8 -> GEO_FRUTIGER_AEROWARE_PC;
            case 7 -> GEO_COOKIEWARE_PC;
            case 6 -> GEO_RUNICWARE_PC;
            case 5 -> GEO_ANGELWARE_PC;
            case 4 -> GEO_DEVILWARE_PC;
            case 3 -> GEO_LOVEWARE_PC;
            default -> GEO_DEFAULT;
        };
    }

    @Override
    public Identifier getTextureResource(BadwarePCEntity animatable) {
        return switch (animatable.getVariant()) {
            case 9 -> TEX_WIPERWARE_PC;
            case 8 -> TEX_FRUTIGER_AEROWARE_PC;
            case 7 -> {
                float healthPercent = animatable.getHealth() / animatable.getMaxHealth();

                if (healthPercent <= 0.25f) {
                    yield TEX_COOKIEWARE_PC_CRITICAL;
                } else if (healthPercent <= 0.50f) {
                    yield TEX_COOKIEWARE_PC_HURT;
                } else {
                    yield TEX_COOKIEWARE_PC;
                }
            }
            case 6 -> TEX_RUNICWARE_PC;
            case 5 -> TEX_ANGELWARE_PC;
            case 4 -> TEX_DEVILWARE_PC;
            case 3 -> TEX_LOVEWARE_PC;
            case 1 -> TEX_GOODWARE_PC;
            default -> TEX_DEFAULT;
        };
    }

    @Override
    public Identifier getAnimationResource(BadwarePCEntity animatable) {
        return null;
    }
}
