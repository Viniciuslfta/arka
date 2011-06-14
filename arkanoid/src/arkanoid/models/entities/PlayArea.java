package arkanoid.models.entities;

import arkanoid.GameState;
import arkanoid.GameState.GameStateType;
import arkanoid.RegisteredPlayerData;
import arkanoid.Settings;
import arkanoid.Sounds;
import arkanoid.Top10;
import arkanoid.models.entities.Bricks.Brick;
import arkanoid.models.entities.Wall.WallType;

import java.util.ArrayList;
import java.util.List;
import arkanoid.models.entities.Bonus.Bonus;
import arkanoid.models.entities.Bonus.BonusInvert;
import arkanoid.models.entities.Bonus.BonusUtils;
import arkanoid.replay.Replay;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/** Classe que representa Àrea de Jogo.
 * É composta essencialmente pela Bola, Raquete , Paredes e Tijolos
 * @author sPeC
 */
public class PlayArea implements Serializable {

    RegisteredPlayerData mPlayerData = RegisteredPlayerData.getInstance();

    RegisteredPlayerData getPlayerData() {
        return mPlayerData;
    }
    float mClubKeyMoveSpeed = Settings.CLUB_KEY_MOVE_SPEED;

    public float getClubKeyMoveSpeed() {
        return mClubKeyMoveSpeed;
    }

    public void setClubKeyMoveSpeed(float _ClubKeyMoveSpeed) {
        this.mClubKeyMoveSpeed = _ClubKeyMoveSpeed;
    }
    Wall mWalls[] = new Wall[3];

    /** Devolve o conjunto de paredes
     * 
     * @return array de paresdes 
     */
    public Wall[] getWalls() {
        return mWalls;
    }
    Ball mBall;

    /** Devolve Bola
     * 
     * @return Bola
     */
    public Ball getBall() {
        return mBall;
    }
    Club mClub;

    /** Devolve Raquete
     * 
     * @return Raquete
     */
    public Club getClub() {
        return mClub;

    }
    GameLevel mCurrentLevel;

    /** Devolve Nivel actual
     * 
     * @return Nivel actual
     */
    public GameLevel getCurrentLevel() {
        return mCurrentLevel;
    }
    private Player mPlayer;

    /** Devolve Jogador
     * 
     * @return Jogador
     */
    public Player getPlayer() {
        return mPlayer;
    }

    /** Devolve centro da Area de Jogo (em X)
     * 
     * @return centro da area de jogo (em X)
     */
    private int getPlayAreaCenterX() {
        return (Settings.DISPLAY_WIDTH - Settings.PLAY_AREA_START_X * 2) / 2;
    }
    Bonus mCurrentBonus;
    List<Bonus> mBonus = new ArrayList<Bonus>();

    public List<Bonus> getBonus() {
        return mBonus;
    }

    public void addBonus(Bonus _bonus) {
        mBonus.add(_bonus);
    }
    long mKeyboardLastPressTime = System.nanoTime();

    /** Constructor da classe
     * Instancia paredes, bola, raquete e tijolos(GameLevel)
     * 
     * @param _firstLevel nome do ficheiro do primeiro nível
     */
    public PlayArea(String _firstLevel) {

        if (GameState.currentState() != GameStateType.REPLAYING) {
            Replay.getInstance().Reset();
        }
        // Força a guarda do event de seed dos bónus
        BonusUtils.initRandom();

        //
        // Cria as paredes

        // Topo
        mWalls[0] = new Wall(0, 0, Settings.DISPLAY_WIDTH, Settings.PLAY_AREA_START_Y, WallType.TOP);

        // Esquerda
        mWalls[1] = new Wall(0, 0, Settings.PLAY_AREA_START_X, Settings.DISPLAY_HEIGHT, WallType.LEFT);

        // Direita
        mWalls[2] = new Wall(Settings.DISPLAY_WIDTH - Settings.PLAY_AREA_START_X,
                0, Settings.PLAY_AREA_START_X, Settings.DISPLAY_HEIGHT,
                WallType.RIGHT);

        // Cria bola
        mBall = new Ball(0, 0);
        mBall.setIsGluedToClub(true);

        // Cria o taco
        mClub = new Club(getPlayAreaCenterX() - Settings.CLUB_WIDTH / 2, Settings.CLUB_LOC_Y);

        // Cria o jogador
        mPlayer = new Player();

        if (_firstLevel != null) {
            changeLevel(_firstLevel);
        }

        /*mBall.setX(540);
        mBall.setY(380);
        mBall.setVelocityY(6.0f);
        mBall.setVelocityX(-6.0f);*/
    }

