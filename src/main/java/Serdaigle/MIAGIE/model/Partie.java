package Serdaigle.MIAGIE.model;

import jakarta.persistence.*;

/**
 * Classe représentant une partie
 */
@Entity
@Table(name = "partie", schema = "miagie")
public class Partie {

    /**
     * Identifiant de la partie
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPartie", nullable = false)
    private Integer id;

    /**
     * Identifiant de la proposition associée à la partie
     */
    @OneToOne
    @JoinColumn(name = "idProposition", referencedColumnName = "idProposition")  // Assure-toi que le nom de la colonne est correct
    private Propositionpartie propositionPartie;

    /**
     * Nom du jeu associé à la partie
     */
    @ManyToOne
    @JoinColumn(name = "nomJeu", referencedColumnName = "nomJeu")
    private Jeu nomJeu;

    /**
     * Constructeur de la classe Partie
     * @param propositionPartie proposition associée à la partie
     */
    public Partie(Propositionpartie propositionPartie) {
        this.propositionPartie = propositionPartie;
        this.nomJeu = propositionPartie.getJeu();
    }

    /**
     * Constructeur vide de la classe Partie
     */
    public Partie() {

    }

    /**
     * Getter de l'identifiant de la partie
     * @return nomJeu
     */
    public Jeu getNomJeu() {
        return nomJeu;
    }

    /**
     * Setter de l'identifiant de la partie
     * @param nomJeu nom du jeu
     */
    public void setNomJeu(Jeu nomJeu) {
        this.nomJeu = nomJeu;
    }


    /**
     * Getter de l'identifiant de la partie
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Getter de la proposition associée à la partie
     * @return propositionPartie
     */
    public Propositionpartie getPropositionPartie() {
        return propositionPartie;
    }

    /**
     * Setter de l'Id de la partie
     * @param id identifiant de la partie
     */
    public void setId(Integer id) {
        this.id = id;
    }

}