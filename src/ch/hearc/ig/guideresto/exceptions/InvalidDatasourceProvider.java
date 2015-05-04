package ch.hearc.ig.guideresto.exceptions;

/**
 *
 * @author julien.plumez
 * This exception is thrown when the method AbstractDAOFactory.getDAOFactory can't find a valid Datasource in the config.properties file.
 */
public class InvalidDatasourceProvider extends RuntimeException{
    
    public InvalidDatasourceProvider() {
        super();
    }
    
    public InvalidDatasourceProvider(String msg) {
        super(msg);
    }
    
    public InvalidDatasourceProvider(Throwable t) {
        super(t);
    }
    
    public InvalidDatasourceProvider(String msg, Throwable t) {
        super(msg, t);
    }
    
}
