package dataaccess;
import java.util.List;
import javax.persistence.EntityManager;
import models.*;
/**
 * TechnicalObjectDB.java is the Data Access class used to retrieve a single
 * technical object or a list of all the technical objects in the database.
 * 
 * @author Laura Diaz Pena
 */
public class TechnicalObjectDB {
    
    /**
    * This method retrieves a single Technical Object from the database, 
    * using the Technical Object ID.
    * @param id - ID of Technical Object to be retrieved
    * @return Technical Object - a Technical Object by its id.
    */
    public TechnicalObject get(int id) {
        //Creating an Entity Manager from the EM Factory
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            //Retrieving the single Technical Object by its id
            TechnicalObject obj = em.find(TechnicalObject.class, id);
            return obj;
        } finally {
            em.close();
        }
    }
    
    /**
    * This method retrieves a list of all the Technical Objects 
    * from the database.
    * @return A list of TechnicalObject  - A list of all Technical Objects.
    */
    public List<TechnicalObject> getAll() {
        //Creating an Entity Manager from the EM Factory
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            //Retrieving a list of all the Technical Objects in the database.
            List<TechnicalObject> objects = em.createNamedQuery("TechnicalObject.findAll", TechnicalObject.class).getResultList();
            return objects;
        } finally {
            em.close();
        }
    }
}
