package Serdaigle.MIAGIE.exception;
/** Exception levée lorsqu'une proposition de partie n'est pas trouvée */
public class PropositionPartieNotFound extends Exception{

    /**
     * Constructeur de l'exception PropositionPartieNotFound
     * @param message le message de l'exception
     */
    public PropositionPartieNotFound(String message) {
        super(message);
    }
}
