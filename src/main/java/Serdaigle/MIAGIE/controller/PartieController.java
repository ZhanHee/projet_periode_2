package Serdaigle.MIAGIE.controller;

import Serdaigle.MIAGIE.dto.PartieDTO;
import Serdaigle.MIAGIE.dto.PropositionPartieDTO;
import Serdaigle.MIAGIE.exception.PartieNotFoundException;
import Serdaigle.MIAGIE.exception.PropositionPartieNotFound;
import Serdaigle.MIAGIE.model.Matiere;
import Serdaigle.MIAGIE.model.Partie;
import Serdaigle.MIAGIE.model.PropositionPartie;
import Serdaigle.MIAGIE.service.EcoleService;
import Serdaigle.MIAGIE.service.JeuxService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur REST pour gérer les matières dans l'école.
 */
@RestController
@RequestMapping("/partie")
public class PartieController {

    /**
     * Service principal de l'école pour les opérations métier liées aux matières.
     */
    private final JeuxService jeuxService;

    public PartieController(JeuxService jeuxService) {
        this.jeuxService = jeuxService;
    }


    @GetMapping
    public Iterable<Partie> getAllPartie() {
        return jeuxService.getAllParties();
    }


    @GetMapping("/{idPartie}")
    public ResponseEntity<PartieDTO> getPartieById(@PathVariable Integer idPartie) throws PartieNotFoundException {
        PartieDTO partie = jeuxService.getPartieById(idPartie);
        return new ResponseEntity<>(partie, HttpStatus.OK);
    }

    @GetMapping("/{idPropositionPartie}")
    public ResponseEntity<PartieDTO> getPartieByIdPropositionPartie(@PathVariable Integer idPropositionPartie) throws PropositionPartieNotFound {
        PartieDTO partieDTO = jeuxService.getPartieByIdPropositionPartie(idPropositionPartie);
        return new ResponseEntity<>(partieDTO, HttpStatus.OK);
    }

}