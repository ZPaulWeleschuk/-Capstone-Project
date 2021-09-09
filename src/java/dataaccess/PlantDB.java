package dataaccess;
import java.util.List;
import javax.persistence.EntityManager;
import models.*;

/**
 * PlantDB.java is the Data Access class used to retrieve a single plant or
 * a List of all plants from the database.
 * 
 * @author Laura Diaz
 */
public class PlantDB {
    
    /**
    * This method retrieves a single plant from the database, 
    * using the Plant ID.
    * @param id - ID of plant to be retrieved
    * @return Plant - a plant by its id.
    */
    public Plant get(int id) {
        //Creating an Entity Manager from the EM Factory
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            //Retrieving the single plant by its id
            Plant plant = em.find(Plant.class, id);
            return plant;
        } finally {
            em.close();
        }
    }
    
    /**
    * This method retrieves a list of all the plants 
    * from the database.
    * @return A list of Plant - A list of all the plants.
    */
    public List<Plant> getAll() {
        //Creating an Entity Manager from the EM Factory
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            //Retrieving a list of all the plants from database
            List<Plant> plants = em.createNamedQuery("Plant.findAll", Plant.class).getResultList();
            return plants;
        } finally {
            em.close();
        }
    }
}
