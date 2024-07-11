package net.thunderstar.placeholder.render.util;

import net.thunderstar.placeholder.entities.EntityBase;
import net.thunderstar.placeholder.entities.Player;
import net.thunderstar.placeholder.objects.Portal;
import net.thunderstar.placeholder.objects.Sector;
import net.thunderstar.placeholder.objects.Wall;

import java.awt.*;
import java.util.List;

public class PortalRenderFunctions {
    public static Color getColor(Sector sector, int X, int Y, List<Sector> checked_sectors) {
        Color color;

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

        double sqrtY = Math.sqrt((Y - 120) * (Y - 120) + (player_camera_fov) * (player_camera_fov));
        double sqrtX = Math.sqrt((X - 160) * (X - 160) + (player_camera_fov) * (player_camera_fov));

        for (Wall wall : sector.s_walls) {
            // wall
            if (wall.w_texture != null) {
                if (wall.intersectBool(player_posX_rounded, player_posY_rounded, player_rotation, X - 160, player_camera_fov, player_r_fov)) {
                    float dist = wall.intersectDist(player_posX_rounded, player_posY_rounded, player_rotation, X - 160, player_camera_fov, player_r_fov);
                    if (Y >= wall.drawTop(sector.s_bottom, sector.s_height, player_posZ_rounded_with_offset, dist, X - 160, player_camera_fov)
                            && Y <= wall.drawBottom(sector.s_bottom, player_posZ_rounded_with_offset, dist, X - 160, player_camera_fov)) {
                        float dist2 = (float) (dist * sqrtY * player_r_fov);
                        color = RenderHelpFunctions.getColor(wall, sector.s_bottom, sector.s_height,
                                player_posX_rounded, player_posY_rounded, player_posZ_rounded_with_offset, player_rotation,
                                dist, X - 160, Y - 120, player_camera_fov, player_r_fov);

                        color = RenderHelpFunctions.applyFog(color, dist2, Player.variables.current_level.l_fog);

                        return color;
                    }
                }
            }
        }

        if (Y > 120) {
            // floor
            Point point = sector.intersectPosFloor(
                    player_posX, player_posY, player_posZ + player_camera_offset,
                    X - 160, Y - 120, player_rotation, player_camera_fov);
            if (RenderHelpFunctions.checkInPolygonBSP(sector.s_vertices, point)) {
                float dist = (float) ((player_posZ + player_camera_offset - sector.s_bottom) * sqrtY / (Y - 120));
                float dist2 = (float) (dist * sqrtX * player_r_fov);

                int modX = Math.round(point.x * sector.s_floorTexture.t_scale);
                int modY = Math.round(point.y * sector.s_floorTexture.t_scale);

                modX = modX & 0xFF;
                modY = modY & 0xFF;

                color = sector.s_floorTexture.t_image[modX][modY];

                color = RenderHelpFunctions.applyFog(color, dist2, Player.variables.current_level.l_fog);

                return color;
            }
        } else if (Y < 120) {
            // ceiling
            Point point = sector.intersectPosCeiling(
                    player_posX, player_posY, player_posZ + player_camera_offset,
                    X - 160, 120 - Y, player_rotation, player_camera_fov);
            if (RenderHelpFunctions.checkInPolygonBSP(sector.s_vertices, point)) {
                float dist = (float) ((player_posZ + player_camera_offset - (sector.s_bottom + sector.s_height)) * sqrtY / (Y - 120));
                float dist2 = (float) (dist * sqrtX * player_r_fov);

                int modX = Math.round(point.x * sector.s_ceilingTexture.t_scale);
                int modY = Math.round(point.y * sector.s_ceilingTexture.t_scale);

                modX = modX & 0xFF;
                modY = modY & 0xFF;

                color = sector.s_ceilingTexture.t_image[modX][modY];

                color = RenderHelpFunctions.applyFog(color, dist2, Player.variables.current_level.l_fog);

                return color;
            }
        }

        for (Portal portal : sector.s_portals) {
            // portal
            if (!checked_sectors.contains(portal.p_sector)) {
                if (portal.intersectBool(player_posX_rounded, player_posY_rounded, player_rotation, X - 160, player_camera_fov, player_r_fov)) {
                    float dist = portal.intersectDist(player_posX_rounded, player_posY_rounded, player_rotation, X - 160, player_camera_fov, player_r_fov);
                    if (Y >= portal.drawTop(sector.s_bottom, sector.s_height, player_posZ_rounded_with_offset, dist, X - 160, player_camera_fov)
                            && Y <= portal.drawBottom(sector.s_bottom, player_posZ_rounded_with_offset, dist, X - 160, player_camera_fov)) {

                        checked_sectors.add(portal.p_sector);

                        return getColor(portal.p_sector, X, Y, checked_sectors);
                    }
                }
            }
        }

        return new Color(0x000000);
    }

    public static Color renderEntity(EntityBase entity, Sector sector) {
        if (RenderHelpFunctions.checkInPolygonBSP(sector.s_vertices, new Point(Math.round(entity.e_posX), Math.round(entity.e_posY)))) {

        }
        return null;
    }
}
