package net.badware.dieofdeath.mixin;

import net.badware.dieofdeath.entity.custom.PursuerEntity;
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
public abstract class PursuerDeathMixin {
    @Shadow @Final private LivingEntity entity;

    @Inject(method = "getDeathMessage", at = @At("HEAD"), cancellable = true)
    private void pursuer$customDeathMessage(CallbackInfoReturnable<Text> cir) {
        LivingEntity attacker = this.entity.getPrimeAdversary();

        if (attacker instanceof PursuerEntity pursuer) {
            if (pursuer.getVariant() == 10) {
                Text msg = Text.translatable("death.attack.pursuer_maze_guy", this.entity.getDisplayName())
                        .formatted(Formatting.BLUE, Formatting.BOLD);
                cir.setReturnValue(msg);
            }
            else if (pursuer.getVariant() == 14) {
                Text msg = Text.translatable("death.attack.pursuer_thec", this.entity.getDisplayName())
                        .formatted(Formatting.RED, Formatting.BOLD);
                cir.setReturnValue(msg);
            }
            else if (pursuer.getVariant() == 15) {
                Text msg = Text.translatable("death.attack.pursuer_bling", this.entity.getDisplayName())
                        .formatted(Formatting.DARK_GREEN, Formatting.BOLD);
                cir.setReturnValue(msg);
            }
        }
    }
}