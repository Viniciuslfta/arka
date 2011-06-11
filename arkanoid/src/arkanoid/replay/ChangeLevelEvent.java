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
public class ChangeLevelEvent extends GameEvent{

    String mLevelName;
    int mPoints;
    public ChangeLevelEvent(String _levelName, int _points, long _timeStamp) {
        super(_timeStamp);
        mLevelName = _levelName;
        mPoints = _points;
    }

    @Override
    public void execute(ModelPlayArea _model) {
        _model.getPlayer().setScore(mPoints);
        _model.changeLevel(mLevelName);
    }
}
