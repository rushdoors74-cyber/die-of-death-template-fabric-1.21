package net.badware.dieofdeath;

import net.badware.dieofdeath.block.ModBlocks;
import net.badware.dieofdeath.item.ModItemGroups;
import net.badware.dieofdeath.item.ModItems;
import net.badware.dieofdeath.util.HammerUsageEvent;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.impl.content.registry.FuelRegistryImpl;
import net.minecraft.client.render.RenderLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DieOfDeath implements ModInitializer {
	public static final String MOD_ID = "dieofdeath";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.KILLER_ONLY_BLOCK, RenderLayer.getTranslucent());

		FuelRegistry.INSTANCE.add(ModItems.BOILING_WATER, 6020);

		PlayerBlockBreakEvents.BEFORE.register(new HammerUsageEvent());

	}
}