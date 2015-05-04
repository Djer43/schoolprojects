package ch.hearc.ig.guideresto.persistence.dao;

import ch.hearc.ig.guideresto.business.CompleteEvaluation;
import ch.hearc.ig.guideresto.business.Restaurant;
import ch.hearc.ig.guideresto.exceptions.ConnectionProblemException;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author julien.plumez 
 */
public interface CompleteEvaluationDAO {
    
    public CompleteEvaluation insertCompleteEvaluation(Restaurant restaurant, Date date, String username, String comment) throws ConnectionProblemException;
    public void insertCompleteEvaluation(CompleteEvaluation completeEval) throws ConnectionProblemException;
    public void deleteWithRestaurantId(Integer restaurantId) throws ConnectionProblemException;
    public void loadEvaluations(Restaurant restaurant) throws ConnectionProblemException;
    
}
