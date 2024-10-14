package dao;

import model.Matiere;
import model.Professeur;
import model.mapper.MatiereMapper;
import model.mapper.ProfesseurMapper;

import java.util.List;

public class MatiereDAO {
    private GenericDAO<Matiere> dao = new GenericDAO<>(new MatiereMapper());
    private GenericDAO<Professeur> daop = new GenericDAO<>(new ProfesseurMapper());

    public List<Matiere> getAllMatiere(){
        List<Matiere> listm = dao.executeQuery("select * from matiere");
        return listm;
    }

    public Matiere getMatiereById(String nommatiere){
        List<Matiere> listm = dao.executeQuery("SELECT * FROM matiere WHERE nommatiere = ?" , nommatiere);
        return listm.getFirst();
    }

    public void creerMatiere(String nommatiere){
        dao.executeInsert("INSERT INTO matiere(nomMatiere) VALUES (?)" , nommatiere);
    }
    public List<Professeur> getProfesseurByMatiere(String nommatiere){
        List<Professeur> listp = daop.executeQuery("SELECT * FROM professeur WHERE nomMatiere = ?" , nommatiere);
        return listp;
    }
    public Matiere getNomMatiereByProfesseur(Professeur professeur){
        return dao.executeQuery("SELECT * FROM matiere WHERE nomMatiere = ?", professeur.getMatiere()).getFirst();
    }


}
