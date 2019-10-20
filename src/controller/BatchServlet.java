package controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BatchBean;
import beans.CourseBean;
import dao.BatchDAO;
import dao.CourseDAO;
import interfacesDAO.BatchDAOI;

@WebServlet(name = "BatchServlet", urlPatterns = { "/batchPage", "/addBatch", "/deleteBatch",
		"/updateBatch","/batch-noOfEntries","/batch-firstPage","/batch-previousPage","/batch-nextPage","/batch-lastPage" })
public class BatchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CourseDAO courseDAO = new CourseDAO();
	private BatchDAOI batchDAOI = new BatchDAO();
	private BatchBean batchBean = new BatchBean();
	private int pageNo=1;
	private String noOfEntries="5";
	private long totalRecords=batchDAOI.totalRecords();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		if (Login.isLoggedIn(request, response)) {
			if (uri.endsWith("addBatch")) {
				addUpdateBatch(request, response,true);
			} else if (uri.endsWith("deleteBatch")) {
				String batchName = request.getParameter("batchName");
				batchBean.setBatchName(batchName);
				request.setAttribute("msg",
						(batchDAOI.deleteBatch(batchBean)) ? "Record Deleted Successfully" : "Record Deletion Failed");
			} else if (uri.endsWith("updateBatch")) {
				addUpdateBatch(request, response,false);
			}else if (uri.endsWith("batch-noOfEntries")) {
				noOfEntries=request.getParameter("noOfEntries");
			}else if (uri.endsWith("batch-firstPage")) {
				pageNo=1;
			}else if (uri.endsWith("batch-previousPage")) {
				if (pageNo>1) {
					pageNo--;
				}
			}else if (uri.endsWith("batch-nextPage")) {
				if (noOfEntries.equalsIgnoreCase("all")) {
					pageNo=1;
				}else {
					int totalPage=(int)Math.ceil(totalRecords/Double.valueOf(noOfEntries));
					if (pageNo<totalPage) {
						pageNo++;
					}
				}
				
			}else if (uri.endsWith("batch-lastPage")) {
				if (noOfEntries.equalsIgnoreCase("all")) {
					pageNo=1;
				}else {
					pageNo=(int)Math.ceil(totalRecords/Double.valueOf(noOfEntries));
				}
				
				
			}
			dispatch(request, response);
		} else {
			response.sendRedirect("login");
		}
	}

	private void setCourses(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<CourseBean> listcourse = courseDAO.getCourse();
		request.setAttribute("listcourse", listcourse);
	}

	private void setBatches(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<BatchBean> listBatches=new LinkedList<>();
		if (noOfEntries.equalsIgnoreCase("all")) {
			pageNo=1;
			listBatches = batchDAOI.getBatches();
		}else {
			Integer noOfEntriesI = Integer.valueOf(noOfEntries);
			listBatches =batchDAOI.getBatches((pageNo-1)*noOfEntriesI, noOfEntriesI);
		}
		totalRecords=batchDAOI.totalRecords();
		request.setAttribute("listBatches", listBatches);
	}

	private void addUpdateBatch(HttpServletRequest request, HttpServletResponse response,boolean isAdding)
			throws ServletException, IOException {
		String batchName = request.getParameter("name");
		String courseName = request.getParameter("course");
		String startTimeStr = request.getParameter("startTime");
		String endTimeStr = request.getParameter("endTime");
		String startDateStr = request.getParameter("startDate");
		String endDateStr = request.getParameter("endDate");

		Time startTime = Time.valueOf(LocalTime.parse(startTimeStr));
		Time endTime = Time.valueOf(LocalTime.parse(endTimeStr));
		Date startDate = Date.valueOf(startDateStr);
		Date endDate = Date.valueOf(endDateStr);
		BatchBean batchBean = new BatchBean(batchName, courseName, startTime, endTime, startDate, endDate);
		if (isAdding) {
			request.setAttribute("msg",
					(batchDAOI.addBatch(batchBean)) ? "Batch Record Added Successfully" : "Record Insertion Failed");
		}else {
			request.setAttribute("msg",
					(batchDAOI.updateBatch(batchBean)) ? "Batch Record updated Successfully" : "Record updation Failed");
		}
		
	}
	private void dispatch(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException {
		setBatches(request, response);
		setCourses(request, response);
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("totalRecords", totalRecords);
		request.setAttribute("page", "batch.jsp");
		request.getRequestDispatcher("home.jsp").forward(request, response);
	}
}
