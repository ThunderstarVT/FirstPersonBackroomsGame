package net.thunderstar.placeholder.util;

import net.thunderstar.placeholder.entities.EntityBase;
import net.thunderstar.placeholder.entities.one_to_ninety_nine.Entity_1;
import net.thunderstar.placeholder.entities.util.Entity_Textures;
import net.thunderstar.placeholder.objects.*;
import net.thunderstar.placeholder.render.util.Texture;
import net.thunderstar.placeholder.entities.util.Texture_Rotation;
import net.thunderstar.placeholder.sound.util.Sound;
import net.thunderstar.placeholder.sound.util.Sounds;

import java.awt.*;

public class Levels {
    public static class LevelHome {
        public static final float fog = 0.05f; // one over the distance in meters you can see

        public static final Sound[] background_songs = {
                Sounds.Music.ITS_JUST_A_BURNING_MEMORY,
                Sounds.Music.TEST
        };

        public static class vertices {
            public static Vertex V_0 = new Vertex(new Point(-5000, 5000));
            public static Vertex V_1 = new Vertex(new Point(5000, 5000));
        }

        public static class walls {
            public static Wall W_0 = new Wall(vertices.V_0.v_pos, vertices.V_1.v_pos, Textures.LEVEL0_WALL_1);
        }

        public static class sectors {
            public static Sector S_0 = new Sector(new Wall[] {
                walls.W_0
            }, new Vertex[] {

            }, 0, 2500, Textures.LEVEL0_FLOOR_1, Textures.LEVEL0_CEILING_1);
        }
    }

    public static Level levelHome = new Level(LevelHome.fog, "Home", LevelHome.background_songs, new Sector[] {
            LevelHome.sectors.S_0
    }, new Wall[] {
            LevelHome.walls.W_0
    }, new EntityBase[] {

    });


    public static class Level0 {
        public static final float fog = 0.05f;

        public static final Sound[] background_songs = {
                Sounds.Music.ITS_JUST_A_BURNING_MEMORY
        };

        public static class vertices {
            public static Vertex V_0  = new Vertex(new Point(-3500,-500));
            public static Vertex V_1  = new Vertex(new Point(-2500,-500));
            public static Vertex V_2  = new Vertex(new Point(-2500,-2500));
            public static Vertex V_3  = new Vertex(new Point(-750,-2500));
            public static Vertex V_4  = new Vertex(new Point(-2500,-5500));
            public static Vertex V_5  = new Vertex(new Point(2500,-5500));
            public static Vertex V_6  = new Vertex(new Point(2500,-2500));
            public static Vertex V_7  = new Vertex(new Point(750,-2500));
            public static Vertex V_8  = new Vertex(new Point(2500,2500));
            public static Vertex V_9  = new Vertex(new Point(750,2500));
            public static Vertex V_10 = new Vertex(new Point(2500,5500));
            public static Vertex V_11 = new Vertex(new Point(-2500,5500));
            public static Vertex V_12 = new Vertex(new Point(-2500,2500));
            public static Vertex V_13 = new Vertex(new Point(-750,2500));
            public static Vertex V_14 = new Vertex(new Point(-2500,500));
            public static Vertex V_15 = new Vertex(new Point(-3500,500));
        }

        public static class walls {
            public static Wall W_0  = new Wall(vertices.V_2.v_pos,  vertices.V_3.v_pos,  Textures.LEVEL0_WALL_1).setSize(0, 2000);
            public static Wall W_1  = new Wall(vertices.V_2.v_pos,  vertices.V_4.v_pos,  Textures.LEVEL0_WALL_1).setSize(0, 2000);
            public static Wall W_2  = new Wall(vertices.V_4.v_pos,  vertices.V_5.v_pos,  Textures.LEVEL0_WALL_1).setSize(0, 2000);
            public static Wall W_3  = new Wall(vertices.V_5.v_pos,  vertices.V_6.v_pos,  Textures.LEVEL0_WALL_1).setSize(0, 2000);
            public static Wall W_4  = new Wall(vertices.V_6.v_pos,  vertices.V_7.v_pos,  Textures.LEVEL0_WALL_1).setSize(0, 2000);
            public static Wall W_5  = new Wall(vertices.V_6.v_pos,  vertices.V_8.v_pos,  Textures.LEVEL0_WALL_1).setSize(0, 2000);
            public static Wall W_6  = new Wall(vertices.V_8.v_pos,  vertices.V_9.v_pos,  Textures.LEVEL0_WALL_1).setSize(0, 2000);
            public static Wall W_7  = new Wall(vertices.V_12.v_pos, vertices.V_13.v_pos, Textures.LEVEL0_WALL_1).setSize(0, 2000);
            public static Wall W_8  = new Wall(vertices.V_12.v_pos, vertices.V_14.v_pos, Textures.LEVEL0_WALL_1).setSize(0, 2000);
            public static Wall W_9  = new Wall(vertices.V_1.v_pos,  vertices.V_2.v_pos,  Textures.LEVEL0_WALL_1).setSize(0, 2000);
            public static Wall W_10 = new Wall(vertices.V_8.v_pos,  vertices.V_10.v_pos, Textures.LEVEL0_WALL_1).setSize(0, 2000);
            public static Wall W_11 = new Wall(vertices.V_10.v_pos, vertices.V_11.v_pos, Textures.LEVEL0_WALL_1).setSize(0, 2000);
            public static Wall W_12 = new Wall(vertices.V_11.v_pos, vertices.V_12.v_pos, Textures.LEVEL0_WALL_1).setSize(0, 2000);
            public static Wall W_13 = new Wall(vertices.V_14.v_pos, vertices.V_15.v_pos, Textures.LEVEL0_WALL_1).setSize(0, 2000);
            public static Wall W_14 = new Wall(vertices.V_0.v_pos,  vertices.V_1.v_pos,  Textures.LEVEL0_WALL_1).setSize(0, 2000);
        }

