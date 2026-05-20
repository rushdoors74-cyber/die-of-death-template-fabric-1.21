package net.badware.dieofdeath.item.advanced;

import net.badware.dieofdeath.entity.ModEntities;
import net.badware.dieofdeath.entity.custom.ArtfulMusicBoxEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class ArtfulMusicBoxItem extends Item {
    public ArtfulMusicBoxItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!world.isClient) {
            ArtfulMusicBoxEntity musicBox = new ArtfulMusicBoxEntity(ModEntities.ARTFUL_MUSIC_BOX, world);
            musicBox.refreshPositionAndAngles(context.getBlockPos().up(), 0, 0);
            world.spawnEntity(musicBox);
            context.getStack().decrement(1);
            return ActionResult.SUCCESS;
        }
        return ActionResult.CONSUME;
    }
}
