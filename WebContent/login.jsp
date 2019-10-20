<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery-3.2.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="fontawesome-free-5.5.0-web/css/all.min.css">
</head>
<body class="alert alert-success">
    <div class="container" >
        <div  class="bg-light p-5 m-auto rounded" style="width: 50%">
            <form action="login" method="post" >
                <label>Email</label>
                <input type="email" name="email" class="form-control mt-2">
                <label>Password:</label>
                <input type="password" name="password"  class="form-control mt-2" class="">
                <button name="login" class="btn btn-block btn-dark mt-2 mb-2 fas fa-2x fa-sign-in-alt">LOGIN</button>
            </form>
            <p class="alert alert-success">${msg}</p>
        </div>
    </div>
</body>
</html>