package Serdaigle.MIAGIE.exception;

/**
 * Exception levée lorsqu'un élève tente d'accepter une proposition sans y être autorisé
 */
public class EleveNonAutoriseAAccepterPropositionException extends Exception {
    /**
     * Constructeur de l'exception EleveNonAutoriseAAccepterPropositionException
     * @param message message d'erreur
     */
    public EleveNonAutoriseAAccepterPropositionException(String message) {
        super(message);
    }
}
