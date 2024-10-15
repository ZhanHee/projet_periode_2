package Serdaigle.MIAGIE.jeux;

import Serdaigle.MIAGIE.dto.EleveDTO;
import Serdaigle.MIAGIE.dto.MouvementDTO;
import Serdaigle.MIAGIE.dto.PartieDTO;
import Serdaigle.MIAGIE.dto.PropositionPartieDTO;
import Serdaigle.MIAGIE.exception.PropositionPartieNotFound;
import Serdaigle.MIAGIE.service.JeuxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ChifoumiModel {
    private static final boolean ACTIVATE_TEST = true;
    private final ScheduledExecutorService scheduler;

    @Autowired
    private JeuxService jeuxService;

    private List<MouvementDTO> mouvements;
    private Map<Integer, String> optionsCoups = new HashMap<>();

    public ChifoumiModel() {
        this.mouvements = new ArrayList<>();

        // init options coups
        this.optionsCoups.put(1, CoupChifoumi.BAGUETTE.name());
        this.optionsCoups.put(2, CoupChifoumi.BALAI.name());
        this.optionsCoups.put(3, CoupChifoumi.CHAUDRON.name());

        this.scheduler = Executors.newScheduledThreadPool(1); // Crée le scheduler
    }

    private Runnable execUpdateMouvLog(PartieDTO partie) {
        return new Runnable() {
            @Override
            public void run() {
                try{
                    updateMouvements(partie,true);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
    }

    // cree objet partie en DB et lance la boucle
    public int lancerPartie(EleveDTO joueur, PropositionPartieDTO prop) throws PropositionPartieNotFound {
        Boolean isLanceur = false;
        if(prop.getJoueurSource() == joueur)
            isLanceur = true;
        PartieDTO partie = isLanceur ? jeuxService.getPartieByIdPropositionPartie(prop.getId()) : jeuxService.accepterPartie(prop);
        if(joueur.getMaison().getNomMaison().equals("SERPENTARD")){
            Runnable task = this.execUpdateMouvLog(partie);
            this.scheduler.scheduleAtFixedRate( task, 0, 2, TimeUnit.SECONDS);
        }
        int idGagnant = this.start(partie, joueur);
        if(idGagnant == joueur.getId()){
            System.out.println("Vous avez gagné ! + "+prop.getMise()+" points pour "+ joueur.getMaison().getNomMaison());
            jeuxService.setGagnantPartie(idGagnant, prop);
        }
        else {
            System.out.println("Vous avez perdu :/");
        }
        return idGagnant;
    }

    private Integer start(PartieDTO partieDTO, EleveDTO joueur){
        while (true){
            if(this.canPlay(partieDTO, joueur)){
                this.ajouterMouvement();
                System.out.println("Attente du mouvement de l'adveresaire...");
            }
            if(this.mouvements.size()%2 == 0){
                // les deux joueurs ont joué
                MouvementDTO m1 = this.mouvements.get(this.mouvements.size()-1);
                MouvementDTO m2 = this.mouvements.get(this.mouvements.size()-2);
                int idGagnantDuel = this.duel(m1,m2);
                boolean currentPlayerWon = joueur.getId() == idGagnantDuel;
                System.out.println(m1.getCoup()+ " contre "+m2.getCoup()+", "+ (currentPlayerWon ? "tu gagnes la manche !" : "tu as perdu la manche."));
            }
            Integer gagnant = this.checkPartieScore();

            // condition de fin de partie
            if(gagnant != null){
                return gagnant;
            }
        }
    }

    private boolean canPlay(PartieDTO partieDTO, EleveDTO joueur) {
        int currentPlayerPlaysCount = this.mouvements.stream().filter(m -> m.getIdEleve() == joueur.getId()).toList().size();
        int otherPlayerPlaysCount = this.mouvements.stream().filter(m -> m.getIdEleve() != joueur.getId()).toList().size();
        try{
            Thread.sleep(2000);
            this.updateMouvements(partieDTO,false);
        } catch (InterruptedException e){
            System.out.println("Thread sleep exception");
            e.printStackTrace();
        }catch (Exception e){
            System.out.println("other exception");
            e.printStackTrace();
        }
        if(ChifoumiModel.ACTIVATE_TEST){
        }
        return currentPlayerPlaysCount - otherPlayerPlaysCount <= 0;
    }

    private void updateMouvements(PartieDTO partie, boolean log){
        List<MouvementDTO> dbMouvements = jeuxService.getMouvementsByPartie(partie);
        for (MouvementDTO m : dbMouvements){
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
    public Integer duel(MouvementDTO m1, MouvementDTO m2){
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


    /**
     * 加入controller，请求值
     */
    public void ajouterMouvement(){
    //    Integer coup = Menu.showGenericMenu(this.optionsCoups, "Choisis un mouvement");
    //    Mouvement m = mDAO.createMouvement(this.joueur, this.partie, this.optionsCoups.get(coup));
    //    this.mouvements.add(m);
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