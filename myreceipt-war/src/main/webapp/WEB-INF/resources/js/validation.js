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

$(document).ready(function() {
    console.log( "document loaded" );

    // regex taken from https://developers.google.com/web/fundamentals/design-and-ui/input/forms/provide-real-time-validation?hl=en
    var phoneRegex ='^(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?$',
        emailRegex = /^[-a-z0-9~!$%^&*_=+}{'?]+(\.[-a-z0-9~!$%^&*_=+}{'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+)*\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$/i,
        alphaNumericRegex = /^[a-zA-Z0-9\s]*$/;

    // add popover for every form group class
    $('.form-group').attr('data-toggle', 'popover');

    // manually trigger popover
    var popOverOptions = {
        container : 'body',
        trigger: 'manual'
    };

    // initialize bootstrap popover functionality
    $('[data-toggle="popover"]').popover(popOverOptions);

    // init input jquery objects
    var unInp = $('#user-name-inp'),
        pwInp = $('#password-inp'),
        fnInp = $('#first-name-inp'),
        lnInp = $('#last-name-inp'),
        phInp = $("#phone-number-inp"),
        emInp = $('#email-inp'),
        confEmInp = $('#email-confirm-inp');

    fnInp.validateFormInput("First Name", true, 0, 10, "^[a-zA-Z ]*$");
    lnInp.validateFormInput("Last Name", true, 0, 10, "^[a-zA-Z ]*$");
    phInp.mask('(000) 000-0000').validateFormInput("Phone Number", true, 0, 14, phoneRegex);
    emInp.validateFormInput("Email", true, 0, 50, emailRegex);
    confEmInp.validateFormInput("Email Confirm",true, 0, 50, emailRegex, emInp);
    unInp.validateFormInput("User Name", true, 0, 20, alphaNumericRegex);
    //pwInp.validateFormInput("Password", true, 0, 0, null);
    pwInp.strengthenPassword(6, true, true);
});
$.fn.strengthenPassword = function (minLength, specialChar, capitalChar) {
    var warningList = $('<ul>', {
        class:"list-unstyled"
    });
    this.on('keyup', function (e) {
        var code = (e.keyCode || e.which);
        // do nothing if it's an arrow key or tab
        if(code == 37 || code == 38 || code == 39 || code == 40 || code == 9) {
            return;
        }
        var length = this.value.length,
            capitalCharRegex = /[A-Z]/,
            lowCapitalCharRegex = /[a-z]/,
            $parent = $(this).parent();

        warningList.empty();
        if(minLength && length < minLength) {
            warningList.append('<li style="color: #b6434f;"><span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span> Password is less than min length.</li>');
        }
        if (capitalChar && !new RegExp(lowCapitalCharRegex).test(this.value)) {
            warningList.append('<li style="color: #b6434f;"><span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span> Password does not contain a low cap character.</li>');
        }
        if (capitalChar && !new RegExp(capitalCharRegex).test(this.value)) {
            warningList.append('<li style="color: #b6434f;"><span class="glyphicon glyphicon-warning-sign" aria-hidden="true"></span> Password does not contain a capital character.</li>');
        }
        //$parent.attr('data-content', warningList);
        //$parent.popover('show');
        $parent.append(warningList);
        //console.log(list);
    });
};

$.fn.validateFormInput = function (source, required, minLength, maxLength, pattern, targetInp) {
    var consoleMessage;
    this.on('focusout', function () {
        console.log(consoleMessage);
    });
    this.on('keyup', function (e) {
        var code = (e.keyCode || e.which);
        // do nothing if it's an arrow key or tab
        if(code == 37 || code == 38 || code == 39 || code == 40 || code == 9) {
            return;
        }
        var valid = false,
            inpValue = this.value,
            length = inpValue.length,
            status = source.toLowerCase().replace(' ','-') + "-inp-status";

        if (required && !inpValue) {
            consoleMessage = "Missing required input " + source;
        } else if (targetInp && inpValue !== targetInp.val()) {
            consoleMessage = source + " not matched its source.";
        } else if (maxLength > 0 && length > maxLength) {
            consoleMessage = source + " has more than " + (length - maxLength) + " characters allowed.";
        } else if (minLength && length < minLength) {
            consoleMessage = source + " needs more " + (minLength - length) + " characters.";
        } else if (pattern && !new RegExp(pattern).test(inpValue)) {
            consoleMessage = "Invalid value \"" + inpValue + "\" for " + source;
        } else {
            consoleMessage = source + " is valid.";
            valid = true;
        }
        $(this).populatePopover(valid, consoleMessage);
    });
};
$.fn.populatePopover = function (valid, message) {
    var $element = this;
    var $parent = $element.parent();
    $parent.toggleClass("has-error", false).toggleClass("has-success", false).toggleClass("has-feedback", false);
    $parent.find('span').remove();
    $parent.toggleClass("has-feedback", true);
    $element.attr('aria-describedby', status);
    if(valid) {
        $parent.attr('data-content', "");
        $parent.popover('hide');
        $parent.toggleClass("has-success", true);
        $parent.append('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>');
        $parent.append('<span id="' + status + '" class="sr-only">(success)</span>');
    } else {
        $parent.attr('data-content', message);
        $parent.popover('show');
        $parent.toggleClass("has-error", true);
        $parent.append('<span class="glyphicon glyphicon-warning-sign form-control-feedback" aria-hidden="true"></span>');
        $parent.append('<span id="' + status + '" class="sr-only">(warning)</span>');
    }
};

