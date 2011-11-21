package com.jcj.gameLayout;

import com.jcj.framework.Music;
import com.jcj.framework.Sound;
import com.jcj.framework.gl.Animation;
import com.jcj.framework.gl.Font;
import com.jcj.framework.gl.Texture;
import com.jcj.framework.gl.TextureRegion;
import com.jcj.framework.impl.GLGame;

public class Recursos {
    public static Texture background;
    public static TextureRegion backgroundRegion;
    public static Texture backgroundjuego;
    public static TextureRegion backgroundRegionjuegp;

    public static Texture items;
    public static TextureRegion mainMenu;
    public static TextureRegion pauseMenu;
    public static TextureRegion misionIzq;
    public static TextureRegion misionDer;
    public static TextureRegion ready;
    public static TextureRegion gameOver;
    public static TextureRegion highScoresRegion;
    public static TextureRegion logo;
    public static TextureRegion soundOn;
    public static TextureRegion soundOff;
    public static TextureRegion cell;
    public static TextureRegion flechaderecha;
    public static TextureRegion flechaizquierda;
    public static TextureRegion dpadderecha;
    public static TextureRegion dpadizquierda;
    public static TextureRegion dpadarriba;
    public static TextureRegion pause;
    public static TextureRegion resume;
    public static TextureRegion spring;
    public static TextureRegion regresarMenuPrincipal;
    public static TextureRegion botones;

    public static TextureRegion vidaJohn;

    public static TextureRegion score;

    public static Animation coinAnim;
    public static Animation villanoIzquierda;
    public static Animation villanoDerecha;
    public static Animation balaIzquierda;
    public static Animation balaDerecha;
    public static Animation johnIzquierda;
    public static Animation johnDerecha;
    public static TextureRegion bobHit;
    public static Animation squirrelFly;
    public static TextureRegion platform;
    public static Animation breakingPlatform;
    public static Animation gripeJDerecha;
    public static Animation gripeJIzquierda;
    public static Animation balaGripe;
    public static Font font;
    public static Music music;
    public static Sound jumpSound;
    public static Sound highJumpSound;
    public static Sound hitSound;
    public static Sound coinSound;
    public static Sound clickSound;
    public static Sound balazo;

