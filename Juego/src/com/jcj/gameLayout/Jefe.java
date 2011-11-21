package com.jcj.gameLayout;

import com.jcj.gd2d.DynamicGameObject;

public class Jefe extends DynamicGameObject {
	
	/**
	 * Clase VILLANO extiende a la clase DynamicGameObject.
	 * La clase VILLANO define el modelo del personaje del juego estableciendo sus
	 * propiedades como su estado, su velocidad de movimiento, sus dimensiones y su
	 * posición, entre otras. La clase VILLANO hereda de la clase padre atributos
	 * de velocidad, aceleración, posición y un rectangulo que delimita el área del objeto.
	 *
	 * @author jugandoconjava basado en el libro Beginning Android Games de Mario Zechner
	 */
	    /*
	     * En esta sección definimos las propiedades del objeto. Es recomendable manejar
	     * diversos estados para el personaje. Se define su estado, sus velocidades
	     * (vertical y horizontal) y sus dimensiones en unidades del mundo de juego.
	     */
	    public static final int ESTADO_DERECHA = 0;                 //Estado del objeto
	    public static final int ESTADO_IZQUIERDA = 1;                 //Estado del objeto
	    public static final int SALTO_JEFEDER = 10;           //Velocidad vertical
	    public static final int SALTO_JEFEIZQ = 11; 
	    public static final float VELOCIDAD_JEFE = 20;       //Velocidad horizontal
	    public static final float ANCHO_JEFE = 166;         //Ancho del objeto
	    public static final float ALTO_JEFE = 130;          //Alto del objeto
	    public static final float VIDA_JEFE = 25;
	    
	    int estado;                                           //Guarda el estado actual del personaje
	    float tiempoEstado;                                    // Indica el tiempo que el personaje lleva en dicho estado
	    float velocidad = 0;									// Velocidad del malo
	    float velocidady = 0;
	    float puntos = 1000;
	    boolean choque=false;
	    int vidas=30;
	    
	    /*
	     * Método constructor de la clase VILLANO.
	     * En el constructor de clase VILLANO definimos el estado inicial del objeto asi como también llamamos
	     * al constructor de la clase padre para establecerlo en su posición incial y
	     * estableciendo su ancho y alto.
	     * El constructor de la clase padre toma los valores de la posición y las dimensiones
	     * para crear un objeto de la clase Rectangulo que delimita la region de nuestro objeto
	     * y el cual utilizamos en el manejo de colisiones. Los puntos de la posición corresponden
	     * al centro del objeto.
	     *
	     * @param float x es la posición en x del objeto.
	     * @param float y es la posición en y del objeto.
	     */
	    public Jefe(float x, float y) {
	        super(x, y, ANCHO_JEFE, ALTO_JEFE);
	        /*
	         * En esta sección agregamos el código para determinar el estado inicial
	         * del objeto.
	         */
	        estado = ESTADO_DERECHA;
	        tiempoEstado = 0;
	        
	    }

	    /*
	     * Método update.
	     * El método update se encarga de actualizar los atributos de la clase VILLANO,
	     * como por ejemplo su estado, su posición, su velocidad, etc.
	     *
	     * @param float deltaTime indica el tiempo transcurrido del sistema desde la
	     * última vez que se actualizo el objeto.
	     */
	    public void update(float deltaTime) {
	    	 /*
	         * En esta sección agregamos el código para actualizar el objeto
	         */
	      
	        // Actualiza la posición del objeto.
	        position.add(velocidad, velocidady);
	        // Actualiza la posición del rectángulo que delimita el área del objeto
	        bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);
	        //Actualiza el tiempo que el objeto lleva en el estado actual
	        tiempoEstado +=deltaTime;
	    }

}
