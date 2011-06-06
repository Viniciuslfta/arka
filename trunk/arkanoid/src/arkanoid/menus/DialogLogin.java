package arkanoid.menus;

import arkanoid.GameState;
import arkanoid.RegisteredPlayerData;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.lwjgl.opengl.Display;

/** Dialogo de Login
 *
 * @author Filipe
 */
public class DialogLogin extends JFrame {

    private Container cp = getContentPane(); // referência para o contentor desta frame
    // referências para os objectos gráficos a colocar
    private JLabel usernameLabel = new JLabel("Username:");
    private JLabel passwordLabel = new JLabel("Password:");
    private JTextField usernameText = new JTextField(20);
    private JPasswordField passwordText = new JPasswordField(20);
    private JButton submitButton = new JButton("Login");
    private JButton cancelButton = new JButton("Cancelar");

    public DialogLogin() {
        super("Login"); // define o titulo da frame


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

        int height = 175;
        int width = 300;
        this.setSize(width, height);
        this.setLocation(Display.getDesktopDisplayMode().getWidth() / 2 - width / 2, Display.getDesktopDisplayMode().getHeight() / 2 - height / 2);
        this.setAlwaysOnTop(true);

        // dispoe os objectos gráficos
        cp.setLayout(new FlowLayout());
        cp.add(usernameLabel);
        cp.add(usernameText);
        cp.add(passwordLabel);
        cp.add(passwordText);
        cp.add(submitButton);
        cp.add(cancelButton);

    }

    /* Liga os objectos gráficos aos listeners associados permitindo que
     * interajam com o utilizador 
     */
    protected void registerListeners() {
        submitButton.addActionListener(new SubmitListener());
        cancelButton.addActionListener(new CancelListener());
    }

    class CancelListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            GameState.changeState(GameState.GameStateType.MAIN_MENU);
        }
    }

    class SubmitListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String username = usernameText.getText().trim(); //trim remove espaços
            String password = passwordText.getText().trim();
            
            setAlwaysOnTop(false);
            
            List<RegisteredPlayerData> readPlayers = RegisteredPlayerData.getInstance().readPlayersFile();

            int found = 0;
            int i = 0;

            for (i = 0; i < readPlayers.size(); i++) {
                if (readPlayers.get(i).getUsername().equalsIgnoreCase(username) && readPlayers.get(i).getPassword().equals(password)) {
                    found++;
                    RegisteredPlayerData.getInstance().setInstance(readPlayers.get(i));
                    break;
                }
            }


            if (found == 0) {
                JOptionPane.showMessageDialog(null, "Não foi encontrado o jogador pretendido.");
                return;
            }

            dispose();
            JOptionPane.showMessageDialog(null, "Login efectuado com sucesso.");
            GameState.changeState(GameState.GameStateType.MAIN_MENU);
        }
    }
}



