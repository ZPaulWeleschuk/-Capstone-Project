package servlets;

import Services.UserService;
import Utilities.PasswordUtil;
import java.io.IOException;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Users;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

/**
 * Resets a user's password if an admin has forced them to reset password
 * @author rcgam
 */
public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Users u = (Users)session.getAttribute("user");
        if (u == null){ //If a user is not signed in
            response.sendRedirect("login");
        }
        else if(!u.getUserForcedReset()){//if a user is signed in but does not need a password reset.
            response.sendRedirect("admin");
            return;
        }
        request.setAttribute("message", request.getParameter("message"));
        getServletContext().getRequestDispatcher("/WEB-INF/resetPass.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Compile password in argon2 before recieveing. This goes for a all places. Note for future
        String regex = "^(?=.*[a-z])(?=."
               + "*[A-Z])(?=.*\\d)"
               + ".+$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
        //get session
        HttpSession session = request.getSession();
        //configure input sanitization policy -Zen
        PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS).and(Sanitizers.BLOCKS).and(Sanitizers.STYLES).and(Sanitizers.TABLES);
        //get inputs and santize inputs -Rylan/Zen
        String password = policy.sanitize(request.getParameter("newPass"));
        Users user = (Users) session.getAttribute("user");
        String conPass = policy.sanitize(request.getParameter("conPass"));
        
        if(password == null || password.equals("") ){
            response.sendRedirect("resetpassword?message=Please enter a password.");
            return;
            
        } else if ( !conPass.equals(password)){
            response.sendRedirect("resetpassword?message=Please enter the same password in both boxes.");
            return;
        } else if ( password.length() <8){
            response.sendRedirect("resetpassword?message=Password cannot be empty or less than 8 characters.");
            return;
        } else if ( !p.matcher((CharSequence)password).matches()){
            response.sendRedirect("resetpassword?message=Password must contain 1 capital letter, 1 lowercase letter and a number.");
            return;
        }
        else if( PasswordUtil.checkPasswordAgainstHashed(password, user.getUserHash())){
            response.sendRedirect("resetpassword?message=Password can not be the same as current password.");
            return;
        }
        UserService us = new UserService();
        
        us.update(user.getUserId(), password);
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
