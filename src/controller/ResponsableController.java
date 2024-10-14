package controller;

import dao.EleveDAO;
import dao.ProfesseurDAO;
import model.Eleve;
import model.Maison;
import model.Professeur;
import model.Responsable;
import vue.Menu;
import vue.ShowInMenu;

import java.util.ArrayList;
import java.util.List;

public class ResponsableController {
    private Responsable responsable;

    public List<Responsable> getResponsables(){
        List<Responsable> responsables = new ArrayList<>();
        ProfesseurDAO professeurDAO = new ProfesseurDAO();
        for(Professeur professeur : professeurDAO.getProfesseur()){
            if(professeur.getIsDirecteur()){
                Responsable responsable1 = new Responsable(professeur.getIdPersonnel(),
                        professeur.getNom(),
                        professeur.getPrenom(),
                        professeur.getMatiere());
                responsables.add(responsable1);
            }
        }
        return responsables;
    }

    public List<Maison> getMaisons(Responsable r){
        return r.getMaisons();
    }


    public void createEleve(Responsable r){
        r.creerEleve();
    }

    public void getNoteParMaisons(Responsable r, List<Maison> maisons){
        r.getNoteParMaisons(maisons);
    }
}
