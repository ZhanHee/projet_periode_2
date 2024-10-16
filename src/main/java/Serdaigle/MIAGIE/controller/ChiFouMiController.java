package Serdaigle.MIAGIE.controller;

import Serdaigle.MIAGIE.exception.EleveNotFoundException;
import Serdaigle.MIAGIE.exception.PartieNotFoundException;
import Serdaigle.MIAGIE.model.Eleve;
import Serdaigle.MIAGIE.model.Mouvement;
import Serdaigle.MIAGIE.model.Partie;
import Serdaigle.MIAGIE.model.Propositionpartie;
import Serdaigle.MIAGIE.service.ChiFouMiInterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chifoumi")
public class ChiFouMiController {

    @Autowired
    private ChiFouMiInterService chiFouMiInterService;


    @PostMapping("/start")
    public ResponseEntity<String> startGame(
            @RequestParam int partieId,
            @RequestParam int joueurId,
            @RequestParam int coupId) {
        try {
            Partie partie = chiFouMiInterService.findPartieByIdPartie(partieId);

            Eleve joueur = chiFouMiInterService.findJoueurByIdJoueur(joueurId);


            int gagnantId = chiFouMiInterService.lancerPartie(partie, joueur, coupId);
            if (gagnantId == joueurId) {
                return ResponseEntity.ok("Vous avez gagné la partie !");
            } else {
                return ResponseEntity.ok("Vous avez perdu la partie.");
            }
        } catch (PartieNotFoundException e) {
            return ResponseEntity.status(404).body("Partie non trouvée: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors du lancement de la partie: " + e.getMessage());
        }
    }

    @PostMapping("/mouvement")
    public ResponseEntity<String> ajouterMouvement(
            @RequestParam int partieId,
            @RequestParam int joueurId,
            @RequestParam int coupId) {
        try {
            chiFouMiInterService.ajouterMouvement(partieId, joueurId, coupId);
            return ResponseEntity.ok("Mouvement ajouté avec succès.");
        } catch (PartieNotFoundException e) {
            return ResponseEntity.status(404).body("Partie non trouvée: " + e.getMessage());
        } catch (EleveNotFoundException e) {
            return ResponseEntity.status(404).body("Joueur non trouvé: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("Coup non valide: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de l'ajout du mouvement: " + e.getMessage());
        }
    }

    @GetMapping("/mouvements/{partieId}")
    public ResponseEntity<List<Mouvement>> getMouvements(@PathVariable int partieId) {

        try {
            Partie partie = chiFouMiInterService.findById(partieId)
                    .orElseThrow(() -> new PartieNotFoundException("Partie non trouvée avec ID: " + partieId));

            List<Mouvement> mouvements = chiFouMiInterService.findMouvementsByPartie(partie);
            return ResponseEntity.ok(mouvements);
        } catch (PartieNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/end")
    public ResponseEntity<String> endGame(@RequestParam int partieId) {
        return ResponseEntity.ok("Partie terminée.");
    }
}