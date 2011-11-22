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
import java.util.List;
import javax.microedition.khronos.opengles.GL10;


/**
 * Clase PantallaMision extiende a la clase GLScreen
 * Clase donde se crea la pantalla en la que se muestra el menu de las misiones
 *
 * @author Bacon Rocket Studios basado en el libro Beginning Android Games de Mario Zechner
 */

public class PantallaMision extends GLScreen {
    Camera2D guiCam;
    SpriteBatcher batcher;
    Rectangle misionunoBounds;
    Rectangle misiondosBounds;
    Rectangle misiontresBounds;
    Rectangle misioncuatroBounds;
    Rectangle instruccionesBounds;
    Rectangle regresarMenuPrincipal;
    Vector2 touchPoint;
    Texture bgImage;
    TextureRegion bgRegion;
    public static int mision=2;
    

    public PantallaMision(Game game) {
        super(game);
        guiCam = new Camera2D(glGraphics, 480, 320);
        batcher = new SpriteBatcher(glGraphics, 100);
        misionunoBounds = new Rectangle(16, 191, 88, 60);
        regresarMenuPrincipal = new Rectangle(300, 0, 180, 61);
        misiondosBounds = new Rectangle(16,125,88,60);
        misiontresBounds = new Rectangle(385,191,88,60);
        misioncuatroBounds = new Rectangle(385,125,88,60);
        
        touchPoint = new Vector2();
    }
    
    /**
     * Método pause
     * Método que pause y deja de actualizar
     * 
     */
    
    @Override
    public void pause() {
        Settings.save(game.getFileIO());
    }

    /**
     * Método resume
     * Metodo que repinta el fondo o background
     * 
     *
     */
    @Override
    public void resume() {
    	bgImage = new Texture(glGame, "misiones.png");
        bgRegion = new TextureRegion(bgImage, 0, 0, 480, 320);

    }
    
    /**
     * Método dispose
     * Método que libera la memoria del background
     */

    @Override
    public void dispose() {
    	bgImage.dispose();
        
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
        for (int i = 0; i < len; i ++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                touchPoint.set(event.x, event.y);
                guiCam.touchToWorld(touchPoint);


                if (OverlapTester.pointInRectangle(regresarMenuPrincipal, touchPoint)) {
                    Recursos.playSound(Recursos.clickSound);
                    game.setScreen(new PantallaMenu(game));
                    return;
                }
                if (OverlapTester.pointInRectangle(misiondosBounds, touchPoint)) {
                    Recursos.playSound(Recursos.clickSound);
                    game.setScreen(new PantallaInfoMision(game));
                    mision=2;
                    PantallaJuego.state= PantallaJuego.GAME_RUNNING;
                   
                    return;
                }
                if (OverlapTester.pointInRectangle(misiontresBounds, touchPoint)) {
                    Recursos.playSound(Recursos.clickSound);
                    game.setScreen(new PantallaInfoMision(game));
                    mision=3;
                    PantallaJuego.state= PantallaJuego.GAME_RUNNING;
                   
                    return;
                }
                if (OverlapTester.pointInRectangle(misioncuatroBounds, touchPoint)) {
                    Recursos.playSound(Recursos.clickSound);
                    game.setScreen(new PantallaInfoMision(game));
                    mision=4;
                    PantallaJuego.state= PantallaJuego.GAME_RUNNING;
                   
                    return;
                }
                

                if (OverlapTester.pointInRectangle(misionunoBounds, touchPoint)) {
                    Recursos.playSound(Recursos.clickSound);
                    game.setScreen(new PantallaInfoMision(game));
                    mision=1;
                    PantallaJuego.state= PantallaJuego.GAME_RUNNING;
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
        batcher.beginBatch(bgImage);
            batcher.drawSprite(240, 160, 480, 320, bgRegion);
        batcher.endBatch();

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        batcher.beginBatch(Recursos.items);
            batcher.drawSprite(60, 160, 90, 185, Recursos.misionIzq);
            batcher.drawSprite(435, 160, 90, 185, Recursos.misionDer);
            batcher.drawSprite(390, 31, 180, 61, Recursos.regresarMenuPrincipal);
            batcher.drawSprite(240, 160, 120, 148, Recursos.cell);
        batcher.endBatch();

        gl.glDisable(GL10.GL_BLEND);
    }

    
}
