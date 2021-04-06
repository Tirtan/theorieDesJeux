package hexView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import hexController.Controller;

public class HexWindow {

	//Frame
	public JFrame     frame;
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
		frame.setPreferredSize(new Dimension(frameWidth,frameHeight));
		frame.setResizable(false);
		frame.setBackground(Color.gray);
		
		frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        //création du panel de jeu
        panelJeu = new board(this);
        panelJeu.setDoubleBuffered(true);
		panelJeu.setPreferredSize(new Dimension(frameWidth-100, frameHeight - 100));
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
