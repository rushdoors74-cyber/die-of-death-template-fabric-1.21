package net.badware.dieofdeath.entity.custom;

import net.badware.dieofdeath.sound.ModSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Comparator;
import java.util.List;

public class ArtfulMusicBoxEntity extends net.minecraft.entity.mob.PathAwareEntity implements GeoAnimatable {

    private int lifetimeTicks = 0;
    private LivingEntity currentTarget = null;
    private int ticksSinceBeam = -1;
    private int overdriveLifetime = 0;

    private static final TrackedData<Boolean> OVERDRIVE_MODE =
            DataTracker.registerData(ArtfulMusicBoxEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ArtfulMusicBoxEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(true);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(OVERDRIVE_MODE, false);
    }

    public void setOverdriveMode(boolean overdrive) {
        this.dataTracker.set(OVERDRIVE_MODE, overdrive);
    }

    public boolean isOverdriveMode() {
        return this.dataTracker.get(OVERDRIVE_MODE);
    }

    @Override
    public boolean cannotDespawn() {
        return true;
    }

    @Override
    public boolean isPersistent() {
        return true;
    }

    public static DefaultAttributeContainer.Builder createMusicBoxAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 150.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient) return;

        lifetimeTicks++;

        if (this.isOverdriveMode()) {
            overdriveLifetime++;

            if (overdriveLifetime >= 140) {
                this.discard();
                return;
            }
        }

        if (this.isOverdriveMode()) {
            List<ArtfulEntity> owners = this.getWorld().getEntitiesByClass(
                    ArtfulEntity.class,
                    this.getBoundingBox().expand(25.0),
                    ArtfulEntity::isAlive
            );

            if (!owners.isEmpty()) {
                ArtfulEntity artful = owners.get(0);
                double targetY = artful.getY() + artful.getHeight() + 1.2;
                this.setPosition(artful.getX(), targetY, artful.getZ());
                this.setVelocity(Vec3d.ZERO);
                this.velocityModified = true;
            } else {
                this.discard();
                return;
            }
        }

        if (lifetimeTicks >= 153.85999999999999) {
            lifetimeTicks = 1;
        }

        if (lifetimeTicks == 1) {
            this.getWorld().playSound(null, this.getBlockPos(), ModSounds.ARTFUL_COPYWRITE_AMBIENCE,
                    net.minecraft.sound.SoundCategory.HOSTILE, 1.0f, 1.0f);
        }

        if (isOverdriveMode()) return;

        if (lifetimeTicks < 20) return;
        if (lifetimeTicks % 5 != 0) {
            if (ticksSinceBeam >= 0) {
                ticksSinceBeam++;
                applyPostBeamIndication();
            }
            return;
        }

        Box scanBox = this.getBoundingBox().expand(7.0);

        List<LivingEntity> candidates = this.getWorld().getEntitiesByClass(LivingEntity.class, scanBox, entity -> {
            if (entity == this || !entity.isAlive()) return false;
            if (entity instanceof PursuerEntity || entity instanceof BadwareEntity
                    || entity instanceof ArtfulEntity || entity instanceof ArtfulMusicBoxEntity
                    || entity instanceof ArtfulWallEntity || entity instanceof ArtfulPuppetEntity) return false;
            if (entity instanceof PlayerEntity p && (p.isCreative() || p.isSpectator())) return false;
            if (entity instanceof net.minecraft.entity.mob.Monster) {
                return false;
            }
            return true;
        });

        if (!candidates.isEmpty()) {
            currentTarget = candidates.stream()
                    .min(Comparator.comparingDouble(e -> e.squaredDistanceTo(this)))
                    .orElse(null);
        } else {
            currentTarget = null;
        }

        if (currentTarget != null) {
            currentTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10, 2, false, false));
            spawnMusicBoxBeam();
            ticksSinceBeam = 0;
        } else if (ticksSinceBeam >= 0) {
            ticksSinceBeam++;
            applyPostBeamIndication();
        }
    }

    private void applyPostBeamIndication() {
        if (ticksSinceBeam > 60) {
            ticksSinceBeam = -1;
            return;
        }
        if (currentTarget != null && currentTarget.isAlive()) {
            currentTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 5, 0, false, false));
        }
    }

    private void spawnMusicBoxBeam() {
        if (!(this.getWorld() instanceof ServerWorld serverWorld) || currentTarget == null) return;

        Vec3d beamStart = this.getPos().add(0, 0.35, 0);
        Vec3d beamEnd = currentTarget.getPos().add(0, currentTarget.getHeight() / 2.0, 0);
        Vec3d originDirection = beamEnd.subtract(beamStart);

        double beamDistance = originDirection.length();
        Vec3d unitVector = originDirection.normalize();

        for (double d = 0; d < beamDistance; d += 0.4) {
            Vec3d particlePoint = beamStart.add(unitVector.multiply(d));
            serverWorld.spawnParticles(ParticleTypes.FIREWORK,
                    particlePoint.x, particlePoint.y, particlePoint.z,
                    1, 0.0, 0.0, 0.0, 0.0);
        }
    }

    @Override public boolean isPushable() { return false; }
    @Override protected void pushAway(net.minecraft.entity.Entity entity) {}
    @Override public Iterable<net.minecraft.item.ItemStack> getArmorItems() { return List.of(); }
    @Override public net.minecraft.item.ItemStack getEquippedStack(net.minecraft.entity.EquipmentSlot slot) { return net.minecraft.item.ItemStack.EMPTY; }
    @Override public void equipStack(net.minecraft.entity.EquipmentSlot slot, net.minecraft.item.ItemStack stack) {}
    @Override public net.minecraft.util.Arm getMainArm() { return net.minecraft.util.Arm.RIGHT; }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new software.bernie.geckolib.animation.AnimationController<>(this, "controller", 0, event -> {
            event.setAndContinue(software.bernie.geckolib.animation.RawAnimation.begin().thenLoop("idle"));
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public void takeKnockback(double strength, double x, double z) {
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    public void setVelocity(Vec3d velocity) {
        if (this.isOverdriveMode()) {
            super.setVelocity(velocity);
        } else {
            super.setVelocity(Vec3d.ZERO);
        }
    }

    @Override
    public double getTick(Object animatable) {
        return this.age;
    }
}