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
public class RedBrick extends Brick {

    public RedBrick(int _x, int _y) {
        super(_x, _y, 1, new BaseColor(1.0f, 0.0f, 0.0f));
    }

    @Override
    public void onBallCollision(PlayArea _area) {
        super.onBallCollision(_area.getBall());

        if (!this.isActive()) {
            _area.getPlayer().addScorePoints(100);
        }
    }
}
