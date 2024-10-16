package Serdaigle.MIAGIE.repository;

import Serdaigle.MIAGIE.model.Partie;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository pour les parties. Fait le lien avec la base de données et alimente le modèle.
 */
@Repository
public interface PartieRepository extends JpaRepository<Partie, Integer> {


    @Query("SELECT p FROM Partie p WHERE p.propositionPartie.id = :id")
    Partie getPartieByIdProposition(@Param("id") Integer id);

}
