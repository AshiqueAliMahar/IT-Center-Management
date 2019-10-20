package controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.FeesBean;
import beans.StudentBean;
import dao.FeesDAO;
import dao.StudentsDAO;
import interfacesDAO.FeesDAOI;
import interfacesDAO.StudentsDAOI;

@WebServlet(name="FeesServlet",urlPatterns= {"/feesPage","/addFees","/delFees","/updateFees",
		"/fees-firstPage","/fees-previousPage","/fees-nextPage","/fees-lastPage","/fees-noOfEntries"})
public class FeesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    FeesDAOI feesDAOI=new FeesDAO();
    StudentsDAOI studentsDAOI=new StudentsDAO();
    
    FeesBean feesBean=new FeesBean();
    
    private int pageNo=1;
    private String noOfEntries="5";
    private long totalNoOfFees=feesDAOI.getTotalNoOfFees();
    private long totalNoOfSumFees=FeesDAO.getTotalNoOfSumFees();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri=request.getRequestURI();
		if (Login.isLoggedIn(request, response)) {
			
			if (uri.endsWith("addFees")) {
				String rollNo=request.getParameter("rollNo");
				String fees=request.getParameter("fees");
				Timestamp nowTStamp = Timestamp.valueOf(LocalDateTime.now());
				feesBean=new FeesBean(0, rollNo, nowTStamp, Long.valueOf(fees));
				boolean isAdded = feesDAOI.addFees(feesBean);
				request.setAttribute("msg",isAdded?"Record Added Successfully":"Record Adding Failed" );
			}else if (uri.endsWith("fees-noOfEntries")) {
				noOfEntries=request.getParameter("noOfEntries");
			}else if (uri.endsWith("fees-firstPage")) {
				pageNo=1;
			}else if (uri.endsWith("fees-previousPage")) {
				System.out.println(pageNo+"previous page");
				if (pageNo>1) {
					pageNo--;
				}
			}else if (uri.endsWith("fees-nextPage")) {
				int pageNoI=(int)Math.ceil(totalNoOfSumFees/Double.valueOf(noOfEntries));
				if (pageNoI>pageNo) {
					pageNo++;
				}
			}else if (uri.endsWith("fees-lastPage")) {
				pageNo=(int)Math.ceil(totalNoOfSumFees/Double.valueOf(noOfEntries));
			}
			dispatch(request, response);
		}else {
			response.sendRedirect("login");
		}
	}
	private void setSumFees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<FeesBean> listSumFees =new LinkedList<>(); 
		List<StudentBean> lStudentBeans=new LinkedList<>();
		if (noOfEntries.equals("all")) {
			pageNo=1;
			listSumFees=feesDAOI.getSumFees();
			lStudentBeans=studentsDAOI.getStudents();
		}else {
			int noOfEntriesI=Integer.valueOf(noOfEntries);
			listSumFees=feesDAOI.getSumFees((pageNo-1)*noOfEntriesI, noOfEntriesI);
			StudentBean studentBean=new StudentBean();
			for (FeesBean fsBean :listSumFees) {
				studentBean.setRollNo(fsBean.getRollNo());
				System.out.println(fsBean.getRollNo());
				lStudentBeans.add(studentsDAOI.getStudent(studentBean));
			}
		}
		request.setAttribute("listSumFees", listSumFees);
		request.setAttribute("lstStudents",lStudentBeans);
	}
	private void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		setSumFees(request, response);	
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("totalNoOfFees", totalNoOfFees);
		request.setAttribute("page","fees.jsp");
		request.getRequestDispatcher("home.jsp").forward(request, response);
	}
}
