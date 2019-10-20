<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="beans.BatchBean"%>
<%@page import="beans.CourseBean"%>
<%@page import="java.util.List"%>
	<h1 class="h1 text-center">BATCHES</h1>
    <button type="button" class="btn btn-success fas fa-plus-circle" data-toggle="modal"
            data-target="#exampleModalCenter">
        Add Batch
    </button>
    <!-- Modal -->
    <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                 <form action="addBatch" method="post">
	                <div class="modal-header">
	                    <h5 class="modal-title" id="exampleModalCenterTitle">Batch</h5>
	                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	                        <span aria-hidden="true">&times;</span>
	                    </button>
	                </div>
	                <div class="modal-body">
	                   
	                        Batch Name*<input class="form-control" name='name'>
	                        Course*
	                        <select class="form-control" name='course'>
	                        	<%
	                        		List<CourseBean> lisCourseBeans=(List<CourseBean>)request.getAttribute("listcourse");
	                        		for(CourseBean courseBean:lisCourseBeans){
	                        			out.print("<option value='"+courseBean.getName()+"'>"+courseBean.getName()+"</option>");
	                        		}
	                        	%>
	                        	
	                        </select>
	                        Class Time*<input type="time" class="form-control" name="startTime">
	                        End Time*<input type="time" class="form-control" name="endTime">
	                        Starting Date*<input type="date" class="form-control" name='startDate'>
	                        Ending Date*<input type="date" class="form-control" name="endDate">
	                    
	                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Save Batch</button>
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
		<a class="dropdown-item" href="batch-noOfEntries?noOfEntries=5">5</a> <a
			class="dropdown-item" href="batch-noOfEntries?noOfEntries=10">10</a> <a
			class="dropdown-item" href="batch-noOfEntries?noOfEntries=20">20</a> <a
			class="dropdown-item" href="batch-noOfEntries?noOfEntries=all">ALL</a>
	</div>
</div>
    <table class="table table-responsive-lg table-dark table-hover rounded mt-2 table-striped text-center">
        <tr>
            <th>Batch Name</th>
            <th>Course Name</th>
            <th>Class Start Time</th>
            <th>Class End Time</th>
            <th>Batch Start Date</th>
            <th>Batch End Date</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        <%
        	List<BatchBean> listBatches=(List<BatchBean>) request.getAttribute("listBatches");
        	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("hh:mm:a");
        	for(BatchBean batchBean:listBatches){
        		String startTime=simpleDateFormat.format(batchBean.getStartTime());
        		String endTime=simpleDateFormat.format(batchBean.getEndTime());
        %>
        <tr>
            <td><%=batchBean.getBatchName() %></td>
            <td><%=batchBean.getCourseName() %></td>
            <td><%=startTime%></td>
            <td><%=endTime%></td>
            <td><%=batchBean.getStartDate()%></td>
            <td><%=batchBean.getEndDate()%></td>
            <td><a href="#" class="fas fa-edit" data-toggle="modal" data-target='#<%=batchBean.getBatchName()%>'></a></td>
            <td><a href="deleteBatch?batchName=<%=batchBean.getBatchName() %>" class="fas fa-trash"></a></td>
        </tr>
        <!-- Modal -->
	    <div class="modal fade" id='<%=batchBean.getBatchName()%>' tabindex="-1" role="dialog"
	         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	        <div class="modal-dialog modal-dialog-centered" role="document">
	            <div class="modal-content">
	                 <form action="updateBatch" method="post">
		                <div class="modal-header">
		                    <h5 class="modal-title" id="exampleModalCenterTitle">Batch</h5>
		                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		                        <span aria-hidden="true">&times;</span>
		                    </button>
		                </div>
		                <div class="modal-body">
		                   
		                        Batch Name*<input class="form-control" name='name' value='<%=batchBean.getBatchName()%>' readonly="readonly">
		                        Course*
		                        <select class="form-control" name='course'>
		                        	<%
		                        		for(CourseBean courseBean:lisCourseBeans){
		                        			if(batchBean.getCourseName().equals(courseBean.getName())){
		                        				out.print("<option selected='selected' value='"+courseBean.getName()+"'>"+courseBean.getName()+"</option>");	
		                        			}else{
		                        				out.print("<option value='"+courseBean.getName()+"'>"+courseBean.getName()+"</option>");
		                        			}
		                        			
		                        		}
		                        	%>
		                        	
		                        </select>
		                        Class Time*<input type="time" class="form-control" name="startTime" value='<%=batchBean.getStartTime()%>'>
		                        End Time*<input type="time" class="form-control" name="endTime" value='<%=batchBean.getEndTime()%>'>
		                        Starting Date*<input type="date" class="form-control" name='startDate' value='<%=batchBean.getStartDate()%>'>
		                        Ending Date*<input type="date" class="form-control" name="endDate" value='<%=batchBean.getEndDate()%>'>
		                </div>
	                <div class="modal-footer">
	                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
	                    <button type="submit" class="btn btn-primary">Update Batch</button>
	                </div>
	                </form>
	            </div>
	        </div>
	    </div>
        <%} %>
    </table>
    <p class="float-left font-weight-bold">Total* ${totalRecords} Batches</p>
    <div class="btn-group float-right" role="group"
		aria-label="Basic example">
		<a href="batch-firstPage" class="btn btn-secondary fa-2x fas fa-fast-backward"></a> <a
			href="batch-previousPage" class="btn btn-secondary fa-2x fas fa-step-backward"></a>
		<button type="button" class="btn btn-secondary font-weight-bold">${pageNo}</button>
		<a href="batch-nextPage" class="btn btn-secondary fa-2x fas fa-step-forward"></a> <a
			href="batch-lastPage" class="btn btn-secondary fa-2x fas fa-fast-forward"></a>
	</div>
    