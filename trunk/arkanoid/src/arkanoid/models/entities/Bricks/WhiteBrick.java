/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.models.entities.Bricks;

import arkanoid.BaseColor;
import arkanoid.Textures;
import arkanoid.models.entities.PlayArea;
import org.newdawn.slick.opengl.Texture;

/** Representa um tijolo branco ( extensão de Brick )
 *
 * @author sPeC
 */
public class WhiteBrick extends Brick {

    /** Constructor da classe.
     * 
     * @param _x valor da posição em X com o qual será instanciado o objecto
     * @param _y valor da posição em Y com o qual será instanciado o objecto
     */
    public WhiteBrick(int _x, int _y) {
        super(_x, _y, 1, new BaseColor(1.0f, 1.0f, 1.0f), Textures.getInstance().getWhiteBrick());
    }

    @Override
    public Texture getTexture() {
        if (mTexture == null) {
            mTexture = Textures.getInstance().getWhiteBrick();
        }
        return mTexture;
    }

    /** Implementa função que define efeito provocado pela colisão da Bola no Tijolo
     * 
     * @param _area Area de jogo a que pertence o Tijolo 
     */
    @Override
    public void onBallCollision(PlayArea _area) {
        super.onBallCollision(_area.getBall());

        _area.getPlayer().addScorePoints(100);


    }
}
