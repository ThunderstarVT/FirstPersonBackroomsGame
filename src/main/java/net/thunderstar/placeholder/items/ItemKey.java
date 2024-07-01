package net.thunderstar.placeholder.items;

import java.awt.*;

public class ItemKey extends Item{
    long k_id;
    String k_level_id;

    public ItemKey(Color[][] texture, String name, byte id, String level_id) {
        super(texture, name);
        k_id = id;
        k_level_id = level_id;
    }
}
