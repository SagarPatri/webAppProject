

var selectedPath = "";
function toRadioButton( obj, bChkd, objform ,path)
{
	selectedPath = path;
	document.forms[1].selectButton.style.display=""; 
	
}//end of function toRadioButton( obj, bChkd, objform )

//function to select the path for the file name in the Web Configuration Information screen
//and go back for the same screen
function onSelect()
{
	document.forms[1].mode.value="doSelect";
	document.forms[1].path.value= selectedPath;
	document.forms[1].action = "/LinkDetailsAction.do";
	document.forms[1].submit();		
}//end of function onSelect()

//function to go back to previous screen
function onClose()
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].action = "/LinkDetailsAction.do";
	document.forms[1].submit();		
}//end of function onClose()