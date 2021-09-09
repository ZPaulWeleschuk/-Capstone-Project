package Services;

import Utilities.PasswordUtil;
import dataaccess.*;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.*;

/**
 * Description: methods between servlet and userDB access
 * @author Zennon Weleschuk
 */
public class UserService {
    
    /**
     * get user object from user id
     * @param userId unique user id
     * @return user object specified
     */
    public Users getUser(int userId) {
        UsersDB usersDB = new UsersDB();
        Users user = usersDB.get(userId);
        return user;
    }
    
    /**
     * method to login a user
     * @param userId unique login id
     * @param password password associated with user 
     * @return the user object that has been logged in successfully, else null
     */
    public Users login(int userId, String password) {
        UsersDB userDB = new UsersDB();
        
        Users user = userDB.get(userId);
        if (user != null && PasswordUtil.checkPasswordAgainstHashed(password, user.getUserHash())){
            return user;
        }
        return null;
    }
    /**
     * Check if the user needs to reset password
     * @param userId The user to check
     * @return If they need to reset password.
     */
    public Boolean checkReset(int userId){
        UsersDB userDB = new UsersDB();
        Users user = userDB.get(userId);
        return user.getUserForcedReset();
    }
    /**
     * Checks if the user account is locked
     * @param userId The user to check
     * @return If the account is locked.
     */
    public Boolean checkLocked(int userId){
        UsersDB userDB = new UsersDB();
        Users user = userDB.get(userId);
        return user.getUserLocked();
    }
    /**
     * Checks if the user account is locked and or reset. 
     * @param userId The user to check
     * @return If the account is locked or reset.
     */
    public Boolean checkLockedAndReset(int userId){
        UsersDB userDB = new UsersDB();
        Users user = userDB.get(userId);
        return user.getUserLocked() || user.getUserForcedReset();
    }
    /**
     *  get a list of all users
     * @return list of all users objects
     */
    public List<Users> getAll()  {
        UsersDB userDB = new UsersDB();
        List<Users> users = userDB.getAll();
        return users;
    }
    
    /**
     * Gets All Users associated with a WorkCenter
     * Added by: Laura Diaz
     * @param workCenterId The WorkCenter that the user is associated with.
     * @return A list of users associated with given WorkCenter
     */
    public List<Users> getAllByWorkCenter(String workCenterId) {
        UsersDB userDB = new UsersDB();
        List<Users> centerUsers = userDB.getAllByWorkCenter(workCenterId);
        return centerUsers;
    }
    
    /**
     * Inserts a new user but with password
     * @param userId Used to find user to update
     * @param fname First name of user
     * @param lname Last name of user
     * @param password Hashed password of the user
     * @param user_role Role of user
     * @param workcenter WorkCenter user belongs to
     */
    public void insert(int userId, String fname, String lname,String password, int user_role, WorkCenter workcenter) {
        UsersDB us = new UsersDB();
        Users user = new Users();
        user.setUserId(userId);
        user.setUserFname(fname);
        user.setUserLname(lname);
        user.setUserRole(user_role);
        user.setWorkCenterId(workcenter);
        user.setUserHash(PasswordUtil.hashPassword(password));
        user.setUserForcedReset(Boolean.FALSE);
        user.setUserLocked(Boolean.FALSE);
        us.insert(user);
    }
                
    /**
     * deletes a user if from admin and not user itself
     * @param userId user id
     * @param deleterId user id
     * @throws AccessDeniedException When the user tries to delete themselves
     */
    public void delete(int userId,int deleterId) throws AccessDeniedException {
        if (userId == deleterId){
            throw new AccessDeniedException("Tried to delete theirself" );
        }
        UsersDB us = new UsersDB();
        Users user = us.get(userId);

        us.delete(user);
    }

    /**
     * update a users information, except userId
     * @param userId userid used to find user to update
     * @param fname first name of user
     * @param lname last name of user
     * @param password password of user
     * @param user_role role of user
     * @param workcenter workcenter user belongs to
     */
    public void update(int userId, String fname, String lname, String password, int user_role, WorkCenter workcenter) {
        try {
            UsersDB us = new UsersDB();
            Users user = us.get(userId);
            user.setUserFname(fname);
            user.setUserLname(lname);
            user.setUserRole(user_role);
            user.setWorkCenterId(workcenter);
            if (!PasswordUtil.checkPasswordAgainstHashed(password, user.getUserHash())){//Now checks to see if the passwords are different, so that it doesn't update a forced reset
                user.setUserHash(PasswordUtil.hashPassword(password));
                user.setUserForcedReset(Boolean.FALSE);
            }
            us.update(user);
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Method to update user only using password
     * @param userId The user to update
     * @param password The new password
     */
    public void update(int userId, String password) {
        try {
            UsersDB us = new UsersDB();
            Users user = us.get(userId);
            user.setUserHash(PasswordUtil.hashPassword(password));
            user.setUserForcedReset(Boolean.FALSE);
            us.update(user);
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Method to change the lock status to the opposite of the current status (True -&gt; False, False -&gt; True)
     * @param userId The user to change lock status on
     * @return 0 if the user account is not locked, 1 if it is, and anything else if something went wrong.
     */
    public int updateLockedStatus(int userId) {
        try {
            UsersDB us = new UsersDB();
            Users user = us.get(userId);
            user.setUserLocked(!user.getUserLocked());//Unlock/Lock account
            us.update(user);
            return user.getUserLocked() ? 1 : 0;//1 is true, 0 is false
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 2;//Anything bigger than 1 is error
    }
    /**
     * Forces user to update password on next login
     * @param userId The user to force
     */
    public void forcePasswordUpdate(int userId) {
        try {
            UsersDB us = new UsersDB();
            Users user = us.get(userId);
            user.setUserForcedReset(Boolean.TRUE);
            
            us.update(user);
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * insert user by object
     * @param u user object
     */
    public void insert(Users u) {
        UsersDB us = new UsersDB();
        try{
        us.insert(u);
        }
        catch (Exception e){
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
