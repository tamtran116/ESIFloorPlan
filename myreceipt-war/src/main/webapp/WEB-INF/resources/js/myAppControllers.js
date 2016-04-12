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
myAppControllers.controller("MainController", function($scope, $route, $routeParams, $location) {
    $scope.$route = $route;
    $scope.$location = $location;
    $scope.$routeParams = $routeParams;
});
myAppControllers.controller("ListController", function($scope, $http, places, $filter) {
    // if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {};
    $scope.hidePreview = function () {
        $('#previewImg').parent().toggle();
    };
    if( /iPhone|iPad|iPod/i.test(navigator.userAgent) ) {
        $('#ios-inp').val(true);
    }
    var jcrop_api;
    $('#previewImg').parent().hide();
    $('#formFile').change(function () {
        readURL(this);
    });
    function readURL(input) {

        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                if(null!=jcrop_api) {
                    jcrop_api.destroy();
                }
                var exif = EXIF.readFromBinaryFile(base64ToArrayBuffer(this.result)),
                    previewImg = $('#previewImg');
                $('#o-inp').val(exif.Orientation);
                // alert("img orentation = " + exif.Orientation);
                previewImg.attr('src', e.target.result);
                previewImg.parent().show();
                previewImg.Jcrop({
                    onSelect: showCoords,
                    onChange: showCoords
                }, function() {
                    jcrop_api = this;
                });

            };

            reader.readAsDataURL(input.files[0]);
        } else {
            $('#previewImg').parent().hide();
        }
    }
    function base64ToArrayBuffer (base64) {
        base64 = base64.replace(/^data\:([^\;]+)\;base64,/gmi, '');
        var binary_string = window.atob(base64);
        var len = binary_string.length;
        var bytes = new Uint8Array( len );
        for (var i = 0; i < len; i++) {
            bytes[i] = binary_string.charCodeAt(i);
        }
        return bytes.buffer;
    }
    function showCoords(c) {
        // variables can be accessed here as
        // c.x, c.y, c.x2, c.y2, c.w, c.h
        $('#x-inp').val(c.x);
        $('#y-inp').val(c.y);
        $('#x2-inp').val(c.x2);
        $('#y2-inp').val(c.y2);
        $('#h-inp').val(c.h);
        $('#w-inp').val(c.w);
    }
    $scope.timestamp = $filter('date')(Date.now(), "yyyy-MM-dd HH:mm");
    var url;
    $scope.getReceipts = function () {
        url = "getreceipts";
        var responsePromise = $http.get(url);
        responsePromise.success(function (data, status, headers, config) {
            $scope.receipts = data; // return array of receipt i guess
            $scope.hasReceipt = true;
        });
        responsePromise.error(function (data, status, headers, config) {
            alert(status);
            y.innerHTML = "AJAX failed! URL is: " + url;
        });
    };
    $scope.submitAddReceipt = function () {
        $('#submit-receipt-btn').empty().append("<img src='resources/images/ajax-loader.gif'/>");
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        var previewImg = $('#previewImg');
        $('#img-h-inp').val(previewImg.height());
        $('#img-w-inp').val(previewImg.width());
        var formData = new FormData($('#saveForm')[0]);
        console.log(formData);
        console.log(header);
        var ajaxConfig = {
            headers: {
                'X-CSRF-TOKEN': token,
                'Content-Type': undefined
            }
        };
        $http.post('receipt', formData, ajaxConfig).then(function successCallback(responseData, status, headers, config){
            console.log(JSON.stringify(responseData));
            if(responseData.data.receiptId != null) {
                console.log("receiptId:"+responseData.data.receiptId);
                window.location = "#/receipts";
            } else if (responseData.data.error != null) {
                $('#message').empty().append(responseData.data.error);
                $('#myModal').modal('show');
            }
            document.getElementById("saveForm").reset();
            $('#submit-receipt-btn').empty().append("submit");
        }, function errorCallback(data, status, headers, config){
            console.log(data);
            $('#message').empty().append(data);
            $('#myModal').modal('show');
            document.getElementById("saveForm").reset();
            $('#submit-receipt-btn').empty().append("submit");
        });
    };
    $scope.getTimestamp = function () {
        $scope.timestamp = $filter('date')(Date.now(), "yyyy-MM-dd HH:mm");
    };
    $scope.getNextPage = function () {
        url = "nearbysearch?token=" + $scope.token;
        ajaxGetPlaces($scope, $http, url, places)
    };
    $scope.putDetail = function(id, name, vicinity, index) {
        console.log("id: " + id + ";name: " + name + ";vicinity: " + vicinity);
        $scope.inputPlaceId = id;
        $scope.inputPlaceName = name;
        $scope.inputPlaceVicinity = vicinity;
        $scope.inputPlaceIndex = index;
        // $('#collapseExample').collapse('hide');
        $('#place-modal').modal('hide');
    };
    $scope.showPlaces = function (event) {
        $('#show-place-btn-modal').empty().append("<img src='resources/images/ajax-loader.gif'/>");
        if (null != places.results) {
            $('#place-modal').modal('show');
            console.log("Places reused!!!");
            $scope.places = {};
            $scope.places.results = places.results;
            $('#show-place-btn-modal').empty().append("show places");
        } else {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(function (position) {
                    url = "nearbysearch?lat=" + position.coords.latitude + "&lng=" + position.coords.longitude + "&radius=500";
                    $scope.coordinate = position.coords.latitude + "," + position.coords.longitude;
                    ajaxGetPlaces($scope, $http, url, places);
                });
            } else {
                x.innerHTML = "Geolocation is not supported by this browser.";
                $('#place-modal').modal('hide');
            }
        }
    };

    function ajaxGetPlaces($scope, $http, url, places) {

        console.log(url);
        var responsePromise = $http.get(url);
        responsePromise.success(function(data, status, headers, config) {
            $('#place-modal').modal('show');
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
        responsePromise.finally(function() {
            // loading.toggleClass("hidden", true);
            $('#show-place-btn-modal').empty().append("show places");
        });
    }
});
/**
 * placeId here is actually place index of place list
 */
