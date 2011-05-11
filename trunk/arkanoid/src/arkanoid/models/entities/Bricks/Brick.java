/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.models.entities.Bricks;

import arkanoid.Collidable;
import arkanoid.BaseColor;
import arkanoid.Settings;
import arkanoid.models.entities.Ball;
import arkanoid.models.entities.PlayArea;
import java.awt.Point;


/**
 *
 * @author sPeC
 */
public abstract class Brick extends Collidable {

    private Point mLocationOnPlayArea;
    
    private int mNumberOfAllowedHits;
    private int mNumberOfOcurredHits;
    
    private BaseColor mColor;

    public BaseColor getColor() {
        return mColor;
    }
    private boolean mIsActive;

    public boolean isActive() {
        return mIsActive;
    }

    
    public void reset()
    {
        mIsActive = true;
        mNumberOfOcurredHits = 0;
    }
    
    public void setIsActive(boolean _value) {
        mIsActive = _value;
    }

    public void setLocationOnPlayArea(int _linha, int _col)
    {
        mLocationOnPlayArea = new Point(_linha, _col);
    }
    
    abstract public void onBallCollision(PlayArea _area);
     
    protected void onBallCollision(Ball _ball) {
            
        // Cima / Baixo
        if (((_ball.getY() < getY() -Settings.BALL_SIZE/2 )
            || (_ball.getY() + Settings.BALL_SIZE/2 > getY() + getHeight()))
            && (_ball.getX()>getX()-Settings.BALL_SIZE/4 && (_ball.getX()< getX() + getWidth()-Settings.BALL_SIZE/4 ))) {
            
            _ball.setVelocityY(_ball.getVelocityY() * -1);
        } else {
            // Esquerda / Direita
            _ball.setVelocityX(_ball.getVelocityX() * -1);
        }

        
        if(++mNumberOfOcurredHits >= mNumberOfAllowedHits)
            mIsActive = false;
    }

    public Brick(int _x, int _y, int _allowedHits, BaseColor _color) {
        super(_x, _y, Settings.BRICK_WIDTH, Settings.BRICK_HEIGHT);
        
        mNumberOfOcurredHits = 0;
        mNumberOfAllowedHits = _allowedHits;
        mColor = _color;
    }
}
