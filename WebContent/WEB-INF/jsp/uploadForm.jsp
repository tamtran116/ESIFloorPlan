<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<script type="text/javascript" src="jquery-1.2.6.min.js"></script>
<title>Upload Floor</title>
</head>
<body>
<img src="resources/images/ExpressScripts-logo1.jpg"/>
  <h3>Please select a file to upload !</h3>
  
<center>
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
  </center>
 
</body>
</html>

