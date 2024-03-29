/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.controllers;

import arkanoid.ElapsedTime;
import arkanoid.GameState;
import arkanoid.GameState.GameStateType;
import arkanoid.RegisteredPlayerData;
import arkanoid.models.ModelPlayArea;
import arkanoid.replay.Replay;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author sPeC
 */
public class GameAreaController implements ArkanoidController {

    static float MouseLastX = Mouse.getX();
    static float MouseLastY = Mouse.getY();
    static long mKeyMovementLastUpdate = 0;
    static long mEscapeLastUpdate = 0;
    private ModelPlayArea mModel;

    public ModelPlayArea getModelPlayArea() {
        return mModel;
    }

    /**
     * Constructor para objectos da classe GameAreaController
     */
    public GameAreaController(ModelPlayArea _m) {
        // initialise instance variables
        mModel = _m;
    }
    private long mLastViewChangeKeyTime = System.currentTimeMillis();

    private void processKeyboard() {

        long timeEllapsed = System.currentTimeMillis() - mLastViewChangeKeyTime;

        if (Keyboard.isKeyDown(Keyboard.KEY_V) && timeEllapsed > 500) {
            mLastViewChangeKeyTime = System.currentTimeMillis();

            if (RegisteredPlayerData.getInstance().drawTextures()) {
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_LIGHT0);
                RegisteredPlayerData.getInstance().drawTextures(false);
                Replay.getInstance().AddChangeViewEvent(false);
            } else {
                // Passa a desenhar texturas
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_LIGHT0);
                RegisteredPlayerData.getInstance().drawTextures(true);
                Replay.getInstance().AddChangeViewEvent(true);
            }
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {

            try {
                Keyboard.destroy();
                Keyboard.create();
            } catch (LWJGLException ex) {
                Logger.getLogger(GameAreaController.class.getName()).log(Level.SEVERE, null, ex);
            }
            GameState.changeState(GameStateType.PAUSED);
        }


        // TODO: Apresentar menu de pausa e alterar o estado de jogo
        if (ElapsedTime.milisecondsSince(mKeyMovementLastUpdate) > 5) {
            mKeyMovementLastUpdate = System.nanoTime();

            if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
                mModel.parseKey(Keyboard.KEY_LEFT);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
                mModel.parseKey(Keyboard.KEY_RIGHT);
            }
        }


        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            mModel.parseKey(Keyboard.KEY_SPACE);
        }
    }

    private void processMouse() {

        if (MouseLastX != Mouse.getX() || MouseLastY != Mouse.getY() || Mouse.isButtonDown(0)) {
            mModel.parseMouse(Mouse.getX(), Mouse.getY(), Mouse.isButtonDown(0));
            MouseLastX = Mouse.getX();
            MouseLastY = Mouse.getY();
        }


    }

    @Override
    public void parseInput() {

        if (GameState.currentState() != GameStateType.PLAYING) {
            return;
        }

        processKeyboard();
        processMouse();
    }

    @Override
    public void update() {
        if (GameState.currentState() == GameStateType.REPLAYING) {
            Replay.getInstance().tick(mModel);
            return;
        }
        mModel.update();
    }
}
