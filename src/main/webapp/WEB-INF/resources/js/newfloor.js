$(window).bind("load", function() {
	//validate user
	var mouseHide = true;
    var role = $("h1#user_role").html().toLowerCase();
	if (role.indexOf("user")!==-1) {
		$("#accordion-user").fadeIn();
    	$('#accordion-user').accordion({
    		heightStyle: "content",
   			collapsible: true,
   			active:false,
    	});
    	$('#user-data-table').dataTable();
    	mouseHide = false;
    } else {
    	$('.left-wrapper').fadeIn("slow");
    };
//	if (mouseHide) {
//		$('#map').mousedown(function(){
//			$('.left-wrapper').hide();
//	    });
//		$('#map').mouseup(function(){
//			$('.left-wrapper').fadeIn();
//	    });
//	};
	
	//welcome user
    if ($('h1#user_role').text().toLowerCase().indexOf('admin') != -1) {
    	$('h1#user_role').html("Admin");
    } else if ($('h1#user_role').text().toLowerCase().indexOf('manager') != -1) {
    	$('h1#user_role').html("Manager");
    } else if ($('h1#user_role').text().toLowerCase().indexOf('user') != -1) {
    	$('h1#user_role').html("User");
    	$('#accordion-user').accordion({ active:1 });
    } else {
    	$('h1#user_role').html("");
    };
	
	// Team color
	var teams = new Array();
	$( ".team" ).each(function( index ) {
		teams.push($(this).text());
		});
	var results = [];
	$.each(teams, function(i, el){
	    if($.inArray(el, results) === -1) results.push(el);
	});
	$.each(results, function(i, el){
		$('#team-color').append(
				"<option>"+el+"</option>"
			);
	});
	$("select#t-color").change(function () {
		var team = $( "select#team-color option:selected" ).text();
		var color = $( "select#t-color option:selected" ).text();
		alert("color changed");
		$( "div:contains("+team+").cube-close").css( "background-color", color );
	});
	
	
	//Hide optimizer if current is home
	var loc = document.URL;
	if (loc.indexOf("list")!==-1 || loc.indexOf("error")!==-1  ) {
		$('#optimize').hide();
	}
	
	
	//init data table
	$('#data-table').dataTable();
	
	//Accordion init
	$(function() {
		 $( "#accordion" ).accordion({
			 heightStyle: "content",
			 collapsible: true,
			 });
	});
	
	//get measurement for cube 
	var height = $(window).height();
	var width = $(window).width();
	var image_w;
	var image_h;
	$("<img />")
    .attr("src", "resources/images/floor03.jpg")
    .load(function() {
    	image_w = Number(this.width);
        image_h = Number(this.height);
	// add more width for the image
        //$('#img-wrapper').css("width",Number(this.width)+600);
        $('#minimap').attr("src","resources/images/floor03.jpg");
    });
	
	//minimap function
	$('#minimap').click(function (e) { //Relative ( to its parent) mouse position 
		var offset = $(this).offset();
		var x = Math.round(e.pageX - offset.left), y = Math.round(e.pageY - offset.top), w = image_w, h = image_h;
		$('#position').text(x + ' , ' + y);
	    map=document.getElementById('map');
	    img=document.getElementById('minimap');
	    var left=((w/img.width*x)-(map.offsetWidth/2))*-1;
	 	var top=((h/img.height*y)-(map.offsetHeight/2))*-1;
		$(document).scrollLeft(-left);
	    $(document).scrollTop(-top);
	});
	
	
	//init image area select
	var role = $("h1#user_role").html().toLowerCase();
	if (role.indexOf("user") == -1) {
		$('img#map').imgAreaSelect({ 
			parent: "div#img-wrapper",
	        maxWidth: 500, 
	        maxHeight: 500, 
	        handles: true,
	        onSelectEnd: function (img, selection) {
	            $('#mask-selection').fadeOut();
	            $('input#cube_x1').val(selection.x1);
	            $('input#cube_y1').val(selection.y1);
	            $('input#cube_x2').val(selection.x2);
	            $('input#cube_y2').val(selection.y2);
	            $('input#cube_width').val(selection.width);
	            $('input#cube_height').val(selection.height);
	            $('input#cube_cx').val((selection.x1+selection.width)/2);
	            $('input#cube_cy').val((selection.y1+selection.height)/2);
	        }
	    });
	};
    var showOrHide = true;
    
    //any cube div clickable + ajax load open form
    $(".clickable").click(function(){
    	var cube_href = $(this).find("span.cube_id").html();
    	$('#u-form').load("updateCube?id="+ cube_href + " #u-form",function(){
    		if($("div#accordion").toggle(showOrHide)) {
        		$( "#accordion" ).accordion({ active: 2 });
        	} else {
        		$("div#accordion").slideToggle('slow');
        		$( "#accordion" ).accordion({ active: 2 });
        	}
    	});
    	$(".cube-info").load( "cube/"+cube_href+" .cube-info");
    	$('#u-form').scrollTop( 0 );
    	$('#request_cube').val(cube_href);
    	$( "#accordion-user" ).accordion({ active: 1 });
        return false;
    });
    
    //for swapping
    $( "#swap-one" ).focus(function() {
    	$(".clickable").unbind( "click" );
    	$(".swappable").click(function(){
    		$( "#swap-one" ).val($(this).attr("id"));
    		});
    });
    $( "#swap-two" ).focus(function() {
    	$(".clickable").unbind( "click" );
    	$(".swappable").click(function(){
    		$( "#swap-two" ).val($(this).attr("id"));
    		});
    });
    
    
    //make button
    $( "a.j-ui-button, button" ).button();
    
    //get previous location and put it on the map
    $.extend($.imgAreaSelect.prototype, {
        animateSelection: function (x1, y1, x2, y2, duration) {
            var fx = $.extend($('<div/>')[0], {
                ias: this,
                start: this.getSelection(),
                end: { x1: x1, y1: y1, x2: x2, y2: y2 }
            });

            $(fx).animate({
                cur: 1
            },
            {
                duration: duration,
                step: function (now, fx) {
                    var start = fx.elem.start, end = fx.elem.end,
                        curX1 = Math.round(start.x1 + (end.x1 - start.x1) * now),
                        curY1 = Math.round(start.y1 + (end.y1 - start.y1) * now),
                        curX2 = Math.round(start.x2 + (end.x2 - start.x2) * now),
                        curY2 = Math.round(start.y2 + (end.y2 - start.y2) * now);
                    fx.elem.ias.setSelection(curX1, curY1, curX2, curY2);
                    fx.elem.ias.update();
                }
            });
        }
    });
    
    $(function () {
        ias = $('img#map').imgAreaSelect({ fadeSpeed: 400, handles: true,
            instance: true });

        $('button#pre-locaton').click(function () {
            // If nothing's selected, start with a tiny area in the center
            if (!ias.getSelection().width) {
            	ias.setOptions({ show: true, x1: 199, y1: 149, x2: 200, y2: 150 });
            };
            var x1 = $('#currentx1').text();
            var y1 = $('#currenty1').text();
            var x2 = $('#currentx2').text();
            var y2 = $('#currenty2').text();
            ias.animateSelection(x1, y1, x2, y2, 'slow');
        });
    });
    
    
    //open cube optimization
    $("select#closestOpen").change(function () {
    	var no = $( "select#closestOpen option:selected" ).text();
    	var ct = $('span#current-team').text();
    	$("#container").load(ct+"/"+no+" #img-wrapper",function(){
    		$('#table-open-cubes').load(ct+"/"+no+" #table-open-cubes");
    		alert("load complete");
    	});
//    	$('#table-open-cubes').load(ct+"/"+no+" #table-open-cubes");
    	
    });
    
});/* end window bind */

