package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import models.*;

/**
 * Order Type Database connector
 * @author Rylan Cook
 */
public class OrderTypeDB {
    /**
     * Get a typeId object from the DB
     * @param typeId The id of the type
     * @return The OrderType Object
     */
    public OrderType get(String typeId) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            OrderType ordertype = em.find(OrderType.class, typeId);
            return ordertype;
        } finally {
            em.close();
        }
    }
    /**
     * Get all OrderType objects from the DB
     * @return A list of all orderTypes
     */
    public List<OrderType> getAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            TypedQuery<OrderType> query = em.createNamedQuery("OrderType.findAll", OrderType.class);
            List<OrderType> users = query.getResultList();
            return users;
        } finally {
            em.close();
        }
    }
    /**
     * Insert a new OrderType in the DB
     * @param orderType The orderType Object
     */
    public void insert(OrderType orderType) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.persist(orderType);
            em.merge(orderType);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    /**
     * Update a orderType in the DB
     * @param orderType The orderType object to update
     */
    public void update(OrderType orderType) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            em.merge(orderType);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    /**
     * Delete an orderType
     * @param typeID The typeId to delete
     */
    public void delete(String typeID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        OrderType order = em.find(OrderType.class, typeID);
        EntityTransaction trans = em.getTransaction();
        
        try {
            
            trans.begin();
            em.remove(em.merge(order));
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }    }
}
