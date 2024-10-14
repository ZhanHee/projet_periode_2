package model.mapper;

import dao.MaisonDAO;
import model.Eleve;
import model.Maison;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EleveMapper implements Mapper<Eleve> {
    @Override
    public Eleve map(ResultSet rs) throws SQLException {
        int id = rs.getInt("idEleve");
        String nom = rs.getString("nom");
        String prenom = rs.getString("prenom");
        int totalPoints = rs.getInt("totalPoints");
        return new Eleve(id, nom, prenom, totalPoints);
    }
}
