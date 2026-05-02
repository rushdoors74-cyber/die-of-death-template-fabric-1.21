package net.badware.dieofdeath.entity.client;

import net.badware.dieofdeath.DieOfDeath;
import net.badware.dieofdeath.entity.custom.PursuerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;

public class PursuerModel extends GeoModel<PursuerEntity> {

    private static final Identifier GEO_DEFAULT = Identifier.of(DieOfDeath.MOD_ID, "geo/pursuer.geo.json");
    private static final Identifier GEO_BLING = Identifier.of(DieOfDeath.MOD_ID, "geo/pursuer_bling.geo.json");
    private static final Identifier GEO_THEC = Identifier.of(DieOfDeath.MOD_ID, "geo/pursuer_thec.geo.json");
    private static final Identifier GEO_SEESAWS = Identifier.of(DieOfDeath.MOD_ID, "geo/pursuer_seesaws.geo.json");
    private static final Identifier GEO_CLAWSGUY = Identifier.of(DieOfDeath.MOD_ID, "geo/pursuer_clawsguy.geo.json");
    private static final Identifier GEO_HARDEST_GAME = Identifier.of(DieOfDeath.MOD_ID, "geo/pursuer_hardest_game.geo.json");
    private static final Identifier GEO_MAZE_GUY = Identifier.of(DieOfDeath.MOD_ID, "geo/pursuer_maze_guy.geo.json");
    private static final Identifier GEO_IQUOT = Identifier.of(DieOfDeath.MOD_ID, "geo/pursuer_iquot.geo.json");
    private static final Identifier GEO_MEQUOT = Identifier.of(DieOfDeath.MOD_ID, "geo/pursuer_mequot.geo.json");
    private static final Identifier GEO_STALKER = Identifier.of(DieOfDeath.MOD_ID, "geo/pursuer_stalker.geo.json");
    private static final Identifier GEO_ZOMBIE_2 = Identifier.of(DieOfDeath.MOD_ID, "geo/pursuer_zombie_2.geo.json");

    private static final Identifier TEX_DEFAULT = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/pursuer/pursuer_killer.png");
    private static final Identifier TEX_BLING = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/pursuer/pursuer_bling.png");
    private static final Identifier TEX_THEC = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/pursuer/pursuer_thec.png");
    private static final Identifier TEX_SEESAWS = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/pursuer/pursuer_seesaws.png");
    private static final Identifier TEX_CLAWSGUY = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/pursuer/pursuer_clawsguy.png");
    private static final Identifier TEX_AVOIDER = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/pursuer/pursuer_avoider.png");
    private static final Identifier TEX_CLASSIC = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/pursuer/pursuer_classic.png");
    private static final Identifier TEX_PHANTASM = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/pursuer/pursuer_phantasm.png");
    private static final Identifier TEX_ZOMBIE = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/pursuer/pursuer_zombie.png");
    private static final Identifier TEX_ZOMBIE_1 = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/pursuer/pursuer_zombie_1.png");
    private static final Identifier TEX_ZOMBIE_2 = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/pursuer/pursuer_zombie_2.png");
    private static final Identifier TEX_STALKER = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/pursuer/pursuer_stalker.png");
    private static final Identifier TEX_MEQUOT = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/pursuer/pursuer_mequot.png");
    private static final Identifier TEX_IQUOT = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/pursuer/pursuer_iquot.png");
    private static final Identifier TEX_MAZE_GUY = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/pursuer/pursuer_maze_guy.png");
    private static final Identifier TEX_HARDEST_GAME = Identifier.of(DieOfDeath.MOD_ID, "textures/entity/pursuer/pursuer_hardest_game.png");

    private static final Identifier ANIM_DEFAULT = Identifier.of(DieOfDeath.MOD_ID, "animations/pursuer.animation.json");
    private static final Identifier ANIM_BLING = Identifier.of(DieOfDeath.MOD_ID, "animations/pursuer.bling.animation.json");
    private static final Identifier ANIM_THEC = Identifier.of(DieOfDeath.MOD_ID, "animations/pursuer.thec.animation.json");
    private static final Identifier ANIM_SEESAWS = Identifier.of(DieOfDeath.MOD_ID, "animations/pursuer.seesaws.animation.json");
    private static final Identifier ANIM_CLAWSGUY = Identifier.of(DieOfDeath.MOD_ID, "animations/pursuer.clawsguy.animation.json");
    private static final Identifier ANIM_IQUOT = Identifier.of(DieOfDeath.MOD_ID, "animations/pursuer.iquot.animation.json");
    private static final Identifier ANIM_MEQUOT = Identifier.of(DieOfDeath.MOD_ID, "animations/pursuer.mequot.animation.json");
    private static final Identifier ANIM_STALKER = Identifier.of(DieOfDeath.MOD_ID, "animations/pursuer.stalker.animation.json");
    private static final Identifier ANIM_ZOMBIE_2 = Identifier.of(DieOfDeath.MOD_ID, "animations/pursuer.zombie_2.animation.json");
    private static final Identifier ANIM_ZOMBIE_1 = Identifier.of(DieOfDeath.MOD_ID, "animations/pursuer.zombie_1.animation.json");
    private static final Identifier ANIM_CLASSIC = Identifier.of(DieOfDeath.MOD_ID, "animations/pursuer.classic.animation.json");

