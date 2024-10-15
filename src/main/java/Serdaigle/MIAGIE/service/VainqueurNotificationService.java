package Serdaigle.MIAGIE.service;

import Serdaigle.MIAGIE.dto.EleveDTO;
import Serdaigle.MIAGIE.exception.EleveNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class VainqueurNotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private JeuxService jeuxService;

    @Scheduled(fixedRate = 2000) //2s
    public void notifyVainqueur() {
        try {
            Integer idGagnant = 1;
            EleveDTO gagnant = jeuxService.getEleveVainqueur(idGagnant);

            messagingTemplate.convertAndSend("/topic/vainqueur", gagnant);
        } catch (EleveNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}