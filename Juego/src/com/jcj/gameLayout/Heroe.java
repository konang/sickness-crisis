package com.jcj.gameLayout;

import com.jcj.gd2d.DynamicGameObject;

/**
 * 
 * Clase Heroe extiende a la clase DynamicGameObject.
 * La clase Heroe define el modelo del personaje del juego estableciendo sus
 * propiedades como su estado, su velocidad de movimiento, sus dimensiones y su
 * posici�n, entre otras. La clase Heroe hereda de la clase padre atributos
 * de velocidad, aceleraci�n, posici�n y un rectangulo que delimita el �rea del objeto.
 *
 * @author Bacon Rocket Studios basado en el libro Beginning Android Games de Mario Zechner
 */
public class Heroe extends DynamicGameObject {
    /*
     * En esta sección definimos las propiedades del objeto. Es recomendable manejar
     * diversos estados para el personaje. Se define su estado, sus velocidades
     * (vertical y horizontal) y sus dimensiones en unidades del mundo de juego.
     */
    public static final int ESTADO_DERECHA = 0;                 //Estado del objeto
    public static final int ESTADO_IZQUIERDA = 1;               //Estado del objeto
    public static final int SALTO_HEROEDER = 10;           		//Estado del objeto
    public static final int SALTO_HEROEIZQ = 11; 				//Estado del objeto
    public static final float ANCHO_HEROE = 61f;         		//Ancho del objeto
    public static final float ALTO_HEROE = 53f;          		//Alto del objeto
    public int vidas=10;										//Vidas del Heroe
    int estado;                                           		//Guarda el estado actual del personaje
    float tiempoEstado;                                    		//Indica el tiempo que el personaje lleva en dicho estado
    float velocidadx=0;											//Velocidad en x del Heroe
    float velocidady=0;											//Velocidad en y del Heroe
    
    /**
     * 
     * M�todo constructor de la clase Heroe.
     * En el constructor de clase Heroe definimos el estado inicial del objeto asi como tambi�n llamamos
     * al constructor de la clase padre para establecerlo en su posici�n incial y
     * estableciendo su ancho y alto.
     * El constructor de la clase padre toma los valores de la posici�n y las dimensiones
     * para crear un objeto de la clase Rectangulo que delimita la region de nuestro objeto
     * y el cual utilizamos en el manejo de colisiones. Los puntos de la posici�n corresponden
     * al centro del objeto.
     *
     * @param float x es la posici�n en x del objeto.
     * @param float y es la posici�n en y del objeto.
     */
    public Heroe(float x, float y) {
        super(x, y, ANCHO_HEROE, ALTO_HEROE);
        /*
         * En esta sección agregamos el código para determinar el estado inicial
         * del objeto.
         */
        estado = ESTADO_DERECHA;
        tiempoEstado = 0;
        
    }

    /**
     * 
     * M�todo update.
     * El m�todo update se encarga de actualizar los atributos de la clase Heroe,
     * como por ejemplo su estado, su posición, su velocidad, etc.
     *
     * @param float deltaTime indica el tiempo transcurrido del sistema desde la
     * �ltima vez que se actualizo el objeto.
     */
    public void update(float deltaTime) {
        /*
         * En esta sección agregamos el código para actualizar el objeto
         */
      
        // Actualiza la posición del objeto.
        position.add(velocidadx, velocidady);
        // Actualiza la posición del rectángulo que delimita el área del objeto
        bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);
        //Actualiza el tiempo que el objeto lleva en el estado actual
        tiempoEstado +=deltaTime;
        if (position.x < 30)
            position.x = 30;
        if (position.x > Mundo.ANCHO_MUNDO-30)
            position.x = Mundo.ANCHO_MUNDO-30;
       
    }
    	

}
