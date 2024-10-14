package model.mapper;

import dao.PartieDAO;
import model.Jeu;
import model.Maison;
import model.Mouvement;
import model.Partie;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JeuMapper implements Mapper<Jeu>{

    @Override
        public Jeu map(ResultSet rs) throws SQLException {
        String nom = rs.getString("nomJeu" );

        Jeu jeu = new Jeu(nom);
        return jeu;
    }
}
