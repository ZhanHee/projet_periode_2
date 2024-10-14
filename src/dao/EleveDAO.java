package dao;

import model.Eleve;
import model.Maison;
import model.PropositionPartie;
import model.mapper.EleveMapper;

import java.util.List;

public class EleveDAO {
    private final GenericDAO<Eleve> eleveDAO = new GenericDAO<>(new EleveMapper());
    private final PropositionPartieDAO ppDAO = new PropositionPartieDAO();

    public void populatePropositionsEleve(Eleve e, boolean log){
        List<PropositionPartie> recues = ppDAO.getPropositionsPartiesRecuesNonAcceptees(e);
        int totalRecues = e.addPropositionsPartie(recues);
        List<PropositionPartie> envoyeesAvecReponse = ppDAO.getPropositionsPartiesAccepteesParReceveur(e);
        int totalEnvoyeeAvecReponse = e.addPropositionsEnvoyee(envoyeesAvecReponse);
        if(log && totalRecues > 0){
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+ totalRecues+" nouveau défi reçu !");
        }
        if(log && totalEnvoyeeAvecReponse > 0){
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+totalEnvoyeeAvecReponse+" défi envoyé a commencé !");
        }
    }
    public List<Eleve> getEleves() {
        return eleveDAO.executeQuery("SELECT * FROM eleve");
    }

    public Eleve getEleveById(int id, boolean populatePropositions){
        Eleve eleve = eleveDAO.executeQuery("SELECT * FROM eleve WHERE idEleve = ?", id).getFirst();
        if(populatePropositions) {
            this.populatePropositionsEleve(eleve, false);
        }
        return eleve;
    }

    public List<Eleve> getElevesByMaison(Maison m, boolean populatePropositions){
        List<Eleve> eleves = eleveDAO.executeQuery("SELECT * FROM eleve WHERE nomMaison = ?", m.getNom());
        if(populatePropositions){
            for (Eleve e : eleves){
                this.populatePropositionsEleve(e, false);
            }
        }
        return eleves;
    }

    public Eleve createEleve(String nom, String prenom, Maison maison){
        Integer id = eleveDAO.executeInsert("INSERT INTO eleve (nom, prenom, nomMaison) VALUES (?,?,?)", nom, prenom, maison.getNom());
        if(id != null){
            Eleve created = this.getEleveById(id, false);
            created.setMaison(maison);
            return created;
        }
        return null;
    }


    public void saveEleve(Eleve e){
        if(e.getMaison() != null){

            eleveDAO.executeUpdate(
                    "UPDATE eleve SET nom = ?, prenom = ?, score = ?, nomMaison = ? WHERE idEleve = ?",
                    e.getNom(), e.getPrenom(), e.getScore(), e.getMaison().getNom(), e.getId());
        }
        else{
            eleveDAO.executeUpdate(
                    "UPDATE eleve SET nom = ?, prenom = ?, totalPoints = ? WHERE idEleve = ?",
                    e.getNom(), e.getPrenom(), e.getScore(), e.getId());
        }
    }
}
