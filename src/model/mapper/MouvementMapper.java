package model.mapper;

import model.Mouvement;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MouvementMapper implements Mapper<Mouvement>{
    @Override
    public Mouvement map(ResultSet rs) throws SQLException {
        int id = rs.getInt("idMouv");
        String mouv = rs.getString("mouv");
        long timestamp = rs.getLong("timestampp");
        int idEleve = rs.getInt("idEleve");
        return new Mouvement(id, mouv, timestamp, idEleve);
    }
}
