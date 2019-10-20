package controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.CourseBean;
import dao.CourseDAO;

@WebServlet(name="CoursesServlet",urlPatterns= {"/addCourse","/coursesPage","/deleteCourse","/updateCourse","/course-nextPage","/course-previousPage","/course-noOfEntries","/course-firstPage","/course-lastPage"})
public class CoursesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static CourseDAO cDao=new CourseDAO();
	static String noOfEntry="5";
	static int pageNo=1;
	static int totalRecords=0;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri=request.getRequestURI();
		
		
		
		if (Login.isLoggedIn(request, response)) {
			if (uri.endsWith("coursesPage")) {
				loadList(request, response);
				sendToCourse(request, response);
			}else if (uri.endsWith("addCourse")) {
				String name=request.getParameter("name");
				String fees=request.getParameter("fees");
				Timestamp startDate=new Timestamp(new Date().getTime());
				CourseBean courseBean=new CourseBean(name,Long.valueOf(fees), startDate);
				boolean isAdded = cDao.addCourse(courseBean);
				request.setAttribute("msg", isAdded?"Course Added Succesfully":"Course Saving Failed");
				loadList(request, response);
				sendToCourse(request, response);
			}else if (uri.endsWith("deleteCourse")) {
				String name=request.getParameter("name");
				boolean isDel = cDao.deleteCourse(new CourseBean(name, 0, null));
				request.setAttribute("msg", isDel?"Course Deleted Succesfully":"Course Deletion Failed");
				loadList(request, response);
				sendToCourse(request, response);
			}else if (uri.endsWith("updateCourse")) {
				String name=request.getParameter("name");
				Long fees=Long.valueOf(request.getParameter("fees"));
				boolean isUpdated = cDao.updateCourse(new CourseBean(name, fees,new Timestamp(new Date().getTime())));
				request.setAttribute("msg", isUpdated?"Course Updated Succesfully":"Course Updation Failed");
				loadList(request, response);
				sendToCourse(request, response);
			}else if (uri.endsWith("course-nextPage")) {
				if (noOfEntry.equalsIgnoreCase("all")) {
					pageNo=1;
				}else {
					int tempPageNo=(int)Math.ceil(totalRecords/Double.valueOf(noOfEntry));
					if (tempPageNo>pageNo) {
						pageNo++;
					}
					
				}
				
				loadList(request, response);
				sendToCourse(request, response);
			}else if (uri.endsWith("course-previousPage")) {
				if (pageNo>1) {
					pageNo--;
				}
				loadList(request, response);
				sendToCourse(request, response);
			}else if (uri.endsWith("course-noOfEntries")) {
				noOfEntry=request.getParameter("id");
				pageNo=1;
				loadList(request, response);
				sendToCourse(request, response);
			
			}else if (uri.endsWith("course-lastPage")) {
				if (noOfEntry.equalsIgnoreCase("all")) {
					pageNo=1;
				}else {
					pageNo=(int) Math.ceil(totalRecords/Double.valueOf(noOfEntry));
				}
				loadList(request, response);
				sendToCourse(request, response);
			}else if (uri.endsWith("course-firstPage")) {
				pageNo=1;
				loadList(request, response);
				sendToCourse(request, response);
			}
			
		}else {
			response.sendRedirect("login");
		}
	}
	private void sendToCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		totalRecords=cDao.getNoOfCourses();
		request.setAttribute("totalRecords", totalRecords);
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("page", "courses.jsp");
		request.getRequestDispatcher("home.jsp").forward(request, response);
	}
	private void allCourseList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<CourseBean> listCourse = cDao.getCourse();
		request.setAttribute("list", listCourse);
	}
	private void loadList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (noOfEntry.equalsIgnoreCase("all")) {
			allCourseList(request, response);
		}else {
			List<CourseBean> listCourse = cDao.getCourse(Integer.valueOf(noOfEntry), pageNo);
			request.setAttribute("list", listCourse);
		}
		
	}
}