$('a.data-cube-id').on( "click", function() {
	var x =$(this).text();
	$('div#'+x).addClass("cube-selected");
	var role = $("h1#user_role").html().toLowerCase();
	if (role.indexOf("user") !== -1) {
		$('#accordion-user').slideUp( 300 ).delay( 800 ).fadeIn( 400 );
	} else {
		$('.left-wrapper').slideUp( 300 ).delay( 800 ).fadeIn( 400 );
	}
	});
$('a.open-cube-id').on( "click", function() {
	alert("hello");
	var x =$(this).text();
	$('div#'+x).addClass("cube-selected");
	var role = $("h1#user_role").html().toLowerCase();
	if (role.indexOf("user") !== -1) {
		$('#accordion-user').slideUp( 300 ).delay( 800 ).fadeIn( 400 );
	} else {
		$('.left-wrapper').slideUp( 300 ).delay( 800 ).fadeIn( 400 );
	}
	});
$("#toolbox-toggle").on("click", function(){
	var role = $("h1#user_role").html().toLowerCase();
	if (role.indexOf("user") !== -1) {
		$("div#accordion-user").slideToggle("slow");
	} else {
		$("div.left-wrapper").slideToggle("slow");
	};
});

function validateRadio (radios)
{
    for (i = 0; i < radios.length; ++ i)
    {
        if (radios [i].checked) return true;
    }
    return false;
};

function validateForm() {
	var role = $("h1#user_role").html().toLowerCase();
	if (role.indexOf("manager") !== -1) {
		alert("you are not allow to do this. Please contact your administrator.");
		return false;
	};
	var x=document.forms["form"]["cube_id"].value;
	var y=document.forms["update-form"]["cube_id"].value;
	if (x==null || x=="" || y==null || y =="")
	{
	alert("Cube ID must be filled out");
	return false;
	};
	if (validateRadio(document.forms["form"]["occupied"]))
	{
		alert("cube added !");
		return true;
	} else if (validateRadio(document.forms["update-form"]["occupied"])) {
		return true;
	} else {
		alert("Please indicate the cube occupy status");
		return false;
	};
};

function validateUpdate() {
	var y=document.forms["update-form"]["cube_id"].value;
	if (y==null || y =="") {
		alert("Cube ID must be filled out");
		return false;
	};
	if (validateRadio(document.forms["update-form"]["occupied"])) {
		alert("updated cube !");
		return true;
	};
}

function validateRequest() {
	var cube =document.forms["request-form"]["request_cube"].value;
	var name =document.forms["request-form"]["employee_name"].value;
	var req = document.forms["request-form"]["other_request"].value;
	if (cube==null || cube =="") {
		alert("Please fill out your requested cube ID");
		return false;
	} else if (name==null || name =="") {
		alert("Please fill out your name");
		return false;
	} else if (req==null || req =="") {
		alert("Please fill out your request information");
		return false;
	} else {
		alert("Thank you for your submission, you request will be received and process by the manager or admin !");
		return true;
	}
	
}

function goHome() {
	window.location.assign("logout");
}

function refresh() {
	window.location.assign("list");
}

function customSlide(event) {
	$('.left-wrapper').slideUp( 300 ).delay( 800 ).fadeIn( 400 );
	$('.cube-open').css("background-color","green");
}
