package Serdaigle.MIAGIE.controller;

import Serdaigle.MIAGIE.dto.ProfesseurDTO;
import Serdaigle.MIAGIE.model.Professeur;
import Serdaigle.MIAGIE.service.EcoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
/**
 * Contrôleur REST pour gérer les opérations liées aux professeurs dans l'école.
 */
@RestController
@RequestMapping("/professeur")
public class ProfesseurController {

    /**
     * Service principal de l'école pour les opérations métier liées aux professeurs.
     */
    @Autowired
    private EcoleService ecoleService;

    /**
     * GET /professeur
     * Endpoint pour récupérer tous les professeurs.
     * @param filter Un filtre pour filtrer sur un substring
     * @return Une liste d'objets {@link ProfesseurDTO} représentant tous les professeurs.
     */
    @GetMapping
    public Iterable<ProfesseurDTO> getAllProfesseurs(@RequestParam(name = "filter", required = false) String filter) {
        return ecoleService.getAllProfesseurs(filter);
    }

    /**
     * GET /professeur/{id}
     * Endpoint pour trouver un professeur par son ID.
     *
     * @param id L'ID du professeur à rechercher.
     * @return Une réponse contenant un objet {@link ProfesseurDTO} représentant le professeur,
     *         ou une réponse 404 Not Found si le professeur n'existe pas.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProfesseurDTO> getProfesseurById(@PathVariable Integer id) {
        ProfesseurDTO professeur = ecoleService.getProfesseurById(id);
        return new ResponseEntity<>(professeur, HttpStatus.OK);

    }

    /**
     * POST /professeur {nom, prenom, nomMatiere}
     * Endpoint pour créer un professeur.
     *
     * @param body Un objet {@link Map} contenant les informations nécessaires pour créer un professeur.
     *             Les clés doivent inclure "nom", "prenom", et "nomMatiere".
     * @return Un objet {@link Professeur} représentant le professeur créé.
     */
    @PostMapping
    public Professeur createProfesseur(@RequestBody Map<String, String> body) {

        String nom = body.get("nom");
        String prenom = body.get("prenom");
        String nomMatiere = body.get("nomMatiere");
        return ecoleService.saveProfesseur(nom,prenom,nomMatiere);
    }



    /**
     * DELETE /professeur/{id}
     * Endpoint pour supprimer un professeur.
     *
     * @param id L'ID du professeur à supprimer.
     * @return Une réponse HTTP avec le statut approprié :
     *         - 204 No Content si le professeur a été supprimé avec succès,
     *         - 404 Not Found si le professeur correspondant n'a pas été trouvé.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfesseur(@PathVariable Integer id) {
        ecoleService.deleteProfesseurById(id);
        return ResponseEntity.noContent().build();
    }


}
