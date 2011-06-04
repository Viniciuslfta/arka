/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid.menus;

import arkanoid.GameState;
import arkanoid.GameState.GameStateType;
import arkanoid.Top10;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import org.lwjgl.opengl.Display;

/**
 *
 * @author sPeC
 */
public class DialogTop10 extends JFrame {

    public DialogTop10() {
        super("Top 10");

        int height = 335;
        int width = 250;
        this.setSize(width, height);
        this.setLocation(Display.getDesktopDisplayMode().getWidth() / 2 - width / 2, Display.getDesktopDisplayMode().getHeight() / 2 - height / 2);
        this.setAlwaysOnTop(true);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                GameState.changeState(GameStateType.MAIN_MENU);
            }
        });
        
        Object columnNames[] = {"NOME", "PONTOS"};
        JTable table = new JTable(Top10.getInstance().getElementsAsTable(), columnNames);
        table.getTableHeader().setPreferredSize(new Dimension(100, 40));
        table.setRowHeight(25);

        // Alinha o texto ao centro
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumn("NOME").setCellRenderer(dtcr);
        table.getColumn("PONTOS").setCellRenderer(dtcr);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        setSize(width, height);

        setResizable(false);
        setVisible(true);

    }
}
