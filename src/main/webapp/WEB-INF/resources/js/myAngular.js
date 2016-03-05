/**
 * Created by Tam Tran on 10/18/2015.
 */
var myApp = angular.module('myApp', [
    'ngRoute',
    'myAppControllers'
]);

myApp.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.
        when('/list', {
            templateUrl: 'resources/template/placelist.html',
            controller: 'ListController'
        }).
        when('/place/:placeId', {
            templateUrl: 'resources/template/placedetail.html',
            controller: 'DetailController'
        }).
        when('/search', {
            templateUrl: 'resources/template/search.html',
            controller: 'SearchController'
        }).
        when('/receipts', {
            templateUrl: 'resources/template/receipts.html',
            controller: 'ListReceiptController'
        }).
        when('/add', {
            templateUrl: 'resources/template/add.html',
            controller: 'ListController'
        }).
        when('/saveRequest', {
            templateUrl: 'resources/template/saverequest.html'
        }).
        when('/userProfile', {
            templateUrl: 'resources/template/userprofile.html'
        }).
        otherwise({
            redirectTo: '/receipts'
        })
}]);
myApp.config(['$httpProvider', function ($httpProvider) {
    //delete $httpProvider.defaults.headers.common['X-Requested-With'];
    //$httpProvider.defaults.headers.post['Accept'] = 'application/json, text/javascript';
    //$httpProvider.defaults.headers.post['Content-Type'] = 'application/json; charset=utf-8';
    //$httpProvider.defaults.headers.post['Access-Control-Max-Age'] = '1728000';
    //$httpProvider.defaults.headers.common['Access-Control-Max-Age'] = '1728000';
    //$httpProvider.defaults.headers.common['Accept'] = 'application/json, text/javascript';
    //$httpProvider.defaults.headers.common['Content-Type'] = 'application/json; charset=utf-8';
    //$httpProvider.defaults.useXDomain = true;
    var token = $("meta[name='_csrf']").attr("content");
    $httpProvider.defaults.headers.post = { 'X-XSRF-TOKEN' : token };
    $httpProvider.defaults.headers.post = {'Content-Type': 'application/x-www-form-urlencoded'};
}]);

