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
 * @author Bacon Rocket Studios basado en el libro Beginning Android Games de Mario Zechner
 * 
 */
public class RenderMundo {
    
    /*
     * Camera2D
     * Los objetos de la clase Camera2D representan a una c谩mara virtual que nos
     * permite ver en pantalla los elementos de alguna pantalla de men煤 y el mundo
     * de juego. Dicha c谩mara puede ser fija o m贸vil de tal manera que pueda ser
     * desplazada por el mundo de juego acompa帽ando al jugador.
     *
     * M茅todo constructor
     * new Camera2D(GLGraphics glGraphics, float ancho, float alto);
     * Ejemplo: Camera2D camara = new Camera2D(glGraphics, 320, 480);
     *
     * Donde glGraphics es el contexto gr谩fico con el cual trabaja la c谩mara, los
     * valores enteros 320 y 480 corresponden al ancho y alto de la vista de la c谩mara. La
     * posici贸n de la c谩mara estar谩 definida en el centro en el centro de la misma y
     * es calculado de manera autom谩tica en base a las dimensiones de la vista (En este
     * ejemplo la posici贸n de la c谩mara esta en el punto 160, 240). Los valores del
     * ancho y alto pueden ser dados en pixeles o en base a las unidades del mundo
     * de juego.
     */

    /*
     * SpriteBatcher
     * Un objeto de la clase SpriteBatcher nos permite dibujar uno o m谩s elementos
     * gr谩ficos en la pantalla haciendo una sola llamada al procesador gr谩fico del
     * dispositivo lo cual nos ayuda a optimizar recursos de memoria. La 煤nica limitante
     * de un SpriteBatcher es que solo puede trabajar con una sola textura a la vez por
     * lo cual por cada textura de la cual vayamos a dibujar es necesario hacer un nuevo
     * llamado al SpriteBatcher.
     *
     * M茅todo constructor
     * new SpriteBatcher(GLGraphics glGraphics, int maxSprites);
     * Ejemplo: SpriteBatcher batcher = new SpriteBatcher(glGraphics, 100);
     *
     * Donde glGraphics es el contexto gr谩fico con el cual trabaja el SpriteBatcher y
     * el valor entero 20 es el n煤mero m谩ximo de Sprites que podr谩 dibujar a la vez el
     * SpriteBatcher.
     */

    /*
     * En esta secci贸n definimos los miembros que nos ayudar谩n a desplegar al mundo
     * de juego y sus elementos. Definimos tambi茅n las dimensiones de la vista en base
     * a las unidades del mundo de juego
     */

    static final float ANCHO_VISTA = 480;
    static final float ALTO_VISTA = 320;
    GLGraphics glGraphics;
    Camera2D camara;
    SpriteBatcher batcher;
    Mundo mundo;

    /**
     * M茅todo constructor de la clase RenderMundo.
     * En este m茅todo se crean los miembros que nos ayudar谩n a desplegar el mundo
     * de juego.
     */
    public RenderMundo(GLGraphics glGraphics, SpriteBatcher batcher, Mundo mundo) {
        /*
         * En esta secci贸n creamos los elementos que nos ayudar谩n con el despliegue
         * del mundo de juego
         */
        this.glGraphics = glGraphics;
        this.mundo = mundo;
        this.camara = new Camera2D(glGraphics, ANCHO_VISTA, ALTO_VISTA);
        this.batcher = batcher;
    }

    /**
     * M閠odo render
     * Metodo donde se juntan todas las imagenes e animaciones a pintar
     * 
     *
     */
    public void render() {
        /*
         * En esta secci贸n actualizamos la posici贸n de la c谩mara del mundo y
         * desplegamos el mundo y sus elementos.
         */
         //Crea un objeto GL10 para trabajar con OpenGL ES.
        GL10 gl =glGraphics.getGL();
        //Actualiza la posici贸n de la c谩mara(vista) en base a la posici贸n del personaje.
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
            case 4 : 
            	batcher.beginBatch(Recursos.background4);
            		batcher.drawSprite(camara.position.x, camara.position.y, ANCHO_VISTA, ALTO_VISTA, Recursos.backgroundRegion4);
            	batcher.endBatch();
            	break;
            }

        // Habilita el modo de fusi贸n.
        gl.glEnable(GL10.GL_BLEND);
        //Establece el modo de fusi贸n
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        // Inicia un batch para dibujar la animaci贸n del personaje.
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
    
