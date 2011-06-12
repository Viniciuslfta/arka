/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.menus;

import arkanoid.GameState;
import arkanoid.GameState.GameStateType;
import arkanoid.RegisteredPlayerData;
import arkanoid.Top10;
import arkanoid.replay.Replay;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import org.lwjgl.opengl.Display;

/**
 *
 * @author sPeC
 */
public class DialogTop10 extends JFrame {

    JButton mReplay = new JButton("Replay");
    JTable mTable;

    public DialogTop10() {
        super("Top 10");

        int height = 322;
        int width = 320;
        

        this.setSize(width, height);
        this.setLocation(Display.getDesktopDisplayMode().getWidth() / 2 - width / 2, Display.getDesktopDisplayMode().getHeight() / 2 - height / 2);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                GameState.changeState(GameStateType.MAIN_MENU);
            }
        });

        Object columnNames[] = {"NOME", "PONTOS"};
        mTable = new JTable(Top10.getInstance().getElementsAsTable(), columnNames) {

            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };

        mTable.getTableHeader().setPreferredSize(new Dimension(100, 40));
        mTable.setRowHeight(25);
        mTable.getSelectionModel().addListSelectionListener(new EventSelectionChanged());
        mTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Alinha o texto ao centro
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);
        mTable.getColumn("NOME").setCellRenderer(dtcr);
        mTable.getColumn("PONTOS").setCellRenderer(dtcr);

        JScrollPane scrollPane = new JScrollPane(mTable);
        add(scrollPane, BorderLayout.CENTER);
        if(RegisteredPlayerData.getInstance().isLoggedIn())
            add(mReplay, BorderLayout.LINE_END);

        mReplay.addActionListener(new EventReplay());
        if (Top10.getInstance().getNumberElements() == 0) {
            mReplay.setEnabled(false);
        } else {
            Top10.Top10Element tmpElement = Top10.getInstance().getElement(0);
            Replay.getInstance().Load(tmpElement.getReplayName());
            mTable.getSelectionModel().setSelectionInterval(0, 0);
        }

        setResizable(false);
        setAlwaysOnTop(true);
        setVisible(true);
    }

    class EventSelectionChanged implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();

            int idx = lsm.getMinSelectionIndex();
            if (idx > Top10.getInstance().getNumberElements() - 1) {
                mReplay.setEnabled(false);
            } else {
                Top10.Top10Element tmpElement = Top10.getInstance().getElement(idx);
                Replay.getInstance().Load(tmpElement.getReplayName());
                mReplay.setEnabled(true);
            }
        }
    }

    class EventReplay implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            GameState.changeState(GameStateType.REPLAYING);
        }
    }
}
