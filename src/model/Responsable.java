package model;

import controller.EcoleController;
import dao.EleveDAO;
import dao.MaisonDAO;
import vue.Menu;


import java.util.*;

public class Responsable extends Professeur {
	EcoleController ecoleController = new EcoleController();

	public Responsable(int id, String nom, String prenom, String matiere) {
		super(id, nom, prenom, matiere, true);
	}

	public void setNom(String nom){
		super.setNom(nom);
	}

	public void setPrenom(String prenom){
		super.setPrenom(prenom);
	}

	public List<Maison> getMaisons(){
		MaisonDAO maisonDAO = new MaisonDAO();
		return maisonDAO.getMaisons();
	}

	public void getNoteParMaisons(List<Maison> maisons) {
		for (Maison maison : maisons) {
			System.out.println("Le maison : " + maison.getNom() + " avoir le note " + maison.getTotalPoints());
		}
	}

	public void creerEleve(){
		EleveDAO eleveDAO = new EleveDAO();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Veuillez entrer le nom:");
		String nom = scanner.nextLine();

		System.out.println("Veuillez entrer le prenom:");
		String prenom = scanner.nextLine();

		Maison maison = (Maison) Menu.showEntityMenu(ecoleController.getMaisons(), " maison ");

        assert maison != null;

        eleveDAO.createEleve(nom, prenom, maison);
	}


}