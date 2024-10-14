package model;

import vue.ShowInMenu;

public class PropositionPartie implements ShowInMenu {
    private int idProposition;
    private int mise;
    private boolean refuse;
    private Eleve eleveLanceur;
    private Eleve eleveReceveur;
    private Partie partie;
    private String nomJeu;

    public PropositionPartie(int idProposition, int mise, boolean refuse, Eleve eleveLanceur, Eleve eleveReceveur, String nomJeu) {
        this.idProposition = idProposition;
        this.mise = mise;
        this.refuse = refuse;
        this.eleveLanceur = eleveLanceur;
        this.eleveReceveur = eleveReceveur;
        this.nomJeu = nomJeu;
    }

    public int getIdProposition() {
        return idProposition;
    }

    public int getMise() {
        return mise;
    }

    public boolean isRefuse() {
        return refuse;
    }

    public Eleve getEleveLanceur() {
        return eleveLanceur;
    }

    public Eleve getEleveReceveur() {
        return eleveReceveur;
    }

    public void setPartie(Partie partie) {
        this.partie = partie;
    }

    public Partie getPartie() {
        return partie;
    }

    public String getNomJeu() {
        return nomJeu;
    }

    @Override
    public String toString() {
        return "Jeu : "+this.getNomJeu()+" / Receveur : "+this.getEleveReceveur().getLabelMenu()+" / Lanceur : "+this.getEleveLanceur().getLabelMenu()+" / Mise : "+this.getMise();
    }

    @Override
    public String getLabelMenu() {
        return this.toString();
    }

    @Override
    public int getIdMenu() {
        return this.idProposition;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PropositionPartie pp ){
            return this.getIdProposition() == pp.getIdProposition();
        }
        return false;
    }
}
