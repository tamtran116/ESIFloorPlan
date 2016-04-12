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
            templateUrl: 'resources/template/place-list.html',
            controller: 'ListController'
        }).
        when('/place/:placeId', {
            templateUrl: 'resources/template/place-detail.html',
            controller: 'DetailController'
        }).
        when('/search', {
            templateUrl: 'resources/template/search.html',
            controller: 'SearchController'
        }).
        when('/receipts', {
            templateUrl: 'resources/template/receipt-list.html',
            controller: 'ListReceiptController'
        }).
        when('/add', {
            templateUrl: 'resources/template/add.html',
            controller: 'ListController'
        }).
        when('/item/:receiptId', {
            templateUrl: 'resources/template/receipt-item.html',
            controller: 'ItemController'
        }).
        otherwise({
            redirectTo: '/receipts'
        })
}]);


