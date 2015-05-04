/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.guideresto.persistence.dao;

import ch.hearc.ig.guideresto.business.City;
import ch.hearc.ig.guideresto.business.Restaurant;
import ch.hearc.ig.guideresto.business.RestaurantType;
import ch.hearc.ig.guideresto.exceptions.ConnectionProblemException;
import java.util.Set;

/**
 *
 * @author julien.plumez
 */
public interface RestaurantDAO {
    
    public Set<Restaurant> researchAll()  throws ConnectionProblemException;
    public Set<Restaurant> researchByName(String name)  throws ConnectionProblemException;
    public Set<Restaurant> researchByCityName(String cityName) throws ConnectionProblemException;
    public Set<Restaurant> researchByType(RestaurantType type) throws ConnectionProblemException;
    public Restaurant insert(String name, String adress, String description, String website, RestaurantType type, City city) throws ConnectionProblemException;
    public void insert(Restaurant restaurant) throws ConnectionProblemException;
    public void update(Restaurant restaurant) throws ConnectionProblemException;
    public void delete(Restaurant restaurant) throws ConnectionProblemException;
    public void delete(Integer restaurantID) throws ConnectionProblemException;
    
}
