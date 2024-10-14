package model;

public class Mouvement {
    private int id;
    private String coup;
    private long timestamp;
    private int idEleve;

    public Mouvement(int id, String coup, long timestamp, int idEleve) {
        this.id = id;
        this.coup = coup;
        this.timestamp = timestamp;
        this.idEleve = idEleve;
    }

    public int getId() {
        return id;
    }

    public String getCoup() {
        return coup;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getIdEleve() {
        return idEleve;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Mouvement m){
            return m.getId() == this.getId();
        }
        return false;
    }

    @Override
    public String toString() {
        return "Le joueur d'id "+this.getIdEleve()+" a joué "+this.getCoup();
    }
}
