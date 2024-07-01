package net.thunderstar.placeholder.items;

import java.awt.*;

public class Item {
    public Color[][] i_texture;
    public String i_name;

    public Item(Color[][] texture, String name) {
        i_texture = texture;
        i_name = name;
    }
}
