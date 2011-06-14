/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.menus;

import arkanoid.GameState;
import arkanoid.GameState.GameStateType;
import arkanoid.Settings;
import arkanoid.Top10;
import arkanoid.replay.Replay;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author sPeC
 */
public class MenuPause extends Menu {

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

        tmpOption = new JButton("Voltar ao Menu Inicial");
        tmpOption.addActionListener(new ReturnToMainMenuListener());
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
            JFileChooser fileChooser = new JFileChooser(Settings.SAVE_PATH);
            fileChooser.setFileFilter(new SaveFilesFilter());

            setAlwaysOnTop(false);
            if (fileChooser.showSaveDialog(MenuPause.this) == JFileChooser.APPROVE_OPTION) {
                File myFile = fileChooser.getSelectedFile();

                mPlayArea.saveGame(SaveFilesFilter.addExtension(myFile));
            }
        }
    }

    class LoadGameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // reacção associada ao botão "Ler Jogo"
            JFileChooser fileChooser = new JFileChooser(Settings.SAVE_PATH);
            FileFilter filter = new SaveFilesFilter();
            fileChooser.setFileFilter(filter);

            setAlwaysOnTop(false);
            if (fileChooser.showOpenDialog(MenuPause.this) == JFileChooser.APPROVE_OPTION) {
                File myFile = fileChooser.getSelectedFile();

                if (mPlayArea.loadGame(myFile.getAbsolutePath())) {
                    GameState.changeState(GameStateType.RESUME_GAME);
                }
            }
        }
    }

    class ResetLevelListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // reacção associada ao botão "Reiniciar Nivel"
            GameState.changeState(GameStateType.RESTARTING_LEVEL);
        }
    }

    class ReturnToMainMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // reacção associada ao botão "Resumir"
            GameState.changeState(GameStateType.MAIN_MENU);
            Replay.getInstance().Save();
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
