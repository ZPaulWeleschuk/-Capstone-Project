package dataaccess;
import java.util.List;
import javax.persistence.EntityManager;
import models.*;
/**
 * DamageDB.java is the Data Access class used to retrieve a single damage or 
 * a List of all damages from the database.
 * 
 * @author Laura Diaz
 */
public class DamageDB {
   
    /**
    * This method retrieves a single damage from the database, 
    * using the Damage ID.
    * @param id - ID of damage to be retrieved
    * @return Damage - a damage by its id.
    */
    public Damage get(int id) {
        //Creating an Entity Manager from the EM Factory
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            //Retrieving a single damage by its id
            Damage damage = em.find(Damage.class, id);
            return damage;
        } finally {
            em.close();
        }
    }
    
    /**
    * This method retrieves a list of all the damages 
    * from the database.
    * @return A list of Damage - A list of all damages.
    */
    public List<Damage> getAll() {
        //Creating an Entity Manager from the EM Factory
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            //Retrieving a list of all the causes from database
            List<Damage> damages = em.createNamedQuery("Damage.findAll", Damage.class).getResultList();
            return damages;
        } finally {
            em.close();
        }
    }
}
