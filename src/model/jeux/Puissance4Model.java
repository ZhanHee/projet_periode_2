package model.jeux;

import dao.PartieDAO;
import dao.PropositionPartieDAO;
import model.Eleve;
import model.Mouvement;
import model.Partie;
import model.PropositionPartie;
import puissance4.Desk;
import puissance4.Move;

import java.util.List;
import java.util.Scanner;

public class Puissance4Model {

    private PartieDAO partieDAO = new PartieDAO();
    private PropositionPartieDAO ppDAO = new PropositionPartieDAO();

    private Partie partie;
    private List<Mouvement> mouvements;
    private Desk desk;

    private PropositionPartie prop;
    private boolean isLanceur;
    private Eleve joueur;

    public Puissance4Model(Eleve joueur, PropositionPartie prop, boolean isLanceur) {
        this.isLanceur = isLanceur;
        this.prop = prop;
        this.joueur = joueur;
    }

    public Integer lancerPartie(){
        this.partie = isLanceur ? partieDAO.getPartieByIdProposition(prop.getIdProposition()) : partieDAO.accepterPartie(prop);
        Move.nettoyer(this.partie.getIdPartie());

        this.desk = new Desk();
        desk.setIdPartie(this.partie.getIdPartie());

        int receveurId = Move.getReceveur(this.partie.getIdPartie());
        if (this.joueur.getId() == receveurId) {
            System.out.println("You are the receiver. You will use 'X' and play first.");
            desk.setIdEleve(this.joueur.getId());
            desk.setPieceColor('X');
        } else {
            System.out.println("You are not the receiver. You will use 'O' and play second.");
            desk.setIdEleve(this.joueur.getId());
            desk.setPieceColor('O');
        }

        int idVainqueur = this.run();
        this.ppDAO.setGagnantPartie(idVainqueur, this.prop);
        return idVainqueur;
    }

    private Integer run(){
        while (true) {
            desk.draw();
            int winner = desk.getIdWinner();
            if (winner != -1) {
                return winner;
            }
        }
    }
}
