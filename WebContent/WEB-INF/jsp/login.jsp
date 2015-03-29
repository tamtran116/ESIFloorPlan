<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags"  prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<c:url var="rootURL" value="/"/>
<html>
<head>
<meta http-equiv="Cache-control" content="NO-CACHE">
<style>
.wrapper {
	background: url('resources/images/floor03_blur.jpg') no-repeat;
	width:100%;
	height:100%;
	padding-top:100px;
}
.blur{
	width:500px;
	margin:10px auto;
    opacity: 0.5;
    border:1px solid #CCC;
    text-align:center;
    background: #999;
    vertical-align:middle;
}
.round-corner {
-moz-border-radius: 10px;
-webkit-border-radius: 10px;
border-radius: 10px; /* future proofing */
-khtml-border-radius: 10px; /* for old Konqueror browsers */
}
</style>
<script>
//Script to prevent accidentally hit back after login
  function preventBack(){window.history.forward();}
  setTimeout("preventBack()", 0);
  window.onunload=function(){null};
</script>
</head>
    <body style="margin:0px;padding:0px;">
    <div class="wrapper">
	    <div class="blur round-corner">
	        <img src="resources/images/es.png"/>
	        <br>
		<img src="resources/images/floorplan_icon.png"/>
		<br>
		<br>
	        <form name="f" action="${rootURL}login" method="POST">
	            <table style="margin: 0px auto;">
	                <tr>
	                    <td>Username:</td>
	                    <td><input type='text' name='username' /></td>
	                </tr>
	                <tr>
	                    <td>Password:</td>
	                    <td>
							<input type='password' name='password'>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						</td>

	                </tr>
	                <tr>
	                    <td colspan="2">&nbsp;</td>
	                </tr>
	                <tr>
	                    <td colspan='2'><input name="submit" type="submit" value="login">&nbsp;<input name="reset" type="reset"></td>
	                </tr>
	            </table>
	        </form>
	    </div>
	</div><!-- /wrapper -->
    </body>
</html>
