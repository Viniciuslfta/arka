package arkanoid.models.entities.Bonus;

import arkanoid.BaseColor;
import arkanoid.Settings;
import arkanoid.models.entities.PlayArea;

/**
 *
 * @author Filipe
 */
public class BonusExpand extends Bonus {

    public BonusExpand(float _x, float _y) {
        super(_x, _y, new BaseColor(1.0f, 1.0f, 1.0f));
    }

    @Override
    public void onClubCollision(PlayArea _area) {

        if (_area.getActiveBonus() != null) {
            _area.getActiveBonus().undo(_area);
        }

        _area.getClub().setWidth(_area.getClub().getWidth() + 50);

        _area.setActiveBonus(this);
    }

    @Override
    public void undo(PlayArea _area) {
        _area.getClub().setWidth(Settings.CLUB_WIDTH);
    }
}
