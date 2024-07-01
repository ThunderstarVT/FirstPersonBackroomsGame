package net.thunderstar.placeholder.entities;

import net.thunderstar.placeholder.objects.Level;
import net.thunderstar.placeholder.util.Levels;

public class Player {
    public static class variables {
        public static float posX = 2500;
        public static float posY = 2500;
        public static float posZ = 0;

        public static float rotation = 45;

        public static float velX = 0;
        public static float velY = 0;
        public static float velZ = 0;

        public static int mouseX = 0;

        public static Level current_level = Levels.levelHome;
    }

    public static class constants {
        public static final float camera_offset = 1500;

        public static final float camera_fov = 200; // not an angle, works opposite of what you expect: lower is wider

        public static final float rotate_factor = 0.05f;
    }


}
