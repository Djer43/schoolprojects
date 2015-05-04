package ch.hearc.ig.guideresto.persistence.dao;

import ch.hearc.ig.guideresto.exceptions.ConnectionProblemException;
import java.sql.Connection;

/**
 *
 * @author Julien
 */
public abstract class AbstractDAO {
    
    protected abstract Connection getConnection() throws ConnectionProblemException;
    
}
