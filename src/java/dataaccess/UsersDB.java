package dataaccess;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.*;
/**
 * UsersDB.java is the Data Access class used to retrieve, create, update and 
 * delete user entities from the database.
 * 
 * @author Laura Diaz
 */
public class UsersDB {
    
    /**
    * This method retrieves a single user from the database, 
    * using the CUser ID.
    * @param id - ID of user to be retrieved
    * @return User - a user by their id.
    */
    public Users get(int id) {
        //Creating an Entity Manager from the EM Factory
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            //Retrieving the single user by its id
            Users user = em.find(Users.class, id);
            return user;
        } finally {
            em.close();
        }
    }
    
    /**
    * This method retrieves a list of all the users.
    * @return A list of Users - a list of all users in the database
    */
    public List<Users> getAll()  {
        //Creating an Entity Manager from the EM Factory
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            //Retrieving the single user by its id
            List<Users> users = em.createNamedQuery("Users.findAll", Users.class).getResultList();
            return users;
        } finally {
            em.close();
        }
    }
    
    /**
    * This method retrieves a list of all the users that belong to a work center
    * @param workCenterId - Work center ID.
    * @return A list of Users - a list of all users from a work center
    */
    public List<Users> getAllByWorkCenter(String workCenterId) {
        //Creating an Entity Manager from the EM Factory
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            //Calling the Work Center Data Access class
            WorkCenterDB wcDB = new WorkCenterDB();
            WorkCenter wc = wcDB.get(workCenterId);
            //Retrieve list of users from a Work Center.
            List<Users> centerUsers = wc.getUsersList();
            return centerUsers;
        } finally {
            em.close();
        }
    }

    /**
     * This void method performs an insertion of a new user entity in the 
     * database.
     * @param user The User object to insert into the DB
     */
    public void insert(Users user) {
        //Calling the entity manager
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        //Calling the entity transaction
        EntityTransaction trans = em.getTransaction();
        
        try {
            WorkCenter workCenter = user.getWorkCenterId();
            List<Users> workCenterUsers = workCenter.getUsersList();
            workCenterUsers.add(user);
            
            //Start the transaction
            trans.begin();
            //Inserting the new entity
            em.persist(user);
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
    * This void method deletes an existing user entity in the database.
    * @param user - User object.
    */
    public void delete(Users user) {
        //Calling the entity manager
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        //Calling the entity transaction
        EntityTransaction trans = em.getTransaction();
        
        try {
            //Start the transaction
            trans.begin();
            //Deleting the entity and merging the changes
            em.remove(em.merge(user));
            //Save the changes
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

    /**
    * This void method performs an update on an existing user 
    * entity in the database.
    * @param user - User object being updated
    */
    public void update(Users user) {
        //Calling the entity manager
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        //Calling the entity transaction
        EntityTransaction trans = em.getTransaction();
        
        try {
            //Start the transaction
            trans.begin();
            //Merging the updated entity
            em.merge(user);
            //Save the changes
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
}
