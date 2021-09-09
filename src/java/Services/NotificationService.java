package Services;
import dataaccess.*;
import java.util.List;
import models.*;

/**
 * NotificationService.java is the Service class used to perform 
 * all the business operations on notification objects, such as 
 * retrieving, creating, modifying and deleting notifications.
 * 
 * @author Laura Diaz
 */
public class NotificationService {
    
    /**
    * This method retrieves a specific Notification object, 
    * using the Notification ID
    * @param id - Notification ID
    * @return Notification - A Notification object.
    */
    public Notification get(int id) {
        NotificationDB notifDB = new NotificationDB();
        Notification notif = notifDB.get(id);
        return notif;
    }
    
    /**
    * This method retrieves a list of all the Notifications in
    * the database.
    * @return A list of all notifications in the Database
    */
    public List<Notification> getAll() {
        NotificationDB notifDB = new NotificationDB();
        List<Notification> notifs = notifDB.getAll();
        return notifs;
    }
    
    /**
    * This method retrieves a list of all the Notifications assigned to
    * a specific work center, using its ID.
    * @param centerId - the work center ID
    * @return A list of all notifications assigned to a work center
    */
    public List<Notification> getAllByWorkCenter(String centerId)  {
        NotificationDB notifDB = new NotificationDB();
        List<Notification> notifs = notifDB.getAllByWorkCenter(centerId);
        return notifs;
    }
    
    /**
    * This method retrieves a list of all predetermined Notification 
    * Types from the database. This is part of the Notification object.
    * @return A list of all the notification types.
    */
    public List<NotifType> getAllTypes()  {
        NotifTypeDB ntDB = new NotifTypeDB();
        List<NotifType> types = ntDB.getAll();
        return types;
    }
    
    /**
    * This method retrieves a list of all predetermined Damages
    * from the database. This is part of the Notification object.
    * @return A list of all the damages.
    */
    public List<Damage> getAllDamages()  {
        DamageDB dDB = new DamageDB();
        List<Damage> damages = dDB.getAll();
        return damages;
    }
    
    /**
    * This method retrieves a list of all predetermined Causes
    * from the database. This is part of the Notification object.
    * @return A list of all the causes.
    */
    public List<Cause> getAllCauses()  {
        CauseDB cDB = new CauseDB();
        List<Cause> causes = cDB.getAll();
        return causes;
    }
    
    /**
    * This method retrieves a list of all predetermined Plants
    * from the database. This is part of the Notification object.
    * @return A list of all the plants.
    */
    public List<Plant> getAllPlants()  {
        PlantDB pDB = new PlantDB();
        List<Plant> plants = pDB.getAll();
        return plants;
    }
    
    /**
    * This method retrieves a list of all predetermined Technical
    * Objects from the database. This is part of the Notification object.
    * @return A list of all the technical objects.
    */
    public List<TechnicalObject> getAllObjects() {
        TechnicalObjectDB toDB = new TechnicalObjectDB();
        List<TechnicalObject> objects = toDB.getAll();
        return objects;
    }
    
    /**
    * This method retrieves a list of all predetermined Work Centers
    * from the database. This is part of the Notification object.
    * @return A list of all the work centers.
    */
    public List<WorkCenter> getAllCenters() {
        WorkCenterDB wcDB = new WorkCenterDB();
        List<WorkCenter> centers = wcDB.getAll();
        return centers;
    }
    
    /**
    * This method retrieves a list of all predetermined Status
    * from the database. This is part of the Notification object.
    * @return A list of all the status.
    */
    public List<Status> getAllStatus() {
        StatusDB stDB = new StatusDB();
        List<Status> allStatus = stDB.getAll();
        return allStatus;
    }
    