    /** Define comportamento tomado pelo jogo
     * quando detecta que a Bola está fora dos limites do ecrã
     */
    private void handleBallOutOfBounds() {

        if (mCurrentBonus != null) {
            mCurrentBonus.undoEffect(this);
            mCurrentBonus = null;
        }

        mPlayer.removeLifes(1);
        if (mPlayer.getLifes() == 0) {
            Sounds.getInstance().playGameOver();
            GameState.changeState(GameStateType.GAME_OVER);
            mBonus.clear();
            Replay.getInstance().AddTickEvent(mNumberOfTicks - 5);
            return;
        }

        // Coloca a bola centrada no taco
        mBall.setIsGluedToClub(true);
        mClub.placeBallAtCenter(mBall);
        mCurrentLevel.resetBallSpeed(mBall);
    }
    /** Define comportamento tomado pelo Jogo a cada iteração sua
     */
    private int mNumberOfTicks = 0;

    public void tick() {
        if (RegisteredPlayerData.getInstance().isLoggedIn()
                && GameState.currentState() != GameStateType.REPLAYING) {
            mNumberOfTicks++;
        }

        if (GameState.currentState() == GameStateType.RESTARTING_LEVEL) {
            Replay.getInstance().Reset();
            BonusUtils.initRandom();

            resetLevel();
            GameState.changeState(GameStateType.PLAYING);
        }

        if (GameState.currentState() == GameStateType.LEVEL_COMPLETE) {
            Sounds.getInstance().playLevelComplete();;
            changeLevel(mCurrentLevel.getNextLevelFilename());

            GameState.changeState(GameStateType.PLAYING);

            //se o jogador não é visitante, actualiza o nº de niveis completos
            if (RegisteredPlayerData.getInstance().isLoggedIn()) {
                if (mCurrentLevel.getLevelNumber() > RegisteredPlayerData.getInstance().getCompletedLevels()) {
                    //se o nivel actual é maior que o nº de niveis completos do jogador

                    RegisteredPlayerData.getInstance().setCompletedLevels(mCurrentLevel.getLevelNumber());
                    RegisteredPlayerData toAdd = RegisteredPlayerData.getInstance();
                    RegisteredPlayerData.getInstance().updatePlayersFile(toAdd.getUsername(), toAdd.getPassword(), toAdd.getCompletedLevels());
                }
            }
        }

        if (GameState.currentState() != GameStateType.PLAYING && GameState.currentState() != GameStateType.REPLAYING) {
            return;
        }

        // Bola
        if (!mBall.isGluedToClub()) {

            // Verifica se a bola saiu fora da área de jogo
            if (mBall.getY() > Settings.DISPLAY_HEIGHT) {
                handleBallOutOfBounds();

            } else {

                mBall.updatePosition();

                // A bola bateu no taco?
                checkBallClubCollision();

                // E numa parede?
                checkBallWallsCollisions();

                // E num dos blocos
                checkBallBricksCollisions();
            }
        }

        //Bonus
        checkClubBonusCollision();
        checkBonusOutOfBounds();
    }

    public void SaveTicks() {
        if (mNumberOfTicks > 0) {
            Replay.getInstance().AddTickEvent(mNumberOfTicks);
            mNumberOfTicks = 0;
        }
    }

    /** Verifica colisões entre bola e tijolos e define o comportamento resultant.
     * 
     * @return true se existe colisão, false caso contrário
     */
    private boolean checkBallBricksCollisions() {

        for (int l = 0; l < Settings.MAX_BRICK_ROWS; l++) {
            for (int c = 0; c < Settings.MAX_BRICK_COLUMNS; c++) {
                Brick tmpBrick = mCurrentLevel.getBrick(l, c);

                if (tmpBrick != null) {
                    if (tmpBrick.getY() + tmpBrick.getHeight() + Settings.BALL_SIZE < mBall.getY()) {
                        // Se o primeiro tijolo da linha está acima da bola
                        // então podemos saltar para a próxima linha
                        break;
                    }

                    if (!tmpBrick.isActive()) {
                        continue;
                    }

                    if (mBall.isCollidingWith(tmpBrick)) {
                        Sounds.getInstance().playBallBrick();

                        tmpBrick.onBallCollision(this);

                        // incrementa o número de blocos destruidos
                        if (!tmpBrick.isActive()) {
                            mCurrentLevel.incNumberOfDestroyedBricks();
                        }

                        // Verifica se foi o último
                        if (mCurrentLevel.isClear() && GameState.currentState() != GameStateType.REPLAYING) {
                            SaveTicks();
                            GameState.changeState(GameStateType.LEVEL_COMPLETE);
                        }

                        return true;
                    }
                }
            }
        }
        return false;
    }

    /** Verifica colisões entre Raquete e Paredes e define o comportamento resultante.
     * 
     * @return true se existe colisão, false caso contrário 
     */
    private boolean checkClubWallsCollisions() {
        for (Wall w : mWalls) {
            if (w.isCollidingWith(mClub)) {
                w.doEffectOnClub(mClub);
                return true;
            }
        }
        return false;
    }

    /** Verifica colisões entre Paredes e Bola e define o comportamento resultante.
     */
    private void checkBallWallsCollisions() {
        for (Wall w : mWalls) {
            if (w.isCollidingWith(mBall)) {
                Sounds.getInstance().playBallBump();
                w.doEffectOnBall(mBall);
                break;
            }
        }
    }

    /** Verifica colisões entre Bola e Raquete e define o comportamento resultante.
     * 
     * @return true se existe colisão, false caso contrário
     */
    private boolean checkBallClubCollision() {
        // Taco e bola
        if (mClub.isCollidingWith(mBall) && !mBall.isGluedToClub()) {
            float tmpVel = Math.abs(mBall.getVelocityY()) * -1;
            mBall.setVelocityY(tmpVel);

            // Altera a direcção da bola horizontalmente
            // consoante o lado em que bate no taco
            float tmpVelX = mBall.getVelocityX();
            float distFromCenter = mBall.getX() - (mClub.getX() + mClub.getWidth() / 2);
            distFromCenter *= .1;

            if (tmpVelX > 0) {
                if (distFromCenter > 0) {
                    tmpVelX += distFromCenter;
                    if (tmpVelX > Settings.BALL_MAX_VEL) {
                        tmpVelX = Settings.BALL_MAX_VEL;
                    }
                } else {
                    tmpVelX += distFromCenter;
                    if (tmpVelX < Settings.BALL_MIN_VEL) {
                        tmpVelX = Settings.BALL_MIN_VEL;
                    }
                }
            } else {
                if (distFromCenter > 0) {
                    tmpVelX += distFromCenter;
                    if (tmpVelX > -Settings.BALL_MIN_VEL) {
                        tmpVelX = -Settings.BALL_MIN_VEL;
                    }
                } else {
                    tmpVelX += distFromCenter;
                    if (tmpVelX < -Settings.BALL_MAX_VEL) {
                        tmpVelX = -Settings.BALL_MAX_VEL;
                    }
                }
            }
            mBall.setVelocityX(tmpVelX);

            //
            mBall.setY(mClub.getY() - Settings.BALL_SIZE);

            if (mBall.isSticky()) {
                mBall.setIsGluedToClub(true);
            }

            Sounds.getInstance().playBallBump();
            return true;
        }

        return false;
    }

    public void parseKey(int _key) {
        if (RegisteredPlayerData.getInstance().isLoggedIn()
                && GameState.currentState() != GameStateType.REPLAYING) {
            SaveTicks();
            Replay.getInstance().AddKeyPressEvent(_key);
        }

        switch (_key) {
            case Keyboard.KEY_LEFT:
            case Keyboard.KEY_RIGHT: {

                float x = mClub.getX() + mClub.getWidth() / 2;

                if (Keyboard.KEY_LEFT == _key) {
                    x -= mClubKeyMoveSpeed;
                } else {
                    x += mClubKeyMoveSpeed;
                }
                updateClubPos(x);
            }
            break;

            case Keyboard.KEY_SPACE: {
                if (mBall.isGluedToClub()) {
                    mBall.setIsGluedToClub(false);
                }
            }
            break;
        }
    }

    /** Analisa eventos do rato e actualiza raquete
     * 
     * @param _x posição do rato
     * @param _clicked booleano que indica se o jogador clicou no rato
     */
    public void parseMouse(float _x, boolean _clicked) {

        if (RegisteredPlayerData.getInstance().isLoggedIn()
                && GameState.currentState() != GameStateType.REPLAYING) {
            SaveTicks();
            Replay.getInstance().AddMouseEvent(_x, _clicked);
        }

        if (_clicked && mBall.isGluedToClub()) {
            mBall.setIsGluedToClub(false);
        }
        if (mCurrentBonus instanceof BonusInvert) {
            _x = _x - Settings.DISPLAY_WIDTH / 2;
            _x = _x * (-1);
            _x += Settings.DISPLAY_WIDTH / 2;

        }
        updateClubPos(_x);
    }

