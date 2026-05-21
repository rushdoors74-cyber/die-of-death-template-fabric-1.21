package net.badware.dieofdeath.entity.client;

import net.badware.dieofdeath.entity.custom.ArtfulEntity;
import net.badware.dieofdeath.entity.custom.ArtfulPuppetEntity;
import net.badware.dieofdeath.entity.custom.BadwareEntity;
import net.badware.dieofdeath.entity.custom.PursuerEntity;
import net.minecraft.client.MinecraftClient;

public class ClientMusicHandler {

    public static void handlePursuerMusic(PursuerEntity entity) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        if (entity.isThemePlayingClient) return;

        double dist = entity.squaredDistanceTo(client.player);
        if (dist < 2500 && !entity.isLmsSuppressed()) {
            entity.isThemePlayingClient = true;
            client.getSoundManager().play(new ChaseThemeSound(entity));
        }
    }

    public static void handleBadwareMusic(BadwareEntity entity) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        if (entity.isThemePlayingClient) return;

        double dist = entity.squaredDistanceTo(client.player);
        if (dist < 2500 && !entity.isLmsSuppressed()) {
            entity.isThemePlayingClient = true;
            client.getSoundManager().play(new BadwareChaseThemeSound(entity));
        }
    }

    public static void handleArtfulMusic(ArtfulEntity entity) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        if (entity.isThemePlayingClient) return;

        double dist = entity.squaredDistanceTo(client.player);
        if (dist < 2500 && !entity.isLmsSuppressed()) {
            entity.isThemePlayingClient = true;
            client.getSoundManager().play(new ArtfulChaseThemeSound(entity));
        }
    }

    public static void handlePuppetMusic(ArtfulPuppetEntity entity) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        if (entity.isThemePlayingClient) return;

        double dist = entity.squaredDistanceTo(client.player);
        if (dist < 250) {
            entity.isThemePlayingClient = true;
            client.getSoundManager().play(new PuppetChaseThemeSound(entity));
        }
    }
}