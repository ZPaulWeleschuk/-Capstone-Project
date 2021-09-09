package dataaccess;

import Services.WorkOrderService;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.*;

/**
 *
 * @author Saeid R
 */
public class OperationsDB {
    /**
     * Get a operation from the database
     * @param id The operation to get.
     * @return The operation object.
     */
    public Operations get(int id){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            Operations operation = em.find(Operations.class, id);
            return operation;
        } finally {
            em.close();
        }
    }
    /**
     * Get all Operations from the database
     * @return A list containing all the operations
     */
    public List<Operations> getAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            List<Operations> operations = em.createNamedQuery("Operations.findAll", Operations.class).getResultList();
            return operations;
        } finally {
            em.close();
        }
    }
    /**
     * Insert a new operation into the database
     * @param operation The operation object to insert
     */
    public void insert(Operations operation) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            WorkOrderService wos = new WorkOrderService();
            int workOrderId = operation.getWorkOrderId().getWorkOrderId();
            WorkOrder workOrder = wos.getWorkOrder(workOrderId);
            workOrder.getOperationsList().add(operation);
            
            trans.begin();
            em.persist(operation);
            em.merge(workOrder);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    /**
     * Update an Operations object
     * @param Operations The updated operation with the same ID
     */
    public void update(Operations Operations)  {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(Operations);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    /**
     * Delete an operation from the database
     * @param Operations The operation to delete
     */
    public void delete(Operations Operations)  {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {

            trans.begin();
            em.remove(em.merge(Operations));
            trans.commit();

        } catch (Exception e) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
}
