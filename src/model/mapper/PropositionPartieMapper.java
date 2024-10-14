package model.mapper;

import dao.EleveDAO;
import model.Eleve;
import model.PropositionPartie;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PropositionPartieMapper implements Mapper<PropositionPartie>{
    @Override
    public PropositionPartie map(ResultSet rs) throws SQLException {
        int id = rs.getInt("idProposition");
        int mise = rs.getInt("mise");
        boolean refuse = rs.getBoolean("refuse");
        int idLanceur = rs.getInt("idEleve_lanceur");
        int idReceveur = rs.getInt("idEleve_receveur");
        String nomJeu = rs.getString("nomJeu");

        EleveDAO eleveDAO = new EleveDAO();
        Eleve eleveLanceur = eleveDAO.getEleveById(idLanceur, false);
        Eleve eleveReceveur = eleveDAO.getEleveById(idReceveur, false);

        return new PropositionPartie(id, mise, refuse, eleveLanceur, eleveReceveur, nomJeu);
    }


}
