package com.jcj.gameLayout;

import com.jcj.framework.FileIO;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


/**
 * Clase Settings
 * Clase que maneja por medio de un archivo las preferencias de sonido y los puntajes altos
 *
 * @author Bacon Rocket Studios basado en el libro Beginning Android Games de Mario Zechner
 */

public class Settings {
    public static boolean soundEnabled = true;
    public final static int[] highscores = new int[] {100, 80, 50, 30, 10};
    public final static String file = ".superjumper";

    /**
     * Método load
     * Metodo que carga el archivo para guardar los puntajes
     * 
     * @param FileIo files variable para accesar los archivos
     *
     */
    public static void load(FileIO files) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(files.readFile(file)));
            soundEnabled = Boolean.parseBoolean(in.readLine());
            for (int i = 0; i < 5; i++) {
                highscores[i] = Integer.parseInt(in.readLine());
            }
        } catch (IOException e) {
            //
        } catch (NumberFormatException e) {
            //
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                //
            }
        }
    }
    
    /**
     * Método load
     * Metodo que guarda y salva el archivo 
     * 
     * @param FileIo files variable para accesar los archivos
     *
     */

    public static void save(FileIO files) {
        BufferedWriter out = null;
        try{
            out = new BufferedWriter(new OutputStreamWriter(files.writeFile(file)));
            out.write(Boolean.toString(soundEnabled));
            out.write("\n");
            for (int i = 0; i < 5; i++) {
                out.write(Integer.toString(highscores[i]));
                out.write("\n");
            }
        } catch (IOException e) {
            //
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                //
            }
        }
    }
    
    /**
     * Método load
     * Metodo que guarda los puntajes si son los más altos obtenidos
     * 
     * @param int score numero de score que se guarda si es mayor al anterior
     *
     */

    public static void addScore(int score) {
    	if (highscores [PantallaMision.mision-1] < score) {
    		highscores [PantallaMision.mision-1]=score;    		
    	}
    }
}
