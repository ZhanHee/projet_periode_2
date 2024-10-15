package Serdaigle.MIAGIE.controller;

import Serdaigle.MIAGIE.dto.EvaluationDTO;
import Serdaigle.MIAGIE.exception.EleveNotFoundException;
import Serdaigle.MIAGIE.model.Evaluer;
import Serdaigle.MIAGIE.service.EcoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

/**
 * Endpoint /evaluer
 * Controleur pour gérer les évaluations des élèves.
 * Cette classe expose des endpoints REST pour créer et récupérer des évaluations.
 */
@RestController
@RequestMapping("/evaluer")
public class EvaluerController {
    /**
     *Le controleur fait appel au service principal de gestion de l'école  pour les opérations liées aux évaluations.
     */
    private final EcoleService ecoleService;

    /**
     * Constructeur du controleur Evaluer. Necessite un service ecoleService
     * @param ecoleService Service principal de gestion de l'école
     */
    public EvaluerController(EcoleService ecoleService) {
        this.ecoleService = ecoleService;
    }

    /**
     * GET /evaluer
     * Endpoint permettant de récupérer toutes les évaluations.
     *
     * @return Une liste d'objets {@link Evaluer} représentant toutes les évaluations.
     */
    @GetMapping
    public Iterable<Evaluer> getAllEvaluer() {
        return ecoleService.getAllEvaluer();
    }
    
    @GetMapping("/{idEleve}") 
    public ArrayList<EvaluationDTO> getAllEvaluationEleve(@PathVariable int idEleve) {
        Iterable<Evaluer> les_evaluations = ecoleService.getAllEvaluer();
        
        ArrayList<EvaluationDTO> les_evaluations_filtrer = new ArrayList<EvaluationDTO>();
        for(Evaluer la_evaluation : les_evaluations) {

            if (la_evaluation.getIdEleve().getId().equals(idEleve)) {
                les_evaluations_filtrer.add(
                new EvaluationDTO(la_evaluation.getId().getNomMatiere(), la_evaluation.getNote(), la_evaluation.getDateEval())
                );
            }
        }
        return les_evaluations_filtrer;
    }

    /**
     * POST /evaluer {idEleve, idProfesseur, nbPoints}
     * Endpoint permettant à un professeur d'évaluer un élève.
     *
     * @param body Un objet {@link Map} contenant les informations nécessaires pour créer une évaluation.
     *             Les clés doivent inclure "idEleve", "idProfesseur", et "nbPoints".
     * @return Une réponse HTTP avec le statut approprié :
     *         - 200 OK si l'évaluation a été créée avec succès,
     *         - 404 Not Found si l'élève correspondant n'a pas été trouvé.
     */
    @PostMapping
    public ResponseEntity<HttpStatus> createEvaluer(@RequestBody Map<String, Integer> body) {

        int idEleve = body.get("idEleve");
        int idProfesseur = body.get("idProfesseur");
        int nbPoints = body.get("nbPoints");

        try {
            Evaluer evaluer = ecoleService.addEvaluer(idEleve,idProfesseur,nbPoints);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (EleveNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


}
