package servlets;

import Services.NotificationService;
import Services.UserService;
import Services.WorkOrderService;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.*;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

/**
 * WorkCenterServlet.java is the servlet that handles the work center jsp and
 * its requests.
 *
 * @author Laura Diaz
 */
public class WorkCenterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Retrieve the current session
        HttpSession session = request.getSession();

        //Initializing the services needed to retrieve information
        NotificationService ns = new NotificationService();
        WorkOrderService os = new WorkOrderService();
        UserService us = new UserService();

        //Getting the user ID from current session
        int userId = (int) session.getAttribute("userId");
        try {
            //Initializing the user in the request
            Users user = us.getUser(userId);

            //Retrieve all the information from the workorder database
            List<Notification> notifs = ns.getAllByWorkCenter(user.getWorkCenterId().getWorkCenterId());
            List<NotifType> types = ns.getAllTypes();
            List<Cause> causes = ns.getAllCauses();
            List<Damage> damages = ns.getAllDamages();
            List<Plant> plants = ns.getAllPlants();
            List<TechnicalObject> objects = ns.getAllObjects();
            List<WorkCenter> centers = ns.getAllCenters();
            List<WorkOrder> orders = os.getAllByWorkCenter(user.getWorkCenterId().getWorkCenterId());
            List<OrderType> oTypes = os.getAllTypes();

            //Setting the entities and lists of entities as attributes in the request
            request.setAttribute("notifications", notifs);
            request.setAttribute("notifTypes", types);
            request.setAttribute("causes", causes);
            request.setAttribute("damages", damages);
            request.setAttribute("plants", plants);
            request.setAttribute("techObjects", objects);
            request.setAttribute("workCenters", centers);
            request.setAttribute("orders", orders);
            request.setAttribute("orderTypes", oTypes);
            request.setAttribute("workOrdersLength", orders.size());

        } catch (Exception ex) {
            Logger.getLogger(WorkCenterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        //dispatch workcenter.jsp
        getServletContext().getRequestDispatcher("/WEB-INF/workcenter.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //configure input sanitization policy -Zen
        PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS).and(Sanitizers.BLOCKS).and(Sanitizers.STYLES).and(Sanitizers.TABLES);

        //Retrieve the current session
        HttpSession session = request.getSession();
        Notification notif;

        //Initializing the services needed to perform transactions on the database
        NotificationService ns = new NotificationService();
        WorkOrderService os = new WorkOrderService();
        UserService us = new UserService();

        //Getting the user ID from current session
        int userId = (int) session.getAttribute("userId");
        //Initializing the user in the response
        Users user = us.getUser(userId);
        try {

            //Retrieve all the information from the workorder database
            List<Notification> notifs = ns.getAllByWorkCenter(user.getWorkCenterId().getWorkCenterId());
            List<NotifType> types = ns.getAllTypes();
            List<Cause> causes = ns.getAllCauses();
            List<Damage> damages = ns.getAllDamages();
            List<Plant> plants = ns.getAllPlants();
            List<TechnicalObject> objects = ns.getAllObjects();
            List<WorkCenter> centers = ns.getAllCenters();
            List<WorkOrder> orders = os.getAllByWorkCenter(user.getWorkCenterId().getWorkCenterId());
            List<OrderType> oTypes = os.getAllTypes();

            //Setting the entities and lists of entities as attributes in the request
            request.setAttribute("notifications", notifs);
            request.setAttribute("notifTypes", types);
            request.setAttribute("causes", causes);
            request.setAttribute("damages", damages);
            request.setAttribute("plants", plants);
            request.setAttribute("techObjects", objects);
            request.setAttribute("workCenters", centers);
            request.setAttribute("orders", orders);
            request.setAttribute("orderTypes", oTypes);
           request.setAttribute("workOrdersLength", orders.size());

        } catch (Exception ex) {
            Logger.getLogger(WorkCenterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        String action = request.getParameter("action");
        try {
            //If user wants to create a work order
            if (action.equals("create")) {
                //Retrieve all the parameters needed from request to create a work order
                //sanitize String inputs -Zen
                notif = ns.get(Integer.parseInt(policy.sanitize(request.getParameter("newOrderNotifId"))));
                String newOrderType = request.getParameter("newOrderType");
                String newOrderDesc = policy.sanitize(request.getParameter("newOrderDesc"));
                String newStartDate = request.getParameter("newOrderStart");
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                Date startDate = formatter.parse(newStartDate);
                String newEndDate = (String) request.getParameter("newOrderEnd");
                Date endDate = formatter.parse(newEndDate);

                //Passing all the parameters to create a new work order
                os.insertWorkOrder(notif.getNotificationId(), newOrderType, newOrderDesc, startDate, endDate, notif);
                String notificationMessage = "Work order created";
                request.setAttribute("notificationMessage", notificationMessage);
            } //If user select delete function for work orders
            else if (action.equals("deleteWorkOrder")) {
                // Retrieve ID of work order to delete
                String[] workOrders = request.getParameterValues("workOrderId");
//                System.out.println(Arrays.toString(workOrders));

                //Iterate through all the work orders
                for (String workOrder : workOrders) {
                    //Delete work orders
                    os.deleteWorkOrder(Integer.parseInt(workOrder));
                }
                //If user selects a notification
            } else if (action.equals("notifOptions")) {
                List<Status> allStatus = ns.getAllStatus();
                request.setAttribute("allStatus", allStatus);

                //Retrieve ID of selected notification
                int notifId = Integer.parseInt(request.getParameter("notifId"));
                //get notification object by its ID
                notif = ns.get(notifId);
                //get work order object based on the notification object
                WorkOrder wo = os.getWorkOrderByNotificationId(notif);

                //If no work order has selected notification, then work order doesn't exist
                if (wo.getWorkOrderId() == null) {
                    //Assign the Notification to the Create Work Order form
                    request.setAttribute("newOrderNotifId", notif.getNotificationId());
                    request.setAttribute("newOrderNotifType", notif.getNotificationType().getTypeDesc());
                    request.setAttribute("newOrderNotifCause", notif.getCauseId().getCodeDescription());
                    request.setAttribute("newOrderNotifDamage", notif.getDamageId().getCodeDescription());
                    request.setAttribute("newOrderNotifPlant", notif.getPlantId().getPlantName());
                    request.setAttribute("newOrderNotifObj", notif.getTechObjId().getTechObjName());

                    //If the work order exists
                } else {
                    //Assigning the work order to a session variable
                    session.setAttribute("workOrder", wo);
                    request.setAttribute("workOrderId", wo.getWorkOrderId());
                    //redirect to workorder  
                    response.sendRedirect("workorder?workOrderId=" + wo.getWorkOrderId());

                    return;
                }
            }
            //Retrieve all the work orders and display in jsp
            List<WorkOrder> orders = os.getAllByWorkCenter(user.getWorkCenterId().getWorkCenterId());
            request.setAttribute("orders", orders);
            request.setAttribute("workOrdersLength", orders.size());
        } catch (Exception ex) {
            String notificationMessage = "error: Work order not created";
            request.setAttribute("notificationMessage", notificationMessage);
            Logger.getLogger(WorkCenterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        //dispatch workcenter.jsp
        getServletContext().getRequestDispatcher("/WEB-INF/workcenter.jsp").forward(request, response);
    }

}
