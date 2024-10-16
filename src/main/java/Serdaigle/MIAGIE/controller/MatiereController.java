package Serdaigle.MIAGIE.controller;

import Serdaigle.MIAGIE.model.Matiere;
import Serdaigle.MIAGIE.service.EcoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour gérer les matières dans l'école.
 */
@RestController
@RequestMapping("/matiere")
public class MatiereController {

    /**
     * Service principal de l'école pour les opérations métier liées aux matières.
     */
    @Autowired
    private EcoleService ecoleService;

    /**
     * GET /matiere
     * Endpoint pour récupérer toutes les matières.
     *
     * @return Une liste d'objets {@link Matiere} représentant toutes les matières.
     */
    @GetMapping
    public Iterable<Matiere> getAllMatieres() {
        return ecoleService.getAllMatieres();
    }

    /**
     * GET /matiere/{nomMatiere}
     * Endpoint pour trouver une matière par son nom.
     *
     * @param nomMatiere Le nom de la matière à rechercher.
     * @return Une réponse contenant un objet {@link Matiere} représentant la matière,
     *         ou une réponse 404 Not Found si la matière n'existe pas.
     */
    @GetMapping("/{nomMatiere}")
    public ResponseEntity<Matiere> getMatiereByNom(@PathVariable String nomMatiere) {
        Matiere matiere = ecoleService.getMatiereByNomMatiere(nomMatiere);
        return ResponseEntity.ok(matiere);

    }

}