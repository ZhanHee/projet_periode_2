package Serdaigle.MIAGIE.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "move")
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int idEleve;     // 玩家 ID，可以根据大家的需要调整
    private int row;         //
    private int column;      //
    private char piece;      // 'R' 或 'Y'
    private int idPartie;    // 游戏 ID
    private String player;   // "Red" 或 "Yellow"

    private LocalDateTime timestamp;

    // 在保存前自动设置时间戳
    @PrePersist
    protected void onCreate() {
        timestamp = LocalDateTime.now();
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public int getIdEleve() {
        return idEleve;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public char getPiece() {
        return piece;
    }

    public int getIdPartie() {
        return idPartie;
    }

    public String getPlayer() {
        return player;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdEleve(int idEleve) {
        this.idEleve = idEleve;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setPiece(char piece) {
        this.piece = piece;
    }

    public void setIdPartie(int idPartie) {
        this.idPartie = idPartie;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
