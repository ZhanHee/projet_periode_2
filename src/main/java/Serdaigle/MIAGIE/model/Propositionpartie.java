package Serdaigle.MIAGIE.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

/**
 * Classe représentant une proposition de partie
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "propositionpartie", schema = "miagie")
public class Propositionpartie {
    /**
     * Identifiant de la proposition
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProposition", nullable = false)
    private Integer id;

    /**
     * Mise de la proposition
     */
    @Column(name = "mise")
    private Integer mise;

    /**
     * Refus (ou non) de la proposition
     */
    @Column(name = "refuse")
    private Boolean refuse;

    /**
     * Identifiant de l'élève vainqueur
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "ideleve_vainqueur", nullable = true)
    private Eleve ideleveVainqueur;

    /**
     * Identifiant de l'élève receveur de proposition
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ideleve_receveur", nullable = false)
    private Eleve ideleveReceveur;

    /**
     * Identifiant de l'élève lanceur de proposition
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ideleve_lanceur", nullable = false)
    private Eleve ideleveLanceur;

    /**
     * Jeu associé à la proposition
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nomJeu", referencedColumnName = "nomJeu", nullable = false ) // Assurez-vous que c'est correct
    private Jeu jeu;


    /**
     * Constructeur de la classe Propositionpartie
     * @param eSource élève source
     * @param eDest élève destinataire
     * @param jeu jeu associé
     * @param mise mise de la partie
     */
    public Propositionpartie(Eleve eSource, Eleve eDest, Jeu jeu, int mise) {
        this.ideleveLanceur = eSource;
        this.ideleveReceveur = eDest;
        this.mise = mise;
        this.refuse = false;
        this.jeu = jeu;

    }

    /**
     * Constructeur vide de la classe Propositionpartie
     */
    public Propositionpartie() {
    }

    /**
     * Getter de l'identifiant de l'élève lanceur
     * @return Eleve
     */
    public Eleve getIdeleveLanceur() {
        return ideleveLanceur;
    }

    /**
     * Getter de l'identifiant de la proposition
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Getter de la mise de la proposition
     * @return mise
     */
    public Integer getMise() {
        return mise;
    }

    /**
     * Getter du refus de la proposition
     * @return refuse
     */
    public Boolean getRefuse() {
        return refuse;
    }

    /**
     * Getter du jeu associé à la proposition
     * @return jeu
     */
    public Jeu getJeu() {
        return jeu;
    }

    /**
     * Setter du refus de la proposition
     * @param refuse refus
     */
    public void setRefuse(Boolean refuse) {
        this.refuse = refuse;
    }

    /**
     * Getter de l'identifiant de l'élève receveur
     * @return ideleveReceveur
     */
    public Eleve getEleveReceveur() {
        return ideleveReceveur;
    }

    /**
     * Getter de l'identifiant de l'élève Lanceur
     * @return ideleveLanceur
     */
    public Eleve getEleveLanceur() {
        return ideleveLanceur;
    }


}