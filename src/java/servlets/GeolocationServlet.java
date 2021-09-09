package servlets;

//import Services.NotificationService;
//import models.Notification;
import Services.WorkOrderService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.WorkOrder;

/**
 *
 * @author Laura Diaz
 */
public class GeolocationServlet extends HttpServlet {


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
        
        WorkOrderService wos = new WorkOrderService();
//        NotificationService ns = new NotificationService();
        
        try {
            WorkOrder order = wos.getWorkOrder(workOrderId); 
//            int notificationId = order.getNotificationId().getNotificationId();
//            Notification notification = ns.get(notificationId);
            
            request.setAttribute("workOrder", order);
        } catch (Exception e) {
            Logger.getLogger(GeolocationServlet.class.getName()).log(Level.SEVERE, null, e);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/location.jsp").forward(request, response);
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

}
