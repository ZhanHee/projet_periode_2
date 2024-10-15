package Serdaigle.MIAGIE.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Classe représentant une matière brute issue de la base de données
 */
@Entity
@Table(name = "matiere", schema = "miagie")
public class Matiere {
    /**
     * Nom de la matière
     */
    @Id
    @Column(name = "nomMatiere", nullable = false, length = 50)
    private String nomMatiere;

    /**
     * Constructeur de la classe Matiere
     */
    public Matiere(String nomMatiere) {
    	this.nomMatiere = nomMatiere;
    }

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public Matiere() {
    }

    /**
     * Getter du nom de la matière
     * @return nomMatiere
     */
    public String getNomMatiere() {
        return nomMatiere;
    }

    /**
     * Setter du nom de la matière
     * @param nomMatiere Le nom de la matière
     */
    public void setNomMatiere(String nomMatiere) {
        this.nomMatiere = nomMatiere;
    }

}