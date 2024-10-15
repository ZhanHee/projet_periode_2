package Serdaigle.MIAGIE.dto;

/**
 * Cette classe permet de représenter une partie.
 * On pourrais imaginer que cette classe contienne des informations sur la partie en cours: tours, vainqueur, proposition initiale, etc.
 * PartieDTO
 */
public class PartieDTO {

    private final int idPartie;
    private final PropositionPartieDTO propositionPartie;

    public PartieDTO(int idPartie, PropositionPartieDTO propositionPartie){
        this.idPartie = idPartie;
        this.propositionPartie = propositionPartie;
    }

    public int getIdPartie() {
        return idPartie;
    }

    public PropositionPartieDTO getPropositionPartie() {
        return propositionPartie;
    }
}
