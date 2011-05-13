/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid;

/** Classe onde estão definidos os valores globais das configurações internas
 *  
 * @author sPeC
 */
public abstract class Settings {

    public static final int DISPLAY_WIDTH = 1024;
    public static final int DISPLAY_HEIGHT = 768;
    
    public static final int MAX_BRICK_COLUMNS = 13;
    public static final int MAX_BRICK_ROWS = 12;
    
    public static final int BRICK_WIDTH = 72;
    public static final int BRICK_HEIGHT = 30;
    public static final int BRICK_INTERVAL = 4;
    
    public static final int PLAY_AREA_START_X = 15;
    public static final int PLAY_AREA_START_Y = 100;
    
    public static final int BALL_SIZE = 10;
    public static final float BALL_MAX_VEL = 0.5f;
    public static final float BALL_MIN_VEL = 0.2f;
    
    public static final int CLUB_LOC_Y = DISPLAY_HEIGHT - 100;
    public static final int CLUB_WIDTH = 100;
    public static final int CLUB_HEIGHT = 20;
    public static final float CLUB_SPEED_INC = 0.05f;
    
    public static final int MENU_BTN_WIDTH = 200;
    public static final int MENU_BTN_HEIGHT = 50;
}
