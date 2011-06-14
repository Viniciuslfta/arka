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
    Sound mBallBrick;
    Sound mBallBump;
    Sound mGameOver;
    Sound mBallWall;
    Sound mRedBrick;
    Sound mLevelComplete;
    Sound mGreenBrick;
    Sound mMusic;
    
    private static Sounds singletonObj = new Sounds();

    public static Sounds getInstance() {
        return singletonObj;
    }

    public void playPowerUp() {
        mPowerUp.play();
    }

    public void playBallBrick() {
       mBallBrick.play();
    }
    public void playBallBump() {
        mBallBump.play();
    }

    
    class BasicThread1 extends Thread {
        long sleepTime;
        public BasicThread1(long _st) {
         sleepTime = _st;
         
    }
    // This method is called when the thread runs
        
    public void run() {        
            try {
                sleep(sleepTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(Sounds.class.getName()).log(Level.SEVERE, null, ex);
            }
       mRedBrick.play();
    }
    }
    int mRedBrickBlast = 1;
    long mLastPlayedRedBrickTime = 0;
    public void playRedBrick() {
      long elapsedTime = System.currentTimeMillis()-mLastPlayedRedBrickTime;
         
      mLastPlayedRedBrickTime = System.currentTimeMillis();

      if(elapsedTime>500) {
          elapsedTime = 0;
          mRedBrickBlast = 1;
      }else{
          elapsedTime = 100*mRedBrickBlast++;
      }
    Thread thread = new BasicThread1(elapsedTime);    
    thread.start();

    }
    public void playGreenBrick() {
       mGreenBrick.play();
    }        
    public void playLevelComplete() {
       mLevelComplete.play();
    }
    
    public void playGameOver() {
       mGameOver.play();
    }
    
    
    public void playMusic() {
        mMusic.stop();
        mMusic.loop();
    }
    
    public void load() {
        try {

            mPowerUp = new Sound("Sounds/power.ogg");
            mBallBrick = new Sound("Sounds/ballbrick.wav");
            mBallBump = new Sound("Sounds/ballbump.wav");
            mGameOver = new Sound("Sounds/gameover.wav");
            mRedBrick = new Sound("Sounds/redbrick.wav");
            mGreenBrick = new Sound("Sounds/greenbrick.wav");
            mLevelComplete = new Sound("Sounds/levelcomplete.wav");
            mMusic = new Sound("Sounds/music.ogg");
        } catch (SlickException ex) {
            Logger.getLogger(Sounds.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
