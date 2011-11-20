package com.jcj.gameLayout;

import com.jcj.framework.Game;
import com.jcj.framework.Input.TouchEvent;
import com.jcj.framework.gl.Camera2D;
import com.jcj.framework.gl.SpriteBatcher;
import com.jcj.framework.impl.GLScreen;
import com.jcj.framework.math.OverlapTester;
import com.jcj.framework.math.Rectangle;
import com.jcj.framework.math.Vector2;
import java.util.List;
import javax.microedition.khronos.opengles.GL10;



public class PantallaMenu extends GLScreen {
    Camera2D guiCam;
    SpriteBatcher batcher;
    Rectangle soundBounds;
    Rectangle jugarBounds;
    Rectangle highscoresBounds;
    Rectangle instruccionesBounds;
    Vector2 touchPoint;
    public static boolean primerjuego=false;

    public PantallaMenu(Game game) {
        super(game);
        guiCam = new Camera2D(glGraphics, 480, 320);
        batcher = new SpriteBatcher(glGraphics, 100);
        soundBounds = new Rectangle(0, 0, 64, 64);
        jugarBounds = new Rectangle(192 , 110 + 17, 191, 35);
        highscoresBounds = new Rectangle(192 , 110 - 17, 191, 35);
        instruccionesBounds = new Rectangle(192, 110 - 17 - 35, 191, 35);
        touchPoint = new Vector2();
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

                if (OverlapTester.pointInRectangle(jugarBounds, touchPoint)) {
                    Recursos.playSound(Recursos.clickSound);
                    game.setScreen(new PantallaInstrucciones(game));
                    primerjuego=true;
                    return;
                    
                }

                if (OverlapTester.pointInRectangle(highscoresBounds, touchPoint)) {
                    Recursos.playSound(Recursos.clickSound);
                    game.setScreen(new PantallaHighscores(game));
                    return;
                }

                if (OverlapTester.pointInRectangle(instruccionesBounds, touchPoint)) {
                    Recursos.playSound(Recursos.clickSound);
                    game.setScreen(new PantallaInstrucciones(game));
                    return;
                }

                if (OverlapTester.pointInRectangle(soundBounds, touchPoint)) {
                    Recursos.playSound(Recursos.clickSound);
                    Settings.soundEnabled = !Settings.soundEnabled;
                    if (Settings.soundEnabled)
                        Recursos.music.play();
                    else
                        Recursos.music.pause();
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
        batcher.beginBatch(Recursos.background);
            batcher.drawSprite(240, 160, 480, 320, Recursos.backgroundRegion);
        batcher.endBatch();

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        batcher.beginBatch(Recursos.items);
            batcher.drawSprite(240, 320 - 10 - 71, 275, 105, Recursos.logo);
            batcher.drawSprite(240, 110, 96, 106, Recursos.mainMenu);
            batcher.drawSprite(20, 17, 40, 35, Settings.soundEnabled ? Recursos.soundOn : Recursos.soundOff);
            batcher.drawSprite(410, 100, 60, 74, Recursos.cell);
        batcher.endBatch();

        gl.glDisable(GL10.GL_BLEND);
    }

    @Override
    public void pause() {
        Settings.save(game.getFileIO());
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        
    }
}
