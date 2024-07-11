package net.thunderstar.placeholder.entities.one_to_ninety_nine;

import jdk.jfr.Name;
import net.thunderstar.placeholder.entities.EntityBase;
import net.thunderstar.placeholder.entities.util.interfaces.States;
import net.thunderstar.placeholder.entities.util.Texture_Rotation;

@Name("The Humans")
public class Entity_1 extends EntityBase {
    public Entity_1(float posX, float posY, float posZ, float rotation, Texture_Rotation[][] animation_textures, int[] animation_frame_duration) {
        super(posX, posY, posZ, rotation, animation_textures, animation_frame_duration);

        health = 100;

        nonPlayer_targetTypes = new Class[]{

        };
    }

    int react_in = e_reactionTime;

    public void update() {
        setDrawTexture();
        setVel();
        setPos();

        switch (e_state) {
            case States.DEAD:
                break;
            case States.IDLE:
                updateIdle();
                break;
        }
    }

    private void updateIdle() {
        react_in = e_reactionTime;
    }
}
