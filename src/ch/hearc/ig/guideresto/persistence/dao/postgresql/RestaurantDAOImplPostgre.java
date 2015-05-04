package ch.hearc.ig.guideresto.persistence.dao.postgresql;

import ch.hearc.ig.guideresto.persistence.dao.oracle.*;
import ch.hearc.ig.guideresto.business.City;
import ch.hearc.ig.guideresto.business.Localisation;
import ch.hearc.ig.guideresto.business.Restaurant;
import ch.hearc.ig.guideresto.business.RestaurantType;
import ch.hearc.ig.guideresto.exceptions.ConnectionProblemException;
import ch.hearc.ig.guideresto.persistence.dao.AbstractDAOFactory;
import ch.hearc.ig.guideresto.persistence.dao.RestaurantDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Julien Plumez
 */
public class RestaurantDAOImplPostgre extends AbstractDAOPostgreSQL implements RestaurantDAO {
    
    /**
     * Cette méthode va chercher tous les Restaurants de la BD
     * @return retourne un tableau de Restaurant
     */
    @Override
    public Set<Restaurant> researchAll() throws ConnectionProblemException{
        Statement stmt = null;
        ResultSet rs = null;
        
        Set<Restaurant> restaurants = new HashSet<>();
        
        try {
            stmt = getConnection().createStatement();        
            String query = "SELECT numero,nom,adresse,description,site_web,fk_type, fk_vill FROM restaurants";
            
            rs = stmt.executeQuery(query);
           
            // On ajoute les restaurants à la liste
            while(rs.next()){
                restaurants.add(getRestaurant(rs));
            }
        }catch (ConnectionProblemException ex) {
            throw ex;
        }catch (SQLException sqlE){
            throw new ConnectionProblemException("A problem appeared while loading all restaurants", sqlE);
        }finally {
            closeStmtAndRS(stmt, rs);
        }
        return restaurants;
    }
    
    @Override
    public Set<Restaurant> researchByName(String name)  throws ConnectionProblemException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        Set<Restaurant> restaurants = new HashSet<>();
        Restaurant currentRestaurant = null;
        
