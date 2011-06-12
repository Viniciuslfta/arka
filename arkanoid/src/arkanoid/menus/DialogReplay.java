/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.menus;

import arkanoid.GameState;
import arkanoid.GameState.GameStateType;
import arkanoid.Settings;
import arkanoid.models.ModelPlayArea;
import arkanoid.replay.Replay;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.lwjgl.opengl.Display;

/**
 *
 * @author sPeC
 */
public class DialogReplay extends JFrame {

    Container cp = getContentPane();
    JButton btnBack = new JButton("<<|");
    JButton btnPlay = new JButton("|>");
    JButton btnPause = new JButton("||");
    JButton btnStop = new JButton("#");
    JButton btnNextLevel = new JButton("|>>");
    JButton btn1X = new JButton("1x");
    JButton btn2X = new JButton("2x");
    JButton btn4X = new JButton("4x");
    JButton btn8X = new JButton("8x");
    JButton btn10X = new JButton("10x");
    ModelPlayArea mArea;

    public DialogReplay(ModelPlayArea _area) {

        super("Controlo de Replay");

        mArea = _area;

        btnBack.addActionListener(new EventBack());
        btnBack.setToolTipText("Volta ao início do Replay");
        btnNextLevel.addActionListener(new EventNextLevel());
        btnNextLevel.setToolTipText("Próximo nível do Replay");
        btnPause.addActionListener(new EventPause());
        btnPause.setToolTipText("Pausa o Replay");
        btnPlay.addActionListener(new EventPlay());
        btnPlay.setToolTipText("Inicia o Replay");
        btnStop.addActionListener(new EventStop());
        btnStop.setToolTipText("Pára o Replay");
        btn1X.addActionListener(new Event1X());
        btn1X.setToolTipText("Velocidade Real");
        btn2X.addActionListener(new Event2X());
        btn2X.setToolTipText("Aumenta a velocidade em 2 vezes");
        btn4X.addActionListener(new Event4X());
        btn4X.setToolTipText("Aumenta a velocidade em 4 vezes");
        btn8X.addActionListener(new Event8X());
        btn8X.setToolTipText("Aumenta a velocidade em 8 vezes");
        btn10X.addActionListener(new Event10X());
        btn10X.setToolTipText("Aumenta a velocidade em 10 vezes");

        cp.setLayout(new FlowLayout());

        cp.add(btnBack);
        cp.add(btnPlay);
        cp.add(btnPause);
        cp.add(btnStop);
        cp.add(btnNextLevel);
        cp.add(btn1X);
        cp.add(btn2X);
        cp.add(btn4X);
        cp.add(btn8X);
        cp.add(btn10X);

        int height = 70;
        int width = 550;
        this.setSize(width, height);
        this.setLocation(Display.getDesktopDisplayMode().getWidth() / 2 - width / 2,
                Display.getDesktopDisplayMode().getHeight() / 2 + Settings.DISPLAY_HEIGHT / 2 - height);

        setResizable(false);
        setAlwaysOnTop(true);
        setVisible(true);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GameState.changeState(GameStateType.MAIN_MENU);
            }
        });
    }

    class EventBack implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            Replay.getInstance().resetReplay(mArea);
        }
    }

    class EventNextLevel implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            Replay.getInstance().nextLevel(mArea);
        }
    }

    class EventPlay implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            Replay.getInstance().play();
        }
    }

    class EventPause implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            Replay.getInstance().stop();
        }
    }

    class EventStop implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            Replay.getInstance().stop();
            dispose();
            GameState.changeState(GameStateType.MAIN_MENU);
        }
    }

    class Event1X implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            Replay.getInstance().setSpeed(1);
        }
    }

    class Event2X implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            Replay.getInstance().setSpeed(2);
        }
    }

    class Event4X implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            Replay.getInstance().setSpeed(4);
        }
    }

    class Event8X implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            Replay.getInstance().setSpeed(8);
        }
    }

    class Event10X implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            Replay.getInstance().setSpeed(10);
        }
    }
}
