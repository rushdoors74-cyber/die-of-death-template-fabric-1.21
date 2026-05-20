package net.badware.dieofdeath.entity.client;

import net.badware.dieofdeath.entity.custom.ArtfulEntity;
import net.badware.dieofdeath.sound.ModSounds;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public class ArtfulChaseThemeSound extends MovingSoundInstance {
    private final ArtfulEntity artful;

    public ArtfulChaseThemeSound(ArtfulEntity artful) {
        super(getVariantSound(artful), SoundCategory.HOSTILE, artful.getWorld().getRandom());

        this.artful = artful;
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 1.0f;
        this.relative = false;
    }

    @Override
    public void tick() {
        boolean shouldStop = this.artful == null || this.artful.isRemoved() || !this.artful.isAlive();

        if (!shouldStop) {
            net.minecraft.client.network.ClientPlayerEntity player = net.minecraft.client.MinecraftClient.getInstance().player;
            if (player != null) {
                if (this.artful.isLmsSuppressed()) shouldStop = true;

                double distanceSq = this.artful.squaredDistanceTo(player);
                if (distanceSq > 4000) shouldStop = true;
            } else {
                shouldStop = true;
            }
        }

        if (shouldStop) {
            if (this.artful != null) {
                this.artful.isThemePlayingClient = false;
                this.artful.setThemePlaying(false);
            }
            this.setDone();
            return;
        }

        this.x = (float) this.artful.getX();
        this.y = (float) this.artful.getY();
        this.z = (float) this.artful.getZ();
    }

    @Override
    public float getVolume() {
        net.minecraft.client.network.ClientPlayerEntity player = net.minecraft.client.MinecraftClient.getInstance().player;
        if (player == null || artful == null) return 0.0f;

        double distanceSq = this.artful.squaredDistanceTo(player);

        if (distanceSq < 2500) {
            return 1.0f;
        } else if (distanceSq < 3600) {
            return (float) (1.0f - ((distanceSq - 2500) / 1100.0));
        }

        return 0.0f;
    }

    private static SoundEvent getVariantSound(ArtfulEntity artful) {
        return switch (artful.getVariant()) {
            case 1 -> ModSounds.CONSTRUCTION;
            default -> ModSounds.EST_CE_TA_CARTE;
        };
    }
}