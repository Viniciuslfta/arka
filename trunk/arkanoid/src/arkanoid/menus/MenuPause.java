/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.menus;

import arkanoid.GameState;
import arkanoid.GameState.GameStateType;
import arkanoid.models.ModelPlayArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author sPeC
 */
public class MenuPause extends Menu {

    ModelPlayArea mPlayArea;

    public void setPlayArea(ModelPlayArea m) {
        mPlayArea = m;
    }

    public MenuPause(String _title) {
        super(_title);

        JButton tmpOption;

        if (GameState.currentState() != GameStateType.GAME_OVER) {

            tmpOption = new JButton("Resumir");
            tmpOption.addActionListener(new ResumeListener());
            this.addOption(tmpOption);

            tmpOption = new JButton("Guardar Jogo");
            tmpOption.addActionListener(new SaveGameListener());
            this.addOption(tmpOption);
        }

        tmpOption = new JButton("Ler Jogo");
        tmpOption.addActionListener(new LoadGameListener());
        this.addOption(tmpOption);

        tmpOption = new JButton("Reiniciar Nivel");
        tmpOption.addActionListener(new ResetLevelListener());
        this.addOption(tmpOption);

        tmpOption = new JButton("Sair");
        tmpOption.addActionListener(new ExitListener());
        this.addOption(tmpOption);


        this.presentOptions(250);
    }

    class ResumeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // reacção associada ao botão "Resumir"
            GameState.changeState(GameStateType.RESUME_GAME);
        }
    }

    class SaveGameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // reacção associada ao botão "Guardar Jogo"
            
            mPlayArea.saveGame("teste.bin");
        }
    }

    class LoadGameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // reacção associada ao botão "Ler Jogo"
            mPlayArea.loadGame("teste.bin");
        }
    }

    class ResetLevelListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // reacção associada ao botão "Reiniciar Nivel"
            GameState.changeState(GameStateType.RESTARTING_LEVEL);

        }
    }

    class ExitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // reacção associada ao botão "Sair"
            System.exit(0);
        }
    }
}