package arkanoid.models.entities.Bricks;

import arkanoid.Collidable;
import arkanoid.BaseColor;
import arkanoid.Settings;
import arkanoid.models.entities.Ball;
import arkanoid.models.entities.PlayArea;
import java.awt.Point;

import org.newdawn.slick.opengl.Texture;


/** Classe que representa um Tijolo ( extensão de Collidable ).
 * É composta pela sua Cor, atributos que definem quantas vezes foi atingido e
 * quantos impactos são necessário para se destruir
 * e os atributos herdados de Collidable: Posição XY, Altura e Largura.
 * 
 * @author sPeC
 */
public abstract class Brick extends Collidable {

    transient protected Texture mTexture = null;

    abstract public Texture getTexture(); 
    
    public void setTexture(Texture mTexture) {
        this.mTexture = mTexture;
    }
    
    private Point mLocationOnPlayArea;
    private int mNumberOfAllowedHits;
    private int mNumberOfOcurredHits;

    public int getNumberOfOcurredHits() {
        return mNumberOfOcurredHits;
    }
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
    public void reset() {
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
    public void setLocationOnPlayArea(int _linha, int _col) {
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

        if (++mNumberOfOcurredHits >= mNumberOfAllowedHits) {
            mIsActive = false;
        }

        // Verifica se é efectivamente uma colisão, ou o método está a ser chamado 
        // pela explosão de outro bloco vermelho
        if (!this.isCollidingWith(_ball)) {
            return;
        }

        // Proposições:
        // 1ª - Se a bola está a descer/subir e bate num dos lados, então a sua direcção
        //      vertical mantém-se, e a direcção horizontal inverte-se
        // 2ª - Se a bola está a descer/subir e bate em cima/baixo do bloco, então
        //      a sua direcção horizontal mantém-se e a direcção vertical inverte-se
        // 3ª - Se a bola vai no sentido ascendente é impossivel a colisão ser com o topo
        // 4ª - Se a bola vai no sentido descendente é impossivel a colisão ser com o fundo
        // 5ª - Se a bola vai no sentido esquerdo é impossivel a colisão ser do lado esquerdo
        // 6ª - Se a bola vai no sentido direito é impossivel a colisão ser do lado direito

        // Assumindo a colisão recuamos no tempo com uma granularidade de velocidade
        // mais baixa de forma a detectar-mos a altura exacta da colisão
        float ballCurX = _ball.getX();
        float ballCurY = _ball.getY();

        // Inverte as direcções se necessário
        float granularidadeX = 0.1f;
        float granularidadeY = 0.1f;

        if (_ball.getVelocityX() < 0) {
            granularidadeX *= -1;
        }

        if (_ball.getVelocityY() < 0) {
            granularidadeY *= -1;
        }

        // Recua no tempo
        do {
            ballCurX -= granularidadeX;
            ballCurY -= granularidadeY;

            _ball.setX(ballCurX);
            _ball.setY(ballCurY);
        } while (this.isCollidingWith(_ball));

        // Aqui a bola e o tijolo não estão a colidir, por isso avançamos uma unidade
        // de tempo e aí será quando ocorreu a colisão
        ballCurX += granularidadeX;
        ballCurY += granularidadeY;

        // Para se saber de que lado ocorreu a colisão
        // 1º - usamos as proposições para primeiro reduzir as opções para 2;
        // 2º - dos 2 lados restantes verificamos do qual a bola está mais distante
        //      se a distância for muito grande é gerado um valor negativo

        float velX = _ball.getVelocityX();
        float velY = _ball.getVelocityY();
        if (velX < 0) {
            // A colisão tem que ser em cima, baixo ou lado direito
            if (velY > 0) {
                // A colisão tem que ser em cima ou do lado direito

                // Lado direito
                float rightDist = (ballCurX + _ball.getWidth()) - (this.getX() + this.getWidth());

                // Cima
                float topDist = this.getY() - ballCurY;

                if (topDist < rightDist) {
                    // a colisão foi do lado direito
                    _ball.setVelocityX(velX * -1);
                } else {
                    // a colisão foi no topo
                    _ball.setVelocityY(velY * -1);
                }
            } else { //  if( velY <= 0)

                // A colisão tem que ser em baixo ou do lado direito

                // Lado direito
                float rightDist = (ballCurX + _ball.getWidth()) - (this.getX() + this.getWidth());

                // Baixo
                float bottomDist = (ballCurY + _ball.getHeight()) - (this.getY() + this.getHeight());

                if (bottomDist < rightDist) {
                    // a colisão foi do lado direito
                    _ball.setVelocityX(velX * -1);
                } else {
                    // a colisão foi por baixo
                    _ball.setVelocityY(velY * -1);
                }
            }
        } else { // if (velX >= 0) {

            // A colisão tem que ser em cima, baixo ou lado esquerdo
            if (velY > 0) {
                // A colisão tem que ser em cima ou do lado esquerdo

                // Lado esquerdo
                float leftDist = this.getX() - ballCurX;

                // Cima
                float topDist = this.getY() - ballCurY;

                if (topDist < leftDist) {
                    // a colisão foi do lado esquerdo
                    _ball.setVelocityX(velX * -1);
                } else {
                    // a colisão foi no topo
                    _ball.setVelocityY(velY * -1);
                }
            } else { //  if( velY <= 0)

                // A colisão tem que ser em baixo ou do lado esquerdo

                // Lado direito
                float leftDist = this.getX() - ballCurX;

                // Baixo
                // Isto será negativo quando a bola ja estiver acima do fundo
                float bottomDist = (ballCurY + _ball.getHeight()) - (this.getY() + this.getHeight());

                if (bottomDist < leftDist) {
                    // a colisão foi do lado esquerdo
                    _ball.setVelocityX(velX * -1);
                } else {
                    // a colisão foi por baixo
                    _ball.setVelocityY(velY * -1);
                }
            }
        }

        /*
        // Se a bola colide em Cima / Baixo
        if (((_ball.getY() < getY() - Settings.BALL_SIZE/2 )
        || (_ball.getY() + Settings.BALL_SIZE/2 > getY() + getHeight()))
        && (_ball.getX()>getX()-Settings.BALL_SIZE/4 && (_ball.getX()< getX() + getWidth()-Settings.BALL_SIZE/4 ))) {
        
        _ball.setVelocityY(_ball.getVelocityY() * -1);
        } else {
        // Se a bola colide à Esquerda / Direita
        _ball.setVelocityX(_ball.getVelocityX() * -1);
        }
         */


    }

    /** Constructor da classe.
     * 
     * @param _x valor da posição em X com o qual será instanciado o objecto
     * @param _y valor da posição em Y com o qual será instanciado o objecto
     * @param _allowedHits  quantidade de impactos necessário para desactivar tijolo
     * @param _color cor do tijolo
     */
    public Brick(int _x, int _y, int _allowedHits, BaseColor _color,Texture _tex) {
        super(_x, _y, Settings.BRICK_WIDTH, Settings.BRICK_HEIGHT);

        mTexture = _tex;
        mNumberOfOcurredHits = 0;
        mNumberOfAllowedHits = _allowedHits;
        mColor = _color;

    }
}
