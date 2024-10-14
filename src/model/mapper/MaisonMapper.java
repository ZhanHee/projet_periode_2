package model.mapper;

import model.Maison;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MaisonMapper implements Mapper<Maison> {
    @Override
    public Maison map(ResultSet rs) throws SQLException {

        String nom = rs.getString("nomMaison");

        return new Maison(nom);

    }
}
