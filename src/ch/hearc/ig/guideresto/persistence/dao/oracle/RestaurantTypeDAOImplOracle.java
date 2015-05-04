package ch.hearc.ig.guideresto.persistence.dao.oracle;

import ch.hearc.ig.guideresto.business.RestaurantType;
import ch.hearc.ig.guideresto.exceptions.ConnectionProblemException;
import ch.hearc.ig.guideresto.persistence.dao.RestaurantTypeDAO;
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
public class RestaurantTypeDAOImplOracle extends AbstractDAOOracle implements RestaurantTypeDAO{
    
    @Override
    public RestaurantType researchById(Integer id) throws ConnectionProblemException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        RestaurantType type;
        
        try {
            String query = "SELECT numero,libelle, description FROM types_gastronomiques WHERE numero = ?";
            
            stmt = getConnection().prepareStatement(query);        
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
           
            // On ajoute les restaurants à la liste
            if(rs.next()){
                type = getRestaurantType(rs);
            }else{
                throw new ConnectionProblemException("Couldn't find a RestaurantType with ID " + id);
            }
        }catch (ConnectionProblemException ex) {
            throw ex;
        }catch (SQLException sqlE){
            throw new ConnectionProblemException("A problem appeared while loading all restaurant types", sqlE);
        }finally {
            closeStmtAndRS(stmt, rs);
        }
        return type;
    }
    
    @Override
    public Set<RestaurantType> researchAll()  throws ConnectionProblemException{
        Statement stmt = null;
        ResultSet rs = null;
        
        Set <RestaurantType> restaurantTypes = new HashSet<>();
        RestaurantType currentType = null;
        
        try {
            stmt = getConnection().createStatement();        
            String query = "SELECT numero,libelle, description FROM types_gastronomiques";
            
            rs = stmt.executeQuery(query);
           
            // On ajoute les restaurants à la liste
            while(rs.next()){
                currentType = getRestaurantType(rs);
                // On en profite pour charger l'adresse du restaurant
                restaurantTypes.add(currentType);
            }
        }catch (ConnectionProblemException ex) {
            throw ex;
        }catch (SQLException sqlE){
            throw new ConnectionProblemException("A problem appeared while loading all restaurant types", sqlE);
        }finally {
            closeStmtAndRS(stmt, rs);
        }
        return restaurantTypes;
    }
    
    private RestaurantType getRestaurantType(ResultSet rs) throws SQLException{
        RestaurantType type = new RestaurantType();
        type.setId(rs.getInt("NUMERO"));
        type.setLabel(rs.getString("LIBELLE"));
        type.setDescription(rs.getString("DESCRIPTION"));
       
        return type; 
    }
}
