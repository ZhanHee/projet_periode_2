package Serdaigle.MIAGIE.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

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

    /**
     * Liste des élèves de la maison
     */
    @OneToMany(mappedBy = "nomMaison", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnore
    private List<Eleve> eleves;

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

    /**
     * getter de la liste des élèves de la maison
     * @return List
     */
    public List<Eleve> getEleves() {
        return eleves;
    }

}