        try {
            String query = "SELECT numero,nom,adresse, description,site_web,fk_type, fk_vill FROM restaurants WHERE UPPER(nom) LIKE UPPER(?)";
            
            stmt = getConnection().prepareStatement(query);        
            name = "%" + name + "%";
            stmt.setString(1, name);
            
            rs = stmt.executeQuery();
           
            // On ajoute les restaurants à la liste
            while(rs.next()){
                currentRestaurant = getRestaurant(rs);
                restaurants.add(currentRestaurant);
            }
        }catch (ConnectionProblemException ex) {
            throw ex;
        }catch (SQLException sqlE){
            throw new ConnectionProblemException("A problem appeared while loading restaurants by name", sqlE);
        }finally {
            closeStmtAndRS(stmt, rs);
        }
        return restaurants;
    }
    
    @Override
    public Set<Restaurant> researchByCityName(String cityName) throws ConnectionProblemException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        Set<Restaurant> restaurants = new HashSet<>();
        
        try {
            String query = "SELECT restaurants.numero,nom,adresse,description,site_web,fk_type, fk_vill FROM restaurants INNER JOIN villes " +
                    "ON fk_vill = villes.numero WHERE UPPER(nom_ville) LIKE UPPER(?)";
            
            stmt = getConnection().prepareStatement(query); 
            cityName = "%" + cityName + "%";
            stmt.setString(1, cityName);
            
            rs = stmt.executeQuery();
           
            // On ajoute les restaurants à la liste
            while(rs.next()){
                restaurants.add(getRestaurant(rs));
            }
        }catch (ConnectionProblemException ex) {
            throw ex;
        }catch (SQLException sqlE){
            throw new ConnectionProblemException("A problem appeared while loading restaurants by name", sqlE);
        }finally {
            closeStmtAndRS(stmt, rs);
        }
        return restaurants;
    }
    
    @Override
    public Set<Restaurant> researchByType(RestaurantType type) throws ConnectionProblemException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        Set<Restaurant> restaurants = new HashSet<>();
        Restaurant currentRestaurant = null;
        
        try {
            String query = "SELECT numero,nom,adresse,description,site_web,fk_type, fk_vill FROM restaurants WHERE fk_type = ?";
            
            stmt = getConnection().prepareStatement(query); 
            stmt.setInt(1, type.getId());
            
            rs = stmt.executeQuery();
           
            // On ajoute les restaurants à la liste
            while(rs.next()){
                currentRestaurant = getRestaurant(rs);
                currentRestaurant.setType(type);
                restaurants.add(currentRestaurant);
            }
            
            type.setRestaurants(restaurants);
        }catch (ConnectionProblemException ex) {
            throw ex;
        }catch (SQLException sqlE){
            throw new ConnectionProblemException("A problem appeared while loading restaurants by name", sqlE);
        }finally {
            closeStmtAndRS(stmt, rs);
        }
        return restaurants;
    }
    
    @Override
    public Restaurant insert(String name, String street, String description, String website, RestaurantType type, City city) throws ConnectionProblemException{
        Restaurant restaurant = new Restaurant(null, name, description, website, street, city, type);
        
        this.insert(restaurant);
        
        return restaurant;
    }
    
    @Override
    public void insert(Restaurant restaurant) throws ConnectionProblemException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Restaurant newRestaurant = null;
        final String generatedColumns[] = {"numero"};
        
        try {
            String query = "INSERT INTO restaurants (nom,adresse,description,site_web,fk_type,fk_vill) VALUES (?,?,?,?,?,?)";
            stmt = getConnection().prepareStatement(query, generatedColumns); 
        
            stmt.setString(1,restaurant.getName());
            stmt.setString(2,restaurant.getAddress().getStreet());
            stmt.setString(3,restaurant.getDescription());
            stmt.setString(4,restaurant.getWebsite());
            stmt.setInt(5,restaurant.getType().getId());
            stmt.setInt(6,restaurant.getAddress().getCity().getId());
            
            int rowCount = stmt.executeUpdate();
            
            if(rowCount != 1){
                throw new ConnectionProblemException("A problem appeared while inserting a restaurant !");
            }
            
            rs = stmt.getGeneratedKeys();
            
            if(rs.next()){
                restaurant.setId(rs.getInt(1));
            }
            
        }catch (ConnectionProblemException ex) {
            throw ex;
        }catch (SQLException sqlE){
            throw new ConnectionProblemException("A problem appeared while inserting a new restaurant", sqlE);
        }finally {
            closeStmtAndRS(stmt, rs);
        }
    }
    
    @Override
    public void update(Restaurant restaurant) throws ConnectionProblemException{
        PreparedStatement stmt = null;
        
        try {
            String query = "UPDATE restaurants SET nom = ?, adresse = ?, description = ?, site_web = ?, fk_type = ?, fk_vill = ? WHERE numero = ?";
            stmt = getConnection().prepareStatement(query); 
        
            stmt.setString(1,restaurant.getName());
            stmt.setString(2,restaurant.getAddress().getStreet());
            stmt.setString(3,restaurant.getDescription());
            stmt.setString(4, restaurant.getWebsite());
            stmt.setInt(5, restaurant.getType().getId());
            stmt.setInt(6, restaurant.getAddress().getCity().getId());
            stmt.setInt(7, restaurant.getId());
            int rowCount = stmt.executeUpdate();
            
            if(rowCount != 1){
                throw new ConnectionProblemException("Error : update statement returned " + rowCount + " rows instead of 1.");
            }
        }catch (SQLException sqlE){
            throw new ConnectionProblemException("A problem appeared while updating a restaurant", sqlE);
        }finally {
            closeStmtAndRS(stmt, null);
        }
    }
    
    @Override
    public void delete(Restaurant restaurant) throws ConnectionProblemException{
        this.delete(restaurant.getId());
    }
    
    @Override
    public void delete(Integer restaurantID) throws ConnectionProblemException{
        PreparedStatement stmt = null;
        
        try {
            String query = "DELETE FROM restaurants WHERE numero = ?";
            stmt = getConnection().prepareStatement(query); 

            stmt.setInt(1, restaurantID);
            int rowCount = stmt.executeUpdate();
            
            if(rowCount != 1){
                throw new ConnectionProblemException("Error : delete statement returned " + rowCount + " rows instead of 1.");
            }
        }catch (SQLException sqlE){
            throw new ConnectionProblemException("A problem appeared while deleting a restaurant", sqlE);
        }finally {
            closeStmtAndRS(stmt, null);
        }
    }

    private Restaurant getRestaurant(ResultSet rs) throws SQLException, ConnectionProblemException{
        Restaurant restaurant = new Restaurant();
        restaurant.setId(rs.getInt("NUMERO"));
        restaurant.setName(rs.getString("NOM"));
        restaurant.setDescription(rs.getString("DESCRIPTION"));
        restaurant.setWebsite(rs.getString("SITE_WEB"));
        
        City city = AbstractDAOFactory.getDAOFactory().getCityDAO().researchById(rs.getInt("fk_vill"));
        restaurant.setAddress(new Localisation(rs.getString("ADRESSE"), city));
       
        restaurant.setType(AbstractDAOFactory.getDAOFactory().getRestaurantTypeDAO().researchById(rs.getInt("fk_type")));
        
        return restaurant; 
    }
    
}
