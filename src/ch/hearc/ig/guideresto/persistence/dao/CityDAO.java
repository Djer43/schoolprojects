package ch.hearc.ig.guideresto.persistence.dao;

import ch.hearc.ig.guideresto.business.City;
import ch.hearc.ig.guideresto.exceptions.ConnectionProblemException;
import java.util.Set;

/**
 *
 * @author julien.plumez
 */
public interface CityDAO {
    public City researchById(Integer id)  throws ConnectionProblemException;
    public Set<City> researchAll()  throws ConnectionProblemException;
    public City insert(String zipCode, String name) throws ConnectionProblemException;
    public void insert(City city) throws ConnectionProblemException;
}
