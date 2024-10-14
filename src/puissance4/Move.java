package puissance4;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;


public class Move {

    public static void identification(int idPartie, int idEleve){

    }




    public static int getReceveur(int idPartie) {
        String query = "SELECT idEleve_receveur FROM PropositionPartie, Partie WHERE PropositionPartie.idProposition = Partie.idProposition AND idPartie = ?";
        try (Connection conn = connectBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idPartie); // 设置第1个参数：idPartie

            try (ResultSet res = stmt.executeQuery()) {
                if (res.next()) {
                    return res.getInt("idEleve_receveur");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }



    public static void insertMove(int player, int row, int column, char piece, int idPartie) {
        // SQL 插入语句
        String query = "INSERT INTO Mouvement (idEleve, mouv, timestampp, idPartie) VALUES (?, ?, ?, ?)";

        // 获取数据库连接
        try (Connection conn = connectBD.getConnection();
             PreparedStatement statament = conn.prepareStatement(query)) {

            conn.setAutoCommit(false);
            // 设置查询参数

            statament.setString(1, String.valueOf(player)); // 设置第1个参数：eleve的id
            String mouv = String.valueOf(row)+","+String.valueOf(column)+","+piece;
            statament.setString(2, mouv); // 设置第2个参数：行号+列号+棋子颜色
            Timestamp timestampp = new Timestamp(new Date().getTime());
            statament.setTimestamp(3, timestampp);//当前时间戳
            statament.setInt(4, idPartie);


            // 执行插入操作
            int nbRowAffecte = statament.executeUpdate();
            if (nbRowAffecte == 1) {
                conn.commit(); // 提交事务
            } else {
                conn.rollback(); // 回滚事务
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void getCurrentProcess() {
        String query = "SELECT * FROM Mouvement ORDER BY timestamp ASC";

        try (Connection conn = connectBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet res = stmt.executeQuery()) {

            while (res.next()) {
                char player = res.getString("player").charAt(0);
                String mouv = res.getString("mouv");
                int row = Integer.parseInt(mouv.split(",")[0]);
                int column = Integer.parseInt(mouv.split(",")[1]);
                char piece = mouv.split(",")[2].charAt(0);


                System.out.println(", Player: " + player + ", Row: " + row + ", Column: " + column + ", Piece: " + piece);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void nettoyer(int idPartie) {
        String deleteQuery = "DELETE FROM Mouvement WHERE idPartie = ?";

        try (Connection conn = connectBD.getConnection();
            PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {

            stmt.setInt(1, idPartie);
            conn.setAutoCommit(false); // 开始事务

            stmt.executeUpdate();

            conn.commit(); // 提交事务
            System.out.println("le bord Movement est netoye");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static int[] getLatestMove(int idPartie) {
        String query = "SELECT * FROM Mouvement WHERE idPartie = ? ORDER BY timestampp DESC LIMIT 1";
        try (Connection conn = connectBD.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, idPartie);

            try (ResultSet rs = statement.executeQuery()) {
                int[] res = new int[2];
                if (rs.next()) {
                    String mouv = rs.getString("mouv");
                    int row = Integer.parseInt(mouv.split(",")[0]);
                    int column = Integer.parseInt(mouv.split(",")[1]);

                    res[0] = row;
                    res[1] = column;
                    return res;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static int[] getSecondLatestMove(int idPartie) {
        String query = "SELECT * FROM Mouvement WHERE idPartie = ? ORDER BY timestampp DESC LIMIT 1 OFFSET 1"; // 获取第二个最新的移动
        try (Connection conn = connectBD.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, idPartie); // 设置参数

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    String mouv = rs.getString("mouv");
                    int row = Integer.parseInt(mouv.split(",")[0]);
                    int column = Integer.parseInt(mouv.split(",")[1]);
                    char color = mouv.split(",")[2].charAt(0); // 假设颜色也是存储在字符串中

                    return new int[]{row, column, (int) color}; // 返回行、列和颜色的ASCII值
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // 如果没有找到则返回null
    }


}
