package net.badware.dieofdeath.entity;

import net.badware.dieofdeath.DieOfDeath;
import net.badware.dieofdeath.entity.custom.BadwareEntity;
import net.badware.dieofdeath.entity.custom.BadwarePCEntity;
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
                    .dimensions(1.9f, 2.3f).eyeHeight(1.9f)
                    .build()
    );

    public static final EntityType<BadwareEntity> BADWARE = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(DieOfDeath.MOD_ID, "badware"),
            EntityType.Builder.create(BadwareEntity::new, SpawnGroup.MONSTER)
                    .dimensions(1.0f, 2.38f).eyeHeight(2.15f)
                    .build()
    );

    public static final EntityType<BadwarePCEntity> BADWARE_PC = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(DieOfDeath.MOD_ID,"badware_pc"),
            EntityType.Builder.create(BadwarePCEntity::new, SpawnGroup.CREATURE)
                    .dimensions(2.0f, 2.38f)
                    .build()
    );

    public static void registerModEntities() {
        DieOfDeath.LOGGER.info("Registering Entities for " + DieOfDeath.MOD_ID);
    }
}