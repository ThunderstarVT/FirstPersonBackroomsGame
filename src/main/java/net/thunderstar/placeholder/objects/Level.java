package net.thunderstar.placeholder.objects;

import net.thunderstar.placeholder.entities.EntityBase;
import net.thunderstar.placeholder.sound.util.Sound;
import net.thunderstar.placeholder.util.bsp.BSPTree;

public class Level {
    public float l_fog;
    public String l_levelName;
    public Sound[] l_backgroundSongs;

    public Sector[] l_sectors;
    public Wall[] l_walls;

    public EntityBase[] l_entities;

    public BSPTree l_tree = new BSPTree();

    public Level(float fog, String level_name, Sound[] background_songs, Sector[] sectors, Wall[] walls, EntityBase[] entities) {
        l_fog = fog;
        l_levelName = level_name;
        l_backgroundSongs = background_songs;
        l_sectors = sectors;
        l_walls = walls;
        l_entities = entities;
    }
}
