package net.thunderstar.placeholder.util;

public class FastMath {
    private static final float PI = (float) Math.PI;
    private static final float PI_2 = PI / 2;
    private static final float PI_NEG_2 = -PI_2;
    private static final int SIZE = 1024;
    private static final float[] ATAN2;

    static {
        final int Size_Ar = SIZE + 1;
        ATAN2 = new float[Size_Ar];
        for (int i = 0; i <= SIZE; i++) {
            double d = (double) i / SIZE;
            double x = 1;
            double y = x * d;
            ATAN2[i] = (float) Math.atan2(y, x);
        }
    }

    public static float atan2(float y, float x) {
        if (y < 0) {
            if (x < 0) {
                // (y < x) because == (-y > -x)
                if (y < x) {
                    return PI_NEG_2 - ATAN2[(int) (x / y * SIZE)];
                } else {
                    return ATAN2[(int) (y / x * SIZE)] - PI;
                }
            } else {
                y = -y;
                if (y > x) {
                    return ATAN2[(int) (x / y * SIZE)] - PI_2;
                } else {
                    return -ATAN2[(int) (y / x * SIZE)];
                }
            }
        } else {
            if (x < 0) {
                x = -x;
                if (y > x) {
                    return PI_2 + ATAN2[(int) (x / y * SIZE)];
                } else {
                    return PI - ATAN2[(int) (y / x * SIZE)];
                }
            } else {
                if (y > x) {
                    return PI_2 - ATAN2[(int) (x / y * SIZE)];
                } else {
                    return ATAN2[(int) (y / x * SIZE)];
                }
            }
        }
    }
}
