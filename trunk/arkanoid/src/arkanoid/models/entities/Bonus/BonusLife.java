/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.models.entities.Bonus;

import arkanoid.BaseColor;
import arkanoid.Textures;
import arkanoid.models.entities.PlayArea;
import org.newdawn.slick.opengl.Texture;

/**
 *
 * @author sPeC
 */
public class BonusLife extends Bonus{

    public BonusLife(float _x, float _y) {
        super(_x, _y, new BaseColor(1,0,0),Textures.getInstance().getBonusLife());
    }

    @Override
    public Texture getTexture() {
        if (mTexture == null) {
            mTexture = Textures.getInstance().getBonusLife();
        }
        return mTexture;
    }
        
    
    @Override
    public void onClubCollision(PlayArea _area) {
        _area.getPlayer().addLifes(1);
    }
    
}
