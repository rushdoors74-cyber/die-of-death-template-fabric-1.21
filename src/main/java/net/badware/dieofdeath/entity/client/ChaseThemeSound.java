package net.badware.dieofdeath.entity.client;

import net.badware.dieofdeath.entity.custom.PursuerEntity;
import net.badware.dieofdeath.sound.ModSounds;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.sound.SoundCategory;

public class ChaseThemeSound extends MovingSoundInstance {
    private final PursuerEntity pursuer;

    public ChaseThemeSound(PursuerEntity pursuer) {
        super(pursuer.getVariant() == 1 ? ModSounds.INAPPETENCE : ModSounds.STARVATION_V3, SoundCategory.HOSTILE, pursuer.getWorld().getRandom());

        this.pursuer = pursuer;
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 1.0f;
        this.relative = false;
    }

    @Override
    public void tick() {
        if (this.pursuer == null || this.pursuer.isRemoved() || !this.pursuer.isAlive()) {
            this.setDone();
            if (this.pursuer != null) this.pursuer.setThemePlaying(false);
            return;
        }

        this.x = (float) this.pursuer.getX();
        this.y = (float) this.pursuer.getY();
        this.z = (float) this.pursuer.getZ();

        net.minecraft.client.network.ClientPlayerEntity player = net.minecraft.client.MinecraftClient.getInstance().player;

        if (player != null) {
            double distanceSq = this.pursuer.squaredDistanceTo(player);
            if (distanceSq > 4000) {
                this.setDone();
                this.pursuer.setThemePlaying(false);
            }
        } else {
            this.setDone();
        }
    }

    @Override
    public float getVolume() {
        net.minecraft.client.network.ClientPlayerEntity player = net.minecraft.client.MinecraftClient.getInstance().player;
        if (player == null || pursuer == null) return 0.0f;

        double distanceSq = this.pursuer.squaredDistanceTo(player);

        if (distanceSq < 2500) {
            return 1.0f;
        } else if (distanceSq < 3600) {
            return (float) (1.0f - ((distanceSq - 2500) / 1100.0));
        }

        return 0.0f;
    }
}