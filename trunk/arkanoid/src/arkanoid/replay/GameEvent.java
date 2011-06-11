/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.replay;

import arkanoid.models.ModelPlayArea;
import java.io.Serializable;

/**
 *
 * @author sPeC
 */
abstract public class GameEvent implements Serializable{

    long mTimeStamp;
    public long getTimeStamp(){
        return mTimeStamp;
    }
    
    GameEvent(long _timestamp) {
        mTimeStamp = _timestamp;
    }

    public abstract void execute(ModelPlayArea _model);

    enum EventType {

        MOUSE_INPUT,
        KEYBOARD_INPUT,
    }
}
