package dao;

import model.Maison;
import model.mapper.MaisonMapper;

import java.util.List;

public class MaisonDAO {
    private GenericDAO<Maison> dao = new GenericDAO<>(new MaisonMapper());

    public List<Maison> getMaisons(){
        return this.dao.executeQuery("SELECT * FROM maison");
    }

    public Maison getMaisonByNomMaison(String nomMaison){
        List<Maison> maisons = dao.executeQuery("SELECT * FROM maison WHERE nomMaison = ?" , nomMaison );
        return maisons.getFirst();
    }

}
