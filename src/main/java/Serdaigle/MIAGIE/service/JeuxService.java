package Serdaigle.MIAGIE.service;

import Serdaigle.MIAGIE.dto.EleveDTO;
import Serdaigle.MIAGIE.dto.MouvementDTO;
import Serdaigle.MIAGIE.dto.PartieDTO;
import Serdaigle.MIAGIE.dto.PropositionPartieDTO;
import Serdaigle.MIAGIE.exception.EleveNotFoundException;
import Serdaigle.MIAGIE.exception.PartieNotFoundException;
import Serdaigle.MIAGIE.exception.PropositionPartieNotFound;
import Serdaigle.MIAGIE.model.Eleve;
import Serdaigle.MIAGIE.model.Partie;
import Serdaigle.MIAGIE.model.PropositionPartie;
import Serdaigle.MIAGIE.repository.*;
import Serdaigle.MIAGIE.tooling.ToolingMethods;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JeuxService {

    private EleveRepository eleveRepository;
    private JeuRepository jeuRepository;
    private PartieRepository partieRepository;
    private PropositionPartieRepository propositionPartieRepository;
    private MatiereRepository matiereRepository;
    private MouvementRepository mouvementRepository;
    private final ToolingMethods tooling;

    public JeuxService (EleveRepository eleveRepository, JeuRepository jeuRepository, PartieRepository partieRepository,
                        PropositionPartieRepository propositionPartieRespository, MatiereRepository matiereRepository){
        this.eleveRepository = eleveRepository;
        this.partieRepository = partieRepository;
        this.propositionPartieRepository = propositionPartieRespository;
        this.jeuRepository = jeuRepository;
        this.matiereRepository = matiereRepository;
        this.tooling = new ToolingMethods();;
    }

    public Iterable<Partie> getAllParties() {
        return partieRepository.getAllParties();
    }

    public PartieDTO getPartieById(int idPartie) throws PartieNotFoundException {
        Optional<Partie> partie = partieRepository.findById(idPartie);
        if(!partie.isPresent()) {
            throw new PartieNotFoundException("Partie not found :"+idPartie);
        }
        return tooling.convertPartieToDto(partie.get());
    }

    public PartieDTO getPartieByIdPropositionPartie(Integer idPropositionPartie) throws PropositionPartieNotFound {
        Optional<Partie> partie = partieRepository.findByIdPropositionPartie(idPropositionPartie);
        if(!partie.isPresent()) {
            throw new PropositionPartieNotFound("Partie not found :"+ idPropositionPartie);
        }
        return tooling.convertPartieToDto(partie.get());
    }


    public PartieDTO accepterPartie(PropositionPartieDTO prop) {
        partieRepository.insertPartie(prop.getId());

        Optional<Partie> partie = partieRepository.findByIdPropositionPartie(prop.getId());

        Partie foundPartie = partie.orElseThrow(() -> new RuntimeException("Partie not found for idProposition: " + prop.getId()));

        return tooling.convertPartieToDto(foundPartie);
    }

    public void setGagnantPartie(int idGagnant, PropositionPartieDTO prop) {
        propositionPartieRepository.setGagnantPartie(idGagnant, prop.getId());
    }

    public List<MouvementDTO> getMouvementsByPartie(PartieDTO partie) {
        return mouvementRepository.findMouvementsByPartieId(partie.getPropositionPartie().getId());
    }

    public EleveDTO getEleveVainqueur(Integer idGagnant) {
        Optional<Eleve> optionalEleve = eleveRepository.findByIdEleve(idGagnant);

        if (optionalEleve.isPresent()) {
            return tooling.convertEleveToDto(optionalEleve.get());
        } else {
            throw new EleveNotFoundException("Eleve not found with id: " + idGagnant);
        }
    }

    public PropositionPartieDTO getPropositionById(int propositionId) throws PropositionPartieNotFound {
        Optional<PropositionPartie> optionalPropositionPartie = propositionPartieRepository.findById(propositionId);

        if (optionalPropositionPartie.isPresent()) {
            return tooling.convertPropositionPartieToDto(optionalPropositionPartie.get());
        } else {
            throw new PropositionPartieNotFound("PropositionPartie not found with id: " + propositionId);
        }
    }

    public PropositionPartieDTO createPropositionPartie(int idLanceur, int idReceveur, int mise, String nomJeu) {
        PropositionPartie propositionPartie = new PropositionPartie();

        Eleve lanceur = new Eleve();
        lanceur.setId(idLanceur);
        propositionPartie.setIdEleveLanceur(lanceur);

        Eleve receveur = new Eleve();
        receveur.setId(idReceveur);
        propositionPartie.setIdEleveReceveur(receveur);

        propositionPartie.setMise(mise);

        propositionPartie.setJeu(nomJeu);

        PropositionPartie savedPropositionPartie = propositionPartieRepository.save(propositionPartie);

        return tooling.convertPropositionPartieToDto(savedPropositionPartie);
    }
}
