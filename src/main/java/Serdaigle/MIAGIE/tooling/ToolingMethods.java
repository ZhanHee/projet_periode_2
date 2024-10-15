package Serdaigle.MIAGIE.tooling;

import Serdaigle.MIAGIE.dto.EleveDTO;
import Serdaigle.MIAGIE.dto.ProfesseurDTO;
import Serdaigle.MIAGIE.dto.PropositionPartieDTO;
import Serdaigle.MIAGIE.model.Eleve;
import Serdaigle.MIAGIE.model.Professeur;
import Serdaigle.MIAGIE.model.Propositionpartie;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de méthodes utilitaires pour manipuler les données
 */
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
                eleve.getNomMaison()
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
    public PropositionPartieDTO convertPropositionPartieToDto(Propositionpartie prop){
        return new PropositionPartieDTO(prop.getId(),this.convertEleveToDto(prop.getEleveLanceur()), this.convertEleveToDto(prop.getEleveReceveur()), prop.getJeu(), prop.getMise());
    }

    /**
     * Convertit une liste de propositions de partie en DTO
     * @param propRecues liste de propositions de partie reçues
     * @return ArrayList
     */
    public ArrayList<PropositionPartieDTO> convertPropositionPartieListToDtoList(List<Propositionpartie> propRecues){
        ArrayList<PropositionPartieDTO> propRecuesDto = new ArrayList<>();
        for (Propositionpartie p : propRecues){
            PropositionPartieDTO pdto = new PropositionPartieDTO(p.getId(),this.convertEleveToDto(p.getEleveLanceur()), this.convertEleveToDto(p.getEleveReceveur()), p.getJeu(), p.getMise());
            propRecuesDto.add(pdto);
        }
        return propRecuesDto;
    }

}

