package net.badware.dieofdeath.block.advanced;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SaplingGenerator;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class StoneSaplingBlock extends SaplingBlock {
    public StoneSaplingBlock(SaplingGenerator generator, Settings settings) {
        super(generator, settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(BlockTags.STONE_ORE_REPLACEABLES) ||
                floor.isOf(Blocks.STONE) ||
                floor.isOf(Blocks.DEEPSLATE) ||
                floor.isOf(Blocks.DIORITE) ||
                floor.isOf(Blocks.GRANITE) ||
                floor.isOf(Blocks.ANDESITE) ||
                floor.isOf(Blocks.SNOW) ||
                floor.isOf(Blocks.SNOW_BLOCK);
    }
}