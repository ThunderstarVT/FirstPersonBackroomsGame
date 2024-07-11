package net.thunderstar.placeholder.sound.util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    public Clip s_clip;
    public int s_duration;

    public Sound(String file_name, float duration) {
        File soundFile = new File(Sounds.defaultPath + file_name);
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            s_clip = clip;
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }

        s_duration = (int) (duration*100);
    }
}
