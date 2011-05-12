/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid;

import arkanoid.controllers.ArkanoidController;
import arkanoid.controllers.GameAreaController;
import arkanoid.menus.Menu;
import arkanoid.menus.MenuPause;
import arkanoid.models.ModelPlayArea;
import arkanoid.models.entities.PlayArea;
import arkanoid.views.ArkanoidView;
import arkanoid.views.ViewPlayAreaGeom;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.SlickException;

/**
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
    private List<ArkanoidController> mControllersList = new ArrayList<ArkanoidController>(); // Para guardar os controladores não activos
    private ArkanoidController mCurrentController;   // O controlador activo
    private ArkanoidView mCurrentView;
    private Menu mCurrentMenu;

    /**
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
    }

    private void destroy() {
        Mouse.destroy();
        Keyboard.destroy();
        Display.destroy();
    }

    @Override
    public void run() {
        PlayArea area = new PlayArea("level1.txt");
        ModelPlayArea mArea = new ModelPlayArea(area);

        mCurrentController = new GameAreaController(mArea);
        try {
            mCurrentView = new ViewPlayAreaGeom(mArea);
        } catch (SlickException ex) {
            Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
            mErrorsOnInit = true;
        }

        if (this.mErrorsOnInit) {
            return;
        }

        while (!Display.isCloseRequested()) {
            if (Display.isVisible()) {
                checkGameState();
                mCurrentController.parseInput();
                mCurrentController.update();
                mCurrentView.render();
            } else {
                if (Display.isDirty()) {
                    mCurrentView.render();
                }
            }

            Display.update();
            //Display.sync(60);
        }

    } // Run()

    private void checkGameState() {
        if (!GameState.hasStateChanged()) {
            return;
        }

        switch (GameState.currentState()) {
            case GAME_OVER:
                mCurrentMenu = new MenuPause("Game Over!");
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
        }

        GameState.setHasStateChanged(false);
    }
}
