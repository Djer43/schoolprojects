package ch.hearc.ig.guideresto.persistence.dao;

import ch.hearc.ig.guideresto.business.BasicEvaluation;
import ch.hearc.ig.guideresto.business.Restaurant;
import ch.hearc.ig.guideresto.exceptions.ConnectionProblemException;
import java.util.Date;

/**
 *
 * @author julien.plumez
 */
public interface BasicEvaluationDAO {
    
    public BasicEvaluation insertBasicEvaluation(Restaurant restaurant, Date date, String ipAddress, boolean evaluation) throws ConnectionProblemException;
    public void insertBasicEvaluation(BasicEvaluation basicEval) throws ConnectionProblemException;
    public void deleteWithRestaurantId(Integer restaurantId) throws ConnectionProblemException;
    public void loadEvaluations(Restaurant restaurant) throws ConnectionProblemException;
}
