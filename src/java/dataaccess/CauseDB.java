package dataaccess;
import java.util.List;
import javax.persistence.EntityManager;
import models.*;
/**
 * CauseDB.java is the Data Access class used to retrieve a single cause or
 * a List of all causes from the database.
 * 
 * @author Laura Diaz
 */
public class CauseDB {
    
    /**
    * This method retrieves a single cause from the database, 
    * using the Cause ID.
    * @param id - ID of cause to be retrieved
    * @return Cause - a cause by its id.
    */
    public Cause get(int id)  {
        //Creating an Entity Manager from the EM Factory
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            //Retrieving the single cause by its id
            Cause cause = em.find(Cause.class, id);
            return cause;
        } finally {
            em.close();
        }
    }
    
    /**
    * This method retrieves a list of all the causes 
    * from the database.
    * @return A list of Cause - A list of all causes.
    */
    public List<Cause> getAll() {
        //Creating an Entity Manager from the EM Factory
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            //Retrieving a list of all the causes from database
            List<Cause> causes = em.createNamedQuery("Cause.findAll", Cause.class).getResultList();
            return causes;
        } finally {
            em.close();
        }
    }
}
