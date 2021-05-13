package hexView;

import hexController.Chemin;
import hexController.Noeud;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Board extends JPanel implements MouseListener {

    private static final int SIDES = 6;
    private static final int SIDE_LENGTH = 50;
    private static final int LENGTH = 95;
    private static final int WIDTH = 105;

    private static final int ROWS = 9;
    private static final int COLUMNS = 9;

    private static final Color botColor = Color.BLACK;

    //Attributs poly
    private int row;
    private int col;
    private int x = 50;
    private int y = 50;

    private Noeud[][] board;

    private HexWindow window;

    private ArrayList<Noeud> coupsJoueParLeBot;

    private boolean partieEnCours;

    public Board(HexWindow hexWindow) {

        window = hexWindow;
        coupsJoueParLeBot = new ArrayList<>();
        partieEnCours = true;

        this.addMouseListener(this);
        HexagonButton(9, 9);
    }

    public void HexagonButton(int row, int col) {

        this.row = row;
        this.col = col;

        board = new Noeud[this.row][this.col];


        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                board[i][j] = createPoly(this.x, this.y, i, j);
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

    public Noeud createPoly(int x, int y, int ligne, int colonne) {

        Noeud hex = new Noeud(ligne, colonne);
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

        ArrayList<Noeud> visite = new ArrayList<Noeud>();

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

        while( listeliste.size() > 0) {
            listeVoisinsCourants = listeliste.remove(0);
            for (Noeud voisin : listeVoisinsCourants) {
                if(voisin.isCliquable()) {
                    voisin.setHeuristique(iteration);
                    voisin.setDelta(1);
                    //voisin.setCouleur(colortab[voisin.getHeuristique()%colortab.length]);
                    visite.add(voisin);
                    listeliste.add(new ArrayList<Noeud>());
                    for(Noeud v : voisin.getVoisins()) {
                        if (!visite.contains(v)) {
                            listeliste.get(0).add(v);
                        }
                    }
                }
            }
            iteration++;
        }
        //repaint();
    }

    public ArrayList<Noeud> aEtoile(Noeud s) {
        ArrayList<Noeud> omega = new ArrayList<>();
        Noeud pi[][] = new Noeud[board.length][board[0].length];
        for (int i = 0; i < pi.length; i++){
            for (int j = 0; j < pi[0].length; j++){
                pi[i][j] = null;
                board[i][j].setDelta(Integer.MAX_VALUE);
                board[i][j].setF(Integer.MAX_VALUE);
            }
        }
        s.setF(s.getHeuristique());
        s.setDelta(0);
        Noeud x = s;
        omega.add(x);
        while (x.getLigne() != board.length-1 && !omega.isEmpty()) {
            x = omega.get(0);
            for(Noeud n : omega) {
                if (n.getHeuristique() < x.getHeuristique()){
                    x = n;
                }
            }

            // Fonction Examiner() :
            for (Noeud y : x.getVoisinsCliquable()) {
                if ((x.getDelta() + 1) < y.getDelta()) {
                    y.setDelta(x.getDelta() + 1);
                    pi[y.getLigne()][y.getColonne()] = x;
                    y.setF(y.getDelta() + y.getHeuristique());

                    // Fonction Ouvrir()
                    omega.add(y);
                }
            }
            // Fonction Fermer()
            omega.remove(x);
        }

        ArrayList<Noeud> retour = new ArrayList<>();

        if (x.getLigne() == board.length-1) {
            while (pi[x.getLigne()][x.getColonne()] != s) {
                retour.add(0, x);
                x = pi[x.getLigne()][x.getColonne()];
            }
            retour.add(0, x);
        }

        return retour;
    }

    public void botTour() {
        ArrayList<Noeud> coupsPossibles = new ArrayList<>();
        Noeud prochainCoup = null;
        if (this.coupsJoueParLeBot.isEmpty()) {
            for(int i = 0; i < board[0].length; i++) {
                if (board[0][i].isCliquable()) {
                    coupsPossibles.add(board[0][i]);
                }
            }
            prochainCoup = coupsPossibles.get((int) (Math.random() * coupsPossibles.size()));
        } else {
            ArrayList<Noeud> cheminTemp;
            ArrayList<Noeud> plusCourtChemin = null;
            for (Noeud n : this.coupsJoueParLeBot) {
                cheminTemp = aEtoile(n);
                if ( plusCourtChemin == null || cheminTemp.size() < plusCourtChemin.size() && cheminTemp.size() > 0) {
                    plusCourtChemin = cheminTemp;
                }
            }
            if (plusCourtChemin != null && plusCourtChemin.size() > 0) {
                prochainCoup = plusCourtChemin.get(0);
            }
        }
        if (prochainCoup != null) {
            prochainCoup.setCouleur(Board.botColor);
            this.coupsJoueParLeBot.add(prochainCoup);
        }
    }

    public boolean checkVictoire(Color couleur) {
        if (couleur == Color.BLACK) {
            for(int cpt = 0; cpt < board[0].length; cpt++) {
                if (board[0][cpt].getCouleur() == couleur) {

                    Noeud s = board[0][cpt];

                    ArrayList<Noeud> omega = new ArrayList<>();
                    Noeud pi[][] = new Noeud[board.length][board[0].length];
                    for (int i = 0; i < pi.length; i++){
                        for (int j = 0; j < pi[0].length; j++){
                            pi[i][j] = null;
                            board[i][j].setDelta(Integer.MAX_VALUE);
                            board[i][j].setF(Integer.MAX_VALUE);
                        }
                    }
                    s.setF(s.getHeuristique());
                    s.setDelta(0);
                    Noeud x = s;
                    omega.add(x);
                    while (x.getLigne() != board.length-1 && !omega.isEmpty()) {
                        x = omega.get(0);
                        for(Noeud n : omega) {
                            if (n.getHeuristique() < x.getHeuristique()){
                                x = n;
                            }
                        }

                        // Fonction Examiner() :
                        for (Noeud y : x.getVoisinsCouleur(couleur)) {
                            if ((x.getDelta() + 1) < y.getDelta()) {
                                y.setDelta(x.getDelta() + 1);
                                pi[y.getLigne()][y.getColonne()] = x;
                                y.setF(y.getDelta() + y.getHeuristique());

                                // Fonction Ouvrir()
                                omega.add(y);
                            }
                        }
                        // Fonction Fermer()
                        omega.remove(x);
                    }

                    if (x.getLigne() == board.length-1) {
                        return true;
                    }
                }
            }
        } else if (couleur == Color.WHITE) {
            for(int cpt = 0; cpt < board.length; cpt++) {
                if (board[cpt][0].getCouleur() == couleur) {

                    Noeud s = board[cpt][0];

                    ArrayList<Noeud> omega = new ArrayList<>();
                    Noeud pi[][] = new Noeud[board.length][board[0].length];
                    for (int i = 0; i < pi.length; i++){
                        for (int j = 0; j < pi[0].length; j++){
                            pi[i][j] = null;
                            board[i][j].setDelta(Integer.MAX_VALUE);
                            board[i][j].setF(Integer.MAX_VALUE);
                        }
                    }
                    s.setF(s.getHeuristique());
                    s.setDelta(0);
                    Noeud x = s;
                    omega.add(x);
                    while (x.getColonne() != board[0].length-1 && !omega.isEmpty()) {
                        x = omega.get(0);
                        for(Noeud n : omega) {
                            if (n.getHeuristique() < x.getHeuristique()){
                                x = n;
                            }
                        }

                        // Fonction Examiner() :
                        for (Noeud y : x.getVoisinsCouleur(couleur)) {
                            if ((x.getDelta() + 1) < y.getDelta()) {
                                y.setDelta(x.getDelta() + 1);
                                pi[y.getLigne()][y.getColonne()] = x;
                                y.setF(y.getDelta() + y.getHeuristique());

                                // Fonction Ouvrir()
                                omega.add(y);
                            }
                        }
                        // Fonction Fermer()
                        omega.remove(x);
                    }

                    if (x.getColonne() == board[0].length-1) {
                        return true;
                    }
                }
            }
        }
        return false;
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
        if (partieEnCours) {
            for(int i = 0 ; i < board.length; i++){
                for(int j = 0 ; j < board[1].length; j++){
                    if (board[i][j].getPolygone().contains(new Point(e.getX(),e.getY())) && board[i][j].isCliquable()) {
                        board[i][j].setCouleur(Color.WHITE);
                        if (checkVictoire(Color.WHITE)) {
                            partieEnCours = false;
                        } else {
                            botTour();
                            if (checkVictoire(Color.BLACK)) {
                                partieEnCours = false;
                            }
                        }
                    }
                }
            }
            repaint();
        }
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