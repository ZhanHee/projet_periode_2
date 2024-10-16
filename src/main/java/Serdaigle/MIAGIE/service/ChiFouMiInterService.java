package Serdaigle.MIAGIE.service;

import Serdaigle.MIAGIE.exception.EleveNotFoundException;
import Serdaigle.MIAGIE.exception.PartieNotFoundException;
import Serdaigle.MIAGIE.model.Eleve;
import Serdaigle.MIAGIE.model.Mouvement;
import Serdaigle.MIAGIE.model.Partie;
import Serdaigle.MIAGIE.model.Propositionpartie;
import Serdaigle.MIAGIE.repository.EleveRepository;
import Serdaigle.MIAGIE.repository.MouvementRepository;
import Serdaigle.MIAGIE.repository.PartieRepository;
import Serdaigle.MIAGIE.repository.PropositionPartieRepository;
import Serdaigle.MIAGIE.tooling.ToolingMethods;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ChiFouMiInterService {
    @Autowired
    private PropositionPartieRepository propositionPartieRepository;
    @Autowired
    private PartieRepository partieRepository;
    @Autowired
    private MouvementRepository mouvementRepository;
    @Autowired
    private EleveRepository eleveRepository;
    private ScheduledExecutorService scheduler;

    @Autowired
    private EntityManager entityManager;
    private Map<Integer, String> optionsCoups = new HashMap<>();

    public ChiFouMiInterService() {
        optionsCoups.put(Integer.valueOf(1), "BAGUETTE");
        optionsCoups.put(Integer.valueOf(2), "BALAI");
        optionsCoups.put(Integer.valueOf(3), "CHAUDRON");
    }

    // Méthode pour lancer une partie
    public int lancerPartie(Propositionpartie prop, Eleve joueur, boolean isLanceur) throws PartieNotFoundException {
        Partie partie = isLanceur ? partieRepository.getPartieByIdProposition(prop.getId())
                : accepterPartie(prop);

        if ("SERPENTARD".equals(joueur.getMaison().getNomMaison())) {
            Runnable task = () -> {
                try {
                    updateMouvements(partie, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            scheduler.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);
        }

        int idGagnant = start(partie, joueur);
        if (idGagnant == joueur.getId()) {
            System.out.println("Vous avez gagné ! + " + prop.getMise() + " points pour " + joueur.getMaison().getNomMaison());
            prop.setGagnant(joueur);
            propositionPartieRepository.save(prop);
        } else {
            System.out.println("Vous avez perdu :/");
        }
        return idGagnant;
    }

    private Partie accepterPartie(Propositionpartie prop) {
        // Acceptation d'une proposition de partie
        Partie partie = new Partie(prop);
        return partieRepository.save(partie);
    }

    public int start(Partie partie, Eleve joueur) throws PartieNotFoundException {
        List<Mouvement> mouvements = new ArrayList<>();
        while (true) {
            if (canPlay(mouvements, joueur, partie)) {
                ajouterMouvement(partie.getId(), joueur.getId(), mouvements.get(mouvements.size() - 1).getId());
                System.out.println("Attente du mouvement de l'adversaire...");
            }

            if (mouvements.size() % 2 == 0) {
                Mouvement m1 = mouvements.get(mouvements.size() - 1);
                Mouvement m2 = mouvements.get(mouvements.size() - 2);
                int idGagnantDuel = duel(m1, m2);
                System.out.println(m1.getMouv() + " contre " + m2.getMouv() + ", " +
                        (joueur.getId() == idGagnantDuel ? "tu gagnes la manche !" : "tu as perdu la manche."));
            }

            Integer gagnant = checkPartieScore(mouvements);
            if (gagnant != null) {
                return gagnant;
            }
        }
    }

    private boolean canPlay(List<Mouvement> mouvements, Eleve joueur, Partie partie) {
        int currentPlayerPlaysCount = (int) mouvements.stream().filter(m -> Objects.equals(m.getIdEleve().getId(), joueur.getId())).count();
        int otherPlayerPlaysCount = (int) mouvements.stream().filter(m -> !Objects.equals(m.getIdEleve().getId(), joueur.getId())).count();

        try {
            Thread.sleep(2000);
            updateMouvements(partieRepository.findById(partie.getId()).orElseThrow(), false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return currentPlayerPlaysCount - otherPlayerPlaysCount <= 0;
    }

    private void updateMouvements(Partie partie, boolean log) {
        List<Mouvement> dbMouvements = mouvementRepository.findByPartie(partie);
        for (Mouvement m : dbMouvements) {
            if (!dbMouvements.contains(m)) {
                if (log) {
                    System.out.println(m + "...");
                }
                dbMouvements.add(m);
            }
        }
    }

//    private void ajouterMouvement(Partie partie, Eleve joueur, List<Mouvement> mouvements) {
//        Integer coup = Menu.showGenericMenu(optionsCoups, "Choisis un mouvement");
//        Mouvement m = new Mouvement(joueur, partie, optionsCoups.get(coup));
//        mouvementRepository.save(m);
//        mouvements.add(m);
//    }

    public void ajouterMouvement(int partieId, int joueurId, int coupId) throws PartieNotFoundException {
        Partie partie = partieRepository.findById(Integer.valueOf(partieId))
                .orElseThrow(() -> new PartieNotFoundException("Partie not found: " + partieId));

        Eleve joueur = eleveRepository.findById(Integer.valueOf(joueurId))
                .orElseThrow(() -> new EleveNotFoundException("Joueur not found: " + joueurId));

        if (!optionsCoups.containsKey(Optional.of(coupId))) {
            throw new IllegalArgumentException("Coup non valide: " + coupId);
        }

        Mouvement m = new Mouvement(joueur, optionsCoups.get(Optional.of(coupId)), partie);
        mouvementRepository.save(m);
    }

    public Integer duel(Mouvement m1, Mouvement m2) {
        CoupChifoumi coup1 = CoupChifoumi.valueOf(m1.getMouv());
        CoupChifoumi coup2 = CoupChifoumi.valueOf(m2.getMouv());

        if (coup1 == coup2) {
            return null; // Égalité
        }

        switch (coup1) {
            case BAGUETTE:
                return (coup2 == CoupChifoumi.BALAI) ? m1.getIdEleve().getId() : m2.getIdEleve().getId();
            case BALAI:
                return (coup2 == CoupChifoumi.CHAUDRON) ? m1.getIdEleve().getId() : m2.getIdEleve().getId();
            case CHAUDRON:
                return (coup2 == CoupChifoumi.BAGUETTE) ? m1.getIdEleve().getId() : m2.getIdEleve().getId();
            default:
                return null;
        }
    }

    private Integer checkPartieScore(List<Mouvement> mouvements) {
        Map<Integer, Integer> scores = new HashMap<>();
        for (int i = 0; i + 1 < mouvements.size(); i += 2) {
            Integer gagnantId = duel(mouvements.get(i), mouvements.get(i + 1));
            if (gagnantId != null) {
                scores.merge(gagnantId, 1, Integer::sum);
            }
        }
        for (Map.Entry<Integer, Integer> scoreEntry : scores.entrySet()) {
            if (scoreEntry.getValue() == 2) { // Le premier joueur à gagner deux manches remporte la partie
                return scoreEntry.getKey();
            }
        }
        return null;
    }

    public List<Mouvement> findMouvementsByPartie(Partie partie) {
        return mouvementRepository.findByPartie(partie);
    }

    public Optional<Partie> findById(int partieId) {
        return partieRepository.findById(partieId);
    }
}
