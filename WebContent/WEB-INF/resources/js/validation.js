/**
 * Created by Tam Tran on 8/2/2015.
 */
var phoneRegex ='^(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?$';
var socialRegex ='^\\d{3}-\\d{3}-\\d{4}$';
var emailRegex = /^[-a-z0-9~!$%^&*_=+}{'?]+(\.[-a-z0-9~!$%^&*_=+}{'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+)*\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$/i;

$(document).ready(function() {
    console.log( "document loaded" );
    $("#formFirstName").change(function() {
        validateLength("#formFirstName", 20, 0);
    });
    $("#formLastName").change(function() {
        validateLength("#formLastName", 20, 0);
    });
    $("#formPhoneNumber").mask('(000) 000-0000').change(function() {
        validateRegex("#formPhoneNumber", phoneRegex)
    });
    $("#formEmail").change(function() {
        validateRegex("#formEmail", emailRegex)
    });
    $("#formConfirmedEmail").change(function() {
        validateConfirm("#formConfirmedEmail", "#formEmail");
    });
});

$(window).load(function() {
    console.log( "window loaded" );
});


function validateLength(jquerySelector, maxLength, minLength) {
    var describeAttribute = jquerySelector.substring(5);
    var valueLength = $(jquerySelector).val().length;
    var parentDiv = $(jquerySelector).parent();
    if (valueLength > maxLength || valueLength < minLength) {
        addError(parentDiv, jquerySelector, describeAttribute)
        alert("not valid due to input length = " + $(jquerySelector).val().length);
    } else {
        addSuccess(parentDiv, jquerySelector, describeAttribute);
    }
}

function validateRegex(jquerySelector, pattern) {
    var describeAttribute = jquerySelector.substring(5);
    var value = $(jquerySelector).val();
    var regex = new RegExp(pattern);
    var parentDiv = $(jquerySelector).parent();
    if(regex.test(value)) {
        addSuccess(parentDiv, jquerySelector, describeAttribute);
    } else {
        addError(parentDiv, jquerySelector, describeAttribute)
    }
}

function validateConfirm(jquerySelector, jsConfirmElement) {
    var describeAttribute = jquerySelector.substring(5);
    var objectToConfirm = $(jsConfirmElement).val();
    var confirmObj = $(jquerySelector).val();
    var parentDiv = $(jquerySelector).parent();

    if (objectToConfirm == confirmObj) {
        addSuccess(parentDiv, jquerySelector, describeAttribute);
    } else {
        addError(parentDiv, jquerySelector, describeAttribute)

    }
}

function addError(jqueryObj, jsSelector, descAttr) {
    jqueryObj.removeClass();
    jqueryObj.addClass("form-group has-error has-feedback");
    jqueryObj.find(".glyphicon").removeClass().addClass("glyphicon glyphicon-remove form-control-feedback");
    $(jsSelector).attr('aria-describedby','').attr('aria-describedby', descAttr+'Error2Status');
}

function addSuccess(jqueryObj, jsSelector, descAttr) {
    jqueryObj.removeClass();
    jqueryObj.addClass("form-group has-success has-feedback");
    jqueryObj.find(".glyphicon").removeClass().addClass("glyphicon glyphicon-ok form-control-feedback");
    $(jsSelector).attr('aria-describedby','').attr('aria-describedby', descAttr+'Success2Status');
}