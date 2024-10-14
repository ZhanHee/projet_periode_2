package vue;

import dao.EleveDAO;
import model.Eleve;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Notification {

    private final ScheduledExecutorService scheduler;
    private final Eleve eleve;
    private final EleveDAO eleveDAO;

    public Notification(Eleve eleve) {
        this.eleve = eleve;
        this.eleveDAO = new EleveDAO();
        this.scheduler = Executors.newScheduledThreadPool(1); // Crée le scheduler
        Runnable task = this::updatePropositionsPartie;

        // Planifier la tâche pour s'exécuter périodiquement toutes les 2 secondes
        this.scheduler.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
    }

    public void run() {
        // Appel de myFunction

    }

    public void stop(){
        this.scheduler.shutdown();
    }

    public void updatePropositionsPartie() {
        this.eleveDAO.populatePropositionsEleve(this.eleve, true);
    }
}
