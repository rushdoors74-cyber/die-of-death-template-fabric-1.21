package net.badware.dieofdeath.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class RiftOverlay {
    public static void render(DrawContext context) {
        int width = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int height = MinecraftClient.getInstance().getWindow().getScaledHeight();
        context.fill(0, 0, width, height, 0x80808080);
    }
}