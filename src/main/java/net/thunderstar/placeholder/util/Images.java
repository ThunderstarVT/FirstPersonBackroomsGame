package net.thunderstar.placeholder.util;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Images {
    public static String defaultPath = "src\\main\\resources\\assets\\placeholder\\";

    public static ImageIcon HOME_WALL_1 = new ImageIcon(defaultPath + "textures\\misc\\VS--YouTube-TryingYourSTUPIDESTTacticsinWorldofTanks-15’47”.png");
    public static Color[][] HOME_WALL_1_COLORS = new Color[256][256];


    public static ImageIcon LEVEL0_WALL_1 = new ImageIcon(defaultPath + "textures\\environment\\level0_wall_1.png");
    public static Color[][] LEVEL0_WALL_1_COLORS = new Color[256][256];

    public static ImageIcon LEVEL0_FLOOR_1 = new ImageIcon(defaultPath + "textures\\environment\\level0_floor_1.png");
    public static Color[][] LEVEL0_FLOOR_1_COLORS = new Color[256][256];

    public static ImageIcon LEVEL0_CEILING_1 = new ImageIcon(defaultPath + "textures\\environment\\level0_ceiling_1.png");
    public static Color[][] LEVEL0_CEILING_1_COLORS = new Color[256][256];


    public static ImageIcon INV_TEMPLATE = new ImageIcon(defaultPath + "textures\\misc\\inv.png");


    public static Color[][] EMPTY = null;

    public static Color[][] generateColorArrays(ImageIcon imageIcon) {
        Color[][] array = new Color[256][256];
        BufferedImage bufferedImage = convertToBufferedImage(imageIcon);

        for (int Y = 0; Y < 256; Y++) {
            //System.out.println("Making color array: Row " + Y + " of 256");
            for (int X = 0; X < 256; X++) {
                int c = bufferedImage.getRGB(X,Y);

                int red   = (c >> 16) & 255;
                int green = (c >> 8)  & 255;
                int blue  = (c)       & 255;

                array[X][Y] = new Color(red, green, blue);
            }
        }

        return array;
    }

    public static Color[][] generateColorArraysSmall(ImageIcon imageIcon) {
        Color[][] array = new Color[16][16];
        BufferedImage bufferedImage = convertToBufferedImage(imageIcon);

        for (int Y = 0; Y < 16; Y++) {
            //System.out.println("Making color array: Row " + Y + " of 16");
            for (int X = 0; X < 16; X++) {
                int c = bufferedImage.getRGB(X,Y);

                int alpha = (c >> 24) & 255;
                int red   = (c >> 16) & 255;
                int green = (c >> 8)  & 255;
                int blue  = (c)       & 255;

                array[X][Y] = new Color(red, green, blue, alpha);
            }
        }

        return array;
    }

    public static Color[][] generateColorArraysLarge(ImageIcon imageIcon) {
        Color[][] array = new Color[512][512];
        BufferedImage bufferedImage = convertToBufferedImage(imageIcon);

        for (int Y = 0; Y < 512; Y++) {
            //System.out.println("Making color array: Row " + Y + " of 512");
            for (int X = 0; X < 512; X++) {
                int c = bufferedImage.getRGB(X,Y);

                int alpha = (c >> 24) & 255;
                int red   = (c >> 16) & 255;
                int green = (c >> 8)  & 255;
                int blue  = (c)       & 255;

                array[X][Y] = new Color(red, green, blue, alpha);
            }
        }

        return array;
    }

    public static Color[][] generateColorArraysScreen(ImageIcon imageIcon) {
        Color[][] array = new Color[320][240];
        BufferedImage bufferedImage = convertToBufferedImage(imageIcon);

        for (int Y = 0; Y < 240; Y++) {
            //System.out.println("Making color array: Row " + Y + " of 240");
            for (int X = 0; X < 320; X++) {
                int c = bufferedImage.getRGB(X,Y);

                int alpha = (c >> 24) & 255;
                int red   = (c >> 16) & 255;
                int green = (c >> 8)  & 255;
                int blue  = (c)       & 255;

                array[X][Y] = new Color(red, green, blue, alpha);
            }
        }

        return array;
    }

    public static BufferedImage convertToBufferedImage(ImageIcon icon) {
        Image image = icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_FAST);

        BufferedImage newImage = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newImage.createGraphics();

        g.setColor(new Color(0xFFFFFF));
        g.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());

        new ImageIcon(image).paintIcon(null, g, 0, 0);

        g.dispose();
        return newImage;
    }
}
