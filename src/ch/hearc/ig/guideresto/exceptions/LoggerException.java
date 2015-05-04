package ch.hearc.ig.guideresto.exceptions;

/**
 *  Cette exception hérite de RuntimeException pour éviter de devoir mettre des try..catch partout dans l'application.
 * @author Julien Plumez
 */
public class LoggerException extends RuntimeException {
    
    public LoggerException() {
        super();
    }
    
    public LoggerException(String msg) {
        super(msg);
    }
    
    public LoggerException(Throwable t) {
        super(t);
    }
    
    public LoggerException(String msg, Throwable t) {
        super(msg, t);
    }
    
}
