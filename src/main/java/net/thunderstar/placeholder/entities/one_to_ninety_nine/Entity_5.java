package net.thunderstar.placeholder.entities.one_to_ninety_nine;

import jdk.jfr.Name;
import net.thunderstar.placeholder.entities.EntityBase;
import net.thunderstar.placeholder.entities.util.interfaces.States;
import net.thunderstar.placeholder.entities.util.Texture_Rotation;

@Name("Clumps")
public class Entity_5 extends EntityBase {
    public Entity_5(float posX, float posY, float posZ, float rotation, Texture_Rotation[][] animation_textures, int[] animation_frame_duration) {
        super(posX, posY, posZ, rotation, animation_textures, animation_frame_duration);

        e_reactionTime = 100;
        e_attackCooldown = 50;
        e_attackDuration = 25;

        health = 150;

        attackDamage = 20;

        nonPlayer_targetTypes = new Class[]{
            Entity_1.class
        };
    }

    int react_in = e_reactionTime;
    int attack_in = e_attackCooldown;
    int attack_time = e_attackDuration;

    float dist;

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
            case States.CHASE:
                updateChase();
                break;
            case States.ATTACK_MELEE:
                updateAttackMelee();
        }
    }

    private void updateIdle() {
        react_in = e_reactionTime;
    }

    private void updateChase() {
        if (react_in > 0) {
            react_in--;
        } else {
            if (attack_in > 0) {
                attack_in--;
            } else if (dist <= 2400) {
                e_state = States.ATTACK_MELEE;
            }
        }
    }

    private void updateAttackMelee() {


        if (attack_time <= 0) {
            e_state = States.CHASE;
        }
    }
}
