package Serdaigle.MIAGIE.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un élève brut issu de la base de données
 */
@Entity
@Table(name = "eleve", schema = "miagie")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Eleve {
    /**
     * Identifiant de l'élève
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEleve", nullable = false)
    private Integer idEleve;

    /**
     * Total de points de l'élève
     */
    @ColumnDefault("0")
    @Column(name = "totalPoints")
    private Integer totalPoints;

    /**
     * Nom de l'élève
     */
    @Column(name = "nom", length = 50)
    private String nom;

    /**
     * Prénom de l'élève
     */
    @Column(name = "prenom", length = 50)
    private String prenom;

    /**
     * Maison de l'élève
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nomMaison", referencedColumnName = "nomMaison", nullable = false)
    private Maison maison;

    /**
     * Propositions Recues
     */
    @OneToMany(mappedBy = "eleve", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PropositionPartie> propositionsRecues;

    /**
     * Propositions Envoyees
     */
    @OneToMany(mappedBy = "eleve", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PropositionPartie> propositionsEnvoyees;

    /**
     * Evaluations concernees
     */
    @OneToMany(mappedBy = "eleve", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ArrayList<Evaluer> evaluations;

    /**
     * Constructeur de la classe Eleve
     */
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public Eleve() {
    }

    /**
     * Getter de l'id de l'élève
     * @return Integer
     */
    public Integer getId() {
        return idEleve;
    }

    /**
     * Setter de l'id de l'élève
     * @param id id de l'élève
     */
    public void setId(Integer id) {
        this.idEleve = id;
    }

    /**
     * Getter de la maison de l'élève
     * @return Maison
     */
    public Maison getMaison() {
        return maison;
    }

    /**
     * Getter du nom de la maison de l'élève
     *
     * @return String
     */
    public String getNomMaison(){
        return maison.getNomMaison();
    }

    /**
     * Setter de la maison de l'élève
     * @param maison maison de l'élève
     */
    public void setMaison(Maison maison) {
        if (this.maison == maison) {
            return;
        }

        // Retirer l'élève de l'ancienne maison (s'il y en a une)
        if (this.maison != null) {
            this.maison.removeEleve(this);
        }

        // Ajouter l'élève à la nouvelle maison
        if (maison != null && !maison.getEleves().contains(this)) {
            maison.addEleve(this);
        }

        this.maison = maison;
    }


    /**
     * Getter du total de points de l'élève
     * @return Integer
     */
    public Integer getTotalPoints() {
        return totalPoints;
    }

    /**
     * Setter du total de points de l'élève
     * @param totalPoints total de points
     */
    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    /**
     * Getter du nom de l'élève
     * @return String
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter du nom de l'élève
     * @param nom nom de l'élève
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter du prénom de l'élève
     * @return String
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Setter du prénom de l'élève
     * @param prenom prénom de l'élève
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public ArrayList<Evaluer> getEvaluations(){return evaluations;}

    public void addScore(int value){this.totalPoints += value;}


}