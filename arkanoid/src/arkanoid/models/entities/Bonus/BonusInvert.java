/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.models.entities.Bonus;
import arkanoid.BaseColor;
import arkanoid.Settings;
import arkanoid.Textures;
import arkanoid.models.entities.PlayArea;
import org.newdawn.slick.opengl.Texture;

/**
 *
 * @author Filipe
 */
public class BonusInvert extends Bonus {
 
    
    public BonusInvert(float _x, float _y) {
        super(_x, _y, new BaseColor(0.5f,0.5f,0.5f),Textures.getInstance().getBonusInvert());
    }

    
    @Override
    public Texture getTexture() {
        if (mTexture == null) {
            mTexture = Textures.getInstance().getBonusInvert();
        }
        return mTexture;
    }
        
    
    @Override
    public void onClubCollision(PlayArea _area) {
        _area.setClubKeyMoveSpeed(Settings.CLUB_KEY_MOVE_SPEED*(-1));
    }
    
    
    @Override
    public void undoEffect(PlayArea _area) {
        _area.setClubKeyMoveSpeed(Settings.CLUB_KEY_MOVE_SPEED);
    }
}
