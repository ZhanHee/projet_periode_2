package Serdaigle.MIAGIE.controller;
import Serdaigle.MIAGIE.dto.EleveDTO;
import Serdaigle.MIAGIE.exception.EleveNotFoundException;
import Serdaigle.MIAGIE.exception.ElevesDansLaMemeMaisonException;
import Serdaigle.MIAGIE.exception.PasAssezDePointsPourMiserException;
import Serdaigle.MIAGIE.exception.PropositionPartieNotFound;
import Serdaigle.MIAGIE.model.Eleve;
import Serdaigle.MIAGIE.service.ChiFouMiInterService;
import Serdaigle.MIAGIE.service.ChiFouMiService;
import Serdaigle.MIAGIE.service.EcoleService;
import Serdaigle.MIAGIE.dto.PropositionPartieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * Endpoint /eleve
 * Controleur pour gérer les évaluations des élèves.
 * Cette classe expose des endpoints REST pour récupérer des informations ou effectuer des opérations sur les élèves
 */
@RestController
@RequestMapping("/eleve")
public class EleveController {


    /**
     * Le controleur eleve fait appel à un service ecoleService pour effectuer les opérations métier sur les élèves
     */
    @Autowired
    private  EcoleService ecoleService;


    /**
     * Le controleur fait appel à un service chiFouMiService pour effectuer les opérations métier sur les parties
     */
    @Autowired
    private  ChiFouMiService chiFouMiService;

    @Autowired
    private ChiFouMiInterService chiFouMiInterService;

    /**
     * GET /eleve
     * Récupère tous les élèves de l'école.
     * Peut être filtré avec un paramètre fourni dans l'URL : ?filter=...
     *
     * @param filter Paramètre de filtrage optionnel.
     * @return Une liste d'objets {@link EleveDTO} représentant tous les élèves.
     */
    @GetMapping
    public Iterable<EleveDTO> getAllEleves(@RequestParam(name = "filter", required = false) String filter) {
        Iterable<EleveDTO> eleves = ecoleService.getAllEleves(filter);
        return ecoleService.getAllEleves(filter);
    }



    /**
     * GET /eleve/{id}
     * Endpoint permettant de récupérer un élève à partir de son ID.
     *
     * @param id L'ID de l'élève à récupérer.
     * @return Une réponse contenant l'objet {@link EleveDTO} de l'élève, ou 404 Not Found si l'élève n'existe pas.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EleveDTO> getEleve(@PathVariable Integer id) {
        try {
            EleveDTO eleve = ecoleService.getEleveByIdWithMaison(id);
            return new ResponseEntity<>(eleve, HttpStatus.OK);
        } catch (EleveNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET /eleve/fromOtherHouses
     * Endpoint pour obtenir les élèves qui ne sont pas à Serdaigle.
     * Cet endpoint permet à un élève de Serdaigle de consulter uniquement ses adversaires potentiels lors d'un jeu.
     * @return Une liste d'objets {@link EleveDTO} représentant les élèves d'autres maisons.
     */
    @GetMapping("/fromOtherHouses")
    public Iterable<EleveDTO> getFromOtherHouses() {
        return ecoleService.getEleveFromOtherHouse();
    }

    /*
     * Endpoint pour ajouter un nouvel élève.
     * Prend un body de type Map<String, String> avec les clés nom, prenom et nomMaison
     */
    @PostMapping
    public Eleve createEleve(@RequestBody Map<String, String> body) {
        String nom = body.get("nom");
        String prenom = body.get("prenom");
        String nomMaison = body.get("nomMaison");
        return ecoleService.addEleve(nom,prenom,nomMaison);
    }

    /**
     * POST /eleve/propositionPartie
     * Endpoint pour créer une proposition de partie.
     * Prend un body de type Map String, Integer avec les clés idJoueurSource, idJoueurCible et mise.
     *
     * @param body Un objet contenant les informations de la proposition.
     *             Les clés doivent inclure :
     *             - idJoueurSource : l'ID du joueur qui propose la partie.
     *             - idJoueurCible : l'ID du joueur cible.
     *             - mise : le montant de points misés pour la partie.
     * @return Une réponse indiquant le statut de la création de la proposition :
     *         - 201 Created si la proposition a été créée avec succès.
     *         - 400 Bad Request si les données fournies ne peuvent pas être traitées (par exemple, mauvaise format des ID).
     *         - 409 Conflict si les joueurs sont dans la même maison ou s'il n'y a pas assez de points pour miser.
     *         - 500 Internal Server Error pour toute autre exception non gérée.
     * Exceptions gérées :
     * - NumberFormatException : Si l'un des paramètres dans le corps de la requête ne peut pas être converti en entier.
     * - ElevesDansLaMemeMaisonException : Si les deux joueurs sont dans la même maison, empêchant la création de la proposition.
     * - PasAssezDePointsPourMiserException : Si le joueur source n'a pas suffisamment de points pour effectuer la mise.
     * - PropositionPartieNotFound : Si la proposition n'existe pas (dans d'autres méthodes, mais pertinent pour la gestion globale).
     * - EleveNotFoundException : Si l'un des élèves spécifiés dans la proposition n'existe pas.
     * - Exception : Toute autre exception générale, pour gérer les erreurs inattendues.
     */
    @PostMapping("/propositionPartie"  )
    public ResponseEntity creerPropositionPartie(@RequestBody Map<String, Integer> body) {
        try{
            int idJoueurSource = body.get("idJoueurSource");
            int idJoueurCible = body.get("idJoueurCible");
            int mise = body.get("mise");
            PropositionPartieDTO prop =  this.chiFouMiService.creerPropositionPartie( idJoueurSource,  idJoueurCible, "chifoumi",  mise);
            return new ResponseEntity<>(HttpStatus.CREATED);

        }catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }

