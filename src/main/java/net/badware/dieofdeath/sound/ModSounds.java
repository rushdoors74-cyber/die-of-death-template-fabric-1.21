package net.badware.dieofdeath.sound;

import net.badware.dieofdeath.DieOfDeath;
import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent IMPLEMENT_BREAK = registerSoundEvent("implement_break");
    public static final SoundEvent IMPLEMENT_PLACE = registerSoundEvent("implement_place");
    public static final SoundEvent ENTANGLED = registerSoundEvent("entangled");

    public static final BlockSoundGroup IMPLEMENT_SOUNDS = new BlockSoundGroup(2f, 1.2f,
            ModSounds.IMPLEMENT_BREAK,
            SoundEvents.BLOCK_STONE_STEP,
            ModSounds.IMPLEMENT_PLACE,
            SoundEvents.BLOCK_STONE_HIT,
            SoundEvents.BLOCK_STONE_FALL);

    public static final SoundEvent WAND_USE = registerSoundEvent("wand_use");

    public static SoundEvent ETERNITY_V2 = registerSoundEvent("eternity_v2");
    public static final RegistryKey<JukeboxSong> ETERNITY_V2_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "eternity_v2"));


    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(DieOfDeath.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        DieOfDeath.LOGGER.info("Registering Mod Sounds for " + DieOfDeath.MOD_ID);
    }
}
