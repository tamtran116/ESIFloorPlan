<%@ taglib uri="http://www.springframework.org/tags"  prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
	<!-- Custom styles for this template -->
	<link href="resources/css/registration.css" rel="stylesheet">
	<style>

	</style>


	<!-- Jquery first-->
	<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
	<script src="https://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<body>
<c:choose>
<c:when test="${not empty userName}">
<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">Tam116</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li><a href="uploadfloor">Upload Floor App</a></li>
				<li><a href="receipt">My Receipt App</a></li>
			</ul>

			<ul class="nav navbar-nav navbar-right">
				<li>
					<a href="#/userProfile" data-toggle="collapse" data-target="#navbar">
						<c:if test="${role == 'premium'}">
							<span class="glyphicon glyphicon-king" aria-hidden="true"></span> ${userName}
						</c:if>
						<c:if test="${role == 'regular'}">
							<span class="glyphicon glyphicon-user" aria-hidden="true"></span> ${userName}
						</c:if>
					</a>
				</li>
				<li><a href="logout"><span class="glyphicon glyphicon-log-out" aria-hidden="true"></span> logout</a></li>
			</ul>
		</div><!--/.nav-collapse -->
	</div>
</nav>
</c:when>
</c:choose>
<div class="container" style="padding-top:100px;">
<c:choose>
	<c:when test="${empty userName}">
	<div class="sign-in">
		<form action="${rootURL}home" method="POST">
			<h3>Please sign in</h3>
			<div class="form-group">
				<label class="sr-only" for="username">User Name</label>
				<input name="username" type="text" class="form-control" id="username" placeholder="user name" autofocus>
			</div>
			<div class="form-group">
				<label class="sr-only" for="password">Password</label>
				<input name="password" type="password" class="form-control" id="password" placeholder="Password">
			</div>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<c:if test="${not empty error}">
				<div class="alert alert-danger" role="alert"><strong>${error}</strong></div>
			</c:if>
			<c:if test="${not empty msg}">
				<div class="alert alert-danger" role="alert"><strong>${msg}</strong></div>
			</c:if>
			<div class="checkbox">
				<label>
					<input type="checkbox"> Remember me
				</label>
			</div>
			<input class="btn btn-primary btn-block" name="submit" type="submit" value="Sign in"/>
			<button type="button" class="btn btn-default btn-block" data-toggle="modal" data-target="#registerModal">Register</button>
		</form>
	</div>
	</c:when>
	<c:otherwise>
		<div class="btn-group btn-group-justified" role="group" aria-label="group nav">
			<a role="button" class="btn btn-default" href="receipt#/add">Add Receipt</a>
			<a role="button" class="btn btn-default" href="receipt#/receipts">List Receipt</a>
			<a role="button" class="btn btn-default" href="receipt#">Modify Receipt</a>
		</div>
	</c:otherwise>
</c:choose>
</div>
<c:choose>
	<c:when test="${empty userName}">
<!-- Modal -->
<div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="myModalLabel">Register for My Receipt</h4>
			</div>
			<div class="modal-body">
				<form id="register-form" action="#" th:action="@{/}" th:object="${userRegisterRequest}" method="post">
					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label for="first-name-inp" class="sr-only">First Name</label>
								<input type="text" name="firstName" id="first-name-inp" placeholder="First Name" th:field="*{firstName}" class="form-control" aria-describedby=""/>
							</div>
							<div class="form-group">
								<label for="phone-number-inp" class="sr-only">Phone Number</label>
								<input type="tel" name="phoneNumber" id="phone-number-inp" placeholder="(555) 555-1212" th:field="*{phoneNumber}" class="form-control" aria-describedby=""/>
								<span class="glyphicon form-control-feedback" aria-hidden="true"></span>
							</div>
							<div class="form-group">
								<label for="username-inp" class="sr-only">User Name</label>
								<input type="text" name="userName" id="username-inp" placeholder="Username" th:field="*{userName}" class="form-control" aria-describedby=""/>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label for="last-name-inp" class="sr-only">Last Name</label>
								<input type="text" name="lastName" id="last-name-inp" placeholder="Last Name" th:field="*{lastName}" class="form-control" aria-describedby=""/>
							</div>
							<div class="form-group">
								<label for="email-inp" class="sr-only">Email Address</label>
								<input type="email" name="Email" id="email-inp" placeholder="email" class="form-control" aria-describedby=""/>
							</div>
							<div class="form-group">
								<label for="password-inp" class="sr-only">Password</label>
								<input type="password" name="password" id="password-inp" placeholder="Password" th:field="*{password}" class="form-control" aria-describedby="">
							</div>
						</div>
						<div class="col-md-12">
							<div class="g-recaptcha" data-sitekey="6Ld-wxwTAAAAAHEBtfnq7brzB0QqPfMnUyiRXJbJ"></div>
						</div>
					</div>
					<!--<div class="form-group">
						<label for="email-confirm-inp" class="sr-only">Email Address Confirm</label>
						<input type="text" name="ConfirmEmail" id="email-confirm-inp" placeholder="email" th:field="*{email}" class="form-control" aria-describedby=""/>
					</div>-->
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				</form>
				<div id="form-msg">
					<c:if test="${not empty successMsg}">
						<div class="alert alert-success" role="alert">${successMsg}</div>
					</c:if>
					<c:if test="${not empty errorMsg}">
						<div class="alert alert-danger" role="alert">${errorMsg}</div>
					</c:if>
				</div>
			</div>
			<div class="modal-footer">
				<button id="register-btn" class="btn btn-lg btn-primary btn-block" type="button" disabled>Register</button>
				<button id="reset-btn" class="btn btn-lg btn-default btn-block" type="button">Reset</button>
			</div>
		</div>
	</div>
</div>
	</c:when>
</c:choose>
<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script src="resources/js/jquery-2.1.4.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/tooltip.js"></script>
<!-- Form validation script -->
<script src="resources/js/validation.js"></script>
<!-- Mask Jquery plugin-->
<script src="resources/js/jquery.mask.js"></script>
</body>
</html>
