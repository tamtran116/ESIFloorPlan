<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
	<meta http-equiv="Cache-control" content="NO-CACHE">
	<meta name="description" content="register page">
	<meta name="author" content="Tam Tran">
	<meta name="_csrf" content="${_csrf.token}"/>
	<!-- default header name is X-CSRF-TOKEN -->
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
	<title>Upload Floor</title>
	<!-- Bootstrap core CSS -->
	<link href="resources/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="resources/css/jquery.dataTables.css">
	<style>
		body {
			padding-top: 70px;
		}
	</style>
	<script src="resources/js/jquery.min.js" type="text/javascript"></script>
	<script src="resources/js/jquery.dataTables.min.js" type="text/javascript" language="javascript"></script>
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script>
		$( document ).ready(function() {

			//init data table
			$('#data-table').dataTable();

			//ajax load floor database
			$("#floor-database").hide();
			$("#show-floor").click(function(){
				$("#floor-database").fadeIn("slow");;
			});

			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			$(document).ajaxSend(function(e, xhr, options) {
				xhr.setRequestHeader(header, token);
			});

			$( "#uploadForm" ).submit(function( event ) {
				alert("form submitting");
				// Stop form from submitting normally
				event.preventDefault();
				// Get some values from elements on the page:
				// Send the data using post
				/*var posting = $.post( "uploadfloor", $( "#uploadForm" ).serialize() );
				// Put the results in a div
				posting.done(function( data ) {
					console.log(data);
					alert("posting done");
					/!*var content = $( data ).find( "#content" );
					 $( "#result" ).empty().append( content );*!/
				});*/
				var formData = new FormData($(this)[0]);
				console.log(formData);

				$.ajax({
					url: 'uploadfloor',
					type: 'post',
					data: formData,
					async: false,
					cache: false,
					contentType: false,
					processData: false,
					success: function(response) {
						console.log(response);
						alert("posting done");
						var content = $( response ).find( "#data-table" );
						$('table#data-table').parent().empty().append(content);
						$('#data-table').dataTable();
					},
					fail: function() {
						alert("ajax fail");
					}
				});
			});
		});
	</script>
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">My Receipts</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li><a href="register">register</a></li>
				<li class="active"><a href="uploadfloor">uploadfloor</a></li>
				<li><a href="receipt">Save My Receipt</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="logout">logout</a></li>
			</ul>
		</div><!--/.nav-collapse -->
	</div>
</nav>
<div class="container">
	<div class="page-header">
		<h3>Please select a "floor" to upload</h3>
	</div>
	<div >
		<img src="resources/images/floorplan_icon.png" class="img-responsive center-block"/>
	</div>
	<div class="clearfix">
		<form id="uploadForm" method="post" enctype="multipart/form-data" th:object="${uploadFloorInfo}">
			<div class="col-md-6 center-block ">
				<div class="form-group">
					<label for="formFloorName" class="sr-only">Floor Name</label>
					<input type="text" name="floorName" id="formFloorName" placeholder="Floor Name" th:field="*{floorName}" class="form-control"/>
				</div>
				<div class="form-group">
					<label for="formFloorLocation" class="sr-only">Last Name</label>
					<input type="text" name="floorLocation" id="formFloorLocation" placeholder="Floor Location" th:field="*{floorLocation}" class="form-control" />
				</div>
				<div class="form-group">
					<label for="formUploadedBy" class="sr-only">Uploaded By</label>
					<input type="text" name="uploadedBy" id="formUploadedBy" placeholder="uploaded by" th:field="*{uploadedBy}" class="form-control" aria-describedby=""/>
				</div>
			</div>
			<div class="col-md-6 center-block ">
				<div class="form-group">
					<label for="formFloorDesc">Comment:</label>
					<textarea id="formFloorDesc" name="floorDesc" th:field="*{floorDesc}" form="uploadForm" rows="5" class="form-control" placeholder="Enter text here"></textarea>
				</div>
				<div class="form-group">
					<label for="formFile" class="sr-only">Upload File</label>
					<input id="formFile" name="file" type="file"/>
				</div>
				<input class="btn btn-lg btn-primary btn-block" name="submit" type="submit" value="upload" id="uploadFormBtn"/>
			</div>
		</form>
	</div>
	<div>
		<hr>
		<button class="btn btn-lg btn-primary btn-block" id="show-floor">Show Database</button>
		<div id="floor-database">
			<table id="data-table">
				<thead>
				<tr>
					<th>Floor Name</th>
					<th>File Path</th>
					<th>Floor Location</th>
					<th>Uploaded By</th>
					<th>Floor Description</th>
					<th>Delete</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${floorList}" var="floor">
					<tr>
						<td><a href="floor?floorId=${floor.floorId}">${floor.floorName}</a></td>
						<td>${floor.filePath}</td>
						<td>${floor.floorLocation}</td>
						<td>${floor.uploadedBy}</td>
						<td>${floor.floorDesc}</td>
						<td><a href="deleteFloor?floorId=${floor.floorId}">DELETE</a></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
</body>
</html>

