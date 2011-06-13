package arkanoid;

import arkanoid.controllers.ArkanoidController;
import arkanoid.controllers.GameAreaController;
import arkanoid.controllers.MainMenuController;
import arkanoid.menus.DialogCreateAccount;
import arkanoid.menus.DialogFastRestart;
import arkanoid.menus.DialogLogin;
import arkanoid.menus.DialogReplay;
import arkanoid.menus.DialogTop10;
import arkanoid.menus.MenuInicial;
import arkanoid.menus.MenuPause;
import arkanoid.models.ModelPlayArea;
import arkanoid.models.entities.PlayArea;
import arkanoid.replay.Replay;
import arkanoid.views.ArkanoidView;
import arkanoid.views.ViewMainMenu;
import arkanoid.views.ViewPlayAreaGeom;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import java.io.IOException;

import java.util.logging.FileHandler;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import org.newdawn.slick.SlickException;

/** Classe que representa o motor do jogo.
 *
 * @author sPeC
 */
public class GameEngine implements Runnable {

    public static final Logger LOGGER = Logger.getLogger(GameEngine.class.getName());

    static {
        try {
            LOGGER.addHandler(new FileHandler("errors.log", true));
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, ex.toString(), ex);
        }
    }
    private boolean mErrorsOnInit;
    private ArkanoidController mCurrentController;   // O controlador activo
    private ArkanoidView mCurrentView;
    private JFrame mCurrentMenu;

    /** Constructor da classe.
     * 
     * @param _displayHeight Altura do ecrã
     * @param _displayWidth Largura do ecrã
     */
    public GameEngine() {
        mErrorsOnInit = false;

        try {
            setup();

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            destroy();
            mErrorsOnInit = true;
        }
    }

    /** Configurações base.
     * 
     * @throws LWJGLException 
     */
    private void setup() throws LWJGLException {
        // Display
        Display.setDisplayMode(new DisplayMode(Settings.DISPLAY_WIDTH, Settings.DISPLAY_HEIGHT));
        Display.setFullscreen(false);
        Display.setTitle("Arkanoid (clone) - Por [Avelino Martins & Filipe Brandao]");
        Display.create();

        // Teclado
        Keyboard.create();

        // Rato
        Mouse.setGrabbed(true);

        Mouse.create();

        // OpenGL
        initGL();
    }

    /** Inicializa sistema gráfico 2D
     */
    private void initGL() {
        // Inicialização 2D
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_LIGHTING);

        glEnable(GL_TEXTURE_2D); // exture Mapping, needed for text
        glEnable(GL_BLEND); // Enabled blending for text
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glViewport(0, 0, Settings.DISPLAY_WIDTH, Settings.DISPLAY_HEIGHT);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluOrtho2D(0.0f, Settings.DISPLAY_WIDTH, Settings.DISPLAY_HEIGHT, 0.0f);
        glPushMatrix();

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glPushMatrix();

        //glEnable(GL_LIGHTING);
        glEnable(GL_LIGHT0);
    }

    private void destroy() {
        Mouse.destroy();
        Keyboard.destroy();
        Display.destroy();
    }

    /** Corre o jogo.
     */
    @Override
    public void run() {

        Textures.getInstance().load();
//        Sounds.getInstance().load();
        GameState.changeState(GameState.GameStateType.MAIN_MENU);

        if (this.mErrorsOnInit) {
            return;
        }

        long ellapsedTime;
        while (!Display.isCloseRequested()) {
            ellapsedTime = System.currentTimeMillis();
            checkGameState();

            if (Display.isVisible()) {
                if (GameState.currentState() != GameState.GameStateType.REPLAYING) {
                    mCurrentController.parseInput();
                }

                mCurrentController.update();
                mCurrentView.render();
            } else {

                if (Display.isDirty()) {
                    mCurrentView.render();
                }
            }
            Display.update();

            ellapsedTime = System.currentTimeMillis() - ellapsedTime;
            if (GameState.currentState() != GameState.GameStateType.REPLAYING) {
                try {
                    long timeToSleep = Settings.GAME_DELAY - ellapsedTime;
                    timeToSleep = timeToSleep > Settings.GAME_DELAY || timeToSleep < 0 ? Settings.GAME_DELAY : timeToSleep;
                    Thread.sleep(timeToSleep);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    } // Run()

    /** Verifica estado do jogo e aplica configurações necessárias específicas a certos estados.
     */
    private void checkGameState() {
        if (!GameState.hasStateChanged()) {
            return;
        }

        switch (GameState.currentState()) {
            case GAME_OVER:
                if (RegisteredPlayerData.getInstance().isLoggedIn()) {
                    Top10.getInstance().Save();
                    Replay.getInstance().Save();
                }

                mCurrentMenu = new MenuPause("Game Over!");
                ((MenuPause) mCurrentMenu).setModelPlayArea(((ViewPlayAreaGeom) mCurrentView).getPlayAreaModel());
                Mouse.setGrabbed(false);

                break;

            case PAUSED:
                mCurrentMenu = new MenuPause("Pausa");
                ((MenuPause) mCurrentMenu).setModelPlayArea(((ViewPlayAreaGeom) mCurrentView).getPlayAreaModel());
                Mouse.setGrabbed(false);

                //
                break;
            case RESTARTING_LEVEL:
                if (mCurrentMenu != null) {
                    mCurrentMenu.dispose();
                    mCurrentMenu = null;
                    Mouse.setGrabbed(true);
                }
                break;

            case MAIN_MENU:
                if (mCurrentMenu != null) {
                    mCurrentMenu.dispose();
                    mCurrentMenu = null;
                }
                mCurrentMenu = new MenuInicial("Menu Inicial");

                mCurrentController = new MainMenuController();
                mCurrentView = new ViewMainMenu();
                Mouse.setGrabbed(false);

                break;

            case LOADED_GAME_FROM_MAIN_MENU: {


                ModelPlayArea mArea = ((MenuInicial) mCurrentMenu).getModelPlayArea();
                mArea.ResetElapsedTime();
                mCurrentController = new GameAreaController(mArea);

                try {
                    mCurrentView = new ViewPlayAreaGeom(mArea);


                } catch (SlickException ex) {
                    Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                }

                mCurrentMenu.dispose();
                mCurrentMenu = null;
                Mouse.setGrabbed(true);

                GameState.changeState(GameState.GameStateType.PLAYING);

            }
            break;

            case PLAYING:
                GameState.setIsLoadedGame(false);

                if (mCurrentMenu != null) {
                    mCurrentMenu.dispose();
                    mCurrentMenu = null;
                    Mouse.setGrabbed(true);

                    ModelPlayArea mArea = new ModelPlayArea(new PlayArea(RegisteredPlayerData.getInstance().getStartingLevel()));
                    mCurrentController = new GameAreaController(mArea);
                    try {
                        mCurrentView = new ViewPlayAreaGeom(mArea);


                    } catch (SlickException ex) {
                        Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;

            case CREATING_ACCOUNT:
                if (mCurrentMenu != null) {
                    mCurrentMenu.dispose();
                    mCurrentMenu = null;
                }

                mCurrentMenu = new DialogCreateAccount();
                break;

            case LOGIN:
                if (mCurrentMenu != null) {
                    mCurrentMenu.dispose();
                    mCurrentMenu = null;
                }

                mCurrentMenu = new DialogLogin();

                break;

            case RESUME_GAME:
                if (mCurrentMenu != null) {
                    mCurrentMenu.dispose();
                    mCurrentMenu = null;
                    Mouse.setGrabbed(true);
                }
                GameState.changeState(GameState.GameStateType.PLAYING);
                ModelPlayArea tmpModel = ((GameAreaController) mCurrentController).getModelPlayArea();
                tmpModel.ResetElapsedTime();
                Mouse.setGrabbed(true);
                break;
            case FAST_RESTART:
                GameState.setIsLoadedGame(false);
                if (mCurrentMenu != null) {
                    mCurrentMenu.dispose();
                    mCurrentMenu = null;
                }

                mCurrentMenu = new DialogFastRestart();
                break;

            case SHOW_TOP10:
                if (mCurrentMenu != null) {
                    mCurrentMenu.dispose();
                    mCurrentMenu = null;
                }

                mCurrentMenu = new DialogTop10();
                break;

            case REPLAYING:
                if (mCurrentMenu != null) {
                    mCurrentMenu.dispose();
                    mCurrentMenu = null;
                }

                Mouse.setGrabbed(false);

                ModelPlayArea mArea = new ModelPlayArea(new PlayArea(RegisteredPlayerData.getInstance().getStartingLevel()));
                mCurrentController = new GameAreaController(mArea);
                try {
                    mCurrentView = new ViewPlayAreaGeom(mArea);


                } catch (SlickException ex) {
                    Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
                }
                mCurrentMenu = new DialogReplay(mArea);
                Replay.getInstance().resetReplay(mArea);
                break;
        }

        GameState.setHasStateChanged(false);
    }
}
