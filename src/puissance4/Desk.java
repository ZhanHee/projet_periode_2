package puissance4;
import java.util.Scanner;


public class Desk {
        //棋桌类
        private char[][] date = new char[6][10];
        private Scanner scanner = new Scanner(System.in) ;
        private int hand = 1 ;
        private int idPartie;
        private int idEleve;
        private char pieceColor;
        private int idWinner = -1;

        public Desk() {
            for (int i = 0; i < date.length; i++) {
                for (int j = 0; j < date[i].length; j++) {
                    date[i][j] = ' '; // 将每个格子初始化为空格字符
                }
            }
        }

        public void setPieceColor(char pieceColor) {
            this.pieceColor = pieceColor;
        }

        public void setIdEleve(int idEleve) {
            this.idEleve = idEleve;
        }

        public int getIdWinner() {
            return idWinner;
        }

        //画出整个棋盘以及上面的棋子
        public void draw() {
            for (int i = 0; i < this.date.length; i++) {
                System.out.print("|");
                for (int j = 0; j < this.date[i].length-3; j++) {
                    System.out.print(this.date[i][j] + "|");
                }
                System.out.println();
            }
            if (this.hand % 2 == 1 && pieceColor == 'X') {
                System.out.print("now it's " + hand + " round, your turn (X): ");
                playchess(pieceColor);
            } else if (this.hand % 2 == 0 && pieceColor == 'O') {
                System.out.print("now it's " + hand + " round, your turn (O): ");
                playchess(pieceColor);
            } else {
                System.out.print("now it's " + hand + " round, opponent's turn... \n");
                getOthersideMove();
            }
        }



        //控制落子
        public void playchess(char color) {
            hand++;
            int place = scanner.nextInt() - 1;
            int column = place % 10; // 提取列号

            if (place >= 0 && place < 7) {
                // 正常落子
                dropSinglePiece(column, color);
            } else if (place >= 10 && place <= 17) {
                // vive les serpentaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaard!
                column = place - 10; // 提取列号
                if (!dropDoublePiece(column, color)) {
                    // 如果无法在该列连续下两次棋子，则取消这次操作，重新输入
                    System.out.println("Not enough space in column " + (column + 1) + " for two pieces. Try again.");
                    hand--;
                    playchess(color);
                }
            } else {
                System.out.println("Invalid input. Try again.");
                hand--;
                playchess(color);
            }
        }

    private boolean dropSinglePiece(int column, char color) {
        for (int i = 5; i >= 0; i--) {
            if (date[i][column] == '\u0020') {
                date[i][column] = color;
                int idEleve = Move.getReceveur(getIdPatrie());
                Move.insertMove(idEleve, i, column, color, getIdPatrie());
                win(i, column, color);
                return true;
            }
        }
        System.out.println("Column full.");
        return false;
    }

    private boolean dropDoublePiece(int column, char color) {
        int firstRow = -1;
        int secondRow = -1;

        // 查找两个空位
        for (int i = 5; i >= 0; i--) {
            if (date[i][column] == '\u0020') {
                if (firstRow == -1) {
                    firstRow = i;
                } else {
                    secondRow = i;
                    break;
                }
            }
        }

        // 如果找到两个空位
        if (firstRow != -1 && secondRow != -1) {
            date[firstRow][column] = color;
            date[secondRow][column] = color;

            int idEleve = Move.getReceveur(getIdPatrie());
            Move.insertMove(idEleve, firstRow, column, color, getIdPatrie());
            Move.insertMove(idEleve, secondRow, column, color, getIdPatrie());

            win(firstRow, column, color);
            win(secondRow, column, color);
            return true;
        }
        return false;
    }



    //判断游戏是否有人胜利或平局(依据是根据落子位置的两个方向是否连成四子)
    public void win(int x, int place,char color) {
        int number = 1;
        //第一个方向(右斜线方向)
        for (int i = x - 1, j = place - 1; (i >= 0) && (j >= 0); i--, j--) {
            if (date[i][j] == color) {
                number++;}
            else break; }
        for (int i = x + 1, j = place + 1; (i <= 5) && (j <= 6); i++, j++) {
            if (date[i][j] == color) {
                number ++ ;
            }
            else break;
        }
        if (number == 4)end(color, "win");
        //第二个方向(左斜线方向)
        number = 1;
        for (int i = x - 1, j = place + 1; (i >= 0) && (j <= 6); i--, j++) {
            if (date[i][j] == color) {
                number++;
            }
            else break;
        }
        for (int i = x + 1, j = place - 1; (i <= 5) && (j >= 0); i++, j--) {
            if (date[i][j] == color) {
                number ++ ;
            }
            else break;
        }
        if (number == 4)end(color, "win");
        //第三个方向(水平)
        number = 1;
        for (int i = x, j = place - 1; j >= 0; j--) {
            if (date[i][j] == color) {
                number ++ ;
            }
            else break ;
        }
        for (int i = x, j = place + 1; j <= 6; j++) {
            if (date[i][j] == color) {
                number ++ ;
            }
            else break;
        }
        if (number == 4)end(color, "win");
        //第四个方向(竖直)
        number = 1;
        for (int i = x + 1, j = place; i <= 5; i++) {
            if (date[i][j] == color) {
                number ++ ;
            }
            else break;
        }if (number == 4)end(color, "win") ;
        //判断平局
        boolean res = true;
        for (int i = 0; i < date.length; i++) {
            for (int j = 0; j < date[i].length; j++) {
                if (date[i][j] != 'X' || date[i][j] != 'O')res = false;
            }
        }
        if(res) end(color, "s");
    }

        //判断游戏是否结束
        public void end(char color, String str) {
            if (str.equals("win")) {
                System.out.print(color + " wins!!!");
                if (color==pieceColor){
                    this.idWinner = this.idEleve;
                }else{
                    this.idWinner = Move.getReceveur(idPartie);
                }
                for (int i = 0; i < this.date.length; i++) {
                    System.out.print("|");
                    for (int j = 0; j < this.date[i].length-3; j++) {
                        System.out.print(this.date[i][j] + "|");
                    }
                    System.out.println();
                }

            }else {
                this.idWinner = Move.getReceveur(idPartie);;
                System.out.print("it's a tie.") ;
                for (int i = 0; i < this.date.length; i++) {
                    System.out.print("|");
                    for (int j = 0; j < this.date[i].length-3; j++) {
                        System.out.print(this.date[i][j] + "|");
                    }
                    System.out.println();
                }
            }
        }



    public void getOthersideMove() {
        while (true) {
            int[] firstMove = Move.getLatestMove(this.idPartie);

            if (firstMove != null) {
                int firstRow = firstMove[0];
                int column = firstMove[1];

                // 检查是否有第二个连续的操作
                int[] secondMove = Move.getSecondLatestMove(this.idPartie);

                if (secondMove != null && secondMove[1] == column && secondMove[0] != firstRow && secondMove[2] != this.pieceColor) {
                    int secondRow = secondMove[0];
                    char opponentColor = (this.pieceColor == 'X') ? 'O' : 'X';
                    if (date[secondRow][column] == '\u0020') {
                        date[secondRow][column] = opponentColor;
                        win(secondRow, column, opponentColor);
                    }
                }

                if (date[firstRow][column] == '\u0020') {
                    char opponentColor = (this.pieceColor == 'X') ? 'O' : 'X';
                    date[firstRow][column] = opponentColor;
                    hand++;
                    win(firstRow, column, opponentColor);
                    break;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    public int getIdPatrie(){
            return this.idPartie;
        }

        public void setIdPartie(int idPartie) {
            this.idPartie = idPartie;
        }
}









