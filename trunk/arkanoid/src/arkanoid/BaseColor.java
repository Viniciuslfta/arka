/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid;

/**
 *
 * @author sPeC
 */
public class BaseColor {

    private float mRed;
    private float mGreen;
    private float mBlue;
    private float mAlpha;

    public float getR() {
        return mRed;
    }

    public float getG() {
        return mGreen;
    }

    public float getB() {
        return mBlue;
    }
    
    public float getA() {
        return mAlpha;
    }
    
    public BaseColor(float _red, float _green, float _blue, float _alpha) {
        mRed = _red;
        mGreen = _green;
        mBlue = _blue;
        mAlpha = _alpha;
    }
    
    public BaseColor(float _red, float _green, float _blue) {
        this(_red, _green, _blue, 1.0f);
    }
}
