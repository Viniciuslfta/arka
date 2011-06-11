/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.menus;

import arkanoid.GameState;
import arkanoid.GameState.GameStateType;
import arkanoid.RegisteredPlayerData;
import arkanoid.Settings;
import arkanoid.models.ModelPlayArea;
import arkanoid.models.entities.PlayArea;
import java.awt.HeadlessException;
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
public class MenuInicial extends Menu {

    public MenuInicial(String string) throws HeadlessException {
        super(string);

        JButton tmpOption;

        tmpOption = new JButton("Novo Jogo");
        tmpOption.addActionListener(new NewGameListener());
        this.addOption(tmpOption);

        tmpOption = new JButton("Ler Jogo");
        tmpOption.addActionListener(new LoadGameListener());
        this.addOption(tmpOption);

        if (!RegisteredPlayerData.getInstance().isLoggedIn()) {
            tmpOption = new JButton("Criar Registo");
            tmpOption.addActionListener(new CreateAccountListener());
            this.addOption(tmpOption);

            tmpOption = new JButton("Login");
            tmpOption.addActionListener(new LoginListener());
            this.addOption(tmpOption);
        }


        if (RegisteredPlayerData.getInstance().isLoggedIn() && RegisteredPlayerData.getInstance().getCompletedLevels()>1) {
            tmpOption = new JButton("Reinicio Rápido");
            tmpOption.addActionListener(new FastRestarListener());
            this.addOption(tmpOption);
        }


        tmpOption = new JButton("Pontuação");
        tmpOption.addActionListener(new ShowScoreListener());
        this.addOption(tmpOption);

        if (RegisteredPlayerData.getInstance().isLoggedIn()) {
            tmpOption = new JButton("Logout");
            tmpOption.addActionListener(new LogoutListener());
            this.addOption(tmpOption);
        }

        tmpOption = new JButton("Sair");
        tmpOption.addActionListener(new ExitListener());
        this.addOption(tmpOption);


        this.presentOptions(250);
    }

    class FastRestarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // reacção associada ao botão "Reinicio Rápido"
            GameState.changeState(GameStateType.FAST_RESTART);
        }
    }

    class NewGameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // reacção associada ao botão "Novo Jogo"
            GameState.changeState(GameStateType.PLAYING);
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
            if (fileChooser.showOpenDialog(MenuInicial.this) == JFileChooser.APPROVE_OPTION) {
                File myFile = fileChooser.getSelectedFile();

                mPlayArea = new ModelPlayArea(new PlayArea(null));
                if (mPlayArea.loadGame(myFile.getAbsolutePath())) {
                    GameState.changeState(GameStateType.LOADED_GAME_FROM_MAIN_MENU);
                }else{
                    mPlayArea = null;
                }
            }
        }
    }

    class CreateAccountListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // reacção associada ao botão "Criar Registo"
            GameState.changeState(GameStateType.CREATING_ACCOUNT);
        }
    }

    class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // reacção associada ao botão "Login"
            GameState.changeState(GameStateType.LOGIN);
        }
    }

    class ShowScoreListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // reacção associada ao botão "Mostrar Pontuação"
            GameState.changeState(GameStateType.SHOW_TOP10);
        }
    }

    class LogoutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // reacção associada ao botão "Sair"

            RegisteredPlayerData.getInstance().setIsLoggedIn(false);
            GameState.changeState(GameState.GameStateType.MAIN_MENU);

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
