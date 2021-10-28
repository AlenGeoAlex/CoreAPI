package me.Abhigya.core.util.noteblockapi.model.playmode;

import me.Abhigya.core.util.noteblockapi.model.*;
import me.Abhigya.core.util.noteblockapi.utils.CompatibilityUtils;
import me.Abhigya.core.util.noteblockapi.utils.InstrumentUtils;
import me.Abhigya.core.util.noteblockapi.utils.NoteUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/** {@link Note} is played inside of {@link Player}'s head. */
public class MonoMode extends ChannelMode {

    @Override
    public void play(
            Player player,
            Location location,
            Song song,
            Layer layer,
            Note note,
            SoundCategory soundCategory,
            float volume,
            float pitch) {
        if (InstrumentUtils.isCustomInstrument(note.getInstrument())) {
            CustomInstrument instrument =
                    song.getCustomInstruments()[
                            note.getInstrument() - InstrumentUtils.getCustomInstrumentFirstIndex()];

            if (instrument.getSound() != null) {
                CompatibilityUtils.playSound(
                        player, location, instrument.getSound(), soundCategory, volume, pitch, 0);
            } else {
                CompatibilityUtils.playSound(
                        player,
                        location,
                        instrument.getSoundFileName(),
                        soundCategory,
                        volume,
                        pitch,
                        0);
            }
        } else {
            CompatibilityUtils.playSound(
                    player,
                    location,
                    InstrumentUtils.getInstrument(note.getInstrument()),
                    soundCategory,
                    volume,
                    pitch,
                    0);
        }
    }

    @Override
    public void play(
            Player player,
            Location location,
            Song song,
            Layer layer,
            Note note,
            SoundCategory soundCategory,
            float volume,
            boolean doTranspose) {
        float pitch;
        if (doTranspose) pitch = NoteUtils.getPitchTransposed(note);
        else pitch = NoteUtils.getPitchInOctave(note);
        if (InstrumentUtils.isCustomInstrument(note.getInstrument())) {
            CustomInstrument instrument =
                    song.getCustomInstruments()[
                            note.getInstrument() - InstrumentUtils.getCustomInstrumentFirstIndex()];

            if (!doTranspose) {
                CompatibilityUtils.playSound(
                        player,
                        location,
                        InstrumentUtils.warpNameOutOfRange(
                                instrument.getSoundFileName(), note.getKey(), note.getPitch()),
                        soundCategory,
                        volume,
                        pitch,
                        0);
            } else {
                if (instrument.getSound() != null) {
                    CompatibilityUtils.playSound(
                            player,
                            location,
                            instrument.getSound(),
                            soundCategory,
                            volume,
                            pitch,
                            0);
                } else {
                    CompatibilityUtils.playSound(
                            player,
                            location,
                            instrument.getSoundFileName(),
                            soundCategory,
                            volume,
                            pitch,
                            0);
                }
            }

        } else {
            if (NoteUtils.isOutOfRange(note.getKey(), note.getPitch()) && !doTranspose) {
                CompatibilityUtils.playSound(
                        player,
                        location,
                        InstrumentUtils.warpNameOutOfRange(
                                note.getInstrument(), note.getKey(), note.getPitch()),
                        soundCategory,
                        volume,
                        pitch,
                        0);
            } else {
                CompatibilityUtils.playSound(
                        player,
                        location,
                        InstrumentUtils.getInstrument(note.getInstrument()),
                        soundCategory,
                        volume,
                        pitch,
                        0);
            }
        }
    }
}
