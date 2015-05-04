package ch.hearc.ig.guideresto.services;

import ch.hearc.ig.guideresto.business.BasicEvaluation;
import ch.hearc.ig.guideresto.business.CompleteEvaluation;
import ch.hearc.ig.guideresto.business.EvaluationCriteria;
import ch.hearc.ig.guideresto.business.Grade;
import ch.hearc.ig.guideresto.business.Restaurant;
import ch.hearc.ig.guideresto.exceptions.CommitException;
import ch.hearc.ig.guideresto.exceptions.ConnectionProblemException;
import ch.hearc.ig.guideresto.exceptions.RollbackException;
import ch.hearc.ig.guideresto.log.MyLogger;
import ch.hearc.ig.guideresto.persistence.dao.AbstractDAOFactory;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

/**
 *
 * @author julien.plumez 
 */
public abstract class EvaluationService {

    public static BasicEvaluation createBasicEvaluation(Restaurant restaurant, Date date, String ipAddress, boolean evaluation) throws ConnectionProblemException{
        BasicEvaluation newEval = null;
        try {
            newEval = AbstractDAOFactory.getDAOFactory().getBasicEvaluationDAO().insertBasicEvaluation(restaurant, date, ipAddress, evaluation);
            AbstractDAOFactory.getDAOFactory().commit();
            restaurant.addEvaluation(newEval);
        } catch (ConnectionProblemException ex) {
            MyLogger.getInstance().log(Level.SEVERE, null, ex);
            try {
                AbstractDAOFactory.getDAOFactory().rollback();
            } catch (RollbackException ex1) {
                MyLogger.getInstance().log(Level.SEVERE, null, ex1);
            }
            throw ex;
        } catch (CommitException ex) {
            MyLogger.getInstance().log(Level.SEVERE, null, ex);
            try {
                AbstractDAOFactory.getDAOFactory().rollback();
            } catch (RollbackException ex1) {
                MyLogger.getInstance().log(Level.SEVERE, null, ex1);
            }
            throw new ConnectionProblemException("Error while saving the new basic evaluation", ex);
        }
        
        return newEval;
    }
    
    public static CompleteEvaluation createCompleteEvaluation(Restaurant restaurant, Date date, String username, String comment, Map<EvaluationCriteria, Integer> grades) throws ConnectionProblemException{
        CompleteEvaluation newEval = null;
        try {
            newEval = AbstractDAOFactory.getDAOFactory().getCompleteEvaluationDAO().insertCompleteEvaluation(restaurant, date, username, comment);
            
            Grade newGrade;
            for(EvaluationCriteria currentCrit : grades.keySet()){
                newGrade = AbstractDAOFactory.getDAOFactory().getGradeDAO().insertGrade(currentCrit, newEval, grades.get(currentCrit));
                newEval.addGrade(newGrade);
            }
            
            AbstractDAOFactory.getDAOFactory().commit();
            restaurant.addEvaluation(newEval);
        } catch (ConnectionProblemException ex) {
            MyLogger.getInstance().log(Level.SEVERE, null, ex);
            try {
                AbstractDAOFactory.getDAOFactory().rollback();
            } catch (RollbackException ex1) {
                MyLogger.getInstance().log(Level.SEVERE, null, ex1);
            }
            throw ex;
        } catch (CommitException ex) {
            MyLogger.getInstance().log(Level.SEVERE, null, ex);
            try {
                AbstractDAOFactory.getDAOFactory().rollback();
            } catch (RollbackException ex1) {
                MyLogger.getInstance().log(Level.SEVERE, null, ex1);
            }
            throw new ConnectionProblemException("Error while saving the new complete evaluation", ex);
        }
        
        return newEval;
    }
    
    public static Set<EvaluationCriteria> findAllCriterias() throws ConnectionProblemException{
        Set<EvaluationCriteria> list = null;
        try{
            list = AbstractDAOFactory.getDAOFactory().getEvaluationCriteriaDAO().researchAll();
        }catch(ConnectionProblemException e){
            MyLogger.getInstance().log(Level.SEVERE, null, e);
            throw e;
        }finally{
            AbstractDAOFactory.getDAOFactory().closeConnection();
        }
        return list;
    }
    
}
