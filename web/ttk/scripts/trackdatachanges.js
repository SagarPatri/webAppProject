<!--

// ================================================================================================
/**
 * @ (#) trackdatachanges.js
 * JavaScript Functionality to track any changes made in the form controls on a screen.
 * Author      			:	Anand S J
 * Date Created			: 27th Jul 2002
 *
 * Date Modified		: 27th Oct 2005 // Modified the code to handle DotNet Control Arrays. These controls have ":" as part of their name. Eg. "Crtl:0"
 * Date Modified		: 27th Oct 2005 // Modified the code to handle Controls that do not have any NAME.
 * 
 *
 *	
 *
 * DESCRIPTION			: This page demonstrates how the changes made in any of the form controls can be detected. 
 *										This can be used in such screens where in the user MUST save the changed data before leaving a screen. 
 *										This functionality assumes that the page contains only ONE form. 
 *										It works on all the different types of form controls. It makes use of the feature that in any business form, 
 *										the names of each form control is UNIQUE. The only exception for this is in the case of radio buttons 
 *										or check boxes. When you have multiple radio buttons belonging to one single group, 
 *										i.e. at any time only one radio button can be checked. In such cases, all the grouped radio buttons 
 *										have the same name. In case of check boxes, the same name across many check boxes is used for 
 *										Check All functionality. In some circumstances, programmers do provide same name for a group of similar 
 *										controls for the ease of obtaining these control's data from the request object on the server side. 
 *										The current implementation takes care of all these exceptional conditions.
 *								
 * IMPLEMENTATION		:	The implementation does not require any modification at form control level. Hence this can be implemented 
 *										on already developed screens without much of a rework. The only addition of code will be in the <BODY> tag. 
 *										The function TrackFormData() needs to be called in onLoad event of the <BODY> tag, i.e.
 *										onLoad ="javascript:TrackFormData()" to track all the form controls on a screen.
 *										In some instances, the entire form need not take part in the tracking. In such cases, 
 *										the names of those controls that need not be tracked HAS to be passed as arguments to the function, i.e.
 *										onLoad ="javascript:TrackFormData('CtrlOne','CtrlTwo'')" to avoid tracking of CtrlOne and CtrlTwo.
 *										All the other hyperlinks that an user can click upon from this screen must call the	function TrackChanges().
 *										That is it. It is so simple to track the data changes in a form.
 *										Also, one can obtain the list of control names that has changed. This feature can be used in cases where
 *										database updation of the entire form data can be very critical/time consuming. Instead ONLY those fields
 *										that has changed can be updated at the Database end, thus saving lot of time/resources.
 *										An array of those control names that has changed can be obtained by calling function TC_GetChangedElements().
 *
 * COMPATIBILITY		:	This code has been tested on - 
 *										NS4.x , NS6.x , NS7.0 , IE5.x , IE6.0
 *	
 * NOTES						:	All the global variables/functions used here starts with "TC" which stands for Track Changes. This is done to  
 *										avoid any clash in the names of the variables used here with those used on individuals pages.
 */
// ================================================================================================

// The Initial Setup that actually defines whether 
// a Control Type is Traceable and the parameter of the object that has to be tracked. 
// These first two options can be modified as per the requirement.
var TC_TypesArray = new Array();
TC_TypesArray["TEXT"] 						= 		[true,"value","TC_CtlObject","TC_CtrlValue"];
TC_TypesArray["PASSWORD"] 					= 		[true,"value","TC_CtlObject","TC_CtrlValue"];
TC_TypesArray["HIDDEN"] 					= 		[false,"value","TC_CtlObject","TC_CtrlValue"];
TC_TypesArray["CHECKBOX"] 					= 		[true,"checked","TC_CtlObject","TC_CtrlValue"];
TC_TypesArray["RADIO"] 						= 		[true,"checked","TC_CtlObject","TC_CtrlValue"];
TC_TypesArray["SELECT-ONE"] 				= 		[true,"selectedIndex","TC_OptObject","TC_OptionValue"];
//Start added as per Multiple Browser Change Request
//TC_TypesArray["FIELDSET"]                   =       [true,"selectedIndex","TC_OptObject","TC_OptionValue"];
TC_TypesArray["FIELDSET"] 						= 		[true,"value","TC_CtlObject","TC_CtrlValue"];
//END added as per Multiple Browser Change Request
TC_TypesArray["SELECT-MULTIPLE"] 			= 		[true,"text","TC_OptObject","TC_ListValue"];
TC_TypesArray["FILE"] 						= 		[true,"value","TC_CtlObject","TC_CtrlValue"];
TC_TypesArray["TEXTAREA"] 					= 		[true,"value","TC_CtlObject","TC_CtrlValue"];
TC_TypesArray["SUBMIT"] 					= 		[false,"value","TC_CtlObject","TC_CtrlValue"];
TC_TypesArray["RESET"] 						= 		[false,"value","TC_CtlObject","TC_CtrlValue"];
TC_TypesArray["BUTTON"] 					= 		[false,"value","TC_CtlObject","TC_CtrlValue"];

