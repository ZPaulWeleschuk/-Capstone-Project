package dataaccess;
import java.util.List;
import javax.persistence.EntityManager;
import models.*;
/**
 * WorkCenterDB.java is the Data Access class used to retrieve a single 
 * work center or a List of all work centers from the database.
 * 
 * @author Laura Diaz
 */
public class WorkCenterDB {
    
    /**
    * This method retrieves a single work center from the database, 
    * using the Work Center ID.
    * @param id - ID of Work center to be retrieved
    * @return WorkCenter - a Work center by its id.
    */
    public WorkCenter get(String id) {
        //Creating an Entity Manager from the EM Factory
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            //Retrieving the single work center by its id
            WorkCenter center = em.find(WorkCenter.class, id);
            return center;
        } finally {
            em.close();
        }
    }
    
    /**
    * This method retrieves a list of all the work centers 
    * from the database.
    * @return A list of WorkCenter - A list of all the work centers.
    */
    public List<WorkCenter> getAll() {
        //Creating an Entity Manager from the EM Factory
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            //Retrieving a list of all the work centers from database
            List<WorkCenter> centers = em.createNamedQuery("WorkCenter.findAll", WorkCenter.class).getResultList();
            return centers;
        } finally {
            em.close();
        }
    }
}
