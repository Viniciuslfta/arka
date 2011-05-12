/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.controllers;

import arkanoid.GameState;
import arkanoid.GameState.GameStateType;
import arkanoid.models.ModelPlayArea;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;


/**
 *
 * @author sPeC
 */
public class GameAreaController implements ArkanoidController {
        
    private ModelPlayArea mModel;

    public ModelPlayArea getModelPlayArea() {
        return mModel;
    }

        
    /**
     * Constructor para objectos da classe GameAreaController
     */
    public GameAreaController(ModelPlayArea _m)
    {
        // initialise instance variables
        mModel = _m;
    }

    private void processKeyboard() {
       
        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            System.exit(0);
        }
        // TODO: Apresentar menu de pausa e alterar o estado de jogo

    }

    private void processMouse() {
        
        mModel.parseMouse(Mouse.getX(), Mouse.getY(), Mouse.isButtonDown(0)) ;
    }
  
    @Override
    public void parseInput() {
        if( GameState.currentState() != GameStateType.PLAYING)
            return;
        
        processKeyboard();
        processMouse();
    }

    @Override
    public void update() {
       mModel.update();
    }
}
