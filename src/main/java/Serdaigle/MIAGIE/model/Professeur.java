package Serdaigle.MIAGIE.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

/**
 * Classe représentant un professeur
 */
@Entity
@Table(name = "professeur", schema = "miagie")
public class Professeur {
    /**
     * Identifiant du professeur
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProfesseur", nullable = false)
    private Integer id;

    /**
     * Nom du professeur
     */
    @Column(name = "nom", length = 50)
    private String nom;

    /**
     * Prénom du professeur
     */
    @Column(name = "prenom", length = 50)
    private String prenom;

    /**
     * Boolean indiquant si le professeur est directeur
     */
    @Column(name = "isDirecteur")
    private Boolean isDirecteur;

    /**
     * Matière enseignée par le professeur
     */
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nomMatiere", referencedColumnName = "nomMatiere", nullable = false)
    @JsonBackReference
    private Matiere nomMatiere;


    /**
     * Constructeur de la classe Professeur
     * @param nom nom du professeur
     * @param prenom prénom du professeur
     * @param nomMatiere matière enseignée par le professeur
     * @param isDirecteur boolean indiquant si le professeur est directeur
     */
    public Professeur(String nom, String prenom, Matiere nomMatiere, Boolean isDirecteur) {
        this.nom = nom;
        this.prenom = prenom;
        this.nomMatiere = nomMatiere;
        this.isDirecteur = isDirecteur;
    }

    /**
     * Constructeur vide de la classe Professeur
     */
    public Professeur() {

    }

    /**
     * Getter de l'id du professeur
     * @return Integer
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter de l'id du professeur
     * @param id identifiant du professeur
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter du nom du professeur
     * @return String
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter du nom du professeur
     * @param nom nom du professeur
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter du prénom du professeur
     * @return String
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Setter du prénom du professeur
     * @param prenom prénom du professeur
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Getter de la matière enseignée par le professeur
     * @return Matiere
     */
    public Matiere getNomMatiere() {
        return this.nomMatiere;
    }

    /**
     * Setter de la matière enseignée par le professeur
     * @param nomMatiere matière enseignée par le professeur
     */
    public void setNomMatiere(Matiere nomMatiere) {
        this.nomMatiere = nomMatiere;
    }

    /**
     * Getter du boolean indiquant si le professeur est directeur
     * @return Boolean
     */
    public Boolean getIsDirecteur() {
        return isDirecteur;
    }

    /**
     * Setter du boolean indiquant si le professeur est directeur
     * @param isDirecteur boolean indiquant si le professeur est directeur
     */
    public void setIsDirecteur(Boolean isDirecteur) {
        this.isDirecteur = isDirecteur;
    }


}