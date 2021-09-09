package dataaccess;
import java.util.List;
import javax.persistence.EntityManager;
import models.*;
/**
 * RolesDB.java is the Data Access class used to retrieve a single user role or
 * a List of all the roles from the database.
 * 
 * @author Laura Diaz 
 */
public class RolesDB {
    
    /**
    * This method retrieves a single role from the database, 
    * using the Role ID.
    * @param id - ID of role to be retrieved
    * @return Role - a user role.
    */
    public Roles get(int id) {
        //Creating an Entity Manager from the EM Factory
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            //Retrieving the single role by its id
            Roles role = em.find(Roles.class, id);
            return role;
        } finally {
            em.close();
        }
    }
    
    /**
    * This method retrieves a list of all the user roles 
    * from the database.
    * @return A list of Roles - A list of all the roles.
    */
    public List<Roles> getAll() {
        //Creating an Entity Manager from the EM Factory
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            //Retrieving a list of all the user roles from database
            List<Roles> roles = em.createNamedQuery("Roles.findAll", Roles.class).getResultList();
            return roles;
        } finally {
            em.close();
        }
    }
}
