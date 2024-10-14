package dao;
import model.Jeu;
import model.mapper.JeuMapper;

import java.util.List;

public class JeuDAO{
    private GenericDAO<Jeu> dao = new GenericDAO(new JeuMapper());

    public List<Jeu> getAllJeu() {
        List<Jeu> listj = dao.executeQuery("SELECT * FROM Jeu");
        return listj;
    }
    public Jeu getJeuById(String nom) {
        List<Jeu> listj = dao.executeQuery("SELECT * FROM jeu WHERE nomJeu = " + nom);
        return listj.getFirst();
    }
    public void creerJeu(String nom) {
        dao.executeInsert("INSERT INTO jeu(nomJeu) VALUES (?)",nom);
    }

}
