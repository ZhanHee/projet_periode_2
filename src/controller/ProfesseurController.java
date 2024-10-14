package controller;

import dao.EleveDAO;
import dao.EvaluerDAO;
import dao.MatiereDAO;
import dao.ProfesseurDAO;
import model.Eleve;
import model.Maison;
import model.Matiere;
import model.Professeur;
import vue.Menu;

import java.util.List;
import java.util.Scanner;




public class ProfesseurController {
    private List<Professeur> professeurs;
    private List<Eleve> eleves;
    private List<Matiere> matieres;
    private ProfesseurDAO professeurDAO = new ProfesseurDAO();
    private EvaluerDAO evaluerDAO = new EvaluerDAO();
    private EleveDAO eleveDAO = new EleveDAO();
    private MatiereDAO matiereDAO = new MatiereDAO();
    private Professeur professeur;

    public List<Professeur> getProfesseur(){
        return this.professeurs = professeurDAO.getProfesseur();
    }
    public List<Matiere> getMatieres(){
        return this.matieres = matiereDAO.getAllMatiere();
    }
    public List<Eleve> getEleves(){
        return this.eleves = eleveDAO.getEleves();
    }

    public void ajouterNoteElever(Professeur professeur, List<Maison> maisons){
        Scanner scanner = new Scanner(System.in);
        Maison maison = (Maison) Menu.showEntityMenu(maisons, "Maisons");
        if (maison == null) {
            return;
        }
        Eleve eleve = (Eleve) Menu.showEntityMenu(maison.getEleves(), "Eleve");
        if (eleve == null) {
            return;
        }
        System.out.println("Ajoutez le note:");
        String note = scanner.next();
        if(!note.toLowerCase().equals("exit")) {
            int noteInt = Integer.parseInt(note);
            evaluerDAO.ajouterNoteDeEleve(eleve,professeur.getMatiere(),noteInt);
        }
    }

    public void getProfesseurByMatiere(Professeur p) {
        p.getProfesseurByMatiere();
    }


}
