<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ESI Floor Plan</title>
<link rel="stylesheet" type="text/css" href="resources/css/Reset.css" />
<link rel="stylesheet" type="text/css" href="resources/css/jquery-ui.css"/>

<style>
a {
font-size:1em !important;
}
.wrapper {
margin:0 auto;
margin-top:20px;
width:80%;
padding:20px;
border:1px solid #CCC;
line-height: 1.5em;
}
</style>

<script src="resources/js/jquery.min.js" type="text/javascript"></script>
<script src="resources/js/jquery-ui.js" type="text/javascript"></script>

<script>
$( document ).ready(function() {
	var role = $("#role").html().toLowerCase();
	if (role.indexOf('admin') != -1) {
		$('a#correctPage').text("Admin");
		$('a#correctPage').attr("href","admin")
    	$('#role').html("Admin");
    } else if (role.indexOf('manager') != -1) {
    	$('a#correctPage').text("Manager");
		$('a#correctPage').attr("href","manager")
    	$('#role').html("Manager");
    } else if (role.indexOf('usersDO') != -1) {
    	$('a#correctPage').text("UsersDO");
		$('a#correctPage').attr("href","usersDO")
    	$('#role').html("UsersDO");
    } else {
    	$('#role').html("Stranger");
    };
    $('a').button();
    $('.wrapper').fadeIn('slow');
});
</script>

</head>
<body>
	<div style="width:60%; margin:0px auto; text-align: center;">
		<img src="resources/images/ExpressScripts-logo1.jpg"/>
		<img src="resources/images/floorplan_icon.png"/><br/>
		<div class="wrapper" style="display:none;">
			<h1 style="color:red;font-size:2em;">Oops !!!</h1>
			<hr>
			<h1 style="font-size:1.5em;">You are logging in as <span id="role" style="color:blue;">${roles}</span></h1><br/>
            <p style="margin:0 auto;">If you are here. You may have clicked the wrong button. Please click on the button below to go to the right page<br/>
            <a id="correctPage" href="#"></a>  
            <hr>
            if you are sure that you have clicked the right button, we are sorry about the convenience and please contact your administrator about this problem.</p>
        </div>
        
	</div>
</body>
</html>