package net.thunderstar.placeholder.util;

import javax.swing.*;
import java.awt.*;

public class Reference {
    public static JFrame gameFrame = new JFrame("FPBG");
    public static JFrame invFrame = new JFrame("Inventory");
    public static JFrame fpsFrame = new JFrame("FPS");

    public static Color[][] gameColors = new Color[320][240];
    public static Color[][] invColors = new Color[320][240];


    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static Float scaleX = (float) screenSize.width/1920;
    public static Float scaleY = (float) screenSize.height/1080;

    public static Dimension gameFrameSize = new Dimension((int) (1280 * scaleX), (int) (960 * scaleX));
    public static Dimension invFrameSize = new Dimension((int) (640 * scaleX), (int) (480 * scaleX));

    public static Point gameFramePos = new Point((int) (640 * scaleX), (int) (8 * scaleY));
    public static Point invFramePos = new Point((int) (0 * scaleX), (int) (600 * scaleY));


    public static class inputDirection {
        public static float x = 0;
        public static float y = 0;
        public static float z = 0;
    }

    public static class pressedKeys {
        public static boolean forwardKey = false;
        public static boolean backwardKey = false;
        public static boolean moveLeftKey = false;
        public static boolean moveRightKey = false;
        public static boolean jumpKey = false;
        public static boolean sprintKey = false;
        public static boolean upKey = false;
        public static boolean downKey = false;
        public static boolean leftKey = false;
        public static boolean rightKey = false;
        public static boolean screenshotKey = false;
    }

    public static int forwardKey = 87;
    public static int backwardKey = 83;
    public static int moveLeftKey = 65;
    public static int moveRightKey = 68;
    public static int jumpKey = 32;
    public static int sprintKey = 16;
    public static int upKey = 38;
    public static int downKey = 40;
    public static int leftKey = 37;
    public static int rightKey = 39;

    public static int screenshotKey = 113;
}
