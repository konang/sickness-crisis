package com.jcj.gameLayout;

import com.jcj.gd2d.DynamicGameObject;

public class Lluvia extends DynamicGameObject {

	 public static final int ESTADO_DERECHA = 0;                 //Estado del objeto
	    public static final int ESTADO_IZQUIERDA = 1;                 //Estado del objeto
	    public static final float SALTO_HEROE = 10;           //Velocidad vertical
	    public static final float VELOCIDAD_HEROE = 20;       //Velocidad horizontal
	    public static final float ANCHO_HEROE = 11f;         //Ancho del objeto
	    public static final float ALTO_HEROE = 11f;          //Alto del objeto
	    int estado;                                           //Guarda el estado actual del personaje
	    float tiempoEstado;                                    // Indica el tiempo que el personaje lleva en dicho estado
	    float velocidad=-3;
	    boolean choque = false;
	    public int cantidad=0;
	public Lluvia(float x, float y) {
        super(x, y, ANCHO_HEROE, ALTO_HEROE);
        /*
         * En esta sección agregamos el código para determinar el estado inicial
         * del objeto.
         */
        estado = ESTADO_DERECHA;
        tiempoEstado = 0;
        
    }
	
	 public void update(float deltaTime) {
	        /*
	         * En esta sección agregamos el código para actualizar el objeto
	         */
	        // Actualiza la posición del objeto.
		 	
	        position.add(0, velocidad);
	        // Actualiza |la posición del rectángulo que delimita el área del objeto
	        bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);
	        //Actualiza el tiempo que el objeto lleva en el estado actual
	        tiempoEstado +=deltaTime;
	    }
}
