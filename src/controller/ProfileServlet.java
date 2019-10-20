package controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.codec.binary.Base64;

import beans.UsersBean;
import dao.UsersDAO;
import interfacesDAO.UsersDAOI;

@WebServlet(name="ProfileServlet" ,urlPatterns= {"/profilePage","/updateProfile"})
@MultipartConfig
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsersDAOI usersDAOI=new UsersDAO();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (Login.isLoggedIn(request, response)) {
			String uri=request.getRequestURI();
			UsersBean usersBean=new UsersBean();
			if (uri.endsWith("profilePage")) {
				
				usersBean=(UsersBean)request.getSession().getAttribute("loggedin");
				request.setAttribute("cnic", usersBean.getCnic());
				request.setAttribute("email", usersBean.getEmail());
				request.setAttribute("name", usersBean.getName());
				request.setAttribute("image", Base64.encodeBase64String(usersBean.getPic()));
				request.setAttribute("page", "profile.jsp");
				
			}else if (uri.endsWith("updateProfile")) {
				String name=request.getParameter("name");
				String cnic=request.getParameter("cnic");
				String email=request.getParameter("update");
				Part image=request.getPart("image");
				InputStream imageIS = image.getInputStream();
				byte [] pic=  new byte[imageIS.available()];
				imageIS.read(pic);
				usersBean=new UsersBean(email, name, "", cnic, pic);
				boolean isUpdated = usersDAOI.updateUser(usersBean);
				request.setAttribute("msg", isUpdated?"Your Profile Modifid Successfully":"Profile Modification Failed");
				request.getSession().invalidate();
				request.getSession(true);
			}
			
			if (!Login.isLoggedIn(request, response)) {
				response.sendRedirect("login");
			}else {
				request.getRequestDispatcher("home.jsp").forward(request, response);
			}

		}else {
			response.sendRedirect("login");
		}
	}

}
