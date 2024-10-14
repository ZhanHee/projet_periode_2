package Serdaigle.MIAGIE.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

/**
 * Classe Jeu
 */
@Entity
@Table(name = "jeu", schema = "miagie")
public class Jeu {

    /**
     * Identifiant du jeu
     */
    @Id
    @Column(name = "nomJeu", nullable = false, length = 50)
    private String nomJeu;

    /**
     * Constructeur de la classe Jeu
     */
    public Jeu() {

    }

    /**
     * Constructeur de la classe Jeu avec paramètre nomJeu
     * @param nomJeu nom du jeu
     */
    public Jeu(String nomJeu) {
        this.nomJeu = nomJeu;
    }

    /**
     * Getter du nom du jeu
     * @return nom du jeu
     */
    public String getNomJeu() {
        return nomJeu;

    }

    /**
     * Setter du nom du jeu
     * @param nomJeu nom du jeu
     */
    public void setNomJeu(String nomJeu) {
            this.nomJeu = nomJeu;
    }
}