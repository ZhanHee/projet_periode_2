package Serdaigle.MIAGIE.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import javax.persistence.*;

@Entity
@Table(name = "desk")
public class Desk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String currentPlayer; // "Red" 或 "Yellow"
    private String winner;        // "Red", "Yellow" 或 null

    // Getters and Setters

    public int getId() {
        return id;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public String getWinner() {
        return winner;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
