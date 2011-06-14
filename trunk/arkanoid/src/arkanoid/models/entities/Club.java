package arkanoid.models.entities;

import arkanoid.Collidable;
import arkanoid.BaseColor;
import arkanoid.Settings;
import arkanoid.Textures;
import org.newdawn.slick.opengl.Texture;

/** Classe que representa a raquete como extensão de Collidable.
 * 
 * @author sPeC
 */
public class Club extends Collidable {

    transient private Texture mTexture;

    public Texture getTexture() {
        if (mTexture == null) {
            mTexture = Textures.getInstance().getClub();
        }
        return mTexture;
    }

    public void setTexture(Texture _Texture) {
        this.mTexture = _Texture;
    }
    private BaseColor mColor;

    /** Retorna objecto do tipo BaseColor que indica a cor da raquete.
     * 
     * @return BaseColor que indica a cor da raquete
     */
    public BaseColor getColor() {
        return mColor;
    }
    

    /** Coloca bola centrada no taco.
     * 
     * @param _ball Bola a centrar no taco
     */
    public void placeBallAtCenter(Ball _ball) {
        // Coloca a bola centrada no taco
        float x = getX() + getWidth() / 2 - Settings.BALL_SIZE / 2;
        float y = getY() - Settings.BALL_SIZE;
        _ball.updatePosition(x, y);
    }

    /** Constructor da classe.
     * 
     * @param _x valor da posição em X com o qual instanciar objecto
     * @param _y valor da posição em Y com o qual instanciar objecto
     */
    public Club(int _x, int _y) {
        super(_x, _y, Settings.CLUB_WIDTH, Settings.CLUB_HEIGHT);
        mTexture = Textures.getInstance().getClub();
        mColor = new BaseColor(0.5f, 0.5f, 0.5f);

    }
}
