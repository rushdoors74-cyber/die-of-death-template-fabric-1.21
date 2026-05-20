package net.badware.dieofdeath.entity.custom;

import net.badware.dieofdeath.sound.ModSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class ArtfulPuppetEntity extends HostileEntity implements GeoAnimatable {

    private int lifetimeTicks = 0;
    private boolean spawnSoundPlayed = false;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ArtfulPuppetEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createPuppetAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 50.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.32)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, false));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient) return;

        if (!spawnSoundPlayed) {
            this.getWorld().playSound(null, this.getBlockPos(), ModSounds.ARTFUL_PUPPET_AMBIENCE,
                    net.minecraft.sound.SoundCategory.HOSTILE, 1.0f, 1.0f);
            spawnSoundPlayed = true;
        }
    }

    @Override
    public void mobTick() {
        super.mobTick();
        if (this.getWorld().isClient) return;

        lifetimeTicks++;

        if (lifetimeTicks <= 60) {
            this.getNavigation().stop();
            this.setTarget(null);
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1, 10, false, false));
            return;
        }

        this.setHealth(this.getHealth() - 0.05f);
        if (this.getHealth() <= 0) {
            this.kill();
            return;
        }

        if (lifetimeTicks % 10 == 0 && this.getTarget() != null) {
            if (this.distanceTo(this.getTarget()) <= 1.4) {
                Box attackBox = this.getBoundingBox().expand(0.5);
                List<PlayerEntity> hitPlayers = this.getWorld().getEntitiesByClass(PlayerEntity.class, attackBox,
                        p -> !p.isCreative() && !p.isSpectator());

                for (PlayerEntity player : hitPlayers) {
                    DamageSource source = this.getDamageSources().mobAttack(this);
                    player.damage(source, 3.0F);
                }
            }
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new software.bernie.geckolib.animation.AnimationController<>(this, "controller", 2, event -> {
            return event.setAndContinue(software.bernie.geckolib.animation.RawAnimation.begin().thenLoop("idle"));
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object o) {
        return this.age;
    }
}