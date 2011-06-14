/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.replay;

import arkanoid.Settings;
import arkanoid.models.ModelPlayArea;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sPeC
 */
public class Replay implements Serializable {

    LinkedList<GameEvent> mEvents = new LinkedList<GameEvent>();
    private static Replay singletonObj = null;
    private GameEvent mCurrEvent = null;

    public static Replay getInstance() {
        if (singletonObj == null) {
            singletonObj = new Replay();
            singletonObj.Reset();
        }

        return singletonObj;
    }

    public void resetInstance() {
        singletonObj = null;
    }
    private long mStartTime = System.currentTimeMillis();
    String mUUID;

    public String getUUID() {
        return mUUID.substring(0, 5);
    }

    public void setSpeed(float _newSpeed) {
        mSpeed = _newSpeed;
    }

    public float getSpeed() {
        return mSpeed;
    }
    float mSpeed = 1;

    public Replay() {
        Reset();
    }
    private boolean mIsReplaying = false;

    public boolean isReplaying() {
        return mIsReplaying;
    }

    public void AddChangeViewEvent(boolean _useTex) {
        mEvents.push(new ChangeViewEvent(_useTex, getTimeStamp()));
    }

    public void AddKeyPressEvent(int _key) {
        mEvents.push(new KeyPressEvent(_key, getTimeStamp()));
    }

    public void AddMouseEvent(float _x, boolean _clicked) {
        mEvents.push(new MouseEvent(_x, _clicked, getTimeStamp()));
    }

    public void AddChangeLevelEvent(String _lvlName, long _points) {
        mEvents.push(new ChangeLevelEvent(_lvlName, (int) _points, getTimeStamp()));
    }

    public void AddChangeBonusSeedEvent(long _seed) {
        mEvents.push(new ChangeBonusSeedEvent(_seed, getTimeStamp()));
    }

    public void AddTickEvent(int _quantity) {
        mEvents.push(new TickEvent(_quantity, getTimeStamp()));
    }

    public final void Reset() {
        mUUID = UUID.randomUUID().toString();
        mEvents.clear();
        mStartTime = System.currentTimeMillis();
        mIsReplaying = false;
    }
    LinkedList<GameEvent> mReplayingEvents;

    public void resetReplay(ModelPlayArea _areaModel) {
        mIsReplaying = false;

        mReplayingEvents = new LinkedList<GameEvent>(mEvents);

        // O primeiro carrega o nivel 1 e altera a seed
        mCurrEvent = mReplayingEvents.pollLast();
        mCurrEvent.execute(_areaModel);
        mCurrEvent = mReplayingEvents.pollLast();
        mCurrEvent.execute(_areaModel);

        mCurrEvent = mReplayingEvents.pollLast();

        mStartTime = 0;
        mSpeed = 1;
    }

    public void play() {
        mIsReplaying = true;
        mLastTime = System.currentTimeMillis();
    }
    transient private long mLastTime;

    public void tick(ModelPlayArea _areaModel) {
        if (!mIsReplaying) {
            return;
        }

        if (mCurrEvent == null) {
            resetReplay(_areaModel);
        }

        mStartTime += Settings.GAME_DELAY;
        if (mCurrEvent.getTimeStamp() <= mStartTime) {
            if (mCurrEvent instanceof TickEvent) {
                mCurrEvent.execute(_areaModel);
                ((TickEvent) mCurrEvent).mQuantity--;

                if (((TickEvent) mCurrEvent).mQuantity <= 0) {

                    ((TickEvent) mCurrEvent).reset();
                    mCurrEvent = mReplayingEvents.pollLast();
                    if (mCurrEvent == null) {
                        resetReplay(_areaModel);
                    }
                }
            } else {
                mCurrEvent.execute(_areaModel);

                mCurrEvent = mReplayingEvents.pollLast();
                if (mCurrEvent == null) {
                    resetReplay(_areaModel);
                }

                mLastTime = System.currentTimeMillis();
                return;
            }
        }

        int timeToSleep = (int) (System.currentTimeMillis() - mLastTime);
        timeToSleep = Settings.GAME_DELAY - timeToSleep;
        timeToSleep = timeToSleep > Settings.GAME_DELAY || timeToSleep < 0 ? Settings.GAME_DELAY : timeToSleep;

        try {
            Thread.sleep((int) Math.ceil(timeToSleep / mSpeed));
        } catch (InterruptedException ex) {
            Logger.getLogger(Replay.class.getName()).log(Level.SEVERE, null, ex);
        }
        mLastTime = System.currentTimeMillis();

    }

    public void nextLevel(ModelPlayArea _area) {
        LinkedList<GameEvent> tmpList = new LinkedList<GameEvent>(mReplayingEvents);
        if (mCurrEvent instanceof TickEvent) {
            ((TickEvent) mCurrEvent).reset();
        }

        while (tmpList.size() > 0) {
            GameEvent tmpEvent = tmpList.pollLast();
            if (tmpEvent instanceof ChangeLevelEvent) {
                // Actualiza o ecrã
                tmpEvent.execute(_area);
                tmpEvent = tmpList.pollLast();

                mReplayingEvents = new LinkedList<GameEvent>(tmpList);
                mCurrEvent = tmpEvent;
                mStartTime = tmpEvent.getTimeStamp();
                return;
            }
        }
    }

    public void stop() {
        mIsReplaying = false;
    }

    public void Save() {
        String path = Settings.REPLAY_PATH.concat(mUUID.substring(0, 5)).concat(".ark");
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(path, false);
            out = new ObjectOutputStream(fos);
            out.writeObject(this);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void Load(String _replayName) {
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream(Settings.REPLAY_PATH.concat(_replayName).concat(".ark"));
            in = new ObjectInputStream(fis);
            Replay tmpReplay = (Replay) in.readObject();
            in.close();

            singletonObj = tmpReplay;
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Replay.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private long getTimeStamp() {
        return System.currentTimeMillis() - mStartTime;
    }
}
