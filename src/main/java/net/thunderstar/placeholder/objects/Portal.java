package net.thunderstar.placeholder.objects;

import net.thunderstar.placeholder.render.util.RenderHelpFunctions;
import net.thunderstar.placeholder.util.FastMath;

import java.awt.*;

public class Portal {
    public Point p_start;
    public Point p_end;
    public Sector p_sector;
    public Portal p_negator;

    public Portal(Point start, Point end) {
        p_start = start;
        p_end = end;
    }

    public void setSector(Sector sector) {
        p_sector = sector;
    }

    public float intersectDist(int originX, int originY, float angle, int offset, float fov, float r_fov) {
        if (p_start.x - p_end.x != 0) {
            float radAngle = (angle * (float)Math.PI)/180;

            float a = (float) (p_start.y - p_end.y) / (p_start.x - p_end.x);
            float b = (float) (p_start.x * p_end.y - p_start.y * p_end.x) / (p_start.x - p_end.x);
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
            float b = (float) (p_start.y * p_end.x - p_start.x * p_end.y) / (p_start.y - p_end.y);
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
        if (RenderHelpFunctions.checkBehind(new Point(originX, originY), p_start, radAngle) || RenderHelpFunctions.checkBehind(new Point(originX, originY), p_end, radAngle)) {
            if (p_start.x != p_end.x) {
                float a = (float) (p_start.y - p_end.y) / (p_start.x - p_end.x);
                float b = (float) (p_start.x * p_end.y - p_start.y * p_end.x) / (p_start.x - p_end.x);
                float rad = radAngle + FastMath.atan2(offset, fov);
                float c = (float) Math.tan(rad);
                float d = (float) (originY - originX * Math.tan(rad));

                float x;
                float y;

                float distA;
                float distB;
                float distC;

                distC = (float) Math.sqrt((p_start.x - p_end.x) * (p_start.x - p_end.x) + (p_start.y - p_end.y) * (p_start.y - p_end.y));

                if (a == c) {
                    y = (d - b) / (a - c);
                    x = a * y + b;
                } else {
                    x = (d - b) / (a - c);
                    y = a * x + b;
                }

                distA = (float) Math.sqrt((p_start.x - x) * (p_start.x - x) + (p_start.y - y) * (p_start.y - y));
                distB = (float) Math.sqrt((p_end.x - x) * (p_end.x - x) + (p_end.y - y) * (p_end.y - y));

                return distA <= distC && distB <= distC && RenderHelpFunctions.checkBehind(new Point(originX, originY), new Point((int) x, (int) y), rad);
            } else {
                float a = 0;
                float b = (float) (p_start.y * p_end.x - p_start.x * p_end.y) / (p_start.y - p_end.y);
                float rad = radAngle + FastMath.atan2(offset, fov);
                float c = (float) -Math.tan(rad - Math.PI / 2);
                float d = (float) (originX + originY * Math.tan(rad - Math.PI / 2));

                float x;
                float y;

                float distA;
                float distB;
                float distC;

                distC = (float) Math.abs(p_start.y - p_end.y);

                if (a == c) {
                    x = (d - b) / (a - c);
                    y = a * x + b;
                } else {
                    y = (d - b) / (a - c);
                    x = a * y + b;
                }

                distA = (float) Math.sqrt((p_start.x - x) * (p_start.x - x) + (p_start.y - y) * (p_start.y - y));
                distB = (float) Math.sqrt((p_end.x - x) * (p_end.x - x) + (p_end.y - y) * (p_end.y - y));

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
            return 0;
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

        int deltaX = p_start.x - originX;
        int deltaY = p_start.y - originY;

        float sin = (float) Math.sin(radAngle);
        float cos = (float) Math.cos(radAngle);

        float x = deltaX*cos + deltaY*sin;
        float y = deltaY*cos - deltaX*sin;

        return fov * y/x;
    }

    public float drawEnd(int originX, int originY, float angle, float fov) {
        float radAngle = (float) Math.toRadians(angle);

        int deltaX = p_end.x - originX;
        int deltaY = p_end.y - originY;

        float sin = (float) Math.sin(radAngle);
        float cos = (float) Math.cos(radAngle);

        float x = deltaX*cos + deltaY*sin;
        float y = deltaY*cos - deltaX*sin;

        return fov * y/x;
    }
}
