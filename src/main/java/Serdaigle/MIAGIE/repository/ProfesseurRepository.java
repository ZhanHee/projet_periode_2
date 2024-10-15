package Serdaigle.MIAGIE.repository;

import Serdaigle.MIAGIE.model.Professeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository pour les professeurs. Fait le lien avec la base de données et alimente le modèle.
 */
@Repository
public interface ProfesseurRepository extends JpaRepository<Professeur, Integer> {

    /**
     * Recherche un professeur par son nom ou son prénom avec un Substring.
     * @param nom le nom ou prénom du professeur
     * @return la liste des professeurs correspondant
     */
    @Query("SELECT e FROM Professeur e WHERE e.nom LIKE :nom% OR e.prenom LIKE :nom%")
    List<Professeur> searchWithFilter(String nom);



}