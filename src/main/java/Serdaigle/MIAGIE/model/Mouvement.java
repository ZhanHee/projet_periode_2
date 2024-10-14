package Serdaigle.MIAGIE.model;

import jakarta.persistence.*;

/**
 * Classe représentant un mouvement
 */
@Entity
@Table(name = "mouvement", schema = "miagie")
public class Mouvement {
    @EmbeddedId
    private MouvementId id;

    /**
     * Eleve effectuant le mouvement
     */
    @MapsId("idEleve")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idEleve", nullable = false)
    private Eleve idEleve;

    /**
     * Date du mouvement
     */
    @Column(name = "timestampp", length = 50)
    private String timestampp;

    /**
     * Getter de l'id du mouvement
     * @return id
     */
    public MouvementId getId() {
        return id;
    }

    /**
     * Setter de l'id du mouvement
     * @param id identifiant du mouvement
     */
    public void setId(MouvementId id) {
        this.id = id;
    }

    /**
     * Getter de l'élève effectuant le mouvement
     * @return idEleve
     */
    public Eleve getIdEleve() {
        return idEleve;
    }

    /**
     * Setter de l'élève effectuant le mouvement
     * @param idEleve identifiant de l'élève
     */
    public void setIdEleve(Eleve idEleve) {
        this.idEleve = idEleve;
    }

    /**
     * Getter de la date du mouvement
     * @return timestampp
     */
    public String getTimestampp() {
        return timestampp;
    }

    /**
     * Setter de la date du mouvement
     * @param timestampp date du mouvement
     */
    public void setTimestampp(String timestampp) {
        this.timestampp = timestampp;
    }

}