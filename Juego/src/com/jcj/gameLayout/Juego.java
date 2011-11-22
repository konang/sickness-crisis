package com.jcj.gameLayout;

import com.jcj.framework.Screen;
import com.jcj.framework.impl.GLGame;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
/**
 * Clase Juego extiende a la clase GLGame.
 * Clase principal del paquete, en ella se carga la pantalla inicial y los settings
 * @author Bacon Rocket Studios basado en el libro Beginning Android Games de Mario Zechner
 *
 */
public class Juego extends GLGame {
    boolean firstTimeCreate = true;

    /**
     * Método getStartScreen
     * Método que manda llamar a la primer pantalla del juego cuando se inicia la aplicacion
     *
     */
    
    @Override
    public Screen getStartScreen() {
        return new PantallaLogo(this);
    }

    /**
     * Método getStartScreen
     * Método que carga las especificaciónes y configuraciones anteriormente guardadas
     *	
     *@param gl 
     *@param config
     */
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

    
    /**
     * Método onPause
     * Método que pausa el juego
     *
     */
    @Override
    public void onPause() {
        super.onPause();
        if (Settings.soundEnabled)
            Recursos.music.pause();
    }

}
