package net.badware.dieofdeath.entity.custom;

import net.badware.dieofdeath.DieOfDeath;
import net.badware.dieofdeath.effect.ModEffects;
import net.badware.dieofdeath.entity.ModEntities;
import net.badware.dieofdeath.entity.client.ClientMusicHandler;
import net.badware.dieofdeath.entity.client.LMSDiscCache;
import net.badware.dieofdeath.item.ModItems;
import net.badware.dieofdeath.sound.ModSounds;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
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

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public class ArtfulEntity extends HostileEntity implements GeoEntity {

    public static final TrackedData<Boolean> IS_SWINGING = DataTracker.registerData(ArtfulEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> IS_CASTING_WALL = DataTracker.registerData(ArtfulEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> IS_CASTING_COPYWRITE = DataTracker.registerData(ArtfulEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Integer> STORED_ENTITY_TYPE = DataTracker.registerData(ArtfulEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Boolean> IS_REPURPOSING = DataTracker.registerData(ArtfulEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> LMS_SUPPRESSED = DataTracker.registerData(ArtfulEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(ArtfulEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private final LMSDiscCache lmsDiscCache = new LMSDiscCache();
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private int swingTicks = -1;
    private int swingCooldown = 0;
    private boolean hasHitTarget = false;
    private int wallTicks = -1;
    private int wallCooldown = 0;
    public int copywriteTicks = -1;
    private int copywriteCooldown = 0;
    private int repurposeTicks = -1;
    private int repurposeCooldown = 0;
    private int overdriveTicksLeft = 0;
    public transient boolean isThemePlayingClient = false;
    public boolean isThemePlaying = false;
    private final java.util.List<ArtfulWallEntity> spawnedWalls = new java.util.ArrayList<>();
    private final java.util.List<ArtfulMusicBoxEntity> spawnedMusicBoxes = new java.util.ArrayList<>();

    public ArtfulEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.OPEN, 0.0F);
    }

    public void setThemePlaying(boolean playing) {
        this.isThemePlaying = playing;
    }

    private static final Map<String, Identifier> ADVANCEMENT_IDS = new java.util.HashMap<>();

    private static Identifier getAdvancementId(String name) {
        return ADVANCEMENT_IDS.computeIfAbsent(name, n -> Identifier.of(DieOfDeath.MOD_ID, n));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(IS_SWINGING, false);
        builder.add(IS_CASTING_WALL, false);
        builder.add(IS_CASTING_COPYWRITE, false);
        builder.add(STORED_ENTITY_TYPE, 0);
        builder.add(IS_REPURPOSING, false);
        builder.add(LMS_SUPPRESSED, false);
        builder.add(VARIANT, -1);
    }

    public int getVariant() {
        int v = this.dataTracker.get(VARIANT);
        return v == -1 ? 0 : v;
    }

    public void setVariant(int variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    @Override
    public boolean cannotDespawn() {
        return true;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("variant", this.getVariant());

        nbt.putInt("swingTicks", this.swingTicks);
        nbt.putInt("swingCooldown", this.swingCooldown);
        nbt.putInt("wallTicks", this.wallTicks);
        nbt.putInt("wallCooldown", this.wallCooldown);
        nbt.putInt("copywriteTicks", this.copywriteTicks);
        nbt.putInt("copywriteCooldown", this.copywriteCooldown);
        nbt.putInt("repurposeTicks", this.repurposeTicks);
        nbt.putInt("repurposeCooldown", this.repurposeCooldown);
        nbt.putInt("storedEntityType", this.dataTracker.get(STORED_ENTITY_TYPE));
        nbt.putInt("overdriveTicksLeft", this.overdriveTicksLeft);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("variant")) {
            this.setVariant(nbt.getInt("variant"));
        }

        this.swingTicks        = nbt.contains("swingTicks")        ? nbt.getInt("swingTicks")        : -1;
        this.swingCooldown     = nbt.contains("swingCooldown")     ? nbt.getInt("swingCooldown")     : 0;
        this.wallTicks         = nbt.contains("wallTicks")         ? nbt.getInt("wallTicks")         : -1;
        this.wallCooldown      = nbt.contains("wallCooldown")      ? nbt.getInt("wallCooldown")      : 0;
        this.copywriteTicks    = nbt.contains("copywriteTicks")    ? nbt.getInt("copywriteTicks")    : -1;
        this.copywriteCooldown = nbt.contains("copywriteCooldown") ? nbt.getInt("copywriteCooldown") : 0;
        this.repurposeTicks    = nbt.contains("repurposeTicks")    ? nbt.getInt("repurposeTicks")    : -1;
        this.repurposeCooldown = nbt.contains("repurposeCooldown") ? nbt.getInt("repurposeCooldown") : 0;
        this.overdriveTicksLeft = nbt.contains("overdriveTicksLeft") ? nbt.getInt("overdriveTicksLeft") : 0;

        if (nbt.contains("storedEntityType")) {
            this.dataTracker.set(STORED_ENTITY_TYPE, nbt.getInt("storedEntityType"));
        }

        this.dataTracker.set(IS_SWINGING,          this.swingTicks >= 0);
        this.dataTracker.set(IS_CASTING_WALL,       this.wallTicks >= 0);
        this.dataTracker.set(IS_CASTING_COPYWRITE,  this.copywriteTicks >= 0);
        this.dataTracker.set(IS_REPURPOSING,        this.repurposeTicks >= 0);
    }

    public boolean isLmsSuppressed() {
        return this.dataTracker.get(LMS_SUPPRESSED);
    }

    public void setLmsSuppressed(boolean suppressed) {
        this.dataTracker.set(LMS_SUPPRESSED, suppressed);
    }

    public static DefaultAttributeContainer.Builder createArtfulAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 72.5)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2625)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(2, new ArtfulSwingGoal(this));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 1.25D, false));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public void mobTick() {
        super.mobTick();

        if (swingCooldown > 0) {
            swingCooldown--;
        }

        if (swingTicks >= 0) {
            swingTicks++;

            if (swingTicks == 1 && !this.getWorld().isClient) {
                this.getWorld().playSound(null, this.getBlockPos(), ModSounds.ARTFUL_SWING, net.minecraft.sound.SoundCategory.HOSTILE, 1.0f, 1.0f);
            }

            if (swingTicks <= 4) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 1, 1, false, false));
            }
            else if (swingTicks <= 14) {
                if (!this.getWorld().isClient && !hasHitTarget) {
                    double progressPercentage = (double) (swingTicks - 4) / 10.0;
                    double maxReach = 3.5;
                    double currentReach = 1.0 + (maxReach * progressPercentage);

                    Vec3d lookDir = this.getRotationVec(1.0F);
                    Vec3d scanCenter = this.getEyePos().add(lookDir.multiply(currentReach));
                    Box attackBox = new Box(scanCenter.add(-1.0, -1.0, -1.0), scanCenter.add(1.0, 1.0, 1.0));

                    List<LivingEntity> targets = this.getWorld().getEntitiesByClass(LivingEntity.class, attackBox,
                            entity -> entity != this && entity.isAlive());

                    if (!targets.isEmpty()) {
                        LivingEntity target = targets.get(0);
                        DamageSource source = this.getDamageSources().mobAttack(this);

                        if (target.damage(source, 4.0F)) {
                            this.hasHitTarget = true;
                        }
                    }
                }
            }
            else if (swingTicks <= 24) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1, 1, false, false));
            }

            if (swingTicks > 24) {
                swingTicks = -1;
                this.dataTracker.set(IS_SWINGING, false);
            }
        }

        if (wallTicks >= 0) {
            wallTicks++;

            if (wallTicks == 1 && !this.getWorld().isClient) {
                this.getWorld().playSound(null, this.getBlockPos(), ModSounds.ARTFUL_ABILITY_USE, net.minecraft.sound.SoundCategory.HOSTILE, 1.0f, 1.0f);
            }

            if (wallTicks <= 30) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1, 2, false, false));

                if (wallTicks == 30 && !this.getWorld().isClient) {
                    double surfaceY = this.getWorld().getTopY(net.minecraft.world.Heightmap.Type.MOTION_BLOCKING, (int)this.getX(), (int)this.getZ());
                    double undergroundY = surfaceY - 1.75;
                    ArtfulWallEntity wall = new ArtfulWallEntity(ModEntities.ARTFUL_WALL, this.getWorld());
                    wall.refreshPositionAndAngles(this.getX(), undergroundY, this.getZ(), this.getYaw(), 0.0f);
                    wall.setVariant(this.getVariant());
                    this.spawnedWalls.add(wall);
                    this.getWorld().spawnEntity(wall);
                    this.getWorld().playSound(null, this.getBlockPos(), ModSounds.IMPLEMENT_PLACE, net.minecraft.sound.SoundCategory.HOSTILE, 1.0f, 1.0f);
                }
            }
            else if (wallTicks <= 50) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1, 2, false, false));
            }

            if (wallTicks > 50) {
                wallTicks = -1;
                this.dataTracker.set(IS_CASTING_WALL, false);
            }
        }

        if (!this.getWorld().isClient && this.getTarget() != null && this.wallCooldown <= 0 && this.swingTicks == -1 && this.wallTicks == -1) {
            if (this.distanceTo(this.getTarget()) < 12.0 && this.getRandom().nextFloat() < 0.05) {
                triggerWallAbility();
            }
        }

        if (copywriteCooldown > 0) {
            copywriteCooldown--;
        }

        if (copywriteTicks >= 0) {
            copywriteTicks++;

            if (copywriteTicks == 1 && !this.getWorld().isClient) {
                this.getWorld().playSound(null, this.getBlockPos(), ModSounds.ARTFUL_ABILITY_USE, net.minecraft.sound.SoundCategory.HOSTILE, 1.0f, 1.0f);
            }

            if (copywriteTicks <= 40) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1, 2, false, false));
            }
            else if (copywriteTicks <= 60) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1, 2, false, false));
            }

            if (copywriteTicks > 60) {
                copywriteTicks = -1;
                this.dataTracker.set(IS_CASTING_COPYWRITE, false);
            }
        }

        if (!this.getWorld().isClient && this.getTarget() != null && this.copywriteCooldown <= 0 && this.swingTicks == -1 && this.wallTicks == -1 && this.copywriteTicks == -1) {
            if (this.distanceTo(this.getTarget()) < 14.0 && this.getRandom().nextFloat() < 0.02) {
                triggerCopywriteAbility();
            }
        }

        if (repurposeCooldown > 0) repurposeCooldown--;

        if (overdriveTicksLeft > 0) {
            overdriveTicksLeft--;
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 1, 2, false, false));

            if (this.getWorld() instanceof ServerWorld serverWorld) {
                Vec3d fakeMusicBoxPos = this.getPos().add(0, this.getHeight() + 1.0, 0);
                Vec3d headPos = this.getPos().add(0, this.getHeight(), 0);
                Vec3d originDirection = headPos.subtract(fakeMusicBoxPos);
                double beamDistance = originDirection.length();
                Vec3d unitVector = originDirection.normalize();

                for (double d = 0; d < beamDistance; d += 0.25) {
                    Vec3d particlePoint = fakeMusicBoxPos.add(unitVector.multiply(d));
                    serverWorld.spawnParticles(net.minecraft.particle.ParticleTypes.FIREWORK,
                            particlePoint.x, particlePoint.y, particlePoint.z,
                            1, 0.0, 0.0, 0.0, 0.0);
                }

                if (this.age % 2 == 0) {
                    serverWorld.spawnParticles(net.minecraft.particle.ParticleTypes.GLOW,
                            fakeMusicBoxPos.x, fakeMusicBoxPos.y, fakeMusicBoxPos.z,
                            3, 0.1, 0.1, 0.1, 0.02);
                }
            }
        }

        if (repurposeTicks >= 0) {
            repurposeTicks++;
            int currentStored = this.dataTracker.get(STORED_ENTITY_TYPE);

            if (currentStored == 0) {
                if (repurposeTicks <= 10) {
                    this.getNavigation().stop();
                }
                else if (repurposeTicks <= 20) {
                    this.getNavigation().stop();
                    if (!this.getWorld().isClient && repurposeTicks == 11) {
                        Vec3d lookDir = this.getRotationVec(1.0F);
                        Vec3d scanCenter = this.getEyePos().add(lookDir.multiply(0.8));
                        Box absorptionBox = new Box(scanCenter.add(-0.7, -0.7, -0.42), scanCenter.add(0.7, 0.7, 0.42));

                        List<LivingEntity> targets = this.getWorld().getEntitiesByClass(LivingEntity.class, absorptionBox, e -> e != this && e.isAlive());

                        if (!targets.isEmpty()) {
                            LivingEntity victim = targets.get(0);

                            if (victim instanceof ArtfulWallEntity) {
                                this.dataTracker.set(STORED_ENTITY_TYPE, 1);
                                this.getWorld().playSound(
                                        null,
                                        this.getX(), this.getY(), this.getZ(),
                                        ModSounds.IMPLEMENT_BREAK,
                                        net.minecraft.sound.SoundCategory.HOSTILE,
                                        1.0F, 1.0F
                                );
                                victim.discard();
                            }
                            else if (victim instanceof ArtfulMusicBoxEntity) {
                                this.dataTracker.set(STORED_ENTITY_TYPE, 2);
                                this.getWorld().playSound(
                                        null,
                                        this.getX(), this.getY(), this.getZ(),
                                        ModSounds.IMPLEMENT_BREAK,
                                        net.minecraft.sound.SoundCategory.HOSTILE,
                                        1.0F, 1.0F
                                );
                                victim.discard();
                            }
                            else if (victim instanceof PlayerEntity) {
                                this.dataTracker.set(STORED_ENTITY_TYPE, 3);
                                this.getWorld().playSound(
                                        null,
                                        this.getX(), this.getY(), this.getZ(),
                                        ModSounds.IMPLEMENT_BREAK,
                                        net.minecraft.sound.SoundCategory.HOSTILE,
                                        1.0F, 1.0F
                                );
                            }
                        }
                    }
                }
                else if (repurposeTicks <= 40) {
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1, 1, false, false));
                }

                if (repurposeTicks > 40) {
                    repurposeTicks = -1;
                    this.dataTracker.set(IS_REPURPOSING, false);
                }
            }
            else if (currentStored == 1) {
                if (repurposeTicks <= 20) {
                    this.getNavigation().stop();
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1, 2, false, false));

                    if (repurposeTicks == 1 && !this.getWorld().isClient) {
                        this.triggerAnim("attack_controller", "wall_throw");
                    }
                }

                if (repurposeTicks == 21 && !this.getWorld().isClient) {
                    Vec3d lookDir = this.getRotationVec(1.0F);
                    Vec3d brickImpactPoint = this.getEyePos();

                    for (int step = 0; step < 7; step++) {
                        brickImpactPoint = brickImpactPoint.add(lookDir);
                        Box impactBox = new Box(brickImpactPoint.add(-0.7, -0.7, -0.7), brickImpactPoint.add(0.7, 0.7, 0.7));

                        List<LivingEntity> hitTargets = this.getWorld().getEntitiesByClass(LivingEntity.class, impactBox, e -> e != this && e.isAlive());
                        if (!hitTargets.isEmpty()) {
                            for (LivingEntity target : hitTargets) {
                                DamageSource source = this.getDamageSources().mobAttack(this);
                                target.damage(source, 3.0F);

                                if (target instanceof PlayerEntity player && this.distanceTo(player) >= 7.0) {
                                    target.addStatusEffect(new StatusEffectInstance(ModEffects.RAGDOLLED, 20, 0));
                                }
                            }
                            break;
                        }
                    }
                }

                if (repurposeTicks > 30) {
                    this.dataTracker.set(STORED_ENTITY_TYPE, 0);
                    this.repurposeCooldown = 100;
                    this.repurposeTicks = -1;
                    this.dataTracker.set(IS_REPURPOSING, false);
                }
            }
            else if (currentStored == 2) {
                if (repurposeTicks <= 40) {
                    this.getNavigation().stop();
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1, 10, false, false));

                    if (repurposeTicks == 1) {
                        this.triggerAnim("attack_controller", "repurposed_musicbox");
                    }

                    if (repurposeTicks == 40 && !this.getWorld().isClient) {
                        this.getWorld().getPlayers().forEach(player -> {
                            player.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 140, 0, false, false));
                        });
                        this.overdriveTicksLeft = 140;

                        ArtfulMusicBoxEntity overdriveMusicBox = new ArtfulMusicBoxEntity(ModEntities.ARTFUL_MUSIC_BOX, this.getWorld());
                        overdriveMusicBox.refreshPositionAndAngles(
                                this.getX(), this.getY() + this.getHeight() + 1.0, this.getZ(),
                                this.getYaw(), 0.0f);
                        overdriveMusicBox.setOverdriveMode(true);
                        this.spawnedMusicBoxes.add(overdriveMusicBox);
                        this.getWorld().spawnEntity(overdriveMusicBox);
                    }
                }

                if (repurposeTicks > 40) {
                    this.dataTracker.set(STORED_ENTITY_TYPE, 0);
                    this.repurposeCooldown = 100;
                    this.repurposeTicks = -1;
                    this.dataTracker.set(IS_REPURPOSING, false);
                }
            }
            else if (currentStored == 3) {
                if (repurposeTicks <= 30) {
                    this.getNavigation().stop();
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 1, 2, false, false));

                    if (repurposeTicks == 1) {
                        this.triggerAnim("attack_controller", "puppet");
                    }

                    if (repurposeTicks == 30 && !this.getWorld().isClient) {
                        ArtfulPuppetEntity puppet = new ArtfulPuppetEntity(ModEntities.ARTFUL_PUPPET, this.getWorld());
                        puppet.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0f);
                        this.getWorld().spawnEntity(puppet);
                    }
                }

                if (repurposeTicks > 30) {
                    this.dataTracker.set(STORED_ENTITY_TYPE, 0);
                    this.repurposeCooldown = 200;
                    this.repurposeTicks = -1;
                    this.dataTracker.set(IS_REPURPOSING, false);
                }
            }
        }

        if (this.getTarget() != null && this.repurposeCooldown <= 0 && this.swingTicks == -1 && this.wallTicks == -1 && this.copywriteTicks == -1 && this.repurposeTicks == -1) {
            if (this.distanceTo(this.getTarget()) <= 4.0) {
                triggerRepurposeAbility();
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.getWorld().isClient() && this.isAlive() && this.getTarget() != null) {
            if (this.horizontalCollision && this.isOnGround()) {
                this.getJumpControl().setActive();
            }
        }

        if (this.age % 40 == 0) {
            this.setLmsSuppressed(lmsDiscCache.isLMSDiscNearby(this.getWorld(), this.getBlockPos()));
        }

        if (this.getWorld().isClient) {
            checkAndPlayChaseMusic();
        }
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
                    int[] weights = {50};
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
                this.setVariant(this.random.nextInt(2));
            }
        }

        return entityData;
    }

    private void checkAndPlayChaseMusic() {
        if (this.isAlive()) {
            ClientMusicHandler.handleArtfulMusic(this);
        }
    }

    public void triggerRepurposeAbility() {
        this.repurposeTicks = 0;
        this.dataTracker.set(IS_REPURPOSING, true);
        this.triggerAnim("attack_controller", "repurpose");
    }

    public void triggerCopywriteAbility() {
        if (this.getWorld().isClient) return;

        this.getWorld().playSound(null, this.getBlockPos(),
                ModSounds.ARTFUL_COPYWRITE_PLACED, net.minecraft.sound.SoundCategory.HOSTILE, 1.0f, 1.0f
        );

        List<ArtfulMusicBoxEntity> globalBoxes = this.getWorld().getEntitiesByClass(ArtfulMusicBoxEntity.class,
                this.getBoundingBox().expand(256.0), Entity::isAlive);

        if (globalBoxes.size() >= 2) {
            sendMessageToNearbyPlayers("MusicBox limit reached.");
            return;
        }

        for (ArtfulMusicBoxEntity box : globalBoxes) {
            if (this.distanceTo(box) <= 14.0) {
                sendMessageToNearbyPlayers("Too close to a MusicBox, try again later.");
                return;
            }
        }
        this.copywriteTicks = 0;
        this.copywriteCooldown = 700;
        this.dataTracker.set(IS_CASTING_COPYWRITE, true);
        this.getNavigation().stop();
        this.triggerAnim("attack_controller", "copywrite");

        if (!this.getWorld().isClient()) {
            ArtfulMusicBoxEntity musicBox = new ArtfulMusicBoxEntity(ModEntities.ARTFUL_MUSIC_BOX, this.getWorld());
            double targetX = this.getX() + this.getRotationVec(1.0F).x * 4.0;
            double targetZ = this.getZ() + this.getRotationVec(1.0F).z * 4.0;
            double groundY = this.getWorld().getTopY(net.minecraft.world.Heightmap.Type.MOTION_BLOCKING, (int)targetX, (int)targetZ);

            musicBox.setPosition(targetX, groundY + 1.5, targetZ);
            this.getWorld().spawnEntity(musicBox);
            this.spawnedMusicBoxes.add(musicBox);
        }
    }

    private void sendMessageToNearbyPlayers(String message) {
        List<PlayerEntity> nearby = this.getWorld().getEntitiesByClass(PlayerEntity.class,
                this.getBoundingBox().expand(32.0), p -> true);
        for (PlayerEntity player : nearby) {
            player.sendMessage(Text.literal(message), false);
        }
    }

    public void triggerWallAbility() {
        this.wallTicks = 0;
        this.wallCooldown = 300;
        this.dataTracker.set(IS_CASTING_WALL, true);
        this.getNavigation().stop();
        this.triggerAnim("attack_controller", "implement");
    }

    public void triggerSwingAbility() {
        if (swingCooldown <= 0 && swingTicks == -1) {
            this.swingTicks = 0;
            this.hasHitTarget = false;
            this.swingCooldown = 20;
            this.dataTracker.set(IS_SWINGING, true);
            this.triggerAnim("attack_controller", "swing");
        }
    }

    public boolean isCurrentlySwinging() {
        return this.dataTracker.get(IS_SWINGING);
    }

    @Override
    public Text getDisplayName() {
        int variant = this.dataTracker == null ? 0 : this.getVariant();
        return switch (variant) {
            case 1 -> Text.literal("Builder");
            default -> Text.literal("Artful");
        };
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        if (!this.getWorld().isClient) {
            this.getWorld().playSound(null, this.getBlockPos(), ModSounds.ARTFUL_STUNNED, net.minecraft.sound.SoundCategory.HOSTILE, 1.0f, 1.0f);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "movement_controller", 5, state -> {
            if (this.dataTracker.get(IS_SWINGING)
                    || this.dataTracker.get(IS_CASTING_WALL)
                    || this.dataTracker.get(IS_CASTING_COPYWRITE)
                    || this.dataTracker.get(IS_REPURPOSING)) {
                return PlayState.STOP;
            }

            String anim;
            if (state.isMoving()) {
                anim = this.getTarget() != null
                        ? "animation.artful.run"
                        : "animation.artful.walk";
            } else {
                anim = "animation.artful.idle";
            }
            state.setAndContinue(RawAnimation.begin().thenLoop(anim));
            return PlayState.CONTINUE;
        }));

        controllerRegistrar.add(new AnimationController<>(this, "attack_controller", 2, state -> PlayState.STOP)
                .triggerableAnim("swing",               RawAnimation.begin().thenPlay("animation.artful.swing"))
                .triggerableAnim("implement",           RawAnimation.begin().thenPlay("animation.artful.implement"))
                .triggerableAnim("copywrite",           RawAnimation.begin().thenPlay("animation.artful.copywrite"))
                .triggerableAnim("repurpose",           RawAnimation.begin().thenPlay("animation.artful.repurpose"))
                .triggerableAnim("wall_throw",          RawAnimation.begin().thenPlay("animation.artful.wall_throw"))
                .triggerableAnim("repurposed_musicbox", RawAnimation.begin().thenPlay("animation.artful.repurposed_musicbox"))
                .triggerableAnim("puppet",              RawAnimation.begin().thenPlay("animation.artful.puppet"))
        );
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

            var artfulObj = scoreboard.getNullableObjective("artful_kills");
            if (artfulObj == null) {
                artfulObj = scoreboard.addObjective("artful_kills", net.minecraft.scoreboard.ScoreboardCriterion.DUMMY, Text.literal("Artful Kills"), net.minecraft.scoreboard.ScoreboardCriterion.RenderType.INTEGER, true, null);
            }

            var pursuerScore = scoreboard.getOrCreateScore(player, pursuerObj);
            int currentPursuerKills = pursuerScore.getScore() + 1;
            pursuerScore.setScore(currentPursuerKills);

            int currentBadwareKills = scoreboard.getOrCreateScore(player, badwareObj).getScore();
            int totalKillersKilled = currentBadwareKills + currentPursuerKills;

            this.dropStack(new ItemStack(ModItems.ARTFUL_WAND));
            grantAdvancement(player, "any_killer_kill");
            if (totalKillersKilled % 5 == 0) {
                player.giveItemStack(new ItemStack(ModItems.ETERNITY_V2_MUSIC_DISC));
            }

            if ((this.getVariant() == 0)) {
                grantAdvancement(player, "artful_first_kill");
                AdvancementEntry PursuerAdv = player.getServer().getAdvancementLoader()
                        .get(Identifier.of(DieOfDeath.MOD_ID, "artful_first_kill"));

                if (PursuerAdv != null) {
                    AdvancementProgress progress = player.getAdvancementTracker().getProgress(PursuerAdv);
                    if (progress.isDone()) {
                        this.dropStack(new ItemStack(ModItems.ETERNITY_V2_MUSIC_DISC));
                    }
                }
            }
            if (this.getVariant() > 0) {
                AdvancementEntry variantAdv = player.getServer().getAdvancementLoader()
                        .get(Identifier.of(DieOfDeath.MOD_ID, "artful_variant_kill"));

                if (variantAdv != null) {
                    AdvancementProgress progress = player.getAdvancementTracker().getProgress(variantAdv);
                    if (!progress.isDone()) {
                        this.dropStack(new ItemStack(ModItems.ETERNITY_V2_MUSIC_DISC));
                    }
                }
                grantAdvancement(player, "artful_variant_kill");
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
    protected int getXpToDrop() {
        return 30;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    private static class ArtfulSwingGoal extends Goal {
        private final ArtfulEntity artful;

        public ArtfulSwingGoal(ArtfulEntity artful) {
            this.artful = artful;
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            LivingEntity target = this.artful.getTarget();
            return target != null && target.isAlive() && this.artful.distanceTo(target) <= 3.0 && this.artful.swingCooldown <= 0;
        }

        @Override
        public void start() {
            this.artful.getNavigation().stop();
            this.artful.triggerSwingAbility();
        }

        @Override
        public boolean shouldContinue() {
            return this.artful.isCurrentlySwinging();
        }
    }

    @Override
    public void remove(Entity.RemovalReason reason) {
        super.remove(reason);

        if (!this.getWorld().isClient) {
            for (ArtfulWallEntity wall : spawnedWalls) {
                if (wall != null && wall.isAlive()) {
                    wall.discard();
                }
            }
            spawnedWalls.clear();

            for (ArtfulMusicBoxEntity box : spawnedMusicBoxes) {
                if (box != null && box.isAlive()) {
                    box.discard();
                }
            }
            spawnedMusicBoxes.clear();
        }
    }
}