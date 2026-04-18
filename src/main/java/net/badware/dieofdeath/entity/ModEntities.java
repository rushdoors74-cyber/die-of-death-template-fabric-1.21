package net.badware.dieofdeath.entity;

import net.badware.dieofdeath.DieOfDeath;
import net.badware.dieofdeath.entity.custom.PursuerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<PursuerEntity> PURSUER = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(DieOfDeath.MOD_ID, "pursuer"),
            EntityType.Builder.create(PursuerEntity::new, SpawnGroup.MONSTER)
                    .dimensions(1.25f, 2.3f)
                    .build()
    );

    public static void registerModEntities() {
        DieOfDeath.LOGGER.info("Registering Entities for " + DieOfDeath.MOD_ID);
    }
}