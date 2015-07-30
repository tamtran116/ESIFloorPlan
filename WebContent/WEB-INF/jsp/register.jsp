<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
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
    <body>

		<div class="container">
			<form class="form-signin" action="#" th:action="@{/register}" th:object="${userInfo}" method="post">
				<h2 class="form-signin-heading">Please sign up</h2>
				<div class="form-group">
					<input type="text" placeholder="First Name" th:field="*{firstName}" class="form-control"/>
				</div>

				<div class="form-group">
					<input type="text" placeholder="Last Name" th:field="*{lastName}" class="form-control"/>
				</div>

				<div class="form-group">
					<input type="text" placeholder="Phone Number" th:field="*{phoneNumber}" class="form-control"/>
				</div>

				<div class="form-group">
					<input type="text" placeholder="email" th:field="*{email}" class="form-control"/>
				</div>

				<div class="form-group">
					<input type="text" placeholder="Username" th:field="*{user.username}" class="form-control"/>
				</div>

				<div class="form-group">
					<input type="password" placeholder="Password" th:field="*{user.password}" class="form-control"/>
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
    </body>
</html>