// Global variables - Not to be modified.
var TC_Controls_Ignored = new Array();
var TC_DataArray = new Array();
var TC_ChangedElementsArray;
var TC_TrackingEnabled = false;
var TC_PageDataChanged = false;
var TC_ControlsArray = new Array();
var TC_UnnamedElementsIndex = 0; // Global variable to hold the index of all the un-named elements.
var TC_ConfirmMessage;

// The message that is shown to the user. The message can be changed as required.
TC_ConfirmMessage = "Data has been modified.\nWould you like to Discard the changes?";

// Method to fetch the First Option Object in a Control Array.
function TC_OptObject(optObj) {
	return optObj[0].options;
}

// Method to fetch the First Control Object in a Control Array.
function TC_CtlObject(ctrlObj) {
	return ctrlObj[0];
}

// Method to fetch the data of the Option Box.
function TC_OptionValue(optObj,val) {
	return eval("optObj."+val)
}

// Method to fetch the data of the Multiple List Box.
function TC_ListValue(optObj,val) {
	var temp = optObj.options.length+"||-||";
	for(var l=0;l<optObj.options.length;l++) {
		temp += eval("optObj.options[l]."+val);
	}
	return temp;
}

// Method to fetch the data of other Control Objects.
function TC_CtrlValue(ctrlObj,val) {
	return eval("ctrlObj."+val);
}

// Method to fetch all the Control Data.
function TC_GetControlData(frmElement) {
		var elementType = frmElement.type;
		var elementName = frmElement.name;		
		var tempNameArray = new Array();
		var tempValueArray = new Array();
		var returnArray = new Array();
		
		var ElementArray = TC_TypesArray[elementType.toUpperCase()];
		var val_to_be_copied = ElementArray[1];
		
		returnArray[0] = new Array();
		returnArray[1] = new Array();
		
		// Modified the code to handle DotNet Control Arrays. These controls have ":" as part of their name. Eg. "Crtl:0"
		var frm_ctrl_object = document.forms[1].elements[elementName];
		
		var frm_ctrl_length = 0;
		
		// Modified the code to handle Controls that do not have any NAME.
		// The IF condition enables to track those controls that do not have any NAME attribute.
		// The IF condition fails for elements without any NAME attribute and hence we will not get the value of length.
		// We can be sure that such controls cannot be a part of control array. (The length is required for control arrays).
		// Elements become part of a control array ONLY when more than one controls have the same NAME.
		if(frm_ctrl_object) frm_ctrl_length = frm_ctrl_object.length;		
		//alert(elementType+" <> "+elementName+" <> "+frm_ctrl_length);
		
		// Check if the element is part of the Control Array. If yes, then check if the first element in the array exists.
		// The reason to do so is, if the options in the select boxes (Both single and multiple) are referred using their length property,
		// then the all other element lengths get updated to the length of the options.
		// For eg., if the select box is present in the form with 5 options and the options is looped using the length property of
		// the select box, then all other element length become 5. That is, even if there is only one text box in the current form,
		// the statement document.formName.txtName.length will return 5 instead of undefined. (Happens only in Netscape 4.x)
		if(frm_ctrl_length && eval(ElementArray[2]+"(frm_ctrl_object)")) {
		
			// If the element is part of a Control Array, get the data only once.
			if(TC_ControlsArray[elementName]) {
				return;
			}
			
			TC_ControlsArray[elementName] = frm_ctrl_length;
			for(var i=0;i<frm_ctrl_length;i++) {
				tempNameArray[i] = elementName+"["+i+"]";
				tempValueArray[i] = eval(ElementArray[3]+"(frm_ctrl_object[i],val_to_be_copied)");
			}
		}
		else {
			// Modified the code to handle Controls that do not have any NAME.
			// Increment the global index for the un-named elements and store its value against this index.
			tempNameArray[tempNameArray.length] = (elementName == "") ? TC_UnnamedElementsIndex++ : elementName;	
			tempValueArray[tempValueArray.length] = eval(ElementArray[3]+"(frmElement,val_to_be_copied)");
		}
		returnArray[0] = tempNameArray;
		returnArray[1] = tempValueArray;
		return returnArray;
}

// Method that actually loads all the Control Data when the BODY loads.
function TrackFormData() {
	for( var j=0; j<arguments.length; j++ ) {
		TC_Controls_Ignored[arguments[j]] = true;
	}
	TC_TrackingEnabled = true;
	var frmObject = document.forms[1];
	if(!frmObject) return false;	//if form doesnot exists no need to track controlls
	
	var frmElements = frmObject.elements;
	var returnedArray = new Array();
	
	for(var i=0;i<frmElements.length;i++)	{
		//alert(frmElements[i].type+" <> "+frmElements[i].name+" <> "+frmElements[i].value);
		var elementName = frmElements[i].name;
		var elementType = frmElements[i].type;
		var isAnyCtrlIgnored = TC_Controls_Ignored[elementName];
		
		// Element Type is checked for existence. 
		// This is to avoid LABEL tags in Netscape 6.x which is treated as one of the form elements.
		if(!elementType || isAnyCtrlIgnored)
			continue;
		
		if( (TC_TypesArray[elementType.toUpperCase()])[0] ) {
			returnedArray = TC_GetControlData(frmElements[i]);
			if(returnedArray) {
				for(var j=0;j<returnedArray[0].length;j++) {
					TC_DataArray[returnedArray[0][j]] = returnedArray[1][j];
				}				
			}
		}
	}
	
	// Clear the array so that next calls to TrackChanges() / TC_GetChangedElements() can consider those Controls for Tracking Changes.
	// Else, the Controls that are part of the Contol Array will not be considered in TC_GetControlData().
	TC_ControlsArray = new Array();
	
	// Modified the code to handle Controls that do not have any NAME.
	// Clear the global index for un-named elements so that during the next call to TC_GetControlData we get the same index again.
	TC_UnnamedElementsIndex = 0;
}

