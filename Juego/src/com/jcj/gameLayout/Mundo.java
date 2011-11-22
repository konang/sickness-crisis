package com.jcj.gameLayout;

import com.jcj.framework.math.OverlapTester;
import com.jcj.framework.math.Vector2;

import java.util.ArrayList;

import java.util.List;


/**
 * Clase Mundo
 * La clase Mundo se encarga de actualizar el mundo de juego así cómo también del
 * manejo de las colisiones y si el juego ha terminado.
 *
 * @author Bacon Rocket Studios basado en el libro Beginning Android Games de Mario Zechner
 */
public class Mundo {
    public interface MundoListener {
        /*
         * En esta sección definimos los eventos que el listener va a manejar
         * y las acciones que se llevarán acabo
         */
        
    }
    
    /*
     * En esta secciÃ³n definimos las dimensiones del mundo asi como también todos
     * los elementos que lo conformarn. Es recomendable agregar tambiÃ©n el manejo
     * de estados para el mundo. El mundo de juego no esta basado en pixeles si no 
     * en sus propias unidades.
     */
    public static final float ANCHO_MUNDO = 480;
    public static final float ALTO_MUNDO = 320;
    public static final Vector2 gravedad = new Vector2(0, -12);

    public final Heroe John;
    public final Jefe gripeJ;
    public final List<Villano> Gripe;
    public Lluvia[] lluvia = new Lluvia[6];
    public final MundoListener listener;
    public List<Balas> Mejoral;
    public final Balas BalaGripe;
    public int rG = 5;
    public int score;
    public boolean gameOver=false;
    public boolean jefeYa= false;
    
    /**
     * Método constructor de la clase Mundo.
     * En este método creamos los elementos que componen al mundo de juego e
     * inicializamos todo lo que se tenga que inicializar.
     *
     * @param MundoListener listener se encarga de manejar los eventos del mundo
     * de juego.
     */
    public Mundo (MundoListener listener) {
        /*
         * En esta secciÃ³n agregamos el cÃ³digo para crear los elementos del mundo
         * de juego.
         */

    	this.Mejoral =new ArrayList<Balas>();
        this.John = new Heroe(ANCHO_MUNDO/2, ALTO_MUNDO/4);
        this.gripeJ = new Jefe (0 + Jefe.ANCHO_JEFE/2, ALTO_MUNDO);  //ALTO_MUNDO/4 + Jefe.ALTO_JEFE/4 + 5
        this.listener = listener;
        this.Gripe = new ArrayList<Villano>();
        this.BalaGripe = new Balas(-100, -100);
        for(int i=0; i<lluvia.length; i++) { lluvia[i] = new Lluvia((float)Math.random() * 480,320+(float)Math.random() *320 ); }

        
       
        
        this.score=0;
        gripeJ.vidas = 20;     //Vidas Pruebas, checar ya bien cuantas queremos
        
    }

    /**
     * Método update.
     * En este método se actualizan todos los elementos del mundo de juego y se
     * mandan a llamar los métodos encargados de checar colisiones y si el juego
     * ha finalizado.
     *
     * @param float deltaTime es el tiempo que ha transcurrido desde la última vez
     * que el juego fue actualizado.
     */
    
    
    public void update(float deltaTime) {
        /*
         * En esta secciÃ³n agregamos el cÃ³digo para actualizar el mundo de juego.
         */
        
        updateJohn(deltaTime);
       	updateGripe(deltaTime);  
        updateBala(deltaTime);
        updateLluvia(deltaTime);
        updateBalaGripe(deltaTime);
        
        if(score >= 250){                   // Le movi al score para llegar al jefe
        	updateGripeJ(deltaTime);
        	jefeYa=true;
        	gripeJ.position.set(0 + Jefe.ANCHO_JEFE/2,ALTO_MUNDO/4 + Jefe.ALTO_JEFE/4 + 5);

        }
        checkCollisions();

        
    }
    
