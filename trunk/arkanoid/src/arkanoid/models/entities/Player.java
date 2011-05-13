/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.models.entities;

import arkanoid.RegisteredPlayerData;
import java.util.Observable;

/** Classe que representa o Jogador
 * É composta pelo seu numero de vidas, pontuação e atributo que indica se o Jogador está loggado
 * @author sPeC
 */
public class Player extends Observable {

    private boolean mIsLoggedIn;
    // Vidas
    private int mLifes;

    /** Retorna as vidas do jogador
     * 
     * @return numero de vidas 
     */
    public int getLifes() {
        return mLifes;
    }

    /** Altera numero de vidas do Jogador
     * 
     * @param _quant quantidade de vidas a atribuir
     */
    public void setLifes(int _quant) {
        this.mLifes = _quant;
    }

    /** Adiciona vidas ao Jogador
     * 
     * @param _quant quantidade de vidas a adicionar
     */
    public void addLifes(int _quant) {
        this.mLifes += _quant;
    }

    /** Retira vidas ao Jogadpr
     * 
     * @param _quant quantidade de vidas a retirar
     */
    public void removeLifes(int _quant) {
        this.mLifes -= _quant;
    }
    // Pontos
    private long mScore;

    /** Retorna pontuação do jogador
     * 
     * @return pontuação 
     */
    public long getScore() {
        return mScore;
    }

    /** Altera pontuação do Jogador
     * 
     * @param _newScore valor a atribuir à pontuação
     */
    public void setScore(long _newScore) {
        this.mScore = _newScore;
    }

    /** Adiciona pontuação ao Jogador
     * 
     * @param _quant valor a adicionar 
     */
    public void addScorePoints(int _quant) {
        this.mScore += _quant;
    }
    
    RegisteredPlayerData mRegData;
}
