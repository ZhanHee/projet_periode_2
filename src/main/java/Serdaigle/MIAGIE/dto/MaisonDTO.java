package Serdaigle.MIAGIE.dto;

import java.util.ArrayList;
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
    public MaisonDTO(String nomMaison, int nbPointTotal, List<EleveDTO> eleves) {
        this.nomMaison = nomMaison;
        this.nbPointTotal = nbPointTotal;
        this.eleves = new ArrayList<>();
        this.addEleves(eleves); // Utilise addEleves pour assurer la cohérence.
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

    public void addEleve(EleveDTO eleve) {
        if (eleves.contains(eleve)) {
            return;
        }

        this.eleves.add(eleve);
        eleve.setMaison(this); // Assure que l'élève référence cette maison.
    }

    public void addEleves(List<EleveDTO> eleves){
        for (EleveDTO e : eleves){
            this.addEleve(e);
        }
    }

    public void removeEleve(EleveDTO eleve) {
        this.eleves.remove(eleve);
        eleve.setMaison(null);
    }

    public int getTotalPoints(){
        int totalPoints = 0;
        for (EleveDTO e : this.getEleves()){
            totalPoints += e.getTotalPoints();
        }
        return totalPoints;
    }

}
