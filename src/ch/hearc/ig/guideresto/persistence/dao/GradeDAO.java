package ch.hearc.ig.guideresto.persistence.dao;

import ch.hearc.ig.guideresto.business.CompleteEvaluation;
import ch.hearc.ig.guideresto.business.EvaluationCriteria;
import ch.hearc.ig.guideresto.business.Grade;
import ch.hearc.ig.guideresto.exceptions.ConnectionProblemException;

/**
 *
 * @author julien.plumez 
 */
public interface GradeDAO {

    public Grade insertGrade(EvaluationCriteria criteria, CompleteEvaluation eval, Integer note) throws ConnectionProblemException;
    public void insertGrade(Grade grade) throws ConnectionProblemException;
    public void deleteWithRestaurantId(Integer restaurantId) throws ConnectionProblemException;
    public void loadGrades(CompleteEvaluation eval) throws ConnectionProblemException;
    
}
