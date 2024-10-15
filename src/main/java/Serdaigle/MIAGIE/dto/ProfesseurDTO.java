package Serdaigle.MIAGIE.dto;
/**
 * Cette classe permet de définir un professeur
 * ProfesseurDTO
 */
public class ProfesseurDTO extends PersonneDTO{
    /**
     * Attribut nomMatiere
     */
    private final String nomMatiere;

    /**
     * Constructeur de la classe ProfesseurDTO
     * @param id identifiant du professeur
     * @param nom nom du professeur
     * @param prenom prénom du professeur
     * @param nomMatiere nom de la matière enseignée
     */
    public ProfesseurDTO(int id, String nom, String prenom, String nomMatiere) {
        super(id, nom, prenom);
        this.nomMatiere = nomMatiere;
    }

    /**
     * Getter de l'attribut nomMatiere
     * @return String
     */
    public String getNomMatiere() {
        return nomMatiere;
    }
    /**
     * Definit le type d'utilisateur comme étant un professeur
     *
     */
    @Override
    public void definirJetonUtilisateur() {
        // Définir le jeton comme étant "professeur"
        this.setJeton(JetonUtilisateur.professeur);
    }


}
