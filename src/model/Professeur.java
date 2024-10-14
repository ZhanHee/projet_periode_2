package model;

import dao.MatiereDAO;
import dao.ProfesseurDAO;
import vue.ShowInMenu;

import java.util.List;

public class Professeur implements ShowInMenu {

	private int idPersonnel;
	private String nom;
	private String prenom;
	private String matiere;
	private boolean isDirecteur;

	public Professeur(String nom, String prenom, String matiere, boolean isDirecteur) {
		this.nom = nom;
		this.prenom = prenom;
		this.setMatiere(matiere);
		this.isDirecteur = isDirecteur;
	}

	public Professeur(int idPersonnel, String nom, String prenom, String matiere, boolean isDirecteur) {
		this.idPersonnel = idPersonnel;
		this.nom = nom;
		this.prenom = prenom;
		this.setMatiere(matiere);
		this.isDirecteur = isDirecteur;
	}


	public int getIdPersonnel() {
		return idPersonnel;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom){ this.nom = nom;}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom){this.prenom = prenom;}

	public String getMatiere() {
		return matiere;
	}

	public void setMatiere(String matiere) {
		this.matiere = matiere;
	}

	public void getProfesseurByMatiere() {
		MatiereDAO matiereDAO = new MatiereDAO();
		List<Matiere> matieres = matiereDAO.getAllMatiere();
		for(Matiere matiere : matieres) {
			matiere.getProfesseurs();
			System.out.println("Matiere " + matiere.getNom() + " a des Professeurs : " + "\n" + matiere.getProfesseurs());
		}
	}

	@Override
	public String toString() {
		return "Professeur{" +
				"prenom='" + prenom + '\'' +
				", nom='" + nom + '\'' +
				", matiere=" + matiere +
				", id=" + idPersonnel +
				'}';
	}

	@Override
	public String getLabelMenu() {
		return this.getNom() + " " + this.getPrenom();
	}

	@Override
	public int getIdMenu() {
		return this.getIdPersonnel();
	}

	public boolean getIsDirecteur() {
		return isDirecteur;
	}
}
