package net.thunderstar.placeholder.objects;

import net.thunderstar.placeholder.entities.Player;
import net.thunderstar.placeholder.render.util.RenderHelpFunctions;
import net.thunderstar.placeholder.render.util.Texture;
import net.thunderstar.placeholder.util.FastMath;

import java.awt.*;

public class Sector {
    public Wall[] s_walls;
    public Portal[] s_portals;
    public Vertex[] s_vertices;
    public int s_bottom;
    public int s_height;
    public Texture s_floorTexture;
    public Texture s_ceilingTexture;

    public Sector(Wall[] walls, Vertex[] vertices, int bottom, int height, Texture floorTexture, Texture ceilingTexture) {
        s_walls = walls;
        s_vertices = vertices;
        s_bottom = bottom;
        s_height = height;
        s_floorTexture = floorTexture;
        s_ceilingTexture = ceilingTexture;
    }

    public void setPortals(Portal[] portals) {
        s_portals = portals;
    }

    public boolean checkVisible() {
        for (Vertex vertex : s_vertices) {
            if (RenderHelpFunctions.checkBehind(new Point((int) Player.variables.posX, (int) Player.variables.posY), vertex.v_pos, (float) Math.toRadians(Player.variables.rotation))) {
                return true;
            }
        }
        return false;
    }

    public Point intersectPosFloor(float originX, float originY, float originZ, float offsetX, float offsetY, float rotation, float fov) {
        float rad = (float) (rotation/180 * Math.PI + FastMath.atan2(offsetX, fov));
        float sin = (float) Math.sin(rad);
        float cos = (float) Math.cos(rad);

        float dist = fov * (originZ - s_bottom) / offsetY;
        float dist2 = (float) (dist * Math.sqrt(offsetX*offsetX + (Player.constants.camera_fov)*(Player.constants.camera_fov))/Player.constants.camera_fov);

        float x = cos * dist2 - originX;
        float y = originY - sin * dist2;

        return new Point(Math.round(-x), Math.round(y));
    }

    public Point intersectPosCeiling(float originX, float originY, float originZ, float offsetX, float offsetY, float rotation, float fov) {
        float rad = (float) (rotation/180 * Math.PI + FastMath.atan2(offsetX, fov));
        float sin = (float) Math.sin(rad);
        float cos = (float) Math.cos(rad);

        float dist = fov * (originZ - (s_bottom + s_height)) / offsetY;
        float dist2 = (float) (dist * Math.sqrt(offsetX*offsetX + (Player.constants.camera_fov)*(Player.constants.camera_fov))/Player.constants.camera_fov);

        float x = cos * dist2 + originX;
        float y = originY + sin * dist2;

        return new Point(Math.round(x), Math.round(y));
    }
}
