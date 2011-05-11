/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.models.entities;

import arkanoid.Settings;
import arkanoid.models.entities.Bricks.Brick;
import arkanoid.models.entities.Bricks.BrickUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sPeC
 */
public class GameLevel {

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

    public int getLevelNumber() {
        return mLevelNumber;
    }
    private String mName;

    public String getName() {
        return mName;
    }
    private String mNextLevelFilename;

    public String getNextLevelFilename() {
        return mNextLevelFilename;
    }
    private float mBallVelocity;

    public float getBallVelocity() {
        return mBallVelocity;
    }
    private int mNumberOfBricks;
    private int mNumberDestroyedBricks;

    public void incNumberOfDestroyedBricks() {
        mNumberDestroyedBricks++;
    }

    public boolean isClear() {
        return mNumberDestroyedBricks == mNumberOfBricks;
    }
    //
    //
    private Brick[][] mBricks = new Brick[Settings.MAX_BRICK_ROWS][Settings.MAX_BRICK_COLUMNS];

    public Brick getBrick(int _l, int _c) {
        if (_l < 0 || _c < 0) {
            return null;
        }

        if (_c >= Settings.MAX_BRICK_COLUMNS || _l >= Settings.MAX_BRICK_ROWS) {
            return null;
        }

        return mBricks[_l][_c];
    }
    //
    // Indica se o nível foi carregado para memória ou não
    private boolean mIsLoaded;

    public boolean isLoaded() {
        return mIsLoaded;
    }

    //
    //
    public GameLevel(String _filename, Ball _ball, Player _player) {
        mIsLoaded = readLevelFile(_filename);

        if (mIsLoaded) {
            mInitalPlayerScore = _player.getScore();
            reset(_ball, _player);
        }
    }

    //
    //
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

    //
    //
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

    //
    //
    private void processBricksLine(String aLine, int _curBricksLine) {

        int posX = Settings.BRICK_INTERVAL + Settings.PLAY_AREA_START_X;
        int posY = _curBricksLine * Settings.BRICK_HEIGHT + _curBricksLine * Settings.BRICK_INTERVAL;
        posY += Settings.BRICK_INTERVAL;
        posY += Settings.PLAY_AREA_START_Y;


        for (int i = 0; i < Settings.MAX_BRICK_COLUMNS && i < aLine.length(); i++) {
            this.mBricks[_curBricksLine][i] = BrickUtils.getBrickFromType(aLine.charAt(i), posX, posY);
            if (mBricks[_curBricksLine][i] != null) {
                mBricks[_curBricksLine][i].setLocationOnPlayArea(_curBricksLine, i);
                ++mNumberOfBricks;
            }

            posX += Settings.BRICK_WIDTH + Settings.BRICK_INTERVAL;
        }
    }

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

    private String processInfoLine(String aLine) {
        Scanner scanner = new Scanner(aLine);
        scanner.useDelimiter("=");

        if (scanner.hasNext()) {
            String name = scanner.next();
            return scanner.next();
        }

        return null;
    }

    public void resetBallSpeed(Ball _ball) {
        _ball.setVelocityX(mBallVelocity);
        _ball.setVelocityY(-mBallVelocity);
    }

    public void resetPlayerInfo(Player _player) {
        _player.setScore(mInitalPlayerScore);
        _player.setLifes(mInitalLives);
    }
}
