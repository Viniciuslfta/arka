/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.menus;

import arkanoid.GameState;
import arkanoid.RegisteredPlayerData;
import arkanoid.models.entities.GameLevel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import org.lwjgl.opengl.Display;

/**
 *
 * @author Filipe
 */
public class DialogFastRestart extends JFrame{
       private Container cp = getContentPane(); // referência para o contentor desta frame
    // referências para os objectos gráficos a colocar
    private JLabel infoLabel = new JLabel("Escolha o nível pretendido:");
    private JButton startButton = new JButton("Começar");
    private JButton cancelButton = new JButton("Cancelar");
    private JList levelsList;
    JScrollPane scrollPane;
    
    public DialogFastRestart() {
        super("Reiniciar Rápido"); // define o titulo da frame
        
        DefaultListModel listData = new DefaultListModel();
    
        for(int i=1;i<=RegisteredPlayerData.getInstance().getCompletedLevels();i++) {  
            String s = i + " - " + readLevelName(i);
            listData.addElement(s);
        }      
        
        levelsList = new JList(listData);
        levelsList.setLayoutOrientation(JList.VERTICAL);
        scrollPane = new JScrollPane(levelsList);
        
        layView();  // faz a montagem visual dos objectos gráficos deste exemplo
        registerListeners(); // liga os objectos gráficos aos listeners associados


        setVisible(true); // torna visivel
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//termina a aplicação
        // quando a frame fechar
        validate();// dispoe de novo os seus subcomponentes
            
        

    }

    /*Faz a montagem visual dos objectos gráficos desta caixa de diálogo
     */
    protected void layView() {
        
        int height = 250;
        int width = 250;
        this.setSize(width, height);
        this.setLocation(Display.getDesktopDisplayMode().getWidth()/2 - width / 2, Display.getDesktopDisplayMode().getHeight()/2 - height/2);
        this.setAlwaysOnTop(true);     
             
        // dispoe os objectos gráficos
        cp.setLayout(new FlowLayout());
        cp.add(infoLabel);
        cp.add(scrollPane, BorderLayout.CENTER);
        cp.add(startButton);
        cp.add(cancelButton);
        

        levelsList.setPreferredSize(new Dimension(150, 150));
    }

    /* Liga os objectos gráficos aos listeners associados permitindo que
     * interajam com o utilizador 
     */
    protected void registerListeners() {
       startButton.addActionListener(new StartListener());
       cancelButton.addActionListener(new CancelListener());
    }

    class CancelListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            GameState.changeState(GameState.GameStateType.MAIN_MENU);
        }
    }
    
    class StartListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            GameState.changeState(GameState.GameStateType.PLAYING);

            String level = "level" + (levelsList.getSelectedIndex()+1) + ".txt";
            RegisteredPlayerData.getInstance().setStartingLevel(level);
        }
    }

    private String readLevelName(int _lvl) {
        String filePath = "level" + _lvl +".txt";
        GameLevel newGameLevel = new GameLevel(filePath);        
        return newGameLevel.getName();
    }
}
