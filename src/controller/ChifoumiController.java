package controller;

import model.Eleve;

public class ChifoumiController {
    private Eleve lanceur;
    private Eleve receveur;


    public ChifoumiController(Eleve lanceur, Eleve receveur) {
        this.lanceur = lanceur;
        this.receveur = receveur;
    }
}