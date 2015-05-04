package ch.hearc.ig.guideresto.services;

import ch.hearc.ig.guideresto.business.City;
import ch.hearc.ig.guideresto.business.Restaurant;
import ch.hearc.ig.guideresto.business.RestaurantType;
import ch.hearc.ig.guideresto.exceptions.CommitException;
import ch.hearc.ig.guideresto.exceptions.ConnectionProblemException;
import ch.hearc.ig.guideresto.exceptions.RollbackException;
import ch.hearc.ig.guideresto.log.MyLogger;
import ch.hearc.ig.guideresto.persistence.dao.AbstractDAOFactory;
import java.util.Set;
import java.util.logging.Level;

/**
 *
 * @author julien.plumez 
 */
public abstract class RestaurantService {

    public static Set<Restaurant> findAllRestaurants() throws ConnectionProblemException{
        Set<Restaurant> list = null;
        try{
            list = AbstractDAOFactory.getDAOFactory().getRestaurantDAO().researchAll();
        }catch(ConnectionProblemException e){
            MyLogger.getInstance().log(Level.SEVERE, null, e);
            throw e;
        }finally{
            AbstractDAOFactory.getDAOFactory().closeConnection();
        }
        return list;
    }
    
    public static Set<Restaurant> findRestaurantsByName(String name) throws ConnectionProblemException{
        Set<Restaurant> list = null;
        try{
            list = AbstractDAOFactory.getDAOFactory().getRestaurantDAO().researchByName(name);
        }catch(ConnectionProblemException e){
            MyLogger.getInstance().log(Level.SEVERE, null, e);
            throw e;
        }finally{
            AbstractDAOFactory.getDAOFactory().closeConnection();
        }
        return list;
    }
    
    public static Set<Restaurant> findRestaurantsByCityName(String cityName) throws ConnectionProblemException{
        Set<Restaurant> list = null;
        try{
            list = AbstractDAOFactory.getDAOFactory().getRestaurantDAO().researchByCityName(cityName);
        }catch(ConnectionProblemException e){
            MyLogger.getInstance().log(Level.SEVERE, null, e);
            throw e;
        }finally{
            AbstractDAOFactory.getDAOFactory().closeConnection();
        }
        return list;
    }
    
    public static Set<Restaurant> findRestaurantsByType(RestaurantType type) throws ConnectionProblemException{
        Set<Restaurant> list = null;
        try{
            list = AbstractDAOFactory.getDAOFactory().getRestaurantDAO().researchByType(type);
        }catch(ConnectionProblemException e){
            MyLogger.getInstance().log(Level.SEVERE, null, e);
            throw e;
        }finally{
            AbstractDAOFactory.getDAOFactory().closeConnection();
        }
        return list;
    }
    
    public static Restaurant createRestaurant(String name, String description, String website, String street, City city, RestaurantType type) throws ConnectionProblemException{
        Restaurant newRestaurant = null;
        try {
            // On insère d'abord le restaurant, puis l'adresse puisque c'est elle qui a la clé étrangère du restaurant.
            // Si un problème survient, on rollback et on lève une exception
            newRestaurant = AbstractDAOFactory.getDAOFactory().getRestaurantDAO().insert(name,street, description, website, type, city);
            AbstractDAOFactory.getDAOFactory().commit();
            // Maintenant qu'on a mis à jour la BDD avec succès, on met à jour les objets !
            city.addRestaurant(newRestaurant);
            type.addRestaurant(newRestaurant);
        } catch (ConnectionProblemException ex) {
            try {
                AbstractDAOFactory.getDAOFactory().rollback();
            } catch (RollbackException ex1) {
                MyLogger.getInstance().log(Level.SEVERE, null, ex1);
            }
            MyLogger.getInstance().log(Level.SEVERE, null, ex);
            throw ex;
        } catch (CommitException ex) {
            try {
                AbstractDAOFactory.getDAOFactory().rollback();
            } catch (RollbackException ex1) {
                MyLogger.getInstance().log(Level.SEVERE, null, ex1);
            }
            MyLogger.getInstance().log(Level.SEVERE, null, ex);
            throw new ConnectionProblemException("A problem appeared while inserting the new restaurant and localisation in the database");
        } finally {
            AbstractDAOFactory.getDAOFactory().closeConnection();
        }
        
        return newRestaurant;
    }
    
