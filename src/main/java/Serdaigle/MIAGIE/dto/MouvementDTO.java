package Serdaigle.MIAGIE.dto;

import Serdaigle.MIAGIE.model.MouvementId;

public class MouvementDTO {
    private int id;
    private String coup;
    private long timestamp;
    private int idEleve;

    public MouvementDTO(int id, String coup, long timestamp, int idEleve) {
        this.id = id;
        this.coup = coup;
        this.timestamp = timestamp;
        this.idEleve = idEleve;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoup() {
        return coup;
    }

    public void setCoup(String coup) {
        this.coup = coup;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getIdEleve() {
        return idEleve;
    }

    public void setIdEleve(int idEleve) {
        this.idEleve = idEleve;
    }
}
