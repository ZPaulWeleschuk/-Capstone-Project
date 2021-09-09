package Filters;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Users;

/**
 * Filter that makes sure a user is an admin before entering the page.
 * @author Rylan Cook
 */
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // convert request/response to proper types
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        
        // get logged in user's username
        HttpSession session = httpRequest.getSession();       
        int userId= (int)session.getAttribute("userId");
        try {//Checks if user is currently logged in
            
            //UserService us = new UserService();
            Users user = (Users)session.getAttribute("user");
            if (user == null){//Makes sure user is logged in. Just in cause the auth filter fails
                httpResponse.sendRedirect("login");
            }
            else if(null != user.getUserRole() ) switch (user.getUserRole()) {//If user is not null, check what kind of user it is
                case 30001:
                case 30002:
                    chain.doFilter(request, response);
                    break;
                case 30003:
                    httpResponse.sendRedirect("workcenter");
                    return;
                default:
                    httpResponse.sendRedirect("user");
            } else {
                httpResponse.sendRedirect("user");
            }
        } catch (IOException | ServletException ex) {
            Logger.getLogger(AdminFilter.class.getName()).log(Level.SEVERE, "Problem accessing user: {0}", userId); 
        }
        
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    
    @Override
    public void destroy() {}
    
}
