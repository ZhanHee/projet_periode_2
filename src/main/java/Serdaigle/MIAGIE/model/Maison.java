package Serdaigle.MIAGIE.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant une maison
 */
@Entity
@Table(name = "maison", schema = "miagie")
public class Maison {
    /**
     * Nom de la maison
     */
    @Id
    @Column(name = "nomMaison", nullable = false, length = 50)
    private String nomMaison;

    @OneToMany(mappedBy = "maison", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"maison", "hibernateLazyInitializer", "handler"})
    private List<Eleve> eleves = new ArrayList<>();

    public Maison(String nom) {
        this.nomMaison = nom;
    }

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public Maison() {

    }

    public List<Eleve> getEleves() {
        return eleves;
    }

    /**
     * getter du nom de la maison
     * @return nomMaison
     */
    public String getNomMaison() {
        return nomMaison;
    }

    /**
     * setter du nom de la maison
     * @param nomMaison nom de la maison
     */
    public void setNomMaison(String nomMaison) {
        this.nomMaison = nomMaison;
    }

    public void addEleve(Eleve eleve) {
        if (!eleves.contains(eleve)) {
            eleves.add(eleve);
            eleve.setMaison(this);
        }
    }

    public void removeEleve(Eleve eleve) {
        if (eleves.contains(eleve)) {
            eleves.remove(eleve);
            eleve.setMaison(null);
        }
    }

    public int getTotalPoints() {
        return eleves.stream().mapToInt(Eleve::getTotalPoints).sum();
    }

}