package ch.hearc.ig.guideresto.persistence.dao.oracle;

import ch.hearc.ig.guideresto.business.BasicEvaluation;
import ch.hearc.ig.guideresto.business.Restaurant;
import ch.hearc.ig.guideresto.exceptions.ConnectionProblemException;
import ch.hearc.ig.guideresto.persistence.dao.BasicEvaluationDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Julien Plumez
 */
public class BasicEvaluationDAOImplOracle extends AbstractDAOOracle implements BasicEvaluationDAO{
    
    @Override
    public BasicEvaluation insertBasicEvaluation(Restaurant restaurant, Date date, String ipAddress, boolean evaluation) throws ConnectionProblemException{
        BasicEvaluation basicEval = new BasicEvaluation(null, date, restaurant, evaluation, ipAddress);
        
        this.insertBasicEvaluation(basicEval);
        
        return basicEval;
    }
    
    @Override
    public void insertBasicEvaluation(BasicEvaluation basicEval) throws ConnectionProblemException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        final String generatedColumns[] = {"numero"};
        
        try {
            String query = "INSERT INTO likes (appreciation, date_eval, adresse_ip, fk_rest) VALUES (?,?,?,?)";
            stmt = getConnection().prepareStatement(query, generatedColumns); 
        
            if(basicEval.getLikeRestaurant()){
                stmt.setString(1,"T");
            }else{
                stmt.setString(1, "F");
            }
            stmt.setDate(2, new java.sql.Date(basicEval.getVisitDate().getTime()));
            stmt.setString(3,basicEval.getIpAddress());
            stmt.setInt(4,basicEval.getRestaurant().getId());
            int rowCount = stmt.executeUpdate();
            
            if(rowCount != 1){
                throw new ConnectionProblemException("A problem appeared while inserting a basic evaluation !");
            }
            
            rs = stmt.getGeneratedKeys();
            
            if(rs.next()){
                basicEval.setId(rs.getInt(1));
            }
            
        }catch (ConnectionProblemException ex) {
            throw ex;
        }catch (SQLException sqlE){
            throw new ConnectionProblemException("A problem appeared while inserting a new basic evaluation !", sqlE);
        }finally {
            closeStmtAndRS(stmt, rs);
        }
    }
    
    @Override
    public void deleteWithRestaurantId(Integer restaurantId) throws ConnectionProblemException{
        PreparedStatement stmt = null;
        
        try {
            String query = "DELETE FROM likes WHERE fk_rest = ?";
            stmt = getConnection().prepareStatement(query); 

            stmt.setInt(1, restaurantId);
            stmt.executeUpdate();
        }catch (SQLException sqlE){
            throw new ConnectionProblemException("A problem appeared while deleting basic evaluations with restaurant id " + restaurantId, sqlE);
        }finally {
            closeStmtAndRS(stmt, null);
        }
    }
    
    @Override
    public void loadEvaluations(Restaurant restaurant) throws ConnectionProblemException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            String query = "SELECT numero,appreciation, date_eval, adresse_ip, fk_rest FROM likes WHERE fk_rest = ?";
            
            stmt = getConnection().prepareStatement(query);        
            stmt.setInt(1, restaurant.getId());
            
            rs = stmt.executeQuery();
           
            // On ajoute les restaurants à la liste
            while(rs.next()){
                restaurant.addEvaluation(getBasicEvaluation(rs));
            }
        }catch (SQLException sqlE){
            throw new ConnectionProblemException("A problem appeared while loading a restaurant's evaluations", sqlE);
        }finally {
            closeStmtAndRS(stmt, rs);
        }
    }
    
    private BasicEvaluation getBasicEvaluation(ResultSet rs) throws SQLException, ConnectionProblemException{
        BasicEvaluation basicEval = new BasicEvaluation();
        basicEval.setId(rs.getInt("NUMERO"));
        basicEval.setIpAddress(rs.getString("ADRESSE_IP"));
        if(rs.getString("APPRECIATION").equals("T")){
            basicEval.setLikeRestaurant(true);
        }else{
            basicEval.setLikeRestaurant(false);
        }
        basicEval.setVisitDate(rs.getDate("DATE_EVAL"));
        
        
        return basicEval;
    }
    
    
}
