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
    var inpFirstName = $('#formFirstName').val;
    if ($('#formFirstName').val.length = 0 || $("#formFirstName").val.length > 20 ) {
        $('#formFirstName').parent().addClass("has-error");
        alert("not valid due to input length = " + inpFirstName);
    } else {
        $('#formFirstName').parent().addClass("has-success");
        alert("valid due to input length = " + inpFirstName);
    }
}