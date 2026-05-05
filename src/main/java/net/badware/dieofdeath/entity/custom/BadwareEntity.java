package net.badware.dieofdeath.entity.custom;

import net.badware.dieofdeath.DieOfDeath;
import net.badware.dieofdeath.effect.ModEffects;
import net.badware.dieofdeath.entity.ModEntities;
import net.badware.dieofdeath.entity.client.ClientMusicHandler;
import net.badware.dieofdeath.item.ModItems;
import net.badware.dieofdeath.sound.ModSounds;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.GiantEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.TitleFadeS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
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

import java.util.ArrayList;
import java.util.List;

public class BadwareEntity extends HostileEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private final List<BlockPos> activeComputers = new ArrayList<>();

    private int computerCooldown = 0;
    private int placementEndlag = 0;
    private int boltCooldown = 0;
    private int boltTimer = 0;
    private int boltWindup = 0;
    private int boltLinger = 0;
    private boolean boltHasHit = false;
    private int swingCooldown = 0;
    private int swingTicks = 0;
    private int riftStanceTimer = 0;
    private int teleportWindup = 0;
    private BlockPos selectedPC = null;
    private int riftCooldown = 0;
    public transient boolean isThemePlayingClient = false;
    public boolean isThemePlaying = false;


    public BadwareEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(net.minecraft.entity.ai.pathing.PathNodeType.WATER, -1.0F);
    }


    public static DefaultAttributeContainer.Builder createAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 134.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0);
    }

    private static final TrackedData<Boolean> IS_BOLTING = DataTracker.registerData(BadwareEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> IS_RIFTING = DataTracker.registerData(BadwareEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> IN_ENDLAG = DataTracker.registerData(BadwareEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(BadwareEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public void setThemePlaying(boolean playing) {
        this.isThemePlaying = playing;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(IS_BOLTING, false);
        builder.add(IS_RIFTING, false);
        builder.add(IN_ENDLAG, false);
        builder.add(VARIANT, -1);
    }

    public void tryPlaceComputer() {
        if (this.computerCooldown > 0) return;

        if (this.getComputerCount() >= 6) {
            this.sendMessage(Text.literal("§c[MAX COMPUTER LIMIT REACHED]"));
            return;
        }

        for (BlockPos pos : activeComputers) {
            if (this.getBlockPos().isWithinDistance(pos, 14.0)) {
                this.sendMessage(Text.literal("§e[TOO CLOSE TO ANOTHER COMPUTER, TRY AGAIN LATER]"));
                return;
            }
        }

        this.computerCooldown = 300;
        this.placementEndlag = 60;
        this.triggerAnim("attack_controller", "firewall_bypass");

        this.getNavigation().stop();

        if (!this.getWorld().isClient) {
            BadwarePCEntity pc = new BadwarePCEntity(ModEntities.BADWARE_PC, this.getWorld());
            pc.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), 0, 0);
            pc.setOwner(this.getUuid());
            pc.setVariant(this.getVariant());
            this.getWorld().playSound(null, this.getBlockPos(), ModSounds.BADWARE_PC_MAKING, SoundCategory.HOSTILE, 1.0f, 1.0f);

            this.getWorld().spawnEntity(pc);
            this.addComputer(pc.getBlockPos());
        }
    }

    public boolean canPlaceComputer() {
        return activeComputers.size() < 6;
    }

    public void addComputer(BlockPos pos) {
        if (!activeComputers.contains(pos)) {
            activeComputers.add(pos);
        }
    }

    public void removeComputer(BlockPos pos) {
        activeComputers.remove(pos);
    }

    public int getComputerCount() {
        return activeComputers.size();
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (this.dataTracker.get(IS_BOLTING) || this.dataTracker.get(IS_RIFTING)) return false;

        if (this.swingTicks == 0 && this.swingCooldown <= 0) {
            this.swingTicks = 23;
            this.swingCooldown = 20;

            this.triggerAnim("attack_controller", "swing");

            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(),
                    ModSounds.BADWARE_SWING, this.getSoundCategory(), 1.0f, 1.0f);

            return true;
        }

        if (this.swingTicks > 10) {
            float damage = (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
            return target.damage(this.getDamageSources().mobAttack(this), damage);
        }

        return false;
    }


    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient) return;

        var speedAttr = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (speedAttr != null) {
            if (this.placementEndlag > 0 || this.boltWindup > 0 || this.riftStanceTimer > 0) {
                speedAttr.setBaseValue(0.0);
            } else {
                double targetSpeed = 0.23 + (getComputerCount() * 0.04);
                speedAttr.setBaseValue(targetSpeed);
            }
        }

        if (computerCooldown > 0) computerCooldown--;
        if (boltCooldown > 0) boltCooldown--;
        if (riftCooldown > 0) riftCooldown--;
        if (swingCooldown > 0) swingCooldown--;

        if (this.placementEndlag > 0) {
            this.placementEndlag--;
            this.setVelocity(0, this.getVelocity().y, 0);
            this.getNavigation().stop();
        }

        if (this.boltWindup > 0) {
            this.boltWindup--;
            this.getNavigation().stop();
            if (this.boltWindup == 0) {
                this.boltLinger = 40;
                this.boltHasHit = false;
            }
        }

        if (this.boltLinger > 0 && !this.boltHasHit) {
            this.boltLinger--;
            LivingEntity target = this.getTarget();

            if (target != null) {
                this.lookAtEntity(target, 360, 360);
                Vec3d dir = target.getPos().subtract(this.getPos()).normalize().multiply(0.8);

                double yVel = this.getVelocity().y;
                if (this.horizontalCollision && this.isOnGround()) {
                    yVel = 0.42;
                }

                this.setVelocity(dir.x, yVel, dir.z);
            }

            this.checkBoltCollision();

            if (this.boltLinger == 0 && !this.boltHasHit) {
                this.placementEndlag = 20;
                this.getWorld().playSound(null, this.getBlockPos(), ModSounds.BOLT_MISS, SoundCategory.HOSTILE, 1.0f, 1.0f);
            }
        }

        if (this.riftStanceTimer > 0) {
            this.riftStanceTimer--;
            this.getNavigation().stop();
            if (this.riftStanceTimer == 0) {
                if (!activeComputers.isEmpty()) {
                    PlayerEntity nearestPlayer = this.getWorld().getClosestPlayer(this.getX(), this.getY(), this.getZ(), 64.0, false);
                    BlockPos bestPC = null;

                    if (nearestPlayer != null) {
                        double minDistance = Double.MAX_VALUE;
                        for (BlockPos pcPos : activeComputers) {
                            double dist = pcPos.getSquaredDistance(nearestPlayer.getPos());
                            if (dist < minDistance) {
                                minDistance = dist;
                                bestPC = pcPos;
                            }
                        }
                    }

                    this.selectedPC = (bestPC != null) ? bestPC : activeComputers.get(this.random.nextInt(activeComputers.size()));
                    this.teleportWindup = 20;
                } else {
                    this.sendMessage(Text.literal("§8[FAILED TO TELEPORT]"));
                }
            }
        }

        if (this.teleportWindup > 0) {
            this.teleportWindup--;
            if (this.teleportWindup == 0) performTeleport();
        }

        if (getComputerCount() > 0) {
            this.drawDataChains();
        }

        this.dataTracker.set(IS_BOLTING, this.boltLinger > 0 || this.boltWindup > 0);
        this.dataTracker.set(IS_RIFTING, this.riftStanceTimer > 0 || this.teleportWindup > 0);
        this.dataTracker.set(IN_ENDLAG, this.placementEndlag > 0);
    }

    private static class BadwareSpecialAttackGoal extends Goal {
        private final BadwareEntity badware;
        private int updateCountdownTicks = 20;

        public BadwareSpecialAttackGoal(BadwareEntity badware) {
            this.badware = badware;
        }

        @Override
        public boolean canStart() {
            return badware.getTarget() != null && !badware.dataTracker.get(IS_BOLTING) && !badware.dataTracker.get(IS_RIFTING);
        }

        @Override
        public void tick() {
            if (--updateCountdownTicks <= 0) {
                updateCountdownTicks = 30;
                double distSq = badware.squaredDistanceTo(badware.getTarget());

                if (badware.getComputerCount() >= 1 && distSq > 400 && badware.riftCooldown <= 0) {
                    badware.startRift();
                    return;
                }

                if (distSq < 144 && badware.canPlaceComputer() && badware.computerCooldown <= 0) {
                    badware.tryPlaceComputer();
                    return;
                }

                if (distSq > 64 && badware.boltCooldown <= 0) {
                    badware.startBolt();
                }
            }
        }
    }

    @Override
    public void mobTick() {
        super.mobTick();

        if (this.swingTicks > 0) {
            this.swingTicks--;

            if (this.swingTicks > 20) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10, 2, false, false));
            } else if (this.swingTicks > 10) {
                Vec3d lungeDir = this.getRotationVec(1.0f).multiply(0.3);
                this.setVelocity(lungeDir.x, this.getVelocity().y, lungeDir.z);

                Box hitbox = this.getBoundingBox().expand(0.5);
                List<LivingEntity> targets = this.getWorld().getEntitiesByClass(LivingEntity.class, hitbox, EntityPredicates.EXCEPT_SPECTATOR);

                for (LivingEntity target : targets) {
                    if (target != this) {
                        if (target.damage(this.getDamageSources().mobAttack(this), (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE))) {
                            this.swingTicks = 10;
                            break;
                        }
                    }
                }
            } else if (this.swingTicks == 0) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10, 1, false, false));
            }
        }
    }

    private void checkAndPlayChaseMusic() {
        if (this.isAlive()) {
            ClientMusicHandler.handleBadwareMusic(this);
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getVariant());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setVariant(nbt.getInt("Variant"));
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

    public void startBolt() {
        if (this.boltCooldown <= 0 && this.boltTimer <= 0 && this.placementEndlag <= 0) {
            this.boltWindup = 20;
            this.boltCooldown = 400;
            this.triggerAnim("attack_controller", "bolt");
            this.getNavigation().stop();
            this.getWorld().playSound(null, this.getBlockPos(), ModSounds.BOLT_START, SoundCategory.HOSTILE, 1.0f, 1.0f);
        }
    }

    private void checkBoltCollision() {
        List<PlayerEntity> targets = this.getWorld().getEntitiesByClass(PlayerEntity.class, this.getBoundingBox().expand(1.2), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR);

        if (!targets.isEmpty()) {
            this.boltHasHit = true;
            this.boltLinger = 0;
            this.getWorld().playSound(null, this.getBlockPos(), ModSounds.BOLT_HIT, SoundCategory.HOSTILE, 1.0f, 1.0f);

            float totalDamage = 2.0f + (getComputerCount() * 1.0f);

            for (PlayerEntity player : targets) {
                player.damage(this.getDamageSources().mobAttack(this), totalDamage);

                player.addStatusEffect(new StatusEffectInstance(ModEffects.RAGDOLLED, 40, 0));
                Vec3d launch = this.getRotationVec(1.0f).multiply(1.5).add(0, 0.8, 0);
                player.addVelocity(launch.x, launch.y, launch.z);
                player.velocityModified = true;
            }

            this.addStatusEffect(new StatusEffectInstance(ModEffects.RAGDOLLED, 60, 0));
            this.placementEndlag = 60;
        }
    }

    public void startRift() {
        if (this.getComputerCount() <= 0) {
            this.sendMessage(Text.literal("§c[COMPUTER NEEDED TO INITIATE TELEPORT]"));
            return;
        }

        if (this.riftCooldown <= 0) {
            this.riftStanceTimer = 40 + this.random.nextInt(61);
            this.riftCooldown = 500;
            this.triggerAnim("attack_controller", "rift");
            this.getNavigation().stop();

            Text riftMessage = this.random.nextFloat() < 0.10f ? Text.literal("§8I'm the one who made this mod") : Text.literal("§8YOUR PC HAS BEEN INFECTED!");
            for (PlayerEntity player : this.getWorld().getPlayers()) {
                if (player instanceof ServerPlayerEntity sp) {
                    sp.networkHandler.sendPacket(new TitleS2CPacket(riftMessage));
                    sp.networkHandler.sendPacket(new TitleFadeS2CPacket(10, 40, 10));
                }
            }
        }
    }

    public void selectPC(BlockPos pos) {
        if (this.riftStanceTimer > 0 && activeComputers.contains(pos)) {
            this.selectedPC = pos;
            this.teleportWindup = 20;
            this.riftStanceTimer = 0;
        }
    }

    private void performTeleport() {
        if (selectedPC != null) {
            Box searchBox = new Box(selectedPC).expand(1.0);
            List<BadwarePCEntity> pcs = this.getWorld().getEntitiesByClass(BadwarePCEntity.class, searchBox, pc -> true);

            if (!pcs.isEmpty()) {
                BadwarePCEntity targetPC = pcs.get(0);

                this.requestTeleport(selectedPC.getX() + 0.5, selectedPC.getY(), selectedPC.getZ() + 0.5);

                targetPC.triggerExplosion();

                List<PlayerEntity> victims = this.getWorld().getEntitiesByClass(
                        PlayerEntity.class,
                        this.getBoundingBox().expand(3.0),
                        EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR
                );

                for (PlayerEntity p : victims) {
                    p.addStatusEffect(new StatusEffectInstance(ModEffects.BLEED, 200, 0));
                    p.sendMessage(Text.literal("§c[A COMPUTER WAS ELIMINATED]"), false);
                }
            } else {
                this.sendMessage(Text.literal("§8[TELEPORT TARGET LOST - SPEED OVERRIDE]"));
            }
        }

        this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 80, 2));
        this.selectedPC = null;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.5D));
        this.goalSelector.add(1, new BadwareSpecialAttackGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(5, new LookAroundGoal(this));

        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, GiantEntity.class, true));
    }

    @Override
    public void setTarget(@org.jetbrains.annotations.Nullable LivingEntity target) {
        if (target instanceof BadwarePCEntity) {
            super.setTarget(null);
            return;
        }
        super.setTarget(target);
    }

    public int getVariant() {
        int v = this.dataTracker.get(VARIANT);
        return v == -1 ? 0 : v;
    }

    public void setVariant(int variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "movement_controller", 5, state -> {
            if (this.dataTracker.get(IS_BOLTING) || this.dataTracker.get(IS_RIFTING)) {
                return PlayState.STOP;
            }
            state.getController().setAnimationSpeed(1.0);

            String anim = "animation.badware.idle";

            if (state.isMoving()) {
                anim = "animation.badware.run";
            }
            return state.setAndContinue(RawAnimation.begin().thenLoop(anim));
        }));

        controllers.add(new AnimationController<>(this, "attack_controller", 2, state -> PlayState.STOP)
                .triggerableAnim("swing", RawAnimation.begin().thenPlay("animation.badware.swing"))
                .triggerableAnim("firewall_bypass", RawAnimation.begin().thenPlay("animation.badware.firewall_bypass"))
                .triggerableAnim("bolt", RawAnimation.begin().thenPlay("animation.badware.bolt"))
                .triggerableAnim("rift", RawAnimation.begin().thenPlay("animation.badware.rift"))
        );
    }

    @Override
    public Text getDisplayName() {
        int variant = this.getVariant();
        return switch (variant) {
            case 1 -> Text.literal("Goodware");
            default -> Text.literal("Badware");
        };
    }

    @Override
    protected void dropLoot(DamageSource damageSource, boolean causedByPlayer) {
        super.dropLoot(damageSource, causedByPlayer);

        if (causedByPlayer && damageSource.getAttacker() instanceof ServerPlayerEntity player) {
            var scoreboard = player.getScoreboard();
            var objective = scoreboard.getNullableObjective("badware_kills");

            if (objective == null) {
                objective = scoreboard.addObjective("badware_kills",
                        net.minecraft.scoreboard.ScoreboardCriterion.DUMMY,
                        Text.literal("Badware Kills"),
                        net.minecraft.scoreboard.ScoreboardCriterion.RenderType.INTEGER,
                        true, null);
            }

            var score = scoreboard.getOrCreateScore(player, objective);
            int currentScore = score.getScore() + 1;
            score.setScore(currentScore);

            if (currentScore == 1) {
                grantAdvancement(player, "badware_first_kill");
            }

            if (currentScore >= 2) {
                this.dropStack(new ItemStack(ModItems.Y2K));
                grantAdvancement(player, "badware_second_kill");
                score.setScore(0);
            }
            if (this.getVariant() > 0) {
                grantAdvancement(player, "badware_peon_kill");
            }
        }
    }

    private void grantAdvancement(ServerPlayerEntity player, String name) {
        AdvancementEntry advancementEntry = player.getServer().getAdvancementLoader()
                .get(Identifier.of(DieOfDeath.MOD_ID, name));

        if (advancementEntry != null) {
            AdvancementProgress progress = player.getAdvancementTracker().getProgress(advancementEntry);
            for (String criterion : progress.getUnobtainedCriteria()) {
                player.getAdvancementTracker().grantCriterion(advancementEntry, criterion);
            }
        }
    }

    @Override
    protected int getXpToDrop() {
        return 25;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.BADWARE_STUNNED;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    private void drawDataChains() {
        if (this.getWorld().isClient || this.activeComputers.isEmpty()) return;

        if (this.age % 2 != 0) return;

        for (BlockPos pcPos : activeComputers) {
            Vec3d start = this.getPos().add(0, 1.5, 0);
            Vec3d end = Vec3d.ofCenter(pcPos).add(0, 0.5, 0);

            Vec3d direction = end.subtract(start);
            double distance = direction.length();
            direction = direction.normalize();

            for (double d = 0; d < distance; d += 0.5) {
                Vec3d particlePos = start.add(direction.multiply(d));

                ((ServerWorld) this.getWorld()).spawnParticles(
                        net.minecraft.particle.ParticleTypes.GLOW,
                        particlePos.x, particlePos.y, particlePos.z,
                        1, 0, 0, 0, 0.0
                );
            }
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return super.damage(source, amount);
    }
}