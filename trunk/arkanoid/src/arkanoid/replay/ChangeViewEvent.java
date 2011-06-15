/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.replay;

import arkanoid.RegisteredPlayerData;
import arkanoid.models.ModelPlayArea;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author sPeC
 */
public class ChangeViewEvent extends GameEvent{

    boolean mUseTex;
    public ChangeViewEvent(boolean _useTex, long _timeStamp) {
        super(_timeStamp);
        mUseTex = _useTex;
    }

    
    @Override
    public void execute(ModelPlayArea _model) {

            if (!mUseTex ) {
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_LIGHT0);
                RegisteredPlayerData.getInstance().drawTextures(false);
            } else {
                // Passa a desenhar texturas
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_LIGHT0);
                RegisteredPlayerData.getInstance().drawTextures(true);
            }
    }
    
}
