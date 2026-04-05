package net.badware.dieofdeath.util;

import net.badware.dieofdeath.item.ModItems;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class ModModelPredicates {

    public static void registerModModels() {
        registerCustomBow(ModItems.HARKEN_BOW);
    }

    private static void registerCustomBow(Item item) {
        ModelPredicateProviderRegistry.register(item, Identifier.of("pull"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            }
            if (entity.getActiveItem() != stack) {
                return 0.0F;
            }

            return (float)(stack.getMaxUseTime(entity) - entity.getItemUseTimeLeft()) / 20.0F;
        });

        ModelPredicateProviderRegistry.register(item, Identifier.of("pulling"), (stack, world, entity, seed) -> {
            return entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F;
        });
    }
}