    /**
     * M閠odo renderJohn
     * Metodo que con el batch junta todas las animaciones del Heroe para pintar
     * 
     *
     */
    public void renderJohn(){
    	TextureRegion keyFrame;
    	switch (mundo.John.estado){
    		case Heroe.ESTADO_DERECHA :
    		//   Obtiene el cuadro actual de la animaci贸n
    			keyFrame = Recursos.johnDerecha.getKeyFrame(mundo.John.tiempoEstado, Animation.ANIMATION_LOOPING);
    		//    Dibuja el cuadro de la animaci贸n
    			batcher.drawSprite(mundo.John.position.x, mundo.John.position.y, 61, 53, keyFrame);
    			break;
    		case Heroe.ESTADO_IZQUIERDA :
    		//   Obtiene el cuadro actual de la animaci贸n
                keyFrame = Recursos.johnIzquierda.getKeyFrame(mundo.John.tiempoEstado, Animation.ANIMATION_LOOPING);
            //    Dibuja el cuadro de la animaci贸n
                batcher.drawSprite(mundo.John.position.x, mundo.John.position.y, 61, 53, keyFrame);
                break;
    		case Heroe.SALTO_HEROEDER :
        		//   Obtiene el cuadro actual de la animaci贸n
        			keyFrame = Recursos.johnDerecha.getKeyFrame(mundo.John.tiempoEstado, Animation.ANIMATION_LOOPING);
        		//    Dibuja el cuadro de la animaci贸n
        			batcher.drawSprite(mundo.John.position.x, mundo.John.position.y, 61, 53, keyFrame);
        			break;
        		case Heroe.SALTO_HEROEIZQ :
        		//   Obtiene el cuadro actual de la animaci贸n
                    keyFrame = Recursos.johnIzquierda.getKeyFrame(mundo.John.tiempoEstado, Animation.ANIMATION_LOOPING);
                //    Dibuja el cuadro de la animaci贸n
                    batcher.drawSprite(mundo.John.position.x, mundo.John.position.y, 61, 53, keyFrame);
                    break;
    	}


    }
    
    /**
     * M閠odo renderBalaGripe
     * Metodo que con el batch junta todas las animaciones de la Bala del Jefe para pintar
     * 
     *
     */
    public void renderBalaGripe(){
    	TextureRegion keyFrame;
    	switch(PantallaMision.mision){
    		case 1 : 
    			keyFrame = Recursos.balaGripe.getKeyFrame(mundo.BalaGripe.tiempoEstado, Animation.ANIMATION_LOOPING);
    			batcher.drawSprite(mundo.BalaGripe.position.x, mundo.BalaGripe.position.y, 34, 34, keyFrame);
    			break;
    		case 2 : 
    			keyFrame = Recursos.balaGastritis.getKeyFrame(mundo.BalaGripe.tiempoEstado, Animation.ANIMATION_LOOPING);
    			batcher.drawSprite(mundo.BalaGripe.position.x, mundo.BalaGripe.position.y, 31, 32, keyFrame);
    			break;
    		case 3 : 
    			keyFrame = Recursos.balaVaricela.getKeyFrame(mundo.BalaGripe.tiempoEstado, Animation.ANIMATION_LOOPING);
    			batcher.drawSprite(mundo.BalaGripe.position.x, mundo.BalaGripe.position.y, 35, 36, keyFrame);
    			break;
    		case 4 : 
    			keyFrame = Recursos.balaRota.getKeyFrame(mundo.BalaGripe.tiempoEstado, Animation.ANIMATION_LOOPING);
    			batcher.drawSprite(mundo.BalaGripe.position.x, mundo.BalaGripe.position.y, 42, 33, keyFrame);
    			break;
    	}
    }
    
    /**
     * M閠odo renderBala1
     * Metodo que con el batch junta todas las animaciones de la Bala del Heroe para pintar
     * 
     *
     */
    public void renderBala1(){ 			//Renderea las balas del tipo 1
    	TextureRegion keyFrame;
    	int len = mundo.Mejoral.size();
        for (int i = 0; i < len; i++) {
        	Balas bala = mundo.Mejoral.get(i);
        	switch(PantallaMision.mision){
        		case 1:keyFrame = Recursos.balaDerecha.getKeyFrame(mundo.John.tiempoEstado, Animation.ANIMATION_LOOPING);
    		//    Dibuja el cuadro de la animaci髇
    			batcher.drawSprite(bala.position.x, bala.position.y, 11, 11, keyFrame);
    			break;
        		case 2:keyFrame = Recursos.balaJohnG.getKeyFrame(mundo.John.tiempoEstado, Animation.ANIMATION_LOOPING);
        		//    Dibuja el cuadro de la animaci髇
        			batcher.drawSprite(bala.position.x, bala.position.y, 12, 12, keyFrame);
        		break;
        		case 3:
        			switch(bala.estado){
        			case Heroe.ESTADO_DERECHA:
        			case Heroe.SALTO_HEROEDER: 
        				keyFrame = Recursos.balaJohnV.getKeyFrame(mundo.John.tiempoEstado, Animation.ANIMATION_LOOPING);
        				//    Dibuja el cuadro de la animaci髇
        				batcher.drawSprite(bala.position.x, bala.position.y, 22, 10, keyFrame);
        				break;
        			case Heroe.ESTADO_IZQUIERDA:
        			case Heroe.SALTO_HEROEIZQ:
        				keyFrame = Recursos.balaJohnVIzq.getKeyFrame(mundo.John.tiempoEstado, Animation.ANIMATION_LOOPING);
        				batcher.drawSprite(bala.position.x, bala.position.y, 22, 10, keyFrame);
        				break;
        			}
        		break;
        		case 4:keyFrame = Recursos.balaJohnR.getKeyFrame(mundo.John.tiempoEstado, Animation.ANIMATION_LOOPING);
        		//    Dibuja el cuadro de la animaci髇
        			batcher.drawSprite(bala.position.x, bala.position.y, 29, 29, keyFrame);
        		break;
        			
        	}
        	
    	
    	}

        }
    
