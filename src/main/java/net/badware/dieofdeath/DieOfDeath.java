package net.badware.dieofdeath;

import net.badware.dieofdeath.block.ModBlocks;
import net.badware.dieofdeath.effect.ModEffects;
import net.badware.dieofdeath.enchantment.custom.EntanglementEnchantmentEffect;
import net.badware.dieofdeath.item.ModItemGroups;
import net.badware.dieofdeath.item.ModItems;
import net.badware.dieofdeath.item.advanced.ModArmorItem;
import net.badware.dieofdeath.sound.ModSounds;
import net.badware.dieofdeath.util.HammerUsageEvent;
import net.badware.dieofdeath.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
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

		FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.BACKYARD_BUSH, 30, 60);

		ModSounds.registerSounds();

		ModEffects.registerEffects();

        ModWorldGeneration.generateModWorldGen();

		Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE,
				Identifier.of("dieofdeath", "entanglement"),
				EntanglementEnchantmentEffect.CODEC);

		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.KILLER_ONLY_BLOCK, RenderLayer.getTranslucent());

		FuelRegistry.INSTANCE.add(ModItems.BOILING_WATER, 6020);

		PlayerBlockBreakEvents.BEFORE.register(new HammerUsageEvent());

		ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
			return ModArmorItem.onPlayerDamage(entity, source, amount);
		});
	}
}