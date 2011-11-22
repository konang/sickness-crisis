package com.jcj.gameLayout;

import com.jcj.gd2d.DynamicGameObject;

/**
 * Clase Balas extiende a la clase DynamicGameObject.
 * La clase Balas define el modelo del personaje del juego estableciendo sus
 * propiedades como su estado, su velocidad de movimiento, sus dimensiones y su
 * posición, entre otras. La clase Heroe hereda de la clase padre atributos
 * de velocidad, aceleración, posiciÃ³n y un rectangulo que delimita el Área del objeto.
 *
 * @author Bacon Rocket Studios basado en el libro Beginning Android Games de Mario Zechner
 */

public class Balas extends DynamicGameObject {

	 public static final int ESTADO_DERECHA = 0;                //Estado del objeto
	    public static final int ESTADO_IZQUIERDA = 1;           //Estado del objeto
	    public static final float VELOCIDAD_HEROE = 20;       	//Velocidad horizontal
	    public static final float ANCHO_HEROE = 11f;        	//Ancho del objeto
	    public static final float ALTO_HEROE = 11f;          	//Alto del objeto
	    int estado;                                           	//Guarda el estado actual del personaje
	    float tiempoEstado;                                    	//Indica el tiempo que el personaje lleva en dicho estado
	    float velocidad=0;										//Velocidad inicial
	    boolean choque = false;									//Varialbe que verifica el choque
	    
	public Balas(float x, float y) {
        super(x, y, ANCHO_HEROE, ALTO_HEROE);
        /**
        * En el constructor de clase bala definimos el estado inicial del objeto asi como también llamamos
        * al constructor de la clase padre para establecerlo en su posición incial y
        * estableciendo su ancho y alto.
        * El constructor de la clase padre toma los valores de la posición y las dimensiones
        * para crear un objeto de la clase Rectangulo que delimita la region de nuestro objeto
        * y el cual utilizamos en el manejo de colisiones. Los puntos de la posición corresponden
        * al centro del objeto.
        * 
        * @param x es el valor de la posición en x donde se crea
        * @param y es el valor de la posición en y donde se crea
        * 
        * @return no regresa nada
        */
        estado = ESTADO_DERECHA;
        tiempoEstado = 0;
        
    }
	
	 public void update(float deltaTime) {
		 /**
	         * 
	         * Este metodo sirve para actualizar la posición del objeto
	         * 
	         * @param deltaTime es el valor que utilizar para actualizar 
	         * 
	         * @return no regresa nada
	         */
		 	
	        position.add(velocidad, 0);
	        // Actualiza |la posiciÃ³n del rectÃ¡ngulo que delimita el Ã¡rea del objeto
	        bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);
	        //Actualiza el tiempo que el objeto lleva en el estado actual
	        tiempoEstado +=deltaTime;
	    }
}
