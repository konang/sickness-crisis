package com.jcj.gameLayout;

import com.jcj.gd2d.GameObject;

/**
 * Clase Item extiende a la clase GameObject.
 * La clase Item define el modelo de un item estableciendo sus dimensiones y su posición,
 * etc. La clase Item hereda de la clase padre atributos como la posición y un rectángulo
 * que delimita el área del objeto.
 *
 * @author jugandoconjava basado en el libro Beginning Android Games de Mario Zechner
 */
public class Item extends GameObject{
    /*
     * En esta sección definimos las propiedades del objeto. Podriamos manejar
     * diversos estados como el objeto así como también el valor de puntos que suma
     * o resta al personaje cuando recolecta uno.
     */
    public static final float ANCHO_ITEM = 0.5f;
    public static final float ALTO_ITEM = 0.8f;

    //float tiempoEstado;

    /*
     * Método constructor de la clase Item.
     * En este método definimos el estado inicial del objeto y llamamos al constructor
     * de la clase padre para determinar la posición y el rectángulo que delimita al
     * objeto y el cual utilizamos para el manejo de colisiones. Los puntos de la posición
     * corresponden al centro del objeto.
     *
     * @param float x corresponde al punto en x del objeto.
     * @param float y corresponde al punto en y del objeto.
     */
    public Item(float x, float y) {
        super(x, y, ANCHO_ITEM, ALTO_ITEM);
        /*
         * En esta sección agregamos el código para definir el estado inicial del objeto.
         */
        //tiempoEstado = 0;
    }

    /*
     * Método update
     * El método update se encarga de actualizar el estado del objeto.
     *
     * @param float deltaTime indica el tiempo transcurrido del sistema desde
     * la última vez que se actualizo el objeto.
     */
    public void update(float deltaTime) {
        /*
         * En esta sección agregamos el código para actualizar el objeto.
         */
        //tiempoEstado += detaTime;
    }

}
