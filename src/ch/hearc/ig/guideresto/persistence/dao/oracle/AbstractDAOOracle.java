package ch.hearc.ig.guideresto.persistence.dao.oracle;

import ch.hearc.ig.guideresto.exceptions.ConnectionProblemException;
import ch.hearc.ig.guideresto.log.MyLogger;
import ch.hearc.ig.guideresto.persistence.dao.AbstractDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;

/**
 *
 * @author julien.plumez
 */
public abstract class AbstractDAOOracle extends AbstractDAO {
    
    @Override
    protected Connection getConnection() throws ConnectionProblemException{
        return OracleConnection.getConnection();
    }
    
    /**
     * We usually do the same thing for every Statements and every ResultSets !
     * That methods tries to close the statement and the result set. If an exception appears, it's just logged.
     * @param stmt The statement to close
     * @param rs The ResultSet to close
     */
    protected void closeStmtAndRS(Statement stmt, ResultSet rs){
        try{
            if(stmt != null){
                stmt.close();
            }
        }catch(Exception e){
            MyLogger.getInstance().log(Level.SEVERE, null, e);
        }
        
        try{
            if(rs != null){
                rs.close();
            }
        }catch(Exception e){
            MyLogger.getInstance().log(Level.SEVERE, null, e);
        }
    }
    
}
