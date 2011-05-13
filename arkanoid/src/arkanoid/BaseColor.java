package arkanoid;

/** Classe que permite representar uma cor.
 *  através das suas componentes Vermelha, Verde, Azul e Opacidade (RGBA)
 *
 * @author sPeC
 */

public class BaseColor {

    private float mRed;
    private float mGreen;
    private float mBlue;
    private float mAlpha;

    /** Retorna valor da componente Vermelha.
     * @return valor da componente Vermelha 
     */
    public float getR() {
        return mRed;
    }
    
    /** Retorna valor da componente Verde.
     * @return valor da componente Verde 
     */
    public float getG() {
        return mGreen;
    }
    
    /** Retorna valor da componente Azul.
     * @return valor da componente Azul 
     */
    public float getB() {
        return mBlue;
    }
    
    /** Retorna valor da componente Opacidade.
     * @return valor da componente Opacidade
     */
    public float getA() {
        return mAlpha;
    }
    
    /** Constructor da classe.
     * @param _red  Valor da componente Vermelha com o qual instanciar objecto
     * @param _green  Valor da componente Verde com o qual instanciar objecto
     * @param _blue  Valor da componente Azul com o qual instanciar objecto
     * @param _alpha  Valor da componente Opacidade com o qual instanciar objecto
     */
    public BaseColor(float _red, float _green, float _blue, float _alpha) {
        mRed = _red;
        mGreen = _green;
        mBlue = _blue;
        mAlpha = _alpha;
    }
    
    /** Constructor da classe com o valor máximo de Opacidade por defeito.
     * @param _red  Valor da componente Vermelha com o qual instanciar objecto
     * @param _green  Valor da componente Verde com o qual instanciar objecto
     * @param _blue  Valor da componente Azul com o qual instanciar objecto
     */
    public BaseColor(float _red, float _green, float _blue) {
        this(_red, _green, _blue, 1.0f);
    }
}
