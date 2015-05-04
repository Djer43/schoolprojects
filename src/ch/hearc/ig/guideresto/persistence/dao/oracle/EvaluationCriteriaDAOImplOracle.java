package ch.hearc.ig.guideresto.persistence.dao.oracle;

import ch.hearc.ig.guideresto.business.EvaluationCriteria;
import ch.hearc.ig.guideresto.business.RestaurantType;
import ch.hearc.ig.guideresto.exceptions.ConnectionProblemException;
import ch.hearc.ig.guideresto.persistence.dao.EvaluationCriteriaDAO;
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
public class EvaluationCriteriaDAOImplOracle extends AbstractDAOOracle implements EvaluationCriteriaDAO{
    
    @Override
    public Set<EvaluationCriteria> researchAll() throws ConnectionProblemException{
        Statement stmt = null;
        ResultSet rs = null;
        
        Set <EvaluationCriteria> criterias = new HashSet<>();
        
        try {
            stmt = getConnection().createStatement();        
            String query = "SELECT numero,nom,description FROM criteres_evaluation";
            
            rs = stmt.executeQuery(query);
           
            // On ajoute les restaurants à la liste
            while(rs.next()){
                criterias.add(getCriteria(rs));
            }
        }catch (ConnectionProblemException ex) {
            throw ex;
        }catch (SQLException sqlE){
            throw new ConnectionProblemException("A problem appeared while loading all criterias", sqlE);
        }finally {
            closeStmtAndRS(stmt, rs);
        }
        return criterias;
    }
    
    @Override
    public EvaluationCriteria researchById(Integer id) throws ConnectionProblemException{
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        EvaluationCriteria criteria;
        
        try {
            String query = "SELECT numero,nom,description FROM criteres_evaluation WHERE numero = ?";
            
            stmt = getConnection().prepareStatement(query);        
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
           
            // On ajoute les restaurants à la liste
            if(rs.next()){
                criteria = getCriteria(rs);
            }else{
                throw new ConnectionProblemException("Couldn't find an EvaluationCriteria with ID " + id);
            }
        }catch (ConnectionProblemException ex) {
            throw ex;
        }catch (SQLException sqlE){
            throw new ConnectionProblemException("A problem appeared while searching an EvaluationCriteria with ID " + id, sqlE);
        }finally {
            closeStmtAndRS(stmt, rs);
        }
        return criteria;
    }
    
    private EvaluationCriteria getCriteria(ResultSet rs) throws SQLException{
        EvaluationCriteria criteria = new EvaluationCriteria();
        criteria.setId(rs.getInt("NUMERO"));
        criteria.setName(rs.getString("NOM"));
        criteria.setDescription(rs.getString("DESCRIPTION"));
       
        return criteria; 
    }
    
}
