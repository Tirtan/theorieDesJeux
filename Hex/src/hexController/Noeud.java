package hexController;

import java.awt.*;
import java.util.ArrayList;

public class Noeud {

    private static final Color baseColor = new Color(219, 195, 153);

    private static int nbNoeud;
    private int id, heuristique;
    private ArrayList<Noeud> voisins;
    private Polygon polygone;
    private boolean cliquable;

    private Color couleur;

    private int colonne, ligne;

    public Noeud(int colonne, int ligne) {
        this.id = ++Noeud.nbNoeud;
        this.voisins = new ArrayList<>();
        this.polygone = new Polygon();
        this.heuristique = 0;
        this.cliquable = true;
        this.couleur = baseColor;
        this.colonne = colonne;
        this.ligne = ligne;
    }

    public Noeud() {
        this.id = ++Noeud.nbNoeud;
        this.voisins = new ArrayList<>();
        this.polygone = new Polygon();
        this.heuristique = 0;
        this.cliquable = true;
        this.couleur = baseColor;
        this.colonne = 0;
        this.ligne = 0;
    }

    public int getId() {
        return this.id;
    }

    public int getColonne() {
        return this.colonne;
    }

    public int getLigne() {
        return this.ligne;
    }

    public ArrayList<Noeud> getVoisins() {
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

    public Color getCouleur() {
        return this.couleur;
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    public boolean isCliquable() {
        return this.couleur == baseColor;
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