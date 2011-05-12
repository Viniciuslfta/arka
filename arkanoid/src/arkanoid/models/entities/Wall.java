/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.models.entities;

import arkanoid.Collidable;
import arkanoid.BaseColor;
import arkanoid.Settings;

/**
 *
 * @author sPeC
 */
public class Wall extends Collidable {

    public enum WallType {

        TOP,
        LEFT,
        RIGHT
    }
    private WallType mType;
    private BaseColor mColor;

    public BaseColor getColor() {
        return mColor;
    }

    public Wall(int _x, int _y, int _width, int _height, WallType _type) {
        super(_x, _y, _width, _height);

        mType = _type;


        mColor = new BaseColor(0.0f, 1.0f, 0.5f);
    }

    public void doEffectOnClub(Club _club)
    {
        switch (mType) {
            case LEFT:
                _club.setX(getX() + getWidth());
                break;
            case RIGHT:
                _club.setX(getX() - _club.getWidth());
                break;
        }
    }
    
    public void doEffectOnBall(Ball _ball) {
        // Altera as velocidades da bola
        float velX = _ball.getVelocityX();
        float velY = _ball.getVelocityY();

        switch (mType) {
            case TOP:
                velY *= -1;
                _ball.setY(getY() + getHeight());
                break;
            case LEFT:
                velX *= -1;
                _ball.setX(getX() + getWidth());
                break;
            case RIGHT:
                velX *= -1;
                _ball.setX(getX() - Settings.BALL_SIZE);
                break;
        }

        _ball.setVelocityX(velX);
        _ball.setVelocityY(velY);
    }
}
