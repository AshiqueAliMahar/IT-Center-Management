<%@page import="java.text.SimpleDateFormat"%>
<%@page import="dao.FeesDAO"%>
<%@page import="beans.BatchBean"%>
<%@page import="dao.BatchDAO"%>
<%@page import="interfacesDAO.BatchDAOI"%>
<%@page import="beans.CourseBean"%>
<%@page import="dao.CourseDAO"%>
<%@page import="interfacesDAO.CourseDAOI"%>
<%@page import="beans.StudentBean,dao.StudentsDAO"%>
<%@page import="beans.FeesBean"%>
<%@page import="java.util.List"%>
<h1 class="h1 text-center bg-info text-white rounded">FEES</h1>
    <button type="button" class="btn btn-success fas fa-plus-circle" data-toggle="modal"
            data-target="#exampleModalCenter">
        Add Fees
    </button>
    <!-- Modal -->
    <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <form action="addFees" method="post">
	                <div class="modal-header">
	                    <h5 class="modal-title" id="exampleModalCenterTitle">Course</h5>
	                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	                        <span aria-hidden="true">&times;</span>
	                    </button>
	                </div>
	                <div class="modal-body">
	                    
	                        Roll No:<input class="form-control" name='rollNo'>
	                        Fees:<input type="number" class="form-control" name='fees'>
	                    
	                </div>
	                <div class="modal-footer">
	                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
	                    <button type="submit" class="btn btn-primary">Save changes</button>
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
		<a class="dropdown-item" href="fees-noOfEntries?noOfEntries=5">5</a> <a
			class="dropdown-item" href="fees-noOfEntries?noOfEntries=10">10</a> <a
			class="dropdown-item" href="fees-noOfEntries?noOfEntries=20">20</a> <a
			class="dropdown-item" href="fees-noOfEntries?noOfEntries=all">ALL</a>
	</div>
</div>

    <table class="table table-responsive-lg table-dark table-hover rounded mt-2 table-striped text-center">
        <tr>
            <th>Roll No:</th>
            <th>Name</th>
            <th>Total Fees</th>
            <th>Paid Fees</th>
            <th>Remaining Fees</th>
            <th>Status</th>
            <th>Detail</th>
        </tr>
        <%
        	List<FeesBean> listSumFees= (List<FeesBean>)request.getAttribute("listSumFees");
        	StudentsDAO studentsDAO=new StudentsDAO();
        	CourseDAOI courseDAOI=new CourseDAO();
        	BatchDAOI batchDAOI=new BatchDAO();
        	
        	CourseBean courseBean=new CourseBean();
        	StudentBean studentBean=new StudentBean();
        	BatchBean batchBean=new BatchBean();
        	
        	for(FeesBean feesBean:listSumFees){
        		
        		studentBean.setRollNo(feesBean.getRollNo());
        		studentBean= studentsDAO.getStudent(studentBean);
        		
        		batchBean.setBatchName(studentBean.getBatchName());
        		batchBean= batchDAOI.getBatch(batchBean);
        		
        		courseBean.setName(batchBean.getCourseName());
        		courseBean= courseDAOI.getCourse(courseBean);
        		
        		String status="Not Paid";
        		if(feesBean.getAmount()!=0 && courseBean.getFees()-feesBean.getAmount()>0){
        			status="Partially Paid";
        		}else{
        			status="Paid";
        		}
        %>
	        <tr>
	        
	            <td><%=feesBean.getRollNo() %></td>
	            <td><%=studentBean.getName() %></td>
	            <td><%=courseBean.getFees() %></td>
	            <td><%=feesBean.getAmount() %></td>
	            <td><%=courseBean.getFees()-feesBean.getAmount() %></td>
	            <td><%= status%></td>
	            <td><a href="#" class='fa fa-arrow-right' data-toggle="modal" data-target="#<%=feesBean.getRollNo() %>"></a></td>
	            
	        </tr>
        <%} %>
        
    </table>
    <%
    	FeesDAO feesDAO=new FeesDAO();
    	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
    	List<StudentBean> lstStudents= (List<StudentBean>)request.getAttribute("lstStudents");
    	for(StudentBean studentsBean:lstStudents){
    		
    %>
    <!-- Modal -->
    <div class="modal fade" id="<%=studentsBean.getRollNo()%>" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal modal-lg modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="">Course</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <label class="font-weight-bold">Name*</label><%=studentsBean.getName()%>
                    <br>
                    <label class="font-weight-bold">Roll*</label><%=studentsBean.getRollNo()%>
                    <table class="table table-light text-center">
                        <tr>
                            <th>Paid Amount</th>
                            <th>Date Time</th>
                            <th>Edit</th>
                            <th>Delete</th>
                        </tr>
                        <%
                        	long total=0;
                        	for(FeesBean feesBean:feesDAO.getFees(studentsBean.getRollNo())){
                        		total+=feesBean.getAmount();
                        %>
                        <tr>
                            <td><%=feesBean.getAmount() %></td>
                            <td><%=simpleDateFormat.format(feesBean.getPayDate())%></td>
                            <td><a href="" class="fas fa-edit"></a></td>
                            <td><a href=" fas fa-trash" class="fas fa-trash"></a></td>
                        </tr>
                        <%} %>
                    </table>
                    <span class="font-weight-bold">Paid Amount:</span><%=total %>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary">Print</button>
                </div>
            </div>
        </div>
    </div>
    <%} %>
    <p class="float-left font-weight-bold">Total ${totalNoOfFees} Fees Records</p>
    <div class="btn-group float-right" role="group"
		aria-label="Basic example">
		<a href="fees-firstPage" class="btn btn-secondary fa-2x fas fa-fast-backward"></a> 
		<a href="fees-previousPage" class="btn btn-secondary fa-2x fas fa-step-backward"></a>
		<button type="button" class="btn btn-secondary font-weight-bold">${pageNo}</button>
		<a href="fees-nextPage" class="btn btn-secondary fa-2x fas fa-step-forward"></a> 
		<a href="fees-lastPage" class="btn btn-secondary fa-2x fas fa-fast-forward"></a>
	</div>