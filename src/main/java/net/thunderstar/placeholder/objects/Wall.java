package net.thunderstar.placeholder.objects;

import net.thunderstar.placeholder.render.util.RenderHelpFunctions;
import net.thunderstar.placeholder.render.util.Texture;
import net.thunderstar.placeholder.util.FastMath;

import java.awt.*;

public class Wall {
    public Point w_start;
    public Point w_end;
    public Texture w_texture;

    public int w_bottom = 0;
    public int w_height = 1000;

    public Wall(Point start, Point end, Texture texture) {
        w_start = start;
        w_end = end;
        w_texture = texture;
    }

    public Wall setSize(int bottom, int height) {
        w_bottom = bottom;
        w_height = height;

        return this;
    }

    public float intersectDist(int originX, int originY, float angle, int offset, float fov, float r_fov) {
        if (w_start.x - w_end.x != 0) {
            float radAngle = (angle * (float)Math.PI)/180;

            float a = (float) (w_start.y - w_end.y) / (w_start.x - w_end.x);
            float b = (float) (w_start.x * w_end.y - w_start.y * w_end.x) / (w_start.x - w_end.x);
            float tan = (float) Math.tan(radAngle + FastMath.atan2(offset, fov));
            float c = tan;
            float d = originY - originX * tan;

            float x;
            float y;

            float dist;

            if (a == c) {
                y = (d - b) / (a - c);
                x = a * y + b;
            } else {
                x = (d - b) / (a - c);
                y = a * x + b;
            }

            dist = (float) Math.sqrt((originX - x) * (originX - x) + (originY - y) * (originY - y));

            return dist;
        } else {
            float radAngle = ((angle-90) * (float)Math.PI)/180;

            float a = 0;
            float b = (float) (w_start.y * w_end.x - w_start.x * w_end.y) / (w_start.y - w_end.y);
            float tan = (float) Math.tan(radAngle + FastMath.atan2(offset, fov));
            float c = -tan;
            float d = originX + originY * tan;

            float x;
            float y;

            float dist;

            if (a == c) {
                x = (d - b) / (a - c);
                y = a * x + b;
            } else {
                y = (d - b) / (a - c);
                x = a * y + b;
            }

            dist = (float) Math.sqrt((originX - x) * (originX - x) + (originY - y) * (originY - y));

            return dist;
        }
    }

    public boolean intersectBool(int originX, int originY, float angle, int offset, float fov, float r_fov) {
        float radAngle = (float) Math.toRadians(angle);
        if (RenderHelpFunctions.checkBehind(new Point(originX, originY), w_start, radAngle) || RenderHelpFunctions.checkBehind(new Point(originX, originY), w_end, radAngle)) {
            if (w_start.x != w_end.x) {
                float a = (float) (w_start.y - w_end.y) / (w_start.x - w_end.x);
                float b = (float) (w_start.x * w_end.y - w_start.y * w_end.x) / (w_start.x - w_end.x);
                float rad = radAngle + FastMath.atan2(offset, fov);
                float c = (float) Math.tan(rad);
                float d = (float) (originY - originX * Math.tan(rad));

                float x;
                float y;

                float distA;
                float distB;
                float distC;

                if (a == c) {
                    y = (d - b) / (a - c);
                    x = a * y + b;
                } else {
                    x = (d - b) / (a - c);
                    y = a * x + b;
                }

                distA = (float) Math.hypot(w_start.x - x, w_start.y - y);
                distB = (float) Math.hypot(w_end.x - x, w_end.y - y);
                distC = (float) Math.hypot(w_start.x - w_end.x, w_start.y - w_end.y);

                return distA <= distC && distB <= distC && RenderHelpFunctions.checkBehind(new Point(originX, originY), new Point((int) x, (int) y), rad);
            } else {
                float a = 0;
                float b = (float) (w_start.y * w_end.x - w_start.x * w_end.y) / (w_start.y - w_end.y);
                float rad = radAngle + FastMath.atan2(offset, fov);
                float c = (float) -Math.tan(rad - Math.PI / 2);
                float d = (float) (originX + originY * Math.tan(rad - Math.PI / 2));

                float x;
                float y;

                float distA;
                float distB;
                float distC;

                if (a == c) {
                    x = (d - b) / (a - c);
                    y = a * x + b;
                } else {
                    y = (d - b) / (a - c);
                    x = a * y + b;
                }

                distA = (float) Math.hypot(w_start.x - x, w_start.y - y);
                distB = (float) Math.hypot(w_end.x - x, w_end.y - y);
                distC = (float) Math.abs(w_start.y - w_end.y);

                return distA <= distC && distB <= distC && RenderHelpFunctions.checkBehind(new Point(originX, originY), new Point((int) x, (int) y), rad);
            }
        }
        return false;
    }

    public int drawTop(int bottom, int height, int originZ, float dist, float offset, float fov) {
        float d = ((dist * fov)*RenderHelpFunctions.Q_rsqrt(fov*fov + offset*offset));
        int above = bottom + height - originZ;
        if (d != 0) {
            float top = (fov * above)/d;
            return (int) Math.floor(120 - top);
        } else {
            return 0;
        }
    }
    public int drawTopRaw(int bottom, int height, int originZ, float dist, float fov) {
        int above = bottom + height - originZ;
        if (dist != 0) {
            float top = (fov * above)/dist;
            return (int) Math.floor(120 - top);
        } else {
            return 0;
        }
    }

    public int drawBottom(int bottom, int originZ, float dist, float offset, float fov) {
        float d = (dist*RenderHelpFunctions.Q_rsqrt(fov*fov + offset*offset));
        int above = bottom - originZ;
        if (d != 0) {
            float top = (above)/d;
            return (int) Math.ceil(120 - top);
        } else {
            return 240;
        }
    }
    public int drawBottomRaw(int bottom, int originZ, float dist, float fov) {
        int above = bottom - originZ;
        if (dist != 0) {
            float top = (above)/dist;
            return (int) Math.ceil(120 - top);
        } else {
            return 0;
        }
    }

    public float drawStart(int originX, int originY, float angle, float fov) {
        float radAngle = (float) Math.toRadians(angle);

        int deltaX = w_start.x - originX;
        int deltaY = w_start.y - originY;

        float sin = (float) Math.sin(radAngle);
        float cos = (float) Math.cos(radAngle);

        float x = deltaX*cos + deltaY*sin;
        float y = deltaY*cos - deltaX*sin;

        return fov * y/x;
    }

    public float drawEnd(int originX, int originY, float angle, float fov) {
        float radAngle = (float) Math.toRadians(angle);

        int deltaX = w_end.x - originX;
        int deltaY = w_end.y - originY;

        float sin = (float) Math.sin(radAngle);
        float cos = (float) Math.cos(radAngle);

        float x = deltaX*cos + deltaY*sin;
        float y = deltaY*cos - deltaX*sin;

        return fov * y/x;
    }
}
