package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="MainServlet",urlPatterns={"/home"})
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri=request.getRequestURI();
		if (uri.endsWith("home") && Login.isLoggedIn(request, response)) {
			String page="studentPage";
			if (request.getAttribute("page")==null) {
				request.setAttribute("page","studentPage");
			}else {
				page=request.getAttribute("page").toString();
			}
			
			request.getRequestDispatcher(page).forward(request, response);
		}else {
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
	
}
