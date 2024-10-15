package Serdaigle.MIAGIE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import Serdaigle.MIAGIE.model.Jeu;
import java.util.Optional;

/**
 * Repository pour la classe Jeu. Fait le lien avec la base de données et alimente le modèle.
 */
@Repository
public interface JeuRepository extends JpaRepository<Jeu, Long> {

    /**
     * Méthode personnalisée pour récupérer un jeu par son nom
     * @param nomJeu Nom du jeu
     * @return Jeu
     */
    @Query("SELECT j FROM jeu j WHERE j.nomJeu = :nomJeu")
    Optional<Jeu> findByNom(@Param("nomJeu") String nomJeu);
}