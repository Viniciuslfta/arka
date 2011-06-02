package arkanoid;

import java.util.Observable;

/**
 *
 * @author sPeC
 * @see http://www.javabeginner.com/learn-java/java-singleton-design-pattern
 */
public class GameState extends Observable {

    private static GameState singletonObj = new GameState();

    public static GameState getInstance() {
        return singletonObj;
    }

    private GameState() {
    }
    private static boolean mStateChanged = true;

    public static boolean hasStateChanged() {
        return mStateChanged;
    }

    public static void setHasStateChanged(boolean _val) {
         mStateChanged = _val;
    }
    private static GameStateType mCurrentState = GameStateType.PLAYING;

    public static GameStateType currentState() {
        return mCurrentState;
    }

    public static void changeState(GameStateType _newState) {
        mStateChanged = true;
        mCurrentState = _newState;
    }

    public enum GameStateType {

        INTRO,
        MAIN_MENU,
        SAVING,
        REPLAYING,
        PLAYING,
        LEVEL_COMPLETE,
        RESTARTING_LEVEL,
        PAUSED,
        GAME_OVER,
        CREATING_ACCOUNT,
        RESUME_GAME
    }
}