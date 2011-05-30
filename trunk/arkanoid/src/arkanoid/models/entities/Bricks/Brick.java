package arkanoid.models.entities.Bricks;

import arkanoid.Collidable;
import arkanoid.BaseColor;
import arkanoid.Settings;
import arkanoid.models.entities.Ball;
import arkanoid.models.entities.PlayArea;
import java.awt.Point;


/** Classe que representa um Tijolo ( extensão de Collidable ).
 * É composta pela sua Cor, atributos que definem quantas vezes foi atingido e
 * quantos impactos são necessário para se destruir
 * e os atributos herdados de Collidable: Posição XY, Altura e Largura.
 * 
 * @author sPeC
 */
public abstract class Brick extends Collidable {

    private Point mLocationOnPlayArea;
    
    private int mNumberOfAllowedHits;
    private int mNumberOfOcurredHits;
    
    private BaseColor mColor;
    
    /** Retorna objecto BaseColor que indica cor do tijolo
     * 
     * @return objecto BaseColor que indica cor do tijolo
     */
    public BaseColor getColor() {
        return mColor;
    }
    protected boolean mIsActive;

    /** Retorna um booleano que indica se o tijolo está activo ou não.
     * 
     * @return true se está activo, false caso contrário 
     */
    public boolean isActive() {
        return mIsActive;
    }

    /** Restablece tijolo colocando-o como activo e sem qualquer impacto registado.
     */
    public void reset()
    {
        mIsActive = true;
        mNumberOfOcurredHits = 0;
    }
    /** Altera o valor do atributo que define se o tijolo está activo ou não.
     * 
     * @param _value novo valor a atribuir 
     */
    public void setIsActive(boolean _value) {
        mIsActive = _value;
    }

    /** Altera localização do tijolo na area de jogo.
     * 
     * @param _linha linha correspondente à localização pretendida
     * @param _col coluna correspondente à localização pretendida
     */
    public void setLocationOnPlayArea(int _linha, int _col)
    {
        mLocationOnPlayArea = new Point(_linha, _col);
    }
    
    public Point getLocationOnPlayArea() {
        return mLocationOnPlayArea;
    }
    /** Função abstracta onde será definido o efeito da colisão da Bola com o tijolo.
     * 
     * @param _area Area de jogo a que pertence o tijolo
     */
    abstract public void onBallCollision(PlayArea _area);
    
    /** Define o efeito provocado na Bola quando colide com um tijolo.
     * 
     * @param _ball Bola
     */
    protected void onBallCollision(Ball _ball) {
            
        // Se a bola colide em Cima / Baixo
        if (((_ball.getY() < getY() -Settings.BALL_SIZE/2 )
            || (_ball.getY() + Settings.BALL_SIZE/2 > getY() + getHeight()))
            && (_ball.getX()>getX()-Settings.BALL_SIZE/4 && (_ball.getX()< getX() + getWidth()-Settings.BALL_SIZE/4 ))) {
            
            _ball.setVelocityY(_ball.getVelocityY() * -1);
        } else {
            // Se a bola colide à Esquerda / Direita
            _ball.setVelocityX(_ball.getVelocityX() * -1);
        }

        
        if(++mNumberOfOcurredHits >= mNumberOfAllowedHits)
            mIsActive = false;
    }

    /** Constructor da classe.
     * 
     * @param _x valor da posição em X com o qual será instanciado o objecto
     * @param _y valor da posição em Y com o qual será instanciado o objecto
     * @param _allowedHits  quantidade de impactos necessário para desactivar tijolo
     * @param _color cor do tijolo
     */
    public Brick(int _x, int _y, int _allowedHits, BaseColor _color) {
        super(_x, _y, Settings.BRICK_WIDTH, Settings.BRICK_HEIGHT);
        
        mNumberOfOcurredHits = 0;
        mNumberOfAllowedHits = _allowedHits;
        mColor = _color;
    }
}