// Method to check if any data has changed.
function TrackChanges() {
	if(!TC_TrackingEnabled) {
		return true;
	}
	
	if(typeof(TC_Disabled)!= 'undefined' && TC_Disabled) return true;
	
	if(TC_PageDataChanged) {
	    return  UserChoice();
	}
	
	var frmObject = document.forms[1];
	if(!frmObject) return true;		//if forms[1] is not there return true 
	var frmElements = frmObject.elements;
	var returnedArray = new Array();
	for(var i=0;i<frmElements.length;i++)	{
		//alert(frmElements[i].type+" <> "+frmElements[i].name+" <> "+frmElements[i].value);
		var elementName = frmElements[i].name;
		var elementType = frmElements[i].type;
		var isAnyCtrlIgnored = TC_Controls_Ignored[elementName];
		if(!elementType || isAnyCtrlIgnored)
			continue;
		
		if( (TC_TypesArray[elementType.toUpperCase()])[0] ) {
			returnedArray = TC_GetControlData(frmElements[i]);
			if(returnedArray) {
				for(var j=0;j<returnedArray[0].length;j++) {
					if(TC_DataArray[returnedArray[0][j]] != returnedArray[1][j]) {
						//alert(returnedArray[0][j]+" <.> "+returnedArray[1][j]);
						// Clear the array so that next calls to TrackChanges() / TC_GetChangedElements() can consider those Controls for Tracking Changes.
						// Else, the Controls that are part of the Contol Array will not be considered in TC_GetControlData().
						TC_ControlsArray = new Array();
						return  UserChoice();
					}
				}
			}
		}
	}
	
	// Clear the array so that next calls to TrackChanges() / TC_GetChangedElements() can consider those Controls for Tracking Changes.
	// Else, the Controls that are part of the Contol Array will not be considered in TC_GetControlData().
	TC_ControlsArray = new Array();
	
	// Modified the code to handle Controls that do not have any NAME.
	// Clear the global index for un-named elements so that during the next call to TC_GetControlData we get the same index again.
	TC_UnnamedElementsIndex = 0;
	return true;	
}

// Method to fetch the changed Control Names.
function TC_GetChangedElements() {
	if(!TC_TrackingEnabled) {
		return true;
	}
	
	// Initiate/Clear the array for next calls.
	TC_ChangedElementsArray = new Array();
	
	var frmObject = document.forms[1];
	var frmElements = frmObject.elements;
	var returnedArray = new Array();
	for(var i=0;i<frmElements.length;i++)	{
		//alert(frmElements[i].type+" <> "+frmElements[i].name+" <> "+frmElements[i].value);
		var elementName = frmElements[i].name;
		var elementType = frmElements[i].type;
		var isAnyCtrlIgnored = TC_Controls_Ignored[elementName];
		if(!elementType || isAnyCtrlIgnored)
			continue;
		
		if( (TC_TypesArray[elementType.toUpperCase()])[0] ) {
			returnedArray = TC_GetControlData(frmElements[i]);
			if(returnedArray) {
				for(var j=0;j<returnedArray[0].length;j++) {
					if(TC_DataArray[returnedArray[0][j]] != returnedArray[1][j]) {
						//alert(returnedArray[0][j]+" <.> "+returnedArray[1][j]);
						TC_ChangedElementsArray[TC_ChangedElementsArray.length] = returnedArray[0][j];
					}
				}
			}
		}
	}
	
	// Clear the array so that next calls to TrackChanges() / TC_GetChangedElements() can consider those Controls for Tracking Changes.
	// Else, the Controls that are part of the Contol Array will not be considered in TC_GetControlData().
	TC_ControlsArray = new Array();
	
	// Modified the code to handle Controls that do not have any NAME.
	// Clear the global index for un-named elements so that during the next call to TC_GetControlData we get the same index again.
	TC_UnnamedElementsIndex = 0;
	return TC_ChangedElementsArray;	
	
}

function DisplayChangedElements() {
		var tempArray = TC_GetChangedElements();
		alert("No. of Elements changed <> "+tempArray.length+"\nThese Elements changed <> "+tempArray.toString());
	}
	
function UserChoice() {
	return  confirm(TC_ConfirmMessage);
}

//-->
