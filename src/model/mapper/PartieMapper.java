package model.mapper;

import dao.PropositionPartieDAO;
import model.Partie;
import model.PropositionPartie;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PartieMapper implements Mapper<Partie>{

    public Partie map(ResultSet rs) throws SQLException {
        int idPartie = rs.getInt("idPartie");
        int idProposition = rs.getInt("idProposition");

        PropositionPartieDAO propositionPartieDAO = new PropositionPartieDAO();
        PropositionPartie propositionPartie = propositionPartieDAO.getPropositionPartieById(idProposition);
        return new Partie(idPartie, propositionPartie);
    }
}