    /**
     * Método updateJohn.
     * En este método se actualizan todos los elementos del objeto de la clase Heroe
     *
     * @param float deltaTime es el tiempo que ha transcurrido desde la última vez
     * que el juego fue actualizado.
     */
    public void updateJohn(float deltaTime){
    	
    	
    	if((John.estado==John.SALTO_HEROEDER||John.estado==John.SALTO_HEROEIZQ) && John.position.y<=180 && John.velocidady!=-4){
    		John.velocidady=4;
    	}
    	else if((John.estado==John.SALTO_HEROEDER||John.estado==John.SALTO_HEROEIZQ)&&John.position.y>=180){
    		John.velocidady=-4;
    	}
    	if (John.position.y<80&&John.estado==John.SALTO_HEROEDER){
    		John.estado=Heroe.ESTADO_DERECHA;
    		John.velocidady=0;
    		John.position.y=ALTO_MUNDO/4;
    	}
    	else if (John.position.y<80&&John.estado==John.SALTO_HEROEIZQ){
    		John.estado=Heroe.ESTADO_IZQUIERDA;
    		John.velocidady=0;
    		John.position.y=ALTO_MUNDO/4;
    	}
    	
    	
    	John.update(deltaTime);
    }
    private void updateBalaGripe(float deltaTime){
    	if(jefeYa){
    		if (gripeJ.estado==Jefe.ESTADO_DERECHA && (BalaGripe.position.x > ANCHO_MUNDO || BalaGripe.position.x < 0)){
	    			BalaGripe.velocidad = 1;
	    			BalaGripe.position.x = gripeJ.position.x;
	    			BalaGripe.position.y = John.position.y-10;
	    	}
	    	if (gripeJ.estado==Jefe.ESTADO_IZQUIERDA && (BalaGripe.position.x > ANCHO_MUNDO || BalaGripe.position.x < 0)){
	    		BalaGripe.velocidad = -1;
	    		BalaGripe.position.x = gripeJ.position.x;
	    		BalaGripe.position.y = John.position.y-10;
	    	}
	
	    	if (OverlapTester.overlapRectangles(John.bounds, BalaGripe.bounds)) {
	    		BalaGripe.position.y = 500;
	    		Recursos.playSound(Recursos.hitSound);
	    		John.vidas--;
	    	}
	
	    	for (int i = 0; i < Mejoral.size(); i++){
	    		Balas bala = Mejoral.get(i);
	    		if (OverlapTester.overlapRectangles(bala.bounds, BalaGripe.bounds)) {
	    			BalaGripe.position.x = gripeJ.position.x;
	    			bala.choque = true;
	    		}
	    	}
	    	BalaGripe.update(deltaTime);
	    }
    }

    /**
     * Método updateBala.
     * En este método se actualizan todos los elementos del objeto de la clase Bala
     *
     * @param float deltaTime es el tiempo que ha transcurrido desde la última vez
     * que el juego fue actualizado.
     */
    private void updateBala(float deltaTime){
    	
    	for (int i = 0; i < Mejoral.size(); i++){
    		Balas bala = Mejoral.get(i);
    		for(int j=0; j<lluvia.length; j++){
    			if (OverlapTester.overlapRectangles(bala.bounds, lluvia[j].bounds)) {
    				score+=5;
    				bala.choque=true;
    				lluvia[j].position.y =360;
    				lluvia[j].position.x =(float)Math.random()*480;
    			}
    		}
    		
    		if (OverlapTester.overlapRectangles(bala.bounds, gripeJ.bounds)) {
				gripeJ.vidas--;
				bala.choque=true;
			}
    		
    		if(bala.position.x > 480 ||bala.position.x < 0){
				bala.choque=true;
			}
    	
    		for (int j = 0; j < Gripe.size(); j++) {
    			Villano malo = Gripe.get(j);
            
    			if (OverlapTester.overlapRectangles(bala.bounds, malo.bounds)) {
    				malo.vidas--;
    				bala.choque=true;
    				score+=5;
    			}

    		}
    	
    		bala.update(deltaTime);
    		
    	}
    	
    	for (int i = 0; i < Mejoral.size(); i++){
    		if(Mejoral.get(i).choque){
    			Mejoral.remove(i);
    			i--;
    		}
    	
    	}
    	
    	
    	for (int i = 0; i < Gripe.size(); i++){
    		if(Gripe.get(i).vidas<=0){
    			Gripe.remove(i);
    			i--;
    		}
    	}
    	
    	if(gripeJ.vidas <= 0){
			PantallaJuego.state= PantallaJuego.GAME_LEVEL_END;
			jefeYa= false;
			score +=500;
			PantallaGanar.vidas2 = "" + John.vidas;
			PantallaGanar.score2 = "" + score;
			PantallaGanar.score3 = "" + John.vidas * score;
		}
    	
    }
    /**
     * Método updateGripe.
     * En este método se actualizan todos los elementos del objeto de la clase Villano
     *
     * @param float deltaTime es el tiempo que ha transcurrido desde la última vez
     * que el juego fue actualizado.
     */
    
    
    private void updateGripe(float deltaTime){

    	
        for (int i = 0; i < Gripe.size(); i++) {
            Villano malo = Gripe.get(i);
            if(John.position.x>malo.position.x){
            	malo.velocidad = 1;
            }
            else{
            	malo.velocidad = -1;
            }
            
            if(PantallaMision.mision==2||PantallaMision.mision == 4 && malo.estado==malo.ESTADO_DERECHA){
            		malo.estado = Villano.SALTO_VILLANODER;
            }
            
            else if(PantallaMision.mision==2||PantallaMision.mision==4 &&malo.estado==malo.ESTADO_IZQUIERDA){
            		malo.estado = Villano.SALTO_VILLANOIZQ;
            }
            
            
            if((malo.estado==Villano.SALTO_VILLANODER||malo.estado==Villano.SALTO_VILLANOIZQ)&&malo.position.y<=180&&malo.velocidady!=-1.5){
        		malo.velocidady=1.5f;
        	}
        	else if((malo.estado==Villano.SALTO_VILLANODER||malo.estado==Villano.SALTO_VILLANOIZQ)&&malo.position.y>=180){
        		malo.velocidady=-1.5f;
        	}
        	if (malo.position.y<80 && malo.estado==Villano.SALTO_VILLANODER){
        		malo.estado=malo.ESTADO_DERECHA;
        		malo.velocidady=0;
        		malo.position.y=ALTO_MUNDO/4;
        	}
        	else if (malo.position.y<80 && malo.estado==Villano.SALTO_VILLANOIZQ){
        		malo.estado=malo.ESTADO_IZQUIERDA;
        		malo.velocidady=0;
        		malo.position.y=ALTO_MUNDO/4;
        	}
        	
            
            malo.update(deltaTime);
        }
    	
    }
    
