package hexView;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import hexMain.Main;

public class board extends JPanel{
	
	private static final long serialVersionUID = 1L;
    private static final int SIDES = 6;
    private static final int SIDE_LENGTH = 50;
    public static final int LENGTH = 95;
    public static final int WIDTH = 105;
    
    private static final int ROWS = 9;
    private static final int COLUMNS = 9;
    
    //Attributs poly
    private int row;
    private int col;
    private int x = 50;
    private int y = 50;
    
    private Polygon[][] board;
	
	private HexWindow window;
	
	public board(HexWindow hexWindow) {
		
		window = hexWindow;
		
		HexagonButton(9,9); 
		
	}
	
	public void HexagonButton(int row, int col) {

        this.row = row;
        this.col = col;
        
        board = new Polygon[this.row][this.col];
        
        
        for (int i = 0 ; i < this.row; i= i+2) {
        	
        	for(int j = 0; j < this.col; j++) {
        		board[i][j] = createPoly(this.x, this.y);
        		
        		this.x += 100;
        	}
        	this.x = 50*(i+2);
        	this.y += 75;
        	if (i+1 < this.row) {
        		for(int j = 0; j < this.col; j++) {
            		board[i+1][j] = createPoly(this.x, this.y);
            		
            		this.x += 100;
            		
            	}
            	this.x = 50*(i+3);
            	this.y += 75;
        	}
        	
        	
        }
        
    }
	
	public Polygon createPoly(int x, int y) {
		
		Polygon hex = new Polygon();
		int x1[] = {4, 2, 2, 4, 6, 6};
        int y2[] = {2, 3, 5, 6, 5, 3};
        for (int i = 0; i < SIDES; i++) {
            
        	hex.addPoint((int) (x + x1[i]*SIDE_LENGTH/2), //calculation for side
                    (int) (y +  y2[i]*SIDE_LENGTH/2));   //calculation for side
        }   
        
        return hex; 
	}
	
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);

		g.fillRect(100,100,100*board.length,50);
		
		g.fillRect(50+50*board[1].length,+100+75*board[1].length,100*board.length,25);
	    
		for(int i = 0; i < board.length; i++) {
        	for(int j = 0; j < board[i].length; j++) {
        		
        		g.setColor(new Color(245,222,179));
        		g.fillPolygon(board[i][j]);
        		g.setColor(Color.GRAY);
        		g.drawPolygon(board[i][j]);
        		
        		
        		
        	}
		}
		
		
		   

	  }
}