    public static void updateRestaurant(Restaurant restaurant) throws ConnectionProblemException{
        try {
            AbstractDAOFactory.getDAOFactory().getRestaurantDAO().update(restaurant);
            AbstractDAOFactory.getDAOFactory().commit();
        } catch (ConnectionProblemException ex) {
            try {
                AbstractDAOFactory.getDAOFactory().rollback();
            } catch (RollbackException ex1) {
                MyLogger.getInstance().log(Level.SEVERE, null, ex1);
            }
            MyLogger.getInstance().log(Level.SEVERE, null, ex);
            throw ex;
        } catch (CommitException ex) {
            try {
                AbstractDAOFactory.getDAOFactory().rollback();
            } catch (RollbackException ex1) {
                MyLogger.getInstance().log(Level.SEVERE, null, ex1);
            }
            MyLogger.getInstance().log(Level.SEVERE, null, ex);
            throw new ConnectionProblemException("A problem appeared while inserting the new restaurant and localisation in the database", ex);
        } finally {
            AbstractDAOFactory.getDAOFactory().closeConnection();
        }
    }
    
    public static void deleteRestaurant(Restaurant restaurant) throws ConnectionProblemException{
        try {
            AbstractDAOFactory.getDAOFactory().getBasicEvaluationDAO().deleteWithRestaurantId(restaurant.getId());
            AbstractDAOFactory.getDAOFactory().getCompleteEvaluationDAO().deleteWithRestaurantId(restaurant.getId());
            AbstractDAOFactory.getDAOFactory().getRestaurantDAO().delete(restaurant);
            AbstractDAOFactory.getDAOFactory().commit();
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
            throw new ConnectionProblemException("A problem appeared while deleteing the restaurant in the database");
        } finally {
            AbstractDAOFactory.getDAOFactory().closeConnection();
        }
    }
    
    public static City createCity(String cityZipCode, String cityName) throws ConnectionProblemException{
        City newCity = null;
        try {
            // Si un problème survient, on rollback et on lève une exception
            newCity = AbstractDAOFactory.getDAOFactory().getCityDAO().insert(cityZipCode, cityName);
            AbstractDAOFactory.getDAOFactory().commit();
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
            throw new ConnectionProblemException("A problem appeared while inserting the new city in the database");
        } finally {
            AbstractDAOFactory.getDAOFactory().closeConnection();
        }
        
        return newCity;
    }
    
    public static Set<City> findAllCities() throws ConnectionProblemException{
        Set<City> cities = null;
        try{
            cities = AbstractDAOFactory.getDAOFactory().getCityDAO().researchAll();
        }catch(ConnectionProblemException e){
            MyLogger.getInstance().log(Level.SEVERE, null, e);
            throw e;
        }finally{
            AbstractDAOFactory.getDAOFactory().closeConnection();
        }
        return cities;
    }
    
    public static void loadEvaluations(Restaurant restaurant) throws ConnectionProblemException{
        try{
            AbstractDAOFactory.getDAOFactory().getBasicEvaluationDAO().loadEvaluations(restaurant);
            AbstractDAOFactory.getDAOFactory().getCompleteEvaluationDAO().loadEvaluations(restaurant);
        }catch(ConnectionProblemException e){
            MyLogger.getInstance().log(Level.SEVERE, null, e);
            throw e;
        }finally{
            AbstractDAOFactory.getDAOFactory().closeConnection();
        }
    }
}
