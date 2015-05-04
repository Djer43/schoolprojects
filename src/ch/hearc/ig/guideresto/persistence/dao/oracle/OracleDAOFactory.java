package ch.hearc.ig.guideresto.persistence.dao.oracle;

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
public class OracleDAOFactory extends AbstractDAOFactory {

    private static OracleDAOFactory instance;
    
    private OracleDAOFactory(){}
    
    public static OracleDAOFactory getInstance(){
        if(instance == null){
            instance = new OracleDAOFactory();
        }
        return instance;
    }
    
    @Override
    public Connection getConnection() throws ConnectionProblemException{
        return OracleConnection.getConnection();
    }
    
    @Override
    public void closeConnection() {
        OracleConnection.closeConnection();
    }
    
    @Override
    public void commit() throws CommitException{
        OracleConnection.commit();
    }
    
    @Override
    public void rollback() throws RollbackException{
        OracleConnection.rollback();
    }

    @Override
    public RestaurantDAO getRestaurantDAO() {
        return new RestaurantDAOImplOracle();
    }

    @Override
    public RestaurantTypeDAO getRestaurantTypeDAO() {
        return new RestaurantTypeDAOImplOracle();
    }

    @Override
    public BasicEvaluationDAO getBasicEvaluationDAO() {
        return new BasicEvaluationDAOImplOracle();
    }

    @Override
    public CompleteEvaluationDAO getCompleteEvaluationDAO() {
        return new CompleteEvaluationDAOImplOracle();
    }

    @Override
    public EvaluationCriteriaDAO getEvaluationCriteriaDAO() {
        return new EvaluationCriteriaDAOImplOracle();
    }

    @Override
    public GradeDAO getGradeDAO() {
        return new GradeDAOImplOracle();
    }
    
    @Override
    public CityDAO getCityDAO(){
        return new CityDAOImplOracle();
    }
}
