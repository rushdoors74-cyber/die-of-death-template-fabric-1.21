package net.badware.dieofdeath.entity.custom;

import net.badware.dieofdeath.effect.ModEffects;
import net.badware.dieofdeath.entity.client.ChaseThemeSound;
import net.badware.dieofdeath.sound.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class PursuerEntity extends HostileEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private int howlTicks = 0;
    private int cleaveTicks = 0;
    private boolean isThemePlaying = false;
    private int musicTickDelay = 0;

    public PursuerEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createPursuerAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 100.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.27)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 50.0);
    }

    private static final TrackedData<Boolean> IS_CLEAVING = DataTracker.registerData(PursuerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> IS_HOWLING = DataTracker.registerData(PursuerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(IS_CLEAVING, false);
        builder.add(IS_HOWLING, false);
    }

    public void performCleave() {
        if (this.getWorld().isClient) return;

        this.dataTracker.set(IS_CLEAVING, true);
        this.triggerAnim("attack_controller", "cleave");

        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                ModSounds.PURSUER_CLEAVE_START, this.getSoundCategory(), 1.0f, 1.0f);

        int boostDuration = this.isSprinting() ? 40 : 20;
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, boostDuration, 1));

        this.cleaveTicks = 20;
    }

    private void applyCleaveDamage() {
        Box hitbox = this.getBoundingBox().expand(2.0, 0, 2.0).offset(this.getRotationVec(1.0f).multiply(2.0));
        this.getWorld().getEntitiesByClass(LivingEntity.class, hitbox, EntityPredicates.EXCEPT_SPECTATOR).forEach(target -> {
            if (target != this) {
                target.damage(this.getDamageSources().mobAttack(this), 20.0f);
                target.addStatusEffect(new StatusEffectInstance(ModEffects.BLEED, 100, 1));
                target.takeKnockback(0.5, -this.getX() + target.getX(), -this.getZ() + target.getZ());
            }
        });

        this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 40, 2));
        this.dataTracker.set(IS_CLEAVING, false);
    }

    public void performHowl() {
        this.dataTracker.set(IS_HOWLING, true);
        this.triggerAnim("attack_controller", "howl");
        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                ModSounds.PURSUER_SCREAM, this.getSoundCategory(), 1.0f, 1.0f);
        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
        ModSounds.PURSUER_SHOCKWAVE, this.getSoundCategory(), 1.0f, 1.0f);

        this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 40, 10));

        this.getWorld().getPlayers().forEach(player -> {
            if (player.squaredDistanceTo(this) < 4000) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 120, 3));
            }
        });

        this.howlTicks = 40;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(5, new LookAroundGoal(this));

        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (this.dataTracker.get(IS_CLEAVING) || this.dataTracker.get(IS_HOWLING)) return false;

        int move = this.random.nextInt(10);

        if (move < 2) {
            this.performCleave();
        } else if (move == 2) {
            this.performHowl();
        } else {
            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                    ModSounds.PURSUER_SWINGING, this.getSoundCategory(), 1.0f, 1.0f);
            this.triggerAnim("attack_controller", "swing");
            return super.tryAttack(target);
        }
        return true;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "movement_controller", 5, state -> {
            if (state.isMoving()) {
                state.getController().setAnimationSpeed(1.0);

                return state.setAndContinue(RawAnimation.begin().thenLoop("animation.pursuer.run"));
            }
            return state.setAndContinue(RawAnimation.begin().thenLoop("animation.pursuer.idle"));
        }));

        controllers.add(new AnimationController<>(this, "attack_controller", 2, state -> PlayState.STOP)
                .triggerableAnim("swing", RawAnimation.begin().thenPlay("animation.pursuer.swing"))
                .triggerableAnim("howl", RawAnimation.begin().thenPlay("animation.pursuer.howl"))
                .triggerableAnim("cleave", RawAnimation.begin().thenPlay("animation.pursuer.cleave"))
        );
    }

    @Override
    public void mobTick() {
        super.mobTick();

        if (this.howlTicks > 0) {
            this.howlTicks--;
            if (this.howlTicks <= 0) {
                this.dataTracker.set(IS_HOWLING, false);
            }
        }

        if (this.cleaveTicks > 0) {
            this.cleaveTicks--;

            if (this.cleaveTicks == 10) {
                this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                        ModSounds.PURSUER_CLEAVE_SFX, this.getSoundCategory(), 1.0f, 1.0f);
                applyCleaveDamage();
            }

            if (this.cleaveTicks == 1) {
                this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                        ModSounds.PURSUER_CLEAVE_END, this.getSoundCategory(), 1.0f, 1.0f);

                this.dataTracker.set(IS_CLEAVING, false);
            }
        }

        if (this.dataTracker.get(IS_CLEAVING) && this.hasStatusEffect(StatusEffects.SLOWNESS)) {
            this.setSprinting(false);
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient) {
            checkAndPlayChaseMusic();
        }
    }

    private void checkAndPlayChaseMusic() {
        net.minecraft.client.MinecraftClient client = net.minecraft.client.MinecraftClient.getInstance();
        double dist = this.squaredDistanceTo(client.player);

        if (dist < 2500 && !isThemePlaying) {
            client.getSoundManager().play(new ChaseThemeSound(this));
            isThemePlaying = true;
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}