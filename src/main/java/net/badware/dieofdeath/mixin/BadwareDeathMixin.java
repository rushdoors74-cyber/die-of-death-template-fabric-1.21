package net.badware.dieofdeath.mixin;

import net.badware.dieofdeath.entity.custom.BadwareEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamageTracker.class)
public abstract class BadwareDeathMixin {
    @Shadow
    @Final
    private LivingEntity entity;

    @Inject(method = "getDeathMessage", at = @At("HEAD"), cancellable = true)
    private void badware$customDeathMessage(CallbackInfoReturnable<Text> cir) {
        LivingEntity attacker = this.entity.getPrimeAdversary();
        if (attacker instanceof BadwareEntity badware) {
            if (badware.getVariant() == 2) {
                Text msg = Text.translatable("death.attack.badware_spyware", this.entity.getDisplayName())
                        .formatted(Formatting.AQUA, Formatting.BOLD);
                cir.setReturnValue(msg);
            }
        }
        if (attacker instanceof BadwareEntity badware) {
            if (badware.getVariant() == 4) {
                Text msg = Text.translatable("death.attack.badware_devilware", this.entity.getDisplayName())
                        .formatted(Formatting.RED, Formatting.BOLD);
                cir.setReturnValue(msg);
            }
        }
        if (attacker instanceof BadwareEntity badware) {
            if (badware.getVariant() == 5) {
                Text msg = Text.translatable("death.attack.badware_angelware", this.entity.getDisplayName())
                        .formatted(Formatting.WHITE, Formatting.BOLD);
                cir.setReturnValue(msg);
            }
        }
        if (attacker instanceof BadwareEntity badware) {
            if (badware.getVariant() == 7) {
                Text msg = Text.translatable("death.attack.badware_cookieware", this.entity.getDisplayName())
                        .formatted(Formatting.BOLD).formatted(Formatting.GOLD);
                cir.setReturnValue(msg);
            }
        }
        if (attacker instanceof BadwareEntity badware) {
            if (badware.getVariant() == 9) {
                Text msg = Text.translatable("death.attack.badware_wiperware", this.entity.getDisplayName())
                        .formatted(Formatting.DARK_RED, Formatting.BOLD);
                cir.setReturnValue(msg);
            }
        }
    }
}