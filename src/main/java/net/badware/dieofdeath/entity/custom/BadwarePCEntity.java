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
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
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
                return;
            }
        }

        if (this.age % 2 == 0 && ownerUuid != null) {
            drawKillerChains();
        }

        if (windupTicks > 0) {
            windupTicks--;
            if (windupTicks == 0 && this.getVariant() == 8) {
                if (ownerUuid != null && this.getWorld() instanceof ServerWorld serverWorld) {
                    Entity owner = serverWorld.getEntity(ownerUuid);
                    if (owner instanceof BadwareEntity badware) {
                        SoundEvent spawnSound = switch (badware.computerPlacementCount % 7) {
                            case 0 -> ModSounds.AEROWARE_PC_SPAWN_1;
                            case 1 -> ModSounds.AEROWARE_PC_SPAWN_2;
                            case 2 -> ModSounds.AEROWARE_PC_SPAWN_3;
                            case 3 -> ModSounds.AEROWARE_PC_SPAWN_4;
                            case 4 -> ModSounds.AEROWARE_PC_SPAWN_5;
                            case 5 -> ModSounds.AEROWARE_PC_SPAWN_6;
                            default -> ModSounds.AEROWARE_PC_SPAWN_7;
                        };
                        this.getWorld().playSound(null, this.getBlockPos(), spawnSound, SoundCategory.HOSTILE, 5.0f, 1.0f);
                        badware.computerPlacementCount++;
                    }
                }
            }
            return;
        }

        if (this.age % 20 == 0) {
            int nearbyPCCount = this.getWorld().getEntitiesByClass(
                    BadwarePCEntity.class,
                    this.getBoundingBox().expand(28.0),
                    pc -> !pc.isRemoved()
            ).size();

            if (nearbyPCCount > 0) {
                this.getWorld().getEntitiesByClass(
                        LivingEntity.class,
                        this.getBoundingBox().expand(28.0),
                        entity -> (entity instanceof ArtfulEntity || entity instanceof BadwareEntity || entity instanceof PursuerEntity) && entity.isAlive()).forEach(killer -> {
                    var speedAttr = killer.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
                    if (speedAttr != null) {
                        String modifierId = "badware_pc_speed_" + this.getUuid();
                        Identifier modifierKey = Identifier.of("dieofdeath", modifierId);

                        speedAttr.removeModifier(modifierKey);

                        double bonus = nearbyPCCount * 0.04;
                        speedAttr.addTemporaryModifier(new net.minecraft.entity.attribute.EntityAttributeModifier(
                                modifierKey,
                                bonus,
                                net.minecraft.entity.attribute.EntityAttributeModifier.Operation.ADD_VALUE
                        ));
                    }
                });
            }
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

    private void drawKillerChains() {
        if (this.getWorld().isClient || this.windupTicks > 0) return;
        if (this.age % 2 != 0) return;

        ServerWorld serverWorld = (ServerWorld) this.getWorld();

        this.getWorld().getEntitiesByClass(
                LivingEntity.class,
                this.getBoundingBox().expand(28.0),
                entity -> (entity instanceof ArtfulEntity || entity instanceof BadwareEntity || entity instanceof PursuerEntity) && entity.isAlive()
        ).forEach(killer -> {
            Vec3d start = Vec3d.ofCenter(this.getBlockPos()).add(0, 0.5, 0);
            Vec3d end = killer.getPos().add(0, 1.5, 0);

            Vec3d direction = end.subtract(start);
            double distance = direction.length();
            Vec3d normDir = direction.normalize();

            for (double d = 0; d < distance; d += 1.0) {
                Vec3d particlePos = start.add(normDir.multiply(d));
                serverWorld.spawnParticles(
                        ParticleTypes.GLOW,
                        particlePos.x, particlePos.y, particlePos.z,
                        1, 0.1, 0.1, 0.1, 0.0
                );
            }
        });
    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (windupTicks > 0) return false;

        boolean result = super.damage(source, amount);

        if (source.getAttacker() instanceof PlayerEntity) {
            this.customGage += (amount / 25.0f) * 100.0f;
        }

        if (this.getHealth() <= 2.5f || this.customGage >= 90.0f) {
            this.triggerExplosion();
            return result;
        }

        if (!(source.getAttacker() instanceof PlayerEntity) &&
                !source.isOf(net.minecraft.entity.damage.DamageTypes.EXPLOSION) &&
                !source.isOf(net.minecraft.entity.damage.DamageTypes.OUT_OF_WORLD) &&
                !source.isOf(net.minecraft.entity.damage.DamageTypes.GENERIC_KILL)) {
            return false;
        }

        return result;
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
                var speedAttr = badware.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
                if (speedAttr != null) {
                    String modifierId = "badware_pc_speed_" + this.getUuid();
                    Identifier modifierKey = Identifier.of("dieofdeath", modifierId);
                    speedAttr.removeModifier(modifierKey);
                }

                    boolean isAeroware = this.getVariant() == 8;
                if (this.getHealth() <= 0 || this.customGage >= 90.0f) {
                    SoundEvent explodeSound = isAeroware
                            ? ModSounds.AEROWARE_PC_EXPLODE
                            : ModSounds.BADWARE_PC_EXPLODE_BY_PLAYER;
                    this.getWorld().playSound(null, this.getBlockPos(), explodeSound, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    badware.sendMessage(Text.literal("§c[A COMPUTER WAS ELIMINATED]"));
                } else {
                    SoundEvent explodeSound = isAeroware
                            ? ModSounds.AEROWARE_PC_EXPLODE
                            : ModSounds.BADWARE_PC_EXPLODE;

                    SoundEvent teleportSound = isAeroware
                            ? ModSounds.AEROWARE_RIFT_TELEPORT
                            : ModSounds.RIFT_TELEPORT;

                    this.getWorld().playSound(null, this.getBlockPos(), explodeSound, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    this.getWorld().playSound(null, this.getBlockPos(), teleportSound, SoundCategory.HOSTILE, 1.0f, 1.0f);
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