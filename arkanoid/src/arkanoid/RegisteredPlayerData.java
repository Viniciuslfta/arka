package arkanoid;

import java.io.Serializable;

/** Classe que representa a informação de um Jogador Registado
 *  É composta por um username, password e pelos níveis que já completou
 *
 * @author sPeC
 */
public class RegisteredPlayerData implements Serializable {
    
    
    private String mUsername;
    private String mPassword;
    private int[] mCompletedLevels;

    private static RegisteredPlayerData singletonObj = new RegisteredPlayerData("",""); 
    
    public static RegisteredPlayerData getInstance() {
        return singletonObj;
    }

    private RegisteredPlayerData() {
    }
    
    public RegisteredPlayerData(String _user, String _pass, int[] _completed) {
        mUsername = _user;
        mPassword = _pass;
        mCompletedLevels = _completed;
    }

    public RegisteredPlayerData(String _user, String _pass) {
        mUsername = _user;
        mPassword = _pass;
        mCompletedLevels = null;
    }
    

    
    public int[] getmCompletedLevels() {
        return mCompletedLevels;
    }

    public void setCompletedLevels(int[] mCompletedLevels) {
        this.mCompletedLevels = mCompletedLevels;
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
}
