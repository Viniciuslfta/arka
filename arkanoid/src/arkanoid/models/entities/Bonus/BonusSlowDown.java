/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.models.entities.Bonus;

import arkanoid.BaseColor;
import arkanoid.Settings;
import arkanoid.Textures;
import arkanoid.models.entities.PlayArea;

/**
 *
 * @author sPeC
 */
public class BonusSlowDown extends Bonus{

    float mOldSpeedX;
    float mOldSpeedY;
    
    public BonusSlowDown(float _x, float _y) {
        super(_x, _y, new BaseColor(0,1,0),Textures.getInstance().getBonusSlowDown());
    }

    @Override
    public void onClubCollision(PlayArea _area) {
        mOldSpeedX = _area.getBall().getVelocityX();
        mOldSpeedY = _area.getBall().getVelocityY();
        
        float speedX = mOldSpeedX > 0 ? Settings.BALL_MIN_VEL : -Settings.BALL_MIN_VEL;
        float speedY = mOldSpeedY > 0 ? Settings.BALL_MIN_VEL : -Settings.BALL_MIN_VEL;
        
        _area.getBall().setVelocityX(speedX);
        _area.getBall().setVelocityY(speedY);
    }
    
    @Override
    public void undoEffect(PlayArea _area)
    {
        _area.getBall().setVelocityX(_area.getBall().getVelocityX()*2);
        _area.getBall().setVelocityY(_area.getBall().getVelocityY()*2);
    }
}
