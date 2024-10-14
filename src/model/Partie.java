package model;

public class Partie {

    private int idPartie;
    private PropositionPartie propositionPartie;

    public Partie(int idPartie, PropositionPartie propositionPartie) {
        this.idPartie = idPartie;
        this.propositionPartie = propositionPartie;
    }

    public int getIdPartie() {
        return idPartie;
    }
    public int getIdPropositionPartie(){
        return this.propositionPartie.getIdProposition();
    }



}
