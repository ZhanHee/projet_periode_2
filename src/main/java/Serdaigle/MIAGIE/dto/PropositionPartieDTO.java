package Serdaigle.MIAGIE.dto;

import Serdaigle.MIAGIE.model.Jeu;

/**
 * Cette classe permet de représenter une proposition de partie.
 */
public class PropositionPartieDTO {

    private final int id;
    private final EleveDTO joueurSource;
    private final EleveDTO joueurCible;
    private PartieDTO partie;
    private final int mise;
    private Jeu jeu;


    /**
     * Constructeur de la classe PropositionPartieDTO
     * @param id l'identifiant de la proposition de partie
     * @param joueurSource l'élève qui a fait la proposition
     * @param joueurCible l'élève qui a reçu la proposition
     * @param jeu le jeu proposé
     * @param mise la mise proposée
     */
    public PropositionPartieDTO(int id, EleveDTO joueurSource, EleveDTO joueurCible, Jeu jeu, int mise) {
        this.id = id;
        this.joueurSource = joueurSource;
        this.joueurCible = joueurCible;
        this.mise = mise;
        this.jeu = jeu;
    }

    public void setPartie(PartieDTO p){
        this.partie = p;
    }

    public int getId() {
        return id;
    }

    public int getMise() {
        return mise;
    }

    public EleveDTO getJoueurSource() {
        return joueurSource;
    }

    public EleveDTO getJoueurCible() {
        return joueurCible;
    }
}
