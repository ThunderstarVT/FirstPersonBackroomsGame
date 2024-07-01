package net.thunderstar.placeholder.render.util;

import net.thunderstar.placeholder.objects.Vertex;
import net.thunderstar.placeholder.objects.Wall;

import java.awt.*;
import java.nio.ByteBuffer;

public class RenderHelpFunctions {
    public static Color getColor(Wall wall, int bottom, int height, int originX, int originY, int originZ, float angle, float dist, float offsetX, float offsetY, float fov) {
        if (wall.w_texture == null) {
            return null;
        } else {
            float interX;
            float interY;

            //TODO: make code to get color

            if (wall.w_start.x - wall.w_end.x != 0) {
                float radAngle = (angle * (float)Math.PI)/180;

                float a = (float) (wall.w_start.y - wall.w_end.y) / (wall.w_start.x - wall.w_end.x);
                float b = (float) (wall.w_start.x * wall.w_end.y - wall.w_start.y * wall.w_end.x) / (wall.w_start.x - wall.w_end.x);
                float tan = (float) Math.tan(radAngle + Math.atan(offsetX / fov));
                float c = tan;
                float d = originY - originX * tan;

                float x;
                float y;

                if (a == c) {
                    y = (d - b) / (a - c);
                    x = a * y + b;
                } else {
                    x = (d - b) / (a - c);
                    y = a * x + b;
                }

                interX = dist(wall.w_start, new Point((int) x, (int) y));
            } else {
                float radAngle = ((angle-90) * (float)Math.PI)/180;

                float a = 0;
                float b = (float) (wall.w_start.y * wall.w_end.x - wall.w_start.x * wall.w_end.y) / (wall.w_start.y - wall.w_end.y);
                float tan = (float) Math.tan(radAngle + Math.atan(offsetX / fov));
                float c = -tan;
                float d = originX - originY * tan;

                float x;
                float y;

                if (a == c) {
                    x = (d - b) / (a - c);
                    y = a * x + b;
                } else {
                    y = (d - b) / (a - c);
                    x = a * y + b;
                }

                interX = dist(wall.w_start, new Point((int) x, (int) y));
            }

            interY = getInterY(dist, offsetX, -offsetY, fov, bottom, height, originZ);

            int modX = (int) (interX % 256);
            int modY = (int) (interY % 256);

            while (modX < 0) {
                modX += 256;
            }
            while (modY < 0) {
                modY += 256;
            }

            Color c = wall.w_texture[modX][modY];

            return c;
        }
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

        int red   = (cIn >> 16) & 255;
        int green = (cIn >> 8)  & 255;
        int blue  = (cIn)       & 255;

        red = (int) (factor * red);
        green = (int) (factor * green);
        blue = (int) (factor * blue);

        Color cOut = new Color(Math.max(red, 0), Math.max(green, 0), Math.max(blue, 0));

        return cOut;
    }

    public static boolean checkBehind(Point origin, Point point, float angle) { // returns false if the point is behind
        float x1 = point.x - origin.x;
        float y1 = point.y - origin.y;

        float cos = (float) Math.cos(-angle);
        float sin = (float) Math.sin(-angle);

        float x2 = x1 * cos - y1 * sin;
        //float y2 = x1 * sin + y1 * cos;   // don't need this stuff

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


    public static float Q_rsqrt(float number) {
        int i;
        float x2, y;
        final float threehalfs = 1.5F;

        ByteBuffer buffer1 = ByteBuffer.allocate(Integer.BYTES);
        ByteBuffer buffer2 = ByteBuffer.allocate(Integer.BYTES);

        x2 = number * 0.5F;
        y  = number;
        buffer1.putFloat(y);                        // evil floating point bit hack, but more cursed
        buffer1.rewind();
        i  = buffer1.getInt();
        i  = 0x5f3759df - ( i >> 1 );               // what the fuck?
        buffer2.putInt(i);
        buffer2.rewind();
        y  = buffer2.getFloat();
        y  = y * ( threehalfs - ( x2 * y * y ) );   // 1st iteration
        y  = y * ( threehalfs - ( x2 * y * y ) );   // 2nd iteration, can be removed

        return y;
    }

    public static float cross_product(Point a, Point b) {
        return a.x * b.y - b.x * a.y;
    }
    public static float cross_product(Point o, Point a, Point b) {
        return (a.x - o.x) * (b.y - o.y) - (b.x - o.x) * (a.y - o.y);
    }

    public static float dist(Point a, Point b) {
        return (float) Math.sqrt((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y));
    }

    public static float sq_dist(Point a, Point b) {
        return (a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y);
    }
}
