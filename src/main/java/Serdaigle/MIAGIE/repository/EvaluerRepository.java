package Serdaigle.MIAGIE.repository;

import Serdaigle.MIAGIE.model.Evaluer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour la table d'association "Evaluer". Fait le lien avec la base de données et alimente le modèle.
 */
@Repository
public interface EvaluerRepository extends JpaRepository<Evaluer, Integer> {

}