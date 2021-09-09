package Services;

import dataaccess.*;
import java.util.Date;
import java.util.List;
import models.*;


/**
 * Description: This is the service layer between the WorkOrderServlet and the WorkOrderDD Access
 * @author Zennon Weleschuk
 * @version  June 11 2021
 */
public class WorkOrderService {
    
    /**
     * Gets a workOrder by the workOrderId
     * @param workOrderId Work Order Id 
     * @return WorkOrder object specified
     */
    public WorkOrder getWorkOrder(int workOrderId) {
       WorkOrderDB workOrderDB = new WorkOrderDB(); 
       WorkOrder workOrder = workOrderDB.get(workOrderId);
       return workOrder;
    }
    

    /**
     * Gets all work orders currently in the DB
     * @return a list containing WorkOrder objects
     */
    public List<WorkOrder> getAllWorkOrders() { 
        WorkOrderDB workOrderDB = new WorkOrderDB();
        List<WorkOrder> workOrders = workOrderDB.getAll();
        return workOrders;
    }
    
    //public List<WorkOrder> getAllWOforEmployee() throws Exception{return null}
    
    /**
     * create a new workOrder and enter it into the DB
     * @param notificationID notification Id as an int (note lowercase 'd')
     * @param orderType order type
     * @param description description
     * @param requiredStartDate the start date 
     * @param requiredEndDate the end date
     * @param notificationId notificationID as an object (note uppercase 'D')
     */
    public void insertWorkOrder(int notificationID, String orderType, 
            String description, Date requiredStartDate,  Date requiredEndDate, 
            Notification notificationId){  
        //TODO This needs to call for an OrderType Object so that it can be inserted as the work order type
        OrderTypeDB ot = new OrderTypeDB();
        WorkOrder WorkOrder = new WorkOrder(); 
        
        int workOrderId = notificationID + 7000000; // sets order id to begin with 8
        
        WorkOrder.setWorkOrderId(workOrderId);
        WorkOrder.setOrderType(ot.get(orderType));
        WorkOrder.setDescription(description);
        WorkOrder.setRequiredStartDate(requiredStartDate);
        WorkOrder.setRequiredEndDate(requiredEndDate);
        WorkOrder.setNotificationId(notificationId);
        
        Status status = new Status(1);
        WorkOrder.setStatus(status);
        
        WorkOrderDB workOrderDB = new WorkOrderDB(); 
        workOrderDB.insert(WorkOrder);
    }
    
    /**
     * Method to open work order
     * Added by Trevor Erixon
     * @param workOrderId The WorkOrder to open
     */
    public void openWorkOrder(int workOrderId) {
        WorkOrderDB workOrderDB = new WorkOrderDB();
        WorkOrder workOrder = workOrderDB.get(workOrderId);   
        
        StatusDB statusDB = new StatusDB();
        Status status = statusDB.get(2);        
        
        workOrder.setStatus(status);
        workOrderDB.update(workOrder);
    }
    
    /**
     * Method to pause work order
     * Added by Trevor Erixon
     * @param workOrderId The WorkOrder to pause
     */
    public void pauseWorkOrder(int workOrderId) {
        WorkOrderDB workOrderDB = new WorkOrderDB();
        WorkOrder workOrder = workOrderDB.get(workOrderId);   
        
        StatusDB statusDB = new StatusDB();
        Status status = statusDB.get(4);        
        
        workOrder.setStatus(status);
        workOrderDB.update(workOrder);
    }
    
    /**
     * Method to close work order
     * Added by Trevor Erixon
     * @param workOrderId The WorkOrder to close
     * @param closingNotes Any notes that the User who closed the WorkOrder has left.
     */
    public void closeWorkOrder(int workOrderId, String closingNotes) {
        WorkOrderDB workOrderDB = new WorkOrderDB();
        WorkOrder workOrder = workOrderDB.get(workOrderId);   
        
        StatusDB statusDB = new StatusDB();
        Status status = statusDB.get(3);
        
        workOrder.setNotes(closingNotes);
        workOrder.setStatus(status);
        workOrderDB.update(workOrder);
    }
    
    /**
     * Delete a WorkOrder from the Database
     * @param WorkOrderId The workOrder Id to delete
     */
    public void deleteWorkOrder(int WorkOrderId){
     WorkOrderDB workorderDB = new WorkOrderDB();
        WorkOrder workOrder = workorderDB.get(WorkOrderId);
        workorderDB.delete(workOrder);
    
    };
    
    /**
     * get the work order by using the notification object
     * @param n notification object
     * @return the work order associated with the notification 
     */
    public WorkOrder getWorkOrderByNotificationId(Notification n){
     WorkOrderDB workOrderDB = new WorkOrderDB();
     WorkOrder workOrder = workOrderDB.getByNotificationId(n);
     return workOrder;   
    }

    /**
    *Added by: Laura Diaz
    * This method gets all the Work Orders by Work Center.
     * @param centerId The WorkCenter associated with the wanted WorkOrders
     * @return A list of WorkOrders that belong to given WorkCenter
    */    
    public List<WorkOrder> getAllByWorkCenter(String centerId){
        WorkOrderDB orderDB = new WorkOrderDB();
        List<WorkOrder> orders = orderDB.getAllByWorkCenter(centerId);
        return orders;
    }

    /**
    * Added by: Laura Diaz
    * This method gets a list of all the Work Order Types.
     * @return Returns all WorkOrder Types
    */    
    public List<OrderType> getAllTypes(){
        OrderTypeDB typeDB = new OrderTypeDB();
        List<OrderType> types = typeDB.getAll();
        return types;
    }

    /**
     * checks if a number is positive
     * @param number accepts a number in the form of a string
     * @return returns true if it can be parsed to a double, else false
     */
    public boolean isPosNumber(String number) {
        try {
            double isNumber = Double.parseDouble(number);
            return isNumber > 0.0;
        } catch (NumberFormatException e) {
            System.out.println("error, not a number");
            return false;
        }
    }
    
    /**
     * checks if input is not null or not empty
     * @param input string input
     * @return true if valid else false
     */
    public boolean isValidStringInput(String input) {
        try {
            if (input != null) {
                if (input.length() != 0) {
                    if (!input.equals("")) {
                        if (!input.trim().isEmpty()) {
                            return true;
                        }
                    }
                }
            }
            throw new Exception();
        } catch (Exception ex) {
            return false;
        }
    }
    
}
