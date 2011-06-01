/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.models.entities.Bricks;

import arkanoid.BaseColor;
import arkanoid.models.entities.PlayArea;
import java.awt.Point;

/** Representa um tijolo vermelho ( extensão de Brick )
 *
 * @author sPeC
 */
public class RedBrick extends Brick {

    /** Constructor da classe.
     * 
     * @param _x valor da posição em X com o qual será instanciado o objecto
     * @param _y valor da posição em Y com o qual será instanciado o objecto
     */
    public RedBrick(int _x, int _y) {
        super(_x, _y, 1, new BaseColor(1.0f, 0.0f, 0.0f));
    }

    /** Implementa função que define efeito provocado pela colisão da Bola no Tijolo
     * 
     * @param _area Area de jogo a que pertence o Tijolo 
     */
    @Override
    public void onBallCollision(PlayArea _area) {
        super.onBallCollision(_area.getBall());

        _area.getPlayer().addScorePoints(100);

        Point tempLocation = getLocationOnPlayArea();

        int linhaActual = tempLocation.x - 1;
        int colunaActual = tempLocation.y - 1;

        linhaActual = linhaActual >= 0 ? linhaActual : 0;
        colunaActual = colunaActual >= 0 ? colunaActual : 0;

        mIsActive = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Brick tempBrick = _area.getCurrentLevel().getBrick(linhaActual + i, colunaActual + j);

                if (tempBrick != null && tempBrick.isActive()) {
                    tempBrick.onBallCollision(_area);
                    
                    if( !tempBrick.isActive())
                        _area.getCurrentLevel().incNumberOfDestroyedBricks();
                }

            }
        }

    }
}
