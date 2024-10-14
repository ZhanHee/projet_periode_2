package dao;

import model.Eleve;
import model.Evaluation;
import model.Matiere;
import model.mapper.EvaluerMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EvaluerDAO {
    private GenericDAO<Evaluation> dao = new GenericDAO<>(new EvaluerMapper());

    public List<Evaluation> getEvaleur(){
        return dao.executeQuery("SELECT * FROM evaleur");
    }

    public List<Evaluation> getEvaleurByMatiere(Matiere matiere){
        return dao.executeQuery("SELECT * FROM evaleur WHERE nomMatiere = ?",matiere.getNom());
    }
    public List<Evaluation> getEvaleurByEleve(Eleve eleve){
        return dao.executeQuery("SELECT * FROM evaleur WHERE nomMatiere = ?",eleve.getNom());
    }

    public void ajouterNoteDeEleve(Eleve eleve, String matiere,int note){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        String formattedDate = currentDate.format(formatter);

        String insertQuery = "INSERT INTO evaluer (idEleve, nomMatiere, note, dateEval) VALUES (?, ?, ?, ?)";
        dao.executeInsertWithoutGeneratedKeys(insertQuery,eleve.getId(),matiere,note,formattedDate);

        this.updatePointsEleve(eleve, note);
    }

    public void updatePointsEleve(Eleve eleve,int points){
        eleve.addScore(points);
        dao.executeUpdate("UPDATE eleve SET totalPoints = ? WHERE idEleve = ?",points,eleve.getId());
    }
}
