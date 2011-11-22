package com.jcj.gameLayout;

import com.jcj.framework.Game;
import com.jcj.framework.Input.TouchEvent;
import com.jcj.framework.gl.Camera2D;
import com.jcj.framework.gl.SpriteBatcher;
import com.jcj.framework.gl.Texture;
import com.jcj.framework.gl.TextureRegion;
import com.jcj.framework.impl.GLScreen;
import com.jcj.framework.math.OverlapTester;
import com.jcj.framework.math.Rectangle;
import com.jcj.framework.math.Vector2;
import com.jcj.gameLayout.Recursos;
import com.jcj.gameLayout.Settings;

import java.util.List;
import javax.microedition.khronos.opengles.GL10;

/**
 * Clase PantallaHighscores extiende a la clase GLScreen
 * Clase donde se crea la pantalla para visualizar los puntajes salvados de cada misión
 *
 * @author jugandoconjava basado en el libro Beginning Android Games de Mario Zechner
 */

public class PantallaHighscores extends GLScreen {
    Camera2D guiCam;
    SpriteBatcher batcher;
    Rectangle backBounds;
    Vector2 touchPoint;
    String[] highScores;
    float xOffset = 0;
    public static Texture backgroundHi;
    public static TextureRegion backgroundHigh;

    public PantallaHighscores(Game game) {
        super(game);

        guiCam = new Camera2D(glGraphics, 480, 320);
        backBounds = new Rectangle(0, 0, 100, 100);
        touchPoint = new Vector2();
        batcher = new SpriteBatcher(glGraphics, 100);
        highScores = new String[5];
        for (int i = 0; i < 4; i++) {
            highScores[i] = "Mision "+(i + 1) + ":" + Settings.highscores[i];
            xOffset = Math.max(highScores[i].length() * Recursos.font.glyphWidth, xOffset);
        }
    }

    /**
     * Método update
     * Metodo que actualiza las acciones realizadas en la pantalla
     * 
     * @param float deltaTime indica el tiempo transcurrido del sistema desde la
     * última vez que se actualizo
     *
     */
    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            touchPoint.set(event.x, event.y);
            guiCam.touchToWorld(touchPoint);

            if (event.type == TouchEvent.TOUCH_UP) {
                if (OverlapTester.pointInRectangle(backBounds, touchPoint)) {
                    game.setScreen(new PantallaMenu(game));
                    return;
                }
            }
        }
    }

    /**
     * Método present
     * Método que maneja el batcher y la camara, donde se pintan las texturas
     * 
     * @param float deltaTime indica el tiempo transcurrido del sistema desde la
     * última vez que se actualizo
     *
     */
    
    @Override
    public void present(float deltaTime) {
        GL10 gl = glGraphics.getGL();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        guiCam.setViewportAndMatrices();

        gl.glEnable(GL10.GL_TEXTURE_2D);

        batcher.beginBatch(backgroundHi);
            batcher.drawSprite(240, 160, 480, 320, backgroundHigh);
        batcher.endBatch();

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        batcher.beginBatch(Recursos.items);
            float y = 120;
            for (int i = 3; i >= 0; i--) {
                Recursos.font.drawText(batcher, highScores[i], 160+65, y);             //Cheque para ponerlos despues de la imagen
                y += Recursos.font.glyphHeight;
            }
            batcher.drawSprite(32, 32, 30, 19, Recursos.flechaizquierda);
        batcher.endBatch();

        gl.glDisable(GL10.GL_BLEND);
    }
    
    /**
     * Método resume
     * Metodo que repinta el fondo o background
     * 
     * @param float deltaTime indica el tiempo transcurrido del sistema desde la
     * última vez que se actualizo
     *
     */

    @Override
    public void resume() {
    	backgroundHi = new Texture(glGame, "backgroundhighscore.png");
        backgroundHigh = new TextureRegion(backgroundHi, 0, 0, 480, 320);
    }
    
    /**
     * Método pause
     * Método que pause y deja de actualizar
     * 
     */

    @Override
    public void pause() {

    }

    /**
     * Método dispose
     * Método que libera la memoria del background
     * 
     * @param float deltaTime indica el tiempo transcurrido del sistema desde la
     * última vez que se actualizo
     *
     */
    @Override
    public void dispose() {
    	backgroundHi.dispose();
    }
}
