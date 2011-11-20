package com.jcj.jumper;

import com.jcj.gd2d.DynamicGameObject;

public class Bob extends DynamicGameObject {
    public static final int BOB_STATE_JUMP = 0;
    public static final int BOB_STATE_FALL = 1;
    public static final int BOB_STATE_HIT = 2;
    public static final float BOB_JUMP_VELOCITY = 11;
    public static final float BOB_MOVE_VELOCITY = 20;
    public static final float BOB_WIDTH = 0.8f;
    public static final float BOB_HEIGHT = 0.8f;

    int state;
    float stateTime;

    public Bob(float x, float y) {
        super(x, y, BOB_WIDTH, BOB_HEIGHT);
        state = BOB_STATE_FALL;
        stateTime = 0;
    }

    public void update(float deltaTime) {
        velocity.add(0,0);
        position.add(0 ,1 );
        bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);

        if (velocity.y > 0 && state != BOB_STATE_HIT) {
            if (state != BOB_STATE_JUMP) {
                state = BOB_STATE_JUMP;
                stateTime = 0;
            }
        }

        if (velocity.y < 0 && state != BOB_STATE_HIT) {
            if (state != BOB_STATE_FALL) {
                state = BOB_STATE_FALL;
                stateTime = 0;
            }
        }

        if (position.x < 0)
            position.x = 0;
        if (position.x > World.WORLD_WIDTH)
            position.x = World.WORLD_WIDTH;

        stateTime += deltaTime;
    }

    public void hitSquirrel() {
        velocity.set(0, 0);
        state = BOB_STATE_HIT;
        stateTime = 0;
    }

    public void hitPlatform() {
        velocity.y = BOB_JUMP_VELOCITY;
        state = BOB_STATE_JUMP;
        stateTime = 0;
    }

    public void hitSpring() {
        velocity.y = BOB_JUMP_VELOCITY * 1.5f;
        state = BOB_STATE_JUMP;
        stateTime = 0;
    }
}
