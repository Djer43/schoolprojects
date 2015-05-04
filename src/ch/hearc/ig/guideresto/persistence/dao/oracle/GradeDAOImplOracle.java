package ch.hearc.ig.guideresto.persistence.dao.oracle;

import ch.hearc.ig.guideresto.business.CompleteEvaluation;
import ch.hearc.ig.guideresto.business.EvaluationCriteria;
import ch.hearc.ig.guideresto.business.Grade;
import ch.hearc.ig.guideresto.exceptions.ConnectionProblemException;
import ch.hearc.ig.guideresto.persistence.dao.AbstractDAOFactory;
import ch.hearc.ig.guideresto.persistence.dao.GradeDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author Julien Plumez
 */
public class GradeDAOImplOracle extends AbstractDAOOracle implements GradeDAO{
    
    @Override
    public Grade insertGrade(EvaluationCriteria criteria, CompleteEvaluation eval, Integer note) throws ConnectionProblemException{
        Grade grade = new Grade(null, note, eval, criteria);
        
        this.insertGrade(grade);
        
        return grade;
    }
    
    @Override
    public void insertGrade(Grade grade) throws ConnectionProblemException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        final String generatedColumns[] = {"numero"};
        
        try {
            String query = "INSERT INTO notes (note, fk_comm, fk_crit) VALUES (?,?,?)";
            stmt = getConnection().prepareStatement(query, generatedColumns); 
        
            stmt.setInt(1,grade.getGrade());
            stmt.setInt(2,grade.getEvaluation().getId());
            stmt.setInt(3,grade.getCriteria().getId());
            
            int rowCount = stmt.executeUpdate();
            
            if(rowCount != 1){
                throw new ConnectionProblemException("A problem appeared while inserting a grade !");
            }
            
            rs = stmt.getGeneratedKeys();
            
            if(rs.next()){
                grade.setId(rs.getInt(1));
            }
            
        }catch (ConnectionProblemException ex) {
            throw ex;
        }catch (SQLException sqlE){
            throw new ConnectionProblemException("A problem appeared while inserting a new grade", sqlE);
        }finally {
            closeStmtAndRS(stmt, rs);
        }
    }
    
    @Override
    public void deleteWithRestaurantId(Integer restaurantId) throws ConnectionProblemException{
        PreparedStatement stmt = null;
        
        try {
            String query = "DELETE FROM notes  WHERE (notes.fk_comm) IN (SELECT commentaires.numero FROM commentaires WHERE commentaires.fk_rest = ?)";
            stmt = getConnection().prepareStatement(query); 

            stmt.setInt(1, restaurantId);
            stmt.executeUpdate();
        }catch (SQLException sqlE){
            throw new ConnectionProblemException("A problem appeared while deleting grades with restaurant id " + restaurantId, sqlE);
        }finally {
            closeStmtAndRS(stmt, null);
        }
    }
    
    @Override
    public void loadGrades(CompleteEvaluation eval) throws ConnectionProblemException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            String query = "SELECT numero,note, fk_comm, fk_crit FROM notes WHERE fk_comm = ?";
            
            stmt = getConnection().prepareStatement(query);        
            stmt.setInt(1, eval.getId());
            
            rs = stmt.executeQuery();
           
            // On ajoute les restaurants à la liste
            while(rs.next()){
                eval.addGrade(getGrade(rs));
            }
        }catch (SQLException sqlE){
            throw new ConnectionProblemException("A problem appeared while loading a restaurant's complete evaluations", sqlE);
        }finally {
            closeStmtAndRS(stmt, rs);
        }
    }
    
    private Grade getGrade(ResultSet rs) throws SQLException, ConnectionProblemException{
        Grade grade = new Grade();
        grade.setId(rs.getInt("NUMERO"));
        grade.setGrade(rs.getInt("NOTE"));
        
        grade.setCriteria(AbstractDAOFactory.getDAOFactory().getEvaluationCriteriaDAO().researchById(rs.getInt("FK_CRIT")));
        
        return grade;
    }
}
