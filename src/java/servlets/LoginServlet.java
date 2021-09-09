package servlets;

import Services.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Users;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

/**
 * Servlet that handles logins
 * @author Rylan Cook
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        HttpSession session = request.getSession();
        if (path.equals("/forcedLogout")) {//If user called for logout
            session.invalidate();
            request.setAttribute("message", "Something has went wrong, please try to login again");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
        else if (request.getParameter("logout") != null || path.equals("/logout")) {//If user called for logout
            session.invalidate();
            request.setAttribute("message", "You have sucessfully logged out.");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            
        }
        else if (session.getAttribute("userId") == null) {//If the user has not logined yet
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        } else {

            response.sendRedirect("admin");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            //configure input sanitization policy -Zen
            PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS).and(Sanitizers.BLOCKS).and(Sanitizers.STYLES).and(Sanitizers.TABLES);
            //get inputs and santize inputs -Rylan/Zen
            String loginId = policy.sanitize(request.getParameter("userId"));
            String loginPass = policy.sanitize(request.getParameter("password"));

            if (loginId.length() == 0 || loginPass.length() == 0) {
                request.setAttribute("message", "Please fill out both fields.");
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                return;
            }
            int userId;
            try {//Parse for int
            userId = Integer.parseInt(loginId);
            } catch (NumberFormatException e) {
                request.setAttribute("message", "Incorrect user ID or password");
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                return;
            }           

            UserService us = new UserService();
            Users user = us.login(userId, loginPass);//Check the password agains the DB
            if (user == null) {
                request.setAttribute("message", "Incorrect user ID or password");
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                return;
            }
            if(user.getUserLocked()){
                request.setAttribute("message", "This account has been locked. If you think this is a mistake, please contact an admin.");
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                return;
            }
            HttpSession session = request.getSession();
            session.setAttribute("userId", userId);
            session.setAttribute("user", user);
            if (user.getUserForcedReset()){
                response.sendRedirect("resetpassword");
                return;
            }
            if (null != user.getUserRole()) switch (user.getUserRole()) {
            case 30001:
            case 30002://Send 30001 and 30002 to admin if they are one
                response.sendRedirect("admin");
                break;
            case 30003:
                response.sendRedirect("workcenter");
                break;
            case 30004:
                response.sendRedirect("user");
                break;
            default:
                break;
        }        
    }
}
