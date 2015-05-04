/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.guideresto.exceptions;

/**
 *
 * @author mitch
 */
public class RollbackException extends Exception {
    
    public RollbackException() {
        super();
    }
    
    public RollbackException(String msg) {
        super(msg);
    }
    
    public RollbackException(Throwable t) {
        super(t);
    }
    
    public RollbackException(String msg, Throwable t) {
        super(msg, t);
    }
    
}
