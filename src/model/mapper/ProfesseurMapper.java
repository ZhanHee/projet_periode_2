package model.mapper;

import model.Professeur;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfesseurMapper implements Mapper<Professeur> {


    @Override
    public Professeur map(ResultSet rs) throws SQLException {
        int id_prof = rs.getInt("idProfesseur");
        String nom = rs.getString("nom");
        String prenom = rs.getString("prenom");
        String nomMatiere = rs.getString("nomMatiere");
        Boolean isDirecteur = rs.getBoolean("isDirecteur");
        return new Professeur(id_prof, nom, prenom, nomMatiere, isDirecteur);
    }
}
