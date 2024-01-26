// Global variable to hold the handle for the Child Window
var childWindow = "";

// Global variable to recognize the browser
var nav4 = window.Event ? true : false;

// Global variables to hold the coordinates of the Child Window
var childX=0, childY=0;

// Function for opening Child Window
function openChildWindow(childURL,childFeatures) {
	childWindow = window.open(childURL, "ChildWindow", childFeatures);
}

// Function for setting the Child Window's features 
// This is the function that has to be called from the Parent Window 
// This funcion has an additional argument for accepting the Scrolling property, Resizable property and Event Object
function openWindow(childURL,childWidth,childHeight) {
	var childFeatures = "directory=no,location=no,toolbar=no,status=no";
	childFeatures = childFeatures + ",scrollbars=" + (( arguments[3] == null ) ? "no" : arguments[3]);
	childFeatures = childFeatures + ",resizable=" + (( arguments[4] == null ) ? "no" : arguments[4]);
	childFeatures = childFeatures + ",width=" + childWidth;
	childFeatures = childFeatures + ",height=" + childHeight;
	
	// Populate childX and childY with the center of the screen or the cursor position
	if(arguments[5] == null)
		getScreenCenter(childWidth,childHeight);
	else
		getCursorPosition(arguments[5],childWidth,childHeight);
	
	childFeatures = childFeatures + ",left=" + childX;
	childFeatures = childFeatures + ",top=" + childY;
	
	// Creating Dependable Popup Window, i.e close all previously opened child windows before opening a new one.
	clearChildWindows();
	
	// Open the Child Window
	openChildWindow(childURL, childFeatures);
	
}

// Function to get the Co-ordinates of Center of the Screen
function getScreenCenter(width,height) {
	childX = (640 - width)/2;
	childY = (480 - height)/2;
	if (screen) {
        childX = (screen.availWidth - width)/2;
    }
  
	if (screen) {
        childY = (screen.availHeight - height)/2;
    } 
}

// Function to get the Co-ordinates of the cursor
function getCursorPosition(evt,width,height) {
	if(nav4)	{	
		childX = evt.screenX;
		childY = evt.screenY;
	}
	else {
		childX = event.screenX;
		childY = event.screenY;
	}
	
	winX = (640 - width)/2;
	winY = (480 - height)/2;
	
	if (screen) {
   	winX = screen.availWidth;
		winY = screen.availHeight;	
		if(childX+width >= winX)	{
			childX = childX - width;
		}
		if(childY+height >= winY) {
			childY =  childY - height;
		}
	}
}

// New code added to clear all calendar instances when the browser window is closed or url is changed
function clearChildWindows() {
	if(childWindow && childWindow.open && !childWindow.closed)
		childWindow.close();
}

// E N D




