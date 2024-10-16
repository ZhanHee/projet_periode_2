package Serdaigle.MIAGIE.repository;

import Serdaigle.MIAGIE.model.Maison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository pour la classe Maison. Fait le lien avec la base de données et alimente le modèle.
 */
@Repository
public interface MaisonRepository extends JpaRepository<Maison, Integer> {

    /**
     * Récupère une maison avec ses élèves par son nom
     * @param nomMaison Nom de la maison
     * @return Maison
     */
    @Query("SELECT m FROM Maison m LEFT JOIN FETCH m.eleves WHERE m.nomMaison = :nomMaison")
    Maison getMaisonWithElevesByNomMaison(@Param("nomMaison") String nomMaison);

    /**
     * Récupère une maison par son nom
     * @param nomMaison Nom de la maison
     * @return Maison
     */
    Maison findByNomMaison(String nomMaison);

    /**
     * Compte le nombre total de points d'une maison
     * @param nomMaison Nom de la maison
     * @return int nombre total de points
     */
    @Query("SELECT SUM(e.totalPoints) FROM Eleve e WHERE e.nomMaison.nomMaison = :nomMaison")
    int countTotalPoints(@Param("nomMaison") String nomMaison);


    /**
     * Récupère la maison gagnante
     * @return Maison
     */
    @Query("SELECT e.nomMaison, SUM(e.totalPoints) FROM Eleve e GROUP BY e.nomMaison ORDER by SUM(e.totalPoints) DESC  limit 1")
    Maison getMaisonGagnante();
}
