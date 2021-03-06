<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
	<meta http-equiv="Cache-control" content="NO-CACHE">
	<meta name="description" content="register page">
	<meta name="author" content="Tam Tran">
	<title>Register Template for Bootstrap</title>

	<!-- Bootstrap core CSS -->
	<link href="resources/css/bootstrap.min.css" rel="stylesheet">

	<!-- Custom styles for this template -->
	<link href="resources/css/registration.css" rel="stylesheet">

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
				<li class="active"><a href="register">register</a></li>
				<li><a href="uploadfloor">uploadfloor</a></li>
				<li><a href="receipt">Save My Receipt</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="logout">logout</a></li>
			</ul>
		</div><!--/.nav-collapse -->
	</div>
</nav>
<body>
<div class="container">
	<form class="form-signin" action="#" th:action="@{/register}" th:object="${userRegisterRequest}" method="post">
		<h2 class="form-signin-heading">Please sign up</h2>
		<div class="form-group">
			<label for="first-name-inp" class="sr-only">First Name</label>
			<input type="text" name="firstName" id="first-name-inp" placeholder="First Name" th:field="*{firstName}" class="form-control" aria-describedby=""/>
		</div>

		<div class="form-group">
			<label for="last-name-inp" class="sr-only">Last Name</label>
			<input type="text" name="lastName" id="last-name-inp" placeholder="Last Name" th:field="*{lastName}" class="form-control" aria-describedby=""/>
		</div>

		<div class="form-group">
			<label for="phone-number-inp" class="sr-only">Phone Number</label>
			<input type="tel" name="phoneNumber" id="phone-number-inp" placeholder="(555) 555-1212" th:field="*{phoneNumber}" class="form-control" aria-describedby=""/>
			<span class="glyphicon form-control-feedback" aria-hidden="true"></span>
		</div>

		<div class="form-group">
			<label for="email-inp" class="sr-only">Email Address</label>
			<input type="email" name="Email" id="email-inp" placeholder="email" class="form-control" aria-describedby=""/>
		</div>

		<div class="form-group">
			<label for="email-confirm-inp" class="sr-only">Email Address Confirm</label>
			<input type="text" name="ConfirmEmail" id="email-confirm-inp" placeholder="email" th:field="*{email}" class="form-control" aria-describedby=""/>
		</div>

		<!-- TODO: real time validation (promises) / check if usersDO name is taken-->
		<div class="form-group">
			<label for="user-name-inp" class="sr-only">User Name</label>
			<input type="text" name="userName" id="user-name-inp" placeholder="Username" th:field="*{userName}" class="form-control" aria-describedby=""/>
		</div>
		<div class="form-group">
			<label for="password-inp" class="sr-only">Password</label>
			<input type="password" name="password" id="password-inp" placeholder="Password" th:field="*{password}" class="form-control" aria-describedby="">
		</div>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="checkbox">
			<label>
				<input type="checkbox" value="remember-me"> Remember me
			</label>
		</div>
		<input class="btn btn-lg btn-primary btn-block" name="submit" type="submit" value="register" />
	</form>

</div> <!-- /container -->
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
