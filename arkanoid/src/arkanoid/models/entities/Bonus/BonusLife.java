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
public class BonusLife extends Bonus{

    public BonusLife(float _x, float _y) {
        super(_x, _y, new BaseColor(1,0,0));
    }

    @Override
    public void onClubCollision(PlayArea _area) {
        _area.getPlayer().addLifes(1);
    }
    
}
