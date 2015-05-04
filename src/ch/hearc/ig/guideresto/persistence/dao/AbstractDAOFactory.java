package ch.hearc.ig.guideresto.persistence.dao;

import ch.hearc.ig.guideresto.exceptions.CommitException;
import ch.hearc.ig.guideresto.exceptions.ConnectionProblemException;
import ch.hearc.ig.guideresto.exceptions.InvalidDatasourceProvider;
import ch.hearc.ig.guideresto.exceptions.RollbackException;
import ch.hearc.ig.guideresto.persistence.dao.oracle.OracleDAOFactory;
import ch.hearc.ig.guideresto.persistence.dao.postgresql.PostgreDAOFactory;
import ch.hearc.ig.guideresto.utils.PropertyReader;
import ch.hearc.ig.guideresto.utils.ReadFileProperties;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author julien.plumez
 */
public abstract class AbstractDAOFactory {
    
    private static String datasource = null;
    
    public abstract RestaurantDAO getRestaurantDAO();
    public abstract RestaurantTypeDAO getRestaurantTypeDAO();
    public abstract BasicEvaluationDAO getBasicEvaluationDAO();
    public abstract CompleteEvaluationDAO getCompleteEvaluationDAO();
    public abstract EvaluationCriteriaDAO getEvaluationCriteriaDAO();
    public abstract GradeDAO getGradeDAO();
    public abstract CityDAO getCityDAO();
    
    public abstract Connection getConnection() throws ConnectionProblemException;
    public abstract void closeConnection();
    public abstract void commit() throws CommitException;
    public abstract void rollback() throws RollbackException;
    
    /**
     * Returns an instance of a DAOFactory. The used implementation depends on the file META-INF/config.properties
     * @return An instance of a DAOFactory
     * @throws InvalidDatasourceProvider RuntimeException thrown when the datasource is invalid.
     */
    public static AbstractDAOFactory getDAOFactory() {
        // Si l'attribut est null, il faut aller lire le fichier META-INF/config.properties.
        if(datasource == null){
            datasource = readDatasourceFile();
        }
        
        switch(datasource){
            case "oracle" : return OracleDAOFactory.getInstance();
            case "postgresql" : return PostgreDAOFactory.getInstance();
            default : throw new InvalidDatasourceProvider("The config.propeties file contains an invalid datasource.");
        }
        
    }
    
    /**
     * Reads the file META-INF/config.properties to get the datasource and returns the value.
     * @return The value of the property "datasource" in the file
     * @throws InvalidDatasourceProvider RuntimeException thrown when the file doesn't contains a valid datasource.
     */
    private static String readDatasourceFile() throws InvalidDatasourceProvider{
        // On va lire le fichier de properties pour savoir s'il faut utiliser Oracle ou le fichier en DataSource
        PropertyReader rfp = new ReadFileProperties();
        Properties props;
        try {
            props = rfp.read(rfp.find("META-INF//config.properties"));
            String data = props.get("datasource").toString();
            return data;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AbstractDAOFactory.class.getName()).log(Level.SEVERE, null, ex);
            throw new InvalidDatasourceProvider("Couldn't find file 'config.properties' ! ", ex);
        } catch (IOException ex) {
            Logger.getLogger(AbstractDAOFactory.class.getName()).log(Level.SEVERE, null, ex);
            throw new InvalidDatasourceProvider("An IO error appeared while accessing file 'config.properties' ! ", ex);
        }
    }
}
