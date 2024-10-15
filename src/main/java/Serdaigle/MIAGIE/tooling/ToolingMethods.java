package Serdaigle.MIAGIE.tooling;

import Serdaigle.MIAGIE.dto.*;
import Serdaigle.MIAGIE.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de méthodes utilitaires pour manipuler les données
 */
@Service
public  class  ToolingMethods {

    /**
     * Constructeur vide de la classe ToolingMethods
     */
    public ToolingMethods() {
    }

    /**
     * Convertit un modèle Eleve BDD en modèle DTO
     * @param eleve élève à convertir
     * @return EleveDTO
     */
    public EleveDTO convertEleveToDto(Eleve eleve) {
        return new EleveDTO(
                eleve.getId(),
                eleve.getNom(),
                eleve.getPrenom(),
                eleve.getTotalPoints(),
                this.convertMaisonToDto(eleve.getMaison())
        );
    }

    /**
     * Convertit une liste de modeles d'eleves bdd en une liste DTO
     * @param eleves liste d'eleves
     * @return ArrayList
     */
    public ArrayList<EleveDTO> convertDBElevesListtoDTOList(List<Eleve> eleves){
        ArrayList<EleveDTO> elevesDto= new ArrayList<>();
        for (Eleve e : eleves){
            elevesDto.add(this.convertEleveToDto(e));
        }
        return elevesDto;
    }

    /**
     * Convertit un modèle Professeur BDD en modèle DTO
     * @param professeur professeur à convertir
     * @return ProfesseurDTO
     */
    public ProfesseurDTO convertProfesseurToDto(Professeur professeur) {
        return new ProfesseurDTO(
                professeur.getId(),
                professeur.getNom(),
                professeur.getPrenom(),
                professeur.getNomMatiere().getNomMatiere()
        );
    }

    /**
     * Convertit une liste de modeles de professeurs bdd en une liste DTO
     * @param professeurs liste de professeurs
     * @return ArrayList
     */
    public ArrayList<ProfesseurDTO> convertDBProfesseurListtoDTOList(List<Professeur> professeurs){
        ArrayList<ProfesseurDTO> professeurDto= new ArrayList<>();
        for (Professeur p : professeurs){
            professeurDto.add(this.convertProfesseurToDto(p));
        }
        return professeurDto;
    }

    /**
     * Convertit une proposition de partie en DTO
     * @param prop proposition de partie
     * @return PropositionPartieDTO
     */
    public PropositionPartieDTO convertPropositionPartieToDto(PropositionPartie prop){
        return new PropositionPartieDTO(prop.getId(),this.convertEleveToDto(prop.getEleveLanceur()), this.convertEleveToDto(prop.getEleveReceveur()), prop.getJeu(), prop.getMise());
    }

    /**
     * Convertit une liste de propositions de partie en DTO
     * @param propRecues liste de propositions de partie reçues
     * @return ArrayList
     */
    public ArrayList<PropositionPartieDTO> convertPropositionPartieListToDtoList(List<PropositionPartie> propRecues){
        ArrayList<PropositionPartieDTO> propRecuesDto = new ArrayList<>();
        for (PropositionPartie p : propRecues){
            PropositionPartieDTO pdto = new PropositionPartieDTO(p.getId(),this.convertEleveToDto(p.getEleveLanceur()), this.convertEleveToDto(p.getEleveReceveur()), p.getJeu(), p.getMise());
            propRecuesDto.add(pdto);
        }
        return propRecuesDto;
    }

    public PartieDTO convertPartieToDto(Partie partie) {
        int idPartie = partie.getId();
        PropositionPartieDTO propositionPartieDto = this.convertPropositionPartieToDto(partie.getPropositionPartie());
        return new PartieDTO(idPartie, propositionPartieDto);
    }

    public MouvementDTO convertMouvementToDto(Mouvement mouvement) {
        return new MouvementDTO(
                mouvement.getId(),
                mouvement.getCoup(),
                mouvement.getTimestampp(),
                mouvement.getIdEleve().getId()
        );
    }

    public MaisonDTO convertMaisonToDto(Maison maison){
        return new MaisonDTO(
                maison.getNomMaison(),
                maison.getTotalPoints(),
                this.convertElevesToDto(maison.getEleves())
        );
    }

    private List<EleveDTO> convertElevesToDto(List<Eleve> eleves) {
        ArrayList<EleveDTO> elevesDto= new ArrayList<>();
        for (Eleve e : eleves){
            elevesDto.add(this.convertEleveToDto(e));
        }
        return elevesDto;
    }


}

