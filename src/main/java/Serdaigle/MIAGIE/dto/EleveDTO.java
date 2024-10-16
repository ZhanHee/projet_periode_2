package Serdaigle.MIAGIE.dto;
/**
 * Classe représentant un élève métier de l'application
 */
public class EleveDTO extends PersonneDTO{
    /**
     * Nombre de points de l'élève
     */
    private final Integer totalPoints;
    /**
     * Nom de la maison de l'élève
     */
    private final String nomMaison;

    /**
     * Constructeur de la classe EleveDTO
     * @param idEleve identifiant de l'élève
     * @param nom nom de l'élève
     * @param prenom prenom de l'élève
     * @param totalPoints nombre de points de l'élève
     * @param nomMaison nom de la maison de l'élève
     */
    public EleveDTO(Integer idEleve, String nom, String prenom,  Integer totalPoints, String nomMaison) {
        super(idEleve, nom, prenom);
        this.totalPoints = totalPoints;
        this.nomMaison = nomMaison;
    }
    /**
     * Getter de l'attribut nomMaison
     * @return nom de la maison
     */
    public String getNomMaison() {
        return nomMaison;
    }


    /*
     * Definit le type d'utilisateur comme étant un élève
     *
     */
    public void definirJetonUtilisateur() {
        this.setJeton(JetonUtilisateur.eleve);
    }

    /**
     * Getter de l'attribut totalPoints
     * @return nombre de points
     */
    public Integer getTotalPoints() {
        return totalPoints;
    }


}
