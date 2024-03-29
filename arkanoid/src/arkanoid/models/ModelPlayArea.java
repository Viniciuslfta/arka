/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.models;

import arkanoid.models.entities.Ball;
import arkanoid.models.entities.Club;
import arkanoid.models.entities.GameLevel;
import arkanoid.models.entities.PlayArea;
import arkanoid.models.entities.Player;
import arkanoid.models.entities.Wall;

import java.util.List;
import arkanoid.models.entities.Bonus.Bonus;

/**
 *
 * @author sPeC
 */
public class ModelPlayArea {

    PlayArea mArea;

    public ModelPlayArea(PlayArea _area) {
        this.mArea = _area;
    }

    public void update() {
        mArea.tick();
    }

    public void parseKey(int _key) {
        mArea.parseKey(_key);
    }

    public void parseMouse(int _x, int _y, boolean _clicked) {
        mArea.parseMouse(_x, _clicked);
    }

    public GameLevel getLoadedLevel() {
        return mArea.getCurrentLevel();
    }

    public Club getClub() {
        return mArea.getClub();
    }

    public Ball getBall() {
        return mArea.getBall();
    }

    public Wall[] getWalls() {
        return mArea.getWalls();
    }

    public Player getPlayer() {
        return mArea.getPlayer();
    }

    public List<Bonus> getBonus() {
        return mArea.getBonus();
    }

    public Bonus getActiveBonus() {
        return mArea.getActiveBonus();
    }

    public void saveGame(String _path) {
        mArea.SaveGame(_path);
    }

    public boolean loadGame(String _path) {
        PlayArea tmpArea = PlayArea.LoadGame(_path);

        if (tmpArea != null) {
            mArea = tmpArea;
        }

        return tmpArea != null;
    }

    public void ResetElapsedTime() {
        mArea.ResetElapsedTime();
    }

    public void changeLevel(String _lvlName) {
        mArea.changeLevel(_lvlName);
    }
    
    public void saveLastTicks()
    {
        mArea.saveLastTicks();
    }
}