    /**
    * This void method creates a new notification by passing
    * all the parameters and inserts it in the database.
    * @param id - Notification Id
    * @param type - ID of notification type
    * @param causeId - ID of notification cause
    * @param damageId - ID of notification damage
    * @param plantId - ID of new notification's plant 
    * @param objId - ID of new notification's Technical Object
    * @param userId - ID of person creating the notification.
    * @param statusId - ID of new notification status. 
    * @param centerId - Id of work center being assigned the notification
    */
    public void createNotification(int id, String type, int causeId, int damageId, 
            int plantId, int objId, int userId, int statusId, String centerId) {
        Notification notif = new Notification(id);
        //Assigning Notification Type
        NotifTypeDB ntDB = new NotifTypeDB();
        NotifType nt = ntDB.get(type);
        notif.setNotificationType(nt);
        
        // Assigning Cause
        CauseDB cDB = new CauseDB();
        Cause cause = cDB.get(causeId);
        notif.setCauseId(cause);
        
        //Assigning Damage
        DamageDB dDB = new DamageDB();
        Damage damage = dDB.get(damageId);
        notif.setDamageId(damage);
        
        //Assigning Plant
        PlantDB pDB = new PlantDB();
        Plant plant = pDB.get(plantId);
        notif.setPlantId(plant);
        
        //Assignin Technical Object
        TechnicalObjectDB toDB = new TechnicalObjectDB();
        TechnicalObject obj = toDB.get(objId);
        notif.setTechObjId(obj);
        
        //Assigning Created By
        UsersDB uDB = new UsersDB();
        Users user = uDB.get(userId);
        notif.setCreatedBy(user);
        
        //Assigning Status
        StatusDB sDB = new StatusDB();
        Status status = sDB.get(statusId);
        notif.setStatus(status);
        
        //Assigning Work Center
        WorkCenterDB wcDB = new WorkCenterDB();
        WorkCenter wc = wcDB.get(centerId);
        notif.setWorkCenterId(wc);
        
        //Inserting the new Notification Object in the database
        NotificationDB notifDB = new NotificationDB();
        notifDB.insert(notif);
    }
    
    /**
     * Method to update the cause and damage properties of the notification
     * Added by Trevor Erixon
     * @param id - id of the notification to be updated
     * @param cause - description of the cause
     * @param damage - description of the damage 
     */
    public void update(int id, String cause, String damage) {
        NotificationDB notifDB = new NotificationDB();
        Notification notif = notifDB.get(id);
        
        //get the notification cause object
        Cause notifCause = notif.getCauseId();
        notifCause.setCodeDescription(cause);
        
        //get the notification damage object
        Damage notifDmg = notif.getDamageId();
        notifDmg.setCodeDescription(damage);
        
        notifDB.update(notif);
    }
    
    /**
     * Method to update notification status to 'OPEN'
     * Added by Trevor Erixon
     * @param notif - Notification to be opened
     */
    public void openNotification(Notification notif) {
        NotificationDB notifDB = new NotificationDB();        
        StatusDB statusDB = new StatusDB();
        Status status = statusDB.get(2);
        notif.setStatus(status);
        notifDB.update(notif);        
    }
    
    /**
     * Method to update notification status to 'PAUSED'
     * Added by Trevor Erixon
     * @param notif - Notification to be paused
     */
    public void pauseNotification(Notification notif) {
        NotificationDB notifDB = new NotificationDB();        
        StatusDB statusDB = new StatusDB();
        Status status = statusDB.get(4);
        notif.setStatus(status);
        notifDB.update(notif);        
    }
    
    /**
    * This method closes the notification by updating it's status to 
    * 'CLOSED'.
    * @param notif - Notification to be closed.
    */
    public void closeNotification(Notification notif) {
        NotificationDB notifDB = new NotificationDB();
        StatusDB statusDB = new StatusDB();
        Status status = statusDB.get(3);
        notif.setStatus(status);
        notifDB.update(notif);
    }
    
    /**
    * This method deletes the notification object from the database, using its
    * ID.
    * @param id - Notification to be deleted.
    */
    public void delete(int id)  {
        NotificationDB notifDB = new NotificationDB();
        notifDB.delete(id);
    }
}
