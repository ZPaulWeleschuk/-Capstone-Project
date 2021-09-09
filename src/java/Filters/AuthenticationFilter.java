package Filters;

import Services.UserService;
import java.io.IOException;
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
 * Makes sure that the user is logged in when entering anything that needs that.
 * @author Rylan Cook
 */
public class AuthenticationFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

            // code that is executed before the servlet
            // convert request/response to proper types
            HttpServletRequest httpRequest = (HttpServletRequest)request;
            HttpServletResponse httpResponse = (HttpServletResponse)response;
            
       
            HttpSession session = httpRequest.getSession();
            
            Users user = (Users)session.getAttribute("user");//Check if user is logged in.
            if (user == null) {
                
                httpResponse.sendRedirect("login");
                return;
            } 
            UserService us = new UserService();
            //Check if user has to change his password.
            if (us.checkLockedAndReset(user.getUserId())){
                httpResponse.sendRedirect("forcedLogout");
                return;
            }               
            chain.doFilter(request, response); // execute the servlet 

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void destroy() {
       
    }
}