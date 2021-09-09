package Services;

import dataaccess.*;
import java.util.List;
import models.*;

/**
 * Performs CRUD functions on orderType Objects in the db.
 * @author Rylan cook
 */
public class OrderTypeService {
    /**
     * Get an order based on the ID Given
     * @param typeId Which Order
     * @return The OrderType Object retrieved
     */
    public OrderType getOrderType(String typeId) {
       OrderTypeDB workOrderDB = new OrderTypeDB(); 
       OrderType workOrder = workOrderDB.get(typeId);
       return workOrder;
    }
    /**
     * Returns all order Types
     * @return A list of all OrderType Objects.
     */
    public List<OrderType> getAll() {
       OrderTypeDB workOrderDB = new OrderTypeDB(); 
       List<OrderType> workOrder = workOrderDB.getAll();
       return workOrder;
    }
    /**
     * Inserts a new OrderType object into the DB
     * @param orderType The object to insert
     */
    public void insert(OrderType orderType) {
       OrderTypeDB workOrderDB = new OrderTypeDB(); 
       workOrderDB.insert(orderType);
    }
    /**
     * Creates and inserts a new OrderType object into the DB
     * @param typeId the id of the Object
     * @param typeDesc The description of the type.
     */
    public void insert(String typeId, String typeDesc) {
       OrderTypeDB workOrderDB = new OrderTypeDB(); 
       OrderType orderType = new OrderType(typeId);
       orderType.setTypeDesc(typeDesc);
       workOrderDB.insert(orderType);
    }
    /**
     * Updates the description of an order type
     * @param typeId Which order type to update
     * @param typeDesc The new description
     */
    public void update(String typeId, String typeDesc) {
       OrderTypeDB workOrderDB = new OrderTypeDB(); 
       OrderType orderType = new OrderType(typeId);
       orderType.setTypeDesc(typeDesc);
       workOrderDB.update(orderType);
    }
    /**
     * Removes an order type from the DB
     * @param typeId Which type to delete
     */
    public void delete(String typeId) {
       OrderTypeDB workOrderDB = new OrderTypeDB(); 
       workOrderDB.delete(typeId);
    }
}
