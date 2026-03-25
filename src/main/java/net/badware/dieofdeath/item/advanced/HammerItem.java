package net.badware.dieofdeath.item.advanced;

import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.List;

public class HammerItem extends MiningToolItem {
    public HammerItem(ToolMaterial material, Settings settings) {
        super(material, BlockTags.PICKAXE_MINEABLE, settings);
    }

    public static List<BlockPos> getBlocksToBeDestroyed(BlockPos initialBlockPos, ServerPlayerEntity player) {
        List<BlockPos> positions = new ArrayList<>();
        HitResult hit = player.raycast(4.0, 0, false); //

        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult) hit;
            Direction side = blockHit.getSide();

            for (int x = -1; x <= 2; x++) {
                for (int y = -1; y <= 2; y++) {

                    if (side == Direction.DOWN || side == Direction.UP) {
                        positions.add(initialBlockPos.add(x, 0, y));
                    } else if (side == Direction.NORTH || side == Direction.SOUTH) {
                        positions.add(initialBlockPos.add(x, y, 0));
                    } else if (side == Direction.EAST || side == Direction.WEST) {
                        positions.add(initialBlockPos.add(0, y, x));
                    }
                }
            }
        }
        return positions;
    }
}
