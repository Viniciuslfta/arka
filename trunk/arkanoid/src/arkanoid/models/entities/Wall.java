package arkanoid.models.entities;

import arkanoid.Collidable;
import arkanoid.BaseColor;
import arkanoid.Settings;

/** Classe que representa uma parede da àrea de jogo (extensão de Collidable).
 * È composta pela sua Cor e Tipo ( esquerda / topo / direita )
 * e os atributos herdados de Collidable: Posição XY, Altura e Largura.
 * 
 * @author sPeC
 */
public class Wall extends Collidable {

    public enum WallType {

        TOP,
        LEFT,
        RIGHT
    }
    private WallType mType;
    private BaseColor mColor;

    /** Retorna objecto BaseColor que indica cor da parede.
     * 
     * @return objecto BaseColor que indica cor da parede
     */
    public BaseColor getColor() {
        return mColor;
    }

    /** Constructor da classe
     * 
     * @param _x posição em X
     * @param _y posição em Y
     * @param _width Largura
     * @param _height Altura
     * @param _type Tipo ( LEFT, TOP , RIGHT )
     */
    public Wall(int _x, int _y, int _width, int _height, WallType _type) {
        super(_x, _y, _width, _height);

        mType = _type;


        mColor = new BaseColor(0.0f, 1.0f, 0.5f);
    }

    /** Define efeito sobre a raquete quando existe uma colisão entre estas duas entidades.
     *  Não deixa que raquete sobreponha a parede.
     * @param _club Raquete a afectar
     */
    public void doEffectOnClub(Club _club)
    {
        switch (mType) {
            case LEFT:
                _club.setX(getX() + getWidth());
                break;
            case RIGHT:
                _club.setX(getX() - _club.getWidth());
                break;
        }
    }
    
    /** Define efeito sobre a bola quando existe colisão entre estas duas entidades.
     * Não deixa que bola sobreponha parede e altera direcção da bola dependendo
     * do tipo de parede que se trata.
     * @param _ball Bola a afectar
     */
    public void doEffectOnBall(Ball _ball) {
        // Altera as velocidades da bola
        float velX = _ball.getVelocityX();
        float velY = _ball.getVelocityY();

        switch (mType) {
            case TOP:
                velY *= -1;
                _ball.setY(getY() + getHeight());
                break;
            case LEFT:
                velX *= -1;
                _ball.setX(getX() + getWidth());
                break;
            case RIGHT:
                velX *= -1;
                _ball.setX(getX() - Settings.BALL_SIZE);
                break;
        }

        _ball.setVelocityX(velX);
        _ball.setVelocityY(velY);
    }
}
