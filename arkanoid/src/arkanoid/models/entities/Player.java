/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.models.entities;

import arkanoid.RegisteredPlayerData;
import java.util.Observable;

/**
 *
 * @author sPeC
 */
public class Player extends Observable {

    private boolean mIsLoggedIn;
    // Vidas
    private int mLifes;

    public int getLifes() {
        return mLifes;
    }

    public void setLifes(int _quant) {
        this.mLifes = _quant;
    }

    public void addLifes(int _quant) {
        this.mLifes += _quant;
    }

    public void removeLifes(int _quant) {
        this.mLifes -= _quant;
    }
    // Pontos
    private long mScore;

    public long getScore() {
        return mScore;
    }

    public void setScore(long _newScore) {
        this.mScore = _newScore;
    }

    public void addScorePoints(int _quant) {
        this.mScore += _quant;
    }
    RegisteredPlayerData mRegData;
}
