package servlets;

import Services.*;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.*;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

/**
 * WorkOrderServlet controls function of the work order jsp
 * @author Trevor Erixon
 */
public class WorkOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String workOrderIds = request.getParameter("workOrderId");
        if (workOrderIds == null || workOrderIds.equals("")){
            response.sendRedirect("admin");
            return;
        }
        int workOrderId = Integer.parseInt(request.getParameter("workOrderId"));
        session.setAttribute("workOrderId", workOrderId);
        int userId = (int)session.getAttribute("userId");        
        int operationsCompleted = 0;
        boolean isPaused = false;

        boolean showUpdateWorkOrderButton = false;
        request.setAttribute("showUpdateWorkOrderButton", showUpdateWorkOrderButton);

        WorkOrderService wos = new WorkOrderService();
        NotificationService ns = new NotificationService();
        UserService us = new UserService();
        
        //get the current work order and all status options to allow for status changes
        try {
            Users user = us.getUser(userId);
            int roleId = user.getUserRole();
            WorkOrder order = wos.getWorkOrder(workOrderId);
            List<Users> users = us.getAllByWorkCenter(order.getNotificationId().getWorkCenterId().getWorkCenterId());            
            List<Status> allStatus = ns.getAllStatus();            
            List<Operations> operationsList = order.getOperationsList();
            int notificationId = order.getNotificationId().getNotificationId();
            Notification notification = ns.get(notificationId);

            //loop through operations list to confirm how many have been completed
            for (int i = 0; i < operationsList.size(); i++) {
                if (operationsList.get(i).getStatus().getStatusId() == 3) {
                    operationsCompleted++;
                }
                if (operationsList.get(i).getStatus().getStatusId() == 4) {
                    isPaused = true;
                }
            }

            if (operationsCompleted == operationsList.size()) {
                showUpdateWorkOrderButton = true;
                request.setAttribute("showUpdateWorkOrderButton", showUpdateWorkOrderButton);
            }
            else if (isPaused) {                
                ns.pauseNotification(notification);
                wos.pauseWorkOrder(workOrderId);
            }
            else {
                ns.openNotification(notification);
                wos.openWorkOrder(workOrderId); 
            }
            order = wos.getWorkOrder(workOrderId);
            request.setAttribute("allUsers", users);
            request.setAttribute("workOrder", order);
            request.setAttribute("allStatus", allStatus);
            request.setAttribute("roleId", roleId);
        } catch (Exception e) {
            Logger.getLogger(WorkOrderServlet.class.getName()).log(Level.SEVERE, null, e);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/workorder.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //configure input sanitization policy -Zen
        PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS).and(Sanitizers.BLOCKS).and(Sanitizers.STYLES).and(Sanitizers.TABLES);
            
        HttpSession session = request.getSession();
        int workOrderId = (int) session.getAttribute("workOrderId");
        int userId = (int)session.getAttribute("userId");
        //determine whether any buttons have been activated
        boolean updateOperationButton = request.getParameter("action") != null && request.getParameter("action").equals("updateOperation");
        boolean updateWorkOrderButton = request.getParameter("action") != null && request.getParameter("action").equals("updateWorkOrder");
        boolean createOperationButton = request.getParameter("action") != null && request.getParameter("action").equals("createOperation");
        //conditional parameter
        boolean showUpdateWorkOrderButton = false;
        request.setAttribute("showUpdateWorkOrderButton", showUpdateWorkOrderButton);

        WorkOrder workOrder;
        Users user;
        List<Status> allStatus;
        List<Operations> operationsList;
        int operationsCompleted = 0;
        int roleId;
        boolean isPaused = false;
        boolean operationError;

        WorkOrderService wos = new WorkOrderService();
        NotificationService ns = new NotificationService();
        OperationsService ops = new OperationsService();
        UserService us = new UserService();
        
        try {
            user = us.getUser(userId);
            workOrder = wos.getWorkOrder(workOrderId);
            roleId = user.getUserRole();
            
            if (updateOperationButton) {
                //Operation fields to pass to update method
                //sanitize String inputs -Zen
                int operationIdToEdit = Integer.parseInt(request.getParameter("operationIdToEdit"));
                String description = request.getParameter("operationDescription");
                String longText = policy.sanitize(request.getParameter("longText"));
                int statusId = Integer.parseInt(request.getParameter("updateStatus"));                
                int personId = Integer.parseInt(request.getParameter("personId"));
                  
                //only the assigned user or an admin should be able to update operations
                if (personId == user.getUserId() || user.getUserRole() == 30001 || user.getUserRole() == 30002 || user.getUserRole() == 30003) {
                    //update selected operation
                    ops.update(operationIdToEdit, description, longText, workOrderId, statusId, personId);
                    String operationMessage = "Operation updated successfully";
                    request.setAttribute("operationMessage", operationMessage);
                }
                else {
                    operationError = true;
                    String operationErrorMessage = "You cannot update operations that are not assigned to you.";
                    request.setAttribute("operationError", operationError);
                    request.setAttribute("operationErrorMessage", operationErrorMessage);
                    String operationMessage = "";
                    request.setAttribute("operationMessage", operationMessage);
                }                

            } else if (updateWorkOrderButton) {                
                String closingNotes = policy.sanitize(request.getParameter("closingNotes"));

                try {
                    int notificationId = workOrder.getNotificationId().getNotificationId();
                    Notification notification = ns.get(notificationId);
                    ns.closeNotification(notification);

                    wos.closeWorkOrder(workOrder.getWorkOrderId(), closingNotes);
                    String closingMessage = "Closing notes added";
                    request.setAttribute("closingMessage", closingMessage);
                } catch (Exception e) {

                }
            } else if (createOperationButton) {
                //Retrieving all the values needed for creating a new operation
                int newOperationId = Integer.parseInt(policy.sanitize(request.getParameter("newOperationId")));
                int newOperationAssignTo = Integer.parseInt(request.getParameter("newOperationAssignTo"));
                String newOperationDesc = policy.sanitize(request.getParameter("newOperationDesc"));
                int newOperationStatus = Integer.parseInt(request.getParameter("newOperationStatus"));
                String newOperationLongText = policy.sanitize(request.getParameter("newOperationLongText"));
                
                ops.insert(newOperationId, newOperationDesc, newOperationLongText, workOrderId, newOperationStatus, newOperationAssignTo);
                String operationMessage = "Operation created successfully";
                request.setAttribute("operationMessage", operationMessage);
            }           

            List<Users> users = us.getAllByWorkCenter(workOrder.getNotificationId().getWorkCenterId().getWorkCenterId());    
            allStatus = ns.getAllStatus();
            workOrder = wos.getWorkOrder(workOrderId);
            operationsList = workOrder.getOperationsList();
            int notificationId = workOrder.getNotificationId().getNotificationId();
            Notification notification = ns.get(notificationId);
            
            //loop through operations list to confirm how many have been completed
            for (int i = 0; i < operationsList.size(); i++) 
            {
                if (operationsList.get(i).getStatus().getStatusId() == 3) {
                    operationsCompleted++;
                }
                if (operationsList.get(i).getStatus().getStatusId() == 4) {
                    isPaused = true;
                }
            }

            if (operationsCompleted == operationsList.size()) {
                showUpdateWorkOrderButton = true;
                request.setAttribute("showUpdateWorkOrderButton", showUpdateWorkOrderButton);
            }
            else if (isPaused) {                
                ns.pauseNotification(notification);
                wos.pauseWorkOrder(workOrderId);
            }
            else {
                ns.openNotification(notification);
                wos.openWorkOrder(workOrderId); 
            }
            workOrder = wos.getWorkOrder(workOrderId);
            request.setAttribute("allUsers", users);
            request.setAttribute("workOrder", workOrder);
            request.setAttribute("allStatus", allStatus);
            request.setAttribute("roleId", roleId);
        } catch (NumberFormatException e) {
            Logger.getLogger(WorkOrderServlet.class.getName()).log(Level.SEVERE, null, e);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/workorder.jsp").forward(request, response);
    }

}
