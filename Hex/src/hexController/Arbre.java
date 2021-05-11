package hexController;

import java.util.ArrayList;
import java.util.List;

public class Arbre {

    private Noeud racine;
    private List<Noeud> fils;

    public Arbre() {
        this.racine = new Noeud();
        this.fils = new ArrayList<>();
    }

    public Arbre(Noeud racine) {
        this.racine = racine;
        this.fils = new ArrayList<>();
    }

    public void setRacine(Noeud racine) {
        this.racine = racine;
    }

    public void ajouterFils(Noeud fils) {
        this.fils.add(fils);
    }

    public Noeud getRacine() {
        return this.racine;
    }

    public List<Noeud> getFils() {
        return this.fils;
    }
}
