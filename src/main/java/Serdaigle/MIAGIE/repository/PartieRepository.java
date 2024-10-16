package Serdaigle.MIAGIE.repository;

import Serdaigle.MIAGIE.model.Partie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour les parties. Fait le lien avec la base de données et alimente le modèle.
 */
@Repository
public interface PartieRepository extends JpaRepository<Partie, Integer> {

}
