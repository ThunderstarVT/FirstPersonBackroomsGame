package net.thunderstar.placeholder.objects;

import net.thunderstar.placeholder.sound.util.Sound;

public class Level {
    public float l_fog;
    public String l_levelName;
    public Sound l_backgroundMusic;

    public Vertex[] l_vertices;
    public Wall[] l_walls;
    public Sector[] l_sectors;

    public Level(float fog, String level_name, Sound background_music, Vertex[] vertices, Wall[] walls, Sector[] sectors) {
        l_fog = fog;
        l_levelName = level_name;
        l_backgroundMusic = background_music;
        l_vertices = vertices;
        l_walls = walls;
        l_sectors = sectors;
    }
}
