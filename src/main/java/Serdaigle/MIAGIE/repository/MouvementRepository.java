package Serdaigle.MIAGIE.repository;

import Serdaigle.MIAGIE.model.Mouvement;
import Serdaigle.MIAGIE.model.MouvementId;
import Serdaigle.MIAGIE.model.Partie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MouvementRepository extends JpaRepository<Mouvement, MouvementId> {

    @Query("SELECT m FROM Mouvement m WHERE m.partie = :partie")
    List<Mouvement> findByPartie(@Param("partie") Partie partie);

    @Query("SELECT m FROM Mouvement m WHERE m.idEleve = :idEleve AND m.mouv = :mouv")
    Optional<Mouvement> findByIdEleveAndMouv(@Param("idEleve") Integer idEleve, @Param("mouv") String mouv);
}