        catch(ElevesDansLaMemeMaisonException e2){
            return new ResponseEntity<>( e2.getMessage(),HttpStatus.CONFLICT);
            // 404 Not Found
        }
        catch(PasAssezDePointsPourMiserException e3){
            return new ResponseEntity<>(e3.getMessage(), HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
                //return new ResponseEntity<PropositionPartieDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * DELETE /eleve/{id}
     * Endpoint pour supprimer un élève.
     *
     * @param id L'ID de l'élève à supprimer.
     * @throws EleveNotFoundException Si l'élève avec l'ID spécifié n'existe pas.
     */
    @DeleteMapping("/{id}")
    /*
     * Endpoint pour supprimer un élève
     */
    public void deleteEleve(@PathVariable int id) {
        this.ecoleService.deleteEleve(id);
    }


    /**
     * GET /eleve/voirPropositionsPartieRecues/{id}
     * Permet de récupérer les propositions de partie reçues par l'ID du joueur passé en paramètres qui n'ont pas encore été acceptées.
     *
     * @param id L'ID du joueur pour lequel on souhaite voir les propositions reçues.
     * @return Une liste d'objets {@link PropositionPartieDTO} représentant les propositions de partie reçues.
     * @throws EleveNotFoundException Si l'élève avec l'ID spécifié n'existe pas.
     */
    @GetMapping("/voirPropositionsPartieRecues/{id}")
    public Iterable<PropositionPartieDTO> voirPropositionsPartieRecues(@PathVariable Integer id){
        return this.chiFouMiService.getAllPropositionsPartieRecues(id);
    }

    /**
     * GET /eleve/voirPropositionsPartiesProposees/{id}
     * Endpoint permettant de récupérer les parties proposées par l'ID du joueur passé en paramètres qui n'ont pas encore abouti à une partie.
     *
     * @param id L'ID du joueur pour lequel on souhaite voir les propositions proposées.
     * @return Une liste d'objets {@link PropositionPartieDTO} représentant les propositions de partie en attente.
     * @throws EleveNotFoundException Si l'élève avec l'ID spécifié n'existe pas.
     */
    @GetMapping("/voirPropositionsPartiesProposees/{id}")
    public Iterable<PropositionPartieDTO> voirPropositionsPartieEnAttente(@PathVariable Integer id){
        return this.chiFouMiService.getAllPropositionsPartieProposees(id);
    }

    /**
     * POST /eleve/accepterUnePartie
     * Endpoint pour accepter une partie.
     * Prend un body de type Map String, Integer avec les clés idProposition et idEleveCible.
     *
     * @param body Un objet contenant les informations nécessaires pour accepter une partie.
     *             Les clés doivent inclure :
     *             - idProposition : l'ID de la proposition de partie à accepter.
     *             - idEleveCible : l'ID de l'élève cible.
     * @return Une réponse indiquant le statut de l'acceptation :
     *         - 201 Created si la partie a été acceptée avec succès.
     *         - 404 Not Found si la proposition ou l'élève cible n'existe pas.
     *         - 500 Internal Server Error pour toute autre exception non gérée.
     * Exceptions gérées :
     * - PropositionPartieNotFound : Si la proposition à accepter n'existe pas.
     * - EleveNotFoundException : Si l'élève cible spécifié n'existe pas.
     * - Exception : Toute autre exception générale pour gérer les erreurs inattendues.
     */
    @PostMapping("/accepterUnePartie")
    public ResponseEntity  accepterUnePartie(@RequestBody Map<String, Integer> body){
        int idProposition = body.get("idProposition");
        int idEleve = body.get("idEleveCible");
        try {
            this.chiFouMiService.accepterPartie(idProposition, idEleve);
            return ResponseEntity.status(HttpStatus.CREATED).body("Partie acceptée");

        }catch (PropositionPartieNotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch(EleveNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }





}