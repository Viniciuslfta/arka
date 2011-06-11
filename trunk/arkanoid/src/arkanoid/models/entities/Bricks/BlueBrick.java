package arkanoid.models.entities.Bricks;

import arkanoid.BaseColor;
import arkanoid.Textures;

import arkanoid.models.entities.PlayArea;
import arkanoid.models.entities.Bonus.BonusUtils;
import org.newdawn.slick.opengl.Texture;

/** Representa um tijolo azul ( extensão de Brick )
 *
 * @author sPeC
 */
public class BlueBrick extends Brick {

    /** Implementa função que define efeito provocado pela colisão da Bola no Tijolo
     * 
     * @param _area Area de jogo a que pertence o Tijolo 
     */
    @Override
    public void onBallCollision(PlayArea _area) {
        super.onBallCollision(_area.getBall());

        _area.getPlayer().addScorePoints(100);
        _area.addBonus(BonusUtils.getRandomBonus(getX(), getY()));

    }

    /** Constructor da classe.
     * 
     * @param _x valor da posição em X com o qual será instanciado o objecto
     * @param _y valor da posição em Y com o qual será instanciado o objecto
     */
    public BlueBrick(int _x, int _y) {
        super(_x, _y, 1, new BaseColor(0.0f, 0.0f, 1.0f), Textures.getInstance().getBlueBrick());
    }

    @Override
    public Texture getTexture() {
        if (mTexture == null) {
            mTexture = Textures.getInstance().getBlueBrick();
        }
        return mTexture;
    }
}
