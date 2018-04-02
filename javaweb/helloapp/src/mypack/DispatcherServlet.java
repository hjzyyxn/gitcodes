package mypack;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class DispatcherServlet extends GenericServlet {
	private String target = "/hello.jsp";
	
	public void service(ServletRequest request, ServletResponse response)
		throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		request.setAttribute("USER", username);
		request.setAttribute("PASSWORD", password);
		
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(target);
		dispatcher.forward(request, response);
	}

}
