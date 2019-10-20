<%@page import="java.text.SimpleDateFormat"%>
<%@page import="controller.CoursesServlet"%>
<%@page import="beans.CourseBean"%>
<%@page import="java.util.List"%>
<h1 class="h1 text-center">COURSES</h1>

<button type="button" class="btn btn-success fas fa-plus-circle"
	data-toggle="modal" data-target="#exampleModalCenter">Add
	Course</button>
<!-- Modal -->
<div class="modal fade" id="exampleModalCenter" tabindex="-1"
	role="dialog" aria-labelledby="exampleModalCenterTitle"
	aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<form action="addCourse" method="post">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalCenterTitle">Course</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">

					Name:<input class="form-control" name='name'> 
					Fee:<input
						type="number" class="form-control" name='fees'>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="submit" class="btn btn-primary">Save</button>
				</div>
			</form>
		</div>
	</div>
</div>


<div class="dropdown float-right">
<button class="btn btn-secondary btn-sm dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    Show No Of Entries
  </button>
  <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
    <a class="dropdown-item" href="course-noOfEntries?id=5">5</a>
    <a class="dropdown-item" href="course-noOfEntries?id=10">10</a>
    <a class="dropdown-item" href="course-noOfEntries?id=20">20</a>
    <a class="dropdown-item" href="course-noOfEntries?id=all">ALL</a>
  </div>
</div>
<table
	class="table table-dark table-hover rounded mt-2 table-striped text-center">
	<tr>
		<th>Course Name</th>
		<th>Fees</th>
		<th>Course Start Date</th>
		<th>Edit</th>
		<th>Delete</th>
	</tr>
	<%
		List<CourseBean> list = (List<CourseBean>) request.getAttribute("list");
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMMM-yyyy hh:mm:a");
		for (CourseBean courseBean : list) {
			String startDate=simpleDateFormat.format(courseBean.getStartDate());
	%>
	<tr>
		<td><%=courseBean.getName()%></td>
		<td><%=courseBean.getFees()%></td>
		<th><%=startDate%></th>
		<td><a href="#" data-toggle="modal" data-target="#<%=courseBean.getName()%>" class="fas fa-edit fa-2x"></a>
		<div class="modal fade" id="<%=courseBean.getName()%>" tabindex="-1"
				role="dialog" aria-labelledby="exampleModalCenterTitle"
				aria-hidden="true">
				<div class="modal-dialog modal-dialog-centered" role="document">
					<div class="modal-content">
						<form action="updateCourse" method="post">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalCenterTitle">Update Course</h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">

								Name:<input readonly="readonly" class="form-control" name='name' value="<%=courseBean.getName()%>"> 
								Fee:<input type="number" class="form-control" name='fees' value="<%=courseBean.getFees()%>">

							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary"
									data-dismiss="modal">Close</button>
								<button type="submit" class="btn btn-primary">Update</button>
							</div>
						</form>
					</div>
				</div>
			</div>		
	</td>
		<td><a href="deleteCourse?name=<%=courseBean.getName()%>" class="fas fa-trash fa-2x"></a></td>
	</tr>
	<%
		}
	%>
</table>
<p class="float-left font-weight-bold">Total :${totalRecords} Courses</p>
<div class="btn-group float-right" role="group"
	aria-label="Basic example">
	<a href="course-firstPage" class="btn btn-secondary">First</a>
	<a href="course-previousPage" class="btn btn-secondary">Previous</a>
	<button type="button" class="btn btn-secondary">${ pageNo }</button>
	<a href="course-nextPage" class="btn btn-secondary">Next</a>
	<a href="course-lastPage" class="btn btn-secondary">Last</a>
</div>
