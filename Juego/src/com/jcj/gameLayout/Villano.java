package com.jcj.gameLayout;

import com.jcj.gd2d.DynamicGameObject;

public class Villano extends DynamicGameObject {
	/**
	 * Clase VILLANO extiende a la clase DynamicGameObject.
	 * La clase VILLANO define el modelo del personaje del juego estableciendo sus
	 * propiedades como su estado, su velocidad de movimiento, sus dimensiones y su
	 * posici髇, entre otras. La clase VILLANO hereda de la clase padre atributos
	 * de velocidad, aceleraci髇, posici髇 y un rectangulo que delimita el 羠ea del objeto.
	 *
	 * @author Bacon Rocket Studios basado en el libro Beginning Android Games de Mario Zechner
	 */
	    /*
	     * En esta secci贸n definimos las propiedades del objeto. Es recomendable manejar
	     * diversos estados para el personaje. Se define su estado, sus velocidades
	     * (vertical y horizontal) y sus dimensiones en unidades del mundo de juego.
	     */
	    public static final int ESTADO_DERECHA = 0;                 //Estado del objeto
	    public static final int ESTADO_IZQUIERDA = 1;               //Estado del objeto
	    public static final int SALTO_VILLANODER = 10;           	//Velocidad vertical
	    public static final int SALTO_VILLANOIZQ = 11; 				//
	    public static final float VELOCIDAD_VILLANO = 20;      		//Velocidad horizontal
	    public static final float ANCHO_VILLANO = 32;         		//Ancho del objeto
	    public static final float ALTO_VILLANO = 35;          		//Alto del objeto
	    public static final float VIDA_VILLANO = 2;					//
	    
	    int estado;                               		            //Guarda el estado actual del personaje
	    float tiempoEstado;                             	    	//Indica el tiempo que el personaje lleva en dicho estado
	    float velocidad = 0;										//Velocidad en x del malo
	    float velocidady = 0;										//Velocidad en y del malo
	    float puntos = 10;											//puntos que otorga al vencer
	    boolean choque=false;										//variable que verifica si choco
	    int vidas=2;												//Vidas iniciales del objeto
	    
	    /**
	     * M閠odo constructor de la clase VILLANO.
	     * En el constructor de clase VILLANO definimos el estado inicial del objeto asi como tambi閚 llamamos
	     * al constructor de la clase padre para establecerlo en su posici髇 incial y
	     * estableciendo su ancho y alto.
	     * El constructor de la clase padre toma los valores de la posici髇 y las dimensiones
	     * para crear un objeto de la clase Rectangulo que delimita la region de nuestro objeto
	     * y el cual utilizamos en el manejo de colisiones. Los puntos de la posici髇 corresponden
	     * al centro del objeto.
	     *
	     * @param float x es la posici髇 en x del objeto.
	     * @param float y es la posici髇 en y del objeto.
	     */
	    public Villano(float x, float y) {
	        super(x, y, ANCHO_VILLANO, ALTO_VILLANO);
	        /*
	         * En esta secci贸n agregamos el c贸digo para determinar el estado inicial
	         * del objeto.
	         */
	        estado = ESTADO_DERECHA;
	        tiempoEstado = 0;
	        
	    }

	    /**
	     * M閠odo update.
	     * El m茅todo update se encarga de actualizar los atributos de la clase VILLANO,
	     * como por ejemplo su estado, su posici髇, su velocidad, etc.
	     *
	     * @param float deltaTime indica el tiempo transcurrido del sistema desde la
	     * 鷏tima vez que se actualizo el objeto.
	     */
	    public void update(float deltaTime) {
	    	 /*
	         * En esta secci贸n agregamos el c贸digo para actualizar el objeto
	         */
	      
	        // Actualiza la posici贸n del objeto.
	        position.add(velocidad, velocidady);
	        // Actualiza la posici贸n del rect谩ngulo que delimita el 谩rea del objeto
	        bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);
	        //Actualiza el tiempo que el objeto lleva en el estado actual
	        tiempoEstado +=deltaTime;
	    }
	    

}