        public static class portals {
            public static Portal P_0_1 = new Portal(vertices.V_3.v_pos, vertices.V_7.v_pos);
            public static Portal P_1_0 = new Portal(vertices.V_3.v_pos, vertices.V_7.v_pos);
            public static Portal P_1_2 = new Portal(vertices.V_9.v_pos, vertices.V_13.v_pos);
            public static Portal P_2_1 = new Portal(vertices.V_9.v_pos, vertices.V_13.v_pos);
            public static Portal P_1_3 = new Portal(vertices.V_1.v_pos, vertices.V_14.v_pos);
            public static Portal P_3_1 = new Portal(vertices.V_1.v_pos, vertices.V_14.v_pos);
        }

        public static class sectors {
            public static Sector S_0 = new Sector(new Wall[] {
                    walls.W_0,
                    walls.W_1,
                    walls.W_2,
                    walls.W_3,
                    walls.W_4
            }, new Vertex[] {
                    vertices.V_2,
                    vertices.V_4,
                    vertices.V_5,
                    vertices.V_6
            }, 0, 2000, Textures.LEVEL0_FLOOR_1, Textures.LEVEL0_CEILING_1);

            public static Sector S_1 = new Sector(new Wall[] {
                    walls.W_0,
                    walls.W_4,
                    walls.W_5,
                    walls.W_6,
                    walls.W_7,
                    walls.W_8,
                    walls.W_9
            }, new Vertex[] {
                    vertices.V_2,
                    vertices.V_6,
                    vertices.V_8,
                    vertices.V_12
            }, 0, 2000, Textures.LEVEL0_FLOOR_1, Textures.LEVEL0_CEILING_1);

            public static Sector S_2 = new Sector(new Wall[] {
                    walls.W_6,
                    walls.W_7,
                    walls.W_10,
                    walls.W_11,
                    walls.W_12
            }, new Vertex[] {
                    vertices.V_8,
                    vertices.V_10,
                    vertices.V_11,
                    vertices.V_12
            }, 0, 2000, Textures.LEVEL0_FLOOR_1, Textures.LEVEL0_CEILING_1);

            public static Sector S_3 = new Sector(new Wall[] {
                    walls.W_13,
                    walls.W_14
            }, new Vertex[] {
                    vertices.V_0,
                    vertices.V_1,
                    vertices.V_14,
                    vertices.V_15
            }, 0, 2000, Textures.LEVEL0_FLOOR_1, Textures.LEVEL0_CEILING_1);
        }
    }

    public static Level level0 = new Level(Level0.fog, "Level 0", Level0.background_songs, new Sector[] {
            Level0.sectors.S_0,
            Level0.sectors.S_1,
            Level0.sectors.S_2,
            Level0.sectors.S_3
    }, new Wall[] {
            Level0.walls.W_0,
            Level0.walls.W_1,
            Level0.walls.W_2,
            Level0.walls.W_3,
            Level0.walls.W_4,
            Level0.walls.W_5,
            Level0.walls.W_6,
            Level0.walls.W_7,
            Level0.walls.W_8,
            Level0.walls.W_9,
            Level0.walls.W_10,
            Level0.walls.W_11,
            Level0.walls.W_12,
            Level0.walls.W_13,
            Level0.walls.W_14
    }, new EntityBase[] {

    });

    public static void updatePortals() {
        Level0.portals.P_0_1.setSector(Level0.sectors.S_1);
        Level0.portals.P_1_0.setSector(Level0.sectors.S_0);
        Level0.portals.P_1_2.setSector(Level0.sectors.S_2);
        Level0.portals.P_1_3.setSector(Level0.sectors.S_3);
        Level0.portals.P_2_1.setSector(Level0.sectors.S_1);
        Level0.portals.P_3_1.setSector(Level0.sectors.S_1);

        Level0.portals.P_0_1.p_negator = Level0.portals.P_1_0;
        Level0.portals.P_1_0.p_negator = Level0.portals.P_0_1;
        Level0.portals.P_1_2.p_negator = Level0.portals.P_2_1;
        Level0.portals.P_1_3.p_negator = Level0.portals.P_3_1;
        Level0.portals.P_2_1.p_negator = Level0.portals.P_1_2;
        Level0.portals.P_3_1.p_negator = Level0.portals.P_1_3;

        Level0.sectors.S_0.setPortals(new Portal[] {
                Level0.portals.P_0_1
        });
        Level0.sectors.S_1.setPortals(new Portal[] {
                Level0.portals.P_1_0,
                Level0.portals.P_1_2,
                Level0.portals.P_1_3
        });
        Level0.sectors.S_2.setPortals(new Portal[] {
                Level0.portals.P_2_1
        });
        Level0.sectors.S_3.setPortals(new Portal[] {
                Level0.portals.P_3_1
        });
    }

    public static Level[] levels = {
            levelHome,
            level0
    };
}
