package com.jcj.gameLayout;

import com.jcj.framework.gl.Animation;
import com.jcj.framework.gl.Camera2D;
import com.jcj.framework.gl.SpriteBatcher;
import com.jcj.framework.gl.TextureRegion;
import com.jcj.framework.impl.GLGraphics;

import javax.microedition.khronos.opengles.GL10;

/**
 * Clase RenderMundo
 * En esta clase nos encargamos de dibujar en pantalla el mundo de juego y sus
 * elementos.
 * 
 * @author jugandoconjava basado en el libro Beginning Android Games de Mario Zechner
 */
public class RenderMundo {
    
    /*
     * Camera2D
     * Los objetos de la clase Camera2D representan a una cámara virtual que nos
     * permite ver en pantalla los elementos de alguna pantalla de menú y el mundo
     * de juego. Dicha cámara puede ser fija o móvil de tal manera que pueda ser
     * desplazada por el mundo de juego acompañando al jugador.
     *
     * Método constructor
     * new Camera2D(GLGraphics glGraphics, float ancho, float alto);
     * Ejemplo: Camera2D camara = new Camera2D(glGraphics, 320, 480);
     *
     * Donde glGraphics es el contexto gráfico con el cual trabaja la cámara, los
     * valores enteros 320 y 480 corresponden al ancho y alto de la vista de la cámara. La
     * posición de la cámara estará definida en el centro en el centro de la misma y
     * es calculado de manera automática en base a las dimensiones de la vista (En este
     * ejemplo la posición de la cámara esta en el punto 160, 240). Los valores del
     * ancho y alto pueden ser dados en pixeles o en base a las unidades del mundo
     * de juego.
     */

    /*
     * SpriteBatcher
     * Un objeto de la clase SpriteBatcher nos permite dibujar uno o más elementos
     * gráficos en la pantalla haciendo una sola llamada al procesador gráfico del
     * dispositivo lo cual nos ayuda a optimizar recursos de memoria. La única limitante
     * de un SpriteBatcher es que solo puede trabajar con una sola textura a la vez por
     * lo cual por cada textura de la cual vayamos a dibujar es necesario hacer un nuevo
     * llamado al SpriteBatcher.
     *
     * Método constructor
     * new SpriteBatcher(GLGraphics glGraphics, int maxSprites);
     * Ejemplo: SpriteBatcher batcher = new SpriteBatcher(glGraphics, 100);
     *
     * Donde glGraphics es el contexto gráfico con el cual trabaja el SpriteBatcher y
     * el valor entero 20 es el número máximo de Sprites que podrá dibujar a la vez el
     * SpriteBatcher.
     */

    /*
     * En esta sección definimos los miembros que nos ayudarán a desplegar al mundo
     * de juego y sus elementos. Definimos también las dimensiones de la vista en base
     * a las unidades del mundo de juego
     */

    static final float ANCHO_VISTA = 480;
    static final float ALTO_VISTA = 320;
    GLGraphics glGraphics;
    Camera2D camara;
    SpriteBatcher batcher;
    Mundo mundo;

    /*
     * Método constructor de la clase RenderMundo.
     * En este método se crean los miembros que nos ayudarán a desplegar el mundo
     * de juego.
     */
    public RenderMundo(GLGraphics glGraphics, SpriteBatcher batcher, Mundo mundo) {
        /*
         * En esta sección creamos los elementos que nos ayudarán con el despliegue
         * del mundo de juego
         */
        this.glGraphics = glGraphics;
        this.mundo = mundo;
        this.camara = new Camera2D(glGraphics, ANCHO_VISTA, ALTO_VISTA);
        this.batcher = batcher;
    }

    public void render() {
        /*
         * En esta sección actualizamos la posición de la cámara del mundo y
         * desplegamos el mundo y sus elementos.
         */
         //Crea un objeto GL10 para trabajar con OpenGL ES.
        GL10 gl =glGraphics.getGL();
        //Actualiza la posición de la cámara(vista) en base a la posición del personaje.
        // Establece la vista.
        camara.setViewportAndMatrices();

        // Inicia un batch para dibujar el fondo
            switch (PantallaMision.mision){
            case 1 : 
            	batcher.beginBatch(Recursos.background);
            		batcher.drawSprite(camara.position.x, camara.position.y, ANCHO_VISTA, ALTO_VISTA, Recursos.backgroundRegion);
            	batcher.endBatch();
            		break;
            case 2 : 
            	batcher.beginBatch(Recursos.background2);
            		batcher.drawSprite(camara.position.x, camara.position.y, ANCHO_VISTA, ALTO_VISTA, Recursos.backgroundRegion2);
            	batcher.endBatch();
            	break;	
            case 3 : 
            	batcher.beginBatch(Recursos.background3);
            		batcher.drawSprite(camara.position.x, camara.position.y, ANCHO_VISTA, ALTO_VISTA, Recursos.backgroundRegion3);
            	batcher.endBatch();
            	break;
            }

        // Habilita el modo de fusión.
        gl.glEnable(GL10.GL_BLEND);
        //Establece el modo de fusión
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        // Inicia un batch para dibujar la animación del personaje.
        batcher.beginBatch(Recursos.items);
        	renderJohn();
           	renderGripe();
           	renderBala1();
           	renderLluvia();
           	if(mundo.jefeYa){
           		renderGripeJ();
           		renderBalaGripe();
           	}
        batcher.endBatch();
        gl.glDisable(GL10.GL_BLEND);

    }
    
