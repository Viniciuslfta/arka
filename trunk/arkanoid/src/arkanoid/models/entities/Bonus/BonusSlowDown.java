/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.models.entities.Bonus;

import arkanoid.BaseColor;
import arkanoid.Settings;
import arkanoid.Textures;
import arkanoid.models.entities.PlayArea;
import org.newdawn.slick.opengl.Texture;

/**
 *
 * @author sPeC
 */
public class BonusSlowDown extends Bonus {

    float mOldSpeedX;
    float mOldSpeedY;

    
    @Override
    public Texture getTexture() {
        if (mTexture == null) {
            mTexture = Textures.getInstance().getBonusSlowDown();
        }
        return mTexture;
    }
        
    
    public BonusSlowDown(float _x, float _y) {
        super(_x, _y, new BaseColor(0, 1, 0), Textures.getInstance().getBonusSlowDown());
    }

    @Override
    public void onClubCollision(PlayArea _area) {
        mOldSpeedX = _area.getBall().getVelocityX();
        mOldSpeedY = _area.getBall().getVelocityY();

        float speedX = mOldSpeedX > 0 ? Settings.BALL_MIN_VEL : Settings.BALL_MIN_VEL * -1;
        float speedY = mOldSpeedY > 0 ? Settings.BALL_MIN_VEL : Settings.BALL_MIN_VEL * -1;

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
                currSpeedX = mOldSpeedX * -1;
            }
        } else {
            if (mOldSpeedX < 0) {
                currSpeedX = mOldSpeedX;
            } else {
                currSpeedX = mOldSpeedX * -1;
            }
        }

        // Direcção Y
        if (currSpeedY > 0) {
            if (mOldSpeedY > 0) {
                currSpeedY = mOldSpeedY;
            } else {
                currSpeedY = mOldSpeedY * -1;
            }
        } else {
            if (mOldSpeedY < 0) {
                currSpeedY = mOldSpeedY;
            } else {
                currSpeedY = mOldSpeedY * -1;
            }
        }

        // Finalmente aplica as novas velocidades à bola
        _area.getBall().setVelocityX(currSpeedX);
        _area.getBall().setVelocityY(currSpeedY);
    }
}
