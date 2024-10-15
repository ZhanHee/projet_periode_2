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
    private MaisonDTO maison;

    /**
     * Constructeur de la classe EleveDTO
     * @param idEleve identifiant de l'élève
     * @param nom nom de l'élève
     * @param prenom prenom de l'élève
     * @param totalPoints nombre de points de l'élève
     * @param maison la maison de l'élève
     */
    public EleveDTO(Integer idEleve, String nom, String prenom, Integer totalPoints, MaisonDTO maison) {
        super(idEleve, nom, prenom);
        this.totalPoints = totalPoints;
        this.setMaison(maison);
    }
    /**
     * Getter de l'attribut nomMaison
     * @return nom de la maison
     */
    public MaisonDTO getMaison() {
        return maison;
    }

    public void setMaison(MaisonDTO maison) {
        if (this.maison == maison) {
            return;
        }
        // Retirer de l'ancienne maison (si elle existe)
        if (this.maison != null) {
            this.maison.removeEleve(this);
        }
        // Ajouter à la nouvelle maison
        if (maison != null && !maison.getEleves().contains(this)) {
            maison.addEleve(this);
        }
        // Mettre à jour l'attribut maison de l'élève
        this.maison = maison;
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
