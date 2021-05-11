package hexView;

import hexController.Controller;

import javax.swing.*;
import java.awt.*;

public class HexWindow {

    //Frame
    public JFrame frame;
    public static int frameWidth = 1500;
    public static int frameHeight = 1000;


    //panels
    public JPanel panelJeu;
    public JPanel panelControl;
    public JPanel panelInfo;
    public JLabel labelJoueur;
    public JLabel labelTour;

    //controller
    public Controller controller;

    public HexWindow() {

        controller = new Controller();

        createView();
        createController();
    }

    private void createView() {

        //gestion et création de la frame
        frame = new JFrame("Hex");
        frame.setPreferredSize(new Dimension(frameWidth, frameHeight));
        frame.setResizable(false);
        frame.setBackground(Color.gray);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //création du panel de jeu
        panelJeu = new Board(this);
        panelJeu.setDoubleBuffered(true);
        panelJeu.setPreferredSize(new Dimension(frameWidth - 100, frameHeight - 100));
        panelJeu.setBackground(Color.lightGray);


        //Ajout des panels à la frame
        frame.add(panelJeu, BorderLayout.NORTH);
    }

    private void createController() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void refresh() {
        frame.repaint();
    }

}
