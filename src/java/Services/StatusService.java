package Services;

import dataaccess.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.*;

/**
 * Serves status from The DB
 * @author Rylan Cook
 */
public class StatusService {
    /**
     * Get a status from the db based on statusId
     * @param statusId The status you want to get
     * @return The Status object if found in the DB
     */
    public Status get(int statusId)  {
        StatusDB statusDB = new StatusDB();
        Status status = statusDB.get(statusId);
        return status;
    }
    /**
     * Gets all status from DB
     * @return A list of all Status in the DB
     */
    public List<Status> getAll() {
        StatusDB statusDB = new StatusDB();
        List<Status> status = statusDB.getAll();
        return status;
    }
    /**
     * Insert status creating a new object
     * @param statusId The new status id
     * @param status_desc The description of the new status
     */
    public void insert(int statusId, String status_desc)  {
        StatusDB statusDB = new StatusDB();
        Status status = statusDB.get(statusId);
        status.setStatusDesc(status_desc);
        statusDB.insert(status);
    }
    /**
     * Delete a Status from the DB
     * @param statusId The status you want to delete
     */
    public void delete(int statusId)  {
        StatusDB statusDB = new StatusDB();
        Status status = statusDB.get(statusId);
        statusDB.delete(status);
    }
    /**
     * Update a status from the DB
     * @param statusId The status to update
     * @param status_desc The new status description
     */
    public void update(int statusId, String status_desc) {
        try {
        StatusDB statusDB = new StatusDB();
        Status status = statusDB.get(statusId);
        status.setStatusDesc(status_desc);
        statusDB.update(status);
        } catch (Exception ex) {
            Logger.getLogger(StatusService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Insert a new status into the DB
     * @param s The status to insert
     */
    public void insert(Status s) {
        StatusDB statusDB = new StatusDB();
        try{
        statusDB.insert(s);
        }
        catch (Exception e){
            Logger.getLogger(StatusService.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
}
