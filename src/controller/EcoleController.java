package controller;

import dao.EleveDAO;
import dao.MaisonDAO;
import dao.ProfesseurDAO;
import dao.PropositionPartieDAO;
import model.Eleve;
import model.Maison;
import model.Professeur;
import model.Responsable;
import model.PropositionPartie;
import model.jeux.ChifoumiModel;
import model.jeux.Puissance4Model;
import vue.Menu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EcoleController {
    private List<Maison> maisons;
    private MaisonDAO maisonDAO = new MaisonDAO();
    private EleveDAO eleveDAO = new EleveDAO();
    private List<Professeur> professeurs;
    private List<Responsable> responsables;
    private ProfesseurDAO professeurDAO = new ProfesseurDAO();
    private PropositionPartieDAO propPartieDAO = new PropositionPartieDAO();

    public EcoleController() {
        this.init();
    }

    private void init(){
//        get maisons
        this.maisons = maisonDAO.getMaisons();

//        get eleves par maison
        for (Maison m : this.maisons) {
            List<Eleve> elevesMaison = eleveDAO.getElevesByMaison(m, true);
            m.addEleves(elevesMaison);
        }
        this.initMaisons();
        this.initProfesseurs();
    }

    private void initMaisons(){
//        get maisons
        this.maisons = maisonDAO.getMaisons();

//        get eleves par maison
        for (Maison m : this.maisons) {
            List<Eleve> elevesMaison = eleveDAO.getElevesByMaison(m, true);
            m.addEleves(elevesMaison);
        }
    }

    public List<Maison> getMaisons() {
        return maisons;
    }

    public void initProfesseurs(){
        this.professeurs = professeurDAO.getProfesseur();
//        for (Professeur p : this.professeurs){
//            if(p.getIsDirecteur()){
//                this.responsables.add((Responsable) p);
//            }
//        }

    }

    public List<Professeur> getProfesseurs() {
        return this.professeurs;
    }

    private Eleve getEleveById(int id){
        for (Maison m : this.getMaisons()){
            for(Eleve e : m.getEleves()){
                if(id == e.getId()){
                    return e;
                }
            }
        }
        return null;
    }

    public boolean proposerPartie(Eleve lanceur){
        Maison maison = (Maison) Menu.showEntityMenu(this.getMaisons(), "Maison cible");
        if(maison == null){
            return false;
        }
        Eleve receveur = (Eleve) Menu.showEntityMenu(maison.getEleves(), "Eleve cible");
        if(receveur == null){
            return false;
        }
        if(receveur == lanceur){
            System.out.println("On ne peut pas se défier soi-même.");
            return false;
        }
        Integer mise = Menu.getIntegerInput("Nombre de points à miser", Math.min(lanceur.getScore(), receveur.getScore()), "La mise est trop élevée par rapport aux points possédés par les deux joueurs.");
        if(mise == null){
            return false;
        }

        Map<Integer, String> optionsJeu = new HashMap<>();
        optionsJeu.put(1, "Chifoumi");
        optionsJeu.put(2, "Puissance4");

        Integer jeu = Menu.showGenericMenu(optionsJeu, "Jeu");

        PropositionPartie pp = propPartieDAO.createPropositionPartie(receveur, lanceur, mise, optionsJeu.get(jeu));
        receveur.addPropositionPartie(pp);
        return true;
    }


    public void accepterPropositionsPartieRecues(Eleve receveur){
        List<PropositionPartie> list = propPartieDAO.getPropositionsPartiesRecuesNonAcceptees(receveur);
        if(list.isEmpty()){
            System.out.println("Personne ne vous a défié pour le moment !");
            return;
        }
        PropositionPartie pp = (PropositionPartie) Menu.showEntityMenu(list, "Choisir un défi à accepter");
        if(pp == null){
            return;
        }

        if(pp.getNomJeu().equals("Puissance4")){
            Puissance4Model pui4 = new Puissance4Model(receveur, pp, false);
            int idGagnant = pui4.lancerPartie();
        }
        if(pp.getNomJeu().equals("Chifoumi")){
            ChifoumiModel cm = new ChifoumiModel(pp, receveur, false);
            int idGagnant = cm.lancerPartie();
        }
    }

    public void rejoindreDebutPartie(Eleve lanceur){
        List<PropositionPartie> list = propPartieDAO.getPropositionsPartiesAccepteesParReceveur(lanceur);
        if(list.isEmpty()){
            System.out.println("Personne n'a accepté un de vos défis pour le moment !");
            return;
        }
        PropositionPartie pp = (PropositionPartie) Menu.showEntityMenu(list, "Choisir un défi à accepter");
        System.out.println("test : "+pp.getNomJeu());
        if(pp == null){
            return;
        }

        if(pp.getNomJeu().equals("Puissance4")){
            Puissance4Model pui4 = new Puissance4Model(lanceur, pp, true);
            int idGagnant = pui4.lancerPartie();
        }
        if(pp.getNomJeu().equals("Chifoumi")){
            ChifoumiModel cm = new ChifoumiModel(pp, lanceur, true);
            int idGagnant = cm.lancerPartie();
        }


    }


    public void ajouterEleveAMaison(){
        EleveDAO eleveDAO = new EleveDAO();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Le nom:");
        String nom = scanner.nextLine();

        System.out.println("Le prenom:");
        String prenom = scanner.nextLine();

        System.out.println("Maison:");
        String nomMaison = scanner.nextLine();
        Maison maison=maisonDAO.getMaisonByNomMaison(nomMaison);
        eleveDAO.createEleve(nom,prenom, maison);

    }
}