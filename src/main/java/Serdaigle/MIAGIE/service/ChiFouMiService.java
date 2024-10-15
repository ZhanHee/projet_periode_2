package Serdaigle.MIAGIE.service;
import Serdaigle.MIAGIE.exception.*;
import Serdaigle.MIAGIE.model.Jeu;
import Serdaigle.MIAGIE.model.Partie;
import Serdaigle.MIAGIE.model.Propositionpartie;
import Serdaigle.MIAGIE.repository.JeuRepository;
import Serdaigle.MIAGIE.repository.PartieRepository;
import Serdaigle.MIAGIE.tooling.ToolingMethods;
import Serdaigle.MIAGIE.dto.EleveDTO;
import Serdaigle.MIAGIE.dto.PropositionPartieDTO;
import Serdaigle.MIAGIE.model.Eleve;
import Serdaigle.MIAGIE.repository.PropositionPartieRepository;
import Serdaigle.MIAGIE.repository.EleveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service permettant de gérer les parties de ChiFouMi
 * Le service service se base sur les repositories pour effectuer les opérations CRUD et autres requetes liées au métier.
 * Il manipule les données et les convertit en DTO pour les envoyer à l'API.
 */
@Service
public class ChiFouMiService {

    /**
     * Attribut eleveRepository
     */
    private final EleveRepository eleveRepository;

    /**
     * Attribut tooling
     */
    private final ToolingMethods tooling;

    /**
     * Attribut propositionPartieRepository
     */
    private final PropositionPartieRepository propositionPartieRepository;

    /**
     * Attribut partieRepository
     */
    private final PartieRepository partieRepository;

    /**
     * Attribut jeuRepository
     */
    private final JeuRepository jeuRepository;


    /**
     * Constructeur de la classe ChiFouMiService
     * @param eleveRepository repository des élèves
     * @param propositionPartieRepository repository des propositions de partie
     * @param partieRepository repository des parties
     * @param jeuRepository repository des jeux
     */
    public ChiFouMiService(EleveRepository eleveRepository, PropositionPartieRepository propositionPartieRepository,
                           PartieRepository partieRepository, JeuRepository jeuRepository) {
        this.eleveRepository = eleveRepository;
        this.propositionPartieRepository = propositionPartieRepository;
        this.tooling = new ToolingMethods();
        this.partieRepository = partieRepository;
        this.jeuRepository = jeuRepository;
    }


