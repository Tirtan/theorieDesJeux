/**
 * Authors : Bruno ARIGANELLO, Titouan CORNILLEAU
 * Date : 2021-05-13
 */
package hexView;

import javax.swing.*;
import java.awt.*;

public class HexWindow extends JFrame {

    public static int frameWidth = 1500;
    public static int frameHeight = 1000;

    //panels
    public JPanel panelBoard;
    public JMenuBar menu;

    public HexWindow() {

        this.setTitle("Hex");
        this.setPreferredSize(new Dimension(frameWidth, frameHeight));
        this.setResizable(false);
        this.setBackground(Color.gray);
        this.pack();
        this.setLocationRelativeTo(null);

        // Création du menu
        menu = new MenuBar(this);
        this.setJMenuBar(menu);

        // Création du panel de jeu
        panelBoard = new Board(this);
        panelBoard.setDoubleBuffered(true);
        panelBoard.setPreferredSize(new Dimension(frameWidth - 100, frameHeight - 100));
        panelBoard.setBackground(Color.lightGray);

        // Ajout des panels à la frame
        this.add(panelBoard, BorderLayout.NORTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
