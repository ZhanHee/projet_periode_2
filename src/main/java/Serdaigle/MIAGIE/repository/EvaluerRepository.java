package Serdaigle.MIAGIE.repository;

import Serdaigle.MIAGIE.model.Evaluer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository pour la table d'association "Evaluer". Fait le lien avec la base de données et alimente le modèle.
 */
@Repository
public interface EvaluerRepository extends JpaRepository<Evaluer, Integer> {

    /**
     * Requête custom pour récupérer un élève avec sa maison (FK).
     * @param nomMatiere identifiant de le matiere
     * @param idEleve identifiant de le matiere
     */
     @Modifying
     @Transactional
     @Query("DELETE FROM Evaluer WHERE (idEleve = :idEleve AND nomMatiere  = :nomMatiere)")
    void deleteByEleveEtMatiere(@Param("idEleve") int idEleve, @Param("nomMatiere") String nomMatiere);

}