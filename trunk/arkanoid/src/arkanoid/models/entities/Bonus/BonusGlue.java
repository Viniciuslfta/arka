package arkanoid.models.entities.Bonus;

import arkanoid.BaseColor;
import arkanoid.models.entities.PlayArea;

/**
 *
 * @author Filipe
 */


public class BonusGlue extends Bonus {
    
    public BonusGlue(float _x, float _y){
        super(_x, _y, new BaseColor(0.0f,0.0f,1.0f));
    }
    
    
    @Override
    public void onClubCollision(PlayArea _area) {
        _area.getBall().setSticky(true);
    }
    
    @Override
    public void undo(PlayArea _area) {
        _area.getBall().setSticky(false);
    }
}
