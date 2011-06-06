/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.views;

import arkanoid.Settings;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.GL11;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

/**
 * @author sPeC
 */
public class ViewMainMenu extends ArkanoidView {

    UnicodeFont mBigFont;

    public ViewMainMenu() {
        try {
            mBigFont = new UnicodeFont("fonts\\JerseyLetters.ttf", 100, false, false);
            mBigFont.addGlyphs("ARKNOID!");
            mBigFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
            mBigFont.loadGlyphs();
        } catch (SlickException ex) {
            Logger.getLogger(ViewMainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void render() {

        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glLoadIdentity();

        float x = Settings.PLAY_AREA_START_X;
        float y = Settings.PLAY_AREA_START_Y;

        float width = Settings.DISPLAY_WIDTH - Settings.PLAY_AREA_START_X * 2;
        float height = Settings.DISPLAY_HEIGHT - Settings.PLAY_AREA_START_Y;

        x += (width / 2) - mBigFont.getWidth("GAME OVER!") / 2;
        y += (height / 2) - mBigFont.getHeight("GAME OVER!");

        mBigFont.drawString(x, y, "ARKANOID!", Color.red);
    }

    @Override
    public void update(Observable o, Object o1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
