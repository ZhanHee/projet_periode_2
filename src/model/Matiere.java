package model;

import dao.MatiereDAO;

import java.util.List;

public class Matiere {
	
	private String nom;
	
	public Matiere(String nom) {
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}

	public List<Professeur> getProfesseurs() {
		MatiereDAO dao = new MatiereDAO();
		return dao.getProfesseurByMatiere(this.getNom());
	}

}
