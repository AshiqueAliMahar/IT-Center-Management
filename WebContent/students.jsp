<%@page import="java.text.SimpleDateFormat"%>
<%@page import="dao.FeesDAO"%>
<%@page import="dao.CourseDAO"%>
<%@page import="dao.BatchDAO"%>
<%@page import="dao.StudentsDAO"%>
<%@page import="beans.StudentBean"%>
<%@page import="beans.CourseBean"%>
<%@page import="java.util.List"%>
<%@page import="beans.BatchBean"%>
<h1 class="h1 text-center">STUDENTS</h1>
<button type="button" class="btn btn-success fas fa-plus-circle"
	data-toggle="modal" data-target="#exampleModalCenter">Add
	Student</button>

<!-- Modal -->
<div class="modal fade" id="exampleModalCenter" tabindex="-1"
	role="dialog" aria-labelledby="exampleModalCenterTitle"
	aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalCenterTitle">Modal
					title</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<form action="addStudent" method="post" enctype="multipart/form-data">
				<div class="modal-body">

					Name:<input class="form-control" name='name'> Roll No:<input
						class="form-control" name='rollNo'> Date Of Birth:(YYYY-MM-DD)<input
						class="form-control" type="date" name='dob'> Batch Name: <select
						class="form-control" name='batchName'>
						<%
							for (BatchBean bean : (List<BatchBean>) request.getAttribute("batches")) {
								out.print("<option value='" + bean.getBatchName() + "'>" + bean.getBatchName() + "</option>");
							}
						%>

					</select> Fee:<input type="number" class="form-control" name='fee'>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="submit" class="btn btn-primary">Add Student</button>
				</div>
			</form>
		</div>
	</div>
</div>
<div class="dropdown float-right">
	<button class="btn btn-secondary btn-sm dropdown-toggle" type="button"
		id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true"
		aria-expanded="false">Show No Of Entries</button>
	<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
		<a class="dropdown-item" href="student-noOfEntries?noOfRecords=5">5</a> <a
			class="dropdown-item" href="student-noOfEntries?noOfRecords=10">10</a> <a
			class="dropdown-item" href="student-noOfEntries?noOfRecords=20">20</a> <a
			class="dropdown-item" href="student-noOfEntries?noOfRecords=all">ALL</a>
	</div>
</div>

<table class="table table-responsive-lg table-dark text-center">
	<tr>
		<th>Name</th>
		<th>Roll No</th>
		<th>Date of birth</th>
		<th>Admission Date</th>
		<th>Batch Name</th>
		<th>Course Name</th>
		<th>Course Fee</th>
		<th>Paid Fees</th>
		<th>Fee Status</th>
		<th>Edit</th>
		<th>Delete</th>
	</tr>
	<%
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy hh:mm:a");
		for (StudentBean studentBean : (List<StudentBean>) request.getAttribute("listStudent")) {
			String admsnDate=simpleDateFormat.format( studentBean.getAdmsnDate());
	%>
	<tr>
		<td><%=studentBean.getName()%></td>
		<td><%=studentBean.getRollNo()%></td>
		<td><%=studentBean.getDob()%></td>
		<td><%=admsnDate%></td>
		<td><%=studentBean.getBatchName()%></td>
		<%
			BatchDAO batchDAO = new BatchDAO();
				BatchBean batchBean = new BatchBean();
				batchBean.setBatchName(studentBean.getBatchName());
				batchBean = batchDAO.getBatch(batchBean);
				CourseDAO courseDAO = new CourseDAO();
				CourseBean courseBean = new CourseBean();
				courseBean.setName(batchBean.getCourseName());
				courseBean = courseDAO.getCourse(courseBean);
				long totalPaidFees = FeesDAO.getTotalFees(studentBean.getRollNo());
				String feesStatus = "Not Paid";
				if (totalPaidFees != 0) {
					feesStatus = courseBean.getFees() > totalPaidFees ? "Partially Paid" : "Paid";
				}
		%>
		<td><%=courseBean.getName()%></td>
		<td><%=courseBean.getFees()%></td>
		<td><%=totalPaidFees%></td>
		<td><%=feesStatus%></td>
		<td>
			<a href="#" data-toggle="modal" data-target="#<%=studentBean.getRollNo()%>" class="fas fa-edit fa-2x"></a>
		</td>
		<!-- Modal -->
			<div class="modal fade" id="<%=studentBean.getRollNo()%>" tabindex="-1"
				role="dialog" aria-labelledby="exampleModalCenterTitle"
				aria-hidden="true">
				<div class="modal-dialog modal-dialog-centered" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalCenterTitle">Update Student</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<form action="updateStudent" method="post">
							<div class="modal-body">
			
								Name:<input class="form-control" name='name' value='<%=studentBean.getName()%>'> 
								Roll No:<input class="form-control" name='rollNo' readonly="readonly" value='<%=studentBean.getRollNo()%>'> 
								Date Of Birth:(YYYY-MM-DD)<input class="form-control" type="date" name='dob' value='<%=studentBean.getDob()%>'> 
								Batch Name: <select class="form-control" name='batchName'>
									<%
										for (BatchBean bean : (List<BatchBean>) request.getAttribute("batches")) {
											if(studentBean.getBatchName().equals(bean.getBatchName())){
												out.print("<option selected='selected' value='" + bean.getBatchName() + "'>" + bean.getBatchName() + "</option>");
											}else{
												out.print("<option value='" + bean.getBatchName() + "'>" + bean.getBatchName() + "</option>");
											}
										}
									%>
								</select> <%-- Fee:<input type="number" class="form-control" name='fee' value='<%=%>'> --%>
			
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary"
									data-dismiss="modal">Close</button>
								<button type="submit" class="btn btn-primary">Update Student</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		
		<td><a href="delStudent?rollNo=<%=studentBean.getRollNo()%>"
			class="fas fa-trash fa-2x"></a></td>
	</tr>
	<%
		}
	%>
</table>
<p class="float-left font-weight-bold">Total :${totalStudents}
	Students</p>
<div class="btn-group float-right" role="group"
	aria-label="Basic example">
	<a href="student-firstPage" class="btn btn-secondary">First</a> <a
		href="student-previousPage" class="btn btn-secondary">Previous</a>
	<button type="button" class="btn btn-secondary">${pageNo}</button>
	<a href="student-nextPage" class="btn btn-secondary">Next</a> <a
		href="student-lastPage" class="btn btn-secondary">Last</a>
</div>
