/**
 * Created by Tam Tran on 10/18/2015.
 */
var x = document.getElementById("demo");
var y = document.getElementById("error");
var myAppControllers = angular.module('myAppControllers', []);
/*window.onbeforeunload = function() {
    return "Data will be lost if you leave the page";
};*/
$(document).ready(function() {
    $('#receiptTotal').mask('0,000,000.00', {reverse: true});
    $('#date').mask('0000-00-00 00:00:00');
});
myAppControllers.factory('places',function(){
    return {};
});
myAppControllers.controller("ListController", function($scope, $http, places, $filter) {
    var url;
    $scope.getReceipts = function () {
        url = "getreceipts";
        var responsePromise = $http.get(url);
        responsePromise.success(function (data, status, headers, config) {
            $scope.receipts = data;
            $scope.hasReceipt = true;
        });
        responsePromise.error(function (data, status, headers, config) {
            alert(status);
            y.innerHTML = "AJAX failed! URL is: " + url;
        });
    };
    $scope.getTotal = function () {
        var total = 0;
        for (var i = 0; i < $scope.receipts.length; i++) {
            var receipt = $scope.receipts[i];
            total += parseFloat(receipt.receiptTotal);
        }
        return total;
    };
    $scope.getTimestamp = function () {
        $scope.timestamp = $filter('date')(Date.now(), "yyyy-MM-MM HH:mm");
    };
    $scope.getNextPage = function () {
        url = "nearbysearch?token=" + $scope.token;
        ajaxGetPlaces($scope, $http, url, places)
    };
    $scope.putDetail = function(id, name, vicinity) {
        console.log("id: " + id + ";name: " + name + ";vicinity: " + vicinity);
        $scope.inputPlaceId = id;
        $scope.inputPlaceName = name;
        $scope.inputPlaceVicinity = vicinity;
        $('#collapseExample').collapse('hide');
    };
    $scope.showPlaces = function () {
        if (null != places.results) {
            console.log("Places reused!!!");
            $scope.places = {};
            $scope.places.results = places.results;
        } else {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(function (position) {
                    url = "nearbysearch?lat=" + position.coords.latitude + "&lng=" + position.coords.longitude + "&radius=500";
                    $scope.coordinate = position.coords.latitude + "," + position.coords.longitude;
                    ajaxGetPlaces($scope, $http, url, places);
                });
            } else {
                x.innerHTML = "Geolocation is not supported by this browser.";
            }
        }
    };

    $scope.submitDelete = function () {
        //console.log("--> Submitting form");
        var responsePromise = $http.post("/esi/deletereceipts", $( "#deleteForm" ).serialize(), {});

        responsePromise.success(function (dataFromServer, status, headers, config) {
            var content = $( dataFromServer ).find( "#deleteForm" );
            $( "#deleteForm").parent().empty().append( content );
        });
        responsePromise.error(function (data, status, headers, config) {
            alert("Submitting form failed!");
        });
    };


    function ajaxGetPlaces($scope, $http, url, places) {
        console.log(url);
        var responsePromise = $http.get(url);
        responsePromise.success(function(data, status, headers, config) {
            $scope.places = places;
            $scope.places = data;
            places.results = data.results;
            $scope.token = data.next_page_token;

            if(data.error_message) {
                alert("Daily request quota reach");
            }
        });
        responsePromise.error(function(data, status, headers, config) {
            alert(status);
            y.innerHTML = "AJAX failed! URL is: "+ url;
        });
    }
});
myAppControllers.controller("DetailController", function($scope, $http, $route, $routeParams, places) {
    //console.log(JSON.stringify(places));
    if($routeParams.placeId >= 0 && places.results != null ) {
        $scope.totalCount = places.results.length;
        //alert("hello");
        //console.log(JSON.stringify(places.results[$routeParams.placeId]));
        $scope.whichPlace = parseInt($routeParams.placeId) + 1;
        $scope.nextPlace = parseInt($routeParams.placeId) + 1;
        $scope.previousPlace = $routeParams.placeId - 1;
        $scope.placeDetail = places.results[$routeParams.placeId];
        //console.log(JSON.stringify(places.results[$routeParams.placeId], null, 2));
    }
});
myAppControllers.controller("SearchController", function($scope, $http, places) {
    $scope.searchPlace = function (value) {
        if( null != places.placeList) {
            $scope.getPlaces = places;
        } else if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function(position) {
                var url = "textsearch?name=" + value + "&location=" + position.coords.latitude + "," + position.coords.longitude + "&radius=5000";
                $scope.coordinate = position.coords.latitude + "," + position.coords.longitude;
                console.log(url);
                var responsePromise = $http.get(url);

                responsePromise.success(function(data, status, headers, config) {
                    $scope.getPlaces = places;
                    $scope.getPlaces.placeList = data.results;
                    $scope.token = data.next_page_token;
                    if(data.error_message) {
                        alert("Daily request quota reach");
                    }
                });
                responsePromise.error(function(data, status, headers, config) {
                    alert(status);
                    y.innerHTML = "AJAX failed! URL is: "+ url;
                });
            });
        } else {
            x.innerHTML = "Geolocation is not supported by this browser.";
        }
    };
});
myAppControllers.controller("RegisterController", function($scope, $route, $routeParams, places) {
});
