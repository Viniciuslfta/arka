/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.menus;

import arkanoid.Settings;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.lwjgl.opengl.Display;

/**
 *
 * @author sPeC
 */
public abstract class Menu extends JFrame {

    List<JButton> mOptions = new ArrayList<JButton>();
    private static Font mFont = new Font("Verdana", Font.PLAIN, 12);

    public static Font getOptionsFont() {
        return mFont;
    }

    public List<JButton> getOptions() {
        return mOptions;
    }

    protected void addOption(JButton _newOption) {
        _newOption.setFont(mFont);
        _newOption.setPreferredSize(new Dimension(Settings.MENU_BTN_WIDTH, Settings.MENU_BTN_HEIGHT));
        mOptions.add(_newOption);
    }

    public Menu(String string) throws HeadlessException {
        super(string);
    }

    protected void presentOptions(int _width) {
        JPanel jp = new JPanel();

        for (JButton jb : mOptions) {
            jp.add(jb);
        }

        Container cp = getContentPane();
        cp.add(jp, BorderLayout.CENTER);

        // Loclização do Frame e tamanho do frame
        this.setLocation(Display.getDesktopDisplayMode().getWidth()/2, Display.getDesktopDisplayMode().getHeight()/2);
        //setLocation(Display.getDisplayMode().getWidth(), 100);
        setSize(_width, (mOptions.size() + 1) * Settings.MENU_BTN_HEIGHT + 10);
        setVisible(true);

        // Termina a aplicação quando a frame fechar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        validate();
    }
}