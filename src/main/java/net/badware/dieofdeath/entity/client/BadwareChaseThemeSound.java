package net.badware.dieofdeath.entity.client;

import net.badware.dieofdeath.entity.custom.BadwareEntity;
import net.badware.dieofdeath.sound.ModSounds;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public class BadwareChaseThemeSound extends MovingSoundInstance {
    private final BadwareEntity badware;

    public BadwareChaseThemeSound(BadwareEntity badware) {
        super(getVariantSound(badware), SoundCategory.HOSTILE, badware.getWorld().getRandom());

        this.badware = badware;
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 1.0f;
        this.relative = false;
        this.attenuationType = AttenuationType.LINEAR;
    }

    @Override
    public void tick() {
        boolean shouldStop = this.badware == null || this.badware.isRemoved() || !this.badware.isAlive();

        if (!shouldStop) {
            net.minecraft.client.network.ClientPlayerEntity player = net.minecraft.client.MinecraftClient.getInstance().player;
            if (player != null) {
                if (this.badware.isLmsSuppressed()) shouldStop = true;

                double distanceSq = this.badware.squaredDistanceTo(player);
                if (distanceSq > 4000) shouldStop = true;
            } else {
                shouldStop = true;
            }
        }

        if (shouldStop) {
            if (this.badware != null) {
                this.badware.isThemePlayingClient = false;
                this.badware.setThemePlaying(false);
            }
            this.setDone();
            return;
        }

        this.x = (float) this.badware.getX();
        this.y = (float) this.badware.getY();
        this.z = (float) this.badware.getZ();
    }

    @Override
    public float getVolume() {
        net.minecraft.client.network.ClientPlayerEntity player = net.minecraft.client.MinecraftClient.getInstance().player;
        if (player == null || badware == null) return 0.0f;

        double distanceSq = this.badware.squaredDistanceTo(player);

        if (distanceSq < 2500) {
            return 1.0f;
        } else if (distanceSq < 3600) {
            return (float) (1.0f - ((distanceSq - 2500) / 1100.0));
        }

        return 0.0f;
    }
    private static SoundEvent getVariantSound(BadwareEntity badware) {
        return switch (badware.getVariant()) {
            case 9 -> ModSounds.T0_B3_3RA5ED;
            case 8 -> ModSounds.HEATED_ROCKS_FROM_MAR;
            case 7 -> ModSounds.MADE_WITH_CARE;
            case 6 -> ModSounds.THORNS_THAT_PIERCE_BY_LEXXIEMOW;
            case 5 -> ModSounds.PURIFIED;
            case 4 -> ModSounds.PANDEMIC_PANDEMONIUM;
            case 3 -> ModSounds.I_HEART_U;
            case 2 -> ModSounds.EYESPY;
            case 1 -> ModSounds.INSCRIPTUS;
            default -> ModSounds.POLYMORPHIC;
        };
    }
}