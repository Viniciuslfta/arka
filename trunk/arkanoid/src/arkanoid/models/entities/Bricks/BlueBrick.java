package arkanoid.models.entities.Bricks;

import arkanoid.BaseColor;
import arkanoid.models.entities.Bonus.BonusCalm;
import arkanoid.models.entities.Bonus.BonusExpand;
import arkanoid.models.entities.Bonus.BonusGlue;
import arkanoid.models.entities.Bonus.BonusLife;
import arkanoid.models.entities.PlayArea;

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

        if (!this.isActive()) {
            _area.getPlayer().addScorePoints(100);
            
            //gera bonus
            int random = (int)(Math.random()*3);
            switch (random) {
                case 0: //bonus cola
                    _area.addBonus(new BonusGlue(getX(),getY()));
                    break;
                case 1: //bonus expansao
                    _area.addBonus(new BonusExpand(getX(),getY()));
                    break;
                case 2: //bonus vida
                    _area.addBonus(new BonusLife(getX(),getY()));
                    break;
                case 3: //bonus calma
                    _area.addBonus(new BonusCalm(getX(),getY()));
                    break;
            } 
        }
    }

    /** Constructor da classe.
     * 
     * @param _x valor da posição em X com o qual será instanciado o objecto
     * @param _y valor da posição em Y com o qual será instanciado o objecto
     */
    public BlueBrick(int _x, int _y) {
        super(_x, _y, 1, new BaseColor(0.0f, 0.0f, 1.0f));
    }
}
