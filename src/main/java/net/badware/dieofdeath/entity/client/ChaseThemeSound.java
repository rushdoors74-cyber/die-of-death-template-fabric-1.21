package net.badware.dieofdeath.entity.client;

import net.badware.dieofdeath.entity.custom.PursuerEntity;
import net.badware.dieofdeath.sound.ModSounds;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public class ChaseThemeSound extends MovingSoundInstance {
    private final PursuerEntity pursuer;

    public ChaseThemeSound(PursuerEntity pursuer) {
        super(getVariantSound(pursuer), SoundCategory.HOSTILE, pursuer.getWorld().getRandom());

        this.pursuer = pursuer;
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 1.0f;
        this.relative = false;
        this.attenuationType = AttenuationType.LINEAR;
    }

    @Override
    public void tick() {
        boolean shouldStop = this.pursuer == null || this.pursuer.isRemoved() || !this.pursuer.isAlive();

        if (!shouldStop) {
            net.minecraft.client.network.ClientPlayerEntity player = net.minecraft.client.MinecraftClient.getInstance().player;
            if (player != null) {
                if (this.pursuer.isLmsSuppressed()) shouldStop = true;

                double distanceSq = this.pursuer.squaredDistanceTo(player);
                if (distanceSq > 4000) shouldStop = true;
            } else {
                shouldStop = true;
            }
        }

        if (shouldStop) {
            if (this.pursuer != null) {
                this.pursuer.isThemePlayingClient = false;
                this.pursuer.setThemePlaying(false);
            }
            this.setDone();
            return;
        }

        this.x = (float) this.pursuer.getX();
        this.y = (float) this.pursuer.getY();
        this.z = (float) this.pursuer.getZ();
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
    private static SoundEvent getVariantSound(PursuerEntity pursuer) {
        return switch (pursuer.getVariant()) {
            case 1 -> ModSounds.INAPPETENCE;
            case 2 -> ModSounds.A_FRIENDS_BRO;
            case 3 -> ModSounds.FRIGHTENED;
            case 4, 5, 6 -> ModSounds.APOCALYPSE;
            case 7 -> ModSounds.STALKER_CHASE_THEME;
            case 8, 9 -> ModSounds.INSANELY;
            case 10 -> ModSounds.PLAY_MY_MAZE_GAME;
            case 11 -> ModSounds.BEYOND_THE_BOUNDS_OF_POSSIBILITY;
            case 12, 13 -> ModSounds.ACIDULUS_V2;
            case 14 -> ModSounds.THEC_CHASE_THEME;
            case 15 -> ModSounds.MONETIZATION;
            default -> ModSounds.STARVATION_V3;
        };
    }
}