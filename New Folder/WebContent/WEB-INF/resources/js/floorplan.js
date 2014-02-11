var flr,flrDiv;
function loadStuff() {
	//alert("loading page");
	flr = document.getElementById("floorImg");
	if (window.addEventListener) {
		flr.addEventListener('mousedown',mouseyDown,false);
	} else if (window.attachEvent) {
		flr.attachEvent('onmousedown',mouseyDown);
	}
	flrDiv = document.getElementById("floorDivID");
	
	//alert("page loaded");
}
var mouseDownDef = false;
var mouseDownX = 0,mouseDownY = 0;
function mouseyDown(e) {
	if (e.currentTarget==flr) {
		mouseUpDown(e,"D","floor");
	} 
	/*else if (e.currentTarget==flr2) {
		mouseUpDown(e,"D","floor2");
	} else if (e.currentTarget==flr3) {
		mouseUpDown(e,"D","floor3");
	} else if (e.currentTarget==flr4) {
		mouseUpDown(e,"D","floor4");
	}*/
}
function mouseyUp(e){
	mouseUpDown(e,"U");
}
function mouseUpDown(e,which,source){
	//document.getElementById('my-image').ondragstart = function() { return false; };
	//alert("DOWN!");
	//swipe("D");
	/**
	 * pageX and pageY:
	 * pageX/Y gives the coordinates relative to the <html> element in CSS pixels.
	 * Relative the to the top left of the fully rendered content area in the browser. This reference point is below the url bar 
	 * and back button in the upper left. This point could be anywhere in the browser window and can actually change location if 
	 * there are embedded scrollable pages embedded within pages and the user moves a scrollbar.
	 *
	 * screenX and screenY:
	 * screenX/Y gives the coordinates relative to the screen in device pixels.
	 * Relative to the top left of the physical screen/monitor, this reference point only moves if you increase or decrease the
	 * number of monitors or the monitor resolution.
	 *
	 * clientX and clientY:
	 * clientX/Y gives the coordinates relative to the viewport in CSS pixels.
	 * Relative to the upper left edge of the browser window. This point can move when the user moves/resizes the browser around
	 * the monitor. This point does not move if the user moves a scrollbar from within the browser.
	 */
	var evt = e ? e:window.event;
	var clickX=0, clickY=0;

	/*if ((evt.clientX || evt.clientY) &&
		document.body &&
		document.body.scrollLeft!=null) {
			clickX = evt.clientX + document.body.scrollLeft;
			clickY = evt.clientY + document.body.scrollTop;
			//alert("First Part X="+clickX+"Y="+clickY);
	}
	if ((evt.clientX || evt.clientY) &&
		document.compatMode=='CSS1Compat' && 
		document.documentElement && 
		document.documentElement.scrollLeft!=null) {
			clickX = evt.clientX + document.documentElement.scrollLeft;
			clickY = evt.clientY + document.documentElement.scrollTop;
			//alert("Second Part X="+clickX+"Y="+clickY);
	}
	if (evt.pageX || evt.pageY) {
		clickX = evt.pageX;
		clickY = evt.pageY;
		//alert("Third Part X="+clickX+"Y="+clickY);
	}*/
	if (evt.pageX || evt.pageY) {
		var rect = evt.currentTarget.getBoundingClientRect();
		var obj = evt.currentTarget;
		var obj2 = obj;
		var curtop = 0;
		var curleft = 0;
		if (obj) {
			do {
				curleft += obj.offsetLeft-obj.scrollLeft;
				curtop += obj.offsetTop-obj.scrollTop;
				obj = obj.offsetParent;
				obj2 = obj2.parentNode;
				while(obj2!=obj) {
					curleft -=obj2.scrollLeft;
					curtop -=obj2.scrollTop;
					obj2 = obj2.parentNode;
				}
			} while (obj.offsetParent)
		} else if (document.layers) {
			curtop += obj.y;
			curleft += obj.x;
		}
		clickX = evt.pageX - curleft;
		clickY = evt.pageY - curtop;
	}
	if (which=="D") {
		mouseDownDef = true;
		mouseDownX = clickX;
		mouseDownY = clickY;
		//alert("down X="+mouseDownX+" Y="+mouseDownY);
	} else if (which=="U" && mouseDownDef) {
		if (clickY<mouseDownY) {
			//alert("Swipe left");
			rotate("p");
			//alert("Swipe left");
		} else if (clickY>mouseDownY) {
			//alert("Swipe right");
			rotate("n");
			//alert("Swipe right");
		} else {
			//alert ("Swipey swipey?");
		}
		mouseDownDef = false;
	}
	//alert(source+" X="+mouseDownX+" Y="+mouseDownY);
	/*document.getElementById('cordinate').innerHTML = source+" X="+mouseDownX+" Y="+mouseDownY;
	if (window.XMLHttpRequest)
	  {// code for IE7+, Firefox, Chrome, Opera, Safari
	  xmlhttp=new XMLHttpRequest();
	  }
	else
	  {// code for IE6, IE5
	  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
	xmlhttp.onreadystatechange=function()
	{
	  if (xmlhttp.readyState==4 && xmlhttp.status==200)
	    {
	    document.getElementById("cordinate").innerHTML=xmlhttp.responseText;
	    }
	  }
	xmlhttp.open("GET","mysql_con.php?x="+mouseDownX+"&y="+mouseDownY,true);
	xmlhttp.send();*/
}
function biggerer() {
	var w = flrDiv.offsetWidth;
	var h = flrDiv.offsetHeight;
	var ww = flr.width;
	var hh = flr.height;

	//flrDiv.style.width= (w+10);
	//flrDiv.style.height= (h+10);
	flr.style.width = (ww+10);
	flr.style.height= (hh+10);
	//alert("Make bigger W="+w+" H="+h);
}
function smallerer() {
	var w = flrDiv.offsetWidth;
	var h = flrDiv.offsetHeight;
	var ww = flr.width;
	var hh = flr.height;
	
	//flrDiv.style.width= (w-10);
	//flrDiv.style.height= (h-10);
	flr.style.width = (ww-10);
	flr.style.height= (hh-10);
	//alert("Make smaller W="+w+" H="+h);
}
//add minimap functionality
var mu = true;
var floorImg = document.getElementById('floorImg');
var tablecell = minimap.offsetParent, t_left = tablecell.offsetLeft, t_top = tablecell.offsetTop;

minimap.addEventListener('mousemove',function(e) { mu || pos(e);} ,false);
minimap.addEventListener('mousedown',function(e) { mu=false; pos(e);e.preventDefault();e.stopPropagation()}, false);
minimap.addEventListener('mouseup',function(e) {mu=true;},false);

function pos(e) {
	var minimap = document.getElementById('minimap');
	var x = e.pageX - minimap.offsetLeft - t_left -9, y = e.pageY - minimap.offsetTop - t_top -9;
	floorImg.style.left = 200 - x*4 + "px";
	floorImg.style.top = 200 - y*5 + "px";
	
	var minimap_rect = document.getElementById('minimap-rect');
	minimap_rect.style.left=x-50;
	minimap_rect.style.top=y-40;

	var ias = $('img#floorImg').imgAreaSelect({ instance: true });
    ias.cancelSelection();
    $('#mask-selection').fadeIn();
    $('#selection').fadeOut();
    $('input#form-reset').click();
	//alert(x + " " + y );
};