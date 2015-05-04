/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.guideresto.persistence.entitymanager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author jeremy.wermeill
 * 
 */

public class MyEntityManager {

  private EntityManagerFactory emf; 
  private EntityManager em;
  
  
  
  /**
   * Cette méthode permet de retourner un entity manager
   * @return 
   */
  public EntityManager initEntityManager(){
  emf = Persistence.createEntityManagerFactory("guideRestoDAOPU");
  em = emf.createEntityManager();
  
  return em;
  
  }
  /**
   * Cette méthode permet de fermer un entity manager
   * @param em 
   */
  public void closeEntityManager(){
  em.close(); 
  }
  
}
