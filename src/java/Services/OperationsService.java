/* Operation Codes
*  LIST OF STATUS AND WHAT THEY MEAN
*  1 = ASSIGNED
*  2 = OPEN
*  3 = CLOSED
*  4 = PAUSED
*  5 = STARTED
*  6 = FINISHED
*  7 = CANCELLED
*/
package Services;
import dataaccess.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.*;

/**
 * The service class to perform CRUD functions for Operations
 * @author Saeid R
 */



public class OperationsService {
    /**
     * Retrieves an operation from the DB using the ID
     * @param operationId the operation to be retrieved.
     * @return A Operations Object 
     */
    public Operations get(int operationId)  {
        OperationsDB operationsDB = new OperationsDB();
        Operations operations = operationsDB.get(operationId);
        return operations;
    }
    /**
     * Returns all operations in the DB
     * @return A list of all Operations Objects.
     */
    public List<Operations> getAll()  {
        OperationsDB operationsDB = new OperationsDB();
        List<Operations> operations = operationsDB.getAll();
        return operations;
    }
    
    /**
     * Creates a new Operations Object and inserts it into the DB
     * @param operationId the new Operations Unique identifier
     * @param description What the operation is
     * @param longText Any comments made on it
     * @param work_order_id Which work order it is for
     * @param status what the current status is. (See top of file for codes)
     * @param person_responsible Who is doing the operation
     */
    public void insert(int operationId, String description, String longText, int work_order_id, int status, int person_responsible)  {
        OperationsDB operationsDB = new OperationsDB();
        WorkOrderService workOrderService = new WorkOrderService();
        StatusService statusService = new StatusService();
        UserService userService = new UserService();
        Operations operation = new Operations(operationId);
        //Operations operation = operationsDB.get(operationId);
        operation.setDescription(description);
        operation.setLongText(longText);        
        operation.setWorkOrderId(workOrderService.getWorkOrder(work_order_id));
        operation.setStatus(statusService.get(status));
        operation.setPersonResponsible(userService.getUser(person_responsible));
        operationsDB.insert(operation);
    }
    /**
     * Remove an operation from the DB
     * @param operationId Which operation to remove
     */
    public void delete(int operationId)  {
        OperationsDB operationsDB = new OperationsDB();
        Operations operations = operationsDB.get(operationId);
        operationsDB.delete(operations);
    }
    /**
     * Update an operation in the DB
     * @param operationId The operation to update
     * @param description The new description
     * @param longText The new Comment
     * @param work_order_id the new work order
     * @param status The new Status(See top of file for codes)
     * @param person_responsible The new person responsible
     */
    public void update(int operationId, String description, String longText, int work_order_id, int status, int person_responsible) {
        try {
            OperationsDB operationsDB = new OperationsDB();
            WorkOrderService workOrderService = new WorkOrderService();
            StatusService statusService = new StatusService();
            UserService userService = new UserService();
            Operations operation = operationsDB.get(operationId);
            operation.setDescription(description);
            operation.setLongText(longText);
            operation.setWorkOrderId(workOrderService.getWorkOrder(work_order_id));
            operation.setStatus(statusService.get(status));
            operation.setPersonResponsible(userService.getUser(person_responsible));
            operationsDB.update(operation);
        } catch (Exception ex) {
            Logger.getLogger(OperationsService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Insert an operation into the db using an already created one.
     * @param o The new object to insert
     */
    public void insert(Operations o) {
        OperationsDB operationsDB = new OperationsDB();
        try{
        operationsDB.insert(o);
        }
        catch (Exception e){
            Logger.getLogger(OperationsService.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
}
