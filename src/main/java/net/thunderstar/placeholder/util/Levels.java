package net.thunderstar.placeholder.util;

import net.thunderstar.placeholder.objects.*;
import net.thunderstar.placeholder.sound.util.Sound;
import net.thunderstar.placeholder.sound.util.Sounds;

import java.awt.*;

public class Levels {
    public static class LevelHome {
        public static final float fog = 0.05f; // one over the distance in meters you can see

        public static final Sound background_music = Sounds.Music.ITS_JUST_A_BURNING_MEMORY;

        public static class vertices {
            public static Vertex V_0 = new Vertex(new Point(-5000, 5000));
            public static Vertex V_1 = new Vertex(new Point(5000, 5000));
            public static Vertex V_2 = new Vertex(new Point(5000, -5000));
            public static Vertex V_3 = new Vertex(new Point(-5000, -5000));
            public static Vertex V_4 = new Vertex(new Point(-1000, 1000));
            public static Vertex V_5 = new Vertex(new Point(1000, 1000));
            public static Vertex V_6 = new Vertex(new Point(1000, -1000));
            public static Vertex V_7 = new Vertex(new Point(-1000, -1000));
        }

        public static class walls {
            public static Wall W_0 = new Wall(vertices.V_0.v_pos, vertices.V_1.v_pos, Images.LEVEL0_WALL_1_COLORS);
            public static Wall W_1 = new Wall(vertices.V_1.v_pos, vertices.V_2.v_pos, Images.LEVEL0_WALL_1_COLORS);
            public static Wall W_2 = new Wall(vertices.V_2.v_pos, vertices.V_3.v_pos, Images.LEVEL0_WALL_1_COLORS);
            public static Wall W_3 = new Wall(vertices.V_3.v_pos, vertices.V_0.v_pos, Images.LEVEL0_WALL_1_COLORS);
            public static Wall W_12 = new Wall(vertices.V_4.v_pos, vertices.V_5.v_pos, Images.LEVEL0_WALL_1_COLORS);
            public static Wall W_13 = new Wall(vertices.V_5.v_pos, vertices.V_6.v_pos, Images.LEVEL0_WALL_1_COLORS);
            public static Wall W_14 = new Wall(vertices.V_6.v_pos, vertices.V_7.v_pos, Images.LEVEL0_WALL_1_COLORS);
            public static Wall W_15 = new Wall(vertices.V_7.v_pos, vertices.V_4.v_pos, Images.LEVEL0_WALL_1_COLORS);
        }

        public static class sectors {
            public static Sector S_0 = new Sector(new Wall[] {
                    walls.W_0
            }, new Vertex[] {
                    vertices.V_0,
                    vertices.V_1,
                    vertices.V_5,
                    vertices.V_4
            }, 0, 2500, Images.LEVEL0_FLOOR_1_COLORS, Images.LEVEL0_CEILING_1_COLORS);

            public static Sector S_1 = new Sector(new Wall[] {
                    walls.W_1
            }, new Vertex[] {
                    vertices.V_1,
                    vertices.V_2,
                    vertices.V_6,
                    vertices.V_5
            }, 0, 2500, Images.LEVEL0_FLOOR_1_COLORS, Images.LEVEL0_CEILING_1_COLORS);

            public static Sector S_2 = new Sector(new Wall[] {
                    walls.W_2
            }, new Vertex[] {
                    vertices.V_2,
                    vertices.V_3,
                    vertices.V_7,
                    vertices.V_6
            }, 0, 2500, Images.LEVEL0_FLOOR_1_COLORS, Images.LEVEL0_CEILING_1_COLORS);

            public static Sector S_3 = new Sector(new Wall[] {
                    walls.W_3
            }, new Vertex[] {
                    vertices.V_3,
                    vertices.V_0,
                    vertices.V_4,
                    vertices.V_7
            }, 0, 2500, Images.LEVEL0_FLOOR_1_COLORS, Images.LEVEL0_CEILING_1_COLORS);

            public static Sector S_4 = new Sector(new Wall[] {}, new Vertex[] {
                    vertices.V_4,
                    vertices.V_5,
                    vertices.V_6,
                    vertices.V_7
            }, -1000, 3500, Images.LEVEL0_FLOOR_1_COLORS, Images.LEVEL0_CEILING_1_COLORS);

            public static Sector S_5 = new Sector(new Wall[] {
                    walls.W_12,
                    walls.W_13,
                    walls.W_14,
                    walls.W_15
            }, new Vertex[] {

            }, -1000, 1000, Images.EMPTY, Images.EMPTY);
        }
    }

    public static Level levelHome = new Level(LevelHome.fog, "Home", LevelHome.background_music, new Vertex[] {
            LevelHome.vertices.V_0,
            LevelHome.vertices.V_1,
            LevelHome.vertices.V_2,
            LevelHome.vertices.V_3,
            LevelHome.vertices.V_4,
            LevelHome.vertices.V_5,
            LevelHome.vertices.V_6,
            LevelHome.vertices.V_7
    }, new Wall[] {
            LevelHome.walls.W_0,
            LevelHome.walls.W_1,
            LevelHome.walls.W_2,
            LevelHome.walls.W_3,
            LevelHome.walls.W_12,
            LevelHome.walls.W_13,
            LevelHome.walls.W_14,
            LevelHome.walls.W_15
    }, new Sector[] {
            LevelHome.sectors.S_0,
            LevelHome.sectors.S_1,
            LevelHome.sectors.S_2,
            LevelHome.sectors.S_3,
            LevelHome.sectors.S_4,
            LevelHome.sectors.S_5
    });
}
