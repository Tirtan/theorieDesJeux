package hexView;

import hexController.Arbre;
import hexController.Noeud;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Board extends JPanel {

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

        HexagonButton(9, 9);
    }

    public void HexagonButton(int row, int col) {

        this.row = row;
        this.col = col;

        board = new Noeud[this.row][this.col];


        for (int i = 0; i < this.row; i = i + 2) {
            for (int j = 0; j < this.col; j++) {
                board[i][j] = createPoly(this.x, this.y);

                if (i > 0) board[i][j].ajouterVoisin(board[i-1][j]);
                if (j > 0) board[i][j].ajouterVoisin(board[i][j-1]);
                if (i < this.row-1) board[i][j].ajouterVoisin(board[i+1][j]);
                if (j < this.col-1) board[i][j].ajouterVoisin(board[i][j+1]);
                if (i > 0 && j < this.col-1) board[i][j].ajouterVoisin(board[i-1][j+1]);
                if (i < this.row-1 && j > 0) board[i][j].ajouterVoisin(board[i+1][j-1]);

                this.x += 100;
            }
            this.x = 50 * (i + 2);
            this.y += 75;
            if (i + 1 < this.row) {
                for (int j = 0; j < this.col; j++) {
                    board[i + 1][j] = createPoly(this.x, this.y);

                    this.x += 100;

                }
                this.x = 50 * (i + 3);
                this.y += 75;
            }
        }
    }

    public Noeud createPoly(int x, int y) {

        Noeud hex = new Noeud();
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

        /*for (Noeud[] noeuds : Board) {
            for (Noeud noeud : noeuds) {
                g.setColor(new Color(245, 222, 179));
                g.fillPolygon(noeud.getPolygone());
                g.setColor(Color.GRAY);
                g.drawPolygon(noeud.getPolygone());
            }
        }*/

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                g.setColor(new Color(245, 222, 179));
                g.fillPolygon(board[i][j].getPolygone());
                g.setColor(Color.GRAY);
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

        Iterator<Noeud> it = noeud.getVoisins().iterator();
        Iterator<Noeud> itVoisin = null;
        //Iterator<Noeud> itVoisin2 = null;
        Noeud voisin1, voisin2, voisin3 = null;

        while(it.hasNext()) {

            voisin1 = it.next();
            if(!voisin1.getVoisins().isEmpty()) {
                itVoisin = voisin1.getVoisins().iterator();
                while(itVoisin.hasNext()) {
                    voisin2 = itVoisin.next();
                    if(!voisin2.getVoisins().isEmpty()) {
                        itVoisin = voisin2.getVoisins().iterator();
                        while(itVoisin.hasNext()) {
                            voisin3 = itVoisin.next();
                            if(voisin3.isCliquable()) voisin3.setHeuristique(8 + voisin3.getId());
                        }
                        if(voisin2.isCliquable()) voisin2.setHeuristique(9 + voisin2.getId());
                    }
                }
                if(voisin1.isCliquable()) voisin1.setHeuristique(10 + voisin1.getId());
            }
        }
    }

    public void alphaBeta(Noeud s, Arbre arbre, int alpha, int beta) {

    }
}