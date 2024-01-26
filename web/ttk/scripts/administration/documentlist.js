

var selectedPath = "";
function toRadioButton( obj, bChkd, objform ,path)
{
	selectedPath = path;
	document.forms[1].selectButton.style.display=""; 
	
}//end of function toRadioButton( obj, bChkd, objform )

//function to select the path for the file name 
function onSelect()
{
	document.forms[1].mode.value="doSelect";
	document.forms[1].docPath.value= selectedPath;
	document.forms[1].action = "/AssociateDocumentsListAction.do";
	document.forms[1].submit();		
}//end of function onSelect()

//function to go back to previous screen
function onClose()
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].action = "/AssociateDocumentsListAction.do";
	document.forms[1].submit();		
}//end of function onClose()