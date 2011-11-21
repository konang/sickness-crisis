package com.jcj.gameLayout;

import com.jcj.framework.Screen;
import com.jcj.framework.impl.GLGame;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Juego extends GLGame {
    boolean firstTimeCreate = true;

    @Override
    public Screen getStartScreen() {
        return new PantallaLogo(this);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        super.onSurfaceCreated(gl, config);
        if (firstTimeCreate) {
            Settings.load(getFileIO());
            Recursos.load(this);
            firstTimeCreate = false;
        } else {
            Recursos.reload();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Settings.soundEnabled)
            Recursos.music.pause();
    }

}
