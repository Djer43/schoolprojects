/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.guideresto.exceptions;

/**
 *
 * @author mitch
 */
public class ConnectionProblemException extends Exception {
    
    public ConnectionProblemException() {
        super();
    }
    
    public ConnectionProblemException(String msg) {
        super(msg);
    }
    
    public ConnectionProblemException(Throwable t) {
        super(t);
    }
    
    public ConnectionProblemException(String msg, Throwable t) {
        super(msg, t);
    }
    
}