    /**
     * M閠odo renderGripe
     * Metodo que con el batch junta todas las animaciones de los Villanos para pintar
     * 
     *
     */
    
    public void renderGripe(){ 			//Renderea al malo del nivel 1 gripe
    	
    	int tamanoancho=32;
    	int tamanoalto=35;

    	TextureRegion keyFrame;	
    	
    	int len = mundo.Gripe.size();
        for (int i = 0; i < len; i++) {
            Villano malo = mundo.Gripe.get(i);
            if(malo.vidas==1){
            	tamanoancho=24;
            	tamanoalto=26;
            }
            else{
            	tamanoancho=32;
            	tamanoalto=35;
            }
            switch (malo.estado){
    			case Villano.ESTADO_DERECHA :
    				//   Obtiene el cuadro actual de la animaci贸n
    				keyFrame = Recursos.villanoDerecha.getKeyFrame(malo.tiempoEstado, Animation.ANIMATION_LOOPING);
    				
    				//    Dibuja el cuadro de la animaci贸n
    				batcher.drawSprite(malo.position.x, malo.position.y, tamanoancho, tamanoalto, keyFrame);
    			break;
    			case Villano.ESTADO_IZQUIERDA :
    				//   Obtiene el cuadro actual de la animaci贸n
    				keyFrame = Recursos.villanoIzquierda.getKeyFrame(malo.tiempoEstado, Animation.ANIMATION_LOOPING);
    				//    Dibuja el cuadro de la animaci贸n
    				batcher.drawSprite(malo.position.x, malo.position.y, tamanoancho, tamanoalto, keyFrame);
                break;
    			case Villano.SALTO_VILLANOIZQ :
    				//   Obtiene el cuadro actual de la animaci贸n
    				keyFrame = Recursos.villanoIzquierda.getKeyFrame(malo.tiempoEstado, Animation.ANIMATION_LOOPING);
    				//    Dibuja el cuadro de la animaci贸n
    				batcher.drawSprite(malo.position.x, malo.position.y, tamanoancho, tamanoalto, keyFrame);
                break;
    			case Villano.SALTO_VILLANODER :
    				//   Obtiene el cuadro actual de la animaci贸n
    				keyFrame = Recursos.villanoDerecha.getKeyFrame(malo.tiempoEstado, Animation.ANIMATION_LOOPING);
    				//    Dibuja el cuadro de la animaci贸n
    				batcher.drawSprite(malo.position.x, malo.position.y, tamanoancho, tamanoalto, keyFrame);
                break;
                
            }
                  
        }
    
    }
    
    /**
     * M閠odo renderGripeJ
     * Metodo que con el batch junta todas las animaciones de los Jefes para pintar
     * 
     *
     */
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
    
    /**
     * M閠odo renderLluvia
     * Metodo que con el batch junta todas las animaciones de la Lluvia para pintar
     * 
     *
     */
    private void renderLluvia(){
    	TextureRegion keyFrame;
    	if(PantallaMision.mision == 3||PantallaMision.mision == 4){
    		for(int i=0; i<mundo.lluvia.length; i++) {
    			//Lluvia prueba = mundo.lluvia[i];
    			keyFrame = Recursos.piedra.getKeyFrame(mundo.John.tiempoEstado, Animation.ANIMATION_LOOPING);
    			batcher.drawSprite(mundo.lluvia[i].position.x, mundo.lluvia[i].position.y, 24, 27, keyFrame);
    			
    			
    		}

    	}
    	
    }
}