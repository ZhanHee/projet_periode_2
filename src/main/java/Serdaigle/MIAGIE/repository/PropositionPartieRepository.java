package Serdaigle.MIAGIE.repository;

import Serdaigle.MIAGIE.dto.PropositionPartieDTO;
import Serdaigle.MIAGIE.model.Eleve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import Serdaigle.MIAGIE.model.Propositionpartie;


/**
 * Repository pour les propositions de partie. Fait le lien avec la base de données et alimente le modèle.
 */
@Repository
public interface PropositionPartieRepository extends JpaRepository<Propositionpartie, Integer> {

    /**
     * Recherche les propositions de partie par joueur cible.
     * @param joueurCible le joueur cible
     * @return la liste des propositions de partie correspondant
     */
    @Query("SELECT p \n" +
            "FROM Propositionpartie p \n" +
            "WHERE p.ideleveReceveur = :joueurCible\n" +
            "AND p.id NOT IN(\n" +
            "    SELECT pa.propositionPartie.id\n" +
            "    \tFROM Partie pa\n" +
            " )")
    Iterable<Propositionpartie> getPropositionByJoueurCible(Eleve joueurCible);


    /**
     * Recherche les propositions de partie par joueur source.
     * @param joueurSource le joueur source
     * @return la liste des propositions de partie correspondant
     */
    @Query("SELECT p \n" +
            "FROM Propositionpartie p \n" +
            "WHERE p.ideleveLanceur = :joueurSource\n" +
            "AND p.id NOT IN(\n" +
            "    SELECT pa.propositionPartie.id\n" +
            "    \tFROM Partie pa\n" +
            " )")
    Iterable<Propositionpartie> getPropositionByJoueurSource(Eleve joueurSource);

    @Modifying
    @Query("UPDATE Propositionpartie p SET p.ideleveVainqueur = :idGagnant WHERE p.id = :propId")
    void updateGagnant(@Param("propId") Integer propId, @Param("idGagnant") Integer idGagnant);

    @Query("SELECT p FROM Propositionpartie p WHERE p.ideleveReceveur = :id AND p.refuse = true")
    Iterable<PropositionPartieDTO> getPropositionNonAccepteeByReceveur(Integer id);

    @Query("SELECT p FROM Propositionpartie p WHERE p.ideleveReceveur = :id AND p.refuse = false AND p.ideleveVainqueur = null")
    Iterable<PropositionPartieDTO> getPropositionEnAttendsByReceveur(Integer id);
}


