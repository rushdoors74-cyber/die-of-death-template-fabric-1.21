package net.badware.dieofdeath.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.network.ServerPlayerEntity;

public class RagdolledEffect extends StatusEffect {
    public RagdolledEffect() {
        super(StatusEffectCategory.HARMFUL, 0x4A4A4A);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!entity.getWorld().isClient && entity instanceof ServerPlayerEntity player) {
            player.closeHandledScreen();

            player.stopUsingItem();

            player.setVelocity(0, player.getVelocity().y, 0);
            player.velocityModified = true;
            player.setPitch(player.getPitch() * 0.5f);
        }
        return true;
    }
}