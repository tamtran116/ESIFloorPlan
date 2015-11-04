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
			<form class="form-signin" action="#" th:action="@{/register}" th:object="${userRegisterRequest}" method="post">
				<h2 class="form-signin-heading">Please sign up</h2>
				<div class="form-group">
					<label for="formFirstName" class="sr-only">First Name</label>
					<input type="text" name="firstName" id="formFirstName" placeholder="First Name" th:field="*{firstName}" class="form-control" aria-describedby=""/>
					<span class="glyphicon form-control-feedback" aria-hidden="true"></span>
					<span id="FirstNameSuccess2Status" class="sr-only">(success)</span>
					<span id="FirstNameError2Status" class="sr-only">(error)</span>
				</div>

				<div class="form-group">
					<label for="formLastName" class="sr-only">Last Name</label>
					<input type="text" name="lastName" id="formLastName" placeholder="Last Name" th:field="*{lastName}" class="form-control" aria-describedby=""/>
					<span class="glyphicon form-control-feedback" aria-hidden="true"></span>
					<span id="LastNameSuccess2Status" class="sr-only">(success)</span>
					<span id="LastNameError2Status" class="sr-only">(error)</span>
				</div>

				<div class="form-group">
					<label for="formPhoneNumber" class="sr-only">Phone Number</label>
					<input type="tel" name="phoneNumber" id="formPhoneNumber" placeholder="(555) 555-1212" th:field="*{phoneNumber}" class="form-control" aria-describedby=""/>
					<span class="glyphicon form-control-feedback" aria-hidden="true"></span>
					<span id="PhoneNumberSuccess2Status" class="sr-only">(success)</span>
					<span id="PhoneNumberError2Status" class="sr-only">(error)</span>
				</div>

				<div class="form-group">
					<label for="formEmail" class="sr-only">Email Address</label>
					<input type="email" name="Email" id="formEmail" placeholder="email" class="form-control" aria-describedby=""/>
					<span class="glyphicon form-control-feedback" aria-hidden="true"></span>
					<span id="EmailSuccess2Status" class="sr-only">(success)</span>
					<span id="EmailError2Status" class="sr-only">(error)</span>
				</div>

				<div class="form-group">
					<label for="formConfirmedEmail" class="sr-only">Email Address Confirm</label>
					<input type="text" name="ConfirmEmail" id="formConfirmedEmail" placeholder="email" th:field="*{email}" class="form-control" aria-describedby=""/>
					<span class="glyphicon form-control-feedback" aria-hidden="true"></span>
					<span id="ConfirmedEmailSuccess2Status" class="sr-only">(success)</span>
					<span id="ConfirmedEmailError2Status" class="sr-only">(error)</span>
				</div>

				<!-- TODO: real time validation (promises) / check if usersDO name is taken-->
				<div class="form-group">
					<label for="formUserName" class="sr-only">User Name</label>
					<input type="text" name="userName" id="formUserName"  placeholder="Username" th:field="*{userName}" class="form-control" aria-describedby=""/>
					<span class="glyphicon form-control-feedback" aria-hidden="true"></span>
					<span id="UserNameSuccess2Status" class="sr-only">(success)</span>
					<span id="UserNameError2Status" class="sr-only">(error)</span>
				</div>
				<!-- TODO: provide strong password-->
				<div class="form-group">
					<label for="formPassword" class="sr-only">Password</label>
					<input type="password" name="password" id="formPassword" placeholder="Password" th:field="*{password}" class="form-control" aria-describedby="">
					<span class="glyphicon form-control-feedback" aria-hidden="true"></span>
					<span id="PasswordSuccess2Status" class="sr-only">(success)</span>
					<span id="PasswordError2Status" class="sr-only">(error)</span>
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
		<!-- Form validation script -->
		<script src="resources/js/validation.js"></script>
		<!-- Mask Jquery plugin-->
		<script src="resources/js/jquery.mask.js"></script>
    </body>
</html>
