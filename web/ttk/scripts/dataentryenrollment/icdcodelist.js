//java script for the ICD code details screen in the Enrollment module.

//function to call edit screen
function edit(rownum)
{    
    document.forms[1].mode.value="Close";
    document.forms[1].rownum.value=rownum;       
    document.forms[1].action = "/ICDCodeAction.do";
    document.forms[1].submit();
}//end of edit(rownum)


// function to close the ICD details screen.
function onClose()
{
	if(!TrackChanges()) return false;

	document.forms[1].mode.value="Close";
	document.forms[1].action="/ICDCodeAction.do";
	document.forms[1].submit();
}//end of onClose()