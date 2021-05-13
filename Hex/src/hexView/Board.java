/**
 * Authors : Bruno ARIGANELLO, Titouan CORNILLEAU
 * Date : 2021-05-13
 */
/**
 * Authors : Bruno ARIGANELLO, Titouan CORNILLEAU
 * Date : 2021-05-13
 */
package hexView;

import hexController.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Board extends JPanel implements MouseListener {

    private static final int SIDES = 6;
    private static final int SIDE_LENGTH = 50;

    private static final Color botColor = Color.BLACK;

    //Attributs poly
    private int row;
    private int col;
    private int x = 50;
    private int y = 50;

    private Node[][] board;

    private HexWindow parent;

    private ArrayList<Node> numberOfMovesFromBot;

    private boolean gameInProgress;

    public Board(HexWindow hexWindow) {

        parent = hexWindow;
        numberOfMovesFromBot = new ArrayList<>();
        gameInProgress = true;

        this.addMouseListener(this);
        HexagonButton(9, 9);
    }

    public void HexagonButton(int row, int col) {

        this.row = row;
        this.col = col;

        board = new Node[this.row][this.col];


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
                if (i > 0) board[i][j].addNeighbor(board[i-1][j]);
                if (j > 0) board[i][j].addNeighbor(board[i][j-1]);
                if (i < this.row-1) board[i][j].addNeighbor(board[i+1][j]);
                if (j < this.col-1) board[i][j].addNeighbor(board[i][j+1]);
                if (i > 0 && j < this.col-1) board[i][j].addNeighbor(board[i-1][j+1]);
                if (i < this.row-1 && j > 0) board[i][j].addNeighbor(board[i+1][j-1]);
            }
        }
    }

    public Node createPoly(int x, int y, int ligne, int colonne) {

        Node hex = new Node(ligne, colonne);
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
                g.setColor(board[i][j].getColor());
                g.fillPolygon(board[i][j].getPolygone());
                g.setColor(Color.gray);
                g.drawPolygon(board[i][j].getPolygone());
            }
        }
    }

    public void heuristic(Node node) {

        node.setHeuristic(0);

        ArrayList<Node> visitedNoed = new ArrayList<Node>();
        ArrayList<Node> currentNeighborsList;
        ArrayList<ArrayList<Node>> NeighborsListsList = new ArrayList<>();

        NeighborsListsList.add(new ArrayList<Node>() {{
            add(node);
        }});

        int iteration = 0;

        while( NeighborsListsList.size() > 0) {
            currentNeighborsList = NeighborsListsList.remove(0);
            for (Node neighbor : currentNeighborsList) {
                if(neighbor.isClickable()) {
                    neighbor.setHeuristic(iteration);
                    neighbor.setDelta(1);
                    visitedNoed.add(neighbor);
                    NeighborsListsList.add(new ArrayList<Node>());
                    for(Node n : neighbor.getNeighbors()) {
                        if (!visitedNoed.contains(n)) {
                            NeighborsListsList.get(0).add(n);
                        }
                    }
                }
            }
            iteration++;
        }
    }

    public ArrayList<Node> aStar(Node s) {
        ArrayList<Node> openedNodes = new ArrayList<>();
        Node pi[][] = new Node[board.length][board[0].length];
        for (int i = 0; i < pi.length; i++){
            for (int j = 0; j < pi[0].length; j++){
                pi[i][j] = null;
                board[i][j].setDelta(Integer.MAX_VALUE);
                board[i][j].setF(Integer.MAX_VALUE);
            }
        }
        s.setF(s.getHeuristic());
        s.setDelta(0);
        Node x = s;
        openedNodes.add(x);
        while (x.getLine() != board.length-1 && !openedNodes.isEmpty()) {

            x = openedNodes.get(0);
            for(Node n : openedNodes) {
                if (n.getF() < x.getF()){
                    x = n;
                }
            }

            // Fonction Examiner() :
            for (Node y : x.getClickableNeighbors()) {
                if ((x.getDelta() + 1) < y.getDelta()) {
                    y.setDelta(x.getDelta() + 1);
                    pi[y.getLine()][y.getColumn()] = x;
                    y.setF(y.getDelta() + y.getHeuristic());

                    // Fonction Ouvrir()
                    openedNodes.add(y);
                }
            }
            // Fonction Fermer()
            openedNodes.remove(x);
        }

        ArrayList<Node> pathResult = new ArrayList<>();

        if (x.getLine() == board.length-1) {
            while (pi[x.getLine()][x.getColumn()] != s) {
                pathResult.add(0, x);
                x = pi[x.getLine()][x.getColumn()];
            }
            pathResult.add(0, x);
        }

        return pathResult;
    }

    public void botTurn() {
        ArrayList<Node> possibleMoves = new ArrayList<>();
        Node nextMove = null;
        if (this.numberOfMovesFromBot.isEmpty()) {
            for(int i = 0; i < board[0].length; i++) {
                if (board[0][i].isClickable()) {
                    possibleMoves.add(board[0][i]);
                }
            }
            nextMove = possibleMoves.get((int) (Math.random() * possibleMoves.size()));
        } else {
            ArrayList<Node> tempPath;
            ArrayList<Node> shortestPath = null;
            for (Node n : this.numberOfMovesFromBot) {
                tempPath = aStar(n);
                if ( shortestPath == null || tempPath.size() < shortestPath.size() && tempPath.size() > 0) {
                    shortestPath = tempPath;
                }
            }
            if (shortestPath != null && shortestPath.size() > 0) {
                nextMove = shortestPath.get(0);
            }
        }
        if (nextMove != null) {
            nextMove.setColor(Board.botColor);
            this.numberOfMovesFromBot.add(nextMove);
        }
    }

    public boolean victoryCheck(Color color) {
        if (color == Color.BLACK) {
            for(int t = 0; t < board[0].length; t++) {
                if (board[0][t].getColor() == color) {

                    Node s = board[0][t];

                    ArrayList<Node> openedNodes = new ArrayList<>();
                    Node pi[][] = new Node[board.length][board[0].length];
                    for (int i = 0; i < pi.length; i++){
                        for (int j = 0; j < pi[0].length; j++){
                            pi[i][j] = null;
                            board[i][j].setDelta(Integer.MAX_VALUE);
                            board[i][j].setF(Integer.MAX_VALUE);
                        }
                    }
                    s.setF(s.getHeuristic());
                    s.setDelta(0);
                    Node x = s;
                    openedNodes.add(x);
                    while (x.getLine() != board.length-1 && !openedNodes.isEmpty()) {
                        x = openedNodes.get(0);
                        for(Node n : openedNodes) {
                            if (n.getHeuristic() < x.getHeuristic()){
                                x = n;
                            }
                        }

                        // Fonction Examiner() :
                        for (Node y : x.getNeighborsColor(color)) {
                            if ((x.getDelta() + 1) < y.getDelta()) {
                                y.setDelta(x.getDelta() + 1);
                                pi[y.getLine()][y.getColumn()] = x;
                                y.setF(y.getDelta() + y.getHeuristic());

                                // Fonction Ouvrir()
                                openedNodes.add(y);
                            }
                        }
                        // Fonction Fermer()
                        openedNodes.remove(x);
                    }

                    if (x.getLine() == board.length-1) {
                        return true;
                    }
                }
            }
        } else if (color == Color.WHITE) {
            for(int t = 0; t < board.length; t++) {
                if (board[t][0].getColor() == color) {

                    Node s = board[t][0];

                    ArrayList<Node> openedNodes = new ArrayList<>();
                    Node pi[][] = new Node[board.length][board[0].length];
                    for (int i = 0; i < pi.length; i++){
                        for (int j = 0; j < pi[0].length; j++){
                            pi[i][j] = null;
                            board[i][j].setDelta(Integer.MAX_VALUE);
                            board[i][j].setF(Integer.MAX_VALUE);
                        }
                    }
                    s.setF(s.getHeuristic());
                    s.setDelta(0);
                    Node x = s;
                    openedNodes.add(x);
                    while (x.getColumn() != board[0].length-1 && !openedNodes.isEmpty()) {
                        x = openedNodes.get(0);
                        for(Node n : openedNodes) {
                            if (n.getHeuristic() < x.getHeuristic()){
                                x = n;
                            }
                        }

                        // Fonction Examiner() :
                        for (Node y : x.getNeighborsColor(color)) {
                            if ((x.getDelta() + 1) < y.getDelta()) {
                                y.setDelta(x.getDelta() + 1);
                                pi[y.getLine()][y.getColumn()] = x;
                                y.setF(y.getDelta() + y.getHeuristic());

                                // Fonction Ouvrir()
                                openedNodes.add(y);
                            }
                        }
                        // Fonction Fermer()
                        openedNodes.remove(x);
                    }

                    if (x.getColumn() == board[0].length-1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean victoryPopUp(boolean humanWon) {

        String message;
        if (humanWon) {
            message = "gagné";
        } else {
            message = "perdu";
        }
        int choice = JOptionPane.showOptionDialog(null, //Component parentComponent
                "Vous avez " + message + " ! Voulez-vous refaire une partie ?", //Object message,
                "Faites votre choix", //String title
                JOptionPane.YES_NO_OPTION, //int optionType
                JOptionPane.INFORMATION_MESSAGE, //int messageType
                null, //Icon icon,
                new String[]{"Avec plaisir !", "Non merci."}, //Object[] options,
                "Avec plaisir !");//Object initialValue

        return choice == 0;
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
        if (gameInProgress) {
            for(int i = 0 ; i < board.length; i++){
                for(int j = 0 ; j < board[1].length; j++){
                    if (board[i][j].getPolygone().contains(new Point(e.getX(),e.getY())) && board[i][j].isClickable()) {
                        board[i][j].setColor(Color.WHITE);
                        repaint();
                        if (victoryCheck(Color.WHITE)) {
                            gameInProgress = false;
                            if (victoryPopUp(true)){
                                this.parent.dispose();
                                new HexWindow();
                            } else {
                                this.parent.dispose();
                            }
                        } else {
                            botTurn();
                            if (victoryCheck(Color.BLACK)) {
                                gameInProgress = false;
                                if (victoryPopUp(false)){
                                    this.parent.dispose();
                                    new HexWindow();
                                } else {
                                    this.parent.dispose();
                                }
                            }
                        }
                    }
                }
            }
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