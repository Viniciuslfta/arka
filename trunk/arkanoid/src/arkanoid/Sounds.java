/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 *
 * @author sPeC
 */
public class Sounds {

    Sound mPowerUp;
    private static Sounds singletonObj = new Sounds();

    public static Sounds getInstance() {
        return singletonObj;
    }

    public void playPowerUp() {
        mPowerUp.play();
    }

    public void playBallBump() {
       
    }

    public void load() {
        try {

            mPowerUp = new Sound("Sounds/power.ogg");
            
        } catch (SlickException ex) {
            Logger.getLogger(Sounds.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
