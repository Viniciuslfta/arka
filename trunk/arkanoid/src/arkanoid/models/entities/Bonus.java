/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.models.entities;
import arkanoid.Collidable;
import arkanoid.Settings;
import arkanoid.BaseColor;
/**
 *
 * @author sPeC
 */
public class Bonus extends Collidable {
    
    private BaseColor mColor;
    

    
    public Bonus( float _x, float _y) {
        super(_x,_y,Settings.BONUS_SIZE,Settings.BONUS_SIZE);
        mColor = new BaseColor(1,1,1);
    };
    
        
    /** Retorna objecto BaseColor que indica cor do tijolo
     * @return objecto BaseColor que indica cor do tijolo
     */
    public BaseColor getColor() {
        return mColor;
    }
    
    public void updatePosition() {
        float y = (float) (getY() + Settings.BONUS_VELOCITY);
        super.updatePosition(this.getX(), y);
    }
    
    public void onClubCollision(PlayArea _area) {
        _area.getClub().setWidth(_area.getClub().getWidth()+10);
    }
    
}
