package net.badware.dieofdeath.item.advanced;

import net.badware.dieofdeath.entity.ModEntities;
import net.badware.dieofdeath.entity.custom.ArtfulPuppetEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class ArtfulPuppetSpawnItem extends Item {
    public ArtfulPuppetSpawnItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!world.isClient) {
            ArtfulPuppetEntity puppet = new ArtfulPuppetEntity(ModEntities.ARTFUL_PUPPET, world);
            puppet.refreshPositionAndAngles(context.getBlockPos().up(), 0, 0);
            world.spawnEntity(puppet);
            context.getStack().decrement(1);
            return ActionResult.SUCCESS;
        }
        return ActionResult.CONSUME;
    }
}

