/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.models.entities.Bricks;

import arkanoid.BaseColor;
import arkanoid.models.entities.PlayArea;

/**
 *
 * @author sPeC
 */
public class GreenBrick extends Brick {

    public GreenBrick(int _x, int _y) {
        super(_x, _y, 2, new BaseColor(0.0f, 1.0f, 0.0f));
    }

    @Override
    public void onBallCollision(PlayArea _area) {
        super.onBallCollision(_area.getBall());


        _area.getPlayer().addScorePoints(50);


    }
}
