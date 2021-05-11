package hexController;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Noeud {

    private static int nbNoeud;
    private int id, heuristique;
    private List<Noeud> voisins;
    private Polygon polygone;
    private boolean cliquable;

    public Noeud() {
        this.id = ++Noeud.nbNoeud;
        this.voisins = new ArrayList<>();
        this.polygone = new Polygon();
        this.heuristique = 0;
        this.cliquable = true;
    }

    public Noeud(List<Noeud> voisins) {
        this.id = ++Noeud.nbNoeud;
        this.voisins = voisins;
        this.polygone = new Polygon();
        this.heuristique = 0;
        this.cliquable = true;
    }

    public Noeud(int heuristique) {
        this.id = ++Noeud.nbNoeud;
        this.voisins = new ArrayList<>();
        this.polygone = new Polygon();
        this.heuristique = heuristique;
        this.cliquable = true;
    }

    public Noeud(List<Noeud> voisins, int heuristique) {
        this.id = ++Noeud.nbNoeud;
        this.voisins = voisins;
        this.polygone = new Polygon();
        this.heuristique = heuristique;
        this.cliquable = true;
    }

    public int getId() {
        return this.id;
    }

    public List<Noeud> getVoisins() {
        return this.voisins;
    }

    public Polygon getPolygone() {
        return this.polygone;
    }

    public Noeud getVoisin(int id) {
        for (Noeud n : this.voisins) {
            if (n.getId() == id) {
                return n;
            }
        }
        return null;
    }

    public boolean isCliquable() {
        return this.cliquable;
    }

    public void cliquer() {
        this.cliquable = false;
    }

    public void ajouterVoisin(Noeud voisin) {
        this.voisins.add(voisin);
    }

    public void retirerVoisin(Noeud voisin) {
        this.voisins.remove(voisin);
    }

    public void retirerVoisin(int id) {
        Noeud n = this.getVoisin(id);
        if (n != null) {
            this.voisins.remove(n);
        }
    }

    public String toString() {
        String retour = "(" + this.id + ")";
        if (!this.voisins.isEmpty()) {
            retour += " : ";
            for (Noeud n : this.voisins) {
                retour += "(" + n.getId() + ")";
            }
        }
        return retour;
    }

    public static void main(String[] args) {
        Noeud racine = new Noeud();
        for (int i = 0; i < 3; i++) {
            racine.ajouterVoisin(new Noeud());
        }
        System.out.println(racine);
    }

    public void setHeuristique(int heuristique) {
        this.heuristique = heuristique;
    }
}