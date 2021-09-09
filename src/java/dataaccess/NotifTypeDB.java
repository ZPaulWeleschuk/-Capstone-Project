package dataaccess;
import java.util.List;
import javax.persistence.EntityManager;
import models.*;

/**
 * NotifTypeDB.java is the Data Access class used to retrieve a single
 * notification type or a list of all the notification types from the 
 * database.
 * 
 * @author Laura Diaz 
 */
public class NotifTypeDB {
    
    /**
    * This method retrieves a single Notification Type from 
    * the database, using the Notification Type ID.
    * @param id - ID of Notification Type to be retrieved
    * @return Notification Type - a notification type.
    */
    public NotifType get(String id)  {
        //Creating an Entity Manager from the EM Factory
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            //Retrieving the single notification type, by its ID.
            NotifType type = em.find(NotifType.class, id);
            return type;
        } finally {
            em.close();
        }
    }
    
    /**
    * This method retrieves a list of all the notification types 
    * from the database.
    * @return A list of NotifType - A list of all notification types.
    */
    public List<NotifType> getAll() {
        //Creating an Entity Manager from the EM Factory
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            //Retrieving a list of all the notification types in the database
            List<NotifType> types = em.createNamedQuery("NotifType.findAll", NotifType.class).getResultList();
            return types;
        } finally {
            em.close();
        }
    }
}
