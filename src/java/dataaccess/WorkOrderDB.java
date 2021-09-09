package dataaccess;

import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.*;

/**
 * DataAccess for WorkOrders
 * @author Rylan Cook
 */
public class WorkOrderDB {
    /**
     * Get a work order from the DB
     * @param workOrderId The WorkOrderID to get from the DB
     * @return The WorkOrder Object associated with WorkOrderID
     */
    public WorkOrder get(int workOrderId) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            WorkOrder center = em.find(WorkOrder.class, workOrderId);
            return center;
        } finally {
            em.close();
        }
    }
    /**
     * Get all WorkOrders from the DB
     * @return A list of all WorkOrders
     */
    public List<WorkOrder> getAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List<WorkOrder> centers = em.createNamedQuery("WorkOrder.findAll", WorkOrder.class).getResultList();
            return centers;
        } finally {
            em.close();
        }
    }
    /**
     * Insert a new WorkOrder into the DB
     * @param WorkOrder The WorkOrder Object to insert
     */
    public void insert(WorkOrder WorkOrder) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.persist(WorkOrder);
            trans.commit();            
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    /**
     * Delete a WorkOrder from the database
     * @param WorkOrder The WorkOrder to delete
     */
    public void delete(WorkOrder WorkOrder)  {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {

            trans.begin();
            em.remove(em.merge(WorkOrder));
            trans.commit();

        } catch (Exception e) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
    /**
     * Method to update work order
     * Added by Trevor Erixon
     * @param workOrder - entity being updated
     */
    public void update(WorkOrder workOrder) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try
        {
            trans.begin();
            em.merge(workOrder);
            trans.commit();
        }
        catch (Exception ex)
        {
            trans.rollback();
        }
        finally
        {
            em.close();
        }
    }
    
    //added by: Zennon 
    /**
     * Returns a WorkOrder using a Notification
     * @param n The notification to look at
     * @return The WorkOrder Object associated with the Notification
     */
    public WorkOrder getByNotificationId(Notification n) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {// modified by: Laura
            WorkOrder order = new WorkOrder();
            WorkOrder tmp;
            List<WorkOrder> orders = em.createNamedQuery("WorkOrder.findAll", WorkOrder.class).getResultList();
            for (Iterator<WorkOrder> iterator = orders.iterator(); iterator.hasNext();){
                tmp = iterator.next();
                if (tmp.getNotificationId().equals(n)){
                    order = tmp; 
                }
            } 
            return order;
        } finally {
            em.close();
        }
    }
    
    /**
    * Added by: Laura Diaz
    * This method gets all the Work Orders by Work Center.
    * @param centerId - Work Center id
    * @return A list of all the work orders assigned to a work center.
    */
    public List<WorkOrder> getAllByWorkCenter(String centerId)  {
        //Calling the entity manager
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            //First retrieving all the work orders
            List<WorkOrder> orders = em.createNamedQuery("WorkOrder.findAll", WorkOrder.class).getResultList();
            //Iterating through the list of work orders.
            for (Iterator<WorkOrder> iterator = orders.iterator(); iterator.hasNext();){
                WorkOrder order = iterator.next();
                //find the orders that do not belong to the work center
                if (!order.getNotificationId().getWorkCenterId().getWorkCenterId().equals(centerId)){
                    //remove orders, leaving only the ones that belong to the work center
                    iterator.remove();
                }
            }     
            return orders;
        } finally {
            em.close();
        }
    }
         
    
}
