package Serdaigle.MIAGIE.controller;

import Serdaigle.MIAGIE.model.Mouvement;
import Serdaigle.MIAGIE.model.Partie;
import Serdaigle.MIAGIE.service.ChiFouMiInterService;
import Serdaigle.MIAGIE.service.ChiFouMiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mouvements")
public class MouvementController {

    @Autowired
    private ChiFouMiInterService chiFouMiService;

    @PostMapping("/ajouter")
    public ResponseEntity<String> ajouterMouvement(
            @RequestParam int partieId,
            @RequestParam int joueurId,
            @RequestParam int coupId) {
        try {
            chiFouMiService.ajouterMouvement(partieId, joueurId, coupId);
            return ResponseEntity.ok("Mouvement ajouté avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'ajout du mouvement : " + e.getMessage());
        }
    }

    @GetMapping("/par-partie")
    public ResponseEntity<List<Mouvement>> getMouvementsByPartie(@RequestParam int partieId) {
        try {
            Partie partie = new Partie();
            partie.setId(partieId); // 假设有 setId 方法
            List<Mouvement> mouvements = chiFouMiService.findMouvementsByPartie(partie);
            return ResponseEntity.ok(mouvements);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}