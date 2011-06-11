/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.replay;

import arkanoid.models.ModelPlayArea;

/**
 *
 * @author sPeC
 */
public class MouseEvent extends GameEvent{

    int mPosX;
    boolean mClicked;
    
    MouseEvent(float _posX, boolean _clicked, long _timeStamp)
    {
        super(_timeStamp);
        mPosX = (int)_posX;
        mClicked = _clicked;
    }
    
    @Override
    public void execute(ModelPlayArea _model) {
        _model.parseMouse(mPosX, 0, mClicked);
    }
    
}
