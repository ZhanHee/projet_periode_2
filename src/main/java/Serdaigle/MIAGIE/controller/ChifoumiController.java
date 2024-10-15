package Serdaigle.MIAGIE.controller;


import Serdaigle.MIAGIE.dto.EleveDTO;
import Serdaigle.MIAGIE.dto.PropositionPartieDTO;
import Serdaigle.MIAGIE.exception.PropositionPartieNotFound;
import Serdaigle.MIAGIE.jeux.ChifoumiModel;
import Serdaigle.MIAGIE.repository.EleveRepository;
import Serdaigle.MIAGIE.repository.PartieRepository;
import Serdaigle.MIAGIE.service.JeuxService;
import Serdaigle.MIAGIE.tooling.ToolingMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Chifoumi")
public class ChifoumiController {

    @Autowired
    private JeuxService jeuxService;
    @Autowired
    private ToolingMethods toolingMethods;
    @Autowired
    private ChifoumiModel chifoumiModel;
    @Autowired
    private EleveRepository eleveRepository;
    @Autowired
    private PartieRepository partieRepository;

    @PostMapping("/creerPartie/{idEleveLanceur}")
    public ResponseEntity<String> createPropositionPartie(@PathVariable("idEleveLanceur") int idLanceur, @RequestParam("idEleveReceveur") int idReceveur, @RequestParam("mise") int mise, @RequestParam("nomJeu" )String nomJeu){
        try {
            if (idLanceur <= 0 || idReceveur <= 0 || mise <= 0) {
                return ResponseEntity.badRequest().body("Invalid input parameters.");
            }
            PropositionPartieDTO newPropositionPartie = jeuxService.createPropositionPartie(idLanceur, idReceveur, mise, nomJeu);

            return ResponseEntity.status(HttpStatus.CREATED).body("Partie created with ID: " + newPropositionPartie.getId());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating Partie: " + e.getMessage());
        }
    }

    @GetMapping("/lancerPartie/{idEleve}")
    public ResponseEntity<Integer> lancerPartie(@PathVariable("idEleve")int idEleve, @RequestParam("propositionId") int propositionId) {
        try {
            PropositionPartieDTO propositionPartieDTO = jeuxService.getPropositionById(propositionId);
            EleveDTO lanceur;
            lanceur = toolingMethods.convertEleveToDto(eleveRepository.findByIdEleve(idEleve).get());
            int idGagnant = chifoumiModel.lancerPartie(lanceur, propositionPartieDTO);

            return ResponseEntity.ok(idGagnant);

        } catch (PropositionPartieNotFound e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



}
