/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.models.entities;

import arkanoid.Collidable;
import arkanoid.BaseColor;
import arkanoid.ElapsedTime;
import arkanoid.Settings;

/**
 *
 * @author sPeC
 */
public class Ball extends Collidable {

    private long mLastUpdate;
    private boolean mIsGluedToClub;

    public boolean isGluedToClub() {
        return mIsGluedToClub;
    }

    public void setIsGluedToClub(boolean _value) {
        this.mIsGluedToClub = _value;
        mLastUpdate = System.nanoTime();
    }
    private float mVelocityX;

    public float getVelocityX() {
        return mVelocityX;
    }

    public void setVelocityX(float _velocityX) {
        if (Settings.BALL_MAX_VEL < Math.abs(_velocityX)) {
            mVelocityX = Settings.BALL_MAX_VEL;
        } else if (Settings.BALL_MIN_VEL > Math.abs(_velocityX)) {
            mVelocityX = Settings.BALL_MIN_VEL;
        } else {
            mVelocityX = _velocityX;
        }
    }
    private float mVelocityY;

    public float getVelocityY() {
        return mVelocityY;
    }

    public void setVelocityY(float _velocityY) {
        if (Settings.BALL_MAX_VEL < Math.abs(_velocityY)) {
            mVelocityY = Settings.BALL_MAX_VEL;
        } else if (Settings.BALL_MIN_VEL > Math.abs(_velocityY)) {
            mVelocityY = Settings.BALL_MIN_VEL;
        } else {
            mVelocityY = _velocityY;
        }
    }
    private BaseColor mColor;

    public BaseColor getColor() {
        return mColor;
    }

    public void updatePosition() {
        double time = ElapsedTime.microsecondsSince(mLastUpdate);
        mLastUpdate = System.nanoTime();

        float x = (float) (getX() + mVelocityX * time);
        float y = (float) (getY() + mVelocityY * time);

        updatePosition(x, y);

    }

    public Ball(int _x, int _y) {
        super(_x, _y, Settings.BALL_SIZE, Settings.BALL_SIZE);

        mLastUpdate = System.nanoTime();
        mColor = new BaseColor(1.0f, 1.0f, 0.0f);
    }
}
