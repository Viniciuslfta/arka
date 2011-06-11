package arkanoid.menus;

import arkanoid.GameState;
import arkanoid.RegisteredPlayerData;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.lwjgl.opengl.Display;

/** Dialogo de Registo
 *
 * @author Filipe
 */
public final class DialogCreateAccount extends JFrame {

    private Container cp = getContentPane(); // referência para o contentor desta frame
    
    // referências para os objectos gráficos a colocar
    private JLabel usernameLabel = new JLabel("Username:");
    private JLabel passwordLabel = new JLabel("Password:");
    private JTextField usernameText = new JTextField(20);
    private JPasswordField passwordText = new JPasswordField(20);
    private JButton submitButton = new JButton("Submeter");
    private JButton cancelButton = new JButton("Cancelar");

    public DialogCreateAccount() {
        super("Register"); // define o titulo da frame

        layView();  // faz a montagem visual dos objectos gráficos deste exemplo
        registerListeners(); // liga os objectos gráficos aos listeners associados


        setVisible(true); // torna visivel
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//termina a aplicação quando a frame fechar
        
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
        usernameText.requestFocus();

    }

    /* Liga os objectos gráficos aos listeners associados permitindo que
     * interajam com o utilizador 
     */
    protected void registerListeners() {
        submitButton.addActionListener(new SubmitListener());
        cancelButton.addActionListener(new CancelListener());
    }

    class CancelListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            GameState.changeState(GameState.GameStateType.MAIN_MENU);
        }
    }

    class SubmitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameText.getText().trim(); //trim remove espaços
            String password = passwordText.getText().trim();
            List<RegisteredPlayerData> readPlayers = null;
            ObjectInputStream in = null;

            setAlwaysOnTop(false);
            if (username.length() < 3 || password.length() < 3) {
                JOptionPane.showMessageDialog(null, "Username e Password com mais de 3 digitos!");
            }

            File file = new File("players.ark");
            int found = 0;

            if (file.exists()) {

                try {

                    in = new ObjectInputStream(new FileInputStream(file));


                    readPlayers = (List<RegisteredPlayerData>) in.readObject();
                    for (int i = 0; i < readPlayers.size(); i++) {
                        if (readPlayers.get(i).getUsername().equalsIgnoreCase(username)) {
                            found++;
                            break;
                        }
                    }

                    in.close();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                    ex.printStackTrace();
                } finally {
                    //fecha stream
                    try {
                        if (in != null) {
                            in.close();
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex);
                        ex.printStackTrace();
                    }
                }


                if (found > 0) {
                    JOptionPane.showMessageDialog(null, "Já existe um jogador registado com o username pretendido.");
                    return;
                }


            }

            List<RegisteredPlayerData> newPlayers = new ArrayList<RegisteredPlayerData>();
            if (readPlayers != null) {
                newPlayers.addAll(readPlayers);
            }

            newPlayers.add(new RegisteredPlayerData(username, password));


            RegisteredPlayerData.getInstance().writePlayersFile(newPlayers);

            dispose();
            JOptionPane.showMessageDialog(null, "Registo efectuado com sucesso.");
            GameState.changeState(GameState.GameStateType.MAIN_MENU);

        }
    }
}
