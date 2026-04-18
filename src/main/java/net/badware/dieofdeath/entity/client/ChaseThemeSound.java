package net.badware.dieofdeath.entity.client;

import net.badware.dieofdeath.entity.custom.PursuerEntity;
import net.badware.dieofdeath.sound.ModSounds;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.sound.SoundCategory;

public class ChaseThemeSound extends MovingSoundInstance {
    private final PursuerEntity pursuer;

    public ChaseThemeSound(PursuerEntity pursuer) {
        super(ModSounds.STARVATION_V3, SoundCategory.HOSTILE, pursuer.getWorld().getRandom());
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
            return;
        }

        this.x = (float) this.pursuer.getX();
        this.y = (float) this.pursuer.getY();
        this.z = (float) this.pursuer.getZ();

        net.minecraft.client.network.ClientPlayerEntity player = net.minecraft.client.MinecraftClient.getInstance().player;

        if (player != null) {
            double distanceSq = this.pursuer.squaredDistanceTo(player);

            if (distanceSq > 3600) {
                this.volume -= 0.05f;
                if (this.volume <= 0) {
                    this.setDone();
                }
            } else {
                this.volume = Math.min(this.volume + 0.05f, 1.0f);
            }
        } else {
            this.setDone();
        }
    }
}