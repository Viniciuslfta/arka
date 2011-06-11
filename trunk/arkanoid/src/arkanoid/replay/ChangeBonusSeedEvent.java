/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.replay;

import arkanoid.models.ModelPlayArea;
import arkanoid.models.entities.Bonus.BonusUtils;

/**
 *
 * @author sPeC
 */
public class ChangeBonusSeedEvent extends GameEvent{

    long mSeed;
    ChangeBonusSeedEvent(long _seed, long _timeStamp)
    {
        // Queremos que este evento seja disparado o mais rápido possivel
        super(0);
        mSeed = _seed;
    }
    
    @Override
    public void execute(ModelPlayArea _model) {
        BonusUtils.setRandomSeed(mSeed);
    }
    
}
