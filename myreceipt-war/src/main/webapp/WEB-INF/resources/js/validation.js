/**
 * Check if have jquery. if not include
 */
(function() {
    window.onload = function () {
        if (!window.jQuery) {
            alert("please include jquery");
            throw new Error("Missing jquery");
        }
    };
})();

/**
 * Created by Tam Tran on 8/2/2015.
 */
var phoneRegex ='^(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?$';
var socialRegex ='^\\d{3}-\\d{3}-\\d{4}$';
var emailRegex = /^[-a-z0-9~!$%^&*_=+}{'?]+(\.[-a-z0-9~!$%^&*_=+}{'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+)*\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$/i;
var alphaNumericRegex = /^[a-zA-Z0-9\s]*$/;
$(document).ready(function() {
    var popOverOptions = {
        container : 'body',
        trigger: 'manual'
    };
    console.log( "document loaded" );
    $('[data-toggle="popover"]').popover(popOverOptions);
    $("#formPhoneNumber").mask('(000) 000-0000');
    $('#first-name-inp').validateFormInput("First Name", true, 0, 10, "^[a-zA-Z ]*$");
    $('#last-name-inp').validateFormInput("Last Name", true, 0, 10, "^[a-zA-Z ]*$");

    /*$('form-signin').on("keyup", function() {
        validateRegex("#formPhoneNumber", phoneRegex);
        validateRegex("#formEmail", emailRegex);
        validateConfirm("#formConfirmedEmail", "#formEmail");
    });*/

});

$.fn.validateFormInput = function (source, required, minLength, maxLength, pattern) {
    this.on('keyup', function (e) {
        var code = (e.keyCode || e.which);
        // do nothing if it's an arrow key
        if(code == 37 || code == 38 || code == 39 || code == 40 || code == 9) {
            return;
        }
        var length = this.value.length;
        var parent = this.parentElement;
        var status = source.toLowerCase().replace(' ','-') + "-inp-status";
        $(parent).toggleClass("has-error", false);
        $(parent).toggleClass("has-success", false);
        $(parent).toggleClass("has-feedback", false);
        $(parent).find('span').remove();

        if (!this.value) {
            $(this).populateError("Missing required input " + source);
        } else if (length > maxLength || length < minLength) {
            $(this).populateError("Invalid length for " + source);
        } else if (!new RegExp(pattern).test(this.value)) {
            $(this).populateError("Invalid value for " + source);
        } else {
            $(this).populateSuccess();
        }
    });
};
$.fn.populateError = function (message) {
    var $element = this;
    var $parent = $element.parent();
    $parent.attr('data-content', message);
    $parent.popover('show');
    $parent.toggleClass("has-error", true);
    $parent.toggleClass("has-feedback", true);
    $parent.append('<span class="glyphicon glyphicon-warning-sign form-control-feedback" aria-hidden="true"></span>');
    $element.attr('aria-describedby', status);
    $parent.append('<span id="' + status + '" class="sr-only">(warning)</span>');
};
$.fn.populateSuccess = function () {
    var $element = this;
    var $parent = $element.parent();
    $parent.popover('hide');
    $parent.toggleClass("has-error", false);
    $parent.toggleClass("has-feedback", true);
    $parent.toggleClass("has-success", true);
    $parent.append('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>');
    $element.attr('aria-describedby', status );
    $parent.append('<span id="' + status + '" class="sr-only">(success)</span>');
};

