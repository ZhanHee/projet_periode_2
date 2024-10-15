package Serdaigle.MIAGIE.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

/**
 * Classe représentant l'entité Evaluer, pour évaluer un élève dans une matière
 */
@Embeddable
public class EvaluerId implements java.io.Serializable {

    /**
     * Numéro de sérialisation
     */
    private static final long serialVersionUID = 5268995373866791819L;

    /**
     * Identifiant de l'élève
     */
    @Column(name = "idEleve", nullable = false)
    private Integer idEleve;

    /**
     * Nom de la matière dans laquelle l'élève est évalué
     */
    @Column(name = "nomMatiere", nullable = false, length = 50)
    private String nomMatiere;

    /**
     * Getter de l'identifiant de l'élève
     * @return identifiant de l'élève
     */
    public Integer getIdEleve() {
        return idEleve;
    }

    /**
     * Setter de l'identifiant de l'élève
     * @param idEleve identifiant de l'élève
     */
    public void setIdEleve(Integer idEleve) {
        this.idEleve = idEleve;
    }

    /**
     * Getter du nom de la matière
     * @return nom de la matière
     */
    public String getNomMatiere() {
        return nomMatiere;
    }

    /**
     * Setter du nom de la matière
     * @param nomMatiere nom de la matière
     */
    public void setNomMatiere(String nomMatiere) {
        this.nomMatiere = nomMatiere;
    }

    /**
     * Redéfinition de la méthode equals
     * @param o objet à comparer
     * @return true si les objets sont égaux, false sinon
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EvaluerId entity = (EvaluerId) o;
        return Objects.equals(this.idEleve, entity.idEleve) &&
                Objects.equals(this.nomMatiere, entity.nomMatiere);
    }

    /**
     * Redéfinition de la méthode hashCode
     * @return code de hachage
     */
    @Override
    public int hashCode() {
        return Objects.hash(idEleve, nomMatiere);
    }

    public void deleteEleveByNomMatiere(Matiere nommatiere) {


    }

}