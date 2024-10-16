package Serdaigle.MIAGIE.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

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
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idEleve", insertable = false, updatable = false)
    private Eleve idEleve;

    /**
     * Date du mouvement
     */
    @Column(name = "timestampp", length = 50)
    private String timestampp;

    @Column(name = "mouv", length = 50)
    private String mouv;

    @OneToOne
    @JoinColumn(name = "idPartie", referencedColumnName = "idPartie")
    private Partie partie;

    public Mouvement(Eleve eleve, String mouv, Partie partie){
        this.idEleve = eleve;
        this.timestampp = String.valueOf(LocalDateTime.now());
        this.mouv = mouv;
        this.partie = partie;
    }

    public Mouvement() {

    }

    /**
     * Getter de l'id du mouvement
     * @return id
     */
    public int getId() {
        return id != null ? id.hashCode() : 0;
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

    public String getMouv() {
        return this.mouv;
    }

}