    public static void load(GLGame game) {
    	
        background = new Texture(game, "background.png");
        backgroundRegion = new TextureRegion(background, 0, 0, 480, 320);
       // backgroundjuego = new Texture(game, "background.png");
        //backgroundRegionjuegp = new TextureRegion(background, 0, 0, 480, 320);
        
        items = new Texture(game, "imagenes.png");
        
        logo = new TextureRegion(items, 0, 0, 275, 105);    
        cell = new TextureRegion(items, 0, 106, 60, 74);
        mainMenu = new TextureRegion(items, 304, 121, 192, 213);
        pauseMenu = new TextureRegion(items, 224, 128, 192, 96);
        ready = new TextureRegion(items, 320, 224, 192, 32);
        gameOver = new TextureRegion(items, 352, 256, 160, 96);
        highScoresRegion = new TextureRegion(Recursos.items, 0, 257, 300, 110 / 3);
        misionIzq= new TextureRegion(items,0 ,425 ,90, 185);
        misionDer= new TextureRegion(items,90 ,425 ,90, 185);
        soundOff = new TextureRegion(items, 80, 130, 30, 35);
        soundOn = new TextureRegion(items, 115, 128, 50, 36);
        flechaderecha = new TextureRegion(items, 215, 135, 30, 19);
        flechaizquierda = new TextureRegion(items, 174, 135, 30, 19);
        dpadderecha= new TextureRegion(items, 68, 294, 44, 27);
        dpadizquierda = new TextureRegion(items, 8, 294, 44, 27);
        dpadarriba= new TextureRegion(items,129,289,27,36);
        pause = new TextureRegion(items, 22, 203, 37, 34);
        resume = new TextureRegion(items, 72, 203, 20, 36);
        botones = new TextureRegion(items, 125, 198, 112, 40);

        vidaJohn = new TextureRegion(items, 527, 23, 28, 31);

        score= new TextureRegion (items,189,294,86,28);


        spring = new TextureRegion(items, 128, 0, 32, 32);
        regresarMenuPrincipal = new TextureRegion(items, 305, 341, 192, 66);
        balaGripe = new Animation(0.4f,
								new TextureRegion(items, 552, 356, 34, 33),
								new TextureRegion(items, 603, 359, 34, 34),
								new TextureRegion(items, 650, 359, 34, 32),
								new TextureRegion(items, 703, 357, 33, 34));
        coinAnim = new Animation(0.2f,
                                 new TextureRegion(items, 128, 32, 32, 32),
                                 new TextureRegion(items, 160, 32, 32, 32),
                                 new TextureRegion(items, 192, 32, 32, 32),
                                 new TextureRegion(items, 160, 32, 32, 32));
        balaIzquierda = new Animation(0.4f,
                				new TextureRegion(items, 483, 537, 11, 11),
                				new TextureRegion(items, 510, 537, 11, 11),
        						new TextureRegion(items, 539, 537, 11, 11),
        						new TextureRegion(items, 567, 537, 11, 11));
        balaDerecha = new Animation(0.4f,
        						new TextureRegion(items, 567, 537, 11, 11),
        						new TextureRegion(items, 539, 537, 11, 11),
        						new TextureRegion(items, 510, 537, 11, 11),
        						new TextureRegion(items, 483, 537, 11, 11));
        villanoDerecha = new Animation(0.4f,
								new TextureRegion(items, 191, 498, 32, 35),
								new TextureRegion(items, 237, 498, 32, 35));
        villanoIzquierda = new Animation(0.4f,
        						new TextureRegion(items, 192, 442, 32, 35),
        						new TextureRegion(items, 238, 442, 32, 35));
        gripeJDerecha = new Animation(0.3f, new TextureRegion(items, 462, 434, 78, 61),       // checar Width y length de cada uno en caso de no pintar bien
        						new TextureRegion(items, 549, 432, 79, 64),
        						new TextureRegion(items, 633, 431, 86, 66),
        						new TextureRegion(items, 720, 429, 87, 65),
        						new TextureRegion(items, 808, 430, 87, 64));
        gripeJIzquierda = new Animation(0.3f, new TextureRegion(items, 660, 512, 83, 65), 
								new TextureRegion(items, 749, 512, 83, 65),
								new TextureRegion(items, 836, 514, 80, 65),
								new TextureRegion(items, 923, 515, 80, 65),
								new TextureRegion(items, 1007, 517, 84, 68));
        
        johnIzquierda = new Animation(0.4f,
                                new TextureRegion(items, 291, 502, 50, 53),
                                new TextureRegion(items, 347, 507, 61, 48));
        johnDerecha = new Animation(0.4f,
                                new TextureRegion(items, 289, 437, 61, 48),
                                new TextureRegion(items, 357, 431, 50, 53));
        bobHit = new TextureRegion(items, 128, 128, 32, 32);
        squirrelFly = new Animation(0.2f,
                                    new TextureRegion(items, 0, 160, 32, 32),
                                    new TextureRegion(items, 32, 160, 32, 32));
        platform = new TextureRegion(items, 64, 160, 64, 16);
        breakingPlatform = new Animation(0.2f,
                                         new TextureRegion(items, 64, 160, 64, 16),
                                         new TextureRegion(items, 64, 176, 64, 16),
                                         new TextureRegion(items, 64, 192, 64, 16),
                                         new TextureRegion(items, 64, 208, 64, 16));
        font = new Font(items, 788, 4, 16, 16, 20);
        music = game.getAudio().newMusic("music.mp3");
        music.setLooping(true);
        music.setVolume(0.5f);
        if(Settings.soundEnabled)
            music.play();
        jumpSound = game.getAudio().newSound("jump.ogg");
        highJumpSound = game.getAudio().newSound("highjump.ogg");
        hitSound = game.getAudio().newSound("hit.ogg");
        coinSound = game.getAudio().newSound("coin.ogg");
        clickSound = game.getAudio().newSound("click.ogg");
        balazo =game.getAudio().newSound("balazo.ogg");
    }

    public static void reload() {
        background.reload();
        items.reload();
        if (Settings.soundEnabled)
            music.play();
    }

    public static void playSound(Sound sound) {
        if (Settings.soundEnabled)
            sound.play(1);
    }

}
