package net.thunderstar.placeholder.entities;

import java.awt.*;

public class EntityBase {
    public float e_posX;
    public float e_posY;
    public float e_posZ;

    public float e_rotation;

    public float e_velX = 0;
    public float e_velY = 0;
    public float e_velZ = 0;

    public Color[][] e_texture;

    public EntityBase(float posX, float posY, float posZ, float rotation, Color[][] texture) {
        e_posX = posX;
        e_posY = posY;
        e_posZ = posZ;
        e_rotation = rotation;
        e_texture = texture;
    }
}
