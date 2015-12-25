<%@ taglib uri="http://www.springframework.org/tags"  prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<c:url var="rootURL" value="/"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta content="IE=edge" http-equiv="X-UA-Compatible">
	<meta content="width=device-width, initial-scale=1" name="viewport">
	<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
	<meta content="NO-CACHE" http-equiv="Cache-control">
	<meta content="All of my receipts in one place" name="description">
	<meta content="Tam Tran" name="author">
	<title>My Receipts</title>
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<!-- Optional theme -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">

	<!-- Jquery first-->
	<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
	<script src="https://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">My Receipts</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li><a href="register">register</a></li>
				<li><a href="uploadfloor">uploadfloor</a></li>
				<li><a href="receipt">Save My Receipt</a></li>
			</ul>
			<c:choose>
				<c:when test="${not empty username}">
					<ul class="nav navbar-nav navbar-right">
						<li><a href="#"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> ${username}</a></li>
						<li><a href="logout">logout</a></li>
					</ul>
				</c:when>
				<c:otherwise>
					<form class="form-inline navbar-form" action="${rootURL}home" method="POST">
						<div class="form-group">
							<label class="sr-only" for="username">User Name</label>
							<input name="username" type="text" class="form-control" id="username" placeholder="user name">
						</div>
						<div class="form-group">
							<label class="sr-only" for="password">Password</label>
							<input name="password" type="password" class="form-control" id="password" placeholder="Password">
						</div>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="checkbox">
							<label>
								<input type="checkbox"> Remember me
							</label>
						</div>
						<input class="btn btn-primary " name="submit" type="submit" value="Sign in">
					</form>
				</c:otherwise>
			</c:choose>
		</div><!--/.nav-collapse -->
	</div>
</nav>
<div class="container">
	<div class="">
		<img src="resources/images/pile_of_receipts.jpg" class="img-responsive center-block"/>
	</div>
	<c:if test="${not empty error}">
		<div class="alert alert-danger" role="alert"><strong>${error}</strong></div>
	</c:if>
	<c:if test="${not empty msg}">
		<div class="alert alert-danger" role="alert"><strong>${msg}</strong></div>
	</c:if>
</div>
</body>
</html>
