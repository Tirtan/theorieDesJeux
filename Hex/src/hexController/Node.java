/**
 * Authors : Bruno ARIGANELLO, Titouan CORNILLEAU
 * Date : 2021-05-13
 */
package hexController;

import java.awt.*;
import java.util.ArrayList;

public class Node {

    private static final Color baseColor = new Color(219, 195, 153);

    private int column, line, heuristic, delta, f;
    private ArrayList<Node> neighbors;
    private Polygon polygone;
    private Color color;

    public Node(int line, int column) {
        this.neighbors = new ArrayList<>();
        this.polygone = new Polygon();
        this.color = baseColor;
        this.line = line;
        this.column = column;
        this.heuristic = 0;
        this.delta = 0;
        this.f = 0;
    }

    public void addNeighbor(Node voisin) {
        this.neighbors.add(voisin);
    }

    public boolean isClickable() {
        return this.color == baseColor;
    }

    public void setColor(Color color) { this.color = color; }

    public void setHeuristic(int heuristic) { this.heuristic = heuristic; }

    public void setDelta(int delta) { this.delta = delta; }

    public void setF(int f) {
        this.f = f;
    }

    public ArrayList<Node> getNeighbors() {
        return this.neighbors;
    }

    public ArrayList<Node> getClickableNeighbors() {
        ArrayList<Node> alReturn = new ArrayList<>();
        for (Node n : this.neighbors) {
            if (n.isClickable()) {
                alReturn.add(n);
            }
        }
        return alReturn;
    }

    public ArrayList<Node> getNeighborsColor(Color couleur) {
        ArrayList<Node> alReturn = new ArrayList<>();
        for (Node n : this.neighbors) {
            if (n.getColor() == couleur) {
                alReturn.add(n);
            }
        }
        return alReturn;
    }

    public Polygon getPolygone() {
        return this.polygone;
    }

    public Color getColor() {
        return this.color;
    }

    public int getLine() {
        return this.line;
    }

    public int getColumn() {
        return this.column;
    }

    public int getHeuristic() {
        return this.heuristic;
    }

    public int getDelta() {
        return this.delta;
    }

    public int getF() {
        return this.f;
    }
}