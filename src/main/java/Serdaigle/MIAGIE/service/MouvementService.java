package Serdaigle.MIAGIE.service;


import Serdaigle.MIAGIE.model.Mouvement;
import Serdaigle.MIAGIE.model.MouvementId;
import Serdaigle.MIAGIE.repository.MouvementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MouvementService {

    @Autowired
    private MouvementRepository mouvementRepository;

    @Transactional
    public void save(Mouvement mouvement) {
        mouvementRepository.save(mouvement);
    }
}