    /**
     * Método updateLluvia.
     * En este método se actualizan todos los elementos del objeto de la clase Lluvia
     *
     * @param float deltaTime es el tiempo que ha transcurrido desde la última vez
     * que el juego fue actualizado.
     */
    private void updateLluvia(float deltaTime){
    	if(PantallaMision.mision == 3||PantallaMision.mision == 4){
    		for(int i=0; i<lluvia.length; i++) {
    			//Lluvia prueba = lluvia[i];
    			lluvia[i].update(deltaTime);
    			
    			
    			if(lluvia[i].position.y < 0){
    				lluvia[i].position.y =360;
    				lluvia[i].position.x =(float)Math.random()*480;
    			}
    			if (OverlapTester.overlapRectangles(John.bounds, lluvia[i].bounds)) {
    				lluvia[i].position.y =360;
    				lluvia[i].position.x =(float)Math.random()*480;
    				Recursos.playSound(Recursos.hitSound);
    	            John.vidas--;
    		}

    	}
     }
    	
    }
    
    /**
     * Método updateGripeJ
     * En este método se actualizan todos los elementos del objeto de la clase Jefe
     *
     * @param float deltaTime es el tiempo que ha transcurrido desde la última vez
     * que el juego fue actualizado.
     */

    
   
    private void updateGripeJ(float deltaTime){
    	gripeJ.estado=Jefe.ESTADO_DERECHA;
    	gripeJ.velocidady = 0;
    	gripeJ.velocidad= 0;
    	gripeJ.update(deltaTime);
    }
    /**
     * Método checkCollisions.
     * En esta sección se agrega el código para manejar las colisiones entre
     * objetos.
     */
    private void checkCollisions() {
        /*
         * En esta secciÃ³n se agrega el cÃ³digo para manejar las colisiones entre
         * objetos.
         */
    	for (int i = 0; i < Gripe.size(); i++) {
            Villano malo = Gripe.get(i);
            
            if (OverlapTester.overlapRectangles(John.bounds, malo.bounds)) {
                malo.choque = true;
                Recursos.playSound(Recursos.hitSound);
                John.vidas--;
        	}
            
    	}
        
    	for (int i = 0; i < Gripe.size(); i++){
    		if(Gripe.get(i).choque){
    			Gripe.remove(i);
    			i--;
    		}
    	}
        
    }

    

}
