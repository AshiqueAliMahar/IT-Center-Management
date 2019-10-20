package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UsersBean;
import dao.UsersDAO;
import interfacesDAO.UsersDAOI;

@WebServlet(name="Login",urlPatterns= {"/Login","/login","/logout"})
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UsersDAOI usersDAOI=new UsersDAO();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri=request.getRequestURI();
		HttpSession session = request.getSession(true);
		if (uri.endsWith("login")) {
			if (isLoggedIn(request, response)) {
				request.getRequestDispatcher("home").forward(request,response);
			}else {
				String email=request.getParameter("email");
				String password=request.getParameter("password");
				UsersBean user = usersDAOI.getUser(new UsersBean(email, "", password, "", null));
				//For Setting Message 
				
				
				if (user!=null) {
					
					session.setAttribute("loggedin", user);
					session.setMaxInactiveInterval(0);
					response.sendRedirect("home");
				}else {
					if ((email!=null && password!=null) && (email.length()>0 || password.length()>0)) {
						request.setAttribute("msg", "Incorrect Email or Password");
					}else {
						request.setAttribute("msg","");
					}
					
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
			}
		}else if (uri.endsWith("logout")) {
			session.invalidate();
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} 
	}
	public static boolean isLoggedIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isLoggedIn=false;
		HttpSession session = request.getSession(true);
		if(session.getAttribute("loggedin")!=null){
			isLoggedIn=true;
		}
		return isLoggedIn;
	}
	

}
