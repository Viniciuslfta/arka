/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.models.entities.Bonus;

import arkanoid.BaseColor;
import arkanoid.models.entities.PlayArea;

/**
 *
 * @author Filipe
 */
public class BonusCalm extends Bonus{
    public BonusCalm(float _x, float _y){
        super(_x, _y, new BaseColor(0.0f,1.0f,0.0f));
    }
    
    
    @Override
    public void onClubCollision(PlayArea _area) {
        if (_area.getActiveBonus() != null) {
            _area.getActiveBonus().undo(_area);
        }
        
        _area.getBall().setVelocityX(_area.getBall().getVelocityX()/2);
        _area.getBall().setVelocityY(_area.getBall().getVelocityY()/2);
        
        _area.setActiveBonus(this);
    }
    
    @Override
    public void undo(PlayArea _area) {
        _area.getBall().setVelocityX(_area.getBall().getVelocityX()*2);
        _area.getBall().setVelocityY(_area.getBall().getVelocityY()*2);
    }
}