myAppControllers.controller("DetailController", function($scope, $http, $route, $routeParams, places) {
    //console.log(JSON.stringify(places));
    if($routeParams.placeId != "" & $routeParams.placeId != null && places.results != null ) {
        $scope.totalCount = places.results.length;
        $scope.whichPlace = parseInt($routeParams.placeId) + 1;
        $scope.nextPlace = parseInt($routeParams.placeId) + 1;
        $scope.previousPlace = $routeParams.placeId - 1;
        $scope.placeDetail = places.results[$routeParams.placeId];
        //console.log(JSON.stringify(places.results[$routeParams.placeId], null, 2));
    }
});
myAppControllers.controller("ItemController", function($scope, $http, $route, $routeParams, places) {
    if($('.glyphicon-king').length == 0) {
        $('#scan-premium').hide();
    }
    //console.log(JSON.stringify(places));
    if($routeParams.receiptId != "" & $routeParams.receiptId != null && places.receipts != null ) {
        // console.log(JSON.stringify(places.receipts));
        $scope.updateItemList = function () {
            $scope.itemList = $scope.receipt.items.split(/\r?\n/g);
        };
        $scope.receipt = places.receipts.filter(function(obj){return obj.receiptId==$routeParams.receiptId})[0];
        $scope.itemList = $scope.receipt.items.split(/\r?\n/g);
        $scope.matchDollarSign = function (item) {
            var value = item.match(/\-?\$?\d*\.\d+/g);
            if (value && value.length > 0) {
                return value[0];
            }
        };
        var textAreaHeight = 1.5*$scope.receipt.items.split(/\r*\n/).length;
        $('#scanned-items').css('height', textAreaHeight + "em");

    } else {
        window.location = "#/receipts";
    }
    $scope.editReceipt = function(receiptId, event, premium) {
        var buttonTarget = event.currentTarget;
        $( buttonTarget ).empty().append("<img src='resources/images/ajax-loader.gif'/>");
        $scope.tempId = receiptId;

        /**
         * The response object has these properties:
         * data – {string|Object} – The response body transformed with the transform functions.
         * status – {number} – HTTP status code of the response.
         * headers – {function([headerName])} – Header getter function.
         * config – {Object} – The configuration object that was used to generate the request.
         * statusText – {string} – HTTP status text of the response.
         */
        $http({
            method: 'GET',
            url: 'convertreceipt/'+receiptId+'?premium='+premium
        }).then(function successCallback(response) {
            // this callback will be called asynchronously
            // when the response is available
            $( buttonTarget ).empty().append("<span class='glyphicon glyphicon-ok' aria-hidden='true'></span>");
            if(premium){
                $( buttonTarget ).prop("disabled",true);
            }
            console.log(response.data);
            var textAreaHeight = 1.5*response.data.split(/\r*\n/).length;
            $('#scanned-items').css('height', textAreaHeight + "em");
            $scope.receipt.items = response.data;
            $scope.itemList = response.data.split(/\r?\n/g);
            /*$scope.receipts.items = response.data;
             for(var i=0; i< $scope.receipts.length; i++) {
             if($scope.receipts[i].receiptId == $scope.tempId) {
             $scope.receipts[i].items = response.data;
             }
             }*/
        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            $( buttonTarget ).empty().append("<span class='glyphicon glyphicon-warning-sign' aria-hidden='true'></span>");
            console.log(response.data);
            $scope.receipt.items = "Error occured, please refresh the page.";
            /*for(var i=0; i< $scope.receipts.length; i++) {
             if($scope.receipts[i].receiptId == $scope.tempId) {
             $scope.receipts[i].items = "Error occured, please refresh the page.";
             }
             }*/
        });
    };
    $scope.saveItems = function() {
        var saveItemBtn = $('#save-item-btn');
        saveItemBtn.empty().append("<img src='resources/images/ajax-loader.gif'/>");
        var token = $("meta[name='_csrf']").attr("content");
        console.log("saving items...");
        console.log("receipt id : " + this.receipt.receiptId);
        console.log("items : " + this.receipt.items);
        var request = {
            receiptId: this.receipt.receiptId,
            receiptItems : this.receipt.items
        };
        var config = {
            method:'post',
            url:'save-receipt-items',
            headers :{
                'Content-Type': 'application/json;charset=utf-8',
                'X-CSRF-TOKEN': token,
                'responseType': "json"
            },
            data: JSON.stringify(request)
        };

        var responsePromise = $http(config);
        responsePromise.success(function (data, status, headers, config) {
            console.log(data);
            saveItemBtn.empty().append("<span class='glyphicon glyphicon-ok' aria-hidden='true'></span>");
            saveItemBtn.prop("disabled",true);
        });
        responsePromise.error(function (data, status, headers, config) {
            console.log(data);
            saveItemBtn.empty().append("<span class='glyphicon glyphicon-warning-sign' aria-hidden='true'></span>");
        });
    };
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
    $scope.sortType = "receiptDateTime"; // default sort
    $scope.sortReverse = true; // default descending
    var responsePromise = $http.get('getreceipts');
    responsePromise.success(function(data, status, headers, config) {
        if(data && Array.isArray(data)) {
            for (var i = 0; i < data.length; i++) {
                // console.log(JSON.stringify(data[i].items));
            }
        }
        $scope.receipts = data;
        places.receipts = data;
        $scope.getTotal = function () {
            var total = 0;
            for (var i = 0; i < $scope.receipts.length; i++) {
                var receipt = $scope.receipts[i];
                receipt.receiptTotal = parseFloat(receipt.receiptTotal);
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
    $scope.selectAllCheckbox = function () {
        var checkboxList = $('#deleteForm').find('input[type="checkbox"]');
        if(checkboxList.prop('checked') == true) {
            checkboxList.prop('checked', false);
        } else {
            checkboxList.prop('checked', true);
        }
    };
    $scope.goToReceiptDetail = function (receiptId) {
        window.location = "#/item/" + receiptId;
    };
});
myAppControllers.controller("RegisterController", function($scope, $route, $routeParams, places) {
});