package Serdaigle.MIAGIE.dto;
/**
 * Cette classe permet de définir les attributs communs à tous les types de personnes
 * PersonneDTO. (professeurs, élèves)
 */
public abstract class PersonneDTO {
    private final int id;
    private final String nom;
    private final String prenom;
    private JetonUtilisateur jeton;

    /**
     * Constructeur de la classe PersonneDTO
     * @param id : identifiant de l'utilisateur
     * @param nom : nom de l'utilisateur
     * @param prenom : prénom de l'utilisateur
     */
    public PersonneDTO(int id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.definirJetonUtilisateur();

    }

    /**
     * Getter de l'attribut id
     * @return id de l'utilisateur
     */
    public int getId() {
        return id;
    }

    /**
     * Getter de l'attribut nom
     * @return nom de l'utilisateur
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter de l'attribut jeton
     * @return jeton de l'utilisateur
     */
    public JetonUtilisateur getJeton() {
        return jeton;
    }

    /**
     * Getter de l'attribut prenom
     * @return prénom de l'utilisateur
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Definit le type d'utilisateur comme étant un élève
     *
     */
    public abstract void definirJetonUtilisateur();
    /**
     * Setter de l'attribut jeton
     * @param jeton : jeton de l'utilisateur
     */
    public void setJeton(JetonUtilisateur jeton) {
        this.jeton = jeton;
    }


}
