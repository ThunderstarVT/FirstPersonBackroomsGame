package net.thunderstar.placeholder.util;

import net.thunderstar.placeholder.objects.Level;
import net.thunderstar.placeholder.render.util.PortalBSPRenderFunctions;

import java.awt.*;
import java.io.*;

public class GenerateResources implements Serializable {
    public static void writeFiles() throws IOException {
        Images.HOME_WALL_1_COLORS = Images.generateColorArrays(Images.HOME_WALL_1);
        writer(Images.HOME_WALL_1_COLORS, "home_wall_1.txt");


        Images.LEVEL0_WALL_1_COLORS = Images.generateColorArrays(Images.LEVEL0_WALL_1);
        writer(Images.LEVEL0_WALL_1_COLORS, "level0_wall_1.txt");

        Images.LEVEL0_FLOOR_1_COLORS = Images.generateColorArrays(Images.LEVEL0_FLOOR_1);
        writer(Images.LEVEL0_FLOOR_1_COLORS, "level0_floor_1.txt");

        Images.LEVEL0_CEILING_1_COLORS = Images.generateColorArrays(Images.LEVEL0_CEILING_1);
        writer(Images.LEVEL0_CEILING_1_COLORS, "level0_ceiling_1.txt");

        Reference.invColors = Images.generateColorArraysScreen(Images.INV_TEMPLATE);
        writer(Reference.invColors, "inv.txt");
    }

    public static void writer(Object object, String file_name) throws IOException {
        File file = new File("src\\main\\resources\\assets\\placeholder\\generated\\resources\\" + file_name);
        Boolean _written = file.createNewFile();
        System.out.println("File created: " + file);

        FileOutputStream fileOutputStream = new FileOutputStream("src\\main\\resources\\assets\\placeholder\\generated\\resources\\" + file_name);
        ObjectOutput objectOutput = new ObjectOutputStream(fileOutputStream);
        objectOutput.writeObject(object);

        System.out.println("File written: src\\main\\resources\\assets\\placeholder\\generated\\resources\\" + file_name);
    }

    public static void readFiles() throws IOException, ClassNotFoundException {
        Images.HOME_WALL_1_COLORS = colorArrayReader("home_wall_1.txt");
        Images.LEVEL0_WALL_1_COLORS = colorArrayReader("level0_wall_1.txt");
        Images.LEVEL0_FLOOR_1_COLORS = colorArrayReader("level0_floor_1.txt");
        Images.LEVEL0_CEILING_1_COLORS = colorArrayReader("level0_ceiling_1.txt");

        Reference.invColors = colorArrayReader("inv.txt");
    }

    public static Color[][] colorArrayReader(String file_name) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("src\\main\\resources\\assets\\placeholder\\generated\\resources\\" + file_name);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Color[][] o = (Color[][])objectInputStream.readObject();

        System.out.println("File read: src\\main\\resources\\assets\\placeholder\\generated\\resources\\" + file_name);

        return o;
    }


    public static void generateBSPTrees() {
        for (Level level : Levels.levels) {
            level.l_tree = PortalBSPRenderFunctions.generateBSPTree(level);
        }
    }
}
