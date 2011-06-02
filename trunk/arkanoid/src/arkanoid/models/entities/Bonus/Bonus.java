package arkanoid.models.entities.Bonus;

import arkanoid.Collidable;
import arkanoid.Settings;
import arkanoid.BaseColor;
import arkanoid.ElapsedTime;
import arkanoid.models.entities.PlayArea;

/**
 *
 * @author sPeC
 */
public abstract class Bonus extends Collidable {

    private long mLastUpdate;

    public void setLastUpdate(long _lastUpdate) {
        this.mLastUpdate = _lastUpdate;
    }
    private BaseColor mColor;

    public Bonus(float _x, float _y, BaseColor _color) {
        super(_x, _y, Settings.BONUS_SIZE, Settings.BONUS_SIZE);
        mColor = _color;
        mLastUpdate = System.nanoTime();
    }

    /** Retorna objecto BaseColor que indica cor do bonus
     * @return objecto BaseColor que indica cor do bonus
     */
    public BaseColor getColor() {
        return mColor;
    }

    public void updatePosition() {

        double time = ElapsedTime.microsecondsSince(mLastUpdate);

        float y = (float) (getY() + (Settings.BONUS_VELOCITY * ElapsedTime.microsecondsSince(mLastUpdate)));
        super.updatePosition(this.getX(), y);

        mLastUpdate = System.nanoTime();
    }

    abstract public void onClubCollision(PlayArea _area);

    public void undoEffect(PlayArea _Area) {
    }
}
