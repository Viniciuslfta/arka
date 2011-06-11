/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid;

import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 *
 * @author Filipe
 */
public class Textures {

    Texture mRedBrick;
    Texture mBlueBrick;
    Texture mWhiteBrick;
    Texture mGreenBrick;   
    Texture mGreenBrickBroken;   
    Texture mInvisibleBrick;
    Texture mClub;
    Texture mBall;
    Texture mBonusExpansion;  
    Texture mBonusGlue;
    Texture mBonusInvert;
    Texture mBonusLife;
    Texture mBonusSlowDown;   
    Texture mWallSide;
    Texture mWallTop;
    


    
    private static Textures singletonObj = new Textures();

    public static Textures getInstance() {
        return singletonObj;
    }
    private Textures() {
        
        try {
            mRedBrick = TextureLoader.getTexture("PNG", new FileInputStream("textures/redbrick.png"));
            mBlueBrick = TextureLoader.getTexture("PNG", new FileInputStream("textures/bluebrick.png"));
            mGreenBrick = TextureLoader.getTexture("PNG", new FileInputStream("textures/greenbrick.png"));
            mGreenBrickBroken = TextureLoader.getTexture("PNG", new FileInputStream("textures/greenbrickbroken.png"));
            mWhiteBrick = TextureLoader.getTexture("PNG", new FileInputStream("textures/whitebrick.png"));
            mInvisibleBrick = TextureLoader.getTexture("PNG", new FileInputStream("textures/invisiblebrick.png"));
            mClub = TextureLoader.getTexture("PNG", new FileInputStream("textures/arka2.png"));
            mBall = TextureLoader.getTexture("PNG", new FileInputStream("textures/ball.png"));
            mBonusExpansion = TextureLoader.getTexture("PNG", new FileInputStream("textures/bonus.png"));
            mBonusGlue = TextureLoader.getTexture("PNG", new FileInputStream("textures/bonus2.png"));
            mBonusInvert = TextureLoader.getTexture("PNG", new FileInputStream("textures/bonus3.png"));
            mBonusLife = TextureLoader.getTexture("PNG", new FileInputStream("textures/bonus4.png"));
            mBonusSlowDown = TextureLoader.getTexture("PNG", new FileInputStream("textures/bonus5.png"));
            mWallSide = TextureLoader.getTexture("PNG",new FileInputStream("textures/wallside.png"));
            mWallTop = TextureLoader.getTexture("PNG",new FileInputStream("textures/walltop.png"));

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }     
        
    }
    
    public Texture getWallSide() {
        return mWallSide;
    }

    public void setmWallSide(Texture _WallSide) {
        this.mWallSide = _WallSide;
    }
    
    public Texture getWallTop() {
        return mWallTop;
    }

    public void setmWallTop(Texture _WallTop) {
        this.mWallSide = _WallTop;
    }
             
    public Texture getBlueBrick() {
        return mBlueBrick;
    }

    public void setBlueBrick(Texture _BlueBrick) {
        this.mBlueBrick = _BlueBrick;
    }

    public Texture getGreenBrick() {
        return mGreenBrick;
    }

    public void setGreenBrick(Texture _GreenBrick) {
        this.mGreenBrick = _GreenBrick;
    }

    public Texture getGreenBrickBroken() {
        return mGreenBrickBroken;
    }

    public void setGreenBrickBroken(Texture _GreenBrickBroken) {
        this.mGreenBrickBroken = _GreenBrickBroken;
    }
    
    public Texture getRedBrick() {
        return mRedBrick;
    }

    public void setRedBrick(Texture _RedBrick) {
        this.mRedBrick = _RedBrick;
    }

    public Texture getWhiteBrick() {
        return mWhiteBrick;
    }

    public void setWhiteBrick(Texture _WhiteBrick) {
        this.mWhiteBrick = _WhiteBrick;
    }


    public Texture getBall() {
        return mBall;
    }

    public void setBall(Texture _Ball) {
        this.mBall = _Ball;
    }

    public Texture getBonusExpansion() {
        return mBonusExpansion;
    }

    public void setBonusExpansion(Texture _BonusExpansion) {
        this.mBonusExpansion = _BonusExpansion;
    }

    public Texture getBonusGlue() {
        return mBonusGlue;
    }

    public void setBonusGlue(Texture _BonusGlue) {
        this.mBonusGlue = _BonusGlue;
    }

    public Texture getBonusInvert() {
        return mBonusInvert;
    }

    public void setBonusInvert(Texture _BonusInvert) {
        this.mBonusInvert = _BonusInvert;
    }

    public Texture getBonusLife() {
        return mBonusLife;
    }

    public void setBonusLife(Texture _BonusLife) {
        this.mBonusLife = _BonusLife;
    }

    public Texture getBonusSlowDown() {
        return mBonusSlowDown;
    }

    public void setBonusSlowdown(Texture _BonusSlowdown) {
        this.mBonusSlowDown = _BonusSlowdown;
    }

    public Texture getClub() {
        return mClub;
    }

    public void setClub(Texture _Club) {
        this.mClub = _Club;
    }



    public Texture getInvisibleBrick() {
        return mInvisibleBrick;
    }

    public void setInvisibleBrick(Texture _InvisibleBrick) {
        this.mInvisibleBrick = _InvisibleBrick;
    }



    
}
