<%@ taglib uri="http://www.springframework.org/tags"  prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<c:url var="rootURL" value="/"/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
	<meta http-equiv="Cache-control" content="NO-CACHE">
	<meta name="description" content="login page for ESI">
	<meta name="author" content="Tam Tran">
	<title>Signin Template for Bootstrap</title>

	<!-- Bootstrap core CSS -->
	<link href="resources/css/bootstrap.min.css" rel="stylesheet">

	<!-- Custom styles for this template -->
	<link href="resources/css/signin.css" rel="stylesheet">

	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	<![endif]-->
	<script>
		//Script to prevent accidentally hit back after login
		  function preventBack(){window.history.forward();}
		  setTimeout("preventBack()", 0);
		  window.onunload=function(){null};
	</script>
</head>
    <body>

		<div class="container">
			<img class="img-responsive center-block" src="resources/images/floorplan_icon.png"/>
			<form class="form-signin" name="f" action="${rootURL}login" method="POST">
				<h2 class="form-signin-heading">Please sign in</h2>
				<label for="inputUsername" class="sr-only">Email address</label>
				<input type="text" name='username' id="inputUsername" class="form-control" placeholder="User Name" required autofocus>
				<label for="inputPassword" class="sr-only">Password</label>
				<input type="password" name='password' id="inputPassword" class="form-control" placeholder="Password" required>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<div class="checkbox">
					<label>
						<input type="checkbox" value="remember-me"> Remember me
					</label>
				</div>
				<input class="btn btn-lg btn-primary btn-block" name="submit" type="submit" value="Sign in">
			</form>
			<c:if test="${not empty error}">
				<div class="alert alert-danger" role="alert"><strong>${error}</strong></div>
			</c:if>
			<c:if test="${not empty msg}">
				<div class="alert alert-danger" role="alert"><strong>${msg}</strong></div>
			</c:if>
		</div> <!-- /container -->
    </body>
</html>
