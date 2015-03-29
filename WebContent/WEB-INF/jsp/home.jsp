<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
line-height:10px;
margin:10px;
}
h1 {
font-size:1.5em;
}
</style>

<script src="resources/js/jquery.min.js" type="text/javascript"></script>
<script src="resources/js/jquery-ui.js" type="text/javascript"></script>

<script>
$( document ).ready(function() {
	$('a').button();
});
</script>

</head>
<body>
<div style="width:60%; margin:0px auto; text-align: center;">
<img src="resources/images/ExpressScripts-logo1.jpg"/>
<img src="resources/images/floorplan_icon.png"/><br/><br/>
	<h1>Welcome to Express Script Floor Plan Web Application</h1><br/>
	<p>Please click on the button below to start</p>
	<a href="login" target="_self">login</a>
</div>
</body>
</html>
