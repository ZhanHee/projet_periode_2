package Serdaigle.MIAGIE.repository;

import Serdaigle.MIAGIE.model.Eleve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import Serdaigle.MIAGIE.model.PropositionPartie;
import org.springframework.transaction.annotation.Transactional;


/**
 * Repository pour les propositions de partie. Fait le lien avec la base de données et alimente le modèle.
 */
@Repository
public interface PropositionPartieRepository extends JpaRepository<PropositionPartie, Integer> {

    /**
     * Recherche les propositions de partie par joueur cible.
     * @param joueurCible le joueur cible
     * @return la liste des propositions de partie correspondant
     */
    @Query("SELECT p \n" +
            "FROM propositionpartie p \n" +
            "WHERE p.ideleveReceveur = :joueurCible\n" +
            "AND p.id NOT IN(\n" +
            "    SELECT pa.propositionPartie.id\n" +
            "    \tFROM Partie pa\n" +
            " )")
    Iterable<PropositionPartie> getPropositionByJoueurCible(Eleve joueurCible);


    /**
     * Recherche les propositions de partie par joueur source.
     * @param joueurSource le joueur source
     * @return la liste des propositions de partie correspondant
     */
    @Query("SELECT p \n" +
            "FROM propositionpartie p \n" +
            "WHERE p.ideleveLanceur = :joueurSource\n" +
            "AND p.id NOT IN(\n" +
            "    SELECT pa.propositionPartie.id\n" +
            "    \tFROM Partie pa\n" +
            " )")
    Iterable<PropositionPartie> getPropositionByJoueurSource(Eleve joueurSource);

    @Modifying
    @Transactional
    @Query("UPDATE PropositionPartie p SET p.idEleve_vainqueur = ?1 WHERE p.idProposition = ?2")
    void setGagnantPartie(Integer idEleve, Integer idProposition);

}


