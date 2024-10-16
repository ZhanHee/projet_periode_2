package Serdaigle.MIAGIE.repository;

import Serdaigle.MIAGIE.model.Eleve;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository du modèle "Eleve". Fait le lien avec la base de données et alimente le modèle.
 */
@Repository
public interface EleveRepository extends JpaRepository<Eleve, Integer> {
    /**
     * Requête custom pour récupérer un élève avec sa maison (FK).
     * @param idEleve identifiant de l'élève
     * @return l'élève avec sa maison
     */
    @Query("SELECT e FROM Eleve e LEFT JOIN FETCH e.nomMaison WHERE e.idEleve = :idEleve")
    Optional<Eleve> getEleveByIdWithMaison(@Param("idEleve") int idEleve);

    /**
     * Requête custom pour récupérer tous les élèves des maisons non Serdaigle.
     * @return la liste des élèves des maisons non Serdaigle
     */
    @Query("SELECT e FROM Eleve e WHERE e.nomMaison.nomMaison <> 'Serdaigle'")
    Iterable<Eleve> findElevesFromOtherHouses();

    /**
     * Requête custom pour ajouter des points à un élève.
     * @param idEleve identifiant de l'élève
     * @param nbPoints nombre de points à ajouter
     */
    @Transactional
    @Modifying
    @Query("UPDATE Eleve e SET e.totalPoints = e.totalPoints + :nbPoints WHERE e.idEleve = :idEleve")
    void addPoints(@Param("idEleve") int idEleve, @Param("nbPoints") int nbPoints);

    /**
     * Requête custom pour rechercher des élèves par un Substring.
     * @param nom Substring à rechercher
     * @return la liste des élèves correspondant au Substring
     */
    @Query("SELECT e FROM Eleve e WHERE e.nom LIKE :nom% OR e.prenom LIKE :nom%")
    List<Eleve> searchWithFilter(String nom);
}