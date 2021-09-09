package servlets;

import Services.UserService;
import Services.WorkOrderService;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Users;
import models.WorkCenter;
import models.WorkOrder;

/**
 * Description: the servlet between the user.jsp and userServices
 * @author Zennon Weleschuk
 */
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");
        
        //get services
        UserService us = new UserService();
        WorkOrderService wos = new WorkOrderService();

        Users user;
        WorkCenter workCenter;
        

        try {
            user = us.getUser(userId);
            workCenter = user.getWorkCenterId();

            request.setAttribute("userFName", user.getUserFname());
            request.setAttribute("userLName", user.getUserLname());
            request.setAttribute("userRole", user.getUserRole());
            request.setAttribute("userWorkCenter", user.getWorkCenterId().getWorkCenterName());
            
            List<WorkOrder> workOrderList = wos.getAllByWorkCenter(workCenter.getWorkCenterId());

            request.setAttribute("workOrders", workOrderList);
            System.out.print("debugline");
        } catch (Exception e) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, "error999 : cant load user page", e);
            System.out.println("debug line");
        }
        getServletContext().getRequestDispatcher("/WEB-INF/user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
