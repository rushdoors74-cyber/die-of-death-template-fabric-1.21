package net.badware.dieofdeath.effect;

import net.badware.dieofdeath.sound.ModSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class BleedEffect extends StatusEffect {
    public BleedEffect() {
        super(StatusEffectCategory.HARMFUL, 0x8B0000);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.getHealth() > 1.0f) {
            float damageAmount = (amplifier >= 1) ? 2.0f : 1.0f;
            entity.damage(entity.getDamageSources().magic(), damageAmount);
        }

        if (entity.age % 20 == 0) {
            entity.getWorld().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                    ModSounds.BLEEDING,
                    entity.getSoundCategory(), 1.0f, 1.0f);
        }

        return true;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % 10 == 0;
    }
}