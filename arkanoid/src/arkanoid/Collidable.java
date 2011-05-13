/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid;

/** Classe que representa um elemento com capacidade de colisão.
 *  É composta por duas BoundingBox: uma para a posição actual (mBoundingBox)
 *  e outra para a posição anterior (mLastBoundingBox).
 * @see BoundingBox
 * @author sPeC
 */
public class Collidable {

    BoundingBox mLastBoundingBox;
    BoundingBox mBoundingBox;

    /** Retorna BoundingBox actual.
     * 
     * @return BoundingBox actual
     */    
    public BoundingBox getBoundingBox() {
        return mBoundingBox;
    }

    /** Constructor da classe.
     * 
     * @param _x Posição em X
     * @param _y Posição em Y
     * @param _width Largura
     * @param _height Altura
     */
    public Collidable(float _x, float _y, float _width, float _height) {
        mBoundingBox = new BoundingBox(_x, _y, _width, _height);
        mLastBoundingBox = new BoundingBox(_x, _y, _width, _height);
    }

    /** Altera posição em X.
     * 
     * @param _x novo valor a atribuir à posição 
     */
    public void setX(float _x) {
        mLastBoundingBox.setX(mBoundingBox.getX());
        mBoundingBox.setX(_x);

    }

    /** Altera posição em Y.
     * 
     * @param _y novo valor a atribuir à posição 
     */
    public void setY(float _y) {
        mLastBoundingBox.setY(mBoundingBox.getY());
        mBoundingBox.setY(_y);
    }

    /** Altera largura.
     * 
     * @param _newWidth novo valor a atribuir à largura
     */
    public void setWidth(float _newWidth) {
        mBoundingBox.setWidth(_newWidth);
    }

    /** Altera altura.
     * 
     * @param _newHeight novo valor a atribuir à altura 
     */
    public void setHeight(float _newHeight) {
        mBoundingBox.setHeight(_newHeight);
    }

    /** Retorna posição em X.
     * 
     * @return valor da posição em X 
     */
    public float getX() {
        return mBoundingBox.getX();
    }

    /** Retorna posição em Y.
     * 
     * @return valor da posição em Y 
     */
    public float getY() {
        return mBoundingBox.getY();
    }

    /** Retorna largura.
     * 
     * @return valor da largura 
     */
    public float getWidth() {
        return mBoundingBox.getWidth();
    }

    /** Retorna altura.
     * 
     * @return valor da altura
     */
    public float getHeight() {
        return mBoundingBox.getHeight();
    }

    /** Actualiza posição XY.
     * 
     * @param _x novo valor para a posição em X
     * @param _y novo valor para a posição em Y
     */
    public void updatePosition(float _x, float _y) {
        mLastBoundingBox.setX(mBoundingBox.getX());
        mLastBoundingBox.setY(mBoundingBox.getY());

        mBoundingBox.setX(_x);
        mBoundingBox.setY(_y);
    }

    /** Devolve diferença entre posição actual e posição anterior (em X).
     * 
     * @return diferença entre posição actual e posição anterior (em X)
     */
    public float getDeltaX() {
        return (mBoundingBox.getX() - mLastBoundingBox.getX()) < 1.0f
                ? (mBoundingBox.getX() - mLastBoundingBox.getX()) : 1.0f;
    }

    /** Verifica colisão com outro objecto do mesmo tipo.
     * 
     * @param _obj Collidable com o qual irá ser verificada colisão
     * @return true se existe colisão, false caso contrário
     */
    public boolean isCollidingWith(Collidable _obj) {

        return mBoundingBox.intersects(_obj.mBoundingBox);
    }
}