    @Override
    public Identifier getModelResource(PursuerEntity animatable) {
        return switch (animatable.getVariant()) {
            case 15 -> GEO_BLING;
            case 14 -> GEO_THEC;
            case 13 -> GEO_SEESAWS;
            case 12 -> GEO_CLAWSGUY;
            case 11 -> GEO_HARDEST_GAME;
            case 10 -> GEO_MAZE_GUY;
            case 9 -> GEO_IQUOT;
            case 8 -> GEO_MEQUOT;
            case 7 -> GEO_STALKER;
            case 6 -> GEO_ZOMBIE_2;
            default -> GEO_DEFAULT;
        };
    }

    @Override
    public Identifier getTextureResource(PursuerEntity animatable) {
        return switch (animatable.getVariant()) {
            case 15 -> TEX_BLING;
            case 14 -> TEX_THEC;
            case 13 -> TEX_SEESAWS;
            case 12 -> TEX_CLAWSGUY;
            case 11 -> TEX_HARDEST_GAME;
            case 10 -> TEX_MAZE_GUY;
            case 9 -> TEX_IQUOT;
            case 8 -> TEX_MEQUOT;
            case 7 -> TEX_STALKER;
            case 6 -> TEX_ZOMBIE_2;
            case 5 -> TEX_ZOMBIE_1;
            case 4 -> TEX_ZOMBIE;
            case 3 -> TEX_PHANTASM;
            case 2 -> TEX_CLASSIC;
            case 1 -> TEX_AVOIDER;
            default -> TEX_DEFAULT;
        };
    }

    @Override
    public Identifier getAnimationResource(PursuerEntity animatable) {
        int variant = animatable.getVariant();
        if (variant == 15) return ANIM_BLING;
        if (variant == 14) return ANIM_THEC;
        if (variant == 13) return ANIM_SEESAWS;
        if (variant == 12) return ANIM_CLAWSGUY;
        if (variant == 9) return ANIM_IQUOT;
        if (variant == 8) return ANIM_MEQUOT;
        if (variant == 7) return ANIM_STALKER;
        if (variant == 6) return ANIM_ZOMBIE_2;
        if (variant == 5) return ANIM_ZOMBIE_1;
        if (variant == 2 || variant == 10 || variant == 11) return ANIM_CLASSIC;
        return ANIM_DEFAULT;
    }

    @Override
    public void setCustomAnimations(PursuerEntity animatable, long instanceId, AnimationState<PursuerEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        float time = animatable.age + animationState.getPartialTick();

        var processor = this.getAnimationProcessor();

        GeoBone sword = processor.getBone("sword");

        if (animatable.getVariant() == 11) {
            GeoBone bodyX = processor.getBone("body_x");
            GeoBone headEye = processor.getBone("head_eye");
            GeoBone moving1 = processor.getBone("body_two_moving");
            GeoBone moving2 = processor.getBone("body_two_moving_2");

            if (bodyX != null) {
                bodyX.setPosX((float) Math.cos(time * 0.2f) * 1.5f);
                bodyX.setPosY((float) Math.sin(time * 0.2f) * 1.5f);
            }

            if (headEye != null) {
                float eyeSwitch = (float) Math.sin(time * 0.05f);
                headEye.setPosX((eyeSwitch > 0) ? 0.5f : -0.5f);
            }

            if (moving1 != null) moving1.setPosY((float) Math.sin(time * 0.15f) * 2.0f);
            if (moving2 != null) moving2.setPosY((float) -Math.sin(time * 0.15f) * 2.0f);

            if (sword != null) {
                if (animatable.isCleaving()) {
                    sword.setHidden(false);
                } else {
                    sword.setHidden(true);
                    sword.setScaleX(0.0f);
                    sword.setScaleY(0.0f);
                    sword.setScaleZ(0.0f);
                }
            }
        }
        else {
            if (sword != null && sword.getScaleX() == 0.0f) {
                sword.setHidden(false);
                sword.setScaleX(1.0f);
                sword.setScaleY(1.0f);
                sword.setScaleZ(1.0f);
            }
        }
    }
}