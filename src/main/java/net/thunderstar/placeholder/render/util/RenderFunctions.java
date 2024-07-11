package net.thunderstar.placeholder.render.util;

import net.thunderstar.placeholder.entities.Player;
import net.thunderstar.placeholder.objects.Level;
import net.thunderstar.placeholder.objects.Portal;
import net.thunderstar.placeholder.objects.Sector;
import net.thunderstar.placeholder.objects.Wall;
import net.thunderstar.placeholder.util.Levels;
import net.thunderstar.placeholder.util.Reference;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RenderFunctions {
    public static class Game {
        // the actual rendering code
        public static void renderCode_thread_alt(Sector sector, boolean[][] written, float[][] dist) {
            float player_posX = Player.variables.posX;
            float player_posY = Player.variables.posY;
            float player_posZ = Player.variables.posZ;
            float player_rotation = Player.variables.rotation;

            for (Wall wall : sector.s_walls) {
                //TODO: fix this piece of crap
                if (wall.w_texture != null) {
                    // render the walls
                    boolean start_behindnt = RenderHelpFunctions.checkBehind(new Point((int) player_posX, (int) player_posY), wall.w_start, player_rotation);
                    boolean end_behindnt = RenderHelpFunctions.checkBehind(new Point((int) player_posX, (int) player_posY), wall.w_end, player_rotation);
                    if (start_behindnt && end_behindnt) {
                        float X1 = wall.drawStart((int) player_posX, (int) player_posY, player_rotation, Player.constants.camera_fov);
                        float X2 = wall.drawEnd((int) player_posX, (int) player_posY, player_rotation, Player.constants.camera_fov);

                        int X_left = (int) (Math.floor(Math.min(X1, X2)) + 160);
                        int X_right = (int) (Math.ceil(Math.max(X1, X2)) + 160);

                        float left_dist = wall.intersectDist((int) player_posX, (int) player_posY, player_rotation, X_left-160, Player.constants.camera_fov, Player.constants.r_fov)
                                * RenderHelpFunctions.Q_rsqrt(Player.constants.camera_fov*Player.constants.camera_fov + (X_left-160)*(X_left-160));
                        float right_dist = wall.intersectDist((int) player_posX, (int) player_posY, player_rotation, X_right-160, Player.constants.camera_fov, Player.constants.r_fov)
                                * RenderHelpFunctions.Q_rsqrt(Player.constants.camera_fov*Player.constants.camera_fov + (X_right-160)*(X_right-160));

                        float left_top = wall.drawTopRaw(sector.s_bottom, sector.s_height, (int) player_posZ, left_dist, Player.constants.camera_fov);
                        float right_top = wall.drawTopRaw(sector.s_bottom, sector.s_height, (int) player_posZ, right_dist, Player.constants.camera_fov);

                        float left_bottom = wall.drawBottomRaw(sector.s_bottom, (int) player_posZ, left_dist, Player.constants.camera_fov);
                        float right_bottom = wall.drawBottomRaw(sector.s_bottom, (int) player_posZ, right_dist, Player.constants.camera_fov);

                        float r_length = (float) 1/(X_right - X_left);

                        for (int X = Math.max(0, X_left); X < Math.min(320, X_right); X++) {
                            float distance1 = RenderHelpFunctions.lerp(left_dist, right_dist, (X-X_left)*r_length);
                            float distance2 = (float) (distance1 * Math.sqrt(Player.constants.camera_fov*Player.constants.camera_fov + (X-160)*(X-160)));
                            for (int Y = (int) Math.floor(Math.max(0, RenderHelpFunctions.lerp(left_top, right_top, (X-X_left)*r_length)));
                                 Y < (int) Math.ceil(Math.min(240, RenderHelpFunctions.lerp(left_bottom, right_bottom, (X-X_left)*r_length)));
                                 Y++) {
                                float distance3 = (float) (distance2 * Math.sqrt((Y-120)*(Y-120) + (Player.constants.camera_fov)*(Player.constants.camera_fov))*Player.constants.r_fov);

                                if (written[X][Y]) {
                                    if (distance2 < dist[X][Y]) {
                                        Color c = RenderHelpFunctions.getColor(wall, sector.s_bottom, sector.s_height,
                                                (int) player_posX, (int) player_posY, (int) (player_posZ + Player.constants.camera_offset),
                                                player_rotation, distance2, X - 160, Y - 120, Player.constants.camera_fov, Player.constants.r_fov);
                                        //Color c = new Color(0x4CCCFF);

                                        if (c != null) {
                                            c = RenderHelpFunctions.applyFog(c, distance3, Levels.levelHome.l_fog);

                                            Reference.gameColors[X][Y] = c;

                                            dist[X][Y] = distance3;
                                            written[X][Y] = true;
                                        }
                                    }
                                } else {
                                    Color c = RenderHelpFunctions.getColor(wall, sector.s_bottom, sector.s_height,
                                            (int) player_posX, (int) player_posY, (int) (player_posZ + Player.constants.camera_offset),
                                            player_rotation, distance1, X - 160, Y - 120, Player.constants.camera_fov, Player.constants.r_fov);
                                    //Color c = new Color(0x4CCCFF);

                                    if (c != null) {
                                        c = RenderHelpFunctions.applyFog(c, distance2, Levels.levelHome.l_fog);

                                        Reference.gameColors[X][Y] = c;

                                        dist[X][Y] = distance2;
                                        written[X][Y] = true;
                                    }
                                }
                            }
                        }
                    } else if (start_behindnt) {

                    } else if (end_behindnt) {

                    }
                }
            }

            // render the floors and ceilings
            if (sector.s_floorTexture != null) {
                for (int Y = 120; Y < 240; Y++) {
                    float distance = (float) ((player_posZ + Player.constants.camera_offset - sector.s_bottom)*Math.sqrt((Y-120)*(Y-120) + (Player.constants.camera_fov)*(Player.constants.camera_fov))/(Y-120));
                    for (int X = 0; X < 320; X++) {
                        float distance2 = (float) (distance * Math.sqrt((X-160)*(X-160) + (Player.constants.camera_fov)*(Player.constants.camera_fov))*Player.constants.r_fov);
                        Point point = sector.intersectPosFloor(
                                player_posX, player_posY, player_posZ + Player.constants.camera_offset,
                                X-160, Y-120, player_rotation, Player.constants.camera_fov);
                        if (written[X][Y]) {
                            if (distance2 < dist[X][Y]) {
                                if (RenderHelpFunctions.checkInPolygonBSP(sector.s_vertices, point)) {
                                    int modX = (int) (point.x * sector.s_floorTexture.t_scale);
                                    int modY = (int) (point.y * sector.s_floorTexture.t_scale);

                                    modX = modX & 0xFF;
                                    modY = modY & 0xFF;

                                    Color c = sector.s_floorTexture.t_image[modX][modY];

                                    c = RenderHelpFunctions.applyFog(c, distance2, Player.variables.current_level.l_fog);

                                    Reference.gameColors[X][Y] = c;

                                    dist[X][Y] = distance2;
                                    written[X][Y] = true;
                                }
                            }
                        } else {
                            if (RenderHelpFunctions.checkInPolygonBSP(sector.s_vertices, point)) {
                                int modX = (int) (point.x * sector.s_floorTexture.t_scale);
                                int modY = (int) (point.y * sector.s_floorTexture.t_scale);

                                modX = modX & 0xFF;
                                modY = modY & 0xFF;

                                Color c = sector.s_floorTexture.t_image[modX][modY];

                                c = RenderHelpFunctions.applyFog(c, distance2, Player.variables.current_level.l_fog);

                                Reference.gameColors[X][Y] = c;

                                dist[X][Y] = distance2;
                                written[X][Y] = true;
                            }
                        }
                    }
                }
            }
            if (sector.s_ceilingTexture != null) {
                for (int Y = 0; Y < 120; Y++) {
                    float distance = (float) ((player_posZ + Player.constants.camera_offset - (sector.s_bottom + sector.s_height))*Math.sqrt((Y-120)*(Y-120) + (Player.constants.camera_fov)*(Player.constants.camera_fov))/(Y-120));
                    for (int X = 0; X < 320; X++) {
                        float distance2 = (float) (distance * Math.sqrt((X-160)*(X-160) + (Player.constants.camera_fov)*(Player.constants.camera_fov))*Player.constants.r_fov);
                        //TODO: get point coordinates
                        Point point = sector.intersectPosCeiling(
                                player_posX, player_posY, player_posZ + Player.constants.camera_offset,
                                X-160, 120-Y, player_rotation, Player.constants.camera_fov);
                        if (written[X][Y]) {
                            if (distance2 < dist[X][Y]) {
                                if (RenderHelpFunctions.checkInPolygonBSP(sector.s_vertices, point)) {
                                    int modX = (int) (point.x * sector.s_ceilingTexture.t_scale);
                                    int modY = (int) (point.y * sector.s_ceilingTexture.t_scale);

                                    modX = modX & 0xFF;
                                    modY = modY & 0xFF;

                                    Color c = sector.s_ceilingTexture.t_image[modX][modY];

                                    c = RenderHelpFunctions.applyFog(c, distance2, Player.variables.current_level.l_fog);

                                    Reference.gameColors[X][Y] = c;

                                    dist[X][Y] = distance2;
                                    written[X][Y] = true;
                                }
                            }
                        } else {
                            if (RenderHelpFunctions.checkInPolygonBSP(sector.s_vertices, point)) {
                                int modX = (int) (point.x * sector.s_ceilingTexture.t_scale);
                                int modY = (int) (point.y * sector.s_ceilingTexture.t_scale);

                                modX = modX & 0xFF;
                                modY = modY & 0xFF;

                                Color c = sector.s_ceilingTexture.t_image[modX][modY];

                                c = RenderHelpFunctions.applyFog(c, distance2, Player.variables.current_level.l_fog);

                                Reference.gameColors[X][Y] = c;

                                dist[X][Y] = distance2;
                                written[X][Y] = true;
                            }
                        }
                    }
                }
            }
        }

        //TODO: optimize this or fix the other fucker
        public static void renderCode_thread(Sector sector, boolean[][] written, float[][] dist) {
            if (sector.checkVisible()) {
                float player_posX = Player.variables.posX;
                float player_posY = Player.variables.posY;
                float player_posZ = Player.variables.posZ;
                float player_rotation = Player.variables.rotation;

                Point player_posXY = new Point((int) player_posX, (int) player_posY);

                for (Wall wall : sector.s_walls) {
                    if (wall.w_texture != null) {
                        // render the walls
                        if (RenderHelpFunctions.checkBehind(player_posXY, wall.w_start, (float) Math.toRadians(player_rotation)) || RenderHelpFunctions.checkBehind(player_posXY, wall.w_end, (float) Math.toRadians(player_rotation))) {
                            for (int X = 0; X < 320; X++) {
                                if (wall.intersectBool(
                                        Math.round(player_posX), Math.round(player_posY),
                                        player_rotation, X - 160, Player.constants.camera_fov, Player.constants.r_fov)) {
                                    float distance = wall.intersectDist(
                                            Math.round(player_posX), Math.round(player_posY),
                                            player_rotation, X - 160, Player.constants.camera_fov, Player.constants.r_fov);
                                    int top = wall.drawTop(
                                            sector.s_bottom, sector.s_height,
                                            Math.round(player_posZ + Player.constants.camera_offset),
                                            distance, X - 160, Player.constants.camera_fov);
                                    int bottom = wall.drawBottom(
                                            sector.s_bottom,
                                            Math.round(player_posZ + Player.constants.camera_offset),
                                            distance, X - 160, Player.constants.camera_fov);

                                    for (int Y = Math.max(0, top); Y < Math.min(240, bottom); Y++) {
                                        float distance2 = (float) (distance * Math.sqrt((Y - 120) * (Y - 120) + (Player.constants.camera_fov) * (Player.constants.camera_fov)) * Player.constants.r_fov);
                                        if (written[X][Y]) {
                                            if (distance2 < dist[X][Y]) {
                                                Color c = RenderHelpFunctions.getColor(wall, sector.s_bottom, sector.s_height,
                                                        (int) player_posX, (int) player_posY, (int) (player_posZ + Player.constants.camera_offset),
                                                        player_rotation, distance, X - 160, Y - 120, Player.constants.camera_fov, Player.constants.r_fov);
                                                //Color c = new Color(0x4CCCFF);

                                                if (c != null) {
                                                    c = RenderHelpFunctions.applyFog(c, distance2, Levels.levelHome.l_fog);

                                                    Reference.gameColors[X][Y] = c;

                                                    dist[X][Y] = distance2;
                                                    written[X][Y] = true;
                                                }
                                            }
                                        } else {
                                            Color c = RenderHelpFunctions.getColor(wall, sector.s_bottom, sector.s_height,
                                                    (int) player_posX, (int) player_posY, (int) (player_posZ + Player.constants.camera_offset),
                                                    player_rotation, distance, X - 160, Y - 120, Player.constants.camera_fov, Player.constants.r_fov);
                                            //Color c = new Color(0x4CCCFF);

                                            if (c != null) {
                                                c = RenderHelpFunctions.applyFog(c, distance2, Levels.levelHome.l_fog);

                                                Reference.gameColors[X][Y] = c;

                                                dist[X][Y] = distance2;
                                                written[X][Y] = true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // render the floors and ceilings
                if (sector.s_floorTexture != null) {
                    for (int Y = 120; Y < 240; Y++) {
                        float distance = (float) ((player_posZ + Player.constants.camera_offset - sector.s_bottom) * Math.sqrt((Y - 120) * (Y - 120) + (Player.constants.camera_fov) * (Player.constants.camera_fov)) / (Y - 120));
                        for (int X = 0; X < 320; X++) {
                            float distance2 = (float) (distance * Math.sqrt((X - 160) * (X - 160) + (Player.constants.camera_fov) * (Player.constants.camera_fov)) * Player.constants.r_fov);
                            Point point = sector.intersectPosFloor(
                                    player_posX, player_posY, player_posZ + Player.constants.camera_offset,
                                    X - 160, Y - 120, player_rotation, Player.constants.camera_fov);
                            if (written[X][Y]) {
                                if (distance2 < dist[X][Y]) {
                                    if (RenderHelpFunctions.checkInPolygonBSP(sector.s_vertices, point)) {
                                        int modX = (int) (point.x * sector.s_floorTexture.t_scale);
                                        int modY = (int) (point.y * sector.s_floorTexture.t_scale);

                                        modX = modX & 0xFF;
                                        modY = modY & 0xFF;

                                        Color c = sector.s_floorTexture.t_image[modX][modY];

                                        c = RenderHelpFunctions.applyFog(c, distance2, Player.variables.current_level.l_fog);

                                        Reference.gameColors[X][Y] = c;

                                        dist[X][Y] = distance2;
                                        written[X][Y] = true;
                                    }
                                }
                            } else {
                                if (RenderHelpFunctions.checkInPolygonBSP(sector.s_vertices, point)) {
                                    int modX = (int) (point.x * sector.s_floorTexture.t_scale);
                                    int modY = (int) (point.y * sector.s_floorTexture.t_scale);

                                    modX = modX & 0xFF;
                                    modY = modY & 0xFF;

                                    Color c = sector.s_floorTexture.t_image[modX][modY];

                                    c = RenderHelpFunctions.applyFog(c, distance2, Player.variables.current_level.l_fog);

                                    Reference.gameColors[X][Y] = c;

                                    dist[X][Y] = distance2;
                                    written[X][Y] = true;
                                }
                            }
                        }
                    }
                }
                if (sector.s_ceilingTexture != null) {
                    for (int Y = 0; Y < 120; Y++) {
                        float distance = (float) ((player_posZ + Player.constants.camera_offset - (sector.s_bottom + sector.s_height)) * Math.sqrt((Y - 120) * (Y - 120) + (Player.constants.camera_fov) * (Player.constants.camera_fov)) / (Y - 120));
                        for (int X = 0; X < 320; X++) {
                            float distance2 = (float) (distance * Math.sqrt((X - 160) * (X - 160) + (Player.constants.camera_fov) * (Player.constants.camera_fov)) * Player.constants.r_fov);
                            //TODO: get point coordinates
                            Point point = sector.intersectPosCeiling(
                                    player_posX, player_posY, player_posZ + Player.constants.camera_offset,
                                    X - 160, 120 - Y, player_rotation, Player.constants.camera_fov);
                            if (written[X][Y]) {
                                if (distance2 < dist[X][Y]) {
                                    if (RenderHelpFunctions.checkInPolygonBSP(sector.s_vertices, point)) {
                                        int modX = (int) (point.x * sector.s_ceilingTexture.t_scale);
                                        int modY = (int) (point.y * sector.s_ceilingTexture.t_scale);

                                        modX = modX & 0xFF;
                                        modY = modY & 0xFF;

                                        Color c = sector.s_ceilingTexture.t_image[modX][modY];

                                        c = RenderHelpFunctions.applyFog(c, distance2, Player.variables.current_level.l_fog);

                                        Reference.gameColors[X][Y] = c;

                                        dist[X][Y] = distance2;
                                        written[X][Y] = true;
                                    }
                                }
                            } else {
                                if (RenderHelpFunctions.checkInPolygonBSP(sector.s_vertices, point)) {
                                    int modX = (int) (point.x * sector.s_ceilingTexture.t_scale);
                                    int modY = (int) (point.y * sector.s_ceilingTexture.t_scale);

                                    modX = modX & 0xFF;
                                    modY = modY & 0xFF;

                                    Color c = sector.s_ceilingTexture.t_image[modX][modY];

                                    c = RenderHelpFunctions.applyFog(c, distance2, Player.variables.current_level.l_fog);

                                    Reference.gameColors[X][Y] = c;

                                    dist[X][Y] = distance2;
                                    written[X][Y] = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        static List<Thread> sectorThreads = new ArrayList<Thread>();
        public static void render() {

            Level current_level = Player.variables.current_level;

            boolean[][] written = new boolean[320][240];
            float[][] dist = new float[320][240];
            for (int Y = 0; Y < 240; Y++) {
                for (int X = 0; X < 320; X++) {
                    written[X][Y] = false;
                }
            }

            // one thread per sector, gotta optimize it a bit more because it lags everything else
            sectorThreads.clear();

            for (Sector sector : current_level.l_sectors) {
                Thread thread = new Thread(() -> {
                    renderCode_thread(sector, written, dist);
                });
                sectorThreads.add(thread);
            }

            for (Thread thread : sectorThreads) {
                thread.start();
            }

            for (Thread thread : sectorThreads) {
                while (thread.isAlive()) {}
            }
        }

        // some tests
        public static void portalRender() {
            List<Thread> threads = new ArrayList<Thread>();
            Sector current_sector = Player.functions.getCurrentSector();

            Thread thread0 = new Thread(() -> {
                for (int Y = 0; Y < 120; Y++) {
                    for (int X = 0; X < 320; X++) {
                        List<Sector> checked_sectors = new ArrayList<Sector>();
                        checked_sectors.add(current_sector);

                        Reference.gameColors[X][2*Y] = PortalRenderFunctions.getColor(current_sector, X, 2*Y, checked_sectors);
                    }
                }
            });
            Thread thread1 = new Thread(() -> {
                for (int Y = 0; Y < 120; Y++) {
                    for (int X = 0; X < 320; X++) {
                        List<Sector> checked_sectors = new ArrayList<Sector>();
                        checked_sectors.add(current_sector);

                        Reference.gameColors[X][2*Y+1] = PortalRenderFunctions.getColor(current_sector, X, 2*Y+1, checked_sectors);
                    }
                }
            });

            threads.add(thread0);
            threads.add(thread1);

            for (Thread thread : threads) {
                thread.start();
            }
        }

        public static void imageColor(Color[][] image) {
            for (int Y = 0; Y < 240; Y++) {
                for (int X = 0; X < 320; X++) {
                    Color c = new Color(0xFF8000);

                    if (image[X % 256][Y % 256] != null) {
                        c = image[X % 256][Y % 256];
                    }

                    Reference.gameColors[X][Y] = c;
                }
            }
        }
    }

    public static class Inv {
        // the actual rendering code
        public static void render() {

        }

        // some tests
        public static void color(Color color) {
            for (int Y = 0; Y < 240; Y++) {
                for (int X = 0; X < 320; X++) {
                    Color c = new Color(0xFF8000);

                    if (color != null) {
                        c = color;
                    }

                    Reference.invColors[X][Y] = c;
                }
            }
        }

        public static void imageColor(Color[][] image) {
            for (int Y = 0; Y < 240; Y++) {
                for (int X = 0; X < 320; X++) {
                    Color c = new Color(0xFF8000);

                    if (image[X % 256][Y % 256] != null) {
                        c = image[X % 256][Y % 256];
                    }

                    Reference.invColors[X][Y] = c;
                }
            }
        }
    }
}
