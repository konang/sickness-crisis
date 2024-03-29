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
 * Clase PantallaInstrucciones extiende a la clase GLScreen
 * Clase donde se crea la pantalla en la que se muestra el logo de los creadores
 *
 * @author Bacon Rocket Studios basado en el libro Beginning Android Games de Mario Zechner
 */
public class PantallaLogo extends GLScreen {
    Camera2D guiCam;
    SpriteBatcher batcher;
    Texture bgImage;
    TextureRegion bgRegion;
    int contador;
    Vector2 touchPoint;

    public PantallaLogo(Game game) {
        super(game);
        guiCam = new Camera2D(glGraphics, 480, 320);
        batcher = new SpriteBatcher(glGraphics, 100);
        
       

        
        
        touchPoint = new Vector2();
    }
    
    /**
     * M�todo pause
     * M�todo que pause y deja de actualizar
     * 
     */
    @Override
    public void pause() {
        Settings.save(game.getFileIO());
    }

    /**
     * M�todo resume
     * Metodo que repinta el fondo o background
     * 
     *
     */
    @Override
    public void resume() {
    	bgImage = new Texture(glGame, "bglogo.png");    //aqui logo
        bgRegion = new TextureRegion(bgImage, 0, 0, 480, 320);

    }

    /**
     * M�todo dispose
     * M�todo que libera la memoria del background
     */

    @Override
    public void dispose() {
    	bgImage.dispose();
        
    }
    
    /**
     * M�todo update
     * Metodo que actualiza las acciones realizadas en la pantalla
     * 
     * @param float deltaTime indica el tiempo transcurrido del sistema desde la
     * �ltima vez que se actualizo
     *
     */
    
    @Override
    public void update(float deltaTime) {
    	
      if(contador >= 200){
    	  game.setScreen(new PantallaMenu(game));
      }
    	  contador++;
    }
    
    /**
     * M�todo present
     * M�todo que maneja el batcher y la camara, donde se pintan las texturas
     * 
     * @param float deltaTime indica el tiempo transcurrido del sistema desde la
     * �ltima vez que se actualizo
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
