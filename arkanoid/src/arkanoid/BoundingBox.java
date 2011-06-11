package arkanoid;

import java.awt.Rectangle;
import java.io.Serializable;



/** Classe que representa uma caixa/rectangulo delimitador. 
 *  através da sua posição XY (mX,mY), Largura(mWidth) e Altura(mHeight)
 * 
 * @author sPeC
 */
public class BoundingBox implements Serializable {

    private float mX;
    private float mY;
    private float mWidth;
    private float mHeight;

    /** Altera o valor da posição em X. 
     * @param _x novo valor a atribuir a 'mX'
     */
    public void setX(float _x) {
        mX = _x;
    }
    
    /** Altera o valor da posição em Y.
     * @param _y novo valor a atribuir a 'mY'
     */
    public void setY(float _y) {
        mY = _y;
    }

        
    /** Altera o valor da largura.
     * @param _newWidth novo valor a atribuir a 'mWidth'
     */
    public void setWidth(float _newWidth) {
        mWidth = _newWidth;
    }

        
    /** Altera o valor da altura.
     * @param _newHeight novo valor a atribuir a 'mHeight'
     */
    public void setHeight(float _newHeight) {
        mHeight = _newHeight;
    }

    /** Retorna valor da posição em X.
     * @return posição em X
     */
    public float getX() {
        return mX;
    }

    /** Retorna valor da posição em Y.
     * @return posição em Y
     */
    public float getY() {
        return mY;
    }

    /** Retorna valor da largura.
     * @return largura
     */
    public float getWidth() {
        return mWidth;
    }

     /** Retorna valor da altura.
     * @return altura
     */
    public float getHeight() {
        return mHeight;
    }

     /** Verifica se BoudingBox intersecta outra especificada.
     * @param _box BoundingBox com a qual é verificada intersecção
     * @return true se é verificada intersecção, false caso contrário
     */
    public boolean intersects(BoundingBox _box) {

        Rectangle r1 = new Rectangle((int) mX, (int) mY, (int) mWidth, (int) mHeight);

        return r1.intersects(_box.mX, _box.mY, _box.mWidth, _box.mHeight);
    }

    /** Constructor da classe.
     * @param _X valor da posição em X com o qual instanciar objecto
     * @param _Y valor da posição em Y com o qual instanciar objecto
     * @param _Width valor da Largura com o qual instanciar objecto
     * @param _Height valor da Altura com o qual instanciar objecto
     */
    public BoundingBox(float _X, float _Y, float _Width, float _Height) {
        this.mX = _X;
        this.mY = _Y;
        this.mWidth = _Width;
        this.mHeight = _Height;     
    }

}
