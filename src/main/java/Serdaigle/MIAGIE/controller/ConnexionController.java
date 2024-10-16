package Serdaigle.MIAGIE.controller;

import Serdaigle.MIAGIE.model.Matiere;
import Serdaigle.MIAGIE.service.EcoleService;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour gérer les connexion des étudiants
 */
@RestController
@RequestMapping("/connexion")
public class ConnexionController {

    /**
     * GET /connexion
     * Endpoint pour récupérer toutes les connexions.
     * @return les connexions au format json
     */
    @GetMapping
    public String connexion() {
    	//Création d'un tableau json
        StringBuilder jsonArray = new StringBuilder();
        jsonArray.append("[");

        try {
            // Connexion à la base de données
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/miagie", // URL de la base de données
                "root", // Nom d'utilisateur MySQL
                ""      // Mot de passe MySQL
            );

            // Créer un statement pour exécuter la requête SQL
            Statement stmt = con.createStatement();

            // La requête SQL
            String query = """
                SELECT * 
                FROM (
                    SELECT 
                        CONCAT(eleve.nom, eleve.prenom) AS pseudo,
                        eleve.nom AS nom, 
                        eleve.prenom AS prenom, 
                        eleve.idEleve AS id, 
                        "pwd" AS motDePasse, 
                        "eleve" AS fonction, 
                        eleve.nomMaison AS nomMaison
                    FROM eleve
                    UNION
                    SELECT 
                        CONCAT(professeur.nom, professeur.prenom) AS pseudo,
                        professeur.nom AS nom, 
                        professeur.prenom AS prenom, 
                        professeur.idProfesseur AS id, 
                        "pwd" AS motDePasse, 
                        "professeur" AS fonction, 
                        "aucune" AS nomMaison
                    FROM professeur
                    WHERE professeur.nomMatiere <> "Directeur"
                    UNION
                    SELECT 
                        CONCAT(professeur.nom, professeur.prenom) AS pseudo,
                        professeur.nom AS nom, 
                        professeur.prenom AS prenom, 
                        professeur.idProfesseur AS id, 
                        "pwd" AS motDePasse, 
                        "Directeur" AS fonction, 
                        "aucune" AS nomMaison
                    FROM professeur
                    WHERE professeur.nomMatiere = "Directeur"
                ) AS unionResult;
            """;

            // Exécution de la requête
            ResultSet rs = stmt.executeQuery(query);

            boolean first = true; // Pour gérer les virgules entre les objets

            // Traiter les résultats de la requête
            while (rs.next()) {
                if (!first) {
                	/// Ajouter une virgule entre les objets
                    jsonArray.append(",");
                }
                first = false;

                // Encodage au format json
                jsonArray.append("{");
                jsonArray.append("\"pseudo\":\"").append(rs.getString("pseudo")).append("\",");
                jsonArray.append("\"nom\":\"").append(rs.getString("nom")).append("\",");
                jsonArray.append("\"prenom\":\"").append(rs.getString("prenom")).append("\",");
                jsonArray.append("\"id\":").append(rs.getString("id")).append(",");
                jsonArray.append("\"motDePasse\":\"").append(rs.getString("motDePasse")).append("\",");
                jsonArray.append("\"fonction\":\"").append(rs.getString("fonction")).append("\",");
                jsonArray.append("\"nomMaison\":\"").append(rs.getString("nomMaison"));
                jsonArray.append("\"}");
            }

            // Fermer la connexion
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur lors de la connexion ou de l'exécution de la requête : " + e.getMessage();
        }

        //Fermeture du tableau json
        jsonArray.append("]");

        // On renvoie le tableau json sous forme de string
        return jsonArray.toString();
    }

}