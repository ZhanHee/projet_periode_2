package puissance4;

import java.util.Scanner;

public class lanche {
    public static int puissance4(int idEleve, int idPartie) {
        Move.nettoyer(idPartie);
        Scanner scanner = new Scanner(System.in);

        Desk desk = new Desk();
        desk.setIdPartie(idPartie);

        // 检测是否为接收方
        int receveurId = Move.getReceveur(idPartie);
        if (idEleve == receveurId) {
            System.out.println("You are the receiver. You will use 'X' and play first.");
            desk.setIdEleve(idEleve);
            desk.setPieceColor('X');
        } else {
            System.out.println("You are not the receiver. You will use 'O' and play second.");
            desk.setIdEleve(idEleve);
            desk.setPieceColor('O');
        }

        // 开始下棋
        while (true) {
            desk.draw();
            // 如果有胜者，则返回胜者的idEleve
            int winner = desk.getIdWinner();
            if (winner != -1) {
                return winner;
            }
        }
    }
}
