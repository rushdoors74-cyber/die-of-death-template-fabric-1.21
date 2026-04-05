package net.badware.dieofdeath.mixin;

import net.badware.dieofdeath.effect.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyVariable(method = "damage", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private float dieofdeath$increaseDamage(float amount, DamageSource source) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity.hasStatusEffect(ModEffects.TANGLED)) {
            return amount * 1.25f;
        }

        return amount;
    }
}