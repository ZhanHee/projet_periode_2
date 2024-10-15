package Serdaigle.MIAGIE.service;

import Serdaigle.MIAGIE.dto.EleveDTO;
import Serdaigle.MIAGIE.dto.MaisonDTO;
import Serdaigle.MIAGIE.dto.ProfesseurDTO;
import Serdaigle.MIAGIE.exception.EleveNotFoundException;
import Serdaigle.MIAGIE.exception.ProfesseurNotFoundException;
import Serdaigle.MIAGIE.model.*;
import Serdaigle.MIAGIE.repository.*;
import Serdaigle.MIAGIE.tooling.ToolingMethods;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Le service Ecole service se base sur les repositories pour effectuer les opérations CRUD et autres requetes liées au métier.
 * Il manipule les données et les convertit en DTO pour les envoyer à l'API.
 */
@Service
public class EcoleService {

    /**
     * attribut professeurRepository
     */
    private final ProfesseurRepository professeurRepository;

    /**
     * attribut eleveRepository
     */
    private final EleveRepository eleveRepository;

    /**
     * attribut matiereRepository
     */
    private final MatiereRepository matiereRepository;

    /**
     * attribut maisonRepository
     */
    private final MaisonRepository maisonRepository;

    /**
     * attribut evaluerRepository
     */
    private final EvaluerRepository evaluerRepository;

    private final ToolingMethods tooling;


    /**
     * Constructeur de la classe EcoleService
     * @param professeurRepository repository des professeurs
     * @param eleveRepository repository des élèves
     * @param matiereRepository repository des matières
     * @param maisonRepository repository des maisons
     * @param evaluerRepository repository des évaluations
     */
    public EcoleService(ProfesseurRepository professeurRepository, EleveRepository eleveRepository, MatiereRepository matiereRepository,
                        MaisonRepository maisonRepository, EvaluerRepository evaluerRepository) {

        this.professeurRepository = professeurRepository;
        this.eleveRepository = eleveRepository;
        this.matiereRepository = matiereRepository;
        this.maisonRepository = maisonRepository;
        this.tooling = new ToolingMethods();
        this.evaluerRepository = evaluerRepository;
    }

    // Appels des méthodes Professeur

    /**
     * Méthode pour obtenir tous les professeurs
     * @param filter filtre de recherche
     * @return
     */
    public Iterable<ProfesseurDTO> getAllProfesseurs(String filter) {
        //Iterable<ProfesseurDTO> professeurs = new ArrayList<>();
        if (filter == null || filter.isEmpty()) {
            List<Professeur> professeurdb = professeurRepository.findAll();
            return tooling.convertDBProfesseurListtoDTOList(professeurdb);
        }
        List<Professeur> professeurdb = professeurRepository.searchWithFilter(filter);
        return tooling.convertDBProfesseurListtoDTOList(professeurdb);

    }

    /**
     * Méthode pour obtenir un professeur par son ID
     * @param id id du professeur
     * @return
     */
    public ProfesseurDTO getProfesseurById(Integer id) {
        Optional<Professeur> professeur = professeurRepository.findById(id);
        if(!professeur.isPresent()) {
            throw new ProfesseurNotFoundException("Teacher not found :"+id);
        }
        return tooling.convertProfesseurToDto(professeur.get());
    }

    /**
     * Méthode pour ajouter un nouveau professeur
     * @param nom nom du professeur
     * @param prenom prénom du professeur
     * @param nomMatiere nom de la matière enseignée
     * @return Professeur
     */
    public Professeur saveProfesseur(String nom, String prenom, String nomMatiere) {
        Matiere matiere = matiereRepository.findByNomMatiere(nomMatiere);
        Professeur professeur = new Professeur(nom, prenom, matiere,false);
        return professeurRepository.save(professeur);
    }

    /**
     * Méthode pour supprimer un professeur
     * @param id id du professeur
     */
    public void deleteProfesseurById(Integer id) {
        professeurRepository.deleteById(id);
    }

    // Appels des méthodes Eleve

    /**
     * Méthode pour obtenir tous les élèves
     */


    public Iterable<EleveDTO> getAllEleves(String filter) {
        Iterable<EleveDTO> eleves = new ArrayList<>();
        if (filter == null || filter.isEmpty()) {
            List<Eleve> elevesdb = eleveRepository.findAll();
            return tooling.convertDBElevesListtoDTOList(elevesdb);
        }
        List<Eleve> elevesdb = eleveRepository.searchWithFilter(filter);
        return tooling.convertDBElevesListtoDTOList(elevesdb);
    }

    /**
     * Méthode pour obtenir un élève par son ID. Méthode utilisée en interne
     * @param id id de l'élève
     * @return Eleve
     */
    public Eleve getEleveById(int id) {
        Optional<Eleve> eleve = eleveRepository.findById(id);
        return eleve.orElseThrow(() -> new EleveNotFoundException("Student not found with id: " + id));
    }

    /**
     * Méthode pour obtenir un élève par son ID avec sa maison
     * @param idEleve id de l'élève
     * @return EleveDTO
     */
    public EleveDTO getEleveByIdWithMaison(int idEleve) {
        Optional<Eleve> eleve = eleveRepository.findById(idEleve);
        if(!eleve.isPresent()) {
            throw new EleveNotFoundException("Eleve not found with id: " + idEleve);
        }
        EleveDTO eleveDTO = new EleveDTO(eleve.get().getId(), eleve.get().getNom(),
                eleve.get().getPrenom(), eleve.get().getTotalPoints(), eleve.get().getNomMaison());

        return eleveDTO;
    }

