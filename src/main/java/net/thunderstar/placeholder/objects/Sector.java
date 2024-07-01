package net.thunderstar.placeholder.objects;

import net.thunderstar.placeholder.entities.Player;

import java.awt.*;

public class Sector {
    public Wall[] s_walls;
    public Vertex[] s_vertices;
    public int s_bottom;
    public int s_height;
    public Color[][] s_floorTexture;
    public Color[][] s_ceilingTexture;

    public Sector(Wall[] walls, Vertex[] vertices, int bottom, int height, Color[][] floorTexture, Color[][] ceilingTexture) {
        s_walls = walls;
        s_vertices = vertices;
        s_bottom = bottom;
        s_height = height;
        s_floorTexture = floorTexture;
        s_ceilingTexture = ceilingTexture;
    }

    public Point intersectPosFloor(float originX, float originY, float originZ, float offsetX, float offsetY, float rotation, float fov) {
        float rad = (float) (rotation/180 * Math.PI + Math.atan(offsetX/fov));
        float sin = (float) Math.sin(rad);
        float cos = (float) Math.cos(rad);

        float dist = fov * (originZ - s_bottom) / offsetY;
        float dist2 = (float) (dist * Math.sqrt(offsetX*offsetX + (Player.constants.camera_fov)*(Player.constants.camera_fov))/Player.constants.camera_fov);

        float x = cos * dist2 - originX;
        float y = originY - sin * dist2;

        return new Point(Math.round(-x), Math.round(y));
    }

    public Point intersectPosCeiling(float originX, float originY, float originZ, float offsetX, float offsetY, float rotation, float fov) {
        float rad = (float) (rotation/180 * Math.PI + Math.atan(offsetX/fov));
        float sin = (float) Math.sin(rad);
        float cos = (float) Math.cos(rad);

        float dist = fov * (originZ - (s_bottom + s_height)) / offsetY;
        float dist2 = (float) (dist * Math.sqrt(offsetX*offsetX + (Player.constants.camera_fov)*(Player.constants.camera_fov))/Player.constants.camera_fov);

        float x = cos * dist2 + originX;
        float y = originY + sin * dist2;

        return new Point(Math.round(x), Math.round(y));
    }
}
