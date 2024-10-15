package Serdaigle.MIAGIE.repository;

import Serdaigle.MIAGIE.dto.MouvementDTO;
import Serdaigle.MIAGIE.model.Partie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MouvementRepository extends JpaRepository<Partie, Integer> {

    @Query("SELECT m FROM Mouvement m WHERE m.idPartie.id = ?1 ORDER BY m.timestampp")
    List<MouvementDTO> findMouvementsByPartieId(Integer idPartie);

}
