package net.badware.dieofdeath.entity.custom;

import net.badware.dieofdeath.DieOfDeath;
import net.badware.dieofdeath.effect.ModEffects;
import net.badware.dieofdeath.entity.client.ClientMusicHandler;
import net.badware.dieofdeath.item.ModItems;
import net.badware.dieofdeath.sound.ModSounds;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;
import net.badware.dieofdeath.entity.client.LMSDiscCache;

import java.util.List;
import java.util.Map;

public class PursuerEntity extends HostileEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(PursuerEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private int howlTicks = 0;
    private int cleaveTicks = 0;
    private int swingTicks = 0;
    public transient boolean isThemePlayingClient = false;
    public boolean isCleaving() {
        return this.dataTracker.get(IS_CLEAVING);
    }
    public boolean isThemePlaying = false;
    private int swingCooldown = 0;
    private int cleaveCooldown = 0;
    private int howlCooldown = 0;

    public PursuerEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.RAIL, 0.0F);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
        this.setPathfindingPenalty(PathNodeType.OPEN, 0.0F);
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
    private static final TrackedData<Boolean> LMS_SUPPRESSED = DataTracker.registerData(PursuerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private final LMSDiscCache lmsDiscCache = new LMSDiscCache();

    private static final Map<String, Identifier> ADVANCEMENT_IDS = new java.util.HashMap<>();

    private static Identifier getAdvancementId(String name) {
        return ADVANCEMENT_IDS.computeIfAbsent(name, n -> Identifier.of(DieOfDeath.MOD_ID, n));
    }

    public void setThemePlaying(boolean playing) {
        this.isThemePlaying = playing;
    }

    public int getVariant() {
        int v = this.dataTracker.get(VARIANT);
        return v == -1 ? 0 : v;
    }

    public void setVariant(int variant) {
        this.dataTracker.set(VARIANT, variant);
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
            this.setVariant(nbt.getInt("variant"));
        }
    }

    public boolean isLmsSuppressed() {
        return this.dataTracker.get(LMS_SUPPRESSED);
    }

    public void setLmsSuppressed(boolean suppressed) {
        this.dataTracker.set(LMS_SUPPRESSED, suppressed);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.swingCooldown > 0) this.swingCooldown--;
        if (this.cleaveCooldown > 0) this.cleaveCooldown--;
        if (this.howlCooldown > 0) this.howlCooldown--;

        if (this.getWorld().isClient) {
            this.checkAndPlayChaseMusic();
        }
        if (!this.getWorld().isClient() && this.isAlive() && this.getTarget() != null) {
            if (this.horizontalCollision && this.isOnGround()) {
                this.getJumpControl().setActive();
            }
        }
        if (!this.getWorld().isClient && this.age % 60 == 0 && this.isAlive()) {
            List<VillagerEntity> victims = this.getWorld().getEntitiesByClass(
                    VillagerEntity.class,
                    this.getBoundingBox().expand(16.0),
                    Entity::isAlive
            );

            if (!victims.isEmpty()) {
                for (VillagerEntity villager : victims) {
                    Vec3d fleeVec = villager.getPos().subtract(this.getPos()).normalize();
                    Vec3d targetPos = villager.getPos().add(fleeVec.multiply(10.0));
                    villager.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(targetPos, 0.7f, 0));
                    villager.getBrain().remember(MemoryModuleType.IS_PANICKING, true, 100L);
                }
            }
        }

        if (this.getWorld().isClient && this.getVariant() == 15 && this.isAlive()) {
            if (this.random.nextFloat() < 0.4f) {
                float bodyYaw = this.bodyYaw;
                double radians = Math.toRadians(bodyYaw);

                double offsetX = -Math.cos(radians) * 0.4 + Math.sin(radians) * 0.6;
                double offsetZ = -Math.sin(radians) * 0.4 - Math.cos(radians) * 0.6;
                double sparkY = this.getY() + 0.5 + (this.random.nextDouble() * 2.0);

                this.getWorld().addParticle(
                        net.badware.dieofdeath.particle.ModParticles.GREEN_STAR,
                        this.getX() + offsetX,
                        sparkY,
                        this.getZ() + offsetZ,
                        0, 0.02, 0
                );
            }
        }

        if (!this.getWorld().isClient() && this.isAlive() && this.age % 40 == 0) {
            this.setLmsSuppressed(lmsDiscCache.isLMSDiscNearby(this.getWorld(), this.getBlockPos()));
        }
    }

    private void checkAndPlayChaseMusic() {
        if (this.isAlive()) {
            ClientMusicHandler.handlePursuerMusic(this);
        }
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, -1);
        builder.add(IS_CLEAVING, false);
        builder.add(IS_HOWLING, false);
        builder.add(LMS_SUPPRESSED, false);
    }

    @Override
    public net.minecraft.entity.EntityData initialize(net.minecraft.world.ServerWorldAccess world, net.minecraft.world.LocalDifficulty difficulty, net.minecraft.entity.SpawnReason spawnReason, @org.jetbrains.annotations.Nullable net.minecraft.entity.EntityData entityData) {
        entityData = super.initialize(world, difficulty, spawnReason, entityData);

        boolean hasVariantBeenSet = this.dataTracker.get(VARIANT) != -1;

        if (!hasVariantBeenSet) {
            if (spawnReason != SpawnReason.SPAWN_EGG) {
                if (this.random.nextBoolean()) {
                    this.setVariant(0);
                } else {
                    int[] weights = {50, 45, 40, 35, 33, 32, 30, 25, 23, 20, 17, 15, 12, 10, 5};
                    int totalWeight = 0;
                    for (int w : weights) totalWeight += w;

                    int roll = this.random.nextInt(totalWeight);
                    int cursor = 0;

                    for (int i = 0; i < weights.length; i++) {
                        cursor += weights[i];
                        if (roll < cursor) {
                            this.setVariant(i + 1);
                            break;
                        }
                    }
                }
            } else {
                this.setVariant(this.random.nextInt(16));
            }
        }

        return entityData;
    }

    public void performCleave() {
        if (this.getWorld().isClient) return;

        this.dataTracker.set(IS_CLEAVING, true);

        if (this.getVariant() == 14) {
            this.triggerAnim("attack_controller", "thec_cleave");
        } else if (this.getVariant() == 13) {
            this.triggerAnim("attack_controller", "seesaws_cleave");
        } else if (this.getVariant() == 12) {
            this.triggerAnim("attack_controller", "clawsguy_cleave");
        } else if (this.getVariant() == 8) {
            this.triggerAnim("attack_controller", "mequot_cleave");
        } else {
            this.triggerAnim("attack_controller", "cleave");
        }

        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                ModSounds.PURSUER_CLEAVE_START, this.getSoundCategory(), 2.0f, 1.0f);

        int boostDuration = this.isSprinting() ? 40 : 20;
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, boostDuration, 1));

        this.cleaveTicks = 20;
    }

    private void applyCleaveDamage() {
        Box hitbox = this.getBoundingBox().expand(1.86, 0.0, 1.86).offset(this.getRotationVec(1.0f).multiply(2.0));
        this.getWorld().getEntitiesByClass(LivingEntity.class, hitbox, EntityPredicates.EXCEPT_SPECTATOR).forEach(target -> {
            if (target != this) {
                target.damage(this.getDamageSources().mobAttack(this), 4.0f);
                target.addStatusEffect(new StatusEffectInstance(ModEffects.BLEED, 40, 1));
                target.takeKnockback(0.5, -this.getX() + target.getX(), -this.getZ() + target.getZ());
            }
        });

        this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 40, 2));
    }

    public void performHowl() {
        this.dataTracker.set(IS_HOWLING, true);
        if (this.getVariant() == 14) {
            this.triggerAnim("attack_controller", "thec_howl");
        } else if (this.getVariant() == 12 || this.getVariant() == 13) {
            this.triggerAnim("attack_controller", "clawsguy_howl");
        } else {
            this.triggerAnim("attack_controller", "howl");
        }

        int variant = this.getVariant();

        if (variant == 14) {
            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                    ModSounds.THEC_HOWL, this.getSoundCategory(), 2.0f, 1.0f);
        }

        if (variant == 12 || variant == 13) {
            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                    ModSounds.CLAWSGUY_HOWL, this.getSoundCategory(), 2.0f, 1.0f);
        }

        if (variant == 11) {
            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                    ModSounds.PURSUER_SHOCKWAVE, this.getSoundCategory(), 2.0f, 1.0f);
            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                    ModSounds.PURSUER_SCREAM, this.getSoundCategory(), 2.0f, 1.5f);
        }

        if (variant == 10) {
            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                    ModSounds.PURSUER_SHOCKWAVE, this.getSoundCategory(), 2.0f, 1.0f);
            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                    ModSounds.PURSUER_SCREAM, this.getSoundCategory(), 2.0f, 0.5f);
        }

        if (variant == 8 || variant == 9) {
            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                    ModSounds.MEQUOT_HOWL, this.getSoundCategory(), 2.0f, 1.0f);
        }

        else if (variant == 7) {
            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                    ModSounds.STALKER_HOWL, this.getSoundCategory(), 2.0f, 1.0f);
        }

        else if (variant == 6) {
            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                    ModSounds.ZOMBIE_2_HOWL, this.getSoundCategory(), 2.0f, 1.0f);

        } else if (variant == 4 || variant == 5) {
            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                    ModSounds.ZOMBIE_HOWL, this.getSoundCategory(), 3.0f, 1.0f);

        } else if (variant == 3) {
            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                    ModSounds.PHANTASM_HOWL, this.getSoundCategory(), 2.0f, 1.0f);
            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                    ModSounds.PURSUER_SHOCKWAVE, this.getSoundCategory(), 2.0f, 1.0f);
        } else {
            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                    ModSounds.PURSUER_SCREAM, this.getSoundCategory(), 2.0f, 1.0f);
            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                    ModSounds.PURSUER_SHOCKWAVE, this.getSoundCategory(), 2.0f, 1.0f);
        }

        this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 40, 10));

        this.getWorld().getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(20.0),
                entity -> entity != this).forEach(target -> {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 120, 3));
        });

        this.howlTicks = 40;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.2D, true));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(5, new LookAroundGoal(this));

        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, IronGolemEntity.class, true,
                (entity) -> this.getVariant() != 8));

        this.targetSelector.add(3, new ActiveTargetGoal<>(this, VillagerEntity.class, true,
                (entity) -> this.getVariant() != 8));
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (this.dataTracker.get(IS_CLEAVING) || this.dataTracker.get(IS_HOWLING)) return false;
        if (this.swingTicks == 0 && this.swingCooldown <= 0) {
            this.swingTicks = 23;
            this.swingCooldown = 20;

            if (this.getVariant() == 14) this.triggerAnim("attack_controller", "thec_swing");
            else if (this.getVariant() == 13) this.triggerAnim("attack_controller", "seesaws_swing");
            else if (this.getVariant() == 12) this.triggerAnim("attack_controller", "clawsguy_swing");
            else if (this.getVariant() == 7) this.triggerAnim("attack_controller", "stalker_swing");
            else if (this.getVariant() == 6) this.triggerAnim("attack_controller", "zombie_2_swing");
            else this.triggerAnim("attack_controller", "swing");

            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                   this.getVariant() == 15 ? ModSounds.BLING_HIT : this.getVariant() == 13 ? ModSounds.SEESAWS_HIT : (this.getVariant() == 12 ? ModSounds.CLAWS_PIERCE : ModSounds.PURSUER_SWINGING),
                    this.getSoundCategory(), 1.0f, 1.0f);

            if (this.getVariant() == 15 && !this.getWorld().isClient) {
                for (int i = 0; i < 4; i++) {
                    net.minecraft.entity.ItemEntity point = new net.minecraft.entity.ItemEntity(this.getWorld(),
                            target.getX(), target.getY() + 0.5, target.getZ(),
                            new net.minecraft.item.ItemStack(net.badware.dieofdeath.item.ModItems.BLING_POINT));

                    point.setPickupDelay(32767);

                    point.setVelocity(
                            (this.random.nextDouble() - 0.5) * 0.4,
                            0.3,
                            (this.random.nextDouble() - 0.5) * 0.4
                    );

                    NbtCompound nbt = new NbtCompound();
                    point.writeCustomDataToNbt(nbt);
                    nbt.putShort("Age", (short) 5860);
                    point.readCustomDataFromNbt(nbt);

                    this.getWorld().spawnEntity(point);
                }
            }

            return true;
        }

        if (this.swingTicks > 10) {
            float damage = (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);

            if (this.getVariant() == 12 && (target instanceof PursuerEntity || target instanceof BadwareEntity || target instanceof ArtfulEntity)) {
                damage *= 2.0f;
            }

            return target.damage(this.getDamageSources().mobAttack(this), damage);
        }

        return false;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.getVariant() == 12) {
            Entity attacker = source.getAttacker();

            if (attacker != null && (attacker instanceof PursuerEntity || attacker instanceof BadwareEntity || attacker instanceof  ArtfulEntity)) {

                amount *= 0.8f;
            }
        }

        return super.damage(source, amount);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "movement_controller", 5, state -> {
            if (this.dataTracker.get(IS_HOWLING) || this.dataTracker.get(IS_CLEAVING)) {
                return PlayState.STOP;
            }

            int variant = this.getVariant();
            state.getController().setAnimationSpeed(1.0);

            if (state.isMoving()) {
                boolean isChasing = this.getTarget() != null || this.isAttacking();
                String anim;

                if (variant == 15) {
                    if (isChasing) {
                        anim = "animation.pursuer.bling.run";
                    }  else {
                        anim = "animation.pursuer.bling.walk";
                    }
                } else if (variant == 14) {
                    if (isChasing) {
                        anim = "animation.pursuer.thec.run";
                    }  else {
                        anim = "animation.pursuer.thec.walk";
                    }
                } else if (variant == 13) {
                    if (isChasing) {
                        anim = "animation.pursuer.seesaws.run";
                    } else {
                        anim = "animation.pursuer.seesaws.walk";
                    }
                } else if (variant == 12) {
                    if (isChasing) {
                        anim = "animation.pursuer.clawsguy.run";
                    } else {
                        anim = "animation.pursuer.clawsguy.walk";
                    }
                } else if (variant == 8 || variant == 9) {
                    if (isChasing) {
                        anim = "animation.pursuer.mequot.run";
                    } else {
                        anim = "animation.pursuer.mequot.walk";
                    }
                } else if (variant == 7) {
                    anim = "animation.pursuer.stalker.run";
                } else if (variant == 6) {
                    anim = "animation.pursuer.zombie_2.run";
                } else if (variant == 5) {
                    if (isChasing) {
                        anim = "animation.pursuer.zombie_1.run";
                    } else {
                        anim = "animation.pursuer.zombie_1.walk";
                    }
                } else if (variant == 2 || variant == 10 || variant == 11) {
                    anim = "animation.pursuer.classic.walk";
                } else {
                    anim = "animation.pursuer.run";
                }

                return state.setAndContinue(RawAnimation.begin().thenLoop(anim));
            }
            String idleAnim = "animation.pursuer.idle";
            if (variant == 9) {
                idleAnim = "animation.pursuer.iquot.idle";
            } else if (variant == 7) {
                idleAnim = "animation.pursuer.stalker.idle";
            } else if (variant == 6) {
                idleAnim = "animation.pursuer.zombie_2.idle";
            } else if (variant == 5) {
                idleAnim = "animation.pursuer.zombie_1.idle";
            } else if (variant == 2 || variant == 10 || variant == 11) {
                idleAnim = "animation.pursuer.classic.idle";
            }

            return state.setAndContinue(RawAnimation.begin().thenLoop(idleAnim));
        }));

        controllers.add(new AnimationController<>(this, "attack_controller", 2, state -> PlayState.STOP)
                .triggerableAnim("swing", RawAnimation.begin().thenPlay("animation.pursuer.swing"))
                .triggerableAnim("howl", RawAnimation.begin().thenPlay("animation.pursuer.howl"))
                .triggerableAnim("cleave", RawAnimation.begin().thenPlay("animation.pursuer.cleave"))

                .triggerableAnim("zombie_2_swing", RawAnimation.begin().thenPlay("animation.pursuer.zombie_2.swing"))
                .triggerableAnim("stalker_swing", RawAnimation.begin().thenPlay("animation.pursuer.stalker.swing"))

                .triggerableAnim("mequot_cleave", RawAnimation.begin().thenPlay("animation.pursuer.mequot.cleave"))

                .triggerableAnim("clawsguy_swing", RawAnimation.begin().thenPlay("animation.pursuer.clawsguy.swing"))
                .triggerableAnim("clawsguy_howl", RawAnimation.begin().thenPlay("animation.pursuer.clawsguy.howl"))
                .triggerableAnim("clawsguy_cleave", RawAnimation.begin().thenPlay("animation.pursuer.clawsguy.cleave"))

                .triggerableAnim("seesaws_swing", RawAnimation.begin().thenPlay("animation.pursuer.seesaws.swing"))
                .triggerableAnim("seesaws_cleave", RawAnimation.begin().thenPlay("animation.pursuer.seesaws.cleave"))

                .triggerableAnim("thec_swing", RawAnimation.begin().thenPlay("animation.pursuer.thec.swing"))
                .triggerableAnim("thec_howl", RawAnimation.begin().thenPlay("animation.pursuer.thec.howl"))
                .triggerableAnim("thec_cleave", RawAnimation.begin().thenPlay("animation.pursuer.thec.cleave"))
        );
    }

    @Override
    public void mobTick() {
        super.mobTick();

        if (this.age % 10 == 0 && this.getTarget() != null && !this.dataTracker.get(IS_CLEAVING) && !this.dataTracker.get(IS_HOWLING)) {
            double distance = this.squaredDistanceTo(this.getTarget());
            if (distance > 25 && distance < 100) {
                int randomMove = this.random.nextInt(100);
                if (randomMove < 5 && this.howlCooldown <= 0) {
                    this.performHowl();
                    this.howlCooldown = 700;
                }
                else if (randomMove < 15 && this.cleaveCooldown <= 0) {
                    this.performCleave();
                    this.cleaveCooldown = 400;
                }
            }
        }

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

        if (this.swingTicks > 0) {
            this.swingTicks--;

            if (this.swingTicks > 20) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10, 2, false, false));
            }

            else if (this.swingTicks > 10) {
                Vec3d lungeDir = this.getRotationVec(1.0f).multiply(0.3);
                this.setVelocity(lungeDir.x, this.getVelocity().y, lungeDir.z);

                Box hitbox = this.getBoundingBox().expand(0.5);
                this.getWorld().getEntitiesByClass(LivingEntity.class, hitbox, EntityPredicates.EXCEPT_SPECTATOR).forEach(target -> {
                    if (target != this && this.swingTicks > 10) {
                        if (this.tryAttack(target)) {
                            this.swingTicks = 10;
                        }
                    }
                });
            }

            else if (this.swingTicks > 0) {
                this.removeStatusEffect(StatusEffects.SPEED);
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10, 1, false, false));
            }
        }
    }

    @Override
    protected net.minecraft.sound.SoundEvent getDeathSound() {
        int variant = this.getVariant();
        if (variant == 15) {
            return ModSounds.BLING_STUNNED;
        }
        else if (variant == 14) {
            return ModSounds.THEC_STUNNED;
        }
        else if (variant == 8 || variant == 9) {
            return ModSounds.MEQUOT_STUNNED;
        }
        else if (variant == 7) {
            return ModSounds.STALKER_STUNNED;
        }
        else if (variant == 4 || variant == 5 || variant == 6) {
            return ModSounds.ZOMBIE_STUNNED;
        }
        return ModSounds.PURSUER_STUNNED;
    }

    @Override
    protected void dropLoot(DamageSource damageSource, boolean causedByPlayer) {
        super.dropLoot(damageSource, causedByPlayer);

        if (causedByPlayer && damageSource.getAttacker() instanceof ServerPlayerEntity player) {
            var scoreboard = player.getScoreboard();

            var pursuerObj = scoreboard.getNullableObjective("pursuer_kills");
            if (pursuerObj == null) {
                pursuerObj = scoreboard.addObjective("pursuer_kills", net.minecraft.scoreboard.ScoreboardCriterion.DUMMY, Text.literal("Pursuer Kills"), net.minecraft.scoreboard.ScoreboardCriterion.RenderType.INTEGER, true, null);
            }
            var badwareObj = scoreboard.getNullableObjective("badware_kills");
            if (badwareObj == null) {
                badwareObj = scoreboard.addObjective("badware_kills", net.minecraft.scoreboard.ScoreboardCriterion.DUMMY, Text.literal("Badware Kills"), net.minecraft.scoreboard.ScoreboardCriterion.RenderType.INTEGER, true, null);
            }

            var pursuerScore = scoreboard.getOrCreateScore(player, pursuerObj);
            int currentPursuerKills = pursuerScore.getScore() + 1;
            pursuerScore.setScore(currentPursuerKills);

            int currentBadwareKills = scoreboard.getOrCreateScore(player, badwareObj).getScore();
            int totalKillersKilled = currentBadwareKills + currentPursuerKills;

            this.dropStack(new ItemStack(ModItems.PURSUER_CLEAVE));
            grantAdvancement(player, "any_killer_kill");
            if (totalKillersKilled % 5 == 0) {
                player.giveItemStack(new ItemStack(ModItems.ETERNITY_V2_MUSIC_DISC));
            }

            if ((this.getVariant() == 0)) {

                grantAdvancement(player, "pursuer_first_kill");
                AdvancementEntry PursuerAdv = player.getServer().getAdvancementLoader()
                        .get(Identifier.of(DieOfDeath.MOD_ID, "pursuer_first_kill"));

                if (PursuerAdv != null) {
                    AdvancementProgress progress = player.getAdvancementTracker().getProgress(PursuerAdv);

                    if (progress.isDone()) {
                        this.dropStack(new ItemStack(ModItems.ETERNITY_V2_MUSIC_DISC));
                    }
                }

                grantAdvancement(player, "pursuer_first_kill");
            }
            if (this.getVariant() > 0) {
                AdvancementEntry variantAdv = player.getServer().getAdvancementLoader()
                        .get(Identifier.of(DieOfDeath.MOD_ID, "pursuer_variant_kill"));

                if (variantAdv != null) {
                    AdvancementProgress progress = player.getAdvancementTracker().getProgress(variantAdv);

                    if (!progress.isDone()) {
                        this.dropStack(new ItemStack(ModItems.ETERNITY_V2_MUSIC_DISC));
                    }
                }

                grantAdvancement(player, "pursuer_variant_kill");
            }

            if (totalKillersKilled % 2 == 0) {
                grantAdvancement(player, "double_trouble");
                player.giveItemStack(new ItemStack(ModItems.ETERNITY));
            }

            if (totalKillersKilled % 8 == 0) {
                grantAdvancement(player, "one_bounce");
                player.giveItemStack(new ItemStack(ModItems.ONE_BOUNCE));
            }
        }
    }

    private void grantAdvancement(ServerPlayerEntity player, String name) {
        AdvancementEntry advancementEntry = player.getServer().getAdvancementLoader()
                .get(getAdvancementId(name));

        if (advancementEntry != null) {
            AdvancementProgress progress = player.getAdvancementTracker().getProgress(advancementEntry);
            for (String criterion : progress.getUnobtainedCriteria()) {
                player.getAdvancementTracker().grantCriterion(advancementEntry, criterion);
            }
        }
    }

    @Override
    public Text getDisplayName() {
        int variant = this.dataTracker == null ? 0 : this.getVariant();
        return switch (variant) {
            case 1 -> Text.literal("Avoider");
            case 2 -> Text.literal("Classic");
            case 3 -> Text.literal("Phantasm");
            case 4 -> Text.literal("Zombie");
            case 5 -> Text.literal("Zombie_1");
            case 6 -> Text.literal("Zombie_2");
            case 7 -> Text.literal("The Stalker");
            case 8 -> Text.literal("MeQuot");
            case 9 -> Text.literal("IQuot");
            case 10 -> Text.literal("Maze Guy");
            case 11 -> Text.literal("Hardest Game");
            case 12 -> Text.literal("Clawsguy");
            case 13 -> Text.literal("Seesaws");
            case 14 -> Text.literal("Thec");
            case 15 -> Text.literal("Bling Pursuer");
            default -> Text.translatable("entity.dieofdeath.pursuer");
        };
    }

    @Override
    protected int getXpToDrop() {
        if (this.getVariant() == 15) {
            return 40;
        }
        return 20;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}