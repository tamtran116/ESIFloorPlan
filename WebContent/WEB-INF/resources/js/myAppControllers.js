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
    $('img#loading').hide();
    $('#receiptTotal').mask('0,000,000.00', {reverse: true});
    $('#date').mask('0000-00-00 00:00:00');
});
myAppControllers.factory('places',function(){
    return {};
});
myAppControllers.controller("ListController", function($scope, $http, places, $filter) {
    $scope.timestamp = $filter('date')(Date.now(), "yyyy-MM-MM HH:mm");
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
    $scope.submitAddReceipt = function () {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        console.log(header);
        var formData = new FormData($('#saveForm')[0]);
        console.log(formData);
        var ajaxConfig = {
            headers: {
                'X-CSRF-TOKEN': token,
                'Content-Type': undefined
            }
        };
        $http.post('receipt', formData, ajaxConfig).then(function successCallback(responseData, status, headers, config){
            console.log(responseData);
            $('#message').empty().append(responseData.data);
            $('#myModal').modal('show');
            document.getElementById("saveForm").reset();
        }, function errorCallback(data, status, headers, config){
            console.log(data);
            $('#message').empty().append(data);
            $('#myModal').modal('show');
            document.getElementById("saveForm").reset();
        });
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
myAppControllers.controller("ListReceiptController", function($scope, $http, places) {
    var responsePromise = $http.get('getreceipts');
    responsePromise.success(function(data, status, headers, config) {
        console.log(data);
        $scope.receipts = data;
        $scope.getTotal = function () {
            var total = 0;
            for (var i = 0; i < $scope.receipts.length; i++) {
                var receipt = $scope.receipts[i];
                total += parseFloat(receipt.receiptTotal);
            }
            return total;
        };
    });
    responsePromise.error(function(data, status, headers, config) {
        console.log(data);
        alert(status);
        //y.innerHTML = "AJAX failed! URL is: "+ url;
    });

    $scope.submitDelete = function () {
        var token = $("meta[name='_csrf']").attr("content");
        var ajaxConfig = {
            headers: {
                'X-CSRF-TOKEN': token,
                'Content-Type': undefined
            }
        };
        var formData = new FormData($('#deleteForm')[0]);
        console.log(formData);
        //console.log("--> Submitting form");
        var responsePromise = $http.post("deletereceipts", formData, ajaxConfig);

        responsePromise.success(function (data, status, headers, config) {
            $scope.receipts = data;
            /*var content = $( dataFromServer ).find( "#deleteForm" );
             $( "#deleteForm").parent().empty().append( content );*/
        });
        responsePromise.error(function (data, status, headers, config) {
            console.log(data);
            alert("Submitting form failed!");
        });
    };

    $scope.editReceipt = function(receiptId, event) {
        console.log(receiptId);
        console.log(event);
        var buttonTarget = event.currentTarget;
        $( buttonTarget ).empty().append("<img id='loading' src='resources/images/ajax-loader.gif'/>");
        var responsePromise = $http.get("convertreceipt/"+receiptId);
        responsePromise.success(function (data, status, headers, config) {
            $( buttonTarget ).empty().append("<span class='glyphicon glyphicon-ok' aria-hidden='true'></span>");
            $scope.scanned = data;
            console.log(data);
        });
        responsePromise.error(function (data, status, headers, config) {
            $( buttonTarget ).empty().append("<span class='glyphicon glyphicon-remove' aria-hidden='true'></span>");
            $scope.scanned = data;
            console.log(data);
            alert("Submitting form failed!");
        });
    };
});
myAppControllers.controller("RegisterController", function($scope, $route, $routeParams, places) {
});