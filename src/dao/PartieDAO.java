package dao;


import model.Partie;
import model.PropositionPartie;
import model.mapper.PartieMapper;
import model.mapper.PropositionPartieMapper;

import java.util.List;

public class PartieDAO {
    private final GenericDAO<PropositionPartie> ppDAO = new GenericDAO<>(new PropositionPartieMapper());
    private final GenericDAO<Partie> partieDAO = new GenericDAO<>(new PartieMapper());
    private EleveDAO eleveDAO = new EleveDAO();

    public List<Partie> getPartie(){
        return partieDAO.executeQuery("SELECT * FROM partie");
    }

    public Partie getPartieById(int id){
        return partieDAO.executeQuery("SELECT * FROM partie WHERE idPartie = ?", id).getFirst();
    }

    public Partie getPartieByIdProposition(int idProp){
        return partieDAO.executeQuery("SELECT * FROM partie WHERE idProposition = ?", idProp).getFirst();

    }

    public Partie accepterPartie(PropositionPartie propo){
        Integer idPartie = partieDAO.executeInsert("INSERT INTO partie (idProposition) VALUES (?)", propo.getIdProposition());

        if(idPartie != null){
            return this.getPartieById(idPartie);
        }
        return null;
   }
    public void createPartie(PropositionPartie propositionPartie) {
        partieDAO.executeInsert("INSERT INTO partie (idProposition) VALUES (?)", propositionPartie.getIdProposition());
    }
    public PropositionPartie getPropositionPartieParPartieId(int idp) {
        List<Partie> p = partieDAO.executeQuery("SELECT * FROM partie WHERE idPartie = ?", idp);
        List<PropositionPartie> listp = ppDAO.executeQuery("SELECT * FROM PropositionPartie WHERE idProposition = ?", p.getFirst().getIdPropositionPartie());
        return listp.getFirst();
    }
}
