/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.menus;

import arkanoid.GameState;
import arkanoid.GameState.GameStateType;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

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
        tmpOption.setEnabled(false);
        this.addOption(tmpOption);

        tmpOption = new JButton("Criar Registo");
        tmpOption.addActionListener(new FastRestarListener());
        this.addOption(tmpOption);
        
        tmpOption = new JButton("Login");
        tmpOption.addActionListener(new FastRestarListener());
        this.addOption(tmpOption);
        
        tmpOption = new JButton("Reinicio Rápido");
        tmpOption.addActionListener(new FastRestarListener());
        this.addOption(tmpOption);

        tmpOption = new JButton("Pontuação");
        tmpOption.addActionListener(new ShowScoreListener());
        this.addOption(tmpOption);
        
        tmpOption = new JButton("Sair");
        tmpOption.addActionListener(new ExitListener());
        this.addOption(tmpOption);


        this.presentOptions(250);
    }

    class FastRestarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // reacção associada ao botão "Reinicio Rápido"
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
        }
    }
    
    class CreateAccountListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // reacção associada ao botão "Criar Registo"
        }
    }

    class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // reacção associada ao botão "Login"

        }
    }

        class ShowScoreListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // reacção associada ao botão "Mostrar Pontuação"
           

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
