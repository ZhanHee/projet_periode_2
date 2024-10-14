package model.jeux;

import dao.MouvementDAO;
import dao.PartieDAO;
import dao.PropositionPartieDAO;
import model.*;
import vue.Menu;
import vue.Notification;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChifoumiModel {
    private static final boolean ACTIVATE_TEST = true;
    private final ScheduledExecutorService scheduler;

    private PropositionPartieDAO ppDAO = new PropositionPartieDAO();
    private PartieDAO partieDAO = new PartieDAO();
    private MouvementDAO mDAO = new MouvementDAO();

    private Partie partie;
    private Eleve joueur;
    private List<Mouvement> mouvements;
    private PropositionPartie prop;
    private boolean isLanceur;
    private Map<Integer, String> optionsCoups = new HashMap<>();

    public ChifoumiModel(PropositionPartie prop, Eleve joueur, boolean isLanceur) {
        this.mouvements = new ArrayList<>();

        // init options coups
        this.optionsCoups.put(1, CoupChifoumi.BAGUETTE.name());
        this.optionsCoups.put(2, CoupChifoumi.BALAI.name());
        this.optionsCoups.put(3, CoupChifoumi.CHAUDRON.name());

        this.joueur = joueur;
        this.scheduler = Executors.newScheduledThreadPool(1); // Crée le scheduler

        this.prop = prop;
        this.isLanceur = isLanceur;
    }

    private void execUpdateMouvLog(){
        try{
            this.updateMouvements(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // cree objet partie en DB et lance la boucle
    public int lancerPartie(){
        this.partie = isLanceur ? partieDAO.getPartieByIdProposition(prop.getIdProposition()) : partieDAO.accepterPartie(prop);
        if(this.joueur.getMaison().getNom().equals("SERPENTARD")){
            Runnable task = this::execUpdateMouvLog;
            this.scheduler.scheduleAtFixedRate( task, 0, 2, TimeUnit.SECONDS);
        }
        int idGagnant = this.start();
        if(idGagnant == this.joueur.getId()){
            System.out.println("Vous avez gagné ! + "+this.prop.getMise()+" points pour "+this.joueur.getMaison().getNom());
            ppDAO.setGagnantPartie(idGagnant, this.prop);
        }
        else {
            System.out.println("Vous avez perdu :/");
        }
        return idGagnant;
    }

    private Integer start(){
        while (true){
            if(this.canPlay()){
                this.ajouterMouvement();
                System.out.println("Attente du mouvement de l'adveresaire...");
            }
            if(this.mouvements.size()%2 == 0){
                // les deux joueurs ont joué
                Mouvement m1 = this.mouvements.get(this.mouvements.size()-1);
                Mouvement m2 = this.mouvements.get(this.mouvements.size()-2);
                int idGagnantDuel = this.duel(m1,m2);
                boolean currentPlayerWon = this.joueur.getId() == idGagnantDuel;
                System.out.println(m1.getCoup()+ " contre "+m2.getCoup()+", "+ (currentPlayerWon ? "tu gagnes la manche !" : "tu as perdu la manche."));
            }
            Integer gagnant = this.checkPartieScore();

            // condition de fin de partie
            if(gagnant != null){
                return gagnant;
            }
        }
    }

    private boolean canPlay() {
        int currentPlayerPlaysCount = this.mouvements.stream().filter(m -> m.getIdEleve() == this.joueur.getId()).toList().size();
        int otherPlayerPlaysCount = this.mouvements.stream().filter(m -> m.getIdEleve() != this.joueur.getId()).toList().size();
        try{
            Thread.sleep(2000);
            this.updateMouvements(false);
        } catch (InterruptedException e){
            System.out.println("Thread sleep exception");
            e.printStackTrace();
        }catch (Exception e){
            System.out.println("other exception");
            e.printStackTrace();
        }

        if(ChifoumiModel.ACTIVATE_TEST){
        }
        // if other player played, but you didn't, you can play
        // if both did not (new round), both of you can play
        // if you played and not the other, you cannot play (wait for opponent)
        return currentPlayerPlaysCount - otherPlayerPlaysCount <= 0;
    }

    private void updateMouvements(boolean log){
        List<Mouvement> dbMouvements = mDAO.getMouvementsByPartie(this.partie);
        for (Mouvement m : dbMouvements){
            if(!this.mouvements.contains(m)){
                if(log){
                    System.out.println(m+"...");
                }
                this.mouvements.add(m);
            }
        }
    }

    private CoupChifoumi getCoupValue(String coup){
        return CoupChifoumi.valueOf(coup);
    }

    /**
     * @return idEleve
     */
    public Integer duel(Mouvement m1, Mouvement m2){
        CoupChifoumi coup1 = this.getCoupValue(m1.getCoup());
        CoupChifoumi coup2 = this.getCoupValue(m2.getCoup());

        if (coup1 == coup2) {
            return null; // Égalité
        }

        if (coup1 == CoupChifoumi.BAGUETTE) {
            if (coup2 == CoupChifoumi.BALAI) {
                return m1.getIdEleve(); // Baguette bat Balai
            } else if (coup2 == CoupChifoumi.CHAUDRON) {
                return m2.getIdEleve(); // Chaudron bat Baguette
            }
        }

        if (coup1 == CoupChifoumi.BALAI) {
            if (coup2 == CoupChifoumi.CHAUDRON) {
                return m1.getIdEleve(); // Balai bat Chaudron
            } else if (coup2 == CoupChifoumi.BAGUETTE) {
                return m2.getIdEleve(); // Baguette bat Balai
            }
        }

        if (coup1 == CoupChifoumi.CHAUDRON) {
            if (coup2 == CoupChifoumi.BAGUETTE) {
                return m1.getIdEleve(); // Chaudron bat Baguette
            } else if (coup2 == CoupChifoumi.BALAI) {
                return m2.getIdEleve(); // Balai bat Chaudron
            }
        }
        return null;
    }


    public void ajouterMouvement(){
        Integer coup = Menu.showGenericMenu(this.optionsCoups, "Choisis un mouvement");
        Mouvement m = mDAO.createMouvement(this.joueur, this.partie, this.optionsCoups.get(coup));
        this.mouvements.add(m);
    }

    /**
     * @return idEleve gagnant
     */
    private Integer checkPartieScore(){
        Map<Integer, Integer> scores = new HashMap<>();
        for(int i=0; i+1<this.mouvements.size(); i+=2){
            scores.merge(this.duel(this.mouvements.get(i),this.mouvements.get(i+1)), 1, Integer::sum);
        }
        for (Integer idJoueur : scores.keySet()){
            if(scores.get(idJoueur) == 2){ // BO3 gagant hors égalité
                return idJoueur;
            }
        }
        return null;
    }
}
