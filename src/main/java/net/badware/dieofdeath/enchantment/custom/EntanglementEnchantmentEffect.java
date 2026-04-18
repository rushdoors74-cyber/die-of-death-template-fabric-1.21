package net.badware.dieofdeath.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.badware.dieofdeath.effect.ModEffects;
import net.badware.dieofdeath.sound.ModSounds;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;

public record EntanglementEnchantmentEffect() implements EnchantmentEntityEffect {

    public static final MapCodec<EntanglementEnchantmentEffect> CODEC = MapCodec.unit(new EntanglementEnchantmentEffect());

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity target, Vec3d pos) {
        if (target instanceof LivingEntity livingTarget) {
            boolean alreadyHasEffect = livingTarget.hasStatusEffect(ModEffects.TANGLED);

            if (!alreadyHasEffect) {
                world.playSound(null, target.getX(), target.getY(), target.getZ(),
                        ModSounds.ENTANGLED,
                        SoundCategory.PLAYERS, 1.0F, 1.0F);
            } else {
                world.playSound(null, target.getX(), target.getY(), target.getZ(),
                        ModSounds.ENTANGLED_CRITICAL,
                        SoundCategory.PLAYERS, 1.0F, 1.0F);
            }

            livingTarget.addStatusEffect(new StatusEffectInstance(ModEffects.TANGLED, 100 * level, level - 1));

            if (livingTarget.isDead() || livingTarget.getHealth() <= 0) {
                world.playSound(null, target.getX(), target.getY(), target.getZ(),
                        ModSounds.ENTANGLED_END,
                        SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}