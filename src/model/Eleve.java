package model;

import vue.ShowInMenu;

import java.util.ArrayList;
import java.util.List;


public class Eleve implements ShowInMenu {
    private int id;
    private String nom, prenom;
    private int score;
    private Maison maison;
    private ArrayList<Evaluation> evaluations;
    private List<PropositionPartie> propositionsRecues;
    private List<PropositionPartie> propositionsEnvoyees;

    public Eleve(int id, String nom, String prenom, int score) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.score = score;
        this.maison = null;
        this.evaluations = new ArrayList<>();
        this.propositionsRecues = new ArrayList<>();
        this.propositionsEnvoyees = new ArrayList<>();

    }

    public Eleve(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        this.score = 0;
        this.maison = null;
        this.evaluations = new ArrayList<>();
        this.propositionsRecues = new ArrayList<>();
        this.propositionsEnvoyees = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getScore() {
        return this.score;
    }


    public int getId() {
        return id;
    }

    public Maison getMaison() {
        return maison;
    }

    public ArrayList<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addScore(int value){
        this.score+=value;
    }

    public void reduceScore(int value){
        this.score-=value;
    }

    public void setMaison(Maison maison) {
        if(maison.equals(this.maison)){
            return;
        }
//        remove eleve from old maison (if exists)
        if(this.maison != null){
            this.maison.removeEleve(this);
        }
//        set eleve in new maison
        maison.addEleve(this);
//        update maison in this eleve
        this.maison = maison;
    }

    public void addPropositionPartie(PropositionPartie pp){
        this.propositionsRecues.add(pp);
    }
    public int addPropositionsPartie(List<PropositionPartie> pp){
        int totalAdded = 0;
        for(PropositionPartie prop : pp){
            if(!this.propositionsRecues.contains(prop)){
                totalAdded+=1;
                this.addPropositionPartie(prop);
            }
        }
        return totalAdded;
    }

    public void addPropositionEnvoyee(PropositionPartie pp){
        this.propositionsEnvoyees.add(pp);
    }
    public int addPropositionsEnvoyee(List<PropositionPartie> pp){
        int totalAdded = 0;
        for(PropositionPartie prop : pp){
            if(!this.propositionsEnvoyees.contains(prop)){
                totalAdded+=1;
                this.addPropositionEnvoyee(prop);
            }
        }
        return totalAdded;
    }

    public List<PropositionPartie> getPropositionsRecues() {
        List<PropositionPartie> propositionsSansPartie = new ArrayList<>();
        for (PropositionPartie pp : this.propositionsRecues){
            if(pp.getPartie() == null){
                propositionsSansPartie.add(pp);
            }
        }
        return propositionsSansPartie;
    }

    @Override
    public String toString() {
        return "Eleve{" +
                "score=" + score +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public String getLabelMenu() {
        return this.getNom() + " " + this.getPrenom();
    }

    @Override
    public int getIdMenu() {
        return this.getId();
    }
}