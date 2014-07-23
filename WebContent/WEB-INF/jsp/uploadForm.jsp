<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>Upload Floor</title>
<link rel="stylesheet" type="text/css" href="resources/css/jquery.dataTables.css">
<script src="resources/js/jquery.min.js" type="text/javascript"></script>
<script src="resources/js/jquery.dataTables.min.js" type="text/javascript" language="javascript"></script>
<script>
$( document ).ready(function() {
	
	//init data table
	$('#data-table').dataTable();
	
	//ajax load floor database
	$("#floor-database").hide();
	$("#show-floor").click(function(){
		$("#floor-database").fadeIn("slow");;
	});
	
});
</script>

</head>
<body>

  
<center>
	<img src="resources/images/ExpressScripts-logo1.jpg"/>
  	<h3>Please select a "floor" to upload !</h3>
	<form:form method="post" enctype="multipart/form-data" modelAttribute="uploadedFile" action="fileUpload.htm">
	 <table>
	 	<tr>
	 		<td>Floor Name</td>
	 		<td><form:input path="floorName"/><td>
	 		<td></td>
	 	</tr>
	 	<tr>
	 		<td>Location</td>
	 		<td><form:input path="floorLocation"/><td>
	 		<td></td>   	
	 	</tr>
	 	<tr>
	 		<td>Uploaded by</td>
	 		<td><form:input path="uploadedBy"/><td>
	 		<td></td>
	 	</tr>
	 	<tr>
	 		<td>Floor Description</td>
	 		<td><form:input path="floorDesc"/><td>
	 		<td></td>   	
	 	</tr>
	  <tr>
	   <td>Upload File: </td>
	   <td><input type="file" name="file" /></td>
	   <td style="color: red; font-style: italic;"><form:errors path="file" /></td>
	  </tr>
	  <tr>
	   <td></td>
	   <td><input type="submit" value="Upload" /></td>
	   <td></td>
	  </tr>
	 </table>
	</form:form>
	<hr>
	<button id="show-floor">Show Database</button>
	<div id="floor-database">
		<table id="data-table">
			<thead>
				<tr>
				    <th>Floor Name</th>
				    <th>File Path</th>
				    <th>Floor Location</th>
				    <th>Uploaded By</th>
				    <th>Floor Description</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${floorList}" var="floor">
				    <tr>
					    <td><a href="floor?floorId=${floor.floorId}">${floor.floorName}</a></td>
				        <td>${floor.filePath}</a></td>
				        <td>${floor.floorLocation}</td>
				        <td>${floor.uploadedBy}</td>
				        <td>${floor.floorDesc}</td>
				    </tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
</center>


</body>
</html>

