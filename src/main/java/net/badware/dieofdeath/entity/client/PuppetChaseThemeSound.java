package net.badware.dieofdeath.entity.client;

import net.badware.dieofdeath.entity.custom.ArtfulPuppetEntity;
import net.badware.dieofdeath.sound.ModSounds;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.sound.SoundCategory;

public class PuppetChaseThemeSound extends MovingSoundInstance {
    private final ArtfulPuppetEntity puppet;

    public PuppetChaseThemeSound(ArtfulPuppetEntity puppet) {
        super(ModSounds.ARTFUL_PUPPET_AMBIENCE, SoundCategory.HOSTILE, puppet.getWorld().getRandom());

        this.puppet = puppet;
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 1.0f;
        this.relative = false;
        this.attenuationType = AttenuationType.LINEAR;
    }

    @Override
    public void tick() {
        boolean shouldStop = this.puppet == null || this.puppet.isRemoved() || !this.puppet.isAlive();

        if (!shouldStop) {
            net.minecraft.client.network.ClientPlayerEntity player = net.minecraft.client.MinecraftClient.getInstance().player;
            if (player != null) {
                double distanceSq = this.puppet.squaredDistanceTo(player);
                if (distanceSq > 400) shouldStop = true;
            } else {
                shouldStop = true;
            }
        }

        if (shouldStop) {
            this.setDone();
            return;
        }

        this.x = (float) this.puppet.getX();
        this.y = (float) this.puppet.getY();
        this.z = (float) this.puppet.getZ();
    }
}