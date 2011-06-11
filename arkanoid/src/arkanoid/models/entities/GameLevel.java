/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.models.entities;

import arkanoid.Settings;
import arkanoid.Textures;
import arkanoid.models.entities.Bricks.Brick;
import arkanoid.models.entities.Bricks.BrickUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.opengl.Texture;

/** Classe que representa um nível.
 * È composta pelo  numero do nivel e nome nivel,
 * numero de vidas e pontuação inicial do jogador,
 * velocidade a atribuir à bola e  tijolos.
 * @author sPeC
 */
public class GameLevel implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(GameLevel.class.getName());

    static {
        try {
            LOGGER.addHandler(new FileHandler("errors.log", true));
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, ex.toString(), ex);
        }
    }
    private int mInitalLives;
    private long mInitalPlayerScore;
    private int mLevelNumber;

    /** Devolve número do nível.
     * @return número do nível
     */
    public int getLevelNumber() {
        return mLevelNumber;
    }
    private String mName;

    /** Devolve nome do nível.
     * @return nome do nível
     */
    public String getName() {
        return mName;
    }
    private String mNextLevelFilename;

    /** Devolve nome do ficheiro do nível seguinte.
     * @return nome do ficheiro do nível seguinte 
     */
    public String getNextLevelFilename() {
        return mNextLevelFilename;
    }
    private float mBallVelocity;

    /** Devolve velocidade da bola no nível actual.
     * @return velocidade da bola
     */
    public float getBallVelocity() {
        return mBallVelocity;
    }
    private int mNumberOfBricks;
    private int mNumberDestroyedBricks;

    /** Aumenta quantidade de Tijolos destruidos
     */
    public void incNumberOfDestroyedBricks() {
        mNumberDestroyedBricks++;
    }

    /** Verifica se todos os tijolos estão destruídos.
     * @return true caso todos os tijolos estejam destruídos, false caso contrário
     */
    public boolean isClear() {
        return mNumberDestroyedBricks == mNumberOfBricks;
    }
    private Brick[][] mBricks = new Brick[Settings.MAX_BRICK_ROWS][Settings.MAX_BRICK_COLUMNS];

    /** Devolve um tijolo que se encontre na posição pretendida.
     * 
     * @param _l linha pretendida
     * @param _c coluna pretendida
     * @return  Tijolo que se encontra na posição pretendida
     */
    public Brick getBrick(int _l, int _c) {
        if (_l < 0 || _c < 0) {
            return null;
        }

        if (_c >= Settings.MAX_BRICK_COLUMNS || _l >= Settings.MAX_BRICK_ROWS) {
            return null;
        }

        return mBricks[_l][_c];
    }
    // Indica se o nível foi carregado para memória ou não
    private boolean mIsLoaded;

    /** Devolve boolean que indica se o nível foi carregado para memória ou não.
     * @return  true se nivel foi carregado para memória, false caso contrário
     */
    public boolean isLoaded() {
        return mIsLoaded;
    }
    String mBackgroundName;
    transient Texture mBackgndTexture = null;

    public Texture getBackgndTexture() {

        if (mBackgndTexture == null) {
            mBackgndTexture = Textures.loadTexture(mBackgroundName);
        }

        return mBackgndTexture;
    }

    /** Constructor da classe.
     * 
     * @param _filename nome do ficheiro do nivel a carregar
     * @param _ball Bola
     * @param _player Jogador
     */
    public GameLevel(String _filename, Ball _ball, Player _player) {
        mIsLoaded = readLevelFile(_filename);

        if (mIsLoaded) {
            mInitalPlayerScore = _player.getScore();
            reset(_ball, _player);
        }
    }

    public GameLevel(String _filename) {
        mIsLoaded = readLevelFile(_filename);

    }

    /** Lê ficheiro de nível.
     * 
     * @param _filename nome do ficheiro
     * @return true se ficheiro lido com sucesso, false caso contrário
     */
    private boolean readLevelFile(String _filename) {

        String path = System.getProperty("user.dir") + "\\levels\\" + _filename;
        try {
            processLevelFileLineByLine(new File(path));
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.toString());
            return false;
        }

        return true;
    }

    /** Processa ficheiro de nível linha a linha.
     * 
     * @param _file nome do ficheiro a processar
     * @throws FileNotFoundException 
     */
    public final void processLevelFileLineByLine(File _file) throws FileNotFoundException {

        // Usamos o FileReader em vez do File, porque não possivel fechar um File
        Scanner scanner = new Scanner(new FileReader(_file));

        try {
            // Obtém informação geral sobre o nível
            mLevelNumber = Integer.valueOf(processInfoLine(scanner.nextLine()));
            mName = processInfoLine(scanner.nextLine());
            mNextLevelFilename = processInfoLine(scanner.nextLine());
            mInitalLives = Integer.valueOf(processInfoLine(scanner.nextLine()));
            mBallVelocity = Float.valueOf(processInfoLine(scanner.nextLine()));

            mBackgroundName = processInfoLine(scanner.nextLine());
            

            int curBricksLine = 0;
            mNumberOfBricks = 0;
            while (scanner.hasNextLine() && Settings.MAX_BRICK_ROWS > curBricksLine) {
                // Lê os blocos de cada uma das linhas
                processBricksLine(scanner.nextLine(), curBricksLine++);
            }

        } finally {
            scanner.close();
        }
    }

    /** Processa linha de texto do ficheiro com informação acerca dos Tijolos e instancia-os.
     * 
     * @param aLine texto da linha a processar
     * @param _curBricksLine linha a que pertencem o tijolos que vao ser instanciados
     */
    private void processBricksLine(String aLine, int _curBricksLine) {

        int posX = Settings.BRICK_INTERVAL + Settings.PLAY_AREA_START_X;
        int posY = _curBricksLine * Settings.BRICK_HEIGHT + _curBricksLine * Settings.BRICK_INTERVAL;
        posY += Settings.BRICK_INTERVAL;
        posY += Settings.PLAY_AREA_START_Y;


        for (int i = 0; i < Settings.MAX_BRICK_COLUMNS && i < aLine.length(); i++) {
            //instancia Tijolo:
            this.mBricks[_curBricksLine][i] = BrickUtils.getBrickFromType(aLine.charAt(i), posX, posY);
            if (mBricks[_curBricksLine][i] != null) {
                mBricks[_curBricksLine][i].setLocationOnPlayArea(_curBricksLine, i);
                ++mNumberOfBricks;
            }

            posX += Settings.BRICK_WIDTH + Settings.BRICK_INTERVAL;
        }
    }

    /** Restablece nivel.
     *  Restablece Tijolos, velocidade da bola e informação do jogador
     * @param _ball Bola
     * @param _player Jogador
     */
    public final void reset(Ball _ball, Player _player) {
        for (int l = 0; l < Settings.MAX_BRICK_ROWS; l++) {
            for (int c = 0; c < Settings.MAX_BRICK_COLUMNS; c++) {
                if (mBricks[l][c] != null) {
                    mBricks[l][c].reset();
                }
            }
        }

        mNumberDestroyedBricks = 0;

        resetBallSpeed(_ball);
        resetPlayerInfo(_player);

    }

    /** Extrai informação relevante de uma linha de informação do ficheiro. 
     * 
     * @param aLine linha de texto a processar
     * @return texto extraído
     */
    private String processInfoLine(String aLine) {
        Scanner scanner = new Scanner(aLine);
        scanner.useDelimiter("=");

        if (scanner.hasNext()) {
            String name = scanner.next();
            return scanner.next();
        }

        return null;
    }

    /** Restablece velocidade da bola.
     * @param _ball Bola
     */
    public void resetBallSpeed(Ball _ball) {
        _ball.setVelocityX(mBallVelocity);
        _ball.setVelocityY(-mBallVelocity);
    }

    /** Restablece informação do jogador.
     * @param _player Jogador
     */
    public void resetPlayerInfo(Player _player) {
        _player.setScore(mInitalPlayerScore);
        _player.setLifes(mInitalLives);
    }
}