    public void renderJohn(){
    	TextureRegion keyFrame;
    	switch (mundo.John.estado){
    		case Heroe.ESTADO_DERECHA :
    		//   Obtiene el cuadro actual de la animación
    			keyFrame = Recursos.johnDerecha.getKeyFrame(mundo.John.tiempoEstado, Animation.ANIMATION_LOOPING);
    		//    Dibuja el cuadro de la animación
    			batcher.drawSprite(mundo.John.position.x, mundo.John.position.y, 61, 53, keyFrame);
    			break;
    		case Heroe.ESTADO_IZQUIERDA :
    		//   Obtiene el cuadro actual de la animación
                keyFrame = Recursos.johnIzquierda.getKeyFrame(mundo.John.tiempoEstado, Animation.ANIMATION_LOOPING);
            //    Dibuja el cuadro de la animación
                batcher.drawSprite(mundo.John.position.x, mundo.John.position.y, 61, 53, keyFrame);
                break;
    		case Heroe.SALTO_HEROEDER :
        		//   Obtiene el cuadro actual de la animación
        			keyFrame = Recursos.johnDerecha.getKeyFrame(mundo.John.tiempoEstado, Animation.ANIMATION_LOOPING);
        		//    Dibuja el cuadro de la animación
        			batcher.drawSprite(mundo.John.position.x, mundo.John.position.y, 61, 53, keyFrame);
        			break;
        		case Heroe.SALTO_HEROEIZQ :
        		//   Obtiene el cuadro actual de la animación
                    keyFrame = Recursos.johnIzquierda.getKeyFrame(mundo.John.tiempoEstado, Animation.ANIMATION_LOOPING);
                //    Dibuja el cuadro de la animación
                    batcher.drawSprite(mundo.John.position.x, mundo.John.position.y, 61, 53, keyFrame);
                    break;
    	}


    }
    
    public void renderBalaGripe(){
    	TextureRegion keyFrame;
		//   Obtiene el cuadro actual de la animación
		keyFrame = Recursos.balaGripe.getKeyFrame(mundo.BalaGripe.tiempoEstado, Animation.ANIMATION_LOOPING);
		//    Dibuja el cuadro de la animación
		batcher.drawSprite(mundo.BalaGripe.position.x, mundo.BalaGripe.position.y, 34, 34, keyFrame);
    }
    
    public void renderBala1(){ 			//Renderea las balas del tipo 1
    	TextureRegion keyFrame;
    	int len = mundo.Mejoral.size();
        for (int i = 0; i < len; i++) {
        	Balas bala = mundo.Mejoral.get(i);
        	switch (bala.estado){
    		case Balas.ESTADO_DERECHA :
    		//   Obtiene el cuadro actual de la animación
    			keyFrame = Recursos.balaDerecha.getKeyFrame(mundo.John.tiempoEstado, Animation.ANIMATION_LOOPING);
    		//    Dibuja el cuadro de la animación
    			batcher.drawSprite(bala.position.x, bala.position.y, 11, 11, keyFrame);
    			break;
    		case Balas.ESTADO_IZQUIERDA :
        		//   Obtiene el cuadro actual de la animación
        			keyFrame = Recursos.balaDerecha.getKeyFrame(mundo.John.tiempoEstado, Animation.ANIMATION_LOOPING);
        		//    Dibuja el cuadro de la animación
        			batcher.drawSprite(bala.position.x, bala.position.y, 11, 11, keyFrame);
        			break;
    	}

        }
    }
    
