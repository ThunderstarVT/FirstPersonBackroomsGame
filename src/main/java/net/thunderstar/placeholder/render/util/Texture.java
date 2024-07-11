package net.thunderstar.placeholder.render.util;

import java.awt.*;

public class Texture {
    Color[][] t_image;
    float t_scale;

    public Texture(Color[][] image, float scale) {
        t_image = image;
        t_scale = scale;
    }
}
