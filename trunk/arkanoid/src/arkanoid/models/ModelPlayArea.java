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

    public void parseMouse(int _x, int _y, boolean _clicked) {
        mArea.parseMouse(_x, _clicked);
    }

    public GameLevel getLoadedLevel() {
        return mArea.getCurrentLevel();
    }

    public Club getClub() {
        return mArea.getClub();
    }
    
    public Ball getBall()
    {
        return mArea.getBall();
    }
    
    public Wall[] getWalls()
    {
        return mArea.getWalls();
    }
    
    public Player getPlayer()
    {
        return mArea.getPlayer();
    }
}
