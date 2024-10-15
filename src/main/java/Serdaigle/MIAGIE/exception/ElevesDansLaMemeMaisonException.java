package Serdaigle.MIAGIE.exception;

/**
 * Exception levée lorsqu'un élève est dans la même maison
 */
public class ElevesDansLaMemeMaisonException extends Exception {
    /**
     * Constructeur de l'exception ElevesDansLaMemeMaisonException
     * @param message Message d'erreur
     */
    public ElevesDansLaMemeMaisonException(String message) {

        super(message);
    }

}
