package dataaccess;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * This class will create the Entity Manager Factory object, based on the 
 * Work Order Persistence Unit. This class is used in the Data Access classes 
 * to retrieve, modify and delete data from database entities.
 * @author Trevor Erixon
 */
public class DBUtil 
{
    //Entity Manager Factory object used to interact with the Persistence Context
    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("WorkOrderPU");

    /**
     * This getter method retrieves the Entity Manager Factory.
     * @return EMF - the Entity Manager Factory object
     */
    public static EntityManagerFactory getEmFactory() 
    {
        return EMF;
    }
}