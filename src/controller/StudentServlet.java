package controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.jni.Time;

import beans.BatchBean;
import beans.CourseBean;
import beans.FeesBean;
import beans.StudentBean;
import beans.UsersBean;
import dao.BatchDAO;
import dao.CourseDAO;
import dao.FeesDAO;
import dao.StudentsDAO;
import interfacesDAO.BatchDAOI;
import interfacesDAO.CourseDAOI;
import interfacesDAO.FeesDAOI;

@WebServlet(name = "StudentServlet", urlPatterns = {"/studentPage", "/addStudent", "/updateStudent",
		"/delStudent","/student-noOfEntries","/student-firstPage",
		"/student-previousPage","/student-nextPage","/student-lastPage" })
@MultipartConfig
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StudentsDAO studentsDAOI = new StudentsDAO();
	private FeesDAOI feesDAOI = new FeesDAO();
	private int pageNo = 1;
	private String noOfRecords = "5";
	private long totalStudents = StudentsDAO.getNoStudents();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		String uri = request.getRequestURI();
		if (Login.isLoggedIn(request, response)) {
			if (uri.endsWith("addStudent")) {
				
				addStudent(request, response);
			
			}else if (uri.endsWith("student-noOfEntries")) {
				noOfRecords=request.getParameter("noOfRecords");
			}else if (uri.endsWith("delStudent")) {
				String rollNo = request.getParameter("rollNo");
				StudentBean studentBean=new StudentBean();
				studentBean.setRollNo(rollNo);
				boolean isDel = studentsDAOI.deleteStudent(studentBean);
				request.setAttribute("msg",isDel?"Student Deleted Successfully":"Student Deletion Failed");
			}else if (uri.endsWith("updateStudent")) {
				updateStudent(request, response);
			}else if (uri.endsWith("student-firstPage")) {
				pageNo=1;
			}else if (uri.endsWith("student-previousPage")) {
				if (pageNo>1) {
					pageNo--;
				}
			}else if (uri.endsWith("student-nextPage")) {
				
				int totalPage=noOfRecords.equalsIgnoreCase("all")?1: (int)Math.ceil(totalStudents/Double.valueOf(noOfRecords));
				if (totalPage>pageNo) {
					pageNo++;
				}
			}else if (uri.endsWith("student-lastPage")) {
				int totalPage=noOfRecords.equalsIgnoreCase("all")?1: (int)Math.ceil(totalStudents/Double.valueOf(noOfRecords));
				pageNo=totalPage;
			}
			
			setBatches(request, response);
			setCourses(request, response);
			setStudents(request, response);
			totalStudents = StudentsDAO.getNoStudents();
			request.setAttribute("totalStudents", totalStudents);
			request.setAttribute("page", "students.jsp");
			request.getRequestDispatcher("home.jsp").forward(request, response);
		} else {
			response.sendRedirect("login");
		}
	}

	private void setBatches(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BatchDAOI batchDAOI = new BatchDAO();
		List<BatchBean> batches = batchDAOI.getBatches();

		request.setAttribute("batches", batches);
	}

	private void setCourses(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CourseDAOI courseDAOI = new CourseDAO();
		List<CourseBean> courses = courseDAOI.getCourse();
		request.setAttribute("courses", courses);
	}

	private void setStudents(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<StudentBean> students = null;
		
		if (noOfRecords.equalsIgnoreCase("all")) {
			students = studentsDAOI.getStudents();
			pageNo=1;
		} else {
			int start = Integer.valueOf(noOfRecords) * (pageNo-1);
			int noOfRec = Integer.valueOf(noOfRecords);
			students = StudentsDAO.getStudent(start, noOfRec);
		}
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("listStudent", students);
	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sName = request.getParameter("name");
		String sRollNo = request.getParameter("rollNo");
		String dob = request.getParameter("dob");
		String batchName = request.getParameter("batchName");
		String fee = request.getParameter("fee");
		
		Timestamp admsnDate = new Timestamp(new Date().getTime());
		LocalDate sdob = LocalDate.parse(dob);
		UsersBean usersBean = (UsersBean) request.getSession().getAttribute("loggedin");
		StudentBean sBean = new StudentBean(sRollNo, sName, java.sql.Date.valueOf(sdob), admsnDate, batchName,
				usersBean.getEmail());
		FeesBean feesBean = new FeesBean(0, sRollNo, admsnDate, Long.valueOf(fee));
		boolean isSAdded = studentsDAOI.addStudent(sBean);
		boolean isFeesAdded = feesDAOI.addFees(feesBean);
		request.setAttribute("msg", (isFeesAdded && isSAdded) ? "Student Added Successfully" : "Student Adding Failed");
	}
	private void updateStudent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sName = request.getParameter("name");
		String sRollNo = request.getParameter("rollNo");
		String dob = request.getParameter("dob");
		String batchName = request.getParameter("batchName");
		
		LocalDate sdob = LocalDate.parse(dob);
		UsersBean usersBean = (UsersBean) request.getSession().getAttribute("loggedin");
		StudentBean sBean = new StudentBean(sRollNo, sName, java.sql.Date.valueOf(sdob), null, batchName,
				usersBean.getEmail());
		boolean isSUpdated = studentsDAOI.updateStudent(sBean);
		request.setAttribute("msg", ( isSUpdated) ? "Data Updated Successfully" : "Updating Data Failed");
	}
}
