package net.thunderstar.placeholder.util;

import net.thunderstar.placeholder.items.Item;

import java.awt.*;

public class Inventory {
    public static boolean shouldUpdate = false;

    public static Item[][] inv_items = new Item[12][12];
    public static boolean[][] inv_hasItem = new boolean[12][12];
    public static Point pos = new Point(0, 0);

    public void init() {
        for (int Y = 0; Y < 12; Y++) {
            for (int X = 0; X < 12; X++) {
                inv_hasItem[X][Y] = false;
                inv_items[X][Y] = new Item(Images.EMPTY, "Empty Slot");
            }
        }
    }
}
