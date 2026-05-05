package net.badware.dieofdeath.item.advanced;

import net.badware.dieofdeath.entity.ModEntities;
import net.badware.dieofdeath.entity.custom.BadwarePCEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class BadwarePCSpawnItem extends Item {
    public BadwarePCSpawnItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!world.isClient) {
            BadwarePCEntity pc = new BadwarePCEntity(ModEntities.BADWARE_PC, world);
            pc.refreshPositionAndAngles(context.getBlockPos().up(), 0, 0);
            world.spawnEntity(pc);
            context.getStack().decrement(1);
            return ActionResult.SUCCESS;
        }
        return ActionResult.CONSUME;
    }
}