package Serdaigle.MIAGIE.repository;

import Serdaigle.MIAGIE.model.Partie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour les parties. Fait le lien avec la base de données et alimente le modèle.
 */
@Repository
public interface PartieRepository extends JpaRepository<Partie, Integer> {

    @Query("SELECT p FROM partie p WHERE p.idPartie = :idPartie")
    Partie getPartieById(@Param("idPartie") int idPartie);

    @Query("SELECT p FROM partie p")
    List<Partie> getAllParties();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO partie (idProposition) VALUES (?1)", nativeQuery = true)
    void insertPartie(Integer idProposition);

    @Query("SELECT p FROM partie p WHERE idProposition = ?1")
    Optional<Partie> findByIdPropositionPartie(Integer idPropositionPartie);

}