    /**
     * Permet de créer une proposition de partie
     * @param idEleveSource
     * @param idEleveDest
     * @param nomJeu
     * @param mise
     * @return PropositionPartieDTO
     * @throws ElevesDansLaMemeMaisonException
     * @throws PasAssezDePointsPourMiserException
     * @throws EleveNotFoundException
     * @throws JeuNotFoundException
     */
    public PropositionPartieDTO creerPropositionPartie(int idEleveSource, int idEleveDest, String nomJeu, int mise)throws ElevesDansLaMemeMaisonException, PasAssezDePointsPourMiserException, EleveNotFoundException, JeuNotFoundException, Exception{
        try {

            // Recuperer le jeu
            Jeu jeu = jeuRepository.findByNom(nomJeu).orElseThrow(() -> new JeuNotFoundException("Jeu not found :"+nomJeu));

            // Recuperer les élèves source et destination
            Eleve source = eleveRepository.findById(idEleveSource).orElseThrow(() -> new EleveNotFoundException("Eleve not found :"+idEleveSource));
            Eleve dest = eleveRepository.findById(idEleveDest).orElseThrow(() -> new EleveNotFoundException("Eleve not found :"+idEleveSource));


            // Creer les objets DTO
            EleveDTO sourceDto = this.tooling.convertEleveToDto(source);
            EleveDTO destDto = this.tooling.convertEleveToDto(dest);

            //Verifier qu'ils ne sont pas dans la même maison
            if(source.getNomMaison().equals(dest.getNomMaison())){
                throw new ElevesDansLaMemeMaisonException("Les deux élèves sont dans la même maison");
            }

            //Verifier qu'ils ont tous les deux assez de totalPoints pour miser
            if(source.getTotalPoints() < mise || dest.getTotalPoints() < mise){
                throw new PasAssezDePointsPourMiserException("Un des deux élèves n'a pas assez de points pour miser");
            }
            // A ce stade les eleves sont bien dans une maison opposée et ont assez de points pour miser
            // On crée la proposition de partie
            Propositionpartie propositionPartie = this.propositionPartieRepository.save(new Propositionpartie(source, dest, jeu, mise));
            return new PropositionPartieDTO(propositionPartie.getId(), sourceDto, destDto, jeu, mise);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    /**
     * Permet de récupérer toutes les propositions de partie reçues par un élève
     * @param id
     * @return Iterable PropositionPartieDTO
     */
    public Iterable<PropositionPartieDTO> getAllPropositionsPartieRecues(Integer id) {
        // Recuperer l'eleve destinataire
        Eleve eleve = eleveRepository.findById(id).orElseThrow(() -> new EleveNotFoundException("Eleve not found :"+id));
        // Recuperer les propositions de partie reçues
        Iterable<Propositionpartie> propRecues = this.propositionPartieRepository.getPropositionByJoueurCible(eleve);

       // Iterable propRecuesDto = this.tooling.convertPropositionPartieListToDtoList(propRecues);
        return this.tooling.convertPropositionPartieListToDtoList((List<Propositionpartie>) propRecues);
    }

    /**
     * Permet de récupérer toutes les propositions de partie proposées par un élève
     * @param id
     * @return Iterable PropositionPartieDTO
     */
    public Iterable<PropositionPartieDTO> getAllPropositionsPartieProposees(Integer id) {
        //Recuperer l'eleve source
        Eleve eleve = eleveRepository.findById(id).orElseThrow(() -> new EleveNotFoundException("Eleve not found :"+id));
        // Recuperer les propositions de partie proposées
        Iterable<Propositionpartie> propProposees = this.propositionPartieRepository.getPropositionByJoueurSource(eleve);
        return this.tooling.convertPropositionPartieListToDtoList((List<Propositionpartie>) propProposees);

    }

    /**
     * Permet d'accepter une proposition de partie.
     * Construit la proposition, verifie que l'eleve qui accepte est bien celui qui est la cible de la proposition
     * Verifie ensuite que les élèves ont toujours les points suffisants pour miser
     * @param idProposition
     * @param idEleve
     */
    public void accepterPartie(int idProposition, int idEleve) throws PropositionPartieNotFound, EleveNonAutoriseAAccepterPropositionException, PasAssezDePointsPourMiserException, EleveNotFoundException {
        // construire la proposition
        Propositionpartie propositionPartie = this.propositionPartieRepository.findById(idProposition).orElseThrow(() -> new PropositionPartieNotFound("Proposition not found :"+idProposition));
        // construire l'élève
        Eleve eleve = eleveRepository.findById(idEleve).orElseThrow(() -> new EleveNotFoundException("Eleve not found :"+idEleve));
        // verifier que l'eleve qui accepte est bien celui qui est la cible de la proposition
        if(propositionPartie.getEleveReceveur().getId() != eleve.getId()){
            throw new EleveNonAutoriseAAccepterPropositionException("L'élève n'est pas autorisé à accepter la proposition"+idProposition);
        }

        // verifier que les élèves ont toujours les points suffisants pour miser
        if(propositionPartie.getMise() > propositionPartie.getIdeleveLanceur().getTotalPoints() || propositionPartie.getMise() > propositionPartie.getEleveReceveur().getTotalPoints()){
            throw new PasAssezDePointsPourMiserException("Un des deux élèves n'a pas assez de points pour miser");
        }

        // A ce stade, l'élève est bien celui qui est la cible de la proposition et les deux élèves ont assez de points pour miser
        // On met à jour les points des deux élèves
        propositionPartie.getIdeleveLanceur().setTotalPoints(propositionPartie.getIdeleveLanceur().getTotalPoints() - propositionPartie.getMise());

        propositionPartie.getEleveReceveur().setTotalPoints(propositionPartie.getEleveReceveur().getTotalPoints() - propositionPartie.getMise());
        // On sauvegarde les points des deux élèves
        eleveRepository.save(propositionPartie.getIdeleveLanceur());
        eleveRepository.save(propositionPartie.getEleveReceveur());

        Partie partie = new Partie(propositionPartie);

        // On crée la partie
        this.partieRepository.save(partie);
    }
}
