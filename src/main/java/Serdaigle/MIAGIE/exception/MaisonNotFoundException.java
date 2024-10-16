package Serdaigle.MIAGIE.exception;

/** Exception levée lorsqu'une maison n'est pas trouvée */
public class MaisonNotFoundException extends Exception{
    /**
     * Constructeur de l'exception MaisonNotFoundException.
     * @param message Le message de l'exception.
     */
    public MaisonNotFoundException(String message) {
        super(message);
    }
}
