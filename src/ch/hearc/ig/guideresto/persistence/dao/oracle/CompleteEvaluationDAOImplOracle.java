package ch.hearc.ig.guideresto.persistence.dao.oracle;

import ch.hearc.ig.guideresto.business.BasicEvaluation;
import ch.hearc.ig.guideresto.business.CompleteEvaluation;
import ch.hearc.ig.guideresto.business.Restaurant;
import ch.hearc.ig.guideresto.exceptions.ConnectionProblemException;
import ch.hearc.ig.guideresto.persistence.dao.AbstractDAOFactory;
import ch.hearc.ig.guideresto.persistence.dao.CompleteEvaluationDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Julien Plumez
 */
public class CompleteEvaluationDAOImplOracle extends AbstractDAOOracle implements CompleteEvaluationDAO{
    
    @Override
    public CompleteEvaluation insertCompleteEvaluation(Restaurant restaurant, Date date, String username, String comment) throws ConnectionProblemException{
        CompleteEvaluation completeEval = new CompleteEvaluation(null, date, restaurant, comment, username);
        
        this.insertCompleteEvaluation(completeEval);
        
        return completeEval;
    }
    
    @Override
    public void insertCompleteEvaluation(CompleteEvaluation completeEval) throws ConnectionProblemException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        final String generatedColumns[] = {"numero"};
        
        try {
            String query = "INSERT INTO commentaires (date_eval, commentaire, nom_utilisateur, fk_rest) VALUES (?,?,?,?)";
            stmt = getConnection().prepareStatement(query, generatedColumns); 
        
            stmt.setDate(1, new java.sql.Date(completeEval.getVisitDate().getTime()));
            stmt.setString(2, completeEval.getComment());
            stmt.setString(3,completeEval.getUsername());
            stmt.setInt(4,completeEval.getRestaurant().getId());
            
            int rowCount = stmt.executeUpdate();
            
            if(rowCount != 1){
                throw new ConnectionProblemException("A problem appeared while inserting a complete evaluation !");
            }
            
            rs = stmt.getGeneratedKeys();
            
            if(rs.next()){
                completeEval.setId(rs.getInt(1));
            }
            
        }catch (ConnectionProblemException ex) {
            throw ex;
        }catch (SQLException sqlE){
            throw new ConnectionProblemException("A problem appeared while inserting a new complete evaluation !", sqlE);
        }finally {
            closeStmtAndRS(stmt, rs);
        }
    }
    
    @Override
    public void deleteWithRestaurantId(Integer restaurantId) throws ConnectionProblemException{
        PreparedStatement stmt = null;
        
        try {
            // Avant de supprimer les commentaires on supprime toutes les notes qui y sont liées
            AbstractDAOFactory.getDAOFactory().getGradeDAO().deleteWithRestaurantId(restaurantId);
            
            String query = "DELETE FROM commentaires WHERE fk_rest = ?";
            
            stmt = getConnection().prepareStatement(query); 
            
            stmt.setInt(1, restaurantId);
            stmt.executeUpdate();
        }catch (SQLException sqlE){
            throw new ConnectionProblemException("A problem appeared while deleting complete evaluations with restaurant id " + restaurantId, sqlE);
        }finally {
            closeStmtAndRS(stmt, null);
        }
    }
    
    @Override
    public void loadEvaluations(Restaurant restaurant) throws ConnectionProblemException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            String query = "SELECT numero,date_eval, commentaire, nom_utilisateur, fk_rest FROM commentaires WHERE fk_rest = ?";
            
            stmt = getConnection().prepareStatement(query);        
            stmt.setInt(1, restaurant.getId());
            
            rs = stmt.executeQuery();
           
            // On ajoute les restaurants à la liste
            while(rs.next()){
                restaurant.addEvaluation(getCompleteEvaluation(rs));
            }
        }catch (SQLException sqlE){
            throw new ConnectionProblemException("A problem appeared while loading a restaurant's complete evaluations", sqlE);
        }finally {
            closeStmtAndRS(stmt, rs);
        }
    }
    
    private CompleteEvaluation getCompleteEvaluation(ResultSet rs) throws SQLException, ConnectionProblemException{
        CompleteEvaluation completeEval = new CompleteEvaluation();
        completeEval.setId(rs.getInt("NUMERO"));
        completeEval.setComment(rs.getString("COMMENTAIRE"));
        completeEval.setUsername(rs.getString("NOM_UTILISATEUR"));
        completeEval.setVisitDate(rs.getDate("DATE_EVAL"));
        
        AbstractDAOFactory.getDAOFactory().getGradeDAO().loadGrades(completeEval);
        
        return completeEval;
    }
}
