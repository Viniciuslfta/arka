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
public class TickEvent extends GameEvent {

    public int mOriginalQuantity;
    public int mQuantity;

    public TickEvent(int _quantity, long _timeStamp) {
        super(_timeStamp);
        mOriginalQuantity = _quantity;
        mQuantity = _quantity;
    }

    public void reset() {
        mQuantity = mOriginalQuantity;
    }

    @Override
    public void execute(ModelPlayArea _model) {
        _model.update();
    }
}
