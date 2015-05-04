package ch.hearc.ig.guideresto.persistence.dao;

import ch.hearc.ig.guideresto.business.EvaluationCriteria;
import ch.hearc.ig.guideresto.exceptions.ConnectionProblemException;
import java.util.Set;

/**
 *
 * @author julien.plumez 
 */
public interface EvaluationCriteriaDAO {
    
    public Set<EvaluationCriteria> researchAll() throws ConnectionProblemException;
    public EvaluationCriteria researchById(Integer id) throws ConnectionProblemException;
    
}
