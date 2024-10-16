package Serdaigle.MIAGIE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Serdaigle.MIAGIE.model.Matiere;


/**
 * Repository pour la classe Matiere. Fait le lien avec la base de données et alimente le modèle.
 */
@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Integer> {

    /**
     * Permet de récupérer une matière par son nom.
     * @param nomMatiere le nom de la matière à rechercher.
     * @return la matière correspondante.
     */
    Matiere findByNomMatiere(String nomMatiere);
}
