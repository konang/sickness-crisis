package com.jcj.framework.impl;

import com.jcj.framework.Game;
import com.jcj.framework.Screen;

public abstract class GLScreen extends Screen {
    protected final GLGraphics glGraphics;
    protected final GLGame glGame;

    public GLScreen(Game game) {
        super(game);
        glGame = (GLGame) game;
        glGraphics = ((GLGame)game).getGLGraphics();
    }

}
