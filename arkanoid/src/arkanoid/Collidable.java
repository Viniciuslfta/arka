/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid;

/**
 *
 * @author sPeC
 */
public class Collidable {

    BoundingBox mLastBoundingBox;
    BoundingBox mBoundingBox;

    public BoundingBox getBoundingBox() {
        return mBoundingBox;
    }

    public Collidable(float _x, float _y, float _width, float _height) {
        mBoundingBox = new BoundingBox(_x, _y, _width, _height);
        mLastBoundingBox = new BoundingBox(_x, _y, _width, _height);
    }

    public void setX(float _x) {
        mLastBoundingBox.setX(mBoundingBox.getX());
        mBoundingBox.setX(_x);

    }

    public void setY(float _y) {
        mLastBoundingBox.setY(mBoundingBox.getY());
        mBoundingBox.setY(_y);
    }

    public void setWidth(float _newWidth) {
        mBoundingBox.setWidth(_newWidth);
    }

    public void setHeight(float _newHeight) {
        mBoundingBox.setHeight(_newHeight);
    }

    public float getX() {
        return mBoundingBox.getX();
    }

    public float getY() {
        return mBoundingBox.getY();
    }

    public float getWidth() {
        return mBoundingBox.getWidth();
    }

    public float getHeight() {
        return mBoundingBox.getHeight();
    }

    public void updatePosition(float _x, float _y) {
        mLastBoundingBox.setX(mBoundingBox.getX());
        mLastBoundingBox.setY(mBoundingBox.getY());

        mBoundingBox.setX(_x);
        mBoundingBox.setY(_y);
    }

    public float getDeltaX() {
        return (mBoundingBox.getX() - mLastBoundingBox.getX()) < 1.0f
                ? (mBoundingBox.getX() - mLastBoundingBox.getX()) : 1.0f;
    }

    public boolean isCollidingWith(Collidable _obj) {

        return mBoundingBox.intersects(_obj.mBoundingBox);
    }
}
