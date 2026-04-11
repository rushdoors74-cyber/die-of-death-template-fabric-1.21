package net.badware.dieofdeath.item.advanced;

import net.badware.dieofdeath.sound.ModSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;

public class PursuerCleave extends SwordItem {
    public PursuerCleave(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        var world = target.getWorld();
        if (!world.isClient) {
            world.playSound(null, target.getBlockPos(),
                    ModSounds.PURSUER_CLEAVE_SFX, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
        return super.postHit(stack, target, attacker);
    }
}