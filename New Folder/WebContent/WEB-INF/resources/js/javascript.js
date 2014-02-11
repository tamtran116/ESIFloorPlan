var flr1,flr2,flr3,flr4;
function loadStuff() {
	//alert("loading page");
	//document.onmousedown = mouseyDown;
	//document.onmouseup = mouseyUp;
	flr1 = document.getElementById("floor1");
	//flr2 = document.getElementById("floor2");
	//flr3 = document.getElementById("floor3");
	//flr4 = document.getElementById("floor4");
	if (window.addEventListener) {
		flr1.addEventListener('mousedown',mouseyDown,false);
		//flr2.addEventListener('mousedown',mouseyDown,false);
		//flr3.addEventListener('mousedown',mouseyDown,false);
		//flr4.addEventListener('mousedown',mouseyDown,false);
	} else if (window.attachEvent) {
		flr1.attachEvent('onmousedown',mouseyDown);
		//flr2.attachEvent('onmousedown',mouseyDown);
		//flr3.attachEvent('onmousedown',mouseyDown);
		//flr4.attachEvent('onmousedown',mouseyDown);
	}
	//alert("page loaded");
}
var mouseDownDef = false;
var mouseDownX = 0,mouseDownY = 0;
function mouseyDown(e) {
	if (e.currentTarget==flr1) {
		mouseUpDown(e,"D","floor1");
	} else if (e.currentTarget==flr2) {
		mouseUpDown(e,"D","floor2");
	} else if (e.currentTarget==flr3) {
		mouseUpDown(e,"D","floor3");
	} else if (e.currentTarget==flr4) {
		mouseUpDown(e,"D","floor4");
	}
}
function mouseyUp(e){
	mouseUpDown(e,"U");
}
function mouseUpDown(e,which,source){
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
	alert(source+" X="+mouseDownX+" Y="+mouseDownY);
}
function foo() {
	//var x;
	//var y;
	alert("foo!");
	/*if (e.pageX || e.pageY) { 
  	x = e.pageX;
  	y = e.pageY;
	} else { 
  	x = e.clientX + document.body.scrollLeft + document.documentElement.scrollLeft; 
  	y = e.clientY + document.body.scrollTop + document.documentElement.scrollTop; 
	} 
	x -= gCanvasElement.offsetLeft;
	y -= gCanvasElement.offsetTop;
	alert("clicked X="+x+" Y="+y);*/
}
function bar(e) {
	alert("bar!");
}