    public void renderGripe(){ 			//Renderea al malo del nivel 1 gripe

    	TextureRegion keyFrame;
    	if(mundo.prueba0.estado==Villano.ESTADO_DERECHA){
    		keyFrame = Recursos.villanoDerecha.getKeyFrame(mundo.prueba0.tiempoEstado, Animation.ANIMATION_LOOPING);
    		mundo.prueba0.velocidad = -mundo.prueba0.velocidad;
    		batcher.drawSprite(mundo.prueba0.position.x, mundo.prueba0.position.y, 32, 35, keyFrame);
    	}
    	else{
        	keyFrame = Recursos.villanoIzquierda.getKeyFrame(mundo.prueba0.tiempoEstado, Animation.ANIMATION_LOOPING);
        	mundo.prueba0.velocidad = -mundo.prueba0.velocidad;
        	batcher.drawSprite(mundo.prueba0.position.x, mundo.prueba0.position.y, 32, 35, keyFrame);
        }
    	
    	
    	
    	
    	int len = mundo.Gripe.size();
        for (int i = 0; i < len; i++) {
            Villano malo = mundo.Gripe.get(i);
            switch (malo.estado){
    			case Villano.ESTADO_DERECHA :
    				//   Obtiene el cuadro actual de la animación
    				keyFrame = Recursos.villanoDerecha.getKeyFrame(malo.tiempoEstado, Animation.ANIMATION_LOOPING);
    				
    				//    Dibuja el cuadro de la animación
    				batcher.drawSprite(malo.position.x, malo.position.y, 32, 35, keyFrame);
    			break;
    			case Villano.ESTADO_IZQUIERDA :
    				//   Obtiene el cuadro actual de la animación
    				keyFrame = Recursos.villanoIzquierda.getKeyFrame(malo.tiempoEstado, Animation.ANIMATION_LOOPING);
    				//    Dibuja el cuadro de la animación
    				batcher.drawSprite(malo.position.x, malo.position.y, 32, 35, keyFrame);
                break;
    			case Villano.SALTO_VILLANOIZQ :
    				//   Obtiene el cuadro actual de la animación
    				keyFrame = Recursos.villanoIzquierda.getKeyFrame(malo.tiempoEstado, Animation.ANIMATION_LOOPING);
    				//    Dibuja el cuadro de la animación
    				batcher.drawSprite(malo.position.x, malo.position.y, 32, 35, keyFrame);
                break;
    			case Villano.SALTO_VILLANODER :
    				//   Obtiene el cuadro actual de la animación
    				keyFrame = Recursos.villanoDerecha.getKeyFrame(malo.tiempoEstado, Animation.ANIMATION_LOOPING);
    				//    Dibuja el cuadro de la animación
    				batcher.drawSprite(malo.position.x, malo.position.y, 32, 35, keyFrame);
                break;
                
            }
                  
        }
    
    }
    public void renderGripeJ(){  //Renderea al Jefe
    	TextureRegion keyFrame;
    	switch(PantallaMision.mision){
    	case 1 : 
    		keyFrame = Recursos.gripeJDerecha.getKeyFrame(mundo.gripeJ.tiempoEstado, Animation.ANIMATION_LOOPING);
        	mundo.gripeJ.velocidad = -mundo.gripeJ.velocidad;
        	batcher.drawSprite(mundo.gripeJ.position.x, mundo.gripeJ.position.y, 166, 130, keyFrame);
        	break;
    	case 2 : 
    		keyFrame = Recursos.gastritisDerecha.getKeyFrame(mundo.gripeJ.tiempoEstado, Animation.ANIMATION_LOOPING);
        	mundo.gripeJ.velocidad = -mundo.gripeJ.velocidad;
        	batcher.drawSprite(mundo.gripeJ.position.x, mundo.gripeJ.position.y, 102, 102, keyFrame);
        	break;
    	case 3 : 
    		keyFrame = Recursos.varicelaDerecha.getKeyFrame(mundo.gripeJ.tiempoEstado, Animation.ANIMATION_LOOPING);
        	mundo.gripeJ.velocidad = -mundo.gripeJ.velocidad;
        	batcher.drawSprite(mundo.gripeJ.position.x, mundo.gripeJ.position.y, 146, 126, keyFrame);
        	break;
    	case 4 : 
    		keyFrame = Recursos.rotaDerecha.getKeyFrame(mundo.gripeJ.tiempoEstado, Animation.ANIMATION_LOOPING);
        	mundo.gripeJ.velocidad = -mundo.gripeJ.velocidad;
        	batcher.drawSprite(mundo.gripeJ.position.x, mundo.gripeJ.position.y, 146, 126, keyFrame);
        	break;
    	}
    	
    }
    private void renderLluvia(){
    	TextureRegion keyFrame;
    	if(PantallaMision.mision == 3||PantallaMision.mision == 4){
    		for(int i=0; i<mundo.lluvia.length; i++) {
    			//Lluvia prueba = mundo.lluvia[i];
    			keyFrame = Recursos.balaDerecha.getKeyFrame(mundo.John.tiempoEstado, Animation.ANIMATION_LOOPING);
    			batcher.drawSprite(mundo.lluvia[i].position.x, mundo.lluvia[i].position.y, 24, 27, keyFrame);
    			
    			
    		}

    	}
    	
    }
}