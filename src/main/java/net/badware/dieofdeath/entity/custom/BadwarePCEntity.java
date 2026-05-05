package net.badware.dieofdeath.entity.custom;

import net.badware.dieofdeath.sound.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Arm;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;

import java.util.UUID;

public class BadwarePCEntity extends LivingEntity implements GeoAnimatable {
    private UUID ownerUuid;
    private int windupTicks = 60;
    private float customGage = 0.0f;

    public BadwarePCEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 25.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0);
    }

    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(BadwarePCEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, -1);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient) return;

        if (this.age % 40 == 0 && ownerUuid != null) {
            Entity owner = ((ServerWorld) this.getWorld()).getEntity(ownerUuid);

            if (owner == null || !owner.isAlive()) {
                this.triggerExplosion();
            }
        }

        if (windupTicks > 0) {
            windupTicks--;
            return;
        }

        if (this.isSubmergedInWater() || this.isTouchingWater()) {
            if (this.age % 40 == 0) {
                this.damage(this.getDamageSources().magic(), 1.0f);

                this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                        net.minecraft.sound.SoundEvents.BLOCK_LAVA_EXTINGUISH,
                        SoundCategory.BLOCKS, 0.8f, 1.5f);
            }
        }
    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (windupTicks > 0) return false;

        if (source.getAttacker() instanceof PlayerEntity player) {
            this.customGage += (amount / 25.0f) * 100.0f;

            if (this.getHealth() - amount <= 2.5f || this.customGage >= 90.0f) {
                this.triggerExplosion();
            }
            if (source.isOf(net.minecraft.entity.damage.DamageTypes.OUT_OF_WORLD) ||
                    source.isOf(net.minecraft.entity.damage.DamageTypes.GENERIC_KILL)) {
                return super.damage(source, amount);
            }

            if (!(source.getAttacker() instanceof PlayerEntity) &&
                    !source.isOf(net.minecraft.entity.damage.DamageTypes.EXPLOSION)) {
                return false;
            }
        }
        return super.damage(source, amount);
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return java.util.Collections.emptyList();
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {

    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getVariant());
        if (this.ownerUuid != null) {
            nbt.putUuid("OwnerUUID", this.ownerUuid);
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setVariant(nbt.getInt("Variant"));
        if (nbt.containsUuid("OwnerUUID")) {
            this.ownerUuid = nbt.getUuid("OwnerUUID");
        }
    }

    public void triggerExplosion() {
        if (ownerUuid != null && this.getWorld() instanceof ServerWorld serverWorld) {
            Entity owner = serverWorld.getEntity(ownerUuid);
            if (owner instanceof BadwareEntity badware) {
                if (this.getHealth() <= 0 || this.customGage >= 90.0f) {
                    this.getWorld().playSound(null, this.getBlockPos(), ModSounds.BADWARE_PC_EXPLODE_BY_PLAYER, SoundCategory.BLOCKS, 1.0f, 1.0f);
                } else {
                    this.getWorld().playSound(null, this.getBlockPos(), ModSounds.BADWARE_PC_EXPLODE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    this.getWorld().playSound(null, this.getBlockPos(), ModSounds.RIFT_TELEPORT, SoundCategory.HOSTILE, 1.0f, 1.0f);
                }

                badware.damage(this.getDamageSources().explosion(this, this), 5.0f);
                badware.removeComputer(this.getBlockPos());
            }
        }
        this.discard();
    }

    public void setOwner(UUID uuid) {
        this.ownerUuid = uuid;
    }

    public int getVariant() {
        int v = this.dataTracker.get(VARIANT);
        return v == -1 ? 0 : v;
    }

    public void setVariant(int variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    private final AnimatableInstanceCache cache = software.bernie.geckolib.util.GeckoLibUtil.createInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public boolean canBeLeashed() {
        return false;
    }

    public boolean canCollideWith(Entity other) {
        return false;
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canBeHitByProjectile() {
        return true;
    }

    @Override
    public double getTick(Object o) {
        return 0;
    }

    @Override
    protected int getXpToDrop() {
        return 1;
    }

    @Override
    public boolean isGlowing() {
        return super.isGlowing();
    }
}