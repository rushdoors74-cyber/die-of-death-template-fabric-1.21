package net.badware.dieofdeath.block.advanced;

import net.badware.dieofdeath.entity.custom.ArtfulEntity;
import net.badware.dieofdeath.entity.custom.BadwareEntity;
import net.badware.dieofdeath.entity.custom.PursuerEntity;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class KillerOnlyBlock extends Block {
    public KillerOnlyBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext entityContext && entityContext.getEntity() != null) {
            Entity entity = entityContext.getEntity();

            if (entity instanceof PursuerEntity || entity instanceof BadwareEntity || entity instanceof ArtfulEntity) {
                return VoxelShapes.empty();
            }
        }
        return VoxelShapes.fullCube();
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext entityContext) {
            Entity entity = entityContext.getEntity();

            if (entity instanceof PursuerEntity || entity instanceof BadwareEntity || entity instanceof ArtfulEntity) {
                return VoxelShapes.empty();
            }
        }
        return VoxelShapes.fullCube();
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0f;
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isShapeFullCube(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean canPathfindThrough(BlockState state, NavigationType type) {
        return true;
    }
    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return 0;
    }
}