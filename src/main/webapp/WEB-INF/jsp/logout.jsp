<% session.invalidate(); %>
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
function delayedRedirect(){
    window.location = "../../"
}
</script>

</head>
<body onLoad="setTimeout('delayedRedirect()', 3000)">
<div style="width:60%; margin:0px auto; text-align: center;">
<img src="resources/images/ExpressScripts-logo1.jpg"/>
<img src="resources/images/floorplan_icon.png"/><br/><br/>
<h1>Logging out</h1>
<h2>You'll be redirected soon!</h2> <p>Or click the button to go back now</p>
<a href="${pageContext.request.contextPath}/">Back</a>
</div>
</body>
</html> 


 
 