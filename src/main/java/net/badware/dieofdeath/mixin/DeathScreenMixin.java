package net.badware.dieofdeath.mixin;

import net.badware.dieofdeath.sound.ModSounds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DeathScreen.class)
public abstract class DeathScreenMixin extends Screen {

    @Unique
    private PositionedSoundInstance deathSoundInstance;

    protected DeathScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("HEAD"))
    private void playDeathSound(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            if (deathSoundInstance != null) {
                client.getSoundManager().stop(deathSoundInstance);
            }
            deathSoundInstance = PositionedSoundInstance.master(ModSounds.DIED_OF_DEATH, 1.0f, 1.0f);
            client.getSoundManager().play(deathSoundInstance);
        }
    }

    @Override
    public void removed() {
        super.removed();
        if (deathSoundInstance != null) {
            MinecraftClient.getInstance().getSoundManager().stop(deathSoundInstance);
            deathSoundInstance = null;
        }
    }
}