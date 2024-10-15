package Serdaigle.MIAGIE.dto;

import java.time.LocalDate;

public class EvaluationDTO {
    private String nomMatiere;
    private int note;
    private LocalDate dateEval;

    public EvaluationDTO(String nomMatiere, int note, LocalDate localDate) {
        this.nomMatiere = nomMatiere;
        this.note = note;
        this.dateEval = localDate;
    }

    // Getters et setters
    public String getNomMatiere() {
        return nomMatiere;
    }

    public void setNomMatiere(String nomMatiere) {
        this.nomMatiere = nomMatiere;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public LocalDate getDateEval() {
        return dateEval;
    }

    public void LocalDate(LocalDate dateEval) {
        this.dateEval = dateEval;
    }


}
