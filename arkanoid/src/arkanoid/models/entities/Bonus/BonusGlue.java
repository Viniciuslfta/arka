/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.models.entities.Bonus;

import arkanoid.BaseColor;
import arkanoid.models.entities.PlayArea;

/**
 *
 * @author sPeC
 */
public class BonusGlue extends Bonus{

    public BonusGlue(float _x, float _y) {
        super(_x, _y, new BaseColor(0,0,1));
    }

    @Override
    public void onClubCollision(PlayArea _area) {
        _area.getBall().setSticky(true);
    }
    @Override
    public void undoEffect(PlayArea _area) {
        _area.getBall().setSticky(false);
    }
}
