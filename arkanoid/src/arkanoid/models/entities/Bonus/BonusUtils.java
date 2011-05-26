/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.models.entities.Bonus;

/**
 *
 * @author sPeC
 */
public class BonusUtils {

    public static Bonus getRandomBonus(float _posX, float _posY) {

        // TODO: Adicionar o bonus nosso
        int num = (int) (Math.random() * 4);

        switch (num) {
            case 0:
                return new BonusExpansion(_posX, _posY);
            case 1:
                return new BonusGlue(_posX, _posY);
            case 2:
                return new BonusLife(_posX, _posY);
            case 3:
                return new BonusSlowDown(_posX, _posY);
            default:
                return new BonusSlowDown(_posX, _posY);
        }
    }
}
