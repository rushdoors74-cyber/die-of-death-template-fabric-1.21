package net.badware.dieofdeath.item;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class ModFoodComponents {
    public static final FoodComponent HOTDOG = new FoodComponent.Builder().nutrition(8).saturationModifier(0.7f).alwaysEdible().snack()
            .statusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 4800, 1), 1.0f).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 20, 4), 1.0f)
            .statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 4), 1.0f).statusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 140), 0.10f).build();

}
