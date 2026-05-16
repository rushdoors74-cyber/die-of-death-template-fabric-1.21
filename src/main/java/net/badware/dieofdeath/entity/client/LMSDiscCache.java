package net.badware.dieofdeath.entity.client;

import net.badware.dieofdeath.item.ModItems;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LMSDiscCache {

    private boolean cachedResult = false;
    private long lastCheckedTime = -1;

    private static final int CACHE_TICKS = 50;

    public boolean isLMSDiscNearby(World world, BlockPos pos) {
        long currentTime = world.getTime();

        if (currentTime - lastCheckedTime < CACHE_TICKS) {
            return cachedResult;
        }

        lastCheckedTime = currentTime;
        cachedResult = scan(world, pos);
        return cachedResult;
    }

    private static boolean scan(World world, BlockPos pos) {
        int r = 48;
        for (BlockPos nearbyPos : BlockPos.iterate(pos.add(-r, -r, -r), pos.add(r, r, r))) {
            if (world.getBlockState(nearbyPos).getBlock() instanceof JukeboxBlock) {
                var be = world.getBlockEntity(nearbyPos);
                if (be instanceof JukeboxBlockEntity jukebox) {
                    ItemStack stack = jukebox.getStack();
                    if (!stack.isEmpty() && isLMSTheme(stack)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isLMSTheme(ItemStack stack) {
        return stack.isOf(ModItems.ETERNITY) ||
                stack.isOf(ModItems.ETERNITY_V2_MUSIC_DISC) ||
                stack.isOf(ModItems.ONE_BOUNCE) ||
                stack.isOf(ModItems.Y2K) ||
                stack.isOf(ModItems.SHOWTIME) ||
                stack.isOf(ModItems.VIGILANTE_SHOOTDOWN) ||
                stack.isOf(ModItems.CARELESS) ||
                stack.isOf(ModItems.OMEGAS_FINAL_STAND) ||
                stack.isOf(ModItems.TEAPOT_PALACE_TOUR_LMS);
    }
}
