<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
 
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
	width:400px;
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
</head>
    <body style="margin:0px;padding:0px;">
    <div class="wrapper">
	    <div class="blur round-corner">
	        <img src="resources/images/es.png"/>
	        <br>
	        <form name="f" action="<c:url value='j_spring_security_check'/>" method="POST">
	            <table style="margin: 0px auto;">
	                <tr>
	                    <td>Username:</td>
	                    <td><input type='text' name='j_username' /></td>
	                </tr>
	                <tr>
	                    <td>Password:</td>
	                    <td><input type='password' name='j_password'></td>
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