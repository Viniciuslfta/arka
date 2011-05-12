/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid;

import java.awt.Rectangle;


/**
 *
 * @author sPeC
 */
public class BoundingBox {

    private float mX;
    private float mY;
    private float mWidth;
    private float mHeight;

    public void setX(float _x) {
        mX = _x;
    }

    public void setY(float _y) {
        mY = _y;
    }

    public void setWidth(float _newWidth) {
        mWidth = _newWidth;
    }

    public void setHeight(float _newHeight) {
        mHeight = _newHeight;
    }

    public float getX() {
        return mX;
    }

    public float getY() {
        return mY;
    }

    public float getWidth() {
        return mWidth;
    }

    public float getHeight() {
        return mHeight;
    }

    public boolean intersects(BoundingBox _box) {

        Rectangle r1 = new Rectangle((int) mX, (int) mY, (int) mWidth, (int) mHeight);

        return r1.intersects(_box.mX, _box.mY, _box.mWidth, _box.mHeight);
    }

    public BoundingBox(float _X, float _Y, float _Width, float _Height) {
        this.mX = _X;
        this.mY = _Y;
        this.mWidth = _Width;
        this.mHeight = _Height;
    }
}
