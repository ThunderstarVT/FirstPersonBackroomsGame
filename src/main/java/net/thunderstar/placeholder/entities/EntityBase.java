package net.thunderstar.placeholder.entities;

import net.thunderstar.placeholder.entities.util.interfaces.States;
import net.thunderstar.placeholder.entities.util.Texture_Rotation;
import net.thunderstar.placeholder.render.util.RenderHelpFunctions;

public class EntityBase {

    public float e_posX;
    public float e_posY;
    public float e_posZ;

    public float e_rotation;

    public float e_velX = 0;
    public float e_velY = 0;
    public float e_velZ = 0;

    public float forward_vel = 0;
    public float sideways_vel = 0;

    public int e_reactionTime = 0;
    public int e_attackCooldown = 0;
    public int e_attackDuration = 0;

    public Texture_Rotation[][] e_animationTextures;
    public int[] e_animationFrameDuration; // in game ticks, not frame ticks

    public int e_state = States.IDLE;

    public int animTimer = 0;
    public int animFrame = 0;
    public Texture_Rotation drawTexture;

    public EntityBase nonPlayer_target;
    public Class[] nonPlayer_targetTypes;

    public float health = 0;
    public float attackDamage = 0;

    public EntityBase(float posX, float posY, float posZ, float rotation, Texture_Rotation[][] animation_textures, int[] animation_frame_duration) {
        e_posX = posX;
        e_posY = posY;
        e_posZ = posZ;
        e_rotation = rotation;
        e_animationTextures = animation_textures;
        e_animationFrameDuration = animation_frame_duration;
    }

    public void setDrawTexture() {
        drawTexture = e_animationTextures[e_state][animFrame];
        if (animTimer <= 0) {
            animFrame += 1;
            if (e_state != States.DEAD) {
                animFrame = animFrame % e_animationTextures[e_state].length;
            } else if (animFrame >= e_animationTextures[e_state].length) {
                animFrame = e_animationTextures[e_state].length - 1;
            }
            animTimer = e_animationFrameDuration[e_state];
        }
    }

    public void setPos() {
        e_posX += e_velX;
        e_posY += e_velY;
        e_posZ += e_velZ;

        //TODO: check for collisions
    }

    public void setVel() {
        float sin = (float) Math.sin(Math.toRadians(Player.variables.rotation));
        float cos = (float) Math.cos(Math.toRadians(Player.variables.rotation));

        float target_velX;
        float target_velY;

        if (forward_vel != 0 && sideways_vel != 0) {
            target_velX = (sideways_vel * sin - forward_vel * cos) * RenderHelpFunctions.Q_rsqrt(2);
            target_velY = (-sideways_vel * cos -forward_vel * sin) * RenderHelpFunctions.Q_rsqrt(2);
        } else {
            target_velX = sideways_vel * sin - forward_vel * cos;
            target_velY = -sideways_vel * cos -forward_vel * sin;
        }

        Player.variables.velX += (target_velX - Player.variables.velX)/8;
        Player.variables.velY += (target_velY - Player.variables.velY)/8;

        Player.variables.posX += Player.variables.velX;
        Player.variables.posY += Player.variables.velY;
        Player.variables.posZ += Player.variables.velZ;
    }
}
