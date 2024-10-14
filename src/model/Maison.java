package model;

import dao.EleveDAO;
import vue.ShowInMenu;


import java.util.ArrayList;
import java.util.List;

public class Maison implements ShowInMenu {

	private final String nom;
	private final ArrayList<Eleve> eleves;
	
	public Maison(String nom) {
		this.nom = nom;
		this.eleves = new ArrayList<>();
	}

	public String getNom() {
		return nom;
	}

	public void addEleve(Eleve e){
		if(eleves.contains(e)){
			return;
		}
		this.eleves.add(e);
		e.setMaison(this);
	}

	public void addEleves(List<Eleve> eleves){
		for (Eleve e : eleves){
			this.addEleve(e);
		}
	}

	public List<Eleve> getEleves(){
		return eleves;
	}

	public void removeEleve(Eleve e){
		this.eleves.remove(e);
		e.setMaison(null);
	}


	public int getTotalPoints(){
		int totalPoints = 0;
		for (Eleve e : this.getEleves()){
			totalPoints += e.getScore();
		}
		return totalPoints;
	}

	@Override
	public String toString() {
		return "Maison{" +
				"nom='" + nom + '\'' +
				", eleves=" + eleves.size() +
				'}';
	}


	@Override
	public String getLabelMenu() {
		return this.getNom();
	}

	@Override
	public int getIdMenu() {
		return switch (this.getNom()){
			case "Gryffondor" -> 1;
			case "Poufsouffle"-> 2;
			case "Serdaigle"-> 3;
			case "Serpentard"-> 4;
			default -> 0;
		};
	}
}
