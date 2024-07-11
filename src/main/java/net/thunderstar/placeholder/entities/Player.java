package net.thunderstar.placeholder.entities;

import net.thunderstar.placeholder.objects.Level;
import net.thunderstar.placeholder.objects.Sector;
import net.thunderstar.placeholder.render.util.RenderHelpFunctions;
import net.thunderstar.placeholder.util.Levels;

import java.awt.*;

public class Player {
    public static class variables {
        public static float posX = 900;
        public static float posY = 5000;
        public static float posZ = 0;

        public static float rotation = 45;

        public static float velX = 0;
        public static float velY = 0;
        public static float velZ = 0;

        public static int mouseX = 0;

        public static Level current_level = Levels.level0;

        public static float health = 100;
    }

    public static class constants {
        public static final float camera_offset = 1500;

        public static final float camera_fov = 200; // not an angle, works opposite of what you expect: lower is wider
        public static final float r_fov = 1/camera_fov;

        public static final float rotate_factor = 0.05f;
    }

    public static class functions {
        public static Sector getCurrentSector() {
            for (Sector sector : variables.current_level.l_sectors) {
                if (RenderHelpFunctions.checkInPolygonBSP(sector.s_vertices, new Point((int) variables.posX, (int) variables.posY))) {
                    return sector;
                }
            }
            return null;
        }
    }
}
