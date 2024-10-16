package Serdaigle.MIAGIE.controller;

import Serdaigle.MIAGIE.dto.MaisonDTO;
import Serdaigle.MIAGIE.exception.MaisonNotFoundException;
import Serdaigle.MIAGIE.model.Maison;
import Serdaigle.MIAGIE.model.Matiere;
import Serdaigle.MIAGIE.service.EcoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * Contrôleur REST pour gérer les maisons dans l'école.
 */
@RestController
@RequestMapping("/maison")
public class MaisonController {
    /**
     * Service principal de l'école pour les opérations métier liées aux maisons.
     */
    @Autowired
    private EcoleService ecoleService;

    /**
     * GET /maison
     * Endpoint pour récupérer toutes les maisons.
     *
     * @return Une liste d'objets {@link MaisonDTO} représentant toutes les maisons.
     */
    @GetMapping
    public Iterable<MaisonDTO> getAllMaisons() {
        return ecoleService.getAllMaisons();
    }

    /**
     * GET /maison/{nomMaison}
     * Endpoint pour trouver une maison par son nom.
     *
     * @param nomMaison Le nom de la maison à rechercher.
     * @return Une réponse contenant un objet {@link MaisonDTO} représentant la maison,
     *         ou une réponse 404 Not Found si la maison n'existe pas.
     * @throws Exception Si la maison avec le nom spécifié n'existe pas.
     */
    @GetMapping("/{nomMaison}")
    public ResponseEntity<MaisonDTO> getMaisonByNom(@PathVariable String nomMaison) throws Exception {
        try {
            Maison maison = ecoleService.getMaisonWithElevesByNomMaison(nomMaison);
            MaisonDTO maisonDto = ecoleService.convertMaisonToDto(maison);
            return ResponseEntity.ok(maisonDto);
        } catch (Exception e) { // Ici la MaisonNotFoundException ne fonctionnait pas
            return ResponseEntity.notFound().build();
        }

    }



    /**
     * GET /maison/nomMaisonGagnante
     * Endpoint pour récupérer la maison gagnante.
     *
     * @return Une réponse contenant un objet {@link MaisonDTO} représentant la maison gagnante.
     */
    @GetMapping("/nomMaisonGagnante")
    public ResponseEntity<MaisonDTO> getMaisonGagnante() {
        MaisonDTO nomMaisonGagnante = this.ecoleService.getMaisonGagnante();
        return ResponseEntity.ok(nomMaisonGagnante);
    }




}