    /**
     * Méthode pour ajouter un nouvel élève
     * @param nom nom
     * @param prenom prénom
     * @param nomMaison nom de la maison
     * @return Eleve
     */
    public Eleve addEleve(String nom, String prenom, String nomMaison) {
        Eleve eleve = new Eleve();
        eleve.setNom(nom);
        eleve.setPrenom(prenom);
        eleve.setTotalPoints(0);
        Maison m = maisonRepository.findByNomMaison(nomMaison);
        eleve.setMaison(m);
        return eleveRepository.save(eleve);
    }

    /**
     * Méthode pour supprimer un élève
     * @param id id de l'élève
     */
    public void deleteEleve(int id) {
        eleveRepository.deleteById(id);
    }

    /**
     * Méthode pour rechercher un élève par son nom ou son prénom avec un substring
     * @param ide nom ou prénom de l'élève
     * @return Iterable
     */

    public void deleteEleveByProfesseurId(int idp,int ide) {
        evaluerRepository.deleteByEleveEtMatiere(ide,getProfesseurById(idp).getNomMatiere());


    }
    public Iterable<EleveDTO> searchWithFilter(String nom){
        List<Eleve> eleves = this.eleveRepository.searchWithFilter(nom);
        return tooling.convertDBElevesListtoDTOList(eleves);
    }


    // Appels matières

    /**
     * Méthode pour obtenir toutes les matières
     * @return Iterable
     */
    public Iterable<Matiere> getAllMatieres() {
        return matiereRepository.findAll();
    }

    /**
     * Méthode pour obtenir une matière par son nom
     * @param nomMatiere nom de la matière
     * @return Matiere
     */
    public Matiere getMatiereByNomMatiere(String nomMatiere) {
        return matiereRepository.findByNomMatiere(nomMatiere);
    }

    // Methodes Maisons

    /**
     * Méthode pour obtenir toutes les maisons
     * @return Iterable
     */
    public Iterable<MaisonDTO> getAllMaisons() {
        Iterable<Maison>  maisons = maisonRepository.findAll();
        ArrayList<MaisonDTO> maisonDtoList = new ArrayList<>();
        for(Maison maison : maisons) {
            MaisonDTO mDto = this.convertMaisonToDto(this.getMaisonWithElevesByNomMaison(maison.getNomMaison()));
            maisonDtoList.add(mDto);
        }
        return maisonDtoList;
    }

    /**
     * Méthode pour obtenir une maison avec tous ses élèves
     * @param nomMaison nom de la maison
     * @return Maison
     */
    public Maison getMaisonWithElevesByNomMaison(String nomMaison) {
        Maison maison = maisonRepository.getMaisonWithElevesByNomMaison(nomMaison);
        int totalPoints = maisonRepository.countTotalPoints(nomMaison);
        MaisonDTO maisonDTO = new MaisonDTO(maison.getNomMaison(),totalPoints,tooling.convertDBElevesListtoDTOList(maison.getEleves()));
        return maison;

    }

    /**
     * Méthode pour convertir une maison en DTO
     * @param maison maison
     * @return Maison
     */
    public MaisonDTO convertMaisonToDto(Maison maison) {
        int totalPoints = maisonRepository.countTotalPoints(maison.getNomMaison());
        List<EleveDTO> eleveDtos = tooling.convertDBElevesListtoDTOList(maison.getEleves());
        MaisonDTO maisonDto = new MaisonDTO(maison.getNomMaison(),  totalPoints, eleveDtos);
        return maisonDto;
    }


    // Méthodes d'évaluation (Evaluer)

    /**
     * Méthode pour obtenir toutes les évaluations
     * @return Iterable Evaluer
     */
    public Iterable<Evaluer> getAllEvaluer() {
        return evaluerRepository.findAll();
    }

    /**
     * Méthode pour ajouter une évaluation
     * @param idEleve id de l'élève
     * @param idProfesseur id du professeur
     * @param nbPoints nombre de points
     * @return Evaluer
     */
    public Evaluer addEvaluer(int idEleve, int idProfesseur, int nbPoints) {

        Eleve eleve = this.getEleveById(idEleve);
        ProfesseurDTO professeur = this.getProfesseurById(idProfesseur);
        String nomMatiere = professeur.getNomMatiere();

        // Créer l'ID composite pour Evaluer
        EvaluerId evaluerId = new EvaluerId();
        evaluerId.setIdEleve(eleve.getId());  // Supposons que Eleve ait une méthode getId()
        evaluerId.setNomMatiere(nomMatiere);

        // Une évaluation contient : idEleve (json), nomMatière (à récup par professeur), nbPoints
        Evaluer evaluer = new Evaluer();
        evaluer.setId(evaluerId);
        evaluer.setIdEleve(eleve);
        evaluer.setNote(nbPoints);
        evaluer.setDateEval(LocalDate.from(LocalDateTime.now()));

        eleveRepository.addPoints(idEleve, nbPoints);
        return evaluerRepository.save(evaluer);
    }

    /**
     * Méthode pour récupérer les élèves de toutes les maisons sauf Serdaigle
     * @return Iterable
     */
    public Iterable<EleveDTO> getEleveFromOtherHouse() {
        ArrayList<EleveDTO> elevesPasASerdaigle = new ArrayList<>();
        // Rechercher les élèves dans les autres maisons
        Iterable<Eleve> eleves = eleveRepository.findElevesFromOtherHouses();
        for (Eleve e : eleves){
            EleveDTO edto = tooling.convertEleveToDto(e);
            elevesPasASerdaigle.add(edto);
        }
        return elevesPasASerdaigle;
    }

    /**
     * Méthode pour obtenir la maison gagnante
     * @return MaisonDTO
     */
    public MaisonDTO getMaisonGagnante() {
        Maison maison = maisonRepository.getMaisonGagnante();
        MaisonDTO maisonDTO = this.convertMaisonToDto(maison);
        return maisonDTO;
    }



}