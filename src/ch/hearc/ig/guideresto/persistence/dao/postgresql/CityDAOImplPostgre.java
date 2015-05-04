package ch.hearc.ig.guideresto.persistence.dao.postgresql;

import ch.hearc.ig.guideresto.business.City;
import ch.hearc.ig.guideresto.business.RestaurantType;
import ch.hearc.ig.guideresto.exceptions.ConnectionProblemException;
import ch.hearc.ig.guideresto.persistence.dao.CityDAO;
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
public class CityDAOImplPostgre extends AbstractDAOPostgreSQL implements CityDAO{
    
    @Override
    public City researchById(Integer id)  throws ConnectionProblemException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        City city;
        
        try {
            String query = "SELECT numero, code_postal, nom_ville FROM villes WHERE numero = ?";
            
            stmt = getConnection().prepareStatement(query);        
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
           
            // On ajoute les restaurants à la liste
            if(rs.next()){
                city = getCity(rs);
            }else{
                throw new ConnectionProblemException("Couldn't find a City with ID " + id);
            }
        }catch (ConnectionProblemException ex) {
            throw ex;
        }catch (SQLException sqlE){
            throw new ConnectionProblemException("A problem appeared while loading a city with ID " + id, sqlE);
        }finally {
            closeStmtAndRS(stmt, rs);
        }
        return city;
    }
    
    @Override
    public Set<City> researchAll()  throws ConnectionProblemException{
        Statement stmt = null;
        ResultSet rs = null;
        
        Set <City> cities = new HashSet<>();
        
        try {
            stmt = getConnection().createStatement();        
            String query = "SELECT numero, code_postal, nom_ville FROM villes";
            
            rs = stmt.executeQuery(query);
           
            // On ajoute les restaurants à la liste
            while(rs.next()){
                cities.add(getCity(rs));
            }
        }catch (ConnectionProblemException ex) {
            throw ex;
        }catch (SQLException sqlE){
            throw new ConnectionProblemException("A problem appeared while loading all cities", sqlE);
        }finally {
            closeStmtAndRS(stmt, rs);
        }
        return cities;
    }
    
    @Override
    public City insert(String zipCode, String name) throws ConnectionProblemException{
        City city = new City(null, zipCode, name);
        
        this.insert(city);
        
        return city;
    }
    
    @Override
    public void insert(City city) throws ConnectionProblemException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        final String generatedColumns[] = {"numero"};
        
        try {
            String query = "INSERT INTO villes (code_postal, nom_ville) VALUES (?,?)";
            stmt = getConnection().prepareStatement(query, generatedColumns); 
        
            stmt.setString(1,city.getZipCode());
            stmt.setString(2,city.getCityName());
            
            int rowCount = stmt.executeUpdate();
            
            if(rowCount != 1){
                throw new ConnectionProblemException("A problem appeared while inserting a city !");
            }
            
            rs = stmt.getGeneratedKeys();
            
            if(rs.next()){
                city.setId(rs.getInt(1));
            }
            
        }catch (ConnectionProblemException ex) {
            throw ex;
        }catch (SQLException sqlE){
            throw new ConnectionProblemException("A problem appeared while inserting a new city", sqlE);
        }finally {
            closeStmtAndRS(stmt, rs);
        }
    }
    
    private City getCity(ResultSet rs) throws SQLException{
        City city = new City();
        city.setId(rs.getInt("NUMERO"));
        city.setZipCode(rs.getString("CODE_POSTAL"));
        city.setCityName(rs.getString("NOM_VILLE"));
       
        return city; 
    }
}
