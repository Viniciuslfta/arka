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
    private static Replay singletonObj = new Replay();
    private GameEvent mCurrEvent = null;

    public static Replay getInstance() {
        return singletonObj;
    }
    private long mStartTime = System.currentTimeMillis();
    String mUUID;

    public String getUUID() {
        return mUUID.substring(0, 5);
    }

    public void setSpeed(int _newSpeed) {
        mSpeed = _newSpeed;
    }
    int mSpeed = 1;

    public Replay() {
        Reset();
    }
    private boolean mIsReplaying = false;

    public boolean isReplaying() {
        return mIsReplaying;
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
        mEvents.push(new TickEvent(_quantity));
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
    }

    public void play() {
        mIsReplaying = true;
    }

    public void tick(ModelPlayArea _areaModel) {
        if (!mIsReplaying) {
            return;
        }

        if (mCurrEvent == null) {
            resetReplay(_areaModel);
        }

        if (mCurrEvent.getTimeStamp() > (mStartTime + Settings.GAME_DELAY)) {
            mStartTime += Settings.GAME_DELAY;
        } else {
            if (mCurrEvent instanceof TickEvent) {
                mCurrEvent.execute(_areaModel);
                ((TickEvent) mCurrEvent).mQuantity--;

                if (((TickEvent) mCurrEvent).mQuantity <= 0) {

                    ((TickEvent) mCurrEvent).reset();
                    mCurrEvent = mReplayingEvents.pollLast();
                    if (mCurrEvent == null) {
                        resetReplay(_areaModel);
                    }
                    mStartTime = mCurrEvent.getTimeStamp();
                }
            } else {
                mCurrEvent.execute(_areaModel);

                mCurrEvent = mReplayingEvents.pollLast();
                if (mCurrEvent == null) {
                    resetReplay(_areaModel);
                }
            }
        }

        try {
            Thread.sleep((int) Math.ceil(Settings.GAME_DELAY / mSpeed));
        } catch (InterruptedException ex) {
            Logger.getLogger(Replay.class.getName()).log(Level.SEVERE, null, ex);
        }
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