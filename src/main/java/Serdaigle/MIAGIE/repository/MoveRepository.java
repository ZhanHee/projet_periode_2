package Serdaigle.MIAGIE.repository;

import Serdaigle.MIAGIE.model.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoveRepository extends JpaRepository<Move, Integer> {
    List<Move> findByIdPartie(int idPartie);

    void deleteByIdPartie(int idPartie);

    long countByColumnAndIdPartie(int column, int idPartie);

    List<Move> findByIdPartieOrderByTimestampAsc(int idPartie);

    List<Move> findByColumnAndIdPartieOrderByRowDesc(int column, int idPartie);
}