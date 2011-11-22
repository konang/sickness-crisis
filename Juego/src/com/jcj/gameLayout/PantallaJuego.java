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




import java.util.List;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;


/**
 * Clase PantallaInstrucciones extiende a la clase GLScreen
 * Clase donde se crea la pantalla para jugar donde se manejan las acciones del juego 
 *
 * @author Bacon Rocket Studios basado en el libro Beginning Android Games de Mario Zechner
 */

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
        boton1Bounds = new Rectangle(359,5,47,45 );
        boton2Bounds = new Rectangle(420,5,47,45 );
        izquierdaBounds = new Rectangle(3 , 1, 49, 38);
        derechaBounds = new Rectangle(78, 1, 49, 38);
        touchPoint = new Vector2();
        worldListener = new MundoListener() {
            
        };
        world = new Mundo(worldListener);
        renderer = new RenderMundo(glGraphics, batcher, world);
        lastScore = 0;
        scoreString = "Puntaje: 0";
    }
    
    
    /**
     * Método update
     * Metodo que actualiza y verifica el estado del juego
     * 
     * @param float deltaTime indica el tiempo transcurrido del sistema desde la
     * última vez que se actualizo
     *
     */
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
    
    /**
     * Método updateLevelEnd
     * Metodo que verifica que se acabo el nivel por haber vencido al jefe y salva el puntaje
     *
     *
     */
    public void updateLevelEnd(){
    	Settings.addScore(lastScore * world.John.vidas);
        Settings.save(game.getFileIO());
    	game.setScreen(new PantallaGanar(game));
    }
    
    /**
     * Método updateGameOver
     * Metodo que verifica que se acabo el nivel por haber perdido y salva el puntaje
     *
     *
     */
    
    public void updateGameOver(){
    	  for (int i = 0; i < world.Gripe.size(); i++) {
              Villano malo = world.Gripe.get(i);
              malo.choque = true;
    	  
    	  }
    	  Settings.addScore(lastScore * world.John.vidas);
          Settings.save(game.getFileIO());
    	  game.setScreen(new PantallaPerder(game));
    	world.score = 0;    //Reinicializa el score
    	return;
    }
    
    /**
     * Método updatePaused
     * Metodo detiene las animaciones y para el juego para que el usuario pueda hacer
     * algo mas 
     *
     *
     */
    
    
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
                    if (Settings.soundEnabled)
                        Recursos.music.play();
                    else
                        Recursos.music.pause();
                   
                    state=GAME_RUNNING;
          
                	}
                }
         }
        
        	
    }
    
    /**
     * Método updateRunning
     * Metodo que actualiza las acciones realizadas en la pantalla
     * 
     * @param float deltaTime indica el tiempo transcurrido del sistema desde la
     * última vez que se actualizo
     *
     */
    	
    public void updateRunning(float deltaTime){
    	List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        int len = touchEvents.size();
        Random rand= new Random();
        if(deltaTime>rand.nextFloat()*world.Gripe.size()&&world.Gripe.size()<10){
            Villano villanonuevo = new Villano((world.ANCHO_MUNDO)+((int)(Math.random() * 480*2) + 50), world.ALTO_MUNDO/4);
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
                    Recursos.music.pause();
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
        
        if(world.John.vidas<2){
        	state=GAME_OVER;
        }
        
        if(world.gameOver){
        	state=GAME_LEVEL_END;
        }
        
        world.update(deltaTime);
        if (world.score != lastScore) {
            lastScore = world.score;    //Modifique para que el last score se mantenga a traves de las jugadas. Tambien le puse el 10 para que no haya colision de scores.
            scoreString = "" + "Puntaje: " + lastScore;  //Checar porque no estoy seguro que se mantenga 
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
    	String pausa = "pausa";
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
            batcher.drawSprite(30, 30, 49, 32, Recursos.dpadizquierda);   // Agrego 5 a ambas partes
            batcher.drawSprite(105, 30, 49, 32, Recursos.dpadderecha);
            batcher.drawSprite(415, 34, 120, 45, Recursos.botones);
            for(int i = 0; i<world.John.vidas; i++){
            	batcher.drawSprite(250+14*i, 310, 19, 20, Recursos.vidaJohn);
            }
            if(world.jefeYa){
            	switch (PantallaMision.mision){
            	case 1 : 
            		for(int i = 0; i<world.gripeJ.vidas; i++){
            			batcher.drawSprite(240+14*i, 270, 14, 15, Recursos.vidaGripe);
            		}
            		break;
            	case 2 : 
            		for(int i = 0; i<world.gripeJ.vidas; i++){
            			batcher.drawSprite(240+14*i, 270, 14, 15, Recursos.vidaGastritis);
            		}
            		break;
            	case 3 : 
            		for(int i = 0; i<world.gripeJ.vidas; i++){
            			batcher.drawSprite(240+14*i, 270, 14, 15, Recursos.vidaVaricela);
            		}
            		break;
            	case 4 : 
            		for(int i = 0; i<world.gripeJ.vidas; i++){
            			batcher.drawSprite(240+14*i, 270, 14, 15, Recursos.vidaRota);
            		}
            		break;
            	}
            }
            float anchoScore = Recursos.font.glyphWidth * scoreString.length();
            Recursos.font.drawText(batcher, scoreString, 120 - anchoScore/2, 295);
            float anchoPausa = Recursos.font.glyphWidth * pausa.length();
            if(state == GAME_PAUSED){
            	Recursos.font.drawText(batcher, pausa, world.ANCHO_MUNDO/2 - anchoPausa/2, world.ALTO_MUNDO/2);
            }
        batcher.endBatch();

        gl.glDisable(GL10.GL_BLEND);
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

    }
    
    
    /**
     * Método dispose
     * Método que libera la memoria del background
     */

    @Override
    public void dispose() {
        
    }
}
