package arkanoid.models.entities;

import arkanoid.Collidable;
import arkanoid.BaseColor;
import arkanoid.ElapsedTime;
import arkanoid.Settings;

/** Classe que representa bola como extensão de Collidable.
 * È essencialmente composta pela Cor, Velocidade (em X e Y) 
 * e os atributos herdados de Collidable: Posição XY, Altura e Largura.
 * 
 * @author sPeC
 */
public class Ball extends Collidable {
    /**
     * Indica tempo em que foi efectuada a última alteração
     */
    private long mLastUpdate;

    public void setLastUpdate(long _lastUpdate) {
        this.mLastUpdate = _lastUpdate;
    }
    /**
     * Define se bola está colada à raquete
     */
    private boolean mIsGluedToClub;

    /** Retorna booleano que indica se a bola está colada à raquete
     * 
     * @return true se bola colada à raquete, false caso contrário 
     */
    public boolean isGluedToClub() {
        return mIsGluedToClub;
    }

    /** Altera o atributo que define se a bola está colada à raquete e actualiza
     * tempo registado na última alteração
     * 
     * @param _value valor a atribuir ao atributo mIsGluedToClub
     */
    public void setIsGluedToClub(boolean _value) {
        this.mIsGluedToClub = _value;
        mLastUpdate = System.nanoTime();
    }
    
    private float mVelocityX;

    /** Devolve valor da Velocidade em X.
     * 
     * @return valor da velocidade em X 
     */
    public float getVelocityX() {
        return mVelocityX;
    }

    /** Altera velocidade em X tendo em conta os limites predefinidos.
     * 
     * @param _velocityX novo valor a atribui à velocidade em X.
     */
    public void setVelocityX(float _velocityX) {
        if (Settings.BALL_MAX_VEL < Math.abs(_velocityX)) {
            mVelocityX = Settings.BALL_MAX_VEL;
        } else if (Settings.BALL_MIN_VEL > Math.abs(_velocityX)) {
            mVelocityX = Settings.BALL_MIN_VEL;
        } else {
            mVelocityX = _velocityX;
        }
    }
    
    
    private float mVelocityY;

    /** Devolve valor da velocidade em Y.
     * 
     * @return valor da velocidade em Y 
     */
    public float getVelocityY() {
        return mVelocityY;
    }

    /** Altera valor da velocidade em Y segundo os limites predifinidos
     * 
     * @param _velocityY valor a atribuir à velocidade em Y
     */
    public void setVelocityY(float _velocityY) {
        if (Settings.BALL_MAX_VEL < Math.abs(_velocityY)) {
            mVelocityY = Settings.BALL_MAX_VEL;
        } else if (Settings.BALL_MIN_VEL > Math.abs(_velocityY)) {
            mVelocityY = Settings.BALL_MIN_VEL;
        } else {
            mVelocityY = _velocityY;
        }
    }
    
    private BaseColor mColor;

    /** Retorna objecto BaseColor que indica a cor da bola.
     * 
     * @return BaseColor que indica cor da bola
     */
    public BaseColor getColor() {
        return mColor;
    }

    /** Actualiza Posição actual da bola.
     *  Actualiza o tempo em que o objecto foi alterado pela última vez e posição
     *  da bola ( atributos herdados de Collidable )
     */
    public void updatePosition() {
        double time = ElapsedTime.microsecondsSince(mLastUpdate);
        mLastUpdate = System.nanoTime();

        float x = (float) (getX() + mVelocityX * time);
        float y = (float) (getY() + mVelocityY * time);

        super.updatePosition(x, y);

    }

    /** Constructor da classe
     * 
     * @param _x valor da posição em X com o qual será instanciado o objecto
     * @param _y valor da posição em Y com o qual será instanciado o objecto
     */
    public Ball(int _x, int _y) {
        super(_x, _y, Settings.BALL_SIZE, Settings.BALL_SIZE);

        mLastUpdate = System.nanoTime();
        mColor = new BaseColor(1.0f, 1.0f, 0.0f);
        mSticky = false;
    }
    
    boolean mSticky;
    
    public boolean isSticky() {
        return mSticky;
    }
    
    public void setSticky(boolean _val) {
        mSticky = _val;
    }
}
