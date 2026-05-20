package net.badware.dieofdeath.item.advanced;

import net.badware.dieofdeath.entity.ModEntities;
import net.badware.dieofdeath.entity.custom.ArtfulWallEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class ArtfulWallSpawnItem extends Item {
    public ArtfulWallSpawnItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!world.isClient) {
            ArtfulWallEntity wall = new ArtfulWallEntity(ModEntities.ARTFUL_WALL, world);
            wall.refreshPositionAndAngles(context.getBlockPos().up(), 0, 0);
            world.spawnEntity(wall);
            context.getStack().decrement(1);
            return ActionResult.SUCCESS;
        }
        return ActionResult.CONSUME;
    }
}
