package arkanoid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import javax.swing.JOptionPane;

/** Classe que representa a informação de um Jogador Registado
 *  É composta por um username, password e pelos níveis que já completou
 *
 * @author sPeC
 */
public class RegisteredPlayerData implements Serializable {

    private String mUsername;
    private String mPassword;
    private int mCompletedLevels;
    private String mStartingLevel = "level1.txt";
    private static RegisteredPlayerData singletonObj = new RegisteredPlayerData("", "");

    public String getStartingLevel() {
        return mStartingLevel;
    }

    public void setStartingLevel(String mStartingLevel) {
        this.mStartingLevel = mStartingLevel;
    }

    public void setInstance(RegisteredPlayerData _player) {
        singletonObj = _player;
    }

    public static RegisteredPlayerData getInstance() {
        return singletonObj;
    }
    private boolean mIsLoggedIn = false;

    public boolean isLoggedIn() {
        return mIsLoggedIn;
    }

    public void setIsLoggedIn(boolean _isLogged) {
        mIsLoggedIn = _isLogged;
    }

    public void reset() {
        mUsername = "";
        mIsLoggedIn = false;
    }

    private RegisteredPlayerData() {
    }
    transient boolean mDrawTextures = false;

    public boolean drawTextures() {
        return mDrawTextures;
    }

    public void drawTextures(boolean _drawText) {
        this.mDrawTextures = _drawText;
    }

    public RegisteredPlayerData(String _user, String _pass, int _completed) {
        mUsername = _user;
        mPassword = _pass;
        mCompletedLevels = _completed;
    }

    public RegisteredPlayerData(String _user, String _pass) {
        mUsername = _user;
        mPassword = _pass;
        mCompletedLevels = 1;
    }

    public int getCompletedLevels() {

        return mCompletedLevels;
    }

    public void setCompletedLevels(int _completedLevels) {
        this.mCompletedLevels = _completedLevels;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public List<RegisteredPlayerData> readPlayersFile() {
        List<RegisteredPlayerData> readPlayers = null;
        ObjectInputStream in = null;
        File file = new File("players.ark");

        if (file.exists()) {

            try {

                in = new ObjectInputStream(new FileInputStream(file));
                readPlayers = (List<RegisteredPlayerData>) in.readObject();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
                ex.printStackTrace();
            } finally {
                //fecha stream
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                    ex.printStackTrace();
                }
            }
        }
        return readPlayers;
    }

    public void writePlayersFile(List<RegisteredPlayerData> _newPlayers) {
        ObjectOutputStream out = null;
        File file = new File("players.ark");
        try {//abre outputstream
            out = new ObjectOutputStream(new FileOutputStream(file, false));
            out.writeObject(_newPlayers);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            //Close the ObjectInputStream
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
            }

        }

    }

    public void updatePlayersFile(String _user, String _pass, int _completed) {
        RegisteredPlayerData toAdd = new RegisteredPlayerData(_user, _pass, _completed);

        List<RegisteredPlayerData> playersList = readPlayersFile();

        //se encontrar na lista o jogador a actualizar, nao o volta a adicionar:  
        int found = 0;
        for (int i = 0; i < playersList.size(); i++) {
            if (playersList.get(i).getUsername().equals(_user)) {
                playersList.get(i).setCompletedLevels(_completed);
            }
        }
        if (found == 0) {
            playersList.add(toAdd);
        }
        writePlayersFile(playersList);
    }
}
