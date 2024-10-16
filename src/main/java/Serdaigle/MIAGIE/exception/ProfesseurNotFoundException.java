package Serdaigle.MIAGIE.exception;
/** Exception levée lorsqu'un professeur n'est pas trouvé */
public class ProfesseurNotFoundException extends RuntimeException {

    /**
     * Constructeur de l'exception
     * @param message le message d'erreur
     */
    public ProfesseurNotFoundException(String message) {
        super(message);
    }
}
