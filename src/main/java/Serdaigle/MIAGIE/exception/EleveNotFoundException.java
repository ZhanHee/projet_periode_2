package Serdaigle.MIAGIE.exception;

/**
 * Exception levée lorsqu'un élève n'est pas trouvé
 */
public class EleveNotFoundException extends RuntimeException {
    /**
     * Constructeur
     * @param message le message de l'exception
     */
    public EleveNotFoundException(String message) {
        super(message);
    }
}
