/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.models.entities.Bonus;

import arkanoid.GameState;
import arkanoid.RegisteredPlayerData;
import arkanoid.replay.Replay;
import java.util.Random;

/**
 *
 * @author sPeC
 */
public class BonusUtils {

    private static Random mRandom = null;

    public static void setRandomSeed(long _randomSeed) {
        mRandom = new Random(_randomSeed);
    }

    public static void initRandom() {
        long randomSeed = System.currentTimeMillis();
        mRandom = new Random(randomSeed);

        if (RegisteredPlayerData.getInstance().isLoggedIn()
                && GameState.currentState() != GameState.GameStateType.REPLAYING) {
            Replay.getInstance().AddChangeBonusSeedEvent(randomSeed);
        }
    }

    public static Bonus getRandomBonus(float _posX, float _posY) {
        int num = mRandom.nextInt(5);

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
                return new BonusInvert(_posX, _posY);
        }
    }
}
