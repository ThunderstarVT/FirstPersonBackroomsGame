package net.thunderstar.placeholder.util;

import net.thunderstar.placeholder.objects.Sector;
import net.thunderstar.placeholder.objects.Wall;
import net.thunderstar.placeholder.render.util.RenderHelpFunctions;

public class Normal {
    public static class Point3D {
        public int x;
        public int y;
        public int z;

        public Point3D(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public float x;
    public float y;
    public float z;

    public Point3D origin = new Point3D(0, 0, 0);

    public Normal() {

    }

    public Normal getNormalFromWall(Wall wall) {
        this.origin = new Point3D(wall.w_start.x, wall.w_start.y, wall.w_bottom);

        int deltaX = wall.w_end.x - wall.w_start.x;
        int deltaY = wall.w_end.y - wall.w_start.y;

        this.x = -deltaY;
        this.y = deltaX;
        this.z = 0;

        float rsqrt = RenderHelpFunctions.Q_rsqrt((this.x)*(this.x) + (this.y)*(this.y) + (this.z)*(this.z));

        this.x *= rsqrt;
        this.y *= rsqrt;
        this.z *= rsqrt;

        return this;
    }

    public Normal getNormalFromFloor(Sector sector) {
        this.origin = new Point3D(sector.s_vertices[0].v_pos.x, sector.s_vertices[0].v_pos.y, sector.s_bottom);

        this.x = 0;
        this.y = 0;
        this.z = 1;

        return this;
    }

    public Normal getNormalFromCeiling(Sector sector) {
        this.origin = new Point3D(sector.s_vertices[0].v_pos.x, sector.s_vertices[0].v_pos.y, sector.s_bottom + sector.s_height);

        this.x = 0;
        this.y = 0;
        this.z = -1;

        return this;
    }


    public boolean checkInFront(int x, int y, int z) {
        return false;
    }
}
