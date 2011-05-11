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
public class Club extends Collidable {

    private BaseColor mColor;

    public BaseColor getColor() {
        return mColor;
    }

    public void placeBallAtCenter(Ball _ball)
    {
        // Coloca a bola centrada no taco
        float x = getX() + getWidth() / 2 - Settings.BALL_SIZE / 2;
        float y = getY() - Settings.BALL_SIZE;
        _ball.updatePosition(x, y);
    }
    
    public Club(int _x, int _y) {
        super(_x, _y, Settings.CLUB_WIDTH, Settings.CLUB_HEIGHT);

        mColor = new BaseColor(0.5f, 0.5f, 0.5f);
    }

    
}
