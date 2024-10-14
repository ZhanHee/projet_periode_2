package controller;

import dao.ProfesseurDAO;
import model.Eleve;
import model.Maison;
import model.Professeur;
import model.Responsable;
import vue.Menu;
import vue.Notification;

import java.util.HashMap;
import java.util.Map;

public class AppManager {
    public Map<Integer, String> roleOptions = new HashMap<>();
    public Map<Integer, String> eleveOptions = new HashMap<>();
    public Map<Integer, String> professeurOptions = new HashMap<>();
    public Map<Integer, String> responsableOptions = new HashMap<>();


    public EcoleController ecoleController = new EcoleController();
    public ProfesseurController professeurController = new ProfesseurController();
    public ResponsableController responsableController = new ResponsableController();

    public AppManager(){
        this.initOptions();
        this.appLoop();

    }

    public void initOptions(){
//        Role options
        roleOptions.put(1, "Eleve");
        roleOptions.put(2, "Prof");
        roleOptions.put(3, "Responsable");

//        eleve options
        eleveOptions.put(1, "Proposer une partie");
        eleveOptions.put(2, "Afficher les propositions de partie reçues");
        eleveOptions.put(3, "Rejoindre le début d'une partie proposée");

//      professeur options
        professeurOptions.put(1,"ajouter une note eleve");
        professeurOptions.put(2,"consulter professeur par matiere");
        professeurOptions.put(3, "consulter les eleves");


//      responsable options
        responsableOptions.put(1, "ajouter un eleve");
        responsableOptions.put(2, "consulter le note total dans un maison");

    }

    public void appLoop(){
        while (true){
            int value = Menu.showGenericMenu(this.roleOptions, "Rôle");
            switch (value){
                case 1: this.eleveLoop();
                break;
                case 2: this.professeurLoop();
                break;
                case 3: this.responsableLoop();
                break;
            }
        }
    }



    public void eleveLoop(){
        Maison maison = (Maison) Menu.showEntityMenu(ecoleController.getMaisons(), "Maisons");
        if (maison == null) {
            return;
        }
        Eleve eleve = (Eleve) Menu.showEntityMenu(maison.getEleves(), "Eleve");
        if (eleve == null) {
            return;
        }
        Notification notif = new Notification(eleve);
        notif.run();
        while (true) {
            Integer action = Menu.showGenericMenu(eleveOptions, "Menu élève (" + eleve.getLabelMenu() + ")");
            if (action == null) {
                break;
            }
            switch (action) {
                case 1:
                    ecoleController.proposerPartie(eleve);
                    break;
                case 2:
                    ecoleController.accepterPropositionsPartieRecues(eleve);
                    break;
                case 3:
                    ecoleController.rejoindreDebutPartie(eleve);
                    break;
                default:
                    break;
            }
        }
        notif.stop();
//        reset data
        ecoleController = new EcoleController();
    }

    public void professeurLoop(){
        Professeur professeur= (Professeur) Menu.showEntityMenu(professeurController.getProfesseur(), "Professeurs:" );
        while(true){
            int value = Menu.showGenericMenu(this.professeurOptions, "Menu professeur ("+professeur.getNom()+" " +professeur.getPrenom()+")");
            switch (value){
                //ajouter une note eleve
                case 1:
                    professeurController.ajouterNoteElever(professeur, ecoleController.getMaisons());
                    break;
                case 2:
                    professeurController.getProfesseurByMatiere(professeur);
                    break;
                case 3:
                    professeurController.getEleves();
                    break;
            }
        }
    }

    public void responsableLoop(){
        Responsable responsable = (Responsable) Menu.showEntityMenu(responsableController.getResponsables(), "Responsable");
        while(true){
            Integer value = Menu.showGenericMenu(this.responsableOptions, "Menu responsable ("+responsable.getNom()+" "+responsable.getPrenom()+")");
            switch (value){
                case 1:
                    responsableController.createEleve(responsable);
                    break;
                case 2:
                    responsableController.getNoteParMaisons(responsable, ecoleController.getMaisons());
                    break;
                case null, default: return;
            }
        }
    }
}
