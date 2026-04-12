package net.badware.dieofdeath.block.advanced;

import net.badware.dieofdeath.sound.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BonuspadBlock extends Block {
    private static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 3.0, 16.0);
    private static final Map<UUID, Long> COOLDOWN = new HashMap<>();

    public BonuspadBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient && entity instanceof PlayerEntity player) {
            long currentTime = world.getTime();
            UUID id = player.getUuid();
            if (!COOLDOWN.containsKey(id) || currentTime - COOLDOWN.get(id) >= 100) {

                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 1));

                world.playSound(null, pos, ModSounds.BONUSPAD_STEPPED, SoundCategory.BLOCKS, 1.0f, 1.0f);

                ((ServerWorld) world).spawnParticles(ParticleTypes.GLOW, pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5, 10, 0.3, 0.1, 0.3, 0.1);

                ((ServerWorld) world).spawnParticles(ParticleTypes.SOUL,
                        pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5,
                        10, 0.3, 0.1, 0.3, 0.05);

                COOLDOWN.put(id, currentTime);
            }
        }
        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        world.scheduleBlockTick(pos, this, 6000);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.breakBlock(pos, false);
    }
}