    /** Actualiza posição da Raquete
     * 
     * @param _x Posição do centro do taco
     */
    public void updateClubPos(float _x) {
        float ballDelta = mBall.getX() - mClub.getX();

        // Verifica se o taco se mantém dentro da área de jogo
        float x = _x - mClub.getWidth() / 2;
        mClub.setX(x);
        checkClubWallsCollisions();

        // Se a bola estiver colada ao taco é necessário actualizar 
        // a sua posição manualmente
        if (mBall.isGluedToClub()) {
            mBall.setX(mClub.getX() + ballDelta);
        }
    }

    /** Permite alterar o nível em que o jogo se encontra
     * 
     * @param _filename nome do ficheiro do novo nível 
     */
    public final void changeLevel(String _filename) {
        mCurrentLevel = new GameLevel(_filename, mBall, mPlayer);

        if (RegisteredPlayerData.getInstance().isLoggedIn() && GameState.currentState() != GameStateType.REPLAYING) {
            Replay.getInstance().AddChangeLevelEvent(_filename, mPlayer.getScore());
            Top10.getInstance().Save();
        }
        
        resetLevel();        
        Sounds.getInstance().playMusic();
    }

    /** Restablece nível.
     * Coloca a bola colada à raquete, centra raquete e restablece tijolos do nível 
     */
    public final void resetLevel() {

        // Reset aos bónus
        if (mCurrentBonus != null) {
            mCurrentBonus.undoEffect(this);
        }

        mBonus.clear();
        mCurrentBonus = null;

        mBall.setIsGluedToClub(true);

        // Centra o taco no centro da área de jogo
        float x = getPlayAreaCenterX() - Settings.CLUB_WIDTH / 2;
        updateClubPos(x);

        Mouse.setCursorPosition((int) x, 0);

        // Restablece largura 
        mClub.setWidth(Settings.CLUB_WIDTH);
        // Coloca a bola centrada no taco
        mClub.placeBallAtCenter(mBall);
        // Reinicia o nível
        mCurrentLevel.reset(mBall, mPlayer);
    }

    private void checkClubBonusCollision() {
        List<Bonus> removeList = new ArrayList<Bonus>();

        for (Bonus bonus : mBonus) {
            bonus.updatePosition();
            if (bonus.isCollidingWith(mClub)) {

                Sounds.getInstance().playPowerUp();

                if (mCurrentBonus != null) {
                    mCurrentBonus.undoEffect(this);
                }

                bonus.onClubCollision(this);

                mCurrentBonus = bonus;
                removeList.add(bonus);
            }
        }

        for (Bonus bonus : removeList) {
            mBonus.remove(bonus);
        }
    }

    private void checkBonusOutOfBounds() {
        List<Bonus> removeList = new ArrayList<Bonus>();

        for (Bonus bonus : mBonus) {
            if (bonus.getY() > Settings.DISPLAY_HEIGHT) {
                removeList.add(bonus);
            }
        }

        for (Bonus bonus : removeList) {
            mBonus.remove(bonus);
        }
    }
    Bonus mActiveBonus;

    public void setActiveBonus(Bonus _bonus) {
        mActiveBonus = _bonus;
    }

    public Bonus getActiveBonus() {
        return mActiveBonus;
    }

    public void ResetElapsedTime() {
        mBall.setLastUpdate(System.nanoTime());

        for (Bonus bonus : mBonus) {
            bonus.setLastUpdate(System.nanoTime());
        }
    }

    public void SaveGame(String _path) {

        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(_path, false);
            out = new ObjectOutputStream(fos);
            out.writeObject(this);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static PlayArea LoadGame(String _path) {
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream(_path);
            in = new ObjectInputStream(fis);
            PlayArea tmpArea = (PlayArea) in.readObject();
            in.close();

            // Verifica se o savegame pertence ao utilizador que está logado
            if (!RegisteredPlayerData.getInstance().getUsername().equals(tmpArea.getPlayerData().getUsername())) {
                JOptionPane.showMessageDialog(null, "Não permissões para aceder a este savegame.");
                return null;
            }

            GameState.setIsLoadedGame(true);
            return tmpArea;
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PlayArea.class.getName()).log(Level.SEVERE, null, ex);
        }

        JOptionPane.showMessageDialog(null, "Não foi possivel ler o ficheiro.");
        return null;
    }
}
