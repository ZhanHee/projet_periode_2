package Serdaigle.MIAGIE.model;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Classe représentant une évaluation brut issu de la base de données
 */
@Entity
@Table(name = "evaluer", schema = "miagie")
public class Evaluer {

    /**
     * Identifiant de l'évaluation
     */
    @EmbeddedId
    private EvaluerId id;

    /**
     * Identifiant de l'élève
     */
    @MapsId("idEleve")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idEleve", nullable = false)
    private Eleve idEleve;

    /**
     * Note de l'évaluation
     */
    @Column(name = "note")
    private Integer note;

    /**
     * Date de l'évaluation
     */
    @Column(name = "dateEval")
    private LocalDate dateEval;

    /**
     * Constructeur du modele, attributs gérés par l'ORM
     *
     */
    public Evaluer() {}

    /**
     * Getter de l'identifiant de l'evaluation
     * @return identifiant de l'evaluation
     */
    public EvaluerId getId() {
        return id;
    }

    /**
     * Setter de l'identifiant de l'evaluation
     * @param id identifiant de l'evaluation
     */
    public void setId(EvaluerId id) {
        this.id = id;
    }

    /**
     * Getter de l'identifiant de l'eleve
     * @return identifiant de l'eleve
     */
    public Eleve getIdEleve() {
        return idEleve;
    }

    /**
     * Setter de l'identifiant de l'eleve
     * @param idEleve identifiant de l'eleve
     */
    public void setIdEleve(Eleve idEleve) {
        this.idEleve = idEleve;
    }

    /**
     * Getter de la note de l'evaluation
     * @return note de l'evaluation
     */
    public Integer getNote() {
        return note;
    }

    /**
     * Setter de la note de l'evaluation
     * @param note note de l'evaluation
     */
    public void setNote(Integer note) {
        this.note = note;
    }

    /**
     * Getter de la date de l'evaluation
     * @return date de l'evaluation
     */
    public LocalDate getDateEval() {
        return dateEval;
    }

    /**
     * Setter de la date de l'evaluation
     * @param dateEval date de l'evaluation
     */
    public void setDateEval(LocalDate dateEval) {
        this.dateEval = dateEval;
    }
}