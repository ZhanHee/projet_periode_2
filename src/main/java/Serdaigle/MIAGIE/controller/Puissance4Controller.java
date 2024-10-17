package Serdaigle.MIAGIE.controller;

import Serdaigle.MIAGIE.exception.EleveNotFoundException;
import Serdaigle.MIAGIE.exception.PartieNotFoundException;
import Serdaigle.MIAGIE.model.*;
import Serdaigle.MIAGIE.service.ChiFouMiInterService;
import Serdaigle.MIAGIE.service.Puissance4Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/puissance4")
public class Puissance4Controller {

    @Autowired
    private Puissance4Service puissance4Service;

    // 创建新游戏桌面
    @PostMapping("/new")
    public ResponseEntity<Desk> createNewDesk() {
        Desk newDesk = puissance4Service.createNewDesk();
        return ResponseEntity.ok(newDesk);
    }

    // 获取桌面状态
    @GetMapping("/desk/{idPartie}")
    public ResponseEntity<Desk> getDeskStatus(@PathVariable int idPartie) {
        Desk desk = puissance4Service.getDeskStatus(idPartie);
        return ResponseEntity.ok(desk);
    }

    // 玩家移动
    @PostMapping("/move")
    public ResponseEntity<String> playerMove(@RequestBody Move move) {
        String result = puissance4Service.processMove(move);
        return ResponseEntity.ok(result);
    }

    // 重置桌面
    @PostMapping("/reset/{idPartie}")
    public ResponseEntity<String> resetDesk(@PathVariable int idPartie) {
        puissance4Service.resetDesk(idPartie);
        return ResponseEntity.ok("Desk reset successfully");
    }

    // 获取桌面中的所有移动
    @GetMapping("/desk/{idPartie}/moves")
    public ResponseEntity<List<Move>> getAllMoves(@PathVariable int idPartie) {
        List<Move> moves = puissance4Service.getAllMoves(idPartie);
        return ResponseEntity.ok(moves);
    }
}
