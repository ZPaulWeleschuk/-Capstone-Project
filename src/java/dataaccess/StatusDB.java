package dataaccess;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.*;
/**
 * StatusDB.java is the Data Access class used to retrieve a single status or
 * a List of all the status from the database.
 * 
 * @author Laura Diaz
 */
public class StatusDB {
    
    /**
    * This method retrieves a single status from the database, 
    * using the Status ID.
    * @param id - ID of Status to be retrieved
    * @return Status - a status by its id.
    */
    public Status get(int id)  {
        //Creating an Entity Manager from the EM Factory
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            //Retrieving the single status by its id
            Status status = em.find(Status.class, id);
            return status;
        } finally {
            em.close();
        }
    }
    
    /**
    * This method retrieves a list of all the status 
    * from the database.
    * @return A list of Status - A list of all status.
    */
    public List<Status> getAll()  {
        //Creating an Entity Manager from the EM Factory
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            //Retrieving a list of all the status in the database.
            List<Status> allStatus = em.createNamedQuery("Status.findAll", Status.class).getResultList();
            return allStatus;
        } finally {
            em.close();
        }
    }
    /**
     * Insert a new status into the database
     * @param status The new status object to be inserted.
     */
    public void insert(Status status) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.persist(status);
            em.merge(status);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    /**
     * Delete a status from the DB
     * @param status The status to remove
     */
    public void delete(Status status) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.remove(em.merge(status));
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    /**
     * Update a status
     * @param status The updated status object with the same ID
     */
    public void update(Status status) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.merge(status);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
}
