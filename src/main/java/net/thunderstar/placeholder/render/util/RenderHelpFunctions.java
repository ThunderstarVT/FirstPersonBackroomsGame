package net.thunderstar.placeholder.render.util;

import net.thunderstar.placeholder.objects.Vertex;
import net.thunderstar.placeholder.objects.Wall;
import net.thunderstar.placeholder.util.FastMath;

import java.awt.*;
import java.nio.ByteBuffer;

public class RenderHelpFunctions {
    public static Color getColor(Wall wall, int bottom, int height, int originX, int originY, int originZ, float angle, float dist, float offsetX, float offsetY, float fov, float r_fov) {
        if (wall.w_texture == null) {
            return null;
        }
        float interX;
        float interY;

        Point start = wall.w_start;
        Point end = wall.w_end;

        float deltaX = start.x - end.x;
        float deltaY = start.y - end.y;

        float x;
        float y;

        if (deltaX != 0) {
            float radAngle = (float) Math.toRadians(angle);

            float a = (deltaY) / (deltaX);
            float b = (cross_product(start, end)) / (deltaX);
            float tan = (float) Math.tan(radAngle + FastMath.atan2(offsetX, fov));
            float c = tan;
            float d = originY - originX * tan;

            x = (d - b) / (a - c);
            y = a * x + b;
        } else {
            float radAngle = (float) Math.toRadians(angle-90);

            float a = 0;
            float b = (cross_product(end, start)) / (deltaY);
            float tan = (float) Math.tan(radAngle + FastMath.atan2(offsetX, fov));
            float c = -tan;
            float d = originX + originY * tan;

            y = (d - b) / (a - c);
            x = a * y + b;
        }

        interX = dist(wall.w_start, new Point((int) x, (int) y));

        interY = getInterY(dist, offsetX, -offsetY, fov, bottom, height, originZ);

        int modX = (int) (interX * wall.w_texture.t_scale);
        int modY = (int) (interY * wall.w_texture.t_scale);

        modX = modX & 0xFF;
        modY = modY & 0xFF;

        Color c = wall.w_texture.t_image[modX][modY];

        return c;
    }

    public static float getInterY(float intersectDist, float offsetX, float offsetY, float fov, int bottom, int height, int originZ) {
        float A = bottom + height - originZ;
        float B = intersectDist * offsetY * Q_rsqrt(fov*fov + offsetX*offsetX);

        return A - B;
    }

    public static Color applyFog(Color color, float dist, float fog) {
        float factor = (float) (1 - dist*fog*1e-3);
        if (factor > 1) {
            factor = 1.0f;
        }

        int cIn = color.getRGB();

        int red   = (cIn >> 16) & 0xFF;
        int green = (cIn >> 8)  & 0xFF;
        int blue  = (cIn)       & 0xFF;

        red = (int) (factor * red);
        green = (int) (factor * green);
        blue = (int) (factor * blue);

        Color cOut = new Color(Math.max(red, 0), Math.max(green, 0), Math.max(blue, 0));

        return cOut;
    }

    public static boolean checkBehind(Point origin, Point point, float angle) { // returns false if the point is behind
        float x1 = point.x - origin.x;
        float y1 = point.y - origin.y;

        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);

        float x2 = x1 * cos + y1 * sin;
        //float y2 = - x1 * sin + y1 * cos;   // don't need this stuff

        return x2 <= 0;
    }


    public static boolean checkInPolygon(Vertex[] vertices, Point point) {
        int n = vertices.length;
        if (n < 3) {
            return false;
        }

        boolean isLeft = cross_product(vertices[0].v_pos, vertices[1].v_pos) > 0;

        for (int i = 0; i < n; i++) {
            if ((cross_product(vertices[i].v_pos, vertices[(i+1) % n].v_pos, point) > 0) != isLeft) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkInPolygonBSP(Vertex[] vertices, Point point) {
        int n = vertices.length;
        if (n < 3) {
            return false;
        }

        boolean isLeft = cross_product(vertices[0].v_pos, vertices[1].v_pos, point) > 0;

        int low = 1, high = n - 1;
        while (high - low > 1) {
            int mid = (low + high) / 2;
            if (cross_product(vertices[0].v_pos, vertices[mid].v_pos, point) > 0 == isLeft) {
                low = mid;
            } else {
                high = mid;
            }
        }

        if (cross_product(vertices[low].v_pos, vertices[high].v_pos, point) > 0 != isLeft) {
            return false;
        }

        return cross_product(vertices[high].v_pos, vertices[(high+1)%n].v_pos, point) >= 0 == isLeft;
    }


    public static float Q_rsqrt(float number) {
        int i;
        float x2, y;
        final float threehalfs = 1.5F;

        ByteBuffer buffer1 = ByteBuffer.allocate(Integer.BYTES);
        ByteBuffer buffer2 = ByteBuffer.allocate(Integer.BYTES);

        x2 = number * 0.5F;
        y  = number;
        i  = buffer1.putFloat(y).getInt(0);   // evil floating point bit hack, but more cursed
        i  = 0x5f3759df - ( i >> 1 );               // what the fuck?
        y  = buffer2.putInt(i).getFloat(0);
        y  = y * ( threehalfs - ( x2 * y * y ) );   // 1st iteration
    //  y  = y * ( threehalfs - ( x2 * y * y ) );   // 2nd iteration, can be removed

        return y;
    }

    public static float cross_product(Point a, Point b) {
        return a.x * b.y - b.x * a.y;
    }
    public static float cross_product(Point o, Point a, Point b) {
        float Xa = a.x - o.x;
        float Ya = a.y - o.y;
        float Xb = b.x - o.x;
        float Yb = b.y - o.y;

        return (Xa) * (Yb) - (Xb) * (Ya);
    }

    public static float dist(Point a, Point b) {
        return (float) Math.sqrt((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y));
    }

    public static float sq_dist(Point a, Point b) {
        return (a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y);
    }

    public static float lerp(float a, float b, float t) {
        return (t) * a + (1-t) * b;
    }
}
