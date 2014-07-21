<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
<title>Being Java Guys | Hello World</title>
</head>
<body>
 <center>

  <h2>Being Java Guys | Hello World</h2>
  <h3>
   File name : "<strong>${message}</strong>" uploaded successfully !
  </h3>
  <a href="resources/uploaded_floor/${message}">File</a>
  <img src="<spring:url value='resources/uploaded_floor/${message}'/>"/>

 </center>
</body>
</html>

