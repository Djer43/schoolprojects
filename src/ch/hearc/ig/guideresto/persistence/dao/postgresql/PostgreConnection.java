
package ch.hearc.ig.guideresto.persistence.dao.postgresql;

import ch.hearc.ig.guideresto.exceptions.CommitException;
import ch.hearc.ig.guideresto.exceptions.ConnectionProblemException;
import ch.hearc.ig.guideresto.exceptions.RollbackException;
import ch.hearc.ig.guideresto.log.MyLogger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 *
 * @author baudetc
 */
public abstract class PostgreConnection {

    private static final String DBURL = "jdbc:postgresql://157.26.115.193:5432/coursig";
    private static final String USER = "coursig";
    private static final String PASSWORD = "coursig14";
    private static Connection con = null;

    // method qui créé les connexions pour Oracle
    private static void createConnection() throws ConnectionProblemException{
        try {
            // Se connecter à la BD Oracle
            con = DriverManager.getConnection(DBURL, USER, PASSWORD);
            con.setAutoCommit(false);

        } catch (SQLException ex) {
            throw new ConnectionProblemException(ex);
        } 
    }
    
    public static Connection getConnection() throws ConnectionProblemException{
        if(con==null){
            createConnection();
        }
        return con;
    }

    public static void closeConnection() {
        try {
            if(con != null){
                con.close();
            }
        } catch (SQLException ex) {
            MyLogger.getInstance().log(Level.SEVERE,null, ex);
        }finally{
            con = null;
        }
    }
    
    public static void commit() throws CommitException{
        try {
            con.commit();
        } catch (SQLException ex) {
            throw new CommitException(ex);
        }
    }
    
    public static void rollback() throws RollbackException{
        try {
            con.rollback();
        } catch (SQLException ex) {
            throw new RollbackException(ex);
        }
    }

}
