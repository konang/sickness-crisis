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

public class PantallaGanar extends GLScreen {
    Camera2D guiCam;
    SpriteBatcher batcher;
    Texture bgImage;
    TextureRegion bgRegion;
    Rectangle otraMision;
    static String vidas2;
    static String score2;
    Vector2 touchPoint;
    static String score3;

    public PantallaGanar(Game game) {
        super(game);
        guiCam = new Camera2D(glGraphics, 480, 320);
        batcher = new SpriteBatcher(glGraphics, 100);
        otraMision= new Rectangle(237, 130 ,195 ,67);
       

        
        
        touchPoint = new Vector2();
    }
    
    @Override
    public void pause() {
        Settings.save(game.getFileIO());
    }

    @Override
    public void resume() {
    	bgImage = new Texture(glGame, "ganaste.png");
        bgRegion = new TextureRegion(bgImage, 0, 0, 480, 320);

    }

    @Override
    public void dispose() {
    	bgImage.dispose();
        
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


                
                if (OverlapTester.pointInRectangle(otraMision, touchPoint)) {
                	Recursos.playSound(Recursos.clickSound);
                    game.setScreen(new PantallaMision(game));
              
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
        batcher.beginBatch(bgImage);
            batcher.drawSprite(240, 160, 480, 320, bgRegion);
        batcher.endBatch();
        batcher.beginBatch(Recursos.items);
        	float anchoScore2 = Recursos.font.glyphWidth * score2.length();
            Recursos.font.drawText(batcher, score2, 330 + anchoScore2/2, 298);
            float anchoScore3 = Recursos.font.glyphWidth * score3.length();
            Recursos.font.drawText(batcher, score3, 330 + anchoScore3/2, 240);
            float anchoVidas2 = Recursos.font.glyphWidth * vidas2.length();
            Recursos.font.drawText(batcher, vidas2, 405 + anchoVidas2/2, 270);
         batcher.endBatch();
        

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        

        gl.glDisable(GL10.GL_BLEND);
    }

    
}
