/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.models.entities;
import arkanoid.Collidable;
import arkanoid.Settings;
import arkanoid.BaseColor;
import arkanoid.ElapsedTime;
/**
 *
 * @author sPeC
 */
public class Bonus extends Collidable {
    
    private long mLastUpdate;
    
    private BaseColor mColor;
    

    
    public Bonus( float _x, float _y) {
        super(_x,_y,Settings.BONUS_SIZE,Settings.BONUS_SIZE);
        mColor = new BaseColor(1,1,1);
        mLastUpdate = System.nanoTime();
    };
    
        
    /** Retorna objecto BaseColor que indica cor do tijolo
     * @return objecto BaseColor que indica cor do tijolo
     */
    public BaseColor getColor() {
        return mColor;
    }
    
    public void updatePosition() {
           
        double time = ElapsedTime.microsecondsSince(mLastUpdate);
        
        float y = (float) (getY() + (Settings.BONUS_VELOCITY * ElapsedTime.microsecondsSince(mLastUpdate)));
        super.updatePosition(this.getX(), y);
        
        mLastUpdate = System.nanoTime();
    }
    
    public void onClubCollision(PlayArea _area) {
        _area.getClub().setWidth(_area.getClub().getWidth()+10);
    }
    
}
