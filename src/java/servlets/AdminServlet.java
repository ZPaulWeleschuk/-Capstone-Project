package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.*;
import Services.*;
import java.util.Arrays;
import dataaccess.*;
import java.util.regex.Pattern;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

/**
 * AdminServlet.java is the servlet that handles the admin jsp and its
 * requests. 
 * @author Laura Diaz, Jaehan Kim
 */
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Retrieve the current session

        //Initializing the services needed to retrieve information
        NotificationService ns = new NotificationService();
        WorkOrderService wos = new WorkOrderService();
        OperationsService os = new OperationsService();
        UserService us = new UserService();
        
        //Load RolesDB from dataaccess to add input Users table
        RolesDB rolesDB = new RolesDB();
        
        
        // retrieve notification, work-order, operations and user entities from work-order database
        try {
            List<Notification> notifs = ns.getAll();
            List<NotifType> types = ns.getAllTypes();
            List<Cause> causes = ns.getAllCauses();
            List<Damage> damages = ns.getAllDamages();
            List<Plant> plants = ns.getAllPlants();
            List<TechnicalObject> objects = ns.getAllObjects();
            List<WorkCenter> centers = ns.getAllCenters();
            List<WorkOrder> orders = wos.getAllWorkOrders();
            List<Operations> operations = os.getAll();
            List<Users> users = us.getAll();
            List<Roles> roles = rolesDB.getAll();

            //setting the entities and lists of entities as attributes in the request
            request.setAttribute("notifications", notifs);
            request.setAttribute("notifTypes", types);
            request.setAttribute("causes", causes);
            request.setAttribute("damages", damages);
            request.setAttribute("plants", plants);
            request.setAttribute("techObjects", objects);
            request.setAttribute("workCenters", centers);
            request.setAttribute("orders", orders);
            request.setAttribute("operations", operations);
            request.setAttribute("users", users);
            request.setAttribute("notificationsLength", notifs.size());
            request.setAttribute("workOrdersLength", orders.size());
            request.setAttribute("operationsLength", operations.size());
            request.setAttribute("usersLength", users.size());
            request.setAttribute("roles", rolesDB);
            request.setAttribute("roless", roles);
            request.setAttribute("notificationMessage", request.getParameter("notificationMessage"));
            request.setAttribute("message", request.getParameter("message"));

        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        // dispatch admin.jsp page
        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //configure input sanitization policy -Zen
        PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS).and(Sanitizers.BLOCKS).and(Sanitizers.STYLES).and(Sanitizers.TABLES);
        
        //Retrieve the current session
        HttpSession session = request.getSession();
        //Initializing the services needed to perform the requests
        NotificationService ns = new NotificationService();
        WorkOrderService wos = new WorkOrderService();
        OperationsService os = new OperationsService();
        UserService us = new UserService();
        
        //Load RolesDB from dataaccess to add input Users table
        
        String action = request.getParameter("action");
        Users user = (Users)session.getAttribute("user");
        
        try {
            //If we are forcing a password change
            if (action.equals("forceUser")){
                int id = Integer.parseInt(policy.sanitize(request.getParameter("changedUser")));
                if (id == user.getUserId()){
                    response.sendRedirect("admin?message=User Password Change Error: Cannot force reset on your own account.");
                    return;
                }
                us.forcePasswordUpdate(id);
                response.sendRedirect(String.format("admin?message=User %d will be forced to update password on next logon/action.",id));
                return;
            }
            else if (action.equals("lockUser")){
                int id = Integer.parseInt(policy.sanitize(request.getParameter("changedUser")));
                if (id == user.getUserId()){
                    response.sendRedirect("admin?message=User Lock Error: Cannot lock your own account.");
                    return;
                }
                int val = us.updateLockedStatus(id);
                if (val == 0){
                    response.sendRedirect(String.format("admin?message=User %d  is unlocked.",id));
                }
                else if(val == 1){
                    response.sendRedirect(String.format("admin?message=User %d  is locked.",id));
                }
                else{
                    response.sendRedirect(String.format("admin?message=Something went wrong with user %d.",id));
                }
                
                return;
            }
            //If user clicks on create user
            else if (action.equals("createUser")) { 
                //Retrieve all the parameters needed from request to create a user
                //santize inputs -Zen
                String firstName = policy.sanitize(request.getParameter("firstName"));
                String lastName = policy.sanitize(request.getParameter("lastName"));
                String password = policy.sanitize(request.getParameter("password"));
                String regex = "^(?=.*[a-z])(?=."
                       + "*[A-Z])(?=.*\\d)"
                       + ".+$";
 
                // Compile the ReGex
                Pattern p = Pattern.compile(regex);
                if (firstName == null || firstName.equals("") || lastName == null || lastName.equals("")){
                    response.sendRedirect("admin?message=User Creation Error: Please fill in the form.");
                    return;
                } else if (password == null || password.equals("") || password.length() <8 ){
                    response.sendRedirect("admin?message=User Creation Error: Password cannot be empty or less than 8 characters.");
                    return;
                } else if ( !p.matcher((CharSequence)password).matches()){
                    response.sendRedirect("admin?message=User Creation Error: Password must contain 1 capital letter, 1 lowercase letter and a number");
                    return;
                }
                int role = Integer.parseInt(request.getParameter("roles"));
                String workCenterId = request.getParameter("wc_Id");
                WorkCenterDB wcdb = new WorkCenterDB();
                WorkCenter wc = wcdb.get(workCenterId);
                
                //Getting the list of all users to define the new user's ID
                List<Users> users = us.getAll();
                int userId = users.get(users.size() - 1).getUserId() + 1;
                
                //Passing all the parameters to create the new user
                us.insert(userId, firstName, lastName,password, role, wc);
                response.sendRedirect("admin?message=User created");
                return;
            }
            
             // If user clicks on create notification entity
            else if (action.equals("createNotification")) {
                //Retrieve all the parameters needed from request to create a new notification
                String notificationType = request.getParameter("notificationType");
                int notifCause = Integer.parseInt(request.getParameter("notifCause"));
                int notifDamage = Integer.parseInt(request.getParameter("notifDamage"));
                int notifPlant = Integer.parseInt(request.getParameter("notifPlant"));
                int notifObject = Integer.parseInt(request.getParameter("notifObject"));
                String notifWorkCenter = request.getParameter("notifWorkCenter");
                
                //Getting the list of all notifications to define new notification's ID
                List<Notification> notifs = ns.getAll();
                if(!notifs.isEmpty()) {
                    int notificationId = notifs.get(notifs.size() - 1).getNotificationId() + 1;
                     ns.createNotification(notificationId, notificationType, notifCause, notifDamage, notifPlant,
                        notifObject, user.getUserId(), 1, notifWorkCenter);
                } else {
                    int notificationId = 1000001;
                     ns.createNotification(notificationId, notificationType, notifCause, notifDamage, notifPlant,
                        notifObject, user.getUserId(), 1, notifWorkCenter);
                }
                //Passing all the parameters to create new notification
              
               response.sendRedirect("admin?notificationMessage=Notification created");
               System.out.println("debug line: notification");
               return;
            }
         
             //Read Checkbox for Notification Table for delete function
            else if (action.equals("deleteNotification")){
                //Retrieve ID of notification to delete
                String [] notifications = request.getParameterValues("notificationId");
                System.out.println(Arrays.toString(notifications));
                
                //Iterate through all the notifications
                for (String notification : notifications) {
                    //Delete notification
                    ns.delete(Integer.parseInt(notification));
                }
                
            }
             
              //Read Checkbox for Workorder Table for delete function
            else if (action.equals("deleteWorkOrder")){
                // Retrieve ID of work order to delete
                String [] workOrders = request.getParameterValues("workOrderId");
                System.out.println(Arrays.toString(workOrders));
                
                //Iterate through all the work orders
                for (String workOrder : workOrders) {
                    //Delete work orders
                    wos.deleteWorkOrder(Integer.parseInt(workOrder));
                }
            }
            
            //Read Checkbox for Operation Table for delete function
            else if (action.equals("deleteOperation")){
                //Retrieve ID of operation to delete
                String [] operations = request.getParameterValues("operationId");
                System.out.println(Arrays.toString(operations));
                
                //Iterate through all the operations
                for (String operation : operations) {
                    //Delete operation
                    os.delete(Integer.parseInt(operation));
                }                
            }
             
            // Commented out delete function for users
             //Read Checkbox for User Table for delete function
//            else if (action.equals("deleteUser")){
//                //Retrieve ID of user to delete
//                String [] users = request.getParameterValues("userId");
//                System.out.println(Arrays.toString(users));
//                
//                //Current user perfoming deletion
//                int deleterId = user.getUserId();
//                for (String user1 : users) {
//                    //Delete user
//                    us.delete(Integer.parseInt(user1), deleterId);
//                }
//                
//            }

        } catch (IOException | NumberFormatException ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Sending a redirect, because if a page is refreshed, it actually send the same message as last time, so we force it to not be able to
        response.sendRedirect("admin");

    }

}
