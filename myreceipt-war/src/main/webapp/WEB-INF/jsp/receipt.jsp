<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta http-equiv="Cache-control" content="NO-CACHE">
    <meta name="description" content="Angular Test">
    <meta name="author" content="Tam Tran">
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>My Receipt app</title>

    <!--j-crop for cropping image-->
    <link rel="stylesheet" href="resources/css/jquery.Jcrop.min.css" type="text/css" />
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="resources/css/bootstrap.min.css">
    <!-- Optional theme -->
    <link rel="stylesheet" href="resources/css/bootstrap-theme.min.css">
    <!-- customize css -->
    <link rel="stylesheet" href="resources/css/receipts.css">


    <!--jquery 1.11.3-->
    <script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
    <!--jquery backward compatible-->
    <script src="https://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
    <!--JCROP-->
    <script src="resources/js/jquery.Jcrop.min.js"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script src="resources/js/bootstrap.min.js"></script>
    <!-- angular js-->
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.7/angular.min.js"></script>
    <script src="resources/js/angular-route.min.js"></script>
    <!-- exif image info extration -->
    <script src="resources/js/exif.js"></script>

</head>
<body ng-app="myApp">
<!--Fixed navbar -->
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
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="#/userProfile" data-toggle="collapse" data-target="#navbar">
                        <c:if test="${role == 'premium'}">
                            <span class="glyphicon glyphicon-king" aria-hidden="true"></span> ${userName}
                        </c:if>
                        <c:if test="${role == 'regular'}">
                            <span class="glyphicon glyphicon-user" aria-hidden="true"></span> ${userName}
                        </c:if>
                    </a>
                </li>
                <li><a href="logout"><span class="glyphicon glyphicon-log-out" aria-hidden="true"></span> logout</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>

<div class="container" id="controllerDivId" ng-controller="MainController">
    <div class="btn-group btn-group-justified" role="group" aria-label="group nav">
        <a role="button" class="btn btn-default" href="#/add">Add Receipt</a>
        <a role="button" class="btn btn-default" href="#/receipts">List Receipt</a>
        <a role="button" class="btn btn-default" href="#">Modify Receipt</a>
    </div>
    <div ng-hide="$location.path()">
        <h2>Please click on the button to start or listen to this song :)</h2>
        <div class="embed-responsive embed-responsive-16by9">
            <iframe width="420" height="315" src="https://www.youtube.com/embed/pz0KoNeI2nQ" frameborder="0" allowfullscreen></iframe>
        </div>
    </div>
    <div ng-view></div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
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

