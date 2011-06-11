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
public class KeyPressEvent extends GameEvent{
    int mKeyPressed;
    KeyPressEvent(int _key, long _timeStamp)
    {
        super(_timeStamp);
        mKeyPressed = _key;
    }
    
    @Override
    public void execute(ModelPlayArea _model) {
        _model.parseKey(mKeyPressed);
    }
}
