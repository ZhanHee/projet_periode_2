package Serdaigle.MIAGIE.service;
import Serdaigle.MIAGIE.exception.*;
import Serdaigle.MIAGIE.model.*;
import Serdaigle.MIAGIE.repository.*;
import Serdaigle.MIAGIE.tooling.ToolingMethods;
import Serdaigle.MIAGIE.dto.EleveDTO;
import Serdaigle.MIAGIE.dto.PropositionPartieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class Puissance4Service {

    @Autowired
    private DeskRepository deskRepository;

    @Autowired
    private MoveRepository moveRepository;

    // 创建新游戏桌面
    public Desk createNewDesk() {
        Desk newDesk = new Desk();
        newDesk.setCurrentPlayer("Red"); // 红方先手
        deskRepository.save(newDesk);
        return newDesk;
    }

    // 获取桌面状态
    public Desk getDeskStatus(int idPartie) {
        Optional<Desk> desk = deskRepository.findById(idPartie);
        return desk.orElseThrow(() -> new IllegalArgumentException("Desk not found with id: " + idPartie));
    }

    // 处理玩家的移动
    public String processMove(Move move) {
        // 获取桌面信息
        Desk desk = getDeskStatus(move.getIdPartie());

        // 检查桌面是否已经结束
        if (desk.getWinner() != null) {
            return "Game is already finished. Winner is " + desk.getWinner();
        }

        // 检查是否是当前玩家的回合
        if (!move.getPlayer().equals(desk.getCurrentPlayer())) {
            return "It's not your turn.";
        }

        // 检查列是否已经满
        if (isColumnFull(move.getColumn(), move.getIdPartie())) {
            return "Column is full!";
        }

        // 计算应该放置的行号
        int row = getAvailableRow(move.getColumn(), move.getIdPartie());
        if (row == -1) {
            return "No available row in this column";
        }
        move.setRow(row);

        // 设置棋子颜色
        move.setPiece(getCurrentPlayerPiece(desk));

        // 存储移动
        moveRepository.save(move);

        // 检查胜利条件
        if (checkVictory(move)) {
            desk.setWinner(move.getPlayer());
            deskRepository.save(desk);
            return "Player " + desk.getWinner() + " wins!";
        }

        // 切换当前玩家
        switchPlayer(desk);
        deskRepository.save(desk);

        return "Move registered";
    }

    // 检查某列是否满
    private boolean isColumnFull(int column, int idPartie) {
        long count = moveRepository.countByColumnAndIdPartie(column, idPartie);
        return count >= 6; // 假设每列有 6 行
    }

    // 获取可用的行号
    private int getAvailableRow(int column, int idPartie) {
        List<Move> movesInColumn = moveRepository.findByColumnAndIdPartieOrderByRowDesc(column, idPartie);
        if (movesInColumn.isEmpty()) {
            return 5; // 从底部开始
        } else {
            int lastOccupiedRow = movesInColumn.get(0).getRow();
            if (lastOccupiedRow > 0) {
                return lastOccupiedRow - 1;
            } else {
                return -1; // 列已满
            }
        }
    }

    // 获取当前玩家的棋子颜色
    private char getCurrentPlayerPiece(Desk desk) {
        return "Red".equals(desk.getCurrentPlayer()) ? 'R' : 'Y';
    }

    // 切换当前玩家
    private void switchPlayer(Desk desk) {
        if ("Red".equals(desk.getCurrentPlayer())) {
            desk.setCurrentPlayer("Yellow");
        } else {
            desk.setCurrentPlayer("Red");
        }
    }

    // 检查胜利条件
    private boolean checkVictory(Move move) {
        // 获取当前棋盘状态
        List<Move> moves = moveRepository.findByIdPartie(move.getIdPartie());

        // 构建棋盘数组
        char[][] board = new char[6][7];
        for (Move m : moves) {
            board[m.getRow()][m.getColumn()] = m.getPiece();
        }

        // 检查是否胜利
        return checkWinCondition(board, move.getRow(), move.getColumn(), move.getPiece());
    }

    // 检查四子连线
    private boolean checkWinCondition(char[][] board, int row, int col, char piece) {
        // 水平方向
        if (countConsecutivePieces(board, row, col, 0, 1, piece) >= 4) return true;
        // 垂直方向
        if (countConsecutivePieces(board, row, col, 1, 0, piece) >= 4) return true;
        // 对角线（左上到右下）
        if (countConsecutivePieces(board, row, col, 1, 1, piece) >= 4) return true;
        // 对角线（右上到左下）
        if (countConsecutivePieces(board, row, col, 1, -1, piece) >= 4) return true;

        return false;
    }

    // 计数连续的棋子
    private int countConsecutivePieces(char[][] board, int row, int col, int rowDir, int colDir, char piece) {
        int count = 1;
        int r, c;

        // 正方向
        r = row + rowDir;
        c = col + colDir;
        while (isValidPosition(r, c) && board[r][c] == piece) {
            count++;
            r += rowDir;
            c += colDir;
        }

        // 反方向
        r = row - rowDir;
        c = col - colDir;
        while (isValidPosition(r, c) && board[r][c] == piece) {
            count++;
            r -= rowDir;
            c -= colDir;
        }

        return count;
    }

    // 检查位置是否合法
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 6 && col >= 0 && col < 7;
    }

    // 重置桌面
    public void resetDesk(int idPartie) {
        moveRepository.deleteByIdPartie(idPartie);
        Optional<Desk> deskOpt = deskRepository.findById(idPartie);
        if (deskOpt.isPresent()) {
            Desk desk = deskOpt.get();
            desk.setCurrentPlayer("Red");
            desk.setWinner(null);
            deskRepository.save(desk);
        }
    }

    // 获取所有移动
    public List<Move> getAllMoves(int idPartie) {
        return moveRepository.findByIdPartieOrderByTimestampAsc(idPartie);
    }
}