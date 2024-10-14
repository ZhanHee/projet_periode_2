package dao;

import model.Eleve;
import model.Mouvement;
import model.Partie;
import model.mapper.MouvementMapper;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MouvementDAO {
    private final GenericDAO<Mouvement> dao = new GenericDAO<>(new MouvementMapper());

    public Mouvement getMouvementById(int id){
        return this.dao.executeQuery("SELECT * FROM mouvement WHERE idMouv = ?", id).getFirst();
    }

    public List<Mouvement> getMouvementsByPartie(Partie p){
        return this.dao.executeQuery("SELECT * FROM mouvement WHERE idPartie = ? ORDER BY timestampp", p.getIdPartie());
    }

    public Mouvement createMouvement(Eleve e, Partie p, String coup){
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        long ts = timestamp.getTime();
        Integer id = this.dao.executeInsert("INSERT INTO mouvement (idEleve, mouv, timestampp, idPartie) VALUES (?,?,?,?)", e.getId(), coup, ts, p.getIdPartie());
        if(id != null){
            return this.getMouvementById(id);
        }
        return null;
    }


}
