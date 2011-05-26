/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.models.entities.Bonus;

import arkanoid.BaseColor;
import arkanoid.Settings;
import arkanoid.models.entities.PlayArea;

/**
 *
 * @author sPeC
 */
public class BonusExpansion extends Bonus{

    public BonusExpansion(float _x, float _y) {
        super(_x, _y, new BaseColor(1,1,1));
    }

    @Override
    public void onClubCollision(PlayArea _area) {
        _area.getClub().setWidth(_area.getClub().getWidth() + 50);
    }
    
    @Override
    public void undoEffect(PlayArea _area)
    {
        _area.getClub().setWidth(Settings.CLUB_WIDTH);
    }
    
}
