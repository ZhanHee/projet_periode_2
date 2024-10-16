package Serdaigle.MIAGIE.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Classe représentant une connexion issue de la base de données
 */
@Entity
@Table(name = "connexion", schema = "miagie")
public class Connexion {
    
    /**
     * Pseudo utilisé pour la connexion (concaténation du nom et prénom)
     */
    @Id
    @Column(name = "pseudo", nullable = false, length = 100)
    private String pseudo;

    /**
     * Nom de l'utilisateur (élève ou professeur)
     */
    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    /**
     * Prénom de l'utilisateur
     */
    @Column(name = "prenom", nullable = false, length = 50)
    private String prenom;

    /**
     * Identifiant de l'utilisateur
     */
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Mot de passe de l'utilisateur
     */
    @Column(name = "motDePasse", nullable = false, length = 255)
    private String motDePasse;

    /**
     * Fonction de l'utilisateur (élève, professeur, directeur)
     */
    @Column(name = "fonction", nullable = false, length = 50)
    private String fonction;

    /**
     * Nom de la maison pour un élève, ou "aucune" pour un professeur
     */
    @Column(name = "nomMaison", nullable = false, length = 50)
    private String nomMaison;

    /**
     * Constructeur par défaut
     */
    public Connexion(String pseudo, String nom, String prenom, Integer id, String motDePasse, String fonction, String nomMaison) {
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.id = id;
        this.motDePasse = motDePasse;
        this.fonction = fonction;
        this.nomMaison = nomMaison;
    }

    // Getters et setters

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getNomMaison() {
        return nomMaison;
    }

    public void setNomMaison(String nomMaison) {
        this.nomMaison = nomMaison;
    }
    
    @Override
    public String toString() {
        return "Connexion{" +
                "id='" + id + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", fonction='" + fonction + '\'' +
                ", nomMaison='" + nomMaison + '\'' +
                '}';
    }
}
