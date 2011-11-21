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

public class PantallaInstrucciones extends GLScreen {
    Camera2D guiCam;
    SpriteBatcher batcher;
    Texture bgIns;
    TextureRegion bgInst;
    Rectangle flechaderechaBounds;
    Rectangle flechaizquierdaBounds;
    Vector2 touchPoint;
    String instrucciones= "";
    		
    

    public PantallaInstrucciones(Game game) {
        super(game);
        guiCam = new Camera2D(glGraphics, 480, 320);
        batcher = new SpriteBatcher(glGraphics, 100);
        flechaderechaBounds= new Rectangle(435, 40, 30, 25);
        flechaizquierdaBounds= new Rectangle(15, 40 ,30 ,25);
        
        
        
        touchPoint = new Vector2();
    }
    
    @Override
    public void pause() {
        Settings.save(game.getFileIO());
    }

    @Override
    public void resume() {
    	bgIns = new Texture(glGame, "instrucciones.png");
        bgInst = new TextureRegion(bgIns, 0, 0, 480, 320);

    }

    @Override
    public void dispose() {
    	bgIns.dispose();
        
    }
    
    
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


                if (OverlapTester.pointInRectangle(flechaderechaBounds, touchPoint)&&PantallaMenu.primerjuego) {
                	
                	Recursos.playSound(Recursos.clickSound);
                    game.setScreen(new PantallaMision(game));
                    PantallaMenu.primerjuego=false;
                    return;
                    
                }
                if (OverlapTester.pointInRectangle(flechaizquierdaBounds, touchPoint)) {
                	Recursos.playSound(Recursos.clickSound);
                    game.setScreen(new PantallaMenu(game));
                    PantallaMenu.primerjuego=false;
                    return;
                    
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        GL10 gl = glGraphics.getGL();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        guiCam.setViewportAndMatrices();

        gl.glEnable(GL10.GL_TEXTURE_2D);
        batcher.beginBatch(bgIns);
            batcher.drawSprite(240, 160, 480, 320, bgInst);
        batcher.endBatch();

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        batcher.beginBatch(Recursos.items);
        	if(PantallaMenu.primerjuego)
        	batcher.drawSprite(450, 50, 30, 25, Recursos.flechaderecha);
        	batcher.drawSprite(30, 50, 30, 25, Recursos.flechaizquierda);
        batcher.endBatch();

        gl.glDisable(GL10.GL_BLEND);
    }

    
}
