/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.controllers;

import arkanoid.ElapsedTime;
import arkanoid.GameState;
import arkanoid.GameState.GameStateType;
import arkanoid.models.ModelPlayArea;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;


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

    private void processKeyboard() {


        Keyboard.poll();
        
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {

            GameState.changeState(GameStateType.PAUSED);
            
            try {
                Thread.sleep(250);
            } catch (InterruptedException ex) {
                   Logger.getLogger(GameAreaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


        // TODO: Apresentar menu de pausa e alterar o estado de jogo
        if (ElapsedTime.microsecondsSince(mKeyMovementLastUpdate) > 1) {
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
        mModel.update();
    }
}
