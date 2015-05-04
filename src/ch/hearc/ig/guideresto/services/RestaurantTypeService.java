package ch.hearc.ig.guideresto.services;

import ch.hearc.ig.guideresto.business.RestaurantType;
import ch.hearc.ig.guideresto.exceptions.ConnectionProblemException;
import ch.hearc.ig.guideresto.log.MyLogger;
import ch.hearc.ig.guideresto.persistence.dao.AbstractDAOFactory;
import java.util.Set;
import java.util.logging.Level;

/**
 *
 * @author julien.plumez 
 */
public abstract class RestaurantTypeService {

    public static Set<RestaurantType> findAllRestaurantTypes() throws ConnectionProblemException{
        Set<RestaurantType> list = null;
        try{
            list = AbstractDAOFactory.getDAOFactory().getRestaurantTypeDAO().researchAll();
        }catch(ConnectionProblemException e){
            MyLogger.getInstance().log(Level.SEVERE, null, e);
            throw e;
        }finally{
            AbstractDAOFactory.getDAOFactory().closeConnection();
        }
        return list;
    }
    
}
