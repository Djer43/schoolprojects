package ch.hearc.ig.guideresto.persistence.dao.postgresql;

import ch.hearc.ig.guideresto.exceptions.CommitException;
import ch.hearc.ig.guideresto.exceptions.ConnectionProblemException;
import ch.hearc.ig.guideresto.exceptions.RollbackException;
import ch.hearc.ig.guideresto.persistence.dao.AbstractDAOFactory;
import ch.hearc.ig.guideresto.persistence.dao.BasicEvaluationDAO;
import ch.hearc.ig.guideresto.persistence.dao.CityDAO;
import ch.hearc.ig.guideresto.persistence.dao.CompleteEvaluationDAO;
import ch.hearc.ig.guideresto.persistence.dao.EvaluationCriteriaDAO;
import ch.hearc.ig.guideresto.persistence.dao.GradeDAO;
import ch.hearc.ig.guideresto.persistence.dao.RestaurantDAO;
import ch.hearc.ig.guideresto.persistence.dao.RestaurantTypeDAO;
import java.sql.Connection;

/**
 *
 * @author baudetc
 */
public class PostgreDAOFactory extends AbstractDAOFactory {

    private static PostgreDAOFactory instance;
    
    private PostgreDAOFactory(){}
    
    public static PostgreDAOFactory getInstance(){
        if(instance == null){
            instance = new PostgreDAOFactory();
        }
        return instance;
    }
    
    @Override
    public Connection getConnection() throws ConnectionProblemException{
        return PostgreConnection.getConnection();
    }
    
    @Override
    public void closeConnection() {
        PostgreConnection.closeConnection();
    }
    
    @Override
    public void commit() throws CommitException{
        PostgreConnection.commit();
    }
    
    @Override
    public void rollback() throws RollbackException{
        PostgreConnection.rollback();
    }

    @Override
    public RestaurantDAO getRestaurantDAO() {
        return new RestaurantDAOImplPostgre();
    }

    @Override
    public RestaurantTypeDAO getRestaurantTypeDAO() {
        return new RestaurantTypeDAOImplPostgre();
    }

    @Override
    public BasicEvaluationDAO getBasicEvaluationDAO() {
        return new BasicEvaluationDAOImplPostgre();
    }

    @Override
    public CompleteEvaluationDAO getCompleteEvaluationDAO() {
        return new CompleteEvaluationDAOImplPostgre();
    }

    @Override
    public EvaluationCriteriaDAO getEvaluationCriteriaDAO() {
        return new EvaluationCriteriaDAOImplPostgre();
    }

    @Override
    public GradeDAO getGradeDAO() {
        return new GradeDAOImplPostgre();
    }
    
    @Override
    public CityDAO getCityDAO(){
        return new CityDAOImplPostgre();
    }
}
