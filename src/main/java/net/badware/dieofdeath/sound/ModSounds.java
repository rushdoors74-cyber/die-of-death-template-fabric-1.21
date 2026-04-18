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
    public static final SoundEvent WOOD_BOX_BREAK = registerSoundEvent("wood_box_break");

    public static final BlockSoundGroup IMPLEMENT_SOUNDS = new BlockSoundGroup(2f, 1.2f,
            ModSounds.IMPLEMENT_BREAK,
            SoundEvents.BLOCK_STONE_STEP,
            ModSounds.IMPLEMENT_PLACE,
            SoundEvents.BLOCK_STONE_HIT,
            SoundEvents.BLOCK_STONE_FALL);

    public static final BlockSoundGroup WOOD_BREAKING_FIRE = new BlockSoundGroup(
                            1.0f,
                            1.0f,
                            ModSounds.WOOD_BOX_BREAK,
                            SoundEvents.BLOCK_WOOD_STEP,
                            SoundEvents.BLOCK_WOOD_PLACE,
                            SoundEvents.BLOCK_WOOD_HIT,
                            SoundEvents.BLOCK_WOOD_FALL);

    public static final SoundEvent WAND_USE = registerSoundEvent("wand_use");
    public static final SoundEvent PURSUER_CLEAVE_SFX = registerSoundEvent("pursuer_cleave_sfx");
    public static final SoundEvent BONUSPAD_STEPPED = registerSoundEvent("bonuspad_stepped");
    public static final SoundEvent CAREPAD_HEAL = registerSoundEvent("carepad_heal");
    public static final SoundEvent BLOCKED = registerSoundEvent("blocked");
    public static final SoundEvent ENTANGLED_CRITICAL = registerSoundEvent("entangled_critical");
    public static final SoundEvent ENTANGLED_END = registerSoundEvent("entangled_end");
    public static final SoundEvent BLEEDING = registerSoundEvent("bleeding");
    public static final SoundEvent PURSUER_SWINGING = registerSoundEvent("pursuer_swinging");
    public static final SoundEvent PURSUER_CLEAVE_START = registerSoundEvent("pursuer_cleave_start");
    public static final SoundEvent PURSUER_CLEAVE_END = registerSoundEvent("pursuer_cleave_end");
    public static final SoundEvent PURSUER_SCREAM = registerSoundEvent("pursuer_scream");
    public static final SoundEvent PURSUER_SHOCKWAVE = registerSoundEvent("pursuer_shockwave");
    public static final SoundEvent STARVATION_V3 = registerSoundEvent("starvation_v3");

    public static SoundEvent ETERNITY_V2 = registerSoundEvent("eternity_v2");
    public static final RegistryKey<JukeboxSong> ETERNITY_V2_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "eternity_v2"));

    public static final SoundEvent GRANDMA_BACKYARD = registerSoundEvent("grandma_backyard");
    public static final RegistryKey<JukeboxSong> GRANDMA_BACKYARD_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "grandma_backyard"));

    public static final SoundEvent TEMPLE = registerSoundEvent("temple");
    public static final RegistryKey<JukeboxSong> TEMPLE_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "temple"));

    public static final SoundEvent ROBLOX_HQ = registerSoundEvent("roblox_hq");
    public static final RegistryKey<JukeboxSong> ROBLOX_HQ_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "roblox_hq"));

    public static final SoundEvent BASEPLATE = registerSoundEvent("baseplate");
    public static final RegistryKey<JukeboxSong> BASEPLATE_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "baseplate"));

    public static final SoundEvent TUNDRA_TRENCH = registerSoundEvent("tundra_trench");
    public static final RegistryKey<JukeboxSong> TUNDRA_TRENCH_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "tundra_trench"));

    public static final SoundEvent TEAPOT_PALACE_TOUR_CALM = registerSoundEvent("teapot_palace_tour_calm");
    public static final RegistryKey<JukeboxSong> TEAPOT_PALACE_TOUR_CALM_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "teapot_palace_tour_calm"));

    public static final SoundEvent TEAPOT_PALACE_TOUR_RAIN = registerSoundEvent("teapot_palace_tour_rain");
    public static final RegistryKey<JukeboxSong> TEAPOT_PALACE_TOUR_RAIN_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "teapot_palace_tour_rain"));

    public static final SoundEvent TEAPOT_PALACE_TOUR_LMS = registerSoundEvent("teapot_palace_tour_lms");
    public static final RegistryKey<JukeboxSong> TEAPOT_PALACE_TOUR_LMS_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "teapot_palace_tour_lms"));

    public static final SoundEvent OMEGAS_FINAL_STAND = registerSoundEvent("omegas_final_stand");
    public static final RegistryKey<JukeboxSong> OMEGAS_FINAL_STAND_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "omegas_final_stand"));

    public static final SoundEvent CARELESS = registerSoundEvent("careless");
    public static final RegistryKey<JukeboxSong> CARELESS_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "careless"));

    public static final SoundEvent VIGILANTE_SHOOTDOWN = registerSoundEvent("vigilante_shootdown");
    public static final RegistryKey<JukeboxSong> VIGILANTE_SHOOTDOWN_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "vigilante_shootdown"));

    public static final SoundEvent SHOWTIME = registerSoundEvent("showtime");
    public static final RegistryKey<JukeboxSong> SHOWTIME_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "showtime"));

    public static final SoundEvent ETERNITY = registerSoundEvent("eternity");
    public static final RegistryKey<JukeboxSong> ETERNITY_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "eternity"));

    public static final SoundEvent Y2K = registerSoundEvent("y2k");
    public static final RegistryKey<JukeboxSong> Y2K_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "y2k"));

    public static final SoundEvent ONE_BOUNCE = registerSoundEvent("one_bounce");
    public static final RegistryKey<JukeboxSong> ONE_BOUNCE_KEY =
            RegistryKey.of(RegistryKeys.JUKEBOX_SONG, Identifier.of(DieOfDeath.MOD_ID, "one_bounce"));


    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(DieOfDeath.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        DieOfDeath.LOGGER.info("Registering Mod Sounds for " + DieOfDeath.MOD_ID);
    }
}
