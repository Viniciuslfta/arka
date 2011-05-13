/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.models.entities.Bricks;

/** Classe 
 *
 * @author sPeC
 */
public  class BrickUtils {

    public static Brick getBrickFromType(char _type, int _posX, int _posY) {

        switch (Character.toUpperCase(_type)) {
            case 'W':
                return new WhiteBrick(_posX, _posY);
            case 'B':
                return new BlueBrick(_posX, _posY);
            case 'R':
                return new RedBrick(_posX, _posY);
            case 'G':
                return new GreenBrick(_posX, _posY);
            default:
                return null;

        }
    }
}
