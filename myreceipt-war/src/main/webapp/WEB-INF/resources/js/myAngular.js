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
            templateUrl: 'resources/template/placeDetail.html',
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
        when('/item/:receiptId', {
            templateUrl: 'resources/template/receipt-items.html',
        controller: 'ItemController'
        }).
        otherwise({
            redirectTo: '/receipts'
        })
}]);


