package net.badware.dieofdeath.entity.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class ArtfulWallEntity extends LivingEntity implements GeoAnimatable {

    public static final TrackedData<Boolean> IS_TRANSPARENT = DataTracker.registerData(ArtfulWallEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(ArtfulWallEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private int lifetimeTicks = 0;
    private int transparencyCooldown = 0;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ArtfulWallEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(true);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(IS_TRANSPARENT, false);
        builder.add(VARIANT, 0);
    }

    public int getVariant() {
        int v = this.dataTracker.get(VARIANT);
        return v == -1 ? 0 : v;
    }

    public void setVariant(int variant) {
        this.dataTracker.set(VARIANT, Math.max(0, variant));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("variant", this.getVariant());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("variant")) {
            // FIX: route through setVariant() so the clamp is applied on load too
            this.setVariant(nbt.getInt("variant"));
        }
    }

    public static DefaultAttributeContainer.Builder createWallAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient) return;

        lifetimeTicks++;

        if (lifetimeTicks <= 150) {
            this.setPosition(this.getX(), this.getY() + (2.0 / 150.0), this.getZ());
        }

        if (lifetimeTicks > 60) {
            this.setHealth(this.getHealth() - 0.01f);
        }

        if (this.getHealth() <= 0) {
            this.discard();
        }

        if (transparencyCooldown > 0) {
            transparencyCooldown--;
            if (transparencyCooldown == 0) {
                this.dataTracker.set(IS_TRANSPARENT, false);
            }
        }

        List<LivingEntity> touchingEntities = this.getWorld().getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(0.1),
                entity -> entity != this && entity.isAlive());

        for (LivingEntity entity : touchingEntities) {
            boolean isKiller = entity instanceof PursuerEntity || entity instanceof BadwareEntity || entity instanceof ArtfulEntity;
            boolean isInvisible = entity.hasStatusEffect(StatusEffects.INVISIBILITY);

            if ((isKiller || isInvisible) && transparencyCooldown <= 0) {
                DamageSource wallDamage = this.getDamageSources().magic();

                this.damage(wallDamage, 0.5f);

                this.transparencyCooldown = 60;
                this.dataTracker.set(IS_TRANSPARENT, true);
                break;
            }
        }

        if (lifetimeTicks > 60 && !this.dataTracker.get(IS_TRANSPARENT)) {
            List<PlayerEntity> nearbyPlayers = this.getWorld().getEntitiesByClass(PlayerEntity.class, this.getBoundingBox().expand(0.05),
                    player -> !player.isSpectator() && !player.isCreative());

            for (PlayerEntity player : nearbyPlayers) {
                double pushX = player.getX() - this.getX();
                double pushZ = player.getZ() - this.getZ();
                player.setVelocity(pushX * 0.2, player.getVelocity().y, pushZ * 0.2);
                player.velocityModified = true;
            }
        }
    }

    @Override
    public boolean isPushable() { return false; }

    @Override
    protected void pushAway(Entity entity) {}

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
    public void setVelocity(net.minecraft.util.math.Vec3d velocity) {
        if (this.age <= 20) {
            super.setVelocity(velocity);
        } else {
            super.setVelocity(net.minecraft.util.math.Vec3d.ZERO);
        }
    }

    @Override
    public Iterable<net.minecraft.item.ItemStack> getArmorItems() { return List.of(); }

    @Override
    public net.minecraft.item.ItemStack getEquippedStack(net.minecraft.entity.EquipmentSlot slot) { return net.minecraft.item.ItemStack.EMPTY; }

    @Override
    public void equipStack(net.minecraft.entity.EquipmentSlot slot, net.minecraft.item.ItemStack stack) {}

    @Override
    public net.minecraft.util.Arm getMainArm() { return net.minecraft.util.Arm.RIGHT; }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (lifetimeTicks <= 60) return false;
        return super.damage(source, amount);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new software.bernie.geckolib.animation.AnimationController<>(this, "controller", 0, event -> {
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