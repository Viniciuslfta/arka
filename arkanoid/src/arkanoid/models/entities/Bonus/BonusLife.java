package arkanoid.models.entities.Bonus;
import arkanoid.BaseColor;
import arkanoid.models.entities.PlayArea;


public class BonusLife extends Bonus {
    
    public BonusLife(float _x, float _y){
        super(_x, _y, new BaseColor(1.0f,0.0f,0.0f));
    }
    
    
    @Override
    public void onClubCollision(PlayArea _area) {
                
        _area.getPlayer().setLifes(_area.getPlayer().getLifes()+1);
    }
    
    @Override
    public void undo(PlayArea _area) {
        //sou obrigado a fazer override desta forma para a classe não ser considerada abstracta?? :s
    }
    
}
