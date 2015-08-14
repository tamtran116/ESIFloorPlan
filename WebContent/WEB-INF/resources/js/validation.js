/**
 * Created by Tam Tran on 8/2/2015.
 */
$(document).ready(function() {
    console.log( "document loaded" );
    $("input").change(function() {
        validateForm();
    })
});

$(window).load(function() {
    console.log( "window loaded" );
});

function validateForm() {
    /*
    * $("#formFirstName") doesn't select the element directly, it selects a jQuery object which contains (among other things) the element,
    *  to access actual DOM properties you must select the first one.
    * */
    var successSpan =
        '<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>' +
        '<span id="inputSuccess2Status" class="sr-only">(success)</span>';
    var errorSpan =
        '<span class="glyphicon glyphicon-warning-sign form-control-feedback" aria-hidden="true"></span>' +
        '<span id="inputWarning2Status" class="sr-only">(warning)</span>'
    var inpFirstName = $('#formFirstName').val();
    if (inpFirstName.length = 0 || inpFirstName.length > 20 ) {
        $('#formFirstName').parent().removeClass();
        $('#formFirstName').parent().addClass("form-group has-error has-feedback");
        $('#formFirstName').parent().append(errorSpan);
        alert("not valid due to input length = " + inpFirstName.length);
    } else {
        $('#formFirstName').parent().removeClass();
        $('#formFirstName').parent().addClass("form-group has-success has-feedback");
        $('#formFirstName').parent().append(successSpan);
        alert("valid due to input length = " + inpFirstName.length);
    }
}