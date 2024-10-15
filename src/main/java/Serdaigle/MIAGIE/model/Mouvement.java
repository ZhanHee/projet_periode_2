package Serdaigle.MIAGIE.model;

import jakarta.persistence.*;

/**
 * Classe représentant un mouvement
 */
@Entity
@Table(name = "mouvement", schema = "miagie")
public class Mouvement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMouv", nullable = false)
    private Integer id;

    /**
     * Eleve effectuant le mouvement
     */
    @MapsId("idEleve")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idEleve", nullable = false)
    private Eleve idEleve;

    /**
     * Date du mouvement
     */
    @Column(name = "timestampp", length = 50)
    private long timestampp;

    @Column(name = "coup", nullable = false, length = 50)
    private String coup;


    /**
     * Getter de l'id du mouvement
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter de l'id du mouvement
     * @param id identifiant du mouvement
     */
    public void setId(int id) {
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
    public long getTimestampp() {
        return timestampp;
    }

    /**
     * Setter de la date du mouvement
     * @param timestampp date du mouvement
     */
    public void setTimestampp(long timestampp) {
        this.timestampp = timestampp;
    }

    public String getCoup() {
        return coup;
    }

    public void setCoup(String coup) {
        this.coup = coup;
    }

}