/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid;

import arkanoid.replay.Replay;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sPeC
 */
public class Top10 implements Serializable {
    
    public class Top10Element implements Serializable, Comparable<Top10Element> {
        
        String mReplayName;
        
        public String getReplayName() {
            return mReplayName;
        }
        
        public void setReplayName(String _newName) {
            mReplayName = _newName;
        }
        String mName = "";
        
        public String getName() {
            return mName;
        }
        
        public void setName(String _newName) {
            mName = _newName;
        }
        long mPoints = 0;
        
        public long getPoints() {
            return mPoints;
        }
        
        public void setPoints(long _newPoints) {
            mPoints = _newPoints;
        }
        
        Top10Element(String _username, long _points) {
            mName = _username;
            mPoints = _points;
            mReplayName = Replay.getInstance().getUUID();
        }
        
        @Override
        public int compareTo(Top10Element t) {
            return t.getPoints() > mPoints ? 1 : 0;
        }
    }
    private static Top10 singletonObj = new Top10();
    
    public static Top10 getInstance() {
        return singletonObj;
    }
    List<Top10Element> mElements = new ArrayList<Top10Element>();
    
    public Top10Element getElement(int _idx) {
        return mElements.get(_idx);
    }
    long mLowerLimit;
    
    public int getNumberElements() {
        return mElements.size();
    }
    
    Top10() {
        Load();
    }
    
    public Object[][] getElementsAsTable() {
        Object[][] tmpTable = new Object[10][2];
        
        int currentIdx = 0;
        for (Top10Element elem : mElements) {
            tmpTable[currentIdx][0] = elem.getName();
            tmpTable[currentIdx][1] = elem.getPoints();
            
            currentIdx++;
        }
        
        return tmpTable;
    }
    
    private void UpdateBounds() {
        for (Top10Element elem : mElements) {
            if (elem.getPoints() < mLowerLimit) {
                mLowerLimit = elem.getPoints();
            }
        }
        
        Collections.sort(mElements);
    }
    
    public void update( long _points) {
        
        if (mLowerLimit > _points) {
            return;
        }
        
        String username = RegisteredPlayerData.getInstance().getUsername();
        
        if (mElements.size() >= 9) {
            // O último do top10 salta fora
            mElements.remove(9);
        }
        mElements.add(new Top10Element(username, _points));
        UpdateBounds();



        /*
        if (!mElements.isEmpty()) {
        for (Top10Element elem : mElements) {
        if (elem.getName() == null ? _username == null : elem.getName().equals(_username)) {
        
        if(elem.getPoints() > _points)
        return;
        
        elem.setPoints(_points);
        elem.setReplayName(Replay.getInstance().getUUID());
        UpdateBounds();
        return;
        }
        }
        }
         */

        // O último vais saltar fora do top 10 para entrar o novo
        /*mElements.get (9).setName(_username);
        mElements.get ( 9).setPoints(_points);
        
        UpdateBounds();*/
    }

    public void Save(long _points) {
        update( _points);
        
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream("top10.ark", false);
            out = new ObjectOutputStream(fos);
            out.writeObject(mElements);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public final void Load() {
        File file = new File("top10.ark");
        if (!file.exists()) {
            return;
        }
        
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream(file);
            in = new ObjectInputStream(fis);
            mElements = (List<Top10Element>) in.readObject();
            in.close();
            UpdateBounds();
        } catch (IOException ex) {
            ex.printStackTrace();
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Top10.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
