<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
  <meta http-equiv="Cache-control" content="NO-CACHE">
  <meta name="description" content="Angular Test">
  <meta name="author" content="Tam Tran">
  <meta name="_csrf" content="${_csrf.token}"/>
  <!-- default header name is X-CSRF-TOKEN -->
  <meta name="_csrf_header" content="${_csrf.headerName}"/>
  <title>My Receipt app</title>

  <!-- Latest compiled and minified CSS -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <!-- Optional theme -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">

  <!-- Jquery first-->
  <script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
  <script src="https://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
  <!-- Latest compiled and minified JavaScript -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  <!-- angular js-->
  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.7/angular.min.js"></script>
  <script src="resources/js/angular-route.min.js"></script>
  <style>
    body {
      padding-top: 70px;
    }
    input[type='checkbox'] {
      width: 1.5em;
      height: 1.5em;
    }
  </style>
  <script>

  </script>
</head>
<body ng-app="myApp" >
<!-- Fixed navbar -->
<nav class="navbar navbar-default navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">My Receipt</a>
    </div>
    <div id="navbar" class="navbar-collapse collapse">
      <ul class="nav navbar-nav">
        <li><a href="#/add" data-toggle="collapse" data-target="#navbar">add</a></li>
        <li><a href="#/receipts" data-toggle="collapse" data-target="#navbar">receipts</a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="#">Action</a></li>
            <li><a href="#">Another action</a></li>
            <li><a href="#">Something else here</a></li>
            <li role="separator" class="divider"></li>
            <li class="dropdown-header">Nav header</li>
            <li><a href="#">Separated link</a></li>
            <li><a href="#">One more separated link</a></li>
          </ul>
        </li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="#/userProfile" data-toggle="collapse" data-target="#navbar"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> ${userName}</a></li>
        <li><a href="logout">logout</a></li>
      </ul>
    </div><!--/.nav-collapse -->
  </div>
</nav>
<div class="container" id="controllerDivId">
  <div class="jumbotron">
    <p>This place will soon to be a ultimate form to send place info to server and store the place id with usersDO id, plus any info related to it. Happy receipting</p>
  </div>
  <div ng-view></div>
</div>


<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Upload Error</h4>
      </div>
      <div class="modal-body">
        <strong><span id="message"></span></strong>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
<script src="resources/js/myAngular.js"></script>
<script src="resources/js/myAppControllers.js"></script>
<script src="resources/js/jquery.mask.js"></script><!-- Mask Jquery plugin-->
<script src="resources/js/bootstrap_plugin/transition.js"></script>
<script src="resources/js/bootstrap_plugin/collapse.js"></script>
</body>
</html>

