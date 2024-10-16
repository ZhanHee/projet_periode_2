package Serdaigle.MIAGIE.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

/**
 * Classe représentant un identifiant de mouvement
 */
@Embeddable
public class MouvementId implements java.io.Serializable {


    private static final long serialVersionUID = 6957316629164369763L;

    /**
     * Identifiant de l'élève
     */
    @Column(name = "idEleve", nullable = false)
    private Integer idEleve;

    /**
     * Mouvement effectué
     */
    @Column(name = "idPartie", nullable = false)
    private Integer idPartie;

    /**
     * Getter de l'identifiant de l'élève
     * @return idEleve
     */
    public Integer getIdEleve() {
        return idEleve;
    }

    /**
     * Setter de l'identifiant de l'élève
     * @param idEleve id de l'élève
     */
    public void setIdEleve(Integer idEleve) {
        this.idEleve = idEleve;
    }

    /**
     * Getter du mouvement effectué
     * @return mouv
     */
    public Integer getIdPartie() {
        return idPartie;
    }

    public void setIdPartie(Integer idPartie) {
        this.idPartie = idPartie;
    }

    /**
     * Override de la méthode equals
     * @param o objet à comparer
     * @return true si les objets sont égaux, false sinon
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MouvementId entity = (MouvementId) o;
        return Objects.equals(this.idPartie, entity.idPartie) &&
                Objects.equals(this.idEleve, entity.idEleve);
    }

    /**
     * Override de la méthode hashCode
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(idPartie, idEleve);
    }

}