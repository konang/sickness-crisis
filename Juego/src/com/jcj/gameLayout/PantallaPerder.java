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
 * Clase PantallaPerder extiende a la clase GLScreen
 * Clase donde se crea la pantalla en la que se muestra cuando el usuario pierde
 * 
 *
 * @author Bacon Rocket Studios basado en el libro Beginning Android Games de Mario Zechner
 */
public class PantallaPerder extends GLScreen {
    Camera2D guiCam;
    SpriteBatcher batcher;
    Texture bgImage;
    TextureRegion bgRegion;
    Rectangle otraMision;
    Rectangle volverAIntentar;
    Vector2 touchPoint;

    public PantallaPerder(Game game) {
        super(game);
        guiCam = new Camera2D(glGraphics, 480, 320);
        batcher = new SpriteBatcher(glGraphics, 100);
        otraMision= new Rectangle(56, 150, 138, 26);
        volverAIntentar=  new Rectangle(308, 150 ,138 ,26);

        
        
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
    	bgImage = new Texture(glGame, "perdiste.png");
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


                if (OverlapTester.pointInRectangle(volverAIntentar, touchPoint)) {
                	
                	Recursos.playSound(Recursos.clickSound);
                    game.setScreen(new PantallaJuego(game));
                    PantallaJuego.state= PantallaJuego.GAME_RUNNING;
                    return;
                    
                }
                if (OverlapTester.pointInRectangle(otraMision, touchPoint)) {
                	Recursos.playSound(Recursos.clickSound);
                    game.setScreen(new PantallaMision(game));
              
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

        

        gl.glDisable(GL10.GL_BLEND);
    }

    
}
