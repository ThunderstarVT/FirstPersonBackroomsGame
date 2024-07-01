package net.thunderstar.placeholder.sound.util;

public class Sounds {
    public static String defaultPath = "..\\First Person Backrooms Game\\src\\main\\resources\\assets\\placeholder\\sounds\\";

    public static class Music {
        public static Sound ITS_JUST_A_BURNING_MEMORY = new Sound("music\\its_just_a_burning_memory.wav", 53081);
    }

    public static class SFX {

    }

    public static Sound[] sounds = {
            Music.ITS_JUST_A_BURNING_MEMORY
    };
}
