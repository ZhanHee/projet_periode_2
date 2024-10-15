package Serdaigle.MIAGIE.dto;

import Serdaigle.MIAGIE.model.Maison;

import java.util.HashMap;
import java.util.List;
/**
 * Objet métier d'une maison, construit pour etre consommé par une vue.
 * Une maison contient une liste d'élèves et un nombre de points total.
 * MaisonDTO
 */
public class MaisonDTO {
    /**
     *  Nom de la maison
     */
    private final String nomMaison;
    /**
     *Liste des élèves de la maison
     */
    private final List<EleveDTO> eleves;
    /**
     *  Nombre de points total de la maison
     */
    private final int nbPointTotal;
    /**
     * Constructeur de la classe MaisonDTO
     * @param nomMaison nom de la maison
     * @param nbPointTotal nombre de points total de la maison
     * @param eleves eleves de la maison
     */
    public MaisonDTO(String nomMaison,int nbPointTotal, List<EleveDTO> eleves) {
        this.nomMaison = nomMaison;
        this.eleves = eleves;
        this.nbPointTotal = nbPointTotal;
    }

    /**
     * Getter de l'attribut nomMaison
     * @return nomMaison
     */
    public String getNomMaison() {
        return nomMaison;
    }
    /**
     * Getter de l'attribut nbPointTotal
     * @return nbPointTotal
     */
    public int getNbPointTotal() {
        return nbPointTotal;
    }
    /**
     * Getter de l'attribut eleves
     * @return eleves
     */
    public List<EleveDTO> getEleves() {
        return eleves;
    }




}
