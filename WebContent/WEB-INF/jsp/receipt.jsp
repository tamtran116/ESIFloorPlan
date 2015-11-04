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
        <li><a href="#/list">List</a></li>
        <li><a href="#/search">search</a></li>
        <li><a href="#/saveRequest">save requestDO</a></li>
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
        <li><a href="#/userProfile"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> ${userName}</a></li>
      </ul>
    </div><!--/.nav-collapse -->
  </div>
</nav>
<div class="container" id="controllerDivId" ng-controller="ListController">
  <div class="jumbotron">
    <p>This place will soon to be a ultimate form to send place info to server and store the place id with usersDO id, plus any info related to it. Happy receipting</p>
  </div>
  <div class="page-header">
    <h2 class="form-signin-heading">Please fill the form</h2>
  </div>
  <c:if test="${not empty message}">
    <div class="alert alert-success alert-dismissible fade in" role="alert">
      <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
      </button>
      <strong>${message}!</strong>
    </div>
  </c:if>
  <div style="margin-bottom: 1em;">
    <form class="form-signin" id="saveForm" action="receipt" th:action="@{/receipt}" th:object="${saveReceiptRequest}" method="post">
      <div class="form-group">
        <label for="placeId" class="sr-only">Place Id</label>
        <input type="text" name="placeId" id="placeId" placeholder="place id" th:field="*{placeId}" class="form-control" ng-model="inputPlaceId" required="true" readonly="true"/>
      </div>
      <div class="form-group">
        <label for="placeName" class="sr-only">Place Name</label>
        <input type="text" name="placeName" id="placeName" placeholder="place name" th:field="*{placeName}" class="form-control" ng-model="inputPlaceName" required="true" readonly="true"/>
      </div>
      <div class="form-group">
        <label for="placeLocation" class="sr-only">Place location</label>
        <input type="text" name="placeLocation" id="placeLocation"  placeholder="place location" th:field="*{placeLocation}" class="form-control" ng-model="inputPlaceVicinity" required="true" readonly="true"/>
      </div>
      <div class="form-group">
        <label for="receiptTotal" class="sr-only">Receipt Total</label>
        <input type="text" name="receiptTotal" id="receiptTotal" placeholder="receipt total" th:field="*{receiptTotal}" class="form-control" aria-describedby="" required="true"/>
      </div>
      <div class="form-group">
        <label for="date" class="sr-only">Place location</label>
        <div class="input-group">
          <input type="text" name="date" id="date" placeolder="YYYY-MM-DD HH:MI" th:field="*{date}" class="form-control" aria-describedby="" required="true" ng-model="timestamp"/>
          <span class="input-group-btn">
            <button class="btn btn-default" type="button" ng-click="getTimestamp()">Now!</button>
          </span>
        </div><!-- /input-group -->
      </div>
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <input class="btn btn-lg btn-primary btn-block" name="submit" type="submit" value="submit" />
    </form>
  </div>
  <a class="btn btn-primary" role="button" data-toggle="collapse" href="#collapseExample" aria-expanded="false" aria-controls="collapseExample" ng-click="showPlaces()">
    Show Places
  </a>
  <hr>
  <div class="collapse" id="collapseExample">
    <div class="well">
      <h4>Found <span class="badge">{{places.results.length}}</span> places <span ng-show="coordinate">near my location <strong>({{coordinate}})</strong></span></h4>
      <input type="search" class="form-control" placeholder="search by place name" ng-model="search">
      <br>
      <div class="list-group">
        <!--<a ng-repeat="y in places.results | filter:search" href="#/place/{{places.results.indexOf(y)}}" class="list-group-item clearfix"><img ng-src="{{y.icon}}" width="30px" height="30px"/> <strong>{{y.name}}</strong> <span class="pull-right">{{y.vicinity}}{{y.formatted_address}}</span></a>-->
        <a ng-repeat="y in places.results | filter:search" class="list-group-item clearfix" ng-click="putDetail(y.id, y.name, y.vicinity)"><img ng-src="{{y.icon}}" width="30px" height="30px"/> <strong>{{y.name}}</strong> <span class="pull-right">{{y.vicinity}}{{y.formatted_address}}</span></a>
      </div>
      <button ng-show="token" class="btn btn-default" ng-click="getNextPage()">next result</button>
    </div>
  </div>
  <!-- Show ReceiptDB -->
  <div class="page-header">
    <h2 class="form-signin-heading">Receipt List</h2>
  </div>
  <div class="table-responsive">
    <form class="form-signin" id="deleteForm" th:object="${deleteReceiptRequest}" method="post">
      <table class="table table-striped table-hover table-condensed">
        <thead>
        <tr>
          <th>Select</th>
          <th>Place</th>
          <th>Location</th>
          <th>Date</th>
          <th>Total</th>
        </tr>
        </thead>
        <c:if test="${!empty receiptResourceList}">
          <c:forEach items="${receiptResourceList}" var="receiptResource">
            <tr>
              <td><p class="text-center"><input type="checkbox" name="receiptIds" value="${receiptResource.receiptId}" th:field="*{receiptIds}"></p></td>
              <td>${receiptResource.placeName}</td>
              <td>${receiptResource.placeLocation}</td>
              <td>${receiptResource.receiptDateTime}</td>
              <td><span class="glyphicon glyphicon-usd" aria-hidden="true"></span> <strong>${receiptResource.receiptTotal}</strong></td>
            </tr>
          </c:forEach>
        </c:if>
        <tfoot>
        <tr class="info">
          <td><strong>Total</strong></td>
          <td>-</td>
          <td>-</td>
          <td>-</td>
          <td><span class="glyphicon glyphicon-usd" aria-hidden="true"></span> <strong>${receiptTotal}</strong></td>
        </tr>
        </tfoot>
      </table>
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
  </div>
  <button class="btn btn-lg btn-primary btn-block" ng-click="submitDelete()">Delete Selected</button>
  <%--<button class="btn btn-primary" ng-click="getReceipts()">Show your Receipts</button>--%>
  <hr>
  <p id="demo"/>
  <p id="error"/>
</div>
<script src="resources/js/myAngular.js"></script>
<script src="resources/js/myAppControllers.js"></script>
<script src="resources/js/jquery.mask.js"></script><!-- Mask Jquery plugin-->
<script src="resources/js/bootstrap_plugin/transition.js"></script>
<script src="resources/js/bootstrap_plugin/collapse.js"></script>
</body>
</html>

