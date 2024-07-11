package net.thunderstar.placeholder.sound;

import net.thunderstar.placeholder.entities.Player;
import net.thunderstar.placeholder.sim.util.SimHelpFunctions;
import net.thunderstar.placeholder.sound.util.Sound;

import java.util.Timer;
import java.util.TimerTask;

public class SoundMain {
    public void run() {
        loop();
    }

    public void loop() {
        final int[] time_until_music_start = {0};

        final Sound[] current_music = {null};

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (time_until_music_start[0] >= 0) {
                    time_until_music_start[0]--;
                }

                if (time_until_music_start[0] <= 0) {
                    if (current_music[0] != null) {
                        current_music[0].s_clip.stop();
                        current_music[0].s_clip.setMicrosecondPosition(0);
                    }
                    Sound backgroundMusic = SimHelpFunctions.randomElement(Player.variables.current_level.l_backgroundSongs);
                    backgroundMusic.s_clip.setMicrosecondPosition(0);
                    backgroundMusic.s_clip.start();
                    current_music[0] = backgroundMusic;
                    time_until_music_start[0] = backgroundMusic.s_duration;
                }
            }
        };

        Timer timer = new Timer();

        long intervalPeriod = 10;

        timer.scheduleAtFixedRate(task, 0, intervalPeriod);
    }
}
