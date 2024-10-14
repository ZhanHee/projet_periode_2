package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Eleve;
import model.Matiere;
import model.Professeur;
import model.mapper.EleveMapper;
import model.mapper.ProfesseurMapper;
import sql.MySQLConnection;

public class ProfesseurDAO {

	private final GenericDAO<Professeur> dao = new GenericDAO<>(new ProfesseurMapper());

	public List<Professeur> getProfesseur() {
		return dao.executeQuery("SELECT * FROM professeur");
	}

	public Professeur getProfesseurById(int id){
		List<Professeur> professeur = dao.executeQuery("SELECT * FROM professeur WHERE idProfesseur = ?", id);
		return professeur.getFirst();
	}

	public Professeur getProfesseurByMatiere(String matiere){
		List<Professeur> professeurs =dao.executeQuery("SELECT * FROM professeur WHERE nomMatiere = ?", matiere);
		return professeurs.getFirst();
	}


}
