package com.jcj.gameLayout;

import com.jcj.framework.Game;
import com.jcj.framework.Input.TouchEvent;
import com.jcj.framework.gl.Camera2D;
import com.jcj.framework.gl.SpriteBatcher;
import com.jcj.framework.impl.GLScreen;
import com.jcj.framework.math.OverlapTester;
import com.jcj.framework.math.Rectangle;
import com.jcj.framework.math.Vector2;
import com.jcj.gameLayout.Mundo;
import com.jcj.gameLayout.RenderMundo;
import com.jcj.gameLayout.Mundo.MundoListener;
import com.jcj.jumper.Assets;



import java.util.List;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;



public class PantallaJuego extends GLScreen {
	
	static final int GAME_READY = 0;
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;
    static final int GAME_LEVEL_END = 3;
    static final int GAME_OVER = 4;
    Camera2D guiCam;
    SpriteBatcher batcher;
    Rectangle soundBounds;
    Rectangle boton1Bounds;
    Rectangle boton2Bounds;
    Rectangle pauseresumeBounds;
    Rectangle izquierdaBounds;
    Rectangle derechaBounds;
    Vector2 touchPoint;
    Mundo world;
    MundoListener worldListener;
    RenderMundo renderer;
    public static boolean pauseresume=false;
    int lastScore;
    String scoreString;
    public int balas=0;
    public static int state=GAME_RUNNING;
    

    public PantallaJuego(Game game) {
        super(game);
        guiCam = new Camera2D(glGraphics, 480, 320);
        batcher = new SpriteBatcher(glGraphics, 1000);
        soundBounds = new Rectangle(390, 282, 40, 35);
        pauseresumeBounds = new Rectangle(441 , 282, 37, 36);
        boton1Bounds = new Rectangle(359,5,42,40 );
        boton2Bounds = new Rectangle(420,5,42,40 );
        izquierdaBounds = new Rectangle(3 , 1, 44, 27);
        derechaBounds = new Rectangle(78, 1, 44, 27);
        touchPoint = new Vector2();
        worldListener = new MundoListener() {
            
        };
        world = new Mundo(worldListener);
        renderer = new RenderMundo(glGraphics, batcher, world);
        lastScore = 0;
        scoreString = "score: 0";
    }
    
    @Override
    public void update(float deltaTime) {
    	if (deltaTime > 0.1f)
            deltaTime = 0.1f;
    	
    	
    	switch(state) {
        case GAME_RUNNING:
            updateRunning(deltaTime);
            break;
        case GAME_PAUSED:
            updatePaused();
            break;
        case GAME_LEVEL_END:
            updateLevelEnd();
            break;
        case GAME_OVER:
            updateGameOver();
            break;
    	}
    }
    
    public void updateLevelEnd(){
    	game.setScreen(new PantallaGanar(game));
    }
    
    public void updateGameOver(){
    	  for (int i = 0; i < world.Gripe.size(); i++) {
              Villano malo = world.Gripe.get(i);
              malo.choque = true;
    	  
    	  }
    	game.setScreen(new PantallaPerder(game));
    	world.score = 0;    //Reinicializa el score
    	return;
    }
    
    public void updatePaused(){
    	List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        int len = touchEvents.size();
        
        for (int i = 0; i < len; i ++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_DOWN) {
                touchPoint.set(event.x, event.y);
                guiCam.touchToWorld(touchPoint);
                
                if (OverlapTester.pointInRectangle(pauseresumeBounds, touchPoint)) {
                    pauseresume=!pauseresume;
                    state=GAME_RUNNING;
          
                	}
                }
         }
    	
    }
    	
    public void updateRunning(float deltaTime){
    	List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        int len = touchEvents.size();
        Random rand= new Random();
        if(deltaTime>rand.nextFloat()*world.Gripe.size()&&world.Gripe.size()<10){
            Villano villanonuevo = new Villano((world.ANCHO_MUNDO)+((int)(Math.random() * 480) + 50), world.ALTO_MUNDO/4);
            world.Gripe.add(villanonuevo);
            Recursos.playSound(Recursos.clickSound);
        }
        
        for (int i = 0; i < len; i ++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_DOWN) {
                touchPoint.set(event.x, event.y);
                guiCam.touchToWorld(touchPoint);
                
                if (OverlapTester.pointInRectangle(boton1Bounds, touchPoint)&&world.Mejoral.size()<4) {
                	Recursos.playSound(Recursos.balazo);
                	
                	if(world.John.estado == Heroe.ESTADO_DERECHA|| world.John.estado == Heroe.SALTO_HEROEDER){ 
            		//CREA LA BALA BASANDOSE EN DONDE VOLTEA EL MONO
            		Balas balanueva = new Balas( world.John.position.x+30, world.John.position.y-10);
            		balanueva.estado = Balas.ESTADO_DERECHA;
            		balanueva.velocidad = 7f;
                	//CAMBIA EL ESTADO DE LA BALA BASADO EN LA DIRECCION DE JOHN
            		
            		world.Mejoral.add(balanueva);
            		//agrega la bala a la lista encadenada de balas
                    return;
            	}
            	if(world.John.estado == Heroe.ESTADO_IZQUIERDA||world.John.estado == Heroe.SALTO_HEROEIZQ){ 
            		//CREA LA BALA BASANDOSE EN DONDE VOLTEA EL MONO
            		Balas balanueva = new Balas( world.John.position.x, world.John.position.y-10); 
                	balanueva.estado = Balas.ESTADO_IZQUIERDA;
                	balanueva.velocidad = -7f;
                	//CAMBIE EL ESTADO DE LA BALA BASADO EN LA DIRECCION DE JOHN
                	
            		world.Mejoral.add(balanueva);
            		//agrega la bala a la lista encadenada de balas
                    return;
            	}
				

                }
                
                if (OverlapTester.pointInRectangle(boton2Bounds, touchPoint)){
                	if(world.John.estado==Heroe.ESTADO_DERECHA)
                    	world.John.estado=Heroe.SALTO_HEROEDER;
                	else if(world.John.estado==Heroe.ESTADO_IZQUIERDA)
                    	world.John.estado=Heroe.SALTO_HEROEIZQ;
                }
                
                
                if (OverlapTester.pointInRectangle(pauseresumeBounds, touchPoint)) {
                    pauseresume=!pauseresume;
                    state=GAME_PAUSED;
                    Recursos.playSound(Recursos.clickSound);
                }

                if (OverlapTester.pointInRectangle(derechaBounds, touchPoint)) {
                    world.John.velocidadx=5.0f;
                    if((world.John.estado ==Heroe.SALTO_HEROEDER)||( world.John.estado== Heroe.SALTO_HEROEIZQ)){
                    	world.John.estado = Heroe.SALTO_HEROEDER;
                    	}
                    else{
                      	world.John.estado=Heroe.ESTADO_DERECHA;
                    }
                    return;
                }

                if (OverlapTester.pointInRectangle(izquierdaBounds, touchPoint)) {
                	world.John.velocidadx=-5.0f;
                    if((world.John.estado ==Heroe.SALTO_HEROEDER)||( world.John.estado== Heroe.SALTO_HEROEIZQ)){
                	world.John.estado = Heroe.SALTO_HEROEIZQ;
                	}
                    else{
                    	world.John.estado=Heroe.ESTADO_IZQUIERDA;
                    }
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
            
            if (event.type == TouchEvent.TOUCH_UP) {
                touchPoint.set(event.x, event.y);
                guiCam.touchToWorld(touchPoint);
                world.John.velocidadx=0;
                return;
                
            }
            
        }
        
        if(world.John.vidas<0){
        	state=GAME_OVER;
        }
        
        if(world.gameOver){
        	state=GAME_LEVEL_END;
        }
        
        world.update(deltaTime);
        if (world.score != lastScore) {
            lastScore = world.score + 10;    //Modifique para que el last score se mantenga a traves de las jugadas. Tambien le puse el 10 para que no haya colision de scores.
            scoreString = "" + "Score: " + lastScore;
        }
      }

    @Override
    public void present(float deltaTime) {
        GL10 gl = glGraphics.getGL();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        
        renderer.render();
        

        guiCam.setViewportAndMatrices();
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        batcher.beginBatch(Recursos.items);
            batcher.drawSprite(460, 300, 37, 36, pauseresume? Recursos.resume : Recursos.pause);
            batcher.drawSprite(410, 300, 40, 35, Settings.soundEnabled ? Recursos.soundOn : Recursos.soundOff);
            batcher.drawSprite(25, 15, 44, 27, Recursos.dpadizquierda);
            batcher.drawSprite(100, 15, 44, 27, Recursos.dpadderecha);
            batcher.drawSprite(420, 25, 120, 40, Recursos.botones);
            for(int i = 0; i<world.John.vidas; i++){
            	batcher.drawSprite(250+14*i, 290, 14, 15, Recursos.vidaJohn);
            }
            
            float anchoScore = Recursos.font.glyphWidth * scoreString.length();
            Recursos.font.drawText(batcher, scoreString, 120 - anchoScore/2, 295);
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
