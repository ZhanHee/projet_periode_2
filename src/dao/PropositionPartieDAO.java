package dao;

import model.Eleve;
import model.PropositionPartie;
import model.mapper.PropositionPartieMapper;

import java.util.List;

public class PropositionPartieDAO {
    private final GenericDAO<PropositionPartie> dao = new GenericDAO(new PropositionPartieMapper());

    public List<PropositionPartie> getPropositionPartie() {
        return dao.executeQuery("SELECT * FROM propositionpartie");
    }

    public PropositionPartie getPropositionPartieById(int id){
        List<PropositionPartie> propositionParties = dao.executeQuery("SELECT * FROM propositionpartie WHERE idProposition = ?", id);
        return propositionParties.getFirst();
    }

    // TODO: verifier que les id existent bien en base de donnee
    public PropositionPartie createPropositionPartie(Eleve receveur, Eleve lanceur, int mise, String nomJeu){
        Integer idPP = dao.executeInsert("INSERT INTO PropositionPartie (mise, idEleve_receveur, idEleve_lanceur, nomJeu) VALUES (?, ?, ?, ?)", mise, receveur.getId(), lanceur.getId(),nomJeu);
        if(idPP != null){
            return this.getPropositionPartieById(idPP);
        }
        return null;

    }


    public void ajouterPropositionPartie_Winner(PropositionPartie propositionPartie,Eleve eleve) {
        dao.executeInsert("UPDATE PropositionPartie SET idEleve_vainqueur = ? WHERE idProposition = ?",eleve.getId(),propositionPartie.getIdProposition());
    }


    public  List<PropositionPartie>  getPropositionPartieParEleve(int ide) {
        List<PropositionPartie> lieste= dao.executeQuery("SELECT * FROM PropositionPartie WHERE (idEleve_receveur = ? OR idEleve_lanceur = ?",ide,ide);
        return lieste;
    }

    public List<PropositionPartie> getPropositionsPartiesRecuesNonAcceptees(Eleve receveur){
        return dao.executeQuery("SELECT pp.* FROM propositionpartie pp WHERE pp.idEleve_receveur = ? AND pp.idProposition NOT IN(SELECT p.idProposition FROM partie p WHERE p.idProposition = pp.idProposition)", receveur.getId());
    }

    public List<PropositionPartie> getPropositionsPartiesAccepteesParReceveur(Eleve lanceur){
        return dao.executeQuery("SELECT pp.* FROM propositionpartie pp WHERE pp.idEleve_vainqueur IS NULL AND pp.idEleve_lanceur = ? AND pp.idProposition IN(SELECT p.idProposition FROM partie p WHERE p.idProposition = pp.idProposition)", lanceur.getId());
    }

    public void setGagnantPartie(int idEleve, PropositionPartie pp){
        dao.executeUpdate("UPDATE PropositionPartie SET idEleve_vainqueur = ? WHERE idProposition = ?", idEleve, pp.getIdProposition());
    }

}
