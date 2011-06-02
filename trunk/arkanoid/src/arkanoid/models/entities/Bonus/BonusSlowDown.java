/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.models.entities.Bonus;

import arkanoid.BaseColor;
import arkanoid.Settings;
import arkanoid.models.entities.PlayArea;

/**
 *
 * @author sPeC
 */
public class BonusSlowDown extends Bonus{

    public BonusSlowDown(float _x, float _y) {
        super(_x, _y, new BaseColor(0,1,0));
    }

    @Override
    public void onClubCollision(PlayArea _area) {
        _area.getBall().setVelocityX(Settings.BALL_MIN_VEL);
        _area.getBall().setVelocityY(Settings.BALL_MIN_VEL);
    }
    
    @Override
    public void undoEffect(PlayArea _area)
    {
        _area.getBall().setVelocityX(_area.getBall().getVelocityX()*2);
        _area.getBall().setVelocityY(_area.getBall().getVelocityY()*2);
    }
}
