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
public class BonusSlowDown extends Bonus {

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
    public void undoEffect(PlayArea _area) {

        float currSpeedX = _area.getBall().getVelocityX();
        float currSpeedY = _area.getBall().getVelocityY();

        // Temos que nos certificar que as direcções são consistentes
        
        // Direcçao X
        if (currSpeedX > 0) {
            if (mOldSpeedX > 0) {
                currSpeedX = mOldSpeedX;
            } else {
                currSpeedX = -mOldSpeedX;
            }
        } else {
            if (mOldSpeedX < 0) {
                currSpeedX = mOldSpeedX;
            } else {
                currSpeedX = -mOldSpeedX;
            }
        }

        // Direcção Y
        if (currSpeedY > 0) {
            if (mOldSpeedY > 0) {
                currSpeedY = mOldSpeedY;
            } else {
                currSpeedY = -mOldSpeedY;
            }
        } else {
            if (mOldSpeedY < 0) {
                currSpeedY = mOldSpeedY;
            } else {
                currSpeedY = -mOldSpeedY;
            }
        }
        
        // Finalmente aplica as novas velocidades à bola
        _area.getBall().setVelocityX(currSpeedX);
        _area.getBall().setVelocityX(currSpeedY);
    }
}
