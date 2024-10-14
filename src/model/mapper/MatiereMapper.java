package model.mapper;

import model.Matiere;
import model.Partie;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MatiereMapper implements Mapper<Matiere>{

    @Override
    public Matiere map(ResultSet rs) throws SQLException {
        String nom = rs.getString("nomMatiere");
        return new Matiere(nom);
    }
}

