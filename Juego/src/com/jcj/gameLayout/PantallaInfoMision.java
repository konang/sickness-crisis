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
 * Clase PantallaHighscores extiende a la clase GLScreen
 * Clase donde se crea la pantalla para visualizar la informacion de cada misión
 *
 * @author Bacon Rocket Studios basado en el libro Beginning Android Games de Mario Zechner
 */
public class PantallaInfoMision extends GLScreen {
    Camera2D guiCam;
    SpriteBatcher batcher;
    Texture instruccionesImage;
    TextureRegion instruccionesRegion;
    Rectangle flechaderechaBounds;
    Rectangle flechaizquierdaBounds;
    Vector2 touchPoint;
    Texture bgImage;
    TextureRegion bgRegion;
    Texture bgImage2;
    TextureRegion bgRegion2;
    Texture bgImage3;
    TextureRegion bgRegion3;
    Texture bgImage4;
    TextureRegion bgRegion4;

    public PantallaInfoMision(Game game) {
        super(game);
        guiCam = new Camera2D(glGraphics, 480, 320);
        batcher = new SpriteBatcher(glGraphics, 100);
        flechaderechaBounds= new Rectangle(435, 0, 85, 320);
        flechaizquierdaBounds= new Rectangle(15, 0 ,85 ,320);
        
        
        
        touchPoint = new Vector2();
    }
    
    /**
     * Método resume
     * Metodo que repinta el fondo o background
     * 
     *
     */
    
    @Override
    public void resume() {
        bgImage = new Texture(glGame, "mision1.png");
        bgRegion = new TextureRegion(bgImage, 0, 0, 480, 320);

        bgImage2 = new Texture(glGame, "mision2.png");
        bgRegion2 = new TextureRegion(bgImage2, 0, 0, 480, 320);
        	
        bgImage3 = new Texture(glGame, "mision3.png");
        bgRegion3 = new TextureRegion(bgImage3, 0, 0, 480, 320);
  
        bgImage4 = new Texture(glGame, "mision4.png");
        bgRegion4 = new TextureRegion(bgImage4, 0, 0, 480, 320);

    }

    
    /**
     * Método dispose
     * Método que libera la memoria del background
     * 
     *
     */
    @Override
    public void dispose() {
    	bgImage.dispose();
        
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


                if (OverlapTester.pointInRectangle(flechaderechaBounds, touchPoint)) {
                	
                	Recursos.playSound(Recursos.clickSound);
                    game.setScreen(new PantallaJuego(game));
                    return;
                    
                }
                if (OverlapTester.pointInRectangle(flechaizquierdaBounds, touchPoint)) {
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
        switch (PantallaMision.mision){
        case 1 : 
        	batcher.beginBatch(bgImage);
        		batcher.drawSprite(240, 160, 480, 320, bgRegion);
        	batcher.endBatch();
        	break;
        case 2 : 
        	batcher.beginBatch(bgImage2);
    			batcher.drawSprite(240, 160, 480, 320, bgRegion2);
    		batcher.endBatch();
        	break;	
        case 3 : 
        	batcher.beginBatch(bgImage3);
    			batcher.drawSprite(240, 160, 480, 320, bgRegion3);
    		batcher.endBatch();
        	break;
        case 4 : 
        	batcher.beginBatch(bgImage4);
        		batcher.drawSprite(240, 160, 480, 320, bgRegion4);
        	batcher.endBatch();
        	break;
        }

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        batcher.beginBatch(Recursos.items);
        	batcher.drawSprite(450, 50, 30, 19, Recursos.flechaderecha);
        	batcher.drawSprite(30, 50, 30, 19, Recursos.flechaizquierda);
        batcher.endBatch();

        gl.glDisable(GL10.GL_BLEND);
    }

    
}
