
package dataaccess;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.*;
/**
 * NotificationDB.java is the Data Access class used to retrieve, create, 
 * modify and delete Notification objects.
 * 
 * @author Laura Diaz
 */
public class NotificationDB {
    
    /**
    * This method retrieves a list of all notifications in the database.
    * @return A list of Notification - a list of all notifications in the database
    */
    public List<Notification> getAll() {
        //Calling the entity manager
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            //Retrieving a List of Notification objects
            List <Notification> notifs = em.createNamedQuery("Notification.findAll", Notification.class).getResultList();
            return notifs;
        } finally {
            em.close();
        }
    }
    
    /**
    * This method retrieves a list of all the notifications that belong/are
    * assigned to a work center
    * @param centerId - Work center ID.
    * @return A list of Notification - a list of all notifications assigned to a
    * work center.
    */
    public List<Notification> getAllByWorkCenter(String centerId)  {
        //Calling the entity manager
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            WorkCenterDB wcDB = new WorkCenterDB();
            WorkCenter wc = wcDB.get(centerId);
            //Creating a List of Notification objects
            List <Notification> notifs = wc.getNotificationList();
             return notifs;
        } finally {
            em.close();
        }
    }
    
    /**
    * This method retrieves a single notification from the database, 
    * using the Notification ID.
    * @param id - ID of Notification to be retrieved    
    * @return Notification
    */
    public Notification get(int id)  {
        //Calling the entity manager
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Notification notif = em.find(Notification.class, id);
            return notif;
        } finally {
            em.close();
        }
    }
    
    /**
    * This void method performs an insertion of a new Notification 
    * object in the database.
    * @param notif - Notification Object being inserted.
    */
    public void insert(Notification notif) {
        //Calling the entity manager
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        //Calling the entity transaction
        EntityTransaction trans = em.getTransaction();
        
        try {
            WorkCenter workCenter = notif.getWorkCenterId();
            List<Notification> notifList = workCenter.getNotificationList();
            notifList.add(notif);
            //Start the transaction
            trans.begin();
            //Inserting the new entity
            em.persist(notif);
            //Save the changes
            em.merge(workCenter);
            trans.commit();            
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
    /**
    * This void method performs an update on an existing notification 
    * object in the database.
    * @param notif - Notification object being updated
    */
    public void update(Notification notif) {
        //Calling the entity manager
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        //Calling the entity transaction
        EntityTransaction trans = em.getTransaction();
        try {
            //Start the transaction
            trans.begin();
            //Merging the updated entity
            em.merge(notif);
            //Save the changes
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
    /**
    * This void method deletes an existing notification object in the database.
    * @param id - ID of notification being deleted.
    */
    public void delete(int id) {
        //Calling the entity manager
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        //Calling the entity transaction
        Notification notif = em.find(Notification.class, id);
        EntityTransaction trans = em.getTransaction();
        
        try {
            //Start the transaction
            trans.begin();
            //Deleting the entity and merging the changes
            em.remove(em.merge(notif));
            //Save the changes
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }    }
}
