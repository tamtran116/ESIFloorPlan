<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<!-- <base href="http://localhost:8080/ESIFloorPlan/" /> -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Floor Plan Reloaded</title>
<link rel="stylesheet" type="text/css" href="resources/css/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="resources/css/imgareaselect-default.css" />
<link rel="stylesheet" type="text/css" href="resources/css/Reset.css" />
<link rel="stylesheet" type="text/css" href="resources/css/newfloor.css" />
<link rel="stylesheet" type="text/css" href="resources/css/jquery.dataTables.css"/>

<script src="resources/js/jquery.min.js" type="text/javascript"></script>
<script src="resources/js/jquery.dataTables.min.js" type="text/javascript" language="javascript" ></script>
<script src="resources/js/jquery.imgareaselect.pack.js" type="text/javascript" ></script>
<script src="resources/js/jquery-ui.js" type="text/javascript"></script>
<script>
  function preventBack(){window.history.forward();}
  setTimeout("preventBack()", 0);
  window.onunload=function(){null};
</script>
</head>
<body ondragstart="return false;" ondrop="return false;">
	<div id="container">
		<div id="img-wrapper"><img id="map" src="${floorUrl}" />
			<c:if  test="${!empty cubeList}">
				<c:forEach items="${cubeList}" var="cubeDO">
					<c:if test="${cubeDO.occupied != 'true'}">
						<div draggable="true" id="${cubeDO.cubeId}" class="cubeDO-open clickable swappable" style="left:${cubeDO.x1}px; top:${cubeDO.y1}px; width:${cubeDO.width}px; height:${cubeDO.height}px;"><p>${cubeDO.employee_name}<br><span class="cubeId">${cubeDO.cubeId}</span></p><a name="${cubeDO.cubeId}" class="hidden">${cubeDO.cubeId}</a></div>
					</c:if>
					<c:if test="${cubeDO.occupied != 'false'}">
						<div draggable="true" id="${cubeDO.cubeId}" class="cubeDO-close clickable swappable" style="left:${cubeDO.x1}px; top:${cubeDO.y1}px; width:${cubeDO.width}px; height:${cubeDO.height}px;"><p>${cubeDO.employee_name}<br><span class="cubeId">${cubeDO.cubeId}</span><br><span class="team" style="color:#00CCFF;">${cubeDO.teamLeader}</span></p></div>
					</c:if>
				</c:forEach>
			</c:if>
		</div>
	</div>
	<!--<div id="toolbox-tri" style="position:fixed;"></div>
	<img id="toolbox-toggle" src="resources/images/cog-icon-small.png" style="position:fixed;top:0px;left:0px;"/>-->
	<div id="accordion-resizer" class="left-wrapper">
		<div id="accordion">
			<h3>Intro</h3>
			<div>
				
				Hello <h1 id="user_role" style="color:red; display:inline-block;">${role}</h1>, <br/> <h3 style="color:blue;">${error}</h3><br/>
				Welcome Back to ESI Floor Plan
				<p>Please click on one of these accordions below to customize the cubeDO</p><br/>
				Click on the gear button on the top left of your screen to hide the tool box.
			</div>
			<h3>Add Cube</h3>
			<div>
				<form:form id="form" name="form" method="post" action="addCube" commandName="cubeDO" target="_self" onsubmit="return validateForm(this)">
					<p style="font-size: 0.8em;">To add cubeDO, Please select the area of the cubeDO <br/>by clicking and dragging on your designated position.</p>
					<h2>Cube ID</h2><form:input path="cubeId" id="cubeId"/>
					<p>Occupy status:</p><form:radiobutton path="occupied" value="true"/>Yes
		            <form:radiobutton path="occupied" value="false"/>No
		            <form:hidden path="x1" id="cube_x1" readonly="true"/>
		            <form:hidden path="y1" id="cube_y1" readonly="true"/>
		            <form:hidden path="x2" id="cube_x2" readonly="true"/>
		            <form:hidden path="y2" id="cube_y2" readonly="true"/>
		            <form:hidden path="cx" id="cube_cx" readonly="true"/>
		            <form:hidden path="cy" id="cube_cy" readonly="true"/>
		            <form:hidden path="width" id="cube_width" readonly="true"/>
		            <form:hidden path="height" id="cube_height" readonly="true"/>
		            <p>Floor ID is ${floorId}</p>
		            <p>Employee Name:</p><form:input path="employee_name"/>
		            <p>Team Leader:</p><form:input path="teamLeader"/>
		            <p>Phone number:</p><form:input path="phone"/>
		            <p>Software Request:</p><form:textarea path="software" rows="5" cols="30" />
		            <p>New Hire Check-list :</p><hr>
		            <p>New Workspace/On-Shore</p><form:radiobutton path="onshore" value="true"/>Yes
		            <form:radiobutton path="onshore" value="false"/>No
		            <p>Off-Shore Request</p><form:radiobutton path="offshore" value="true"/>Yes
		            <form:radiobutton path="offshore" value="false"/>No
		            <p>Security Badge/Building Access</p><form:radiobutton path="badge" value="true"/>Yes
		            <form:radiobutton path="badge" value="false"/>No
		            <p>Laptop</p><form:radiobutton path="laptop" value="true"/>Yes
		            <form:radiobutton path="laptop" value="false"/>No
		            <p>Parking Registration (if applicable - site specific)</p><form:radiobutton path="parking" value="true"/>Yes
		            <form:radiobutton path="parking" value="false"/>No
		            <p>VPN Token</p><form:radiobutton path="vpn" value="true"/>Yes
		            <form:radiobutton path="vpn" value="false"/>No
		            <br><hr>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		            <input type="reset"> <input type="submit" value="Submit"><!-- <button id="ajax">Submit By Ajax</button> -->
				</form:form>
			</div>
			<h3>Update Cube</h3>
			<div id="u-form">
				<!-- Update CubeDO -->
				<c:if test="${not empty currentCube}">
				<form:form id="update-form" name="udpate-form" method="post" action="updateCubeDO" commandName="currentCube" target="_self" onsubmit="return validateUpdate(this)">
					<h1 style="color:blue;">Update Cube</h1>
					<h2>Cube ID</h2><form:input path="cubeId" id="cubeId" readonly="true"/>
					<p>Occupy status:</p>
					<form:radiobutton path="occupied" value="true"/>Yes
				          <form:radiobutton path="occupied" value="false"/>No
				          <form:hidden path="x1" id="cube_x1" readonly="true"/>
				          <form:hidden path="y1" id="cube_y1" readonly="true"/>
				          <form:hidden path="x2" id="cube_x2" readonly="true"/>
				          <form:hidden path="y2" id="cube_y2" readonly="true"/>
				          <form:hidden path="cx" id="cube_cx" readonly="true"/>
				          <form:hidden path="cy" id="cube_cy" readonly="true"/>
				          <form:hidden path="width" id="cube_width" readonly="true"/>
				          <form:hidden path="height" id="cube_height" readonly="true"/>
				          <p>Employee Name:</p><form:input path="employee_name"/>
				          <p>Team Leader:</p><form:input path="teamLeader"/>
				          <p>Phone number:</p><form:input path="phone"/>
				          <p>Software Request:</p><form:textarea path="software" rows="5" cols="30" />
				          <p>New Hire Check-list :</p><hr>
				          <p>New Workspace/On-Shore</p><form:radiobutton path="onshore" value="true"/>Yes
				          <form:radiobutton path="onshore" value="false"/>No
				          <p>Off-Shore Request</p><form:radiobutton path="offshore" value="true"/>Yes
				          <form:radiobutton path="offshore" value="false"/>No
				          <p>Security Badge/Building Access</p><form:radiobutton path="badge" value="true"/>Yes
				          <form:radiobutton path="badge" value="false"/>No
				          <p>Laptop</p><form:radiobutton path="laptop" value="true"/>Yes
				          <form:radiobutton path="laptop" value="false"/>No
				          <p>Parking Registration (if applicable - site specific)</p><form:radiobutton path="parking" value="true"/>Yes
				          <form:radiobutton path="parking" value="false"/>No
				          <p>VPN Token</p><form:radiobutton path="vpn" value="true"/>Yes
				          <form:radiobutton path="vpn" value="false"/>No
				          <br><hr>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				          <input type="reset"> <input type="submit" value="Update"><!-- <button id="ajax">Submit By Ajax</button> -->
				</form:form></c:if>
			</div>
			<!-- /update CubeDO -->
			<h3>Swap Cube</h3>
			<div>
				<form action="swap" method="get" accept-charset="UTF-8">
					<p>Begin by clicking on the first cubeDO input<br/>Then clicking on any cubeDO<br/> when you finish choosing the first cubeDO, please click on the second input to pick the second cubeDO</p>
					<p>First Cube</p><input id="swap-one" name="swap-one" type="text"/>
					<p>Second Cube</p><p>Please choose the second cubeDO</p>
					<input id="swap-two" name="swap-two" type="text"/>
					<p>Please pick which info you want to swap</p>
					<input type="checkbox" name="swapinfo" value="name" checked disabled>Name only<br>
					<!-- <input type="checkbox" name="swapinfo" value="ipphone" readonly > -->IP Phone (not implemented yet)<br>
					<!-- <input type="checkbox" name="swapinfo" value="cubeDO" readonly > -->whole cubeDO (not implemented yet)<br>
					<input type="reset"/> | <input type="submit" value="submit"/>
				</form>
			</div>
			<h3>Cube Database</h3>
			<div><span style="color:red;">${message}</span>
				<c:if  test="${!empty cubeList}">
					<table id="data-table">
					<thead>
						<tr>
						    <th>Cube ID</th>
						    <th>Employee</th>
						    <th>Occupy</th>
						    <th>Team Lead</th>
						    <th>Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${cubeList}" var="cubeDO">
						    <tr id="${cubeDO.cubeId}">
						        <td><a href="#${cubeDO.cubeId}" class="data-cubeDO-id">${cubeDO.cubeId}</a></td>
						        <td>${cubeDO.employee_name}</td>
						        <td>${cubeDO.occupied}</td>
						        <td><a href="${cubeDO.teamLeader}">${cubeDO.teamLeader}</a></td>
						        <td><a href="deleteCube/${cubeDO.cubeId}" onclick="return confirm('Are you sure want to delete this cubeDO?')">Delete</a></td>
						    </tr>
						</c:forEach>
					</tbody>
					</table>
				</c:if>
			</div>
			<h3>Cube information</h3>
				<div class="cubeDO-info">
					<p>Cube ID: <span id="delCubeId">${currentCube.cubeId}</span></p>
					<p>Employee Name: ${currentCube.employee_name}</p>
					<p>Occupied: ${currentCube.occupied}</p>
					<p>Team Leader: ${currentCube.teamLeader}</p>
					<p>Cordinate: X1 ${currentCube.x1}</p>
					<p>Cordinate: Y1 ${currentCube.y1}</p>
					<p>Cordinate: X2 ${currentCube.x2}</p>
					<p>Cordinate: Y2 ${currentCube.y2}</p>
					<p>Height: ${currentCube.height}</p>
					<p>Width: ${currentCube.width}</p>
					<a href="deleteCube/${currentCube.cubeId}" class="delCube" onclick="return confirm('Are you sure want to delete this cubeDO?')">Delete</a>
					<span id="currentx1" style="display:none">${currentCube.x1}</span>
					<span id="currenty1" style="display:none">${currentCube.y1}</span>
					<span id="currentx2" style="display:none">${currentCube.x2}</span>
					<span id="currenty2" style="display:none">${currentCube.y2}</span>
					
				</div>
				<h3>List Request</h3>
				<div>
					<c:if  test="${!empty requestList}">
					<table>
					<thead>
						<tr>
						    <th>Cube</th>
						    <th>Employee Name</th>
						    <th>Phone</th>
						    <th>Email</th>
						    <th>Other Request</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${requestList}" var="re">
						    <tr>
						        <td>${re.request_cube}</td>
						        <td>${re.employee_name}</td>
						        <td>${re.employee_phone}</td>
						        <td>${re.employee_email}</td>
						        <td>${re.other_request}</td>
						    </tr>
						</c:forEach>
					</tbody>
					</table>
				</c:if>
				</div>
				<h3>Team Color</h3>
				<div>
					Choose Team <select id="team-color"></select>
					 Choose Color <select id="t-color">
					 	<option>aqua</option>
					 	<option>aquamarine</option>
					 	<option>bisque</option>
					 	<option>coral</option>
					 	<option>crimson</option>
					 	<option>dodgerblue</option>	
					 </select>
					 <h1>Warning ! All the color will be lost when you hit refresh button.<br/> 
					 This "color assign team" will help you to differentiate teams temporaliry </h1>
				</div>
		</div><!-- End accordion -->
		<div id="commands"><br/>
		<p>To save time clicking and dragging, please hit on the click on any of the cubeDO open or closed cubeDO and hit the "pre-location" button and you will have the size of the cubeDO.
		Then you can click on the selected area and drag to a new cubeDO and click on add cubeDO to save the new information for the new cubeDO. </p>
		<button onclick="return window.location.assign('/list')">Refresh</button> |
		<a class="j-ui-button" href="logout" target="_self">Log Out</a> | 
		<button type="button" id="pre-locaton">Pre-location</button><br/><br/>
		Optimization Option will be available when you click on the team name inside the cubeDO database
		<hr>
		<a class="j-ui-button" href="uploadfloor" target="_self">Upload Floor</a>
			<div id="optimize" style="font-size:1em !important;">
			Number of Closest Open Cube for <span id="current-team">${cubeList[0].teamLeader}</span> Team
			
			<select id="closestOpen">
			  <option value="1">1</option>
			  <option value="2">2</option>
			  <option value="3">3</option>
			  <option value="4">4</option>
			  <option value="5">5</option>
			  <option value="6">6</option>
			  <option value="7">7</option>
			  <option value="8">8</option>
			  <option value="9">9</option>
			  <option value="10">10</option>
			</select>
			
			<c:if  test="${!empty cubeList}">
					<table id="table-open-cubeDOs">
					<thead>
						<tr>
						    <th>Cube ID</th>
						    <th>Employee</th>
						    <th>Occupy</th>
						    <th>Team Lead</th>
						    <th>Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${cubeList}" var="cubeDO">
							<c:if test="${cubeDO.occupied != 'true'}">
						    <tr id="${cubeDO.cubeId}">
						        <td><a href="#${cubeDO.cubeId}" class="open-cubeDO-id" onclick="customSlide()">${cubeDO.cubeId}</a></td>
						        <td>${cubeDO.employee_name}</td>
						        <td>${cubeDO.occupied}</td>
						        <td><a href="${cubeDO.teamLeader}">${cubeDO.teamLeader}</a></td>
						        <td><a href="deleteCube/${cubeDO.cubeId}" onclick="return confirm('Are you sure want to delete this cubeDO?')">Delete</a></td>
						    </tr>
						    </c:if>
						</c:forEach>
					</tbody>
					</table>
				</c:if>
				
			</div><!-- End optimize -->
		</div><!-- End commands -->
	</div><!-- End resizer -->
	<div id="accordion-usersDO">
		<h3>Cube Data</h3>
		<div style="background-color: white;">
			<c:if  test="${!empty cubeList}">
				<table id="usersDO-data-table">
				<thead>
					<tr>
					    <th>Cube ID</th>
					    <th>Employee</th>
					    <th>Occupy</th>
					    <th>Team Lead</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${cubeList}" var="cubeDO">
					    <tr id="${cubeDO.cubeId}">
					        <td><a href="#${cubeDO.cubeId}" class="data-cubeDO-id">${cubeDO.cubeId}</a></td>
					        <td>${cubeDO.employee_name}</td>
					        <td>${cubeDO.occupied}</td>
					        <td>${cubeDO.teamLeader}</td>
					    </tr>
					</c:forEach>
				</tbody>
				</table>
			</c:if>
		</div>
		<h3>Request</h3>
		<div><c:if  test="${!empty requestDO}">
			<form:form id="requestDO-form" name="requestDO-form" method="post" action="addRequest" commandName="requestDO" target="_self" onsubmit="return validateRequest(this)">
			<table>
				<tr>
					<td>Employee name: (required)</td>
					<td><form:input id="employee_name" path="employee_name"/></td>
				</tr>
				<tr>
					<td>Employee phone: </td>
					<td><form:input path="employee_phone"/></td>
				</tr>
				<tr>
					<td>Employee email: </td>
					<td><form:input path="employee_email"/></td>
				</tr>
				<tr>
					<td>Request for Cube : (required) </td>
					<td><form:input id="request_cube" path="request_cube"/></td>
				</tr>
				<tr>
					<td>Request for Team : </td>
					<td><form:input path="request_team"/></td>
				</tr>
				<tr>
					<td>Other requestDO : (required)</td>
					<td><form:textarea id="other_request" path="other_request"/></td>
				</tr>
			</table>
			<input type="reset"> <input type="submit" value="Submit"><br/>
			</form:form></c:if>
		</div><!-- /Test Form -->
		<h3>Actions</h3>
		<div><button onclick='goHome()'>Log Out</button> | <button onclick='refresh()'>refresh</button></div>
	</div><!-- /accordion usersDO -->
	<!-- <img id="minimap" class="shadow"/> -->
	<script src="resources/js/newfloor.js" type="text/javascript"></script>
	</body>
</html>
