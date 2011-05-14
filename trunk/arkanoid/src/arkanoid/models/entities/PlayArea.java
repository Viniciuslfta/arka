package arkanoid.models.entities;

import arkanoid.GameState;
import arkanoid.GameState.GameStateType;
import arkanoid.Settings;
import arkanoid.models.entities.Bricks.Brick;
import arkanoid.models.entities.Wall.WallType;

import java.util.ArrayList;
import java.util.List;
import arkanoid.models.entities.Bonus;

/** Classe que representa Àrea de Jogo.
 * É composta essencialmente pela Bola, Raquete , Paredes e Tijolos
 * @author sPeC
 */
public class PlayArea {

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

    /** Constructor da classe
     * Instancia paredes, bola, raquete e tijolos(GameLevel)
     * 
     * @param _firstLevel nome do ficheiro do primeiro nível
     */
    public PlayArea(String _firstLevel) {

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

        changeLevel(_firstLevel);

    }

    /** Define comportamento tomado pelo jogo
     * quando detecta que a Bola está fora dos limites do ecrã
     */
    private void handleBallOutOfBounds() {

        mPlayer.removeLifes(1);
        if (mPlayer.getLifes() == 0) {
            GameState.changeState(GameStateType.GAME_OVER);
            return;
        }

        // Coloca a bola centrada no taco
        mBall.setIsGluedToClub(true);
        mClub.placeBallAtCenter(mBall);
        mCurrentLevel.resetBallSpeed(mBall);
    }

    /** Define comportamento tomado pelo Jogo a cada iteração sua
     */
    public void tick() {

        if (GameState.currentState() == GameStateType.RESTARTING_LEVEL) {
            resetLevel();
            GameState.changeState(GameStateType.PLAYING);
        }

        if (GameState.currentState() == GameStateType.LEVEL_COMPLETE) {
            changeLevel(mCurrentLevel.getNextLevelFilename());
            GameState.changeState(GameStateType.PLAYING);
        }

        if (GameState.currentState() != GameStateType.PLAYING) {
            return;
        }

        // Bola
        if (!mBall.isGluedToClub()) {

            // Verifica se a bola saiu fora da área de jogo
            if (mBall.getY() > Settings.DISPLAY_HEIGHT) {
                handleBallOutOfBounds();

            } else {

                // A bola bateu no taco?
                checkBallClubCollision();

                // E numa parede?
                checkBallWallsCollisions();

                // E num dos blocos
                checkBallBricksCollisions();

                mBall.updatePosition();
            }
        }
        
        //Bonus
        if(mBonus.size()>0) {
            for(Bonus bonus : mBonus) {
                bonus.updatePosition();
            }
            handleBonusOutOfBounds();
            checkClubBonusCollision();
            
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

                    if (!tmpBrick.isActive()
                            || tmpBrick.getX() > mBall.getX()
                            || (tmpBrick.getX() + tmpBrick.getWidth()) < mBall.getX()) {
                        continue;
                    }

                    if (mBall.isCollidingWith(tmpBrick)) {
                        tmpBrick.onBallCollision(this);

                        // incrementa o número de blocos destruidos
                        if (!tmpBrick.isActive()) {
                            mCurrentLevel.incNumberOfDestroyedBricks();
                        }

                        // Verifica se foi o último
                        if (mCurrentLevel.isClear()) {
                            GameState.changeState(GameStateType.LEVEL_COMPLETE);
                        }
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
            float tmpVel = mBall.getVelocityX() + mClub.getDeltaX() * Settings.CLUB_SPEED_INC;

            if (tmpVel > mBall.getVelocityX()) {
                mBall.setVelocityX(tmpVel);
            }

            tmpVel = Math.abs(mBall.getVelocityY()) * -1;
            mBall.setVelocityY(tmpVel);

            mBall.setY(mClub.getY() - Settings.BALL_SIZE);
            return true;
        }

        return false;
    }

    /** Analisa eventos do rato e actualiza raquete
     * 
     * @param _x posição do rato
     * @param _clicked booleano que indica se o jogador clicou no rato
     */
    public void parseMouse(float _x, boolean _clicked) {

        if (_clicked && mBall.isGluedToClub()) {
            mBall.setIsGluedToClub(false);
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

        resetLevel();
    }

    /** Restablece nível.
     * Coloca a bola colada à raquete, centra raquete e restablece tijolos do nível 
     */
    public final void resetLevel() {

        mBall.setIsGluedToClub(true);

        // Centra o taco no centro da área de jogo
        float x = getPlayAreaCenterX() - Settings.CLUB_WIDTH / 2;
        updateClubPos(x);
        // Coloca a bola centrada no taco
        mClub.placeBallAtCenter(mBall);
        // Restablece largura 
        mClub.setWidth(Settings.CLUB_WIDTH);
        // Reinicia o nível
        mCurrentLevel.reset(mBall, mPlayer);
    }
    
    List<Bonus> mBonus = new ArrayList<Bonus>();

    public List<Bonus> getBonus(){
        return mBonus;
    }
    
    public void addBonus(Bonus _bonus) {
        mBonus.add(_bonus);
    }

    private boolean checkClubBonusCollision() {
        boolean ret = false;
        
        for (Bonus bonus : mBonus) {
            if (bonus.isCollidingWith(getClub())) {
                bonus.onClubCollision(this);
                mBonus.remove(bonus);
                ret = true;
                break;
            }
        }
        
        return ret;
    }

    private void handleBonusOutOfBounds() {
        for (Bonus bonus : mBonus) {
            if (bonus.isCollidingWith(getClub())) {
                if (bonus.getY() > Settings.DISPLAY_HEIGHT) {
                    mBonus.remove(bonus);
                }
            }
        }
    }
}
