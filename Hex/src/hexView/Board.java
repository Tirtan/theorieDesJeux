package hexView;

import hexController.Arbre;
import hexController.Noeud;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Board extends JPanel implements MouseListener {

    private static final int SIDES = 6;
    private static final int SIDE_LENGTH = 50;
    private static final int LENGTH = 95;
    private static final int WIDTH = 105;

    private static final int ROWS = 9;
    private static final int COLUMNS = 9;

    //Attributs poly
    private int row;
    private int col;
    private int x = 50;
    private int y = 50;

    private Noeud[][] board;

    private HexWindow window;

    public Board(HexWindow hexWindow) {

        window = hexWindow;

        this.addMouseListener(this);
        HexagonButton(9, 9);
    }

    public void HexagonButton(int row, int col) {

        this.row = row;
        this.col = col;

        board = new Noeud[this.row][this.col];


        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                board[i][j] = createPoly(this.x, this.y);
                this.x += 100;
            }
            this.x = 50 * (i+2);
            this.y += 75;
        }

        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                if (i > 0) board[i][j].ajouterVoisin(board[i-1][j]);
                if (j > 0) board[i][j].ajouterVoisin(board[i][j-1]);
                if (i < this.row-1) board[i][j].ajouterVoisin(board[i+1][j]);
                if (j < this.col-1) board[i][j].ajouterVoisin(board[i][j+1]);
                if (i > 0 && j < this.col-1) board[i][j].ajouterVoisin(board[i-1][j+1]);
                if (i < this.row-1 && j > 0) board[i][j].ajouterVoisin(board[i+1][j-1]);
            }
        }
    }

    public Noeud createPoly(int x, int y) {

        Noeud hex = new Noeud(x, y);
        int[] x1 = {4, 2, 2, 4, 6, 6};
        int[] y2 = {2, 3, 5, 6, 5, 3};
        for (int i = 0; i < SIDES; i++) {

            hex.getPolygone().addPoint(x + x1[i] * SIDE_LENGTH / 2, //calculation for side
                    y + y2[i] * SIDE_LENGTH / 2);   //calculation for side
        }
        return hex;
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.fillRect(100, 100, 100 * board.length, 50);

        g.fillRect(50 + 50 * board[1].length, +100 + 75 * board[1].length, 100 * board.length, 25);

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                g.setColor(board[i][j].getCouleur());
                g.fillPolygon(board[i][j].getPolygone());
                g.setColor(Color.gray);
                g.drawPolygon(board[i][j].getPolygone());
            }
        }
    }

    public void chemin(int x1, int y1, int x2, int y2) {
        Noeud depart = this.board[y1][x1];
        Noeud arrivee = this.board[y2][x2];

        ArrayList<Noeud> hashListe = new ArrayList<>();
        inserer(hashListe, depart);
    }

    private void inserer(ArrayList<Noeud> hashListe, Noeud depart) {
        if (!hashListe.contains(depart)) {
            hashListe.add(depart);
            for (Noeud n : depart.getVoisins()) {
                inserer(hashListe, n);
            }
        }
    }

    public void calculHeuristique (Noeud noeud) {

        // Algo à activer deux fois (une fois pour chaque joueur)
        // prend en paramètre un point de départ et le tableau des cases
        // on part de ce point de départ, on check tous les voisins
        //         pour chaque voisin, s'ils sont gris, on set une heuristique = 9 - distance à parcourir
        //         pour chaque voisin de voisin, on check s'ils sont gris, on set une heuristique = 9 - distance avec noeud départ - distance à parcourir

        noeud.setHeuristique(0);

        ArrayList<Noeud> alV1 = new ArrayList<Noeud>();
        ArrayList<Noeud> alV2 = new ArrayList<Noeud>();

        ArrayList<Noeud> listeVoisinsCourants;

        ArrayList<ArrayList<Noeud>> listeliste = new ArrayList<>();
        listeliste.add(new ArrayList<Noeud>() {{
            add(noeud);
        }});

        int iteration = 0;

        Color[] colortab = {
                new Color(10, 10, 10),
                new Color(20, 20, 20),
                new Color(30, 30, 30),
                new Color(40, 40, 40),
                new Color(50, 50, 50),
                new Color(60, 60, 60),
                new Color(70, 70, 70),
                new Color(80, 80, 80),
                new Color(90, 90, 90),
                new Color(100, 100, 100),
                new Color(110, 110, 110),
                new Color(120, 120, 120),
                new Color(140, 140, 140),
                new Color(150, 150, 150),
                new Color(160, 160, 160),
                new Color(170, 170, 170),
                new Color(180, 180, 180),
                new Color(190, 190, 190),
                new Color(200, 200, 200),
                new Color(210, 210, 210),
                new Color(220, 220, 220),
                new Color(230, 230, 230),
                new Color(240, 240, 240),
                new Color(250, 250, 250)
        };

        boolean bord = false;

        while(!bord && listeliste.size() > 0) {
            System.out.println(listeliste.size());
            listeVoisinsCourants = listeliste.remove(0);
            for (Noeud voisin : listeVoisinsCourants) {
                if (voisin.getColonne() == 0/* ||
                        voisin.getLigne() == 0 ||
                        voisin.getColonne() == board.length-1 ||
                        voisin.getLigne() == board[voisin.getColonne()].length-1*/) {
                    bord = true;
                }
                if(voisin.isCliquable()) {
                    voisin.setHeuristique(9 - iteration);
                    voisin.setCouleur(colortab[iteration%colortab.length]);
                    listeliste.add(new ArrayList<Noeud>());
                    for(Noeud v : voisin.getVoisins()) {
                        if (v.isCliquable()) {
                            listeliste.get(0).add(v);
                        }
                    }
                }
            }
            iteration++;
        }

        /*
        for (Noeud v1 : noeud.getVoisins()) {
            if(v1.isCliquable()) {
                alV1.add(v1);
                v1.setHeuristique(10 + v1.getId());
                v1.setCouleur(Color.RED);
            }
        }

        for (Noeud v2 : alV1) {
            for (Noeud v4 : v2.getVoisins()) {
                if(v4.isCliquable()) {
                    alV2.add(v4);
                    v4.setHeuristique(9 + v4.getId());
                    v4.setCouleur(Color.GREEN);
                }
            }
        }

        for (Noeud v3 : alV2) {
            for (Noeud v4 : v3.getVoisins()) {
                if(v4.isCliquable()) {
                    v4.setHeuristique(8 + v4.getId());
                    v4.setCouleur(Color.BLUE);
                }
            }
        }
        */
        repaint();
    }

    public void alphaBeta(Noeud s, Arbre arbre, int alpha, int beta) {

    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        for(int i = 0 ; i < board.length; i++){
            for(int j = 0 ; j < board[1].length; j++){
                if (board[i][j].getPolygone().contains(new Point(e.getX(),e.getY())) && board[i][j].isCliquable()){
                    //System.out.println(board[i][j].isCliquable());
                    calculHeuristique(board[i][j]);
                    board[i][j].setCouleur(Color.BLACK);
                    /*for (Noeud n :board[i][j].getVoisins()){
                        n.setCouleur(Color.white);
                    }*/
                    System.out.println("j'ai clic ici " + e.getX() +" , "+ e.getY());
                }
            }
        }
        repaint();
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}