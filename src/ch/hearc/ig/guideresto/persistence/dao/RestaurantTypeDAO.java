package ch.hearc.ig.guideresto.persistence.dao;

import ch.hearc.ig.guideresto.business.RestaurantType;
import ch.hearc.ig.guideresto.exceptions.ConnectionProblemException;
import java.util.Set;

/**
 *
 * @author julien.plumez
 */
public interface RestaurantTypeDAO {
    
    public Set<RestaurantType> researchAll()  throws ConnectionProblemException;
    public RestaurantType researchById(Integer id) throws ConnectionProblemException;
    
}
