package net.thunderstar.placeholder.objects;

import net.thunderstar.placeholder.render.util.RenderHelpFunctions;

import java.awt.*;

public class Wall {
    public Point w_start;
    public Point w_end;
    public Color[][] w_texture;

    public Wall(Point start, Point end, Color[][] texture) {
        w_start = start;
        w_end = end;
        w_texture = texture;
    }

    public float intersectDist(int originX, int originY, float angle, int offset, float fov) {
        if (w_start.x - w_end.x != 0) {
            float radAngle = (angle * (float)Math.PI)/180;

            float a = (float) (w_start.y - w_end.y) / (w_start.x - w_end.x);
            float b = (float) (w_start.x * w_end.y - w_start.y * w_end.x) / (w_start.x - w_end.x);
            float tan = (float) Math.tan(radAngle + Math.atan(offset / fov));
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
            float tan = (float) Math.tan(radAngle + Math.atan(offset / fov));
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

    public boolean intersectBool(int originX, int originY, float angle, int offset, float fov) {
        if (w_start.x != w_end.x) {
            float radAngle = (angle * (float)Math.PI)/180;

            float a = (float) (w_start.y - w_end.y) / (w_start.x - w_end.x);
            float b = (float) (w_start.x * w_end.y - w_start.y * w_end.x) / (w_start.x - w_end.x);
            float rad = (float) (radAngle + Math.atan(offset / fov));
            float c = (float) Math.tan(rad);
            float d = (float) (originY - originX * Math.tan(rad));

            float x;
            float y;

            float distA;
            float distB;
            float distC;

            distC = (float) Math.sqrt((w_start.x - w_end.x) * (w_start.x - w_end.x) + (w_start.y - w_end.y) * (w_start.y - w_end.y));

            if (a == c) {
                y = (d - b) / (a - c);
                x = a * y + b;
            } else {
                x = (d - b) / (a - c);
                y = a * x + b;
            }

            distA = (float) Math.sqrt((w_start.x - x) * (w_start.x - x) + (w_start.y - y) * (w_start.y - y));
            distB = (float) Math.sqrt((w_end.x - x) * (w_end.x - x) + (w_end.y - y) * (w_end.y - y));

            return distA <= distC && distB <= distC && RenderHelpFunctions.checkBehind(new Point(originX, originY), new Point((int) x, (int) y), rad);
        } else {
            float radAngle = ((angle) * (float)Math.PI)/180;

            float a = 0;
            float b = (float) (w_start.y * w_end.x - w_start.x * w_end.y) / (w_start.y - w_end.y);
            float rad = (float) (radAngle + Math.atan(offset / fov));
            float c = (float) -Math.tan(rad - Math.PI/2);
            float d = (float) (originX + originY * Math.tan(rad - Math.PI/2));

            float x;
            float y;

            float distA;
            float distB;
            float distC;

            distC = (float) Math.abs(w_start.y - w_end.y);

            if (a == c) {
                x = (d - b) / (a - c);
                y = a * x + b;
            } else {
                y = (d - b) / (a - c);
                x = a * y + b;
            }

            distA = (float) Math.sqrt((w_start.x - x) * (w_start.x - x) + (w_start.y - y) * (w_start.y - y));
            distB = (float) Math.sqrt((w_end.x - x) * (w_end.x - x) + (w_end.y - y) * (w_end.y - y));

            return distA <= distC && distB <= distC && RenderHelpFunctions.checkBehind(new Point(originX, originY), new Point((int) x, (int) y), rad);
        }
    }

    public int drawTop(int bottom, int height, int originZ, float dist, float offset, float fov) {
        float d = ((dist * fov)*RenderHelpFunctions.Q_rsqrt(fov*fov + offset*offset));
        int above = bottom + height - originZ;
        if (d != 0) {
            float top = (fov * above)/d;
            return (int) Math.round(119.5 - top);
        } else {
            return 0;
        }
    }

    public int drawBottom(int bottom, int originZ, float dist, float offset, float fov) {
        float d = ((dist * fov)*RenderHelpFunctions.Q_rsqrt(fov*fov + offset*offset));
        int above = bottom - originZ;
        if (d != 0) {
            float top = (fov * above)/d;
            return (int) Math.round(120.5 - top);
        } else {
            return 0;
        }
    }
}
