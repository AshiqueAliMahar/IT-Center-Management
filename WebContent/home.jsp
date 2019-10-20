<%@page import="beans.UsersBean"%>
<%@page import="org.apache.tomcat.util.codec.binary.Base64"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>IT-Center</title>
     <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery-3.2.1.min.js"></script>
    <script src="js/popper.min.js" ></script>
    <script src="js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="fontawesome-free-5.5.0-web/css/all.min.css">
    <STYLE>
        a{
            text-decoration: none;
            color: inherit;
        }
        a:hover{
            text-decoration: none;
            color: silver;
        }
    </STYLE>
</head>
<body>
    <div class="container-fluid">


        <nav class="navbar navbar-expand-lg navbar-light bg-light">

            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <a class="navbar-brand" href="home">Home</a>
                    <li class="nav-item">
                        <a class="nav-link" href="coursesPage">Courses<span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="studentPage">Students</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link" href="batchPage">Batches</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="feesPage">Fees</a>
                    </li>
                    
                    <li class="nav-item">
                        <a class="nav-link" href="logout">Logout</a>
                    </li>
                </ul>
                		<a href="profilePage">
	                    	<%
	                    		UsersBean usersBean=(UsersBean)session.getAttribute("loggedin");
	                    		String picStr=Base64.encodeBase64String(usersBean.getPic());
	                    	%>
	                        <img alt="Not Found" src="data:image/gif;base64,<%=picStr%>" class="img-fluid rounded-circle mr-3 float-right" style="width:60px;height:60px;">${sessionScope["loggedin"]["name"] }
                        </a>
            </div>
        </nav>
        <p class="alert alert-success">${msg}</p>
        <%
        	String pageName=request.getAttribute("page")!=null?request.getAttribute("page").toString():"";
        %>
		<jsp:include page="<%=pageName%>"/>
    </div>
</body>
</html>