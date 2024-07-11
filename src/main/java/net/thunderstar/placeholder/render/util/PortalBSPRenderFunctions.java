package net.thunderstar.placeholder.render.util;

import net.thunderstar.placeholder.entities.Player;
import net.thunderstar.placeholder.objects.Level;
import net.thunderstar.placeholder.objects.Sector;
import net.thunderstar.placeholder.objects.Wall;
import net.thunderstar.placeholder.util.bsp.BSPTree;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PortalBSPRenderFunctions {
    public static BSPTree generateBSPTree(Level level) {
        //TODO: idk, how do these things work?

        // first split along all floors and ceilings
        // then split along each wall

        System.out.println("Generated BSP Tree for level: \"" + level.l_levelName + "\"");
        return null;
    }

    public static Color[][] renderColor() {
        boolean[][] written = new boolean[320][240];

        for (int i = 0; i < 320; i++) {
            for (int j = 0; j < 240; j++) {
                written[i][j] = false;
            }
        }
        List<Wall> visible_walls = new ArrayList<Wall>();

        for (Wall wall : Player.variables.current_level.l_walls) {
            visible_walls.add(wall);
        }

        float player_posX = Player.variables.posX;
        float player_posY = Player.variables.posY;
        float player_posZ = Player.variables.posZ;

        float player_camera_offset = Player.constants.camera_offset;
        float player_camera_fov = Player.constants.camera_fov;
        float player_r_fov = Player.constants.r_fov;

        int player_posX_rounded = Math.round(player_posX);
        int player_posY_rounded = Math.round(player_posY);
        int player_posZ_rounded_with_offset = Math.round(player_posZ + player_camera_offset);

        float player_rotation = Player.variables.rotation;

        //TODO: figure out BSP and combine it with the portal rendering and UV-maps

        return null;
    }

    public static Color getColor(Point uv, Texture texture, float dist) {
        int modX = Math.round(uv.x * texture.t_scale);
        int modY = Math.round(uv.y * texture.t_scale);

        modX = modX & 0xFF;
        modY = modY & 0xFF;

        return RenderHelpFunctions.applyFog(texture.t_image[modX][modY], dist, Player.variables.current_level.l_fog);
    }

    public static Point getUV_Wall() {
        return null;
    }
}
