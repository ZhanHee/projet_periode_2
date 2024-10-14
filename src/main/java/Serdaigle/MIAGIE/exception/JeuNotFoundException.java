package Serdaigle.MIAGIE.exception;

/** Exception levée lorsqu'une maison n'est pas trouvée */
public class JeuNotFoundException extends Exception{
    /**
     * Constructeur de l'exception MaisonNotFoundException.
     * @param message Le message de l'exception.
     */
    public JeuNotFoundException(String message) {
        super(message);
    }
}