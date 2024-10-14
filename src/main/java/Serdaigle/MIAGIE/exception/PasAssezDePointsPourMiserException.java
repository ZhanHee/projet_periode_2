package Serdaigle.MIAGIE.exception;
/** Exception levée lorsqu'un joueur n'a pas assez de points pour miser */
public class PasAssezDePointsPourMiserException extends Exception{
    /**
     * Constructeur
     * @param message le message de l'exception
     */
    public PasAssezDePointsPourMiserException(String message) {

        super(message);
    }

}



