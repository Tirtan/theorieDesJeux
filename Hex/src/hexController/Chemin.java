package hexController;

import java.util.ArrayList;
import java.util.List;

public class Chemin {

    private ArrayList<Noeud> chemin;

    public Chemin() {
        this.chemin = new ArrayList<>();
    }

    public Chemin(ArrayList<Noeud> chemin) {
        this.chemin = chemin;
    }

    public ArrayList<Noeud> getChemin() {
        return this.chemin;
    }

    public Noeud getNoeud(int indice) {
        return this.chemin.get(indice);
    }

    public void ajouterNoeud(Noeud noeud) {
        this.chemin.add(0, noeud);
    }

    public void supprimerNoeud(Noeud noeud) {
        this.chemin.remove(noeud);
    }

    public int getTailleChemin() {
        return this.chemin.size